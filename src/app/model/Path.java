package app.model;

import java.util.Collections;
import java.util.List;

public class Path {
    private final List<Cell> cells;

    //---------------------------------constructors-------------------------------------

    public Path(){
        this.cells = Collections.emptyList();
    }

    public Path(List<Cell> cells){
        this.cells = (cells == null) ? Collections.emptyList() : List.copyOf(cells);
    }

    //---------------------------------getters and setters----------------------------------------

    public List<Cell> getCells(){
        return cells;
    }

    //---------------------------------methods----------------------------------------

    public boolean isEmpty(){
        return cells.isEmpty();
    }

}//class Path