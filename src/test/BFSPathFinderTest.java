package test;

import java.util.*;
import app.gui.Cell;
import app.gui.Grid;
import app.gui.Path;
import app.algorithms.BFSPathFinder;
import app.algorithms.PathFinder;
import app.algorithms.PathRequest;

public class BFSPathFinderTest {

    public static void main(String[] args) {
        System.out.println(" Starting BFS Tests ");
        testKnownPath();
        testNoPath();
        testStartEqualsGoal();
        testBlockedStartOrGoal();
        System.out.println(" BFS Tests Finished ");
    }

    public static void testKnownPath() {
        Grid grid = new Grid(4, 4);
        grid.setBlocked(0, 1, true);
        grid.setBlocked(1, 1, true);

        PathFinder finder = new BFSPathFinder();
        Cell start = new Cell(0, 0);
        Cell goal = new Cell(0, 2);

        Path path = finder.findPath(new PathRequest(grid, start, goal));
        List<Cell> cells = path.getCells();

        if (!cells.isEmpty()
                && cells.get(0).equals(start)
                && cells.get(cells.size() - 1).equals(goal)
                && cells.size() == 7) {
            System.out.println("[PASS] testKnownPath -> " + cells);
        } else {
            System.out.println("[FAIL] testKnownPath -> Got: " + cells);
        }
    }

    public static void testNoPath() {
        Grid grid = new Grid(3, 3);
        grid.setBlocked(0, 1, true);
        grid.setBlocked(1, 0, true);
        grid.setBlocked(1, 1, true);

        PathFinder finder = new BFSPathFinder();
        Path path = finder.findPath(new PathRequest(grid, new Cell(0, 0), new Cell(2, 2)));

        if (path.getCells().isEmpty()) {
            System.out.println("[PASS] testNoPath");
        } else {
            System.out.println("[FAIL] testNoPath -> Expected empty path");
        }
    }

    public static void testStartEqualsGoal() {
        Grid grid = new Grid(3, 3);
        Cell same = new Cell(1, 1);

        PathFinder finder = new BFSPathFinder();
        Path path = finder.findPath(new PathRequest(grid, same, same));
        List<Cell> cells = path.getCells();

        if (cells.size() == 1 && cells.get(0).equals(same)) {
            System.out.println("[PASS] testStartEqualsGoal -> " + cells);
        } else {
            System.out.println("[FAIL] testStartEqualsGoal -> Got: " + cells);
        }
    }

    public static void testBlockedStartOrGoal() {
        PathFinder finder = new BFSPathFinder();

        Grid grid1 = new Grid(3, 3);
        grid1.setBlocked(0, 0, true);
        Path blockedStart = finder.findPath(new PathRequest(grid1, new Cell(0, 0), new Cell(2, 2)));

        Grid grid2 = new Grid(3, 3);
        grid2.setBlocked(2, 2, true);
        Path blockedGoal = finder.findPath(new PathRequest(grid2, new Cell(0, 0), new Cell(2, 2)));

        if (blockedStart.getCells().isEmpty() && blockedGoal.getCells().isEmpty()) {
            System.out.println("[PASS] testBlockedStartOrGoal");
        } else {
            System.out.println("[FAIL] testBlockedStartOrGoal");
        }
    }
}
