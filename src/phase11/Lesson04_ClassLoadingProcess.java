package phase11;

/**
 * LESSON 4: CLASS LOADING PROCESS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Class loading is the process of reading .class files,
 * verifying them, and making them ready for execution.
 * Like loading a book from the shelf before reading it.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Load classes on demand (lazy loading)
 * - Verify bytecode safety
 * - Prepare memory for class data
 */

public class Lesson04_ClassLoadingProcess {

    public static void main(String[] args) {
        System.out.println("=== CLASS LOADING PROCESS ===\n");

        // ============================================================
        // EXAMPLE 1: Loading phases
        // ============================================================
        System.out.println("--- Example 1: Loading Phases ---\n");

        System.out.println("Class Loading Lifecycle:");
        System.out.println();
        System.out.println("  1. LOADING");
        System.out.println("     - Read .class file bytes");
        System.out.println("     - Create Class object in method area");
        System.out.println("     - Create ClassLoader instance");
        System.out.println();
        System.out.println("  2. VERIFICATION");
        System.out.println("     - Check bytecode format");
        System.out.println("     - Verify access modifiers");
        System.out.println("     - Check final classes not subclassed");
        System.out.println();
        System.out.println("  3. PREPARATION");
        System.out.println("     - Allocate memory for static variables");
        System.out.println("     - Initialize to default values");
        System.out.println();
        System.out.println("  4. RESOLUTION");
        System.out.println("     - Replace symbolic references with direct references");
        System.out.println("     - Resolve method/field references");
        System.out.println();
        System.out.println("  5. INITIALIZATION");
        System.out.println("     - Execute static initializers");
        System.out.println("     - Assign static variable initial values");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Class loaders
        // ============================================================
        System.out.println("--- Example 2: Class Loaders ---\n");

        System.out.println("Class Loader Hierarchy:");
        System.out.println();
        System.out.println("  Bootstrap ClassLoader (null parent)");
        System.out.println("    |");
        System.out.println("    v");
        System.out.println("  Extension ClassLoader (parent: Bootstrap)");
        System.out.println("    |");
        System.out.println("    v");
        System.out.println("  System/App ClassLoader (parent: Extension)");
        System.out.println("    |");
        System.out.println("    v");
        System.out.println("  Custom ClassLoader (parent: System)");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: ClassLoader methods
        // ============================================================
        System.out.println("--- Example 3: ClassLoader Methods ---\n");

        ClassLoader cl = Lesson04_ClassLoadingProcess.class.getClassLoader();
        System.out.println("Current class loader: " + cl);
        System.out.println("Parent: " + cl.getParent());
        System.out.println("Parent's parent: " + cl.getParent().getParent());
        System.out.println();

        // ============================================================
        // EXAMPLE 4: When classes are loaded
        // ============================================================
        System.out.println("--- Example 4: When Classes Load ---\n");

        System.out.println("Classes are loaded when:");
        System.out.println("  - new keyword is used");
        System.out.println("  - Class.forName() is called");
        System.out.println("  - Static method/field is accessed");
        System.out.println("  - Class is referenced (not always!)");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  Class.forName(\"com.example.MyClass\");  // Explicit load");
        System.out.println("  MyClass obj = new MyClass();             // Implicit load");
        System.out.println("  System.out.println(MyClass.CONSTANT);    // Implicit load");
        System.out.println();
    }

    // ============================================================
    // CLASS LOADING DETAILS
    // ============================================================
    /*
     * Class Loading Process:
     *
     * 1. Loading:
     *    - Read .class file bytes
     *    - Create Class object
     *    - Done by ClassLoader
     *
     * 2. Verification:
     *    - Bytecode verifier checks:
     *      * Format correctness
     *      * Type safety
     *      * Access control
     *
     * 3. Preparation:
     *    - Allocate static variables
     *    - Initialize to default values (0, null, false)
     *
     * 4. Resolution:
     *    - Symbolic references -> Direct references
     *    - Can be lazy (on first use) or eager
     *
     * 5. Initialization:
     *    - Execute <clinit>() method
     *    - Static blocks run in order
     *    - Static vars get initial values
     *
     * Class Loaders:
     * - Bootstrap: JRE/lib/rt.jar (native code)
     * - Extension: JRE/lib/ext
     * - System: CLASSPATH
     * - Custom: User-defined
     */
}
