package app.model;

import java.util.*;
import app.model.Cell;
import app.model.Grid;

public class PathRequest {
    private final Grid grid;
    private final Cell start;
    private final Cell goal;

    //---------------------------------constructors-------------------------------------

    public PathRequest(Grid grid) {
        this.grid = grid;
        this.start = (grid != null) ? grid.getStartCell() : null;
        this.goal = (grid != null) ? grid.getGoalCell() : null;
    }

    public PathRequest(Grid grid, Cell start, Cell goal) {
        this.grid = grid;
        this.start = start;
        this.goal = goal;
        this.grid.setStartCell(start);
        this.grid.setGoalCell(goal);
    }

    //---------------------------------getters and setters----------------------------------------

    public Grid getGrid() {
        return grid;
    }

    public Cell getStart() {
        return start;
    }

    public Cell getGoal() {
        return goal;
    }

    //---------------------------------methods----------------------------------------

    public boolean isReady() {
        return (grid != null) && (start != null) && (goal != null);
    }

}//class PathRequest
