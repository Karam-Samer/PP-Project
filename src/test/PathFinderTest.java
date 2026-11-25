package pathfinding;

import java.util.*;
import model.Cell;
import model.Grid;
import model.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PathFinderTest {

    @Test
    void testKnownPathBFS() {
        Grid grid = new Grid(4, 4);
        grid.setBlocked(0, 1, true);
        grid.setBlocked(1, 1, true);

        PathFinder finder = new BFSPathFinder();
        Cell start = new Cell(0, 0);
        Cell goal = new Cell(0, 2);

        Path path = finder.findPath(new PathRequest(grid, start, goal));

        assertFalse(path.getCells().isEmpty());
        assertEquals(start, path.getCells().get(0));
        assertEquals(goal, path.getCells().get(path.getCells().size() - 1));
        assertEquals(7, path.getCells().size());
    }

    @Test
    void testNoPathBFS() {
        Grid grid = new Grid(3, 3);
        grid.setBlocked(0, 1, true);
        grid.setBlocked(1, 0, true);
        grid.setBlocked(1, 1, true);

        PathFinder finder = new BFSPathFinder();
        Path path = finder.findPath(new PathRequest(grid, new Cell(0, 0), new Cell(2, 2)));

        assertTrue(path.getCells().isEmpty());
    }

    @Test
    void testStartEqualsGoal() {
        Grid grid = new Grid(3, 3);
        Cell same = new Cell(1, 1);

        PathFinder finder = new BFSPathFinder();
        Path path = finder.findPath(new PathRequest(grid, same, same));

        assertEquals(List.of(same), path.getCells());
    }

    @Test
    void testBlockedStartOrGoal() {
        Grid grid = new Grid(3, 3);
        grid.setBlocked(0, 0, true);

        PathFinder finder = new BFSPathFinder();
        Path blockedStart = finder.findPath(new PathRequest(grid, new Cell(0, 0), new Cell(2, 2)));
        assertTrue(blockedStart.getCells().isEmpty());

        Grid grid2 = new Grid(3, 3);
        grid2.setBlocked(2, 2, true);
        Path blockedGoal = finder.findPath(new PathRequest(grid2, new Cell(0, 0), new Cell(2, 2)));
        assertTrue(blockedGoal.getCells().isEmpty());
    }

    @Test
    void testDijkstraWeightedPath() {
        Grid grid = new Grid(2, 3);
        grid.getCell(0, 1).setCost(20.0);

        PathFinder dijkstra = new DijkstraPathFinder();
        Path path = dijkstra.findPath(new PathRequest(grid, new Cell(0, 0), new Cell(0, 2)));

        List<Cell> expected = List.of(
                new Cell(0, 0),
                new Cell(1, 0),
                new Cell(1, 1),
                new Cell(1, 2),
                new Cell(0, 2));
        assertEquals(expected, path.getCells());
    }
}