package pathfinding;

import java.util.*;
import model.Cell;
import model.Grid;
import model.Path;

public class BFSPathFinder implements PathFinder {

    @Override
    public Path findPath(PathRequest request) {
        Grid grid = request.getGrid();
        Cell start = request.getStart();
        Cell goal = request.getGoal();

        if (grid == null || start == null || goal == null || !grid.isWalkable(start) || !grid.isWalkable(goal)) {
            return new Path(Collections.emptyList());
        }

        if (start.equals(goal)) {
            return new Path(List.of(start));
        }

        Queue<Cell> queue = new ArrayDeque<>();
        Set<Cell> visited = new HashSet<>();
        Map<Cell, Cell> parentMap = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            if (current.equals(goal)) {
                return buildPath(parentMap, start, goal);
            }

            for (Cell neighbor : grid.getNeighbors(current)) {
                if (grid.isWalkable(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return new Path(Collections.emptyList());
    }

    private Path buildPath(Map<Cell, Cell> parentMap, Cell start, Cell goal) {
        List<Cell> pathCells = new ArrayList<>();
        Cell current = goal;

        while (current != null) {
            pathCells.add(current);
            if (current.equals(start)) {
                break;
            }
            current = parentMap.get(current);
        }

        Collections.reverse(pathCells);
        return new Path(pathCells);
    }
}