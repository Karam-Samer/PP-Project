package test;

import java.util.*;
import app.gui.Cell;
import app.gui.Grid;
import app.gui.Path;
import app.algorithms.DijkstraPathFinder;
import app.algorithms.PathFinder;
import app.algorithms.PathRequest;

public class DijkstraPathFinderTest {

    public static void main(String[] args) {
        System.out.println(" Running DijkstraPathFinder Tests ");
        testCheaperLongerPath();
        testEqualWeightsPath();
        testNoPath();
        testStartEqualsGoal();
        testBlockedStartOrGoal();
        System.out.println(" Dijkstra Tests Finished ");
    }

    public static void testCheaperLongerPath() {
        Grid grid = new Grid(2, 3);
        grid.getCell(0, 1).setCost(20.0);

        PathFinder dijkstra = new DijkstraPathFinder();
        Path path = dijkstra.findPath(new PathRequest(grid, new Cell(0, 0), new Cell(0, 2)));

        List<Cell> expected = Arrays.asList(
            new Cell(0, 0),
            new Cell(1, 0),
            new Cell(1, 1),
            new Cell(1, 2),
            new Cell(0, 2)
        );

        if (path.getCells().equals(expected)) {
            System.out.println("[PASS] testCheaperLongerPath -> " + path.getCells());
        } else {
            System.out.println("[FAIL] testCheaperLongerPath -> Got: " + path.getCells());
        }
    }

  public static void testEqualWeightsPath() {
        Grid grid = new Grid(4, 4);
        grid.setBlocked(0, 1, true);
        grid.setBlocked(1, 1, true);

        PathFinder dijkstra = new DijkstraPathFinder();
        Cell start = new Cell(0, 0);
        Cell goal = new Cell(0, 2);

        Path path = dijkstra.findPath(new PathRequest(grid, start, goal));
        List<Cell> cells = path.getCells();

        if (!cells.isEmpty()
                && cells.get(0).equals(start)
                && cells.get(cells.size() - 1).equals(goal)
                && cells.size() == 7) {
            System.out.println("[PASS] testEqualWeightsPath -> " + cells);
        } else {
            System.out.println("[FAIL] testEqualWeightsPath -> Got: " + cells);
        }
    }

    public static void testNoPath() {
        Grid grid = new Grid(3, 3);
        grid.setBlocked(0, 1, true);
        grid.setBlocked(1, 0, true);
        grid.setBlocked(1, 1, true);

        PathFinder dijkstra = new DijkstraPathFinder();
        Path path = dijkstra.findPath(new PathRequest(grid, new Cell(0, 0), new Cell(2, 2)));

        if (path.getCells().isEmpty()) {
            System.out.println("[PASS] testNoPath");
        } else {
            System.out.println("[FAIL] testNoPath -> Expected empty path");
        }
    }
  
    public static void testStartEqualsGoal() {
        Grid grid = new Grid(3, 3);
        Cell same = new Cell(1, 1);

        PathFinder dijkstra = new DijkstraPathFinder();
        Path path = dijkstra.findPath(new PathRequest(grid, same, same));

        if (path.getCells().size() == 1 && path.getCells().get(0).equals(same)) {
            System.out.println("[PASS] testStartEqualsGoal");
        } else {
            System.out.println("[FAIL] testStartEqualsGoal");
        }
    }

    public static void testBlockedStartOrGoal() {
        PathFinder dijkstra = new DijkstraPathFinder();

        Grid grid1 = new Grid(3, 3);
        grid1.setBlocked(0, 0, true);
        Path blockedStart = dijkstra.findPath(new PathRequest(grid1, new Cell(0, 0), new Cell(2, 2)));

        Grid grid2 = new Grid(3, 3);
        grid2.setBlocked(2, 2, true);
        Path blockedGoal = dijkstra.findPath(new PathRequest(grid2, new Cell(0, 0), new Cell(2, 2)));

        if (blockedStart.getCells().isEmpty() && blockedGoal.getCells().isEmpty()) {
            System.out.println("[PASS] testBlockedStartOrGoal");
        } else {
            System.out.println("[FAIL] testBlockedStartOrGoal");
        }
    }
}
