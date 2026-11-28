package app.model;

import java.util.Objects;

public class Cell{
    public enum CellState{
        EMPTY,
        BLOCKED,
        START,
        END
    }

    private final int row;
    private final int col;
    private CellState state;
    private double cost = 1.0;

    //------------------------------Constructor------------------------------

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    //------------------------------setters and getters------------------------------

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public double getCost(){
        return cost;
    }

    public CellState getCellState(){
        return state;
    }

    public void setCellState(CellState state){
        this.state = state;
    }

    public void setCost(double cost){
        this.cost = cost;
    }

    //------------------------------methods------------------------------

    public void setBlocked(boolean blocked){
        this.state = blocked ? CellState.BLOCKED : CellState.EMPTY;
    }

    public boolean isBlocked(){
        return (state == CellState.BLOCKED);
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if (!(o instanceof Cell)){
            return false;
        }

        Cell other = (Cell) o;
        return ((row == other.row) && (col == other.col));
    }//equals()

    @Override
    public int hashCode(){
        return Objects.hash(row, col);
    }

    @Override
    public String toString(){
        return "(" + row + "," + col + ")";
    }

}//class Cell

