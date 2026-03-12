package com.gitautomator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generates meaningful, runnable Java 21 source code for each topic.
 * Index 1 = basic/foundational, Index 2 = advanced/applied version.
 */
public class CodeGenerator {

    public Map<String, String> generateCode(String topicName, int repoIndex) {
        Map<String, String> files = new LinkedHashMap<>();
        files.put("README.md", generateReadme(topicName, repoIndex));
        String code = getCode(topicName, repoIndex);
        String className = toClassName(topicName) + (repoIndex == 1 ? "Basic" : "Advanced");
        files.put("src/" + className + ".java", code);
        return files;
    }

    // ─────────────────────────────────────────────────────────────────────────
    private String getCode(String topic, int idx) {
        String topicName = "";
        return switch (topic) {

// ══════════════════════════════════════════════════════════════════════════════
case "arithmetic-operators" -> idx == 1 ? """
public class ArithmeticOperatorsBasic {
    public static void main(String[] args) {
        int a = 17, b = 5;
        System.out.println("=== Arithmetic Operators Demo ===");
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Addition       : " + a + " + " + b + " = " + (a + b));
        System.out.println("Subtraction    : " + a + " - " + b + " = " + (a - b));
        System.out.println("Multiplication : " + a + " * " + b + " = " + (a * b));
        System.out.println("Division       : " + a + " / " + b + " = " + (a / b) + " (integer division)");
        System.out.println("Modulus        : " + a + " % " + b + " = " + (a % b) + " (remainder)");
        System.out.println("Float division : " + a + " / " + b + " = " + ((double) a / b));

        // Increment / decrement
        int x = 10;
        System.out.println("\\nBefore: x = " + x);
        System.out.println("x++ (post) = " + x++);
        System.out.println("After x++  = " + x);
        System.out.println("++x (pre)  = " + ++x);

        // Compound assignment
        int n = 100;
        n += 25;  System.out.println("\\nn += 25 → " + n);
        n -= 10;  System.out.println("n -= 10 → " + n);
        n *= 2;   System.out.println("n *= 2  → " + n);
        n /= 3;   System.out.println("n /= 3  → " + n);
        n %= 7;   System.out.println("n %= 7  → " + n);

        // Real example: bill split
        double total = 127.50;
        int people   = 4;
        double each  = total / people;
        int tip      = (int)(total * 0.10);
        System.out.printf("%nBill: $%.2f | People: %d | Each: $%.2f | Tip: $%d%n",
            total, people, each, tip);
    }
}
""" : """
public class ArithmeticOperatorsAdvanced {

    // Temperature converter using arithmetic
    static double celsiusToFahrenheit(double c) { return (c * 9.0 / 5.0) + 32; }
    static double fahrenheitToCelsius(double f) { return (f - 32) * 5.0 / 9.0; }

    // Check even/odd using modulus
    static String evenOrOdd(int n) { return n % 2 == 0 ? "even" : "odd"; }

    // Extract digits using arithmetic
    static void digitExtractor(int n) {
        System.out.println("\\nDigits of " + n + ":");
        int original = Math.abs(n);
        if (original == 0) { System.out.println("  0"); return; }
        while (original > 0) {
            System.out.print("  " + original % 10);
            original /= 10;
        }
        System.out.println();
    }

    // Compound interest: A = P(1 + r/n)^(nt)
    static double compoundInterest(double principal, double rate, int times, int years) {
        return principal * Math.pow(1 + rate / times, times * years);
    }

    public static void main(String[] args) {
        System.out.println("=== Temperature Conversion ===");
        double[] celsius = {0, 20, 37, 100, -40};
        for (double c : celsius) {
            System.out.printf("%.1f°C = %.2f°F%n", c, celsiusToFahrenheit(c));
        }

        System.out.println("\\n=== Even/Odd Checker ===");
        int[] nums = {4, 7, 0, -13, 100};
        for (int n : nums) System.out.printf("%4d is %s%n", n, evenOrOdd(n));

        digitExtractor(4829);

        System.out.println("\\n=== Compound Interest Calculator ===");
        System.out.printf("Principal $1000 | 5%% | 12x/year | 10 years → $%.2f%n",
            compoundInterest(1000, 0.05, 12, 10));
        System.out.printf("Principal $5000 | 3%% | 1x/year  |  5 years → $%.2f%n",
            compoundInterest(5000, 0.03, 1, 5));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "comparison-operators" -> idx == 1 ? """
public class ComparisonOperatorsBasic {
    public static void main(String[] args) {
        int a = 15, b = 20;
        System.out.println("=== Comparison Operators: a=" + a + ", b=" + b + " ===");
        System.out.println("a == b  : " + (a == b));
        System.out.println("a != b  : " + (a != b));
        System.out.println("a >  b  : " + (a >  b));
        System.out.println("a <  b  : " + (a <  b));
        System.out.println("a >= b  : " + (a >= b));
        System.out.println("a <= b  : " + (a <= b));

        // String comparison — IMPORTANT: use .equals(), not ==
        System.out.println("\\n=== String Comparison ===");
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        System.out.println("s1 == s2          : " + (s1 == s2));
        System.out.println("s1 == s3          : " + (s1 == s3) + " (different object!)");
        System.out.println("s1.equals(s3)     : " + s1.equals(s3) + " (correct way)");
        System.out.println("s1.equalsIgnoreCase(\"JAVA\"): " + s1.equalsIgnoreCase("JAVA"));

        // Practical: classify score
        System.out.println("\\n=== Score Classifier ===");
        int[] scores = {95, 82, 70, 55, 40};
        for (int score : scores) {
            String grade = score >= 90 ? "A" : score >= 80 ? "B" :
                           score >= 70 ? "C" : score >= 60 ? "D" : "F";
            System.out.printf("Score %3d → Grade %s%n", score, grade);
        }
    }
}
""" : """
public class ComparisonOperatorsAdvanced {

    record Product(String name, double price, int stock) {}

    static String compareAges(int age1, int age2, String name1, String name2) {
        if (age1 > age2)       return name1 + " is older";
        else if (age1 < age2)  return name2 + " is older";
        else                   return "Same age";
    }

    static String classifyBMI(double bmi) {
        if      (bmi < 18.5) return "Underweight";
        else if (bmi < 25.0) return "Normal";
        else if (bmi < 30.0) return "Overweight";
        else                 return "Obese";
    }

    public static void main(String[] args) {
        // Compare products by price and stock
        System.out.println("=== Product Comparison ===");
        Product[] products = {
            new Product("Laptop",  999.99, 5),
            new Product("Phone",   699.99, 20),
            new Product("Tablet",  399.99, 0),
            new Product("Monitor", 499.99, 12)
        };
        for (Product p : products) {
            String availability = p.stock() > 0 ? "In Stock (" + p.stock() + ")" : "OUT OF STOCK";
            boolean affordable  = p.price() <= 500.0;
            System.out.printf("%-10s $%6.2f | %-20s | Affordable: %b%n",
                p.name(), p.price(), availability, affordable);
        }

        // Age comparisons
        System.out.println("\\n=== Age Comparisons ===");
        System.out.println(compareAges(25, 30, "Alice", "Bob"));
        System.out.println(compareAges(35, 35, "Carol", "Dave"));

        // BMI calculator
        System.out.println("\\n=== BMI Classification ===");
        double[][] people = {{70, 1.75}, {90, 1.65}, {55, 1.80}, {110, 1.70}};
        for (double[] p : people) {
            double bmi = p[0] / (p[1] * p[1]);
            System.out.printf("Weight=%.0fkg Height=%.2fm BMI=%.1f → %s%n",
                p[0], p[1], bmi, classifyBMI(bmi));
        }
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "logical-operators" -> idx == 1 ? """
public class LogicalOperatorsBasic {
    public static void main(String[] args) {
        System.out.println("=== AND (&&) Operator — both must be true ===");
        boolean hasTicket = true, isOldEnough = false;
        System.out.println("Has ticket: " + hasTicket + " | Is old enough: " + isOldEnough);
        System.out.println("Can enter (&&): " + (hasTicket && isOldEnough));

        System.out.println("\\n=== OR (||) Operator — at least one true ===");
        boolean hasCash = false, hasCard = true;
        System.out.println("Has cash: " + hasCash + " | Has card: " + hasCard);
        System.out.println("Can pay (||): " + (hasCash || hasCard));

        System.out.println("\\n=== NOT (!) Operator ===");
        boolean isRaining = true;
        System.out.println("Is raining: " + isRaining);
        System.out.println("Is NOT raining: " + !isRaining);

        System.out.println("\\n=== Full Truth Table ===");
        boolean[] vals = {true, false};
        System.out.printf("%-6s %-6s | AND   | OR    | NOT A%n", "A", "B");
        System.out.println("-------+-------+-------+------");
        for (boolean a : vals)
            for (boolean b : vals)
                System.out.printf("%-6b %-6b | %-5b | %-5b | %-5b%n", a, b, a&&b, a||b, !a);

        // Short-circuit evaluation
        System.out.println("\\n=== Short-Circuit Evaluation ===");
        int x = 0;
        boolean result = (x != 0) && (10 / x > 1);  // Safe: right side not evaluated
        System.out.println("(x!=0) && (10/x>1) = " + result + " — no division by zero!");
    }
}
""" : """
public class LogicalOperatorsAdvanced {

    static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    static boolean isValidAge(int age)           { return age >= 0 && age <= 150; }
    static boolean isWeekend(String day)         { return day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Sunday"); }
    static boolean isValidEmail(String email)    { return email.contains("@") && email.contains(".") && email.length() > 5; }
    static boolean isInRange(int val, int lo, int hi) { return val >= lo && val <= hi; }

    static String accessLevel(boolean isAdmin, boolean isLoggedIn, boolean isBanned) {
        if (isBanned)                    return "BANNED";
        if (!isLoggedIn)                 return "GUEST";
        if (isLoggedIn && isAdmin)       return "ADMIN";
        return "USER";
    }

    public static void main(String[] args) {
        System.out.println("=== Leap Year Checker ===");
        int[] years = {2000, 1900, 2024, 2023, 2100, 2400};
        for (int y : years)
            System.out.printf("%d: %s%n", y, isLeapYear(y) ? "LEAP YEAR" : "not leap year");

        System.out.println("\\n=== Email Validation ===");
        String[] emails = {"user@example.com", "invalid", "test@", "@no.com", "ok@mail.org"};
        for (String e : emails)
            System.out.printf("%-20s → %s%n", e, isValidEmail(e) ? "valid" : "invalid");

        System.out.println("\\n=== Access Control System ===");
        Object[][] users = {
            {"Alice",  true,  true,  false},
            {"Bob",    false, true,  false},
            {"Charlie",false, false, false},
            {"Dave",   false, true,  true}
        };
        for (Object[] u : users)
            System.out.printf("%-10s → %s%n", u[0],
                accessLevel((Boolean)u[1], (Boolean)u[2], (Boolean)u[3]));

        System.out.println("\\n=== Weekend Checker ===");
        String[] days = {"Monday", "Saturday", "Sunday", "Wednesday", "Friday"};
        for (String d : days)
            System.out.printf("%-12s → %s%n", d, isWeekend(d) ? "Weekend!" : "Weekday");
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "scanner-input" -> idx == 1 ? """
import java.util.Scanner;

public class ScannerInputBasic {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Scanner Input Demo ===");
        System.out.println("(Demo mode: using predefined inputs to show what Scanner does)\\n");

        // In real usage you'd call scanner.nextXxx() and wait for user
        // Below we show all Scanner methods with documentation
        System.out.println("Scanner methods:");
        System.out.println("  scanner.nextInt()     — reads integer:    42");
        System.out.println("  scanner.nextDouble()  — reads double:     3.14");
        System.out.println("  scanner.nextLine()    — reads full line:  Hello World");
        System.out.println("  scanner.nextBoolean() — reads boolean:    true");
        System.out.println("  scanner.next()        — reads one word:   Java");

        // Simulated input processing
        System.out.println("\\n=== Simulated Student Record Entry ===");
        String name   = "Alice";
        int    age    = 20;
        double gpa    = 3.85;
        String major  = "Computer Science";

        System.out.println("--- Processing Student ---");
        System.out.println("Name  : " + name);
        System.out.println("Age   : " + age);
        System.out.printf( "GPA   : %.2f%n", gpa);
        System.out.println("Major : " + major);
        System.out.printf( "Status: %s%n", gpa >= 3.5 ? "Dean's List" : "Regular");

        scanner.close();
        System.out.println("\\nScanner closed. In real app: scanner reads from System.in");
    }
}
""" : """
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ScannerInputAdvanced {

    record Student(String name, int age, double gpa) {
        String status() {
            if (gpa >= 3.5) return "Dean's List";
            if (gpa >= 2.0) return "Good Standing";
            return "Academic Probation";
        }
    }

    // Input validation helper (for demo: shows validation logic)
    static boolean isValidAge(int age)   { return age >= 16 && age <= 100; }
    static boolean isValidGPA(double g)  { return g >= 0.0 && g <= 4.0; }
    static boolean isValidName(String n) { return n != null && n.trim().length() >= 2; }

    public static void main(String[] args) {
        // Simulate multi-student input processing
        System.out.println("=== Student Registration System ===");
        System.out.println("(Simulated — replace arrays with Scanner.nextLine() for live input)\\n");

        List<Student> students = new ArrayList<>();

        // Simulated entries
        String[][] data = {
            {"Alice Johnson", "20", "3.9"},
            {"Bob Smith",     "22", "2.8"},
            {"Carol White",   "19", "1.5"},
            {"Dave Brown",    "21", "3.5"}
        };

        for (String[] row : data) {
            String name = row[0].trim();
            int    age  = Integer.parseInt(row[1].trim());
            double gpa  = Double.parseDouble(row[2].trim());

            if (!isValidName(name))  { System.out.println("Invalid name: "  + name); continue; }
            if (!isValidAge(age))    { System.out.println("Invalid age: "   + age);  continue; }
            if (!isValidGPA(gpa))    { System.out.println("Invalid GPA: "   + gpa);  continue; }

            students.add(new Student(name, age, gpa));
            System.out.printf("Registered: %-15s Age: %d GPA: %.1f%n", name, age, gpa);
        }

        System.out.println("\\n=== Report ===");
        System.out.printf("Total students: %d%n", students.size());
        double avgGPA = students.stream().mapToDouble(Student::gpa).average().orElse(0);
        System.out.printf("Average GPA: %.2f%n", avgGPA);

        System.out.println("\\nStudent Status:");
        students.forEach(s -> System.out.printf("  %-15s → %s%n", s.name(), s.status()));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "if-else" -> idx == 1 ? """
public class IfElseBasic {
    public static void main(String[] args) {
        System.out.println("=== If-Else Demo ===\\n");

        // Basic if-else
        int temperature = 28;
        if (temperature > 30) {
            System.out.println("It's hot! Stay hydrated.");
        } else if (temperature >= 20) {
            System.out.println("Pleasant weather. Enjoy your day!");
        } else if (temperature >= 10) {
            System.out.println("Chilly. Grab a jacket.");
        } else {
            System.out.println("Freezing! Stay warm.");
        }

        // Grade calculator
        System.out.println("\\n=== Grade Calculator ===");
        int[] scores = {95, 82, 73, 61, 45};
        for (int score : scores) {
            String grade;
            String feedback;
            if (score >= 90) {
                grade    = "A";
                feedback = "Excellent!";
            } else if (score >= 80) {
                grade    = "B";
                feedback = "Good job!";
            } else if (score >= 70) {
                grade    = "C";
                feedback = "Satisfactory";
            } else if (score >= 60) {
                grade    = "D";
                feedback = "Needs improvement";
            } else {
                grade    = "F";
                feedback = "Failed";
            }
            System.out.printf("Score: %3d | Grade: %s | %s%n", score, grade, feedback);
        }

        // FizzBuzz — classic programming challenge
        System.out.println("\\n=== FizzBuzz (1-20) ===");
        for (int i = 1; i <= 20; i++) {
            if (i % 15 == 0)      System.out.print("FizzBuzz ");
            else if (i % 3 == 0)  System.out.print("Fizz ");
            else if (i % 5 == 0)  System.out.print("Buzz ");
            else                  System.out.print(i + " ");
        }
        System.out.println();
    }
}
""" : """
public class IfElseAdvanced {

    static String getShippingMethod(double weight, double distance, boolean express) {
        if (weight > 30)           return "Freight shipping required";
        if (express && weight < 5) return "Express air delivery (2-3 days)";
        if (distance > 1000)       return "International shipping (7-14 days)";
        if (weight < 1)            return "Standard mail (3-5 days)";
        return "Standard ground shipping (5-7 days)";
    }

    static double calculateTax(double income) {
        if      (income <= 10_000)  return income * 0.0;
        else if (income <= 40_000)  return income * 0.12;
        else if (income <= 85_000)  return income * 0.22;
        else if (income <= 163_000) return income * 0.24;
        else                        return income * 0.32;
    }

    static String classify(int n) {
        if (n < 0)         return "Negative";
        if (n == 0)        return "Zero";
        if (n % 2 == 0)    return "Positive Even";
        if (isPrime(n))    return "Prime";
        return "Positive Odd (composite)";
    }

    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) if (n % i == 0) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("=== Shipping Method Selector ===");
        System.out.println(getShippingMethod(0.5, 200, false));
        System.out.println(getShippingMethod(2.0, 500, true));
        System.out.println(getShippingMethod(35, 100, false));
        System.out.println(getShippingMethod(5.0, 1500, false));

        System.out.println("\\n=== Tax Calculator ===");
        double[] incomes = {8_000, 25_000, 60_000, 120_000, 200_000};
        for (double income : incomes)
            System.out.printf("Income: $%,8.0f → Tax: $%,.0f (%.0f%%)%n",
                income, calculateTax(income), calculateTax(income)/income*100);

        System.out.println("\\n=== Number Classifier ===");
        int[] nums = {-5, 0, 2, 7, 9, 12, 13, 100};
        for (int n : nums)
            System.out.printf("%4d → %s%n", n, classify(n));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "nested-if" -> idx == 1 ? """
public class NestedIfBasic {
    public static void main(String[] args) {
        System.out.println("=== Nested If — Login System ===");
        boolean isRegistered = true;
        boolean correctPassword = true;
        boolean isActive = true;

        if (isRegistered) {
            System.out.println("User found.");
            if (correctPassword) {
                System.out.println("Password correct.");
                if (isActive) {
                    System.out.println("Welcome! Access granted.");
                } else {
                    System.out.println("Account is deactivated. Contact support.");
                }
            } else {
                System.out.println("Wrong password. Try again.");
            }
        } else {
            System.out.println("User not found. Please register.");
        }

        // Nested if: Season + weather activity
        System.out.println("\\n=== Activity Recommender ===");
        String season = "Summer";
        boolean isSunny = true;
        boolean hasCompany = false;

        if (season.equals("Summer")) {
            if (isSunny) {
                if (hasCompany) {
                    System.out.println("Go to the beach with friends!");
                } else {
                    System.out.println("Take a solo bike ride.");
                }
            } else {
                System.out.println("Visit a museum or indoor cafe.");
            }
        } else if (season.equals("Winter")) {
            if (isSunny) {
                System.out.println("Take a winter walk in the park.");
            } else {
                System.out.println("Stay home and read a book.");
            }
        }

        // Ticket pricing nested logic
        System.out.println("\\n=== Movie Ticket Pricing ===");
        boolean isMember = true;
        boolean isWeekend = true;
        int age = 15;
        double price;
        if (age < 12) {
            price = 5.0;
        } else if (age >= 65) {
            price = 7.0;
        } else {
            if (isMember) {
                price = isWeekend ? 10.0 : 8.0;
            } else {
                price = isWeekend ? 14.0 : 12.0;
            }
        }
        System.out.printf("Age: %d | Member: %b | Weekend: %b → $%.2f%n",
            age, isMember, isWeekend, price);
    }
}
""" : """
public class NestedIfAdvanced {

    static String shipmentStatus(double weight, String destination, boolean paid) {
        if (!paid) {
            return "HOLD: Payment required";
        }
        if (destination.equals("Domestic")) {
            if (weight <= 5)        return "Domestic Standard (3 days)";
            else if (weight <= 20)  return "Domestic Freight (5 days)";
            else                    return "Domestic Heavy Freight (7 days)";
        } else if (destination.equals("International")) {
            if (weight <= 2)        return "International Express (5 days)";
            else if (weight <= 10)  return "International Standard (14 days)";
            else                    return "International Freight (21 days)";
        }
        return "Unknown destination";
    }

    static String loanDecision(int creditScore, double income, double requestedAmount) {
        if (creditScore < 580) {
            return "DENIED — credit score too low";
        }
        if (income < 20_000) {
            return "DENIED — insufficient income";
        }
        double ratio = requestedAmount / income;
        if (creditScore >= 750) {
            if (ratio <= 5.0)  return "APPROVED — Premium rate 3.5%";
            else               return "APPROVED — Premium rate 4.0% (high ratio)";
        } else if (creditScore >= 650) {
            if (ratio <= 3.0)  return "APPROVED — Standard rate 6.0%";
            else               return "CONDITIONAL — reduce amount by 20%";
        } else {
            if (ratio <= 1.5)  return "APPROVED — Subprime rate 9.5%";
            else               return "DENIED — too risky at this amount";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Shipment Routing ===");
        System.out.println(shipmentStatus(1.5, "Domestic", true));
        System.out.println(shipmentStatus(25,  "Domestic", true));
        System.out.println(shipmentStatus(3.0, "International", true));
        System.out.println(shipmentStatus(5.0, "International", false));

        System.out.println("\\n=== Loan Decision Engine ===");
        System.out.println(loanDecision(800, 75_000, 200_000));
        System.out.println(loanDecision(700, 50_000, 100_000));
        System.out.println(loanDecision(620, 40_000, 200_000));
        System.out.println(loanDecision(550, 30_000,  50_000));
        System.out.println(loanDecision(660, 15_000,  20_000));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "switch-case" -> idx == 1 ? """
public class SwitchCaseBasic {
    public static void main(String[] args) {
        // Classic switch-case
        System.out.println("=== Day of Week (classic switch) ===");
        int day = 3;
        String dayName;
        switch (day) {
            case 1: dayName = "Monday";    break;
            case 2: dayName = "Tuesday";   break;
            case 3: dayName = "Wednesday"; break;
            case 4: dayName = "Thursday";  break;
            case 5: dayName = "Friday";    break;
            case 6: dayName = "Saturday";  break;
            case 7: dayName = "Sunday";    break;
            default: dayName = "Invalid";
        }
        System.out.println("Day " + day + " = " + dayName);

        // Modern switch expression (Java 14+)
        System.out.println("\\n=== Season Finder (modern switch) ===");
        String[] months = {"January","March","June","September","November","December"};
        for (String month : months) {
            String season = switch (month) {
                case "December","January","February" -> "Winter";
                case "March","April","May"           -> "Spring";
                case "June","July","August"          -> "Summer";
                case "September","October","November"-> "Autumn";
                default                              -> "Unknown";
            };
            System.out.printf("%-12s → %s%n", month, season);
        }

        // Switch with String — Calculator menu
        System.out.println("\\n=== Simple Calculator Menu ===");
        double x = 15.0, y = 4.0;
        String[] ops = {"+", "-", "*", "/", "%"};
        for (String op : ops) {
            double result = switch (op) {
                case "+"  -> x + y;
                case "-"  -> x - y;
                case "*"  -> x * y;
                case "/"  -> y != 0 ? x / y : Double.NaN;
                case "%"  -> x % y;
                default   -> throw new IllegalArgumentException("Unknown op: " + op);
            };
            System.out.printf("%.1f %s %.1f = %.2f%n", x, op, y, result);
        }
    }
}
""" : """
public class SwitchCaseAdvanced {

    enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
    enum HttpMethod { GET, POST, PUT, DELETE, PATCH }

    static int priorityScore(Priority p) {
        return switch (p) {
            case LOW      -> 1;
            case MEDIUM   -> 5;
            case HIGH     -> 10;
            case CRITICAL -> 100;
        };
    }

    static String handleHttpMethod(HttpMethod method, String resource) {
        return switch (method) {
            case GET    -> "Fetching " + resource;
            case POST   -> "Creating new " + resource;
            case PUT    -> "Replacing " + resource;
            case DELETE -> "Deleting " + resource;
            case PATCH  -> "Updating fields of " + resource;
        };
    }

    static double convertCurrency(double amount, String from, String to) {
        // Normalize to USD first
        double usd = switch (from.toUpperCase()) {
            case "USD" -> amount;
            case "EUR" -> amount * 1.08;
            case "GBP" -> amount * 1.27;
            case "JPY" -> amount * 0.0067;
            default    -> throw new IllegalArgumentException("Unknown currency: " + from);
        };
        return switch (to.toUpperCase()) {
            case "USD" -> usd;
            case "EUR" -> usd / 1.08;
            case "GBP" -> usd / 1.27;
            case "JPY" -> usd / 0.0067;
            default    -> throw new IllegalArgumentException("Unknown currency: " + to);
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Priority System ===");
        for (Priority p : Priority.values())
            System.out.printf("%-8s → score: %d%n", p, priorityScore(p));

        System.out.println("\\n=== HTTP Method Router ===");
        for (HttpMethod m : HttpMethod.values())
            System.out.println(handleHttpMethod(m, "users"));

        System.out.println("\\n=== Currency Converter ===");
        System.out.printf("$100 USD → EUR: %.2f%n", convertCurrency(100, "USD", "EUR"));
        System.out.printf("£50 GBP  → JPY: %.0f%n", convertCurrency(50,  "GBP", "JPY"));
        System.out.printf("¥10000 JPY → USD: $%.2f%n", convertCurrency(10000,"JPY","USD"));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "for-loop" -> idx == 1 ? """
public class ForLoopBasic {
    public static void main(String[] args) {
        // Basic for loop
        System.out.println("=== Counting ===");
        for (int i = 1; i <= 5; i++) System.out.print(i + " ");
        System.out.println();

        // Countdown
        System.out.println("\\n=== Countdown ===");
        for (int i = 10; i >= 1; i--) System.out.print(i + " ");
        System.out.println("🚀");

        // Step
        System.out.println("\\n=== Even numbers (step 2) ===");
        for (int i = 2; i <= 20; i += 2) System.out.print(i + " ");
        System.out.println();

        // Multiplication table
        System.out.println("\\n=== Multiplication Table (5) ===");
        for (int i = 1; i <= 10; i++)
            System.out.printf("5 × %2d = %3d%n", i, 5 * i);

        // Nested for loops — pattern
        System.out.println("\\n=== Star Triangle ===");
        for (int row = 1; row <= 5; row++) {
            for (int col = 1; col <= row; col++) System.out.print("* ");
            System.out.println();
        }

        // Enhanced for-each
        System.out.println("\\n=== For-Each Loop ===");
        String[] fruits = {"Apple", "Banana", "Cherry", "Date"};
        for (String fruit : fruits) System.out.println("  → " + fruit);

        // Sum with for loop
        int sum = 0;
        for (int i = 1; i <= 100; i++) sum += i;
        System.out.println("\\nSum 1..100 = " + sum);
    }
}
""" : """
public class ForLoopAdvanced {

    // Print an N×N times table
    static void timesTable(int n) {
        System.out.printf("%n=== %d × %d Times Table ===%n", n, n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) System.out.printf("%4d", i * j);
            System.out.println();
        }
    }

    // Check if a number is prime
    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) if (n % i == 0) return false;
        return true;
    }

    // Bubble sort using for loops
    static int[] bubbleSort(int[] arr) {
        int[] a = arr.clone();
        for (int i = 0; i < a.length - 1; i++)
            for (int j = 0; j < a.length - i - 1; j++)
                if (a[j] > a[j+1]) { int t = a[j]; a[j] = a[j+1]; a[j+1] = t; }
        return a;
    }

    static void printDiamond(int n) {
        System.out.println("\\n=== Diamond (n=" + n + ") ===");
        for (int i = 1; i <= n; i++) {
            for (int s = n-i;  s > 0; s--) System.out.print(" ");
            for (int j = 1; j <= (2*i-1); j++) System.out.print("*");
            System.out.println();
        }
        for (int i = n-1; i >= 1; i--) {
            for (int s = n-i; s > 0; s--) System.out.print(" ");
            for (int j = 1; j <= (2*i-1); j++) System.out.print("*");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Primes up to 50
        System.out.print("=== Primes up to 50 === \\n");
        for (int i = 2; i <= 50; i++) if (isPrime(i)) System.out.print(i + " ");
        System.out.println();

        // Bubble sort demo
        int[] data = {64, 34, 25, 12, 22, 11, 90};
        System.out.print("\\nUnsorted: "); for (int n : data) System.out.print(n + " ");
        int[] sorted = bubbleSort(data);
        System.out.print("\\nSorted:   "); for (int n : sorted) System.out.print(n + " ");
        System.out.println();

        timesTable(5);
        printDiamond(4);
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "while-loop" -> idx == 1 ? """
public class WhileLoopBasic {
    public static void main(String[] args) {
        // Basic while
        System.out.println("=== Basic While Loop ===");
        int count = 1;
        while (count <= 5) {
            System.out.println("Count: " + count);
            count++;
        }

        // Sum while loop
        int n = 1, sum = 0;
        while (n <= 100) { sum += n; n++; }
        System.out.println("\\nSum 1..100 = " + sum);

        // Collatz sequence
        System.out.println("\\n=== Collatz Sequence (starting at 27) ===");
        long num = 27, steps = 0;
        System.out.print(num);
        while (num != 1) {
            num = (num % 2 == 0) ? num / 2 : 3 * num + 1;
            System.out.print(" → " + num);
            steps++;
            if (steps % 8 == 0) System.out.println();
        }
        System.out.println("\\nSteps: " + steps);

        // Digit reversal
        System.out.println("\\n=== Digit Reversal ===");
        int original = 12345, reversed = 0, temp = original;
        while (temp != 0) {
            reversed = reversed * 10 + temp % 10;
            temp /= 10;
        }
        System.out.println(original + " reversed = " + reversed);

        // Number guessing game simulation
        System.out.println("\\n=== Guessing Game Simulation ===");
        int secret = 42, guess = 0, attempts = 0;
        int[] guesses = {10, 60, 30, 50, 42};
        while (guess != secret && attempts < guesses.length) {
            guess = guesses[attempts++];
            if (guess < secret)       System.out.println("Guess " + guess + " → Too low!");
            else if (guess > secret)  System.out.println("Guess " + guess + " → Too high!");
            else                      System.out.println("Guess " + guess + " → Correct! in " + attempts + " tries");
        }
    }
}
""" : """
public class WhileLoopAdvanced {

    // GCD using Euclidean algorithm
    static int gcd(int a, int b) {
        while (b != 0) { int t = b; b = a % b; a = t; }
        return a;
    }

    // Convert decimal to binary
    static String decToBinary(int n) {
        if (n == 0) return "0";
        StringBuilder sb = new StringBuilder();
        int temp = Math.abs(n);
        while (temp > 0) { sb.insert(0, temp % 2); temp /= 2; }
        return (n < 0 ? "-" : "") + sb;
    }

    // Sum of digits
    static int digitSum(int n) {
        int s = 0;
        n = Math.abs(n);
        while (n > 0) { s += n % 10; n /= 10; }
        return s;
    }

    // Happy number check
    static boolean isHappy(int n) {
        int slow = n, fast = sumSquareDigits(sumSquareDigits(n));
        while (fast != 1 && slow != fast) { slow = sumSquareDigits(slow); fast = sumSquareDigits(sumSquareDigits(fast)); }
        return fast == 1;
    }
    static int sumSquareDigits(int n) {
        int s = 0; while (n > 0) { int d = n % 10; s += d*d; n /= 10; } return s;
    }

    public static void main(String[] args) {
        System.out.println("=== GCD using Euclidean Algorithm ===");
        int[][] pairs = {{48, 18}, {100, 75}, {17, 13}, {252, 105}};
        for (int[] p : pairs)
            System.out.printf("GCD(%d, %d) = %d%n", p[0], p[1], gcd(p[0], p[1]));

        System.out.println("\\n=== Decimal to Binary ===");
        int[] nums = {0, 1, 5, 10, 42, 255, 1024};
        for (int n : nums)
            System.out.printf("%5d → %s%n", n, decToBinary(n));

        System.out.println("\\n=== Digit Sum ===");
        for (int n : new int[]{0, 9, 123, 9999, -456})
            System.out.printf("digitSum(%5d) = %d%n", n, digitSum(n));

        System.out.println("\\n=== Happy Numbers 1-50 ===");
        for (int i = 1; i <= 50; i++) if (isHappy(i)) System.out.print(i + " ");
        System.out.println();
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "do-while-loop" -> idx == 1 ? """
public class DoWhileLoopBasic {
    public static void main(String[] args) {
        // Basic do-while (always executes at least once)
        System.out.println("=== Basic Do-While ===");
        int count = 1;
        do {
            System.out.println("Iteration: " + count);
            count++;
        } while (count <= 5);

        // Key difference: do-while runs at least once
        System.out.println("\\n=== Do-while vs While (condition false from start) ===");
        int x = 10;
        System.out.print("while loop   (x=10, condition x<5): ");
        while (x < 5) System.out.print(x++ + " ");
        System.out.println("(never ran)");

        System.out.print("do-while loop (x=10, condition x<5): ");
        int y = 10;
        do { System.out.print(y + " "); y++; } while (y < 5);
        System.out.println("(ran once!)");

        // ATM menu simulation
        System.out.println("\\n=== ATM Menu Simulation ===");
        double balance = 1000.0;
        int[]  choices = {1, 3, 2, 4};  // Simulate: balance, withdraw, deposit, exit
        double[] amounts = {0, 200, 150, 0};
        int menuIdx = 0;
        do {
            int choice = choices[menuIdx];
            double amount = amounts[menuIdx++];
            switch (choice) {
                case 1 -> System.out.printf("Balance: $%.2f%n", balance);
                case 2 -> { balance += amount; System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance); }
                case 3 -> {
                    if (amount > balance) System.out.println("Insufficient funds!");
                    else { balance -= amount; System.out.printf("Withdrew $%.2f. Remaining: $%.2f%n", amount, balance); }
                }
                case 4 -> System.out.println("Thank you. Goodbye!");
            }
        } while (choices[menuIdx-1] != 4 && menuIdx < choices.length);
    }
}
""" : """
public class DoWhileLoopAdvanced {

    // Validate a PIN (simulated)
    static int validatePin(int[] attempts, int correctPin) {
        int idx = 0, triesLeft = 3;
        do {
            int entered = attempts[idx++];
            triesLeft--;
            if (entered == correctPin) {
                System.out.println("  PIN accepted!");
                return idx;
            }
            if (triesLeft > 0) System.out.println("  Wrong PIN. Tries left: " + triesLeft);
            else System.out.println("  Account locked after 3 failed attempts.");
        } while (triesLeft > 0 && idx < attempts.length);
        return -1;
    }

    // Roman numeral converter
    static String toRoman(int num) {
        int[]    vals  = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] syms  = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder sb = new StringBuilder();
        int i = 0;
        do {
            while (num >= vals[i]) { sb.append(syms[i]); num -= vals[i]; }
            i++;
        } while (num > 0 && i < vals.length);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== PIN Validation ===");
        System.out.println("Test 1 (correct on 2nd try):");
        validatePin(new int[]{1234, 4321}, 4321);
        System.out.println("Test 2 (all wrong):");
        validatePin(new int[]{1111, 2222, 3333}, 4321);

        System.out.println("\\n=== Roman Numerals ===");
        int[] nums = {1, 4, 9, 14, 40, 90, 399, 1994, 2024, 3999};
        for (int n : nums)
            System.out.printf("%5d = %s%n", n, toRoman(n));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "break-and-continue" -> idx == 1 ? """
public class BreakAndContinueBasic {
    public static void main(String[] args) {
        // break — exit loop early
        System.out.println("=== break: Stop at first multiple of 7 ===");
        for (int i = 1; i <= 30; i++) {
            if (i % 7 == 0) { System.out.println("Found: " + i + " — stopping."); break; }
            System.out.print(i + " ");
        }

        // continue — skip current iteration
        System.out.println("\\n\\n=== continue: Skip multiples of 3 ===");
        for (int i = 1; i <= 20; i++) {
            if (i % 3 == 0) continue;
            System.out.print(i + " ");
        }

        // Search with break
        System.out.println("\\n\\n=== Linear Search (break when found) ===");
        int[] arr    = {15, 3, 47, 8, 23, 99, 6, 42};
        int   target = 23;
        int   foundAt = -1;
        for (int i = 0; i < arr.length; i++) {
            System.out.println("  Checking index " + i + " → " + arr[i]);
            if (arr[i] == target) { foundAt = i; break; }
        }
        System.out.println(target + (foundAt >= 0 ? " found at index " + foundAt : " not found"));

        // continue in while loop
        System.out.println("\\n=== Print only even numbers (continue odd) ===");
        int n = 0;
        while (n < 20) {
            n++;
            if (n % 2 != 0) continue;
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
""" : """
public class BreakAndContinueAdvanced {

    // Labeled break — exit from nested loops
    static int[] findInMatrix(int[][] matrix, int target) {
        outer:
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                if (matrix[r][c] == target) {
                    System.out.printf("Found %d at [%d][%d]%n", target, r, c);
                    break outer;  // Exits BOTH loops
                }
            }
        }
        return new int[]{-1, -1};
    }

    // Print primes using continue
    static void printPrimes(int limit) {
        System.out.println("Primes up to " + limit + ":");
        for (int n = 2; n <= limit; n++) {
            boolean composite = false;
            for (int d = 2; d * d <= n; d++) {
                if (n % d == 0) { composite = true; break; }
            }
            if (composite) continue;
            System.out.print(n + " ");
        }
        System.out.println();
    }

    // Process valid transactions only (continue on invalid)
    static double processTransactions(double[] transactions) {
        double balance = 1000.0;
        System.out.println("\\nProcessing transactions:");
        for (double t : transactions) {
            if (Double.isNaN(t)) { System.out.println("  SKIP: invalid transaction"); continue; }
            if (t < 0 && Math.abs(t) > balance) { System.out.println("  SKIP: insufficient funds for " + t); continue; }
            balance += t;
            System.out.printf("  %+.2f → balance: $%.2f%n", t, balance);
            if (balance <= 0) { System.out.println("  STOP: account emptied"); break; }
        }
        return balance;
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {5,  12, 8},
            {3,  99, 7},
            {42, 1, 15}
        };
        System.out.println("=== Labeled Break in Matrix Search ===");
        findInMatrix(matrix, 99);
        findInMatrix(matrix, 42);

        System.out.println("\\n=== Prime Sieve with continue ===");
        printPrimes(50);

        double balance = processTransactions(
            new double[]{+200, -50, Double.NaN, -2000, +100, -300, +150}
        );
        System.out.printf("Final balance: $%.2f%n", balance);
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "arrays" -> idx == 1 ? """
import java.util.Arrays;

public class ArraysBasic {
    public static void main(String[] args) {
        // Declaration and initialization
        System.out.println("=== Array Declaration ===");
        int[] nums = {10, 20, 30, 40, 50};
        System.out.println("Array    : " + Arrays.toString(nums));
        System.out.println("Length   : " + nums.length);
        System.out.println("First    : " + nums[0]);
        System.out.println("Last     : " + nums[nums.length - 1]);

        // Modification
        nums[2] = 99;
        System.out.println("After nums[2]=99: " + Arrays.toString(nums));

        // Iteration
        System.out.println("\\n=== Iterating ===");
        for (int i = 0; i < nums.length; i++) System.out.printf("nums[%d] = %d%n", i, nums[i]);

        // 2D Array — grade matrix
        System.out.println("\\n=== 2D Array: Grade Matrix ===");
        int[][] grades = {
            {85, 90, 78},
            {92, 88, 95},
            {70, 75, 80}
        };
        String[] students = {"Alice", "Bob", "Carol"};
        String[] subjects  = {"Math", "English", "Science"};

        System.out.printf("%-8s", "");
        for (String s : subjects) System.out.printf("%-10s", s);
        System.out.println();
        for (int i = 0; i < grades.length; i++) {
            System.out.printf("%-8s", students[i]);
            for (int j = 0; j < grades[i].length; j++) System.out.printf("%-10d", grades[i][j]);
            System.out.println();
        }

        // Copying arrays
        System.out.println("\\n=== Array Copy ===");
        int[] original  = {1, 2, 3, 4, 5};
        int[] copy      = Arrays.copyOf(original, original.length);
        int[] partial   = Arrays.copyOfRange(original, 1, 4);
        System.out.println("Original : " + Arrays.toString(original));
        System.out.println("Full copy: " + Arrays.toString(copy));
        System.out.println("Partial  : " + Arrays.toString(partial));
    }
}
""" : """
import java.util.Arrays;

public class ArraysAdvanced {

    // Matrix multiplication
    static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length, m = B[0].length, k = B.length;
        int[][] C = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int p = 0; p < k; p++) C[i][j] += A[i][p] * B[p][j];
        return C;
    }

    static void printMatrix(int[][] m, String label) {
        System.out.println(label);
        for (int[] row : m) System.out.println("  " + Arrays.toString(row));
    }

    // Spiral matrix traversal
    static int[] spiralOrder(int[][] matrix) {
        int top=0, bottom=matrix.length-1, left=0, right=matrix[0].length-1, idx=0;
        int[] res = new int[matrix.length * matrix[0].length];
        while (top<=bottom && left<=right) {
            for (int i=left;  i<=right;  i++) res[idx++] = matrix[top][i];   top++;
            for (int i=top;   i<=bottom; i++) res[idx++] = matrix[i][right]; right--;
            if (top<=bottom) { for (int i=right; i>=left; i--) res[idx++] = matrix[bottom][i]; bottom--; }
            if (left<=right) { for (int i=bottom;i>=top;  i--) res[idx++] = matrix[i][left];  left++; }
        }
        return res;
    }

    public static void main(String[] args) {
        // Sorting and searching
        System.out.println("=== Sort & Binary Search ===");
        int[] data = {64, 34, 25, 12, 22, 11, 90, 42};
        System.out.println("Before: " + Arrays.toString(data));
        Arrays.sort(data);
        System.out.println("After:  " + Arrays.toString(data));
        int idx = Arrays.binarySearch(data, 42);
        System.out.println("binarySearch(42) → index " + idx);

        // Matrix multiply
        int[][] A = {{1,2,3},{4,5,6}};
        int[][] B = {{7,8},{9,10},{11,12}};
        printMatrix(A, "\\nMatrix A (2x3):");
        printMatrix(B, "Matrix B (3x2):");
        printMatrix(multiply(A,B), "A × B (2x2):");

        // Spiral
        int[][] spiral = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println("\\nSpiral order of 3x3 matrix:");
        System.out.println(Arrays.toString(spiralOrder(spiral)));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "array-operations" -> idx == 1 ? """
import java.util.Arrays;

public class ArrayOperationsBasic {

    static int findMin(int[] arr) {
        int min = arr[0];
        for (int n : arr) if (n < min) min = n;
        return min;
    }

    static int findMax(int[] arr) {
        int max = arr[0];
        for (int n : arr) if (n > max) max = n;
        return max;
    }

    static long sum(int[] arr) {
        long s = 0; for (int n : arr) s += n; return s;
    }

    static double average(int[] arr) {
        return (double) sum(arr) / arr.length;
    }

    static int[] reverse(int[] arr) {
        int[] r = new int[arr.length];
        for (int i = 0; i < arr.length; i++) r[i] = arr[arr.length - 1 - i];
        return r;
    }

    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) if (arr[i] == target) return i;
        return -1;
    }

    static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) if (arr[i] > arr[i+1]) return false;
        return true;
    }

    public static void main(String[] args) {
        int[] data = {34, 7, 23, 32, 5, 62, 18, 45, 9, 71};
        System.out.println("Data    : " + Arrays.toString(data));
        System.out.println("Min     : " + findMin(data));
        System.out.println("Max     : " + findMax(data));
        System.out.println("Sum     : " + sum(data));
        System.out.printf( "Average : %.2f%n", average(data));
        System.out.println("Reversed: " + Arrays.toString(reverse(data)));
        System.out.println("Sorted? : " + isSorted(data));

        System.out.println("\\n=== Linear Search ===");
        int[] targets = {62, 100, 5};
        for (int t : targets) {
            int idx = linearSearch(data, t);
            System.out.printf("Search %3d → %s%n", t, idx >= 0 ? "index " + idx : "not found");
        }

        // Frequency count
        System.out.println("\\n=== Frequency Count ===");
        int[] nums = {1,2,3,1,2,1,3,4,5,3,3};
        int[] freq = new int[6];
        for (int n : nums) freq[n]++;
        for (int i = 1; i <= 5; i++) System.out.printf("  %d appears %d time(s)%n", i, freq[i]);
    }
}
""" : """
import java.util.Arrays;

public class ArrayOperationsAdvanced {

    // Merge two sorted arrays
    static int[] mergeSorted(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int i=0, j=0, k=0;
        while (i<a.length && j<b.length) res[k++] = (a[i]<=b[j]) ? a[i++] : b[j++];
        while (i<a.length) res[k++] = a[i++];
        while (j<b.length) res[k++] = b[j++];
        return res;
    }

    // Remove duplicates (preserving order)
    static int[] removeDuplicates(int[] arr) {
        boolean[] seen = new boolean[1001];
        int count = 0;
        for (int n : arr) if (!seen[n]) { seen[n] = true; count++; }
        int[] res = new int[count]; int idx = 0;
        for (int n : arr) if (seen[n]) { seen[n] = false; res[idx++] = n; }
        return res;
    }

    // Second largest element
    static int secondLargest(int[] arr) {
        int first = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
        for (int n : arr) {
            if (n > first)        { second = first; first = n; }
            else if (n > second)  { second = n; }
        }
        return second;
    }

    // Rotate array by k positions
    static int[] rotateRight(int[] arr, int k) {
        int n = arr.length; k %= n;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) res[(i + k) % n] = arr[i];
        return res;
    }

    public static void main(String[] args) {
        int[] a = {1,3,5,7,9}, b = {2,4,6,8,10};
        System.out.println("Merge sorted:");
        System.out.println("  " + Arrays.toString(a) + " + " + Arrays.toString(b));
        System.out.println("  = " + Arrays.toString(mergeSorted(a, b)));

        int[] withDups = {3,1,4,1,5,9,2,6,5,3,5};
        System.out.println("\\nRemove duplicates:");
        System.out.println("  Before: " + Arrays.toString(withDups));
        System.out.println("  After : " + Arrays.toString(removeDuplicates(withDups)));

        int[] data = {12, 35, 1, 10, 34, 1};
        System.out.println("\\nSecond largest in " + Arrays.toString(data) + " = " + secondLargest(data));

        int[] arr = {1,2,3,4,5,6,7};
        System.out.println("\\nRotate " + Arrays.toString(arr) + " by 3:");
        System.out.println("  → " + Arrays.toString(rotateRight(arr, 3)));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "arraylist" -> idx == 1 ? """
import java.util.*;

public class ArrayListBasic {
    public static void main(String[] args) {
        // Create and populate
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        fruits.add("Elderberry");
        System.out.println("List: " + fruits);
        System.out.println("Size: " + fruits.size());

        // Access
        System.out.println("\\n=== Access ===");
        System.out.println("First : " + fruits.get(0));
        System.out.println("Last  : " + fruits.get(fruits.size()-1));
        System.out.println("Index of 'Cherry': " + fruits.indexOf("Cherry"));
        System.out.println("Contains 'Grape': " + fruits.contains("Grape"));

        // Modify
        System.out.println("\\n=== Modify ===");
        fruits.set(1, "Blueberry");
        fruits.add(2, "Coconut");
        System.out.println("After set+insert: " + fruits);
        fruits.remove("Date");
        System.out.println("After remove 'Date': " + fruits);
        fruits.remove(0);
        System.out.println("After remove index 0: " + fruits);

        // Iteration
        System.out.println("\\n=== Iteration ===");
        for (String f : fruits) System.out.println("  " + f + " (" + f.length() + " chars)");

        // Sorting
        System.out.println("\\nBefore sort: " + fruits);
        Collections.sort(fruits);
        System.out.println("After sort : " + fruits);
        Collections.sort(fruits, Comparator.comparingInt(String::length));
        System.out.println("By length  : " + fruits);

        // Shopping cart simulation
        System.out.println("\\n=== Shopping Cart ===");
        ArrayList<String> cart = new ArrayList<>();
        cart.add("Laptop $999");
        cart.add("Mouse $29");
        cart.add("Keyboard $79");
        System.out.println("Cart: " + cart);
        System.out.println("Items: " + cart.size());
        cart.remove("Mouse $29");
        System.out.println("After removing mouse: " + cart);
    }
}
""" : """
import java.util.*;
import java.util.stream.*;

public class ArrayListAdvanced {

    record Student(String name, int grade, double gpa) {}

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>(Arrays.asList(
            new Student("Alice",   10, 3.9),
            new Student("Bob",     11, 2.8),
            new Student("Carol",   10, 3.5),
            new Student("Dave",    12, 3.7),
            new Student("Eve",     11, 1.9),
            new Student("Frank",   12, 3.2)
        ));

        // Sort by GPA descending
        students.sort(Comparator.comparingDouble(Student::gpa).reversed());
        System.out.println("=== By GPA (desc) ===");
        students.forEach(s -> System.out.printf("  %-8s G%d GPA:%.1f%n", s.name(), s.grade(), s.gpa()));

        // Filter honor students (GPA >= 3.5)
        List<Student> honor = students.stream()
            .filter(s -> s.gpa() >= 3.5)
            .collect(Collectors.toList());
        System.out.println("\\nHonor students (GPA>=3.5): " + honor.stream().map(Student::name).toList());

        // Group by grade
        System.out.println("\\n=== By Grade ===");
        Map<Integer, List<Student>> byGrade = students.stream()
            .collect(Collectors.groupingBy(Student::grade));
        byGrade.forEach((g, list) ->
            System.out.println("  Grade " + g + ": " + list.stream().map(Student::name).toList()));

        // Average GPA per grade
        System.out.println("\\n=== Average GPA per Grade ===");
        byGrade.forEach((g, list) -> {
            double avg = list.stream().mapToDouble(Student::gpa).average().orElse(0);
            System.out.printf("  Grade %d: %.2f%n", g, avg);
        });

        // Remove low performers
        students.removeIf(s -> s.gpa() < 2.0);
        System.out.println("\\nAfter removing GPA<2.0: " + students.stream().map(Student::name).toList());
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "methods-with-parameters" -> idx == 1 ? """
public class MethodsWithParametersBasic {

    // Greet with a name
    static void greet(String name) {
        System.out.println("Hello, " + name + "! Welcome.");
    }

    // Add two integers
    static int add(int a, int b) {
        return a + b;
    }

    // Check if a number is even
    static boolean isEven(int n) {
        return n % 2 == 0;
    }

    // Print a line of repeated character
    static void printLine(char ch, int count) {
        for (int i = 0; i < count; i++) System.out.print(ch);
        System.out.println();
    }

    // Calculate circle area
    static double circleArea(double radius) {
        return Math.PI * radius * radius;
    }

    // Convert km to miles
    static double kmToMiles(double km) {
        return km * 0.621371;
    }

    // Check if a year is leap year
    static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static void main(String[] args) {
        greet("Alice");
        greet("Bob");

        System.out.println("\\n=== Calculations ===");
        System.out.println("add(5, 3)   = " + add(5, 3));
        System.out.println("add(100, 200) = " + add(100, 200));

        System.out.println("\\n=== Even/Odd ===");
        for (int n : new int[]{1,2,3,4,10,15})
            System.out.printf("%3d is %s%n", n, isEven(n) ? "even" : "odd");

        System.out.println("\\n=== Lines ===");
        printLine('=', 30);
        printLine('-', 20);
        printLine('*', 10);

        System.out.println("\\n=== Circle Areas ===");
        for (double r : new double[]{1, 5, 10, 3.14})
            System.out.printf("radius=%.2f → area=%.4f%n", r, circleArea(r));

        System.out.println("\\n=== Leap Years ===");
        for (int y : new int[]{2000,1900,2024,2023})
            System.out.printf("%d: %s%n", y, isLeapYear(y) ? "leap" : "not leap");
    }
}
""" : """
public class MethodsWithParametersAdvanced {

    // Password strength checker
    static String checkPassword(String pw) {
        if (pw.length() < 8)                           return "Too short";
        boolean hasUpper  = pw.chars().anyMatch(Character::isUpperCase);
        boolean hasLower  = pw.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit  = pw.chars().anyMatch(Character::isDigit);
        boolean hasSymbol = pw.chars().anyMatch(c -> "!@#$%^&*".indexOf(c) >= 0);
        int score = (hasUpper?1:0) + (hasLower?1:0) + (hasDigit?1:0) + (hasSymbol?1:0);
        return switch (score) {
            case 4 -> "Strong";
            case 3 -> "Medium";
            case 2 -> "Weak";
            default -> "Very Weak";
        };
    }

    // Format currency with locale
    static String formatCurrency(double amount, String symbol) {
        return String.format("%s%,.2f", symbol, amount);
    }

    // Calculate distance between two points
    static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

    // Generate initials from full name
    static String initials(String fullName) {
        String[] parts = fullName.trim().split("\\\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) if (!p.isEmpty()) sb.append(Character.toUpperCase(p.charAt(0))).append(".");
        return sb.toString();
    }

    // Count vowels in a string
    static int countVowels(String s) {
        int count = 0;
        for (char c : s.toLowerCase().toCharArray())
            if ("aeiou".indexOf(c) >= 0) count++;
        return count;
    }

    public static void main(String[] args) {
        System.out.println("=== Password Strength ===");
        String[] passwords = {"abc", "password", "Pass1", "P@ss1word", "Tr0ub4dor&3"};
        for (String p : passwords)
            System.out.printf("%-15s → %s%n", p, checkPassword(p));

        System.out.println("\\n=== Currency Formatting ===");
        System.out.println(formatCurrency(1234567.89, "$"));
        System.out.println(formatCurrency(9876543.21, "€"));

        System.out.println("\\n=== Point Distances ===");
        System.out.printf("(0,0)→(3,4): %.4f%n", distance(0,0,3,4));
        System.out.printf("(1,1)→(4,5): %.4f%n", distance(1,1,4,5));

        System.out.println("\\n=== Initials ===");
        String[] names = {"John Doe", "Mary Jane Watson", "Leonardo da Vinci"};
        for (String n : names) System.out.printf("%-25s → %s%n", n, initials(n));

        System.out.println("\\n=== Vowel Count ===");
        String[] words = {"Hello", "Programming", "Java", "aeiou", "rhythm"};
        for (String w : words) System.out.printf("%-15s → %d vowels%n", w, countVowels(w));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "methods-return-value" -> idx == 1 ? """
public class MethodsReturnValueBasic {

    static int square(int n)              { return n * n; }
    static double cube(double n)          { return n * n * n; }
    static boolean isPositive(double n)   { return n > 0; }
    static String repeat(String s, int n) { return s.repeat(n); }
    static int absolute(int n)            { return n < 0 ? -n : n; }
    static double celsiusToFahrenheit(double c) { return c * 9.0 / 5.0 + 32; }

    static String classify(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    static boolean isPalindrome(String s) {
        String clean = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        return clean.equals(new StringBuilder(clean).reverse().toString());
    }

    public static void main(String[] args) {
        System.out.println("=== Return Value Examples ===");
        for (int i : new int[]{1,2,3,4,5,10})
            System.out.printf("square(%2d) = %d%n", i, square(i));

        System.out.println("\\n=== Grade Classifier ===");
        for (int s : new int[]{95,85,75,65,55})
            System.out.printf("Score %d → Grade %s%n", s, classify(s));

        System.out.println("\\n=== Factorials ===");
        for (int i = 0; i <= 10; i++) System.out.printf("%2d! = %d%n", i, factorial(i));

        System.out.println("\\n=== Palindrome Checker ===");
        String[] words = {"racecar","hello","A man a plan a canal Panama","level","Java"};
        for (String w : words) System.out.printf("%-40s → %b%n", w, isPalindrome(w));

        System.out.println("\\n=== Temperature Conversion ===");
        for (double c : new double[]{0,-40,20,37,100})
            System.out.printf("%.1f°C = %.2f°F%n", c, celsiusToFahrenheit(c));
    }
}
""" : """
import java.util.*;

public class MethodsReturnValueAdvanced {

    // Returns an array
    static int[] fibonacciSequence(int count) {
        int[] fib = new int[count];
        fib[0] = 0; if (count > 1) fib[1] = 1;
        for (int i = 2; i < count; i++) fib[i] = fib[i-1] + fib[i-2];
        return fib;
    }

    // Returns a String[]
    static String[] splitIntoWords(String sentence) {
        return sentence.trim().split("\\\\s+");
    }

    // Returns a Map (histogram)
    static Map<Character,Integer> charFrequency(String s) {
        Map<Character,Integer> map = new TreeMap<>();
        for (char c : s.toCharArray()) map.merge(c, 1, Integer::sum);
        return map;
    }

    // Chain of return values
    static double circleArea(double r)       { return Math.PI * r * r; }
    static double cylinderVolume(double r, double h) { return circleArea(r) * h; }
    static double cylinderSurface(double r, double h) {
        return 2 * circleArea(r) + 2 * Math.PI * r * h;
    }

    static int romanToInt(String s) {
        Map<Character,Integer> map = Map.of('I',1,'V',5,'X',10,'L',50,'C',100,'D',500,'M',1000);
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int val = map.get(s.charAt(i));
            int nxt = i+1 < s.length() ? map.get(s.charAt(i+1)) : 0;
            res += val < nxt ? -val : val;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("=== Fibonacci ===");
        System.out.println(Arrays.toString(fibonacciSequence(15)));

        System.out.println("\\n=== Cylinder Calculator ===");
        double r=5, h=10;
        System.out.printf("r=%.1f h=%.1f%n", r, h);
        System.out.printf("Volume : %.2f%n", cylinderVolume(r, h));
        System.out.printf("Surface: %.2f%n", cylinderSurface(r, h));

        System.out.println("\\n=== Char Frequency ===");
        charFrequency("hello world").forEach((c,n) -> System.out.printf("'%c': %d%n", c, n));

        System.out.println("\\n=== Roman to Int ===");
        String[] romans = {"III","IV","IX","LVIII","MCMXCIV","MMXXIV"};
        for (String r2 : romans) System.out.printf("%-10s = %d%n", r2, romanToInt(r2));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "method-overloading" -> idx == 1 ? """
public class MethodOverloadingBasic {

    // Same name — different parameter types
    static void print(int n)     { System.out.println("int: " + n); }
    static void print(double d)  { System.out.println("double: " + d); }
    static void print(String s)  { System.out.println("String: \"" + s + "\""); }
    static void print(boolean b) { System.out.println("boolean: " + b); }

    // Same name — different parameter count
    static int add(int a, int b)           { return a + b; }
    static int add(int a, int b, int c)    { return a + b + c; }
    static double add(double a, double b)  { return a + b; }
    static String add(String a, String b)  { return a + b; }

    // Area calculations — overloaded
    static double area(double side)                { return side * side; }          // square
    static double area(double width, double height) { return width * height; }      // rectangle
    static double area(double base, double height, boolean isTriangle) {
        return isTriangle ? 0.5 * base * height : base * height;
    }

    public static void main(String[] args) {
        System.out.println("=== Overloaded print() ===");
        print(42);
        print(3.14);
        print("Hello");
        print(true);

        System.out.println("\\n=== Overloaded add() ===");
        System.out.println("add(3, 4)        = " + add(3, 4));
        System.out.println("add(1, 2, 3)     = " + add(1, 2, 3));
        System.out.println("add(1.5, 2.5)    = " + add(1.5, 2.5));
        System.out.println("add(\"Hi\",\" World\")= " + add("Hi"," World"));

        System.out.println("\\n=== Overloaded area() ===");
        System.out.printf("Square (5)           = %.2f%n", area(5.0));
        System.out.printf("Rectangle (4×6)      = %.2f%n", area(4.0, 6.0));
        System.out.printf("Triangle (b=6, h=4)  = %.2f%n", area(6.0, 4.0, true));
    }
}
""" : """
public class MethodOverloadingAdvanced {

    // Logger: overloaded for different log types
    static void log(String message)               { System.out.println("[INFO]  " + message); }
    static void log(String message, String level) { System.out.println("["+level+"] " + message); }
    static void log(String message, Exception e)  { System.out.println("[ERROR] " + message + ": " + e.getMessage()); }
    static void log(int code, String message)     { System.out.println("[CODE " + code + "] " + message); }

    // Format: different types to String
    static String format(int n)             { return String.format("%,d", n); }
    static String format(double d)          { return String.format("%.4f", d); }
    static String format(double d, int decimals) { return String.format("%." + decimals + "f", d); }
    static String format(String s)          { return "\"" + s + "\""; }
    static String format(boolean b)         { return b ? "YES" : "NO"; }

    // Find: max of different arities
    static int    max(int a, int b)               { return Math.max(a, b); }
    static int    max(int a, int b, int c)        { return Math.max(a, Math.max(b,c)); }
    static double max(double... nums)             { double m=nums[0]; for(double n:nums) if(n>m) m=n; return m; }

    public static void main(String[] args) {
        System.out.println("=== Overloaded Logger ===");
        log("Application started");
        log("User logged in", "AUDIT");
        log("File not found", new RuntimeException("NoSuchFileException"));
        log(404, "Page not found");

        System.out.println("\\n=== Overloaded format() ===");
        System.out.println(format(1234567));
        System.out.println(format(3.14159265));
        System.out.println(format(3.14159265, 2));
        System.out.println(format("hello"));
        System.out.println(format(true));

        System.out.println("\\n=== Overloaded max() ===");
        System.out.println("max(3,7)     = " + max(3,7));
        System.out.println("max(3,7,5)   = " + max(3,7,5));
        System.out.println("max(1.5,9.9,3.3,7.1) = " + max(1.5,9.9,3.3,7.1));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "factorial-recursion" -> idx == 1 ? """
public class FactorialRecursionBasic {

    // Recursive factorial
    static long factorial(int n) {
        if (n < 0)  throw new IllegalArgumentException("Negative input: " + n);
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    // Iterative factorial (for comparison)
    static long factorialIterative(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    // Fibonacci (recursive — educational, not efficient)
    static long fib(int n) {
        if (n <= 1) return n;
        return fib(n-1) + fib(n-2);
    }

    // Fibonacci with memoization
    static long[] memo = new long[100];
    static long fibMemo(int n) {
        if (n <= 1) return n;
        if (memo[n] != 0) return memo[n];
        return memo[n] = fibMemo(n-1) + fibMemo(n-2);
    }

    // Sum of digits recursively
    static int digitSum(int n) {
        if (n < 10) return n;
        return n % 10 + digitSum(n / 10);
    }

    public static void main(String[] args) {
        System.out.println("=== Factorial ===");
        for (int i = 0; i <= 15; i++)
            System.out.printf("%2d! = %,d%n", i, factorial(i));

        System.out.println("\\n=== Fibonacci (first 20) ===");
        for (int i = 0; i < 20; i++) System.out.printf("F(%2d) = %d%n", i, fibMemo(i));

        System.out.println("\\n=== Digit Sum (recursive) ===");
        int[] nums = {0, 5, 123, 9999, 999999};
        for (int n : nums) System.out.printf("digitSum(%6d) = %d%n", n, digitSum(n));

        System.out.println("\\n=== Recursive vs Iterative (same result?) ===");
        for (int i = 0; i <= 5; i++)
            System.out.printf("%d! recursive=%d iterative=%d match=%b%n",
                i, factorial(i), factorialIterative(i), factorial(i)==factorialIterative(i));
    }
}
""" : """
public class FactorialRecursionAdvanced {

    // Tower of Hanoi
    static int hanoiMoves = 0;
    static void hanoi(int n, char from, char to, char aux) {
        if (n == 1) { System.out.printf("  Move disk 1 from %c → %c%n", from, to); hanoiMoves++; return; }
        hanoi(n-1, from, aux, to);
        System.out.printf("  Move disk %d from %c → %c%n", n, from, to); hanoiMoves++;
        hanoi(n-1, aux, to, from);
    }

    // Power set (all subsets) using recursion
    static void printSubsets(int[] set, int idx, java.util.List<Integer> current) {
        System.out.println(current);
        for (int i = idx; i < set.length; i++) {
            current.add(set[i]);
            printSubsets(set, i+1, current);
            current.remove(current.size()-1);
        }
    }

    // GCD recursively
    static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }
    static int lcm(int a, int b) { return a / gcd(a,b) * b; }

    // Recursive binary search
    static int binarySearch(int[] arr, int target, int lo, int hi) {
        if (lo > hi) return -1;
        int mid = (lo+hi)/2;
        if (arr[mid] == target) return mid;
        return arr[mid] > target
            ? binarySearch(arr, target, lo, mid-1)
            : binarySearch(arr, target, mid+1, hi);
    }

    public static void main(String[] args) {
        System.out.println("=== Tower of Hanoi (3 disks) ===");
        hanoi(3, 'A', 'C', 'B');
        System.out.println("Moves: " + hanoiMoves);

        System.out.println("\\n=== Power Set of {1,2,3} ===");
        printSubsets(new int[]{1,2,3}, 0, new java.util.ArrayList<>());

        System.out.println("\\n=== GCD & LCM ===");
        int[][] pairs = {{48,18},{100,75},{7,13},{252,105}};
        for (int[] p : pairs)
            System.out.printf("GCD(%d,%d)=%d  LCM=%d%n", p[0],p[1], gcd(p[0],p[1]), lcm(p[0],p[1]));

        System.out.println("\\n=== Recursive Binary Search ===");
        int[] sorted = {3,7,12,18,25,33,47,58,64,72};
        for (int t : new int[]{25,64,1,72})
            System.out.printf("Search %d → index %d%n", t, binarySearch(sorted,t,0,sorted.length-1));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "math-abs-pow-sqrt" -> idx == 1 ? """
public class MathAbsPowSqrtBasic {
    public static void main(String[] args) {
        System.out.println("=== Math.abs() ===");
        System.out.println("abs(-15)   = " + Math.abs(-15));
        System.out.println("abs(7)     = " + Math.abs(7));
        System.out.println("abs(-3.14) = " + Math.abs(-3.14));
        System.out.println("abs(0)     = " + Math.abs(0));

        System.out.println("\\n=== Math.pow() ===");
        System.out.println("pow(2,10) = " + (int)Math.pow(2,10));
        System.out.println("pow(3,3)  = " + (int)Math.pow(3,3));
        System.out.println("pow(5,0)  = " + (int)Math.pow(5,0));
        System.out.printf( "pow(2,0.5)= %.6f (= sqrt(2))%n", Math.pow(2,0.5));

        System.out.println("\\n=== Math.sqrt() ===");
        double[] nums = {1, 4, 9, 16, 25, 2, 3};
        for (double n : nums) System.out.printf("sqrt(%.0f) = %.6f%n", n, Math.sqrt(n));

        // Practical: Pythagorean theorem
        System.out.println("\\n=== Pythagorean Theorem (c = sqrt(a²+b²)) ===");
        int[][] sides = {{3,4},{5,12},{8,15},{7,24}};
        for (int[] s : sides) {
            double c = Math.sqrt(Math.pow(s[0],2) + Math.pow(s[1],2));
            System.out.printf("a=%2d b=%2d → c=%.4f%n", s[0], s[1], c);
        }

        // Distance between two points
        System.out.println("\\n=== Distance Formula ===");
        double x1=1,y1=2, x2=4,y2=6;
        double dist = Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
        System.out.printf("(%g,%g) → (%g,%g) = %.4f%n", x1,y1,x2,y2,dist);
    }
}
""" : """
public class MathAbsPowSqrtAdvanced {

    // Mandelbrot set membership
    static boolean inMandelbrot(double cr, double ci, int maxIter) {
        double zr=0, zi=0;
        for (int i=0; i<maxIter; i++) {
            double nzr = zr*zr - zi*zi + cr;
            double nzi = 2*zr*zi + ci;
            zr=nzr; zi=nzi;
            if (Math.sqrt(zr*zr + zi*zi) > 2) return false;
        }
        return true;
    }

    // Newton-Raphson square root
    static double sqrtNewton(double n) {
        if (n < 0) return Double.NaN;
        double x = n;
        while (Math.abs(x*x - n) > 1e-10) x = (x + n/x) / 2.0;
        return x;
    }

    // Prime factorization using sqrt
    static java.util.List<Integer> primeFactors(int n) {
        java.util.List<Integer> factors = new java.util.ArrayList<>();
        for (int i=2; i <= Math.sqrt(n); i++)
            while (n % i == 0) { factors.add(i); n /= i; }
        if (n > 1) factors.add(n);
        return factors;
    }

    // Compound interest with Math.pow
    static double futureValue(double pv, double rate, int periods) {
        return pv * Math.pow(1 + rate, periods);
    }

    public static void main(String[] args) {
        System.out.println("=== Newton-Raphson sqrt ===");
        for (double n : new double[]{2, 3, 9, 144, 1000})
            System.out.printf("sqrt(%.0f) = %.10f (Math: %.10f)%n", n, sqrtNewton(n), Math.sqrt(n));

        System.out.println("\\n=== Prime Factorization ===");
        for (int n : new int[]{12, 60, 100, 360, 1024})
            System.out.printf("%4d = %s%n", n, primeFactors(n));

        System.out.println("\\n=== Investment Future Value ===");
        System.out.printf("$10,000 at 7%% for 10yr = $%,.2f%n", futureValue(10000,0.07,10));
        System.out.printf("$50,000 at 5%% for 20yr = $%,.2f%n", futureValue(50000,0.05,20));

        System.out.println("\\n=== Mandelbrot Membership ===");
        double[][] points = {{0,0},{-1,0},{0.5,0.5},{2,2},{-0.5,0.5}};
        for (double[] p : points)
            System.out.printf("(%.1f+%.1fi) → %s%n", p[0],p[1], inMandelbrot(p[0],p[1],100)?"IN":"OUT");
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "string-valueof-format" -> idx == 1 ? """
public class StringValueofFormatBasic {
    public static void main(String[] args) {
        System.out.println("=== String.valueOf() ===");
        System.out.println(String.valueOf(42));
        System.out.println(String.valueOf(3.14));
        System.out.println(String.valueOf(true));
        System.out.println(String.valueOf('A'));
        char[] chars = {'J','a','v','a'};
        System.out.println(String.valueOf(chars));

        System.out.println("\\n=== String.format() ===");
        String name  = "Alice";
        int    age   = 25;
        double gpa   = 3.857;
        System.out.println(String.format("Name: %s, Age: %d, GPA: %.2f", name, age, gpa));
        System.out.println(String.format("Padded: '%10s'", "Java"));
        System.out.println(String.format("Padded: '%-10s'", "Java"));
        System.out.println(String.format("Leading zeros: %05d", 42));
        System.out.println(String.format("Hex: %X", 255));
        System.out.println(String.format("Scientific: %e", 123456.789));

        // Receipt formatting
        System.out.println("\\n=== Receipt ===");
        System.out.println(String.format("%-20s %5s %8s", "Item", "Qty", "Price"));
        System.out.println("-".repeat(35));
        Object[][] items = {{"Laptop",1,999.99},{"Mouse",2,24.99},{"Cable",3,9.99}};
        double total = 0;
        for (Object[] item : items) {
            double price = (Double)item[2] * (Integer)item[1];
            total += price;
            System.out.println(String.format("%-20s %5d %8.2f", item[0], item[1], price));
        }
        System.out.println("-".repeat(35));
        System.out.println(String.format("%-20s %5s %8.2f", "TOTAL","", total));
    }
}
""" : """
public class StringValueofFormatAdvanced {

    static String progressBar(double percent, int width) {
        int filled = (int)(percent / 100 * width);
        return "[" + "#".repeat(filled) + "-".repeat(width-filled) + "]" +
               String.format(" %5.1f%%", percent);
    }

    static String tableRow(Object... cols) {
        StringBuilder sb = new StringBuilder("|");
        for (Object col : cols) sb.append(String.format(" %-14s|", col));
        return sb.toString();
    }

    static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024*1024) return String.format("%.1f KB", bytes/1024.0);
        if (bytes < 1024*1024*1024) return String.format("%.1f MB", bytes/(1024.0*1024));
        return String.format("%.2f GB", bytes/(1024.0*1024*1024));
    }

    static String formatDuration(long seconds) {
        long h = seconds/3600, m = (seconds%3600)/60, s = seconds%60;
        if (h > 0) return String.format("%d:%02d:%02d", h, m, s);
        return String.format("%d:%02d", m, s);
    }

    public static void main(String[] args) {
        System.out.println("=== Progress Bars ===");
        for (double p : new double[]{0, 25, 50, 75, 100})
            System.out.println(progressBar(p, 30));

        System.out.println("\\n=== Table ===");
        String sep = "+" + "-".repeat(16) + "+" + "-".repeat(16) + "+" + "-".repeat(16) + "+";
        System.out.println(sep);
        System.out.println(tableRow("Name","Score","Grade"));
        System.out.println(sep);
        Object[][] rows = {{"Alice",95,"A"},{"Bob",82,"B"},{"Carol",74,"C"}};
        for (Object[] r : rows) { System.out.println(tableRow(r)); System.out.println(sep); }

        System.out.println("\\n=== Byte Formatter ===");
        for (long b : new long[]{512, 2048, 1500000, 2_147_483_648L})
            System.out.printf("%15d → %s%n", b, formatBytes(b));

        System.out.println("\\n=== Duration Formatter ===");
        for (long s : new long[]{45, 90, 3661, 7384})
            System.out.printf("%6d sec → %s%n", s, formatDuration(s));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "string-join-split" -> idx == 1 ? """
import java.util.*;

public class StringJoinSplitBasic {
    public static void main(String[] args) {
        // String.join()
        System.out.println("=== String.join() ===");
        System.out.println(String.join(", ",    "Alice","Bob","Carol"));
        System.out.println(String.join(" | ",   "Java","Python","C++"));
        System.out.println(String.join("-",     "2024","03","15"));
        System.out.println(String.join("/",     "home","user","documents","file.txt"));

        List<String> fruits = Arrays.asList("Apple","Banana","Cherry");
        System.out.println(String.join(", ", fruits));

        // String.split()
        System.out.println("\\n=== String.split() ===");
        String csv   = "Alice,25,Engineer,New York";
        String[] row = csv.split(",");
        System.out.println("Name   : " + row[0]);
        System.out.println("Age    : " + row[1]);
        System.out.println("Job    : " + row[2]);
        System.out.println("City   : " + row[3]);

        String path  = "/home/user/documents/report.pdf";
        String[] parts = path.split("/");
        System.out.println("\\nPath parts:");
        for (int i=1; i<parts.length; i++) System.out.println("  " + parts[i]);
        System.out.println("Filename: " + parts[parts.length-1]);

        // Split by multiple delimiters
        String messy = "one two,three;four|five";
        String[] words = messy.split("[,;| ]+");
        System.out.println("\\nSplit by [,;| ]: " + Arrays.toString(words));

        // Round-trip: split then join
        String sentence = "The quick brown fox jumps";
        String[] ws = sentence.split(" ");
        String joined = String.join("-", ws);
        System.out.println("\\nOriginal: " + sentence);
        System.out.println("Hyphen  : " + joined);
    }
}
""" : """
import java.util.*;

public class StringJoinSplitAdvanced {

    // Parse CSV row (handles quoted fields)
    static String[] parseCsvRow(String row) {
        List<String> fields = new ArrayList<>();
        boolean inQuote = false;
        StringBuilder current = new StringBuilder();
        for (char c : row.toCharArray()) {
            if (c == '"') { inQuote = !inQuote; }
            else if (c == ',' && !inQuote) { fields.add(current.toString().trim()); current.setLength(0); }
            else { current.append(c); }
        }
        fields.add(current.toString().trim());
        return fields.toArray(new String[0]);
    }

    // Word frequency from text
    static Map<String,Integer> wordFrequency(String text) {
        Map<String,Integer> freq = new TreeMap<>();
        for (String w : text.toLowerCase().split("[^a-zA-Z]+"))
            if (!w.isBlank()) freq.merge(w, 1, Integer::sum);
        return freq;
    }

    // Build query string
    static String buildQueryString(Map<String,String> params) {
        List<String> parts = new ArrayList<>();
        params.forEach((k,v) -> parts.add(k + "=" + v));
        return "?" + String.join("&", parts);
    }

    public static void main(String[] args) {
        System.out.println("=== CSV Parser ===");
        String[] csvLines = {
            "Alice,30,\"New York, NY\",Engineer",
            "Bob,25,London,Designer",
            "Carol,35,\"Los Angeles, CA\",Manager"
        };
        for (String line : csvLines) {
            String[] fields = parseCsvRow(line);
            System.out.printf("Name:%-8s Age:%-4s City:%-18s Job:%s%n",
                fields[0], fields[1], fields[2], fields[3]);
        }

        System.out.println("\\n=== Word Frequency ===");
        String text = "to be or not to be that is the question to be";
        wordFrequency(text).forEach((w,c) -> System.out.printf("  %-12s %d%n", w, c));

        System.out.println("\\n=== URL Builder ===");
        Map<String,String> params = new LinkedHashMap<>();
        params.put("query", "java+programming");
        params.put("page", "1");
        params.put("sort", "relevance");
        System.out.println("https://example.com/search" + buildQueryString(params));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "string-to-chararray" -> idx == 1 ? """
import java.util.Arrays;

public class StringToCharArrayBasic {
    public static void main(String[] args) {
        // toCharArray()
        String s = "Hello Java";
        char[] chars = s.toCharArray();
        System.out.println("=== toCharArray() ===");
        System.out.println("String : " + s);
        System.out.println("Chars  : " + Arrays.toString(chars));
        System.out.println("Length : " + chars.length);

        // Iterate character by character
        System.out.println("\\nCharacter by character:");
        for (int i=0; i<chars.length; i++)
            System.out.printf("  [%2d] '%c' (ASCII: %3d)%n", i, chars[i], (int)chars[i]);

        // Palindrome check using char array
        System.out.println("\\n=== Palindrome Checker ===");
        String[] words = {"racecar","level","hello","noon","world","madam"};
        for (String w : words) {
            char[] arr = w.toCharArray();
            boolean isPalin = true;
            for (int i=0, j=arr.length-1; i<j; i++,j--)
                if (arr[i] != arr[j]) { isPalin = false; break; }
            System.out.printf("%-10s → %s%n", w, isPalin ? "palindrome" : "not palindrome");
        }

        // Count uppercase / lowercase / digits
        System.out.println("\\n=== Character Analysis ===");
        String test = "Hello World 2024!";
        int upper=0, lower=0, digits=0, spaces=0, other=0;
        for (char c : test.toCharArray()) {
            if      (Character.isUpperCase(c)) upper++;
            else if (Character.isLowerCase(c)) lower++;
            else if (Character.isDigit(c))     digits++;
            else if (c == ' ')                 spaces++;
            else                               other++;
        }
        System.out.printf("Input: \"%s\"%n", test);
        System.out.printf("Upper: %d | Lower: %d | Digits: %d | Spaces: %d | Other: %d%n",
            upper, lower, digits, spaces, other);
    }
}
""" : """
import java.util.*;

public class StringToCharArrayAdvanced {

    // Caesar cipher using char array
    static String encrypt(String text, int shift) {
        char[] arr = text.toCharArray();
        for (int i=0; i<arr.length; i++) {
            char c = arr[i];
            if (Character.isUpperCase(c)) arr[i] = (char)('A' + (c-'A'+shift)%26);
            else if (Character.isLowerCase(c)) arr[i] = (char)('a' + (c-'a'+shift)%26);
        }
        return new String(arr);
    }
    static String decrypt(String text, int shift) { return encrypt(text, 26-shift); }

    // Anagram check
    static boolean isAnagram(String a, String b) {
        char[] ca = a.toLowerCase().replaceAll("[^a-z]","").toCharArray();
        char[] cb = b.toLowerCase().replaceAll("[^a-z]","").toCharArray();
        Arrays.sort(ca); Arrays.sort(cb);
        return Arrays.equals(ca, cb);
    }

    // Most frequent character
    static char mostFrequent(String s) {
        int[] freq = new int[128];
        for (char c : s.toCharArray()) freq[c]++;
        char best = s.charAt(0);
        for (char c : s.toCharArray()) if (freq[c] > freq[best]) best = c;
        return best;
    }

    // Reverse words
    static String reverseWords(String sentence) {
        String[] words = sentence.split(" ");
        for (int i=0; i<words.length; i++) {
            char[] arr = words[i].toCharArray();
            for (int l=0, r=arr.length-1; l<r; l++,r--) { char t=arr[l]; arr[l]=arr[r]; arr[r]=t; }
            words[i] = new String(arr);
        }
        return String.join(" ", words);
    }

    public static void main(String[] args) {
        System.out.println("=== Caesar Cipher ===");
        String msg = "Hello World";
        String enc = encrypt(msg, 3);
        System.out.println("Original: " + msg);
        System.out.println("Encrypted: " + enc);
        System.out.println("Decrypted: " + decrypt(enc, 3));

        System.out.println("\\n=== Anagram Checker ===");
        String[][] pairs = {{"listen","silent"},{"hello","world"},{"Astronomer","Moon starer"},{"Java","Vaja"}};
        for (String[] p : pairs)
            System.out.printf("%-15s %-15s → %s%n", p[0], p[1], isAnagram(p[0],p[1]) ? "ANAGRAM" : "not anagram");

        System.out.println("\\n=== Most Frequent Char ===");
        String[] words = {"programming","mississippi","aabbccddee"};
        for (String w : words) System.out.printf("%-20s → '%c'%n", w, mostFrequent(w));

        System.out.println("\\n=== Reverse Each Word ===");
        System.out.println(reverseWords("Hello World Java 21"));
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "oop-introduction" -> idx == 1 ? """
/**
 * OOP Introduction: Procedural vs Object-Oriented approach.
 * Demonstrates WHY OOP is better with a real example.
 */
public class OopIntroductionBasic {

    // ─── Procedural approach (before OOP) ──────────────────────────
    static String getFullName(String first, String last) { return first + " " + last; }
    static void printEmployeeProc(String name, String dept, double salary) {
        System.out.printf("[Procedural] Employee: %-15s Dept: %-12s Salary: $%,.0f%n",
            name, dept, salary);
    }

    // ─── OOP approach: data + behavior in one unit ──────────────────
    static class Employee {
        String firstName;
        String lastName;
        String department;
        double salary;

        Employee(String firstName, String lastName, String department, double salary) {
            this.firstName  = firstName;
            this.lastName   = lastName;
            this.department = department;
            this.salary     = salary;
        }

        String getFullName()   { return firstName + " " + lastName; }
        double getAnnualPay()  { return salary * 12; }
        void   raiseSalary(double percent) { salary += salary * percent / 100; }

        void print() {
            System.out.printf("[OOP]        Employee: %-15s Dept: %-12s Salary: $%,.0f%n",
                getFullName(), department, salary);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Procedural Approach ===");
        printEmployeeProc(getFullName("Alice","Smith"), "Engineering", 5000);
        printEmployeeProc(getFullName("Bob","Jones"),   "Marketing",   4000);

        System.out.println("\\n=== OOP Approach ===");
        Employee e1 = new Employee("Alice","Smith","Engineering", 5000);
        Employee e2 = new Employee("Bob","Jones",  "Marketing",   4000);
        e1.print();
        e2.print();

        System.out.println("\\n=== OOP Advantage: Behavior with Data ===");
        e1.raiseSalary(10);
        System.out.println(e1.getFullName() + " after 10% raise: $" + e1.salary);
        System.out.printf("Annual pay: $%,.0f%n", e1.getAnnualPay());

        System.out.println("\\n=== Why OOP? ===");
        System.out.println("1. Encapsulation  — data and methods together");
        System.out.println("2. Reusability    — create many Employee objects");
        System.out.println("3. Maintainability— change Employee class once");
        System.out.println("4. Modeling       — mirrors real-world entities");
    }
}
""" : """
/**
 * OOP Advanced: comparing OOP vs procedural for a Library system.
 * Shows how OOP scales better for complex systems.
 */
import java.util.*;

public class OopIntroductionAdvanced {

    // ─── Without OOP: parallel arrays (messy, error-prone) ─────────
    static void proceduralLibrary() {
        String[] titles   = {"Java 21","Clean Code","Design Patterns"};
        String[] authors  = {"Gosling", "Martin",    "GoF"};
        boolean[] checkedOut = {false,  true,         false};

        System.out.println("=== Procedural Library (parallel arrays) ===");
        for (int i=0; i<titles.length; i++)
            System.out.printf("%-20s by %-10s → %s%n",
                titles[i], authors[i], checkedOut[i] ? "Checked Out" : "Available");
    }

    // ─── With OOP: clean, maintainable, extensible ──────────────────
    static class Book {
        private final String title, author, isbn;
        private boolean checkedOut;
        private String borrower;

        Book(String title, String author, String isbn) {
            this.title  = title; this.author = author; this.isbn = isbn;
        }

        boolean checkout(String borrowerName) {
            if (checkedOut) return false;
            checkedOut = true; borrower = borrowerName; return true;
        }

        boolean returnBook() {
            if (!checkedOut) return false;
            checkedOut = false; borrower = null; return true;
        }

        String status() { return checkedOut ? "Out (" + borrower + ")" : "Available"; }

        @Override public String toString() {
            return String.format("%-25s by %-15s [%s] → %s", title, author, isbn, status());
        }
    }

    static class Library {
        private final String name;
        private final List<Book> books = new ArrayList<>();

        Library(String name) { this.name = name; }

        void addBook(Book b)   { books.add(b); }
        Optional<Book> find(String title) {
            return books.stream().filter(b -> b.title.equalsIgnoreCase(title)).findFirst();
        }
        long availableCount() { return books.stream().filter(b -> !b.checkedOut).count(); }
        void printCatalog()   { books.forEach(b -> System.out.println("  " + b)); }
    }

    public static void main(String[] args) {
        proceduralLibrary();

        System.out.println("\\n=== OOP Library System ===");
        Library lib = new Library("City Library");
        lib.addBook(new Book("Java 21",          "James Gosling",  "ISBN-001"));
        lib.addBook(new Book("Clean Code",        "Robert Martin",  "ISBN-002"));
        lib.addBook(new Book("Design Patterns",   "GoF",            "ISBN-003"));
        lib.addBook(new Book("Effective Java",    "Joshua Bloch",   "ISBN-004"));

        lib.find("Clean Code").ifPresent(b -> System.out.println("Checkout: " + b.checkout("Alice")));
        lib.find("Java 21").ifPresent(b -> System.out.println("Checkout: " + b.checkout("Bob")));

        System.out.println("\\nCatalog:");
        lib.printCatalog();
        System.out.println("Available: " + lib.availableCount() + "/" + 4);

        lib.find("Clean Code").ifPresent(b -> { b.returnBook(); System.out.println("\\nReturned: " + b); });
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "class-blueprint" -> idx == 1 ? """
/**
 * Class as Blueprint: fields, constructors, methods.
 * Shows how a class defines the structure for all objects.
 */
public class ClassBlueprintBasic {

    // Blueprint: defines what every Car object will have
    static class Car {
        // Fields — each Car object has its own copy
        String brand;
        String model;
        int    year;
        double speed;        // current speed
        boolean isRunning;

        // Constructor — called when new Car() is used
        Car(String brand, String model, int year) {
            this.brand = brand;
            this.model = model;
            this.year  = year;
            this.speed = 0;
            this.isRunning = false;
        }

        // Methods — behaviors every Car can do
        void start() {
            if (!isRunning) { isRunning = true; System.out.println(brand + " started. Vroom!"); }
            else            { System.out.println(brand + " is already running."); }
        }

        void accelerate(double amount) {
            if (!isRunning) { System.out.println("Start the car first!"); return; }
            speed += amount;
            System.out.printf("%s accelerated → %.1f km/h%n", brand, speed);
        }

        void brake(double amount) {
            speed = Math.max(0, speed - amount);
            System.out.printf("%s braking → %.1f km/h%n", brand, speed);
        }

        void stop() {
            speed = 0; isRunning = false;
            System.out.println(brand + " stopped.");
        }

        void status() {
            System.out.printf("[%s %s %d] — %s | Speed: %.1f km/h%n",
                brand, model, year, isRunning ? "RUNNING" : "PARKED", speed);
        }
    }

    public static void main(String[] args) {
        // Create multiple instances from the same blueprint
        Car car1 = new Car("Toyota", "Camry",   2022);
        Car car2 = new Car("Tesla",  "Model 3", 2023);
        Car car3 = new Car("BMW",    "X5",      2021);

        System.out.println("=== Created from same Car blueprint ===");
        car1.status();
        car2.status();
        car3.status();

        System.out.println("\\n=== Driving car1 ===");
        car1.start();
        car1.accelerate(30);
        car1.accelerate(20);
        car1.brake(10);
        car1.status();

        System.out.println("\\n=== Other cars unaffected ===");
        car2.status();  // car2 still parked
        car3.status();  // car3 still parked
    }
}
""" : """
/**
 * Advanced: Class with static vs instance, multiple constructors,
 * method chaining (fluent API), toString/equals.
 */
public class ClassBlueprintAdvanced {

    static class BankAccount {
        private static int nextId = 1000;      // class-level (shared)
        private final int  id;                 // instance-level
        private final String owner;
        private double balance;
        private int transactionCount;

        // Constructor 1: start with 0 balance
        BankAccount(String owner) {
            this(owner, 0.0);
        }

        // Constructor 2: start with initial deposit
        BankAccount(String owner, double initialBalance) {
            this.id      = nextId++;
            this.owner   = owner;
            this.balance = initialBalance;
        }

        // Method chaining (fluent API)
        BankAccount deposit(double amount) {
            if (amount > 0) { balance += amount; transactionCount++; }
            return this;   // return this for chaining
        }

        BankAccount withdraw(double amount) {
            if (amount > 0 && amount <= balance) { balance -= amount; transactionCount++; }
            else System.out.println("  Withdraw $" + amount + " failed.");
            return this;
        }

        void printStatement() {
            System.out.printf("Account #%d | Owner: %-10s | Balance: $%8.2f | Transactions: %d%n",
                id, owner, balance, transactionCount);
        }

        @Override public String toString() {
            return "BankAccount{id=" + id + ", owner=" + owner + ", balance=" + balance + "}";
        }

        static int totalAccounts() { return nextId - 1000; }
    }

    public static void main(String[] args) {
        System.out.println("=== Bank Account System ===");
        BankAccount alice = new BankAccount("Alice", 1000.0);
        BankAccount bob   = new BankAccount("Bob");    // starts at 0
        BankAccount carol = new BankAccount("Carol", 500.0);

        // Method chaining
        alice.deposit(500).deposit(200).withdraw(150).withdraw(50);
        bob.deposit(1000).withdraw(200).deposit(300);
        carol.deposit(1000).withdraw(2000).deposit(100);   // one withdraw fails

        System.out.println("\\n=== Statements ===");
        alice.printStatement();
        bob.printStatement();
        carol.printStatement();

        System.out.println("\\nTotal accounts opened: " + BankAccount.totalAccounts());
        System.out.println("toString: " + alice);
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "object-instance" -> idx == 1 ? """
/**
 * Object Instances: new keyword, heap memory, instance vs class members.
 */
public class ObjectInstanceBasic {

    static class Person {
        // Instance variables — each object gets its own copy
        String name;
        int    age;
        String email;

        // Class (static) variable — shared by ALL Person objects
        static int totalPeople = 0;

        Person(String name, int age, String email) {
            this.name  = name;
            this.age   = age;
            this.email = email;
            totalPeople++;    // incremented for every new Person
        }

        void greet() {
            System.out.printf("Hi! I'm %s, age %d. Reach me at %s%n", name, age, email);
        }

        boolean isAdult() { return age >= 18; }

        // Static method — belongs to class, not instance
        static void showCount() {
            System.out.println("Total people created: " + totalPeople);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Creating Object Instances ===");

        // new keyword: allocates memory on heap, calls constructor
        Person p1 = new Person("Alice", 25, "alice@example.com");
        Person p2 = new Person("Bob",   17, "bob@example.com");
        Person p3 = new Person("Carol", 30, "carol@example.com");

        System.out.println("3 objects created. Each has its OWN data:");
        p1.greet();
        p2.greet();
        p3.greet();

        // Instance methods called on each object
        System.out.println("\\n=== Instance Method Calls ===");
        System.out.println(p1.name + " is adult: " + p1.isAdult());
        System.out.println(p2.name + " is adult: " + p2.isAdult());

        // Static variable is shared
        System.out.println("\\n=== Static (Class) Variable ===");
        Person.showCount();

        // null reference
        System.out.println("\\n=== Null Reference ===");
        Person nobody = null;
        System.out.println("nobody == null: " + (nobody == null));
        System.out.println("p1     == null: " + (p1 == null));

        // Object comparison
        System.out.println("\\n=== Object Identity ===");
        Person copy = p1;  // both point to same object
        System.out.println("copy == p1: " + (copy == p1) + " (same object in memory)");
        System.out.println("p1   == p2: " + (p1   == p2) + " (different objects)");
    }
}
""" : """
/**
 * Object lifecycle, equals/hashCode, immutable vs mutable objects.
 */
import java.util.*;

public class ObjectInstanceAdvanced {

    // Mutable class (has state that can change)
    static class MutablePoint {
        int x, y;
        MutablePoint(int x, int y) { this.x=x; this.y=y; }
        void move(int dx, int dy)  { x+=dx; y+=dy; }
        @Override public String toString() { return "(" + x + "," + y + ")"; }
    }

    // Immutable class (state never changes after construction)
    static final class ImmutablePoint {
        private final int x, y;
        ImmutablePoint(int x, int y) { this.x=x; this.y=y; }
        ImmutablePoint move(int dx, int dy) { return new ImmutablePoint(x+dx, y+dy); }  // NEW object!

        @Override public boolean equals(Object o) {
            if (!(o instanceof ImmutablePoint p)) return false;
            return x==p.x && y==p.y;
        }
        @Override public int hashCode() { return Objects.hash(x,y); }
        @Override public String toString() { return "(" + x + "," + y + ")"; }
    }

    static class Student implements Comparable<Student> {
        String name; int score;
        Student(String name, int score) { this.name=name; this.score=score; }

        @Override public int compareTo(Student o) { return Integer.compare(o.score, this.score); }

        @Override public boolean equals(Object o) {
            if (!(o instanceof Student s)) return false;
            return name.equals(s.name) && score == s.score;
        }
        @Override public int hashCode() { return Objects.hash(name, score); }
        @Override public String toString() { return name + ":" + score; }
    }

    public static void main(String[] args) {
        System.out.println("=== Mutable vs Immutable ===");
        MutablePoint mp = new MutablePoint(3, 4);
        System.out.println("Mutable before: " + mp);
        mp.move(2, -1);
        System.out.println("Mutable after : " + mp + " (same object changed!)");

        ImmutablePoint ip = new ImmutablePoint(3, 4);
        ImmutablePoint ip2 = ip.move(2, -1);
        System.out.println("Immutable original: " + ip  + " (unchanged)");
        System.out.println("Immutable moved   : " + ip2 + " (new object)");

        System.out.println("\\n=== equals() and hashCode() ===");
        ImmutablePoint a = new ImmutablePoint(5, 5);
        ImmutablePoint b = new ImmutablePoint(5, 5);
        System.out.println("a==b  (reference): " + (a==b));
        System.out.println("a.equals(b): "        + a.equals(b));
        System.out.println("Same hashCode: "       + (a.hashCode()==b.hashCode()));

        Set<ImmutablePoint> set = new HashSet<>();
        set.add(a); set.add(b);
        System.out.println("In HashSet — size: " + set.size() + " (deduped by equals/hashCode)");

        System.out.println("\\n=== Sorting Objects (Comparable) ===");
        List<Student> students = Arrays.asList(
            new Student("Alice",88), new Student("Bob",95),
            new Student("Carol",72), new Student("Dave",95)
        );
        Collections.sort(students);
        students.forEach(System.out::println);
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
case "class-declaration" -> idx == 1 ? """
/**
 * class Car { } + new Car() — full introductory OOP example.
 * Shows class declaration, fields, constructors, methods, objects.
 */
public class ClassDeclarationBasic {

    // ─── Class Declaration ────────────────────────────────────────────
    static class Car {
        // Fields (attributes)
        String brand;
        String color;
        int    year;
        int    doors;

        // Constructor (called with 'new')
        Car(String brand, String color, int year, int doors) {
            this.brand = brand;
            this.color = color;
            this.year  = year;
            this.doors = doors;
        }

        // Methods (behaviors)
        void honk() {
            System.out.println(brand + ": Beep beep!");
        }

        void describe() {
            System.out.printf("🚗 %d %s %s (%d doors)%n", year, color, brand, doors);
        }

        int age() {
            return 2024 - year;
        }
    }

    // ─── Another class: Book ──────────────────────────────────────────
    static class Book {
        String title;
        String author;
        int    pages;
        double price;

        Book(String title, String author, int pages, double price) {
            this.title  = title;
            this.author = author;
            this.pages  = pages;
            this.price  = price;
        }

        void display() {
            System.out.printf("📚 \"%s\" by %s | %d pages | $%.2f%n",
                title, author, pages, price);
        }

        boolean isLongBook() { return pages > 400; }
        String  category()   { return pages <= 150 ? "Short" : pages <= 400 ? "Medium" : "Long"; }
    }

    public static void main(String[] args) {
        // Creating Car objects (instances)
        System.out.println("=== Car Objects ===");
        Car car1 = new Car("Toyota",  "White",  2020, 4);
        Car car2 = new Car("Ferrari", "Red",    2023, 2);
        Car car3 = new Car("Tesla",   "Black",  2022, 4);

        car1.describe();
        car2.describe();
        car3.describe();

        System.out.println("\\n=== Honking ===");
        car1.honk();
        car2.honk();

        System.out.println("\\n=== Ages ===");
        System.out.println(car1.brand + " is " + car1.age() + " years old");
        System.out.println(car2.brand + " is " + car2.age() + " years old");

        // Creating Book objects
        System.out.println("\\n=== Book Objects ===");
        Book[] books = {
            new Book("Clean Code",      "Martin",  431, 35.99),
            new Book("Java in a Nutshell","Evans", 988, 59.99),
            new Book("Pragmatic Programmer","Hunt",352, 44.99)
        };
        for (Book b : books) {
            b.display();
            System.out.println("   Category: " + b.category());
        }
    }
}
""" : """
/**
 * Advanced: Multiple related classes working together.
 * School system: Student, Teacher, Classroom.
 */
import java.util.*;

public class ClassDeclarationAdvanced {

    static class Student {
        private final String name;
        private final int    studentId;
        private final List<Integer> grades = new ArrayList<>();
        private static int idCounter = 1001;

        Student(String name) {
            this.name      = name;
            this.studentId = idCounter++;
        }

        void addGrade(int grade) { grades.add(grade); }

        double average() {
            return grades.isEmpty() ? 0 :
                grades.stream().mapToInt(Integer::intValue).average().orElse(0);
        }

        String letterGrade() {
            double avg = average();
            if (avg >= 90) return "A";
            if (avg >= 80) return "B";
            if (avg >= 70) return "C";
            if (avg >= 60) return "D";
            return "F";
        }

        @Override public String toString() {
            return String.format("Student[%d] %-10s avg=%.1f (%s)", studentId, name, average(), letterGrade());
        }
    }

    static class Teacher {
        final String name;
        final String subject;
        Teacher(String name, String subject) { this.name=name; this.subject=subject; }

        void grade(Student s, int... scores) {
            for (int sc : scores) s.addGrade(sc);
            System.out.printf("  %s graded %s: %s → avg %.1f%n",
                name, s.name, Arrays.toString(scores), s.average());
        }
    }

    static class Classroom {
        final String roomName;
        final Teacher teacher;
        final List<Student> students = new ArrayList<>();

        Classroom(String roomName, Teacher teacher) {
            this.roomName = roomName; this.teacher = teacher;
        }

        void enroll(Student s)  { students.add(s); }
        void printReport() {
            System.out.println("\\n=== " + roomName + " — " + teacher.subject + " ===");
            students.forEach(s -> System.out.println("  " + s));
            double classAvg = students.stream().mapToDouble(Student::average).average().orElse(0);
            System.out.printf("  Class average: %.1f%n", classAvg);
        }
    }

    public static void main(String[] args) {
        Teacher t1 = new Teacher("Mr. Smith", "Mathematics");
        Teacher t2 = new Teacher("Ms. Jones", "Science");

        Student alice = new Student("Alice");
        Student bob   = new Student("Bob");
        Student carol = new Student("Carol");

        Classroom math    = new Classroom("Room 101", t1);
        Classroom science = new Classroom("Room 202", t2);

        math.enroll(alice); math.enroll(bob); math.enroll(carol);
        science.enroll(alice); science.enroll(carol);

        System.out.println("=== Grading ===");
        t1.grade(alice, 95, 88, 92);
        t1.grade(bob,   70, 75, 68);
        t1.grade(carol, 85, 90, 87);
        t2.grade(alice, 88, 91, 94);
        t2.grade(carol, 78, 82, 80);

        math.printReport();
        science.printReport();
    }
}
""";

// ══════════════════════════════════════════════════════════════════════════════
default -> generateDefaultCode(topicName, idx);
        };
    }

    // ─── Fallback code for topics without custom template ─────────────────────
    private String generateDefaultCode(String topic, int idx) {
        String cls  = toClassName(topic) + (idx==1 ? "Basic" : "Advanced");
        String disp = toDisplayName(topic);
        String lvl  = idx==1 ? "Basic" : "Advanced";
        return """
            import java.util.*;
            import java.util.stream.*;

            /**
             * %s — %s examples in Java 21.
             */
            public class %s {

                record DataPoint(String label, int value, String group) {}

                static List<DataPoint> sampleData() {
                    return Arrays.asList(
                        new DataPoint("Alpha",   88, "A"),
                        new DataPoint("Beta",    42, "B"),
                        new DataPoint("Gamma",   95, "A"),
                        new DataPoint("Delta",   67, "C"),
                        new DataPoint("Epsilon", 73, "B"),
                        new DataPoint("Zeta",    55, "C"),
                        new DataPoint("Eta",     81, "A")
                    );
                }

                public static void main(String[] args) {
                    var data = sampleData();
                    System.out.println("=== %s — %s ===");

                    // Statistics
                    var stats = data.stream().mapToInt(DataPoint::value).summaryStatistics();
                    System.out.printf("Count: %%d | Min: %%d | Max: %%d | Avg: %%.1f%%n",
                        stats.getCount(), stats.getMin(), stats.getMax(), stats.getAverage());

                    // Group by category
                    System.out.println("\\nGroups:");
                    data.stream()
                        .collect(Collectors.groupingBy(DataPoint::group))
                        .forEach((g, list) -> System.out.println("  " + g + ": " +
                            list.stream().map(DataPoint::label).collect(Collectors.joining(", "))));

                    // Top 3
                    System.out.println("\\nTop 3:");
                    data.stream()
                        .sorted(Comparator.comparingInt(DataPoint::value).reversed())
                        .limit(3)
                        .forEach(d -> System.out.printf("  %%s: %%d%%n", d.label(), d.value()));

                    System.out.println("\\n[Done] %s completed.");
                }
            }
            """.formatted(disp, lvl, cls, disp, lvl, disp);
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────
    private String generateReadme(String topicName, int idx) {
        String display = toDisplayName(topicName);
        String level   = idx==1 ? "Basic" : "Advanced";
        String cls     = toClassName(topicName) + level;
        return """
            # %s — %s (Java 21)

            Java 21 da yozilgan **%s** mavzusidagi mashqlar (%s daraja).

            ## Run
            ```bash
            javac src/%s.java
            java -cp src %s
            ```

            ## Topic
            `%s`

            """.formatted(display, level, display, level, cls, cls, topicName);
    }

    private String toClassName(String t) {
        StringBuilder sb = new StringBuilder();
        for (String p : t.split("-")) if (!p.isEmpty()) sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
        return sb.toString();
    }

    private String toDisplayName(String t) {
        StringBuilder sb = new StringBuilder();
        for (String p : t.split("-")) { if (sb.length()>0) sb.append(" "); if (!p.isEmpty()) sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1)); }
        return sb.toString();
    }
}
