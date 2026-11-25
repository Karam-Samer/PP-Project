package pathfinding;

import java.util.*;
import model.Cell;
import model.Grid;
import model.Path;

public class DijkstraPathFinder implements PathFinder {

    private static class NodeCost {
        final Cell cell;
        final double totalCost;

        NodeCost(Cell cell, double totalCost) {
            this.cell = cell;
            this.totalCost = totalCost;
        }
    }

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

        Map<Cell, Double> minCost = new HashMap<>();
        Map<Cell, Cell> parentMap = new HashMap<>();
        Set<Cell> visited = new HashSet<>();

        PriorityQueue<NodeCost> pq = new PriorityQueue<>(
                Comparator.comparingDouble(nc -> nc.totalCost));

        minCost.put(start, 0.0);
        pq.add(new NodeCost(start, 0.0));

        while (!pq.isEmpty()) {
            NodeCost currentEntry = pq.poll();
            Cell current = currentEntry.cell;

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            if (current.equals(goal)) {
                return buildPath(parentMap, start, goal);
            }

            double currentCost = minCost.get(current);

            for (Cell neighbor : grid.getNeighbors(current)) {
                if (!grid.isWalkable(neighbor) || visited.contains(neighbor)) {
                    continue;
                }

                double newCost = currentCost + neighbor.getCost();
                double oldCost = minCost.getOrDefault(neighbor, Double.POSITIVE_INFINITY);

                if (newCost < oldCost) {
                    minCost.put(neighbor, newCost);
                    parentMap.put(neighbor, current);
                    pq.add(new NodeCost(neighbor, newCost));
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