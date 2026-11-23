package test;

import app.algorithms.PathFinder;
import app.gui.Path;
import app.gui.PathRequest;
import app.parallel.ParallelPathfindingEngine;

import java.util.ArrayList;
import java.util.List;

public class ParallelPathfindingEngineTest {

    public static void main(String[] args) {
        // 1. Create 20 fake requests
        List<PathRequest> requests = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            requests.add(new PathRequest());
        }

        // 2. Create a simple dummy PathFinder (until M2 finishes BFS/Dijkstra)
        PathFinder dummyFinder = request -> new Path();

        // 3. Run sequential baseline
        List<Path> sequentialResults = new ArrayList<>();
        for (PathRequest req : requests) {
            sequentialResults.add(dummyFinder.findPath(req));
        }

        // 4. Test parallel engine with 1, 2, 4, and 8 threads (5 repeated runs each)
        int[] threadCounts = {1, 2, 4, 8};

        for (int threads : threadCounts) {
            ParallelPathfindingEngine engine = new ParallelPathfindingEngine(threads, dummyFinder);

            for (int run = 1; run <= 5; run++) {
                List<Path> parallelResults = engine.findPaths(requests);

                if (parallelResults.size() != sequentialResults.size()) {
                    System.out.println("FAILED: Size mismatch for threads = " + threads);
                    return;
                }
            }
            System.out.println("PASSED: " + threads + " threads (5 runs)");
        }

        // 5. Test empty input
        ParallelPathfindingEngine engine = new ParallelPathfindingEngine(4, dummyFinder);
        if (engine.findPaths(new ArrayList<>()).isEmpty()) {
            System.out.println("PASSED: Empty list check");
        }

        System.out.println("ALL TESTS DONE!");
    }
}