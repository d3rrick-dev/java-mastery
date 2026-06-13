package phase11;

import java.lang.ref.*;
import java.util.*;

/**
 * LESSON 7: STRONG, WEAK, SOFT, AND PHANTOM REFERENCES
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Strong references
 * 2. Weak references
 * 3. Soft references
 * 4. Phantom references
 * 5. ReferenceQueue usage
 * 6. Interview questions
 */

public class Lesson07_StrongWeakSoftPhantomReferences {
    public static void main(String[] args) {
        System.out.println("""
            === REFERENCE TYPES ===
            
            1. STRONG REFERENCE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Normal Java references that prevent GC.
            
               WHY IT EXISTS:
               - Default reference type
               - Object lifecycle control
            
               SIMPLE EXAMPLE:
                   String strong = new String("strong");
                   // GC will NOT collect while 'strong' variable exists
                   
                   strong = null;  // Now eligible for GC
            
               REAL-WORLD BACKEND EXAMPLE:
                   A service instance:
                   - Spring bean with strong reference
                   - Lives for application lifetime
                   - Not eligible for GC
            
               INTERVIEW QUESTION:
                   "What is a strong reference?
                   How does it differ from other reference types?"
            
               COMMON MISTAKES:
                   - Not understanding reference strength
                   - Memory leaks from strong refs
            
            ─────────────────────────────────────────────────────────────────────
            
            2. WEAK REFERENCE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Collected when no strong references exist.
            
               WHY IT EXISTS:
               - Canonicalizing mappings
               - Cache implementations
            
               SIMPLE EXAMPLE:
                   WeakReference<String> weak = new WeakReference<>(new String("weak"));
                   // When no strong refs, GC collects immediately
                   // weak.get() returns null after collection
            
               REAL-WORLD BACKEND EXAMPLE:
                   A cache with WeakHashMap:
                   - Keys are weak references
                   - Entries auto-removed when key GC'd
                   - Prevents cache memory leaks
            
               INTERVIEW QUESTION:
                   "When would you use WeakReference?
                   How does WeakHashMap work internally?"
            
               COMMON MISTAKES:
                   - Not checking for null
                   - Using for critical data
            
            ─────────────────────────────────────────────────────────────────────
            
            3. SOFT REFERENCE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Collected only when memory is low (before OOM).
            
               WHY IT EXISTS:
               - Memory-sensitive caches
               - Prevent OutOfMemoryError
            
               SIMPLE EXAMPLE:
                   SoftReference<String> soft = new SoftReference<>(new String("soft"));
                   // Kept until memory pressure
                   // Good for: caches that can be rebuilt
            
               REAL-WORLD BACKEND EXAMPLE:
                   A memory-sensitive cache:
                   - Cache entries as soft references
                   - JVM clears under memory pressure
                   - Prevents OOM in production
            
               INTERVIEW QUESTION:
                   "How does SoftReference differ from WeakReference?
                   When to use each?"
            
               COMMON MISTAKES:
                   - Not understanding memory pressure
                   - Using for non-rebuildable data
            
            ─────────────────────────────────────────────────────────────────────
            
            4. PHANTOM REFERENCE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Enqueued after finalization, get() always returns null.
            
               WHY IT EXISTS:
               - Cleanup without preventing GC
               - More flexible than finalize()
            
               SIMPLE EXAMPLE:
                   ReferenceQueue<String> queue = new ReferenceQueue<>();
                   PhantomReference<String> phantom = new PhantomReference<>(
                       new String("phantom"), queue
                   );
                   // phantom.get() always returns null
                   // Added to queue after finalization
            
               REAL-WORLD BACKEND EXAMPLE:
                   A resource cleanup system:
                   - Phantom ref for native resources
                   - Cleanup triggered on queue
                   - No risk of resurrection
            
               INTERVIEW QUESTION:
                   "Why does PhantomReference.get() always return null?
                   What's the use case?"
            
               COMMON MISTAKES:
                   - Expecting get() to return value
                   - Not using ReferenceQueue
            
            ─────────────────────────────────────────────────────────────────────
            
            5. REFERENCEQUEUE USAGE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Queue that receives references when objects are collected.
            
               WHY IT EXISTS:
               - Post-mortem cleanup
               - Cache entry removal
            
               SIMPLE EXAMPLE:
                   ReferenceQueue<Object> refQueue = new ReferenceQueue<>();
                   Map<String, WeakReference<Object>> cache = new HashMap<>();
                   
                   // Add to cache
                   WeakReference<Object> ref = new WeakReference<>(
                       new Object(), refQueue
                   );
                   cache.put("key", ref);
                   
                   // Clean up collected entries
                   Reference<?> cleared = refQueue.poll();
                   if (cleared != null) {
                       cache.remove("key");
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A distributed cache cleanup:
                   - Weak refs to local cache entries
                   - Queue tracks evicted entries
                   - Clean up distributed state
            
               INTERVIEW QUESTION:
                   "How would you implement a cache that
                   automatically removes entries when objects are GC'd?"
            
               COMMON MISTAKES:
                   - Not polling the queue
                   - Race conditions in cleanup
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Reference types are essential for:
            - Memory management
            - Cache implementations
            - Resource cleanup
            - JVM tuning
            """);
    }
}
