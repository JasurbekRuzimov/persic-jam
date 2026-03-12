package com.gitautomator;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * GitHub REST API v3 client.
 *
 * KEY FEATURE: uploadFile() accepts an ISO-8601 timestamp so that
 * commits can be backdated — making the contribution graph show
 * activity on past dates.
 */
public class GitHubClient {

    private static final String   API_BASE = "https://api.github.com";
    private static final MediaType JSON    = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient http;
    private final String       token;
    private final String       username;
    private final Gson         gson = new Gson();

    public GitHubClient(String token, String username) {
        this.token    = token;
        this.username = username;
        this.http     = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    }

    // ── Repository ────────────────────────────────────────────────────────────

    /**
     * Creates a new public (or private) repository.
     * @return html_url of the created repo
     */
    public String createRepository(String repoName, String description,
                                   boolean isPrivate) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("name",        repoName);
        body.addProperty("description", description);
        body.addProperty("private",     isPrivate);
        body.addProperty("auto_init",   false);
        body.addProperty("has_issues",  true);
        body.addProperty("has_wiki",    false);

        Request req = buildReq(API_BASE + "/user/repos")
            .post(RequestBody.create(body.toString(), JSON))
            .build();

        try (Response resp = http.newCall(req).execute()) {
            String rb = resp.body().string();
            if (resp.code() == 201) {
                return JsonParser.parseString(rb).getAsJsonObject()
                    .get("html_url").getAsString();
            }
            if (resp.code() == 422) throw new IOException("Repo already exists: " + repoName);
            throw new IOException("createRepo [" + resp.code() + "]: " + rb);
        }
    }

    /**
     * Uploads a file with a BACKDATED commit.
     *
     * @param repoName      target repository name
     * @param filePath      path inside the repo (e.g. "src/Main.java")
     * @param content       file content (will be Base64-encoded)
     * @param commitMessage English commit message
     * @param isoTimestamp  ISO-8601 datetime for the commit e.g. "2025-01-30T09:23:07+00:00"
     *                      Pass null to use current time.
     */
    public void uploadFile(String repoName, String filePath, String content,
                           String commitMessage, String isoTimestamp)
            throws IOException, InterruptedException {

        String sha = getFileSha(repoName, filePath);

        JsonObject body = new JsonObject();
        body.addProperty("message", commitMessage);
        body.addProperty("content", Base64.getEncoder().encodeToString(content.getBytes()));

        // ── Backdate the commit ────────────────────────────────────────────
        if (isoTimestamp != null) {
            // author date  → shows on contribution graph (green squares!)
            JsonObject author = new JsonObject();
            author.addProperty("name",  username);
            author.addProperty("email", username + "@users.noreply.github.com");
            author.addProperty("date",  isoTimestamp);
            body.add("author", author);

            // committer date → metadata
            JsonObject committer = new JsonObject();
            committer.addProperty("name",  username);
            committer.addProperty("email", username + "@users.noreply.github.com");
            committer.addProperty("date",  isoTimestamp);
            body.add("committer", committer);
        }

        if (sha != null) body.addProperty("sha", sha);

        String url = String.format("%s/repos/%s/%s/contents/%s",
            API_BASE, username, repoName, filePath);
        Request req = buildReq(url)
            .put(RequestBody.create(body.toString(), JSON))
            .build();

        try (Response resp = http.newCall(req).execute()) {
            String rb = resp.body().string();
            if (resp.code() != 200 && resp.code() != 201) {
                throw new IOException("uploadFile [" + resp.code() + "]: " + rb);
            }
        }

        Thread.sleep(600);   // avoid secondary rate limit
    }

    /** Tags a repository with GitHub topics. */
    public void setTopics(String repoName, List<String> topics) throws IOException {
        JsonObject body = new JsonObject();
        body.add("names", gson.toJsonTree(topics));
        String url = String.format("%s/repos/%s/%s/topics", API_BASE, username, repoName);
        Request req = buildReq(url)
            .header("Accept", "application/vnd.github.mercy-preview+json")
            .put(RequestBody.create(body.toString(), JSON))
            .build();
        try (Response resp = http.newCall(req).execute()) {
            if (!resp.isSuccessful())
                System.out.println("[WARN] setTopics " + resp.code());
        }
    }

    /** Checks if a repo already exists. */
    public boolean repoExists(String repoName) throws IOException {
        String url = String.format("%s/repos/%s/%s", API_BASE, username, repoName);
        Request req = buildReq(url).get().build();
        try (Response resp = http.newCall(req).execute()) {
            return resp.code() == 200;
        }
    }

    /** Returns authenticated user login. */
    public String getAuthenticatedUser() throws IOException {
        Request req = buildReq(API_BASE + "/user").get().build();
        try (Response resp = http.newCall(req).execute()) {
            if (resp.isSuccessful())
                return JsonParser.parseString(resp.body().string())
                    .getAsJsonObject().get("login").getAsString();
            throw new IOException("Auth failed: " + resp.code());
        }
    }

    /** Prints and returns remaining API rate limit. */
    public int getRateLimit() throws IOException {
        Request req = buildReq(API_BASE + "/rate_limit").get().build();
        try (Response resp = http.newCall(req).execute()) {
            if (resp.isSuccessful()) {
                JsonObject core = JsonParser.parseString(resp.body().string())
                    .getAsJsonObject().getAsJsonObject("resources").getAsJsonObject("core");
                int remaining  = core.get("remaining").getAsInt();
                long resetIn   = (core.get("reset").getAsLong() - System.currentTimeMillis()/1000) / 60;
                System.out.printf("[RateLimit] remaining=%d | resets in %d min%n", remaining, resetIn);
                return remaining;
            }
        }
        return -1;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String getFileSha(String repoName, String filePath) throws IOException {
        String url = String.format("%s/repos/%s/%s/contents/%s",
            API_BASE, username, repoName, filePath);
        Request req = buildReq(url).get().build();
        try (Response resp = http.newCall(req).execute()) {
            if (resp.code() == 200) {
                JsonObject json = JsonParser.parseString(resp.body().string()).getAsJsonObject();
                return json.has("sha") ? json.get("sha").getAsString() : null;
            }
        }
        return null;
    }

    private Request.Builder buildReq(String url) {
        return new Request.Builder()
            .url(url)
            .header("Authorization",       "token " + token)
            .header("Accept",              "application/vnd.github.v3+json")
            .header("X-GitHub-Api-Version","2022-11-28")
            .header("User-Agent",          "GitHubAutoRepo/2.0");
    }
}
