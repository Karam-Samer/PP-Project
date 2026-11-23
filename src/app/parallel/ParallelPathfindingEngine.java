package app.parallel;

import app.algorithms.PathFinder;
import app.gui.PathRequest;
import app.gui.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;

public class ParallelPathfindingEngine {
    //This class will find paths in parallel using multiple threads
    private final int numThreads;
    private final PathFinder pathFinder;

    public ParallelPathfindingEngine(int numThreads, PathFinder pathFinder) {
        if (numThreads < 1) throw new IllegalArgumentException("numThreads must be at least 1");
        if (pathFinder == null)  throw new IllegalArgumentException("pathFinder must not be null"); 
        this.numThreads = numThreads;
        this.pathFinder = pathFinder;
    }  

    public int getNumThreads() {
        return numThreads;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public List<Path> findPaths(List<PathRequest> requests) {
        //Here is the core engine that will find paths in parallel
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(numThreads, requests.size()));
        List<Future<Path>> futures = new ArrayList<Future<Path>>(requests.size());
        try{
            for(PathRequest request : requests) {
                Future<Path> future = executorService.submit(() -> pathFinder.findPath(request));
                futures.add(future);
            }
            List<Path> paths = new ArrayList<>();
            for(Future<Path> future : futures) {
                paths.add(future.get());
            }
            return paths;
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            cancel(futures);
            throw new RuntimeException("Interrupted", e);
        } catch(ExecutionException e) {
            cancel(futures);
            throw new RuntimeException("Error during execution", e);
        }finally{
            //I just tell the executor service to not accept any new tasks.
            executorService.shutdown();
        }
        
    }

    private void cancel(List<Future<Path>> futures) {
        //I just cancel all the future tasks which are running or waiting to run in the queue.
        for (Future<Path> f : futures) {
            f.cancel(true);
        }
    }
}

