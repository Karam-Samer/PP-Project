package app.model;

import java.util.ArrayList;
import java.util.List;

public class Grid{
    private final int rows;
    private final int cols;
    private final Cell[][] cells;
    private Cell startCell = null;
    private Cell goalCell = null;

    //------------------------------Constructor------------------------------

    public Grid(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];

        for(int r = 0; r < rows; r++){

            for(int c = 0; c < cols; c++){
                cells[r][c] = new Cell(r, c);
            }

        }

    }//Grid()

    //------------------------------setters and getters------------------------------

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public Cell getCell(int row, int col){

        if(row < 0 || row >= rows || col < 0 || col >= cols){
            return null;
        }

        return cells[row][col];
    }

    public Cell getStartCell(){
        return startCell;
    }

    public Cell getGoalCell(){
        return goalCell;
    }

    public void setStartCell(int row, int col){
        Cell cell = getCell(row, col);

        if(cell == null){
            return;
        }

        if(cell.equals(goalCell)){
            goalCell = null;
        }

        if(
            startCell != null 
            && !startCell.equals(cell)
            && startCell.getCellState() == Cell.CellState.START
        ){
            startCell.setCellState(Cell.CellState.EMPTY);
        }

        startCell = cell;
        startCell.setCellState(Cell.CellState.START);
    }//setStartCell()

    public void setStartCell(Cell startCell){
        if(startCell != null){
            setStartCell(startCell.getRow(), startCell.getCol());
        }
    }   

    public void setGoalCell(int row, int col){
        Cell cell = getCell(row, col);

        if(cell == null){
            return;
        }

        if(cell.equals(startCell)){
            startCell = null;
        }

        if(
            goalCell != null 
            && !goalCell.equals(cell) 
            && goalCell.getCellState() == Cell.CellState.END
        ){
            goalCell.setCellState(Cell.CellState.EMPTY);
        }

        goalCell = cell;
        goalCell.setCellState(Cell.CellState.END);
    }//setGoalCell()

    public void setGoalCell(Cell goalCell){
        if(goalCell != null){
            setGoalCell(goalCell.getRow(), goalCell.getCol());
        }
    }

    //------------------------------methods------------------------------

    public void refreshStartGoal(){
        Cell foundStart = null;
        Cell foundGoal = null;

        for(int r = 0; r < rows; r++){

            for(int c = 0; c < cols; c++){
                Cell current = cells[r][c];
                Cell.CellState state = current.getCellState();

                if(state == Cell.CellState.START){

                    if(foundStart == null){
                        foundStart = current;
                    } 
                    else{
                        current.setCellState(Cell.CellState.EMPTY);
                    }

                }
                else if(state == Cell.CellState.END){

                    if(foundGoal == null){
                        foundGoal = current;
                    }
                    else{
                        current.setCellState(Cell.CellState.EMPTY);
                    }

                }

            }//for

        }//for

        this.startCell = foundStart;
        this.goalCell = foundGoal;
    } // refreshStartGoal()

    private void clearState(Cell.CellState targetState){

        for(int r = 0; r < rows; r++){

            for(int c = 0; c < cols; c++){

                if(cells[r][c].getCellState() == targetState){
                    cells[r][c].setCellState(Cell.CellState.EMPTY);
                }

            }

        }

    }//clearState()

    public List<Cell> getCellsByState(Cell.CellState state){
        List<Cell> matches = new ArrayList<>();

        if(state == null){
            return matches;
        }

        for(int r = 0; r < rows; r++){

            for(int c = 0; c < cols; c++){

                if(cells[r][c].getCellState() == state){
                    matches.add(cells[r][c]);
                }

            }

        }

        return matches;
    }//getCellsByState()

    public boolean isWalkable(Cell cell){

        if(cell == null){
            return false;
        }

        Cell target = getCell(cell.getRow(), cell.getCol());
        return (target != null) && (!target.isBlocked());
    }//isWalkable()

    public void setCellState(int row, int col, Cell.CellState state){
        Cell cell = getCell(row, col);

        if(cell != null){
            cell.setCellState(state);
        }

    }//setCellState()

    public List<Cell> getNeighbors(Cell cell){
        List<Cell> neighbors = new ArrayList<>();
        if(cell == null){
            return neighbors;
        }

        int[][] dirs = { 
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        for(int[] d : dirs){
            Cell next = getCell(cell.getRow() + d[0], cell.getCol() + d[1]);

            if(next != null){
                neighbors.add(next);
            }

        }

        return neighbors;
    }//getNeighbors()

}//class Grid


