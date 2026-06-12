package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the class loading process work?
 *
 * Difficulty: SENIOR
 */

public class Lesson06_HowDoesTheClassLoadingProcessWork {
    public static void main(String[] args) {
        System.out.println("=== CLASS LOADING PROCESS ===\n");
        System.out.println("Three Main Steps:");
        System.out.println("  1. Loading: find and load .class file");
        System.out.println("  2. Linking: verify, prepare, resolve");
        System.out.println("  3. Initialization: execute static initializers");
        System.out.println();
        System.out.println("Loading:");
        System.out.println("  - ClassLoader finds .class file");
        System.out.println("  - Creates Class object in method area");
        System.out.println("  - Bootstrap -> Extension -> System -> Custom");
        System.out.println();
        System.out.println("Linking:");
        System.out.println("  - Verification: bytecode verifier checks validity");
        System.out.println("  - Preparation: allocate memory for static variables");
        System.out.println("  - Resolution: replace symbolic references with direct");
        System.out.println();
        System.out.println("Initialization:");
        System.out.println("  - Execute static variables and static blocks");
        System.out.println("  - Happens on first active use");
        System.out.println("  - Thread-safe (class initialization lock)");
        System.out.println();
        System.out.println("ClassLoader Hierarchy:");
        System.out.println("  - Bootstrap (null): core Java classes");
        System.out.println("  - Extension: JRE/lib/ext");
        System.out.println("  - System/App: CLASSPATH");
        System.out.println("  - Custom: user-defined");
        System.out.println();
        System.out.println("Senior-Level Topics:");
        System.out.println("  - Delegation model (parent-first)");
        System.out.println("  - Custom class loaders (OSGi, web containers)");
        System.out.println("  - Class unloading (Metaspace GC)");
        System.out.println("  - Reflection and class loading");
    }
}
