package phase11;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * LESSON 12: FUNCTIONAL PROGRAMMING CONCEPTS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Functional programming treats computation as evaluation
 * of mathematical functions. No mutable state, no side effects.
 * Like a math formula: input -> output, always same result.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Easier to reason about
 * - Better for parallel processing
 * - Less bugs from mutable state
 * - Composable operations
 */

public class Lesson12_FunctionalProgrammingConcepts {

    public static void main(String[] args) {
        System.out.println("=== FUNCTIONAL PROGRAMMING CONCEPTS ===\n");

        // ============================================================
        // EXAMPLE 1: Pure functions
        // ============================================================
        System.out.println("--- Example 1: Pure Functions ---\n");

        // Pure: Same input -> Same output, no side effects
        Function<Integer, Integer> square = n -> n * n;
        System.out.println("square(5) = " + square.apply(5));
        System.out.println("square(5) = " + square.apply(5));  // Same result
        System.out.println();

        // Impure: Depends on external state
        int factor = 2;
        Function<Integer, Integer> impureSquare = n -> n * n * factor;
        System.out.println("impureSquare(5) = " + impureSquare.apply(5));
        factor = 3;  // Changes result!
        System.out.println("impureSquare(5) = " + impureSquare.apply(5));  // Different!
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Immutability
        // ============================================================
        System.out.println("--- Example 2: Immutability ---\n");

        List<Integer> mutable = new ArrayList<>(Arrays.asList(1, 2, 3));
        mutable.add(4);  // Modifies original
        System.out.println("Mutable: " + mutable);

        List<Integer> immutable = Arrays.asList(1, 2, 3);
        // immutable.add(4);  // UnsupportedOperationException
        System.out.println("Immutable: " + immutable);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Higher-order functions
        // ============================================================
        System.out.println("--- Example 3: Higher-Order Functions ---\n");

        // Function that returns a function
        Function<Integer, Function<Integer, Integer>> multiplier =
            factor -> n -> n * factor;

        Function<Integer, Integer> times3 = multiplier.apply(3);
        Function<Integer, Integer> times5 = multiplier.apply(5);

        System.out.println("times3.apply(4) = " + times3.apply(4));
        System.out.println("times5.apply(4) = " + times5.apply(4));
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Function composition
        // ============================================================
        System.out.println("--- Example 4: Function Composition ---\n");

        Function<Integer, Integer> doubleIt = n -> n * 2;
        Function<Integer, Integer> addOne = n -> n + 1;
        Function<Integer, Integer> square = n -> n * n;

        // Compose: doubleIt then addOne then square
        Function<Integer, Integer> composed = square.compose(addOne).compose(doubleIt);

        System.out.println("composed.apply(3) = " + composed.apply(3));
        // (3 * 2 + 1)^2 = 7^2 = 49
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Currying
        // ============================================================
        System.out.println("--- Example 5: Currying ---\n");

        // Multi-argument function as curried functions
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

        // Curried version
        Function<Integer, Function<Integer, Integer>> curriedAdd =
            a -> b -> a + b;

        System.out.println("add.apply(3, 4) = " + add.apply(3, 4));
        System.out.println("curriedAdd.apply(3).apply(4) = " +
            curriedAdd.apply(3).apply(4));
        System.out.println();
    }

    // ============================================================
    // FUNCTIONAL PROGRAMMING CONCEPTS
    // ============================================================
    /*
     * Key Concepts:
     *
     * 1. Pure Functions
     *    - Same input -> Same output
     *    - No side effects
     *    - No mutation of external state
     *
     * 2. Immutability
     *    - Data cannot be changed after creation
     *    - New data created from old
     *    - Thread-safe by default
     *
     * 3. First-Class Functions
     *    - Functions as values
     *    - Can be passed as arguments
     *    - Can be returned from functions
     *
     * 4. Higher-Order Functions
     *    - Take functions as parameters
     *    - Return functions
     *    - map, filter, reduce are examples
     *
     * 5. Function Composition
     *    - Combine functions: f(g(x))
     *    - compose() and andThen()
     *
     * 6. Currying
     *    - Transform f(a, b) -> f(a)(b)
     *    - Partial application
     */
}
