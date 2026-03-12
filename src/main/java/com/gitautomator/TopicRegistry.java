package com.gitautomator;

import com.gitautomator.models.Topic;
import java.util.Arrays;
import java.util.List;

/**
 * 41 ta mavzu × 2 repo = 82 repo → 41 kun (kuniga 2 ta).
 * Barcha mavzular Java asoslari va OOP bo'yicha.
 */
public class TopicRegistry {

    public static List<Topic> getAllTopics() {
        return Arrays.asList(

            new Topic("arithmetic-operators",
                "Arithmetic Operators",
                "Java arithmetic operators: +, -, *, /, %, with practical calculator examples",
                Arrays.asList("java", "operators", "arithmetic", "basics")),

            new Topic("comparison-operators",
                "Comparison Operators",
                "Java comparison operators: ==, !=, >, <, >=, <= with real-world comparisons",
                Arrays.asList("java", "operators", "comparison", "basics")),

            new Topic("logical-operators",
                "Logical Operators",
                "Java logical operators: &&, ||, ! — boolean logic and short-circuit evaluation",
                Arrays.asList("java", "operators", "logical", "boolean")),

            new Topic("scanner-input",
                "Scanner Input",
                "Reading user input with Scanner: nextInt, nextLine, nextDouble, input validation",
                Arrays.asList("java", "scanner", "input", "basics")),

            new Topic("if-else",
                "If-Else Condition",
                "Conditional branching with if, else if, else — grade calculator, FizzBuzz",
                Arrays.asList("java", "conditionals", "if-else", "control-flow")),

            new Topic("nested-if",
                "Nested If Conditions",
                "Nested if statements: multi-level decision trees, login validation",
                Arrays.asList("java", "nested-if", "conditionals", "control-flow")),

            new Topic("switch-case",
                "Switch-Case",
                "Switch-case and modern switch expressions (Java 14+): day names, menu systems",
                Arrays.asList("java", "switch", "control-flow", "modern-java")),

            new Topic("for-loop",
                "For Loop",
                "for loop, enhanced for-each, indexed iteration: patterns, multiplication tables",
                Arrays.asList("java", "for-loop", "loops", "iteration")),

            new Topic("while-loop",
                "While Loop",
                "while loop: number guessing game, digit sum, Collatz sequence",
                Arrays.asList("java", "while-loop", "loops", "iteration")),

            new Topic("do-while-loop",
                "Do-While Loop",
                "do-while loop: menu-driven programs, input until valid, ATM simulation",
                Arrays.asList("java", "do-while", "loops", "iteration")),

            new Topic("break-and-continue",
                "Break and Continue",
                "break and continue in loops: prime finder, skip pattern, labeled break",
                Arrays.asList("java", "break", "continue", "loops")),

            new Topic("arrays",
                "Arrays",
                "1D and 2D arrays: declaration, initialization, iteration, matrix operations",
                Arrays.asList("java", "arrays", "data-structures", "basics")),

            new Topic("array-operations",
                "Array Operations",
                "Array algorithms: min, max, sum, average, reverse, linear search",
                Arrays.asList("java", "arrays", "algorithms", "data-structures")),

            new Topic("arraylist",
                "ArrayList",
                "ArrayList: add, remove, get, contains, sort, iterate — shopping cart simulation",
                Arrays.asList("java", "arraylist", "collections", "data-structures")),

            new Topic("methods-with-parameters",
                "Methods With Parameters",
                "Methods with parameters: greet, calculate, validate — reusable utility methods",
                Arrays.asList("java", "methods", "parameters", "functions")),

            new Topic("methods-return-value",
                "Methods Return Value",
                "Methods that return values: int, String, boolean, double — converter utilities",
                Arrays.asList("java", "methods", "return", "functions")),

            new Topic("method-overloading",
                "Method Overloading",
                "Method overloading: same name, different parameters — print, add, convert",
                Arrays.asList("java", "overloading", "methods", "polymorphism")),

            new Topic("method-structure",
                "Method Structure",
                "Method anatomy: access modifier, return type, name, parameters, body",
                Arrays.asList("java", "methods", "structure", "fundamentals")),

            new Topic("static-methods",
                "Static Methods",
                "Static methods and fields: Math utilities, counter, factory methods",
                Arrays.asList("java", "static", "methods", "oop")),

            new Topic("void-methods",
                "Void Methods",
                "void methods: print patterns, draw shapes, log messages — side-effect focused",
                Arrays.asList("java", "void", "methods", "fundamentals")),

            new Topic("return-statement",
                "Return Statement",
                "return statement: early return, guard clauses, multiple return paths",
                Arrays.asList("java", "return", "methods", "control-flow")),

            new Topic("method-naming-convention",
                "Method Naming Convention",
                "camelCase naming, verb-first convention, clean code principles in Java",
                Arrays.asList("java", "naming", "clean-code", "conventions")),

            new Topic("dry-principle",
                "DRY Principle",
                "Don't Repeat Yourself: refactoring duplicate code into reusable methods",
                Arrays.asList("java", "dry", "clean-code", "refactoring")),

            new Topic("multiple-parameters",
                "Multiple Parameters",
                "Methods with multiple typed parameters: (int a, int b, String c) patterns",
                Arrays.asList("java", "parameters", "methods", "fundamentals")),

            new Topic("no-default-params",
                "No Default Parameters in Java",
                "Java has no default params — workarounds: overloading, builder, varargs",
                Arrays.asList("java", "parameters", "overloading", "design")),

            new Topic("array-as-parameter",
                "Array as Method Parameter",
                "Passing arrays to methods: sort helper, statistics calculator, matrix ops",
                Arrays.asList("java", "arrays", "parameters", "methods")),

            new Topic("parameter-mutation",
                "Parameter Mutation",
                "Pass-by-value vs pass-by-reference in Java — does changing params affect caller?",
                Arrays.asList("java", "parameters", "memory", "fundamentals")),

            new Topic("method-resolution",
                "Method Resolution",
                "How Java picks which overloaded method to call — resolution rules with examples",
                Arrays.asList("java", "overloading", "resolution", "compiler")),

            new Topic("factorial-recursion",
                "Factorial and Recursion",
                "Factorial: n! iterative and recursive, Fibonacci, recursion vs iteration",
                Arrays.asList("java", "recursion", "factorial", "algorithms")),

            new Topic("math-abs-pow-sqrt",
                "Math.abs, pow, sqrt",
                "Math.abs(), Math.pow(), Math.sqrt() — distance calculator, hypotenuse",
                Arrays.asList("java", "math", "methods", "stdlib")),

            new Topic("math-max-min-floor-ceil",
                "Math.max, min, floor, ceil",
                "Math.max(), Math.min(), Math.floor(), Math.ceil() — clamping, rounding",
                Arrays.asList("java", "math", "methods", "stdlib")),

            new Topic("math-round-random",
                "Math.round and Math.random",
                "Math.round() and Math.random() — dice roller, random password, statistics",
                Arrays.asList("java", "math", "random", "stdlib")),

            new Topic("math-log-sin-cos",
                "Math.log, sin, cos",
                "Math.log(), Math.sin(), Math.cos() — logarithms, trigonometry in Java",
                Arrays.asList("java", "math", "trigonometry", "stdlib")),

            new Topic("string-valueof-format",
                "String.valueOf and format",
                "String.valueOf(), String.format(), printf — number formatting, templates",
                Arrays.asList("java", "string", "formatting", "stdlib")),

            new Topic("string-join-split",
                "String.join and split",
                "String.join(), String.split() — CSV processing, text parsing, joining lists",
                Arrays.asList("java", "string", "parsing", "stdlib")),

            new Topic("string-to-chararray",
                "String.toCharArray",
                "String.toCharArray() — character analysis, palindrome check, anagram detection",
                Arrays.asList("java", "string", "char-array", "algorithms")),

            new Topic("oop-introduction",
                "OOP Introduction",
                "What is OOP? Why use it? Comparing procedural vs object-oriented approach",
                Arrays.asList("java", "oop", "introduction", "design")),

            new Topic("oop-four-pillars",
                "OOP Four Pillars",
                "Encapsulation, Inheritance, Polymorphism, Abstraction — all four with examples",
                Arrays.asList("java", "oop", "pillars", "design")),

            new Topic("class-blueprint",
                "Class as Blueprint",
                "Class definition: fields, constructors, methods — Person, Car, Product blueprints",
                Arrays.asList("java", "oop", "class", "blueprint")),

            new Topic("object-instance",
                "Object Instance",
                "Creating objects with new: instance variables, heap memory, object lifecycle",
                Arrays.asList("java", "oop", "object", "instance")),

            new Topic("class-declaration",
                "Class Declaration and Object Creation",
                "class Car {} declaration + new Car() instantiation — full OOP starter example",
                Arrays.asList("java", "oop", "class", "object-creation"))
        );
    }
}
