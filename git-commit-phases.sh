#!/bin/bash
# Git commit script for Java Backend Engineering Mastery Roadmap
# Creates structured commits for each phase - run AFTER git-init.sh

set -e

echo "Configuring git user..."
git config user.email "d3rrick-dev@users.noreply.github.com"
git config user.name "d3rrick-dev"

echo "Resetting to uncommitted state..."
git reset HEAD

echo "Staging only README and pom.xml first..."
git add README.md pom.xml .gitignore
git commit -m "docs: add README.md with complete roadmap and navigation hub"

echo "Committing Phase 1: Functional Programming..."
git add src/phase1/
git commit -m "feat(phase1): add functional programming lessons (lambdas, functional interfaces, optional)"

echo "Committing Phase 2: Java Streams Fundamentals..."
git add src/phase2/
git commit -m "feat(phase2): add streams fundamentals (lifecycle, intermediate/terminal operations, lazy evaluation)"

echo "Committing Phase 3: Advanced Streams..."
git add src/phase3/
git commit -m "feat(phase3): add advanced streams (grouping, partitioning, collectors, nested collections)"

echo "Committing Phase 4: Data Structures..."
git add src/phase4/
git commit -m "feat(phase4): add data structures (Array, ArrayList, LinkedList, Stack, Queue, Deque, Map, Set)"

echo "Committing Phase 5: Stream + DSA Patterns..."
git add src/phase5/
git commit -m "feat(phase5): add stream + DSA patterns (duplicate detection, frequency counting, top K, group by)"

echo "Committing Phase 6: Coding Interview Preparation..."
git add src/phase6/
git commit -m "feat(phase6): add coding interview problems (easy to interview-level)"

echo "Committing Phase 7: Concurrency & Multithreading..."
git add src/phase7/
git commit -m "feat(phase7): add concurrency lessons (threads, executors, synchronization, JMM, concurrent collections)"

echo "Committing Phase 8: Parallelism & High-Performance Java..."
git add src/phase8/
git commit -m "feat(phase8): add parallelism lessons (parallel streams, fork-join, async programming, GC, performance tuning)"

echo "Committing Phase 9: Modern Java..."
git add src/phase9/
git commit -m "feat(phase9): add modern Java lessons (records, enums, generics, design patterns, SOLID, clean code)"

echo "Committing Phase 10: Collections Internals..."
git add src/phase10/
git commit -m "feat(phase10): add collections internals (HashMap, LinkedHashMap, TreeMap, HashSet, PriorityQueue, iterators)"

echo "Committing Phase 11: Spring Boot Backend Engineering..."
git add src/phase11_spring_boot/
git commit -m "feat(phase11): add Spring Boot backend engineering (core, REST, JPA, security, testing, microservices)"

echo "Committing Phase 12: JVM Internals & Backend Concepts..."
git add src/phase11/
git commit -m "feat(phase12): add JVM internals and backend concepts (architecture, GC, class loading, memory, design patterns)"

echo "Committing Phase 13: Java Interview Questions..."
git add src/phase12/
git commit -m "feat(phase13): add interview questions (BEGINNER 50, INTERMEDIATE 50, ADVANCED 50, SENIOR 50)"

echo ""
echo "Git repository structured successfully!"
echo "Total commits created: 14"
echo ""
echo "Commit structure:"
echo "  1. docs: add README.md with complete roadmap and navigation hub"
echo "  2. feat(phase1): add functional programming lessons"
echo "  3. feat(phase2): add streams fundamentals"
echo "  4. feat(phase3): add advanced streams"
echo "  5. feat(phase4): add data structures"
echo "  6. feat(phase5): add stream + DSA patterns"
echo "  7. feat(phase6): add coding interview problems"
echo "  8. feat(phase7): add concurrency lessons"
echo "  9. feat(phase8): add parallelism lessons"
echo " 10. feat(phase9): add modern Java lessons"
echo " 11. feat(phase10): add collections internals"
echo " 12. feat(phase11): add Spring Boot backend engineering"
echo " 13. feat(phase12): add JVM internals and backend concepts"
echo " 14. feat(phase13): add interview questions"
echo ""
echo "To view the commit history, run: git log --oneline"
