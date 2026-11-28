package app.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import app.model.Cell;
import app.model.Grid;
import app.model.Path;

public class GridPanel extends JPanel {

    private final Grid grid;
    private final int cellSize;
    private Path currentPath;
    private boolean showGrid = true;
    private boolean editingEnabled = true;

    //--------------------------------------Constructor--------------------------------------

    public GridPanel(Grid grid, int cellSize){
        this.grid = grid;
        this.cellSize = cellSize;
        setPreferredSize(new Dimension(grid.getCols() * cellSize, grid.getRows() * cellSize));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(!editingEnabled){
                    return;
                }
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;
                Cell clicked = grid.getCell(row, col);

                if(clicked == null){
                    return;
                }

                if(SwingUtilities.isRightMouseButton(e)){
                    grid.setGoalCell(clicked);
                }
                else if(e.isShiftDown()){
                    grid.setStartCell(clicked);
                }
                else{
                    boolean isBlocked = !grid.isWalkable(clicked);
                    Cell cell = grid.getCell(row, col);

                    if(cell != null){
                        cell.setBlocked(!isBlocked);
                    }

                }

                repaint();
            }//mousePreesd()
            
        });//mouseListener()

    }//Constructor()

    //--------------------------------------getters and setters--------------------------------------

    public void setStart(Cell start){
        this.grid.setStartCell(start);
        repaint();
    }

    public void setGoal(Cell goal){
        this.grid.setGoalCell(goal);
        repaint();
    }

    public void setPath(Path path){
        this.currentPath = path;
        repaint();
    }

    public Cell getStart(){ 
        return this.grid.getStartCell(); 
    }

    public Cell getGoal(){ 
        return this.grid.getGoalCell(); 
    }

    public Grid getGrid(){ 
        return grid; 
    }

    public void setEditingEnabled(boolean enabled){
        this.editingEnabled = enabled;
    }

    //--------------------------------------methods--------------------------------------

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        Cell start = grid.getStartCell();
        Cell goal = grid.getGoalCell();

        Set<Cell> pathCells = new HashSet<>();

        if(currentPath != null){
            pathCells.addAll(currentPath.getCells());
        }

        for(int r = 0; r < grid.getRows(); r++){
            for(int c = 0; c < grid.getCols(); c++){

                Cell cell = grid.getCell(r, c);

                int x = c * cellSize;
                int y = r * cellSize;

                // Determine cell color
                if(cell.equals(start)){
                    g2.setColor(new Color(46, 204, 113)); // Start
                }
                else if(cell.equals(goal)){
                    g2.setColor(new Color(231, 76, 60)); // Goal
                }
                else if(!grid.isWalkable(cell)){
                    g2.setColor(Color.DARK_GRAY); // Obstacle
                }
                else if(pathCells.contains(cell)){
                    g2.setColor(new Color(52, 152, 219)); // Path
                }
                else{
                    g2.setColor(Color.WHITE); // Empty
                }

                g2.fillRect(x, y, cellSize, cellSize);

                if(showGrid){
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.drawRect(x, y, cellSize, cellSize);
                }

            }//for

        }//for

        g2.dispose();
    }//paintComponent()

}//class GridPanel











































    // private final Grid grid;
    // private final int cellSize;
    // private Path currentPath;

    // public void setStart(Cell start){
    //     this.start = start;
    //     repaint();
    // }

    // public void setGoal(Cell goal){
    //     this.goal = goal;
    //     repaint();
    // }

    // public void setPath(Path path){
    //     this.currentPath = path;
    //     repaint();
    // }

    // public Cell getStart(){ 
    //     return start; 
    // }

    // public Cell getGoal(){ 
    //     return goal; 
    // }

    // public Grid getGrid(){ 
    //     return grid; 
    // }

    // public GridPanel(Grid grid, int cellSize){
    //     this.grid = grid;
    //     this.cellSize = cellSize;
    //     setPreferredSize(new Dimension(grid.getCols() * cellSize, grid.getRows() * cellSize));
    //     setBackground(Color.WHITE);

    //     addMouseListener(new MouseAdapter(){
    //         @Override
    //         public void mousePressed(MouseEvent e){
    //             int col = e.getX() / cellSize;
    //             int row = e.getY() / cellSize;
    //             Cell clicked = grid.getCell(row, col);
    //             if(clicked == null) 
    //                 return;

    //             if(SwingUtilities.isRightMouseButton(e)){
    //                 goal = clicked;
    //             }
    //             else if(e.isShiftDown()){
    //                 start = clicked;
    //             }
    //             else{
    //                 boolean isBlocked = !grid.isWalkable(clicked);
    //                 grid.setBlocked(row, col, !isBlocked);
    //             }

    //             repaint();
    //         }//mousePreesd()
            
    //     });//mouseListener()

    // }//GridPanel()

    // @Override
    // protected void paintComponent(Graphics g){
    //     super.paintComponent(g);
    //     Graphics2D g2 = (Graphics2D) g;

    //     Set<Cell> pathCells = new HashSet<>();
    //     if(currentPath != null && currentPath.getCells() != null){
    //         pathCells.addAll(currentPath.getCells());
    //     }

    //     for (int r = 0; r < grid.getRows(); r++){
    //         for (int c = 0; c < grid.getCols(); c++){
    //             Cell cell = grid.getCell(r, c);
    //             int x = c * cellSize;
    //             int y = r * cellSize;

    //             if(!grid.isWalkable(cell)){
    //                 g2.setColor(Color.DARK_GRAY);
    //             } 
    //             else if(cell.equals(start)){
    //                 g2.setColor(new Color(46, 204, 113)); // Start
    //             } 
    //             else if(cell.equals(goal)){
    //                 g2.setColor(new Color(231, 76, 60));  // Goal
    //             } 
    //             else if(pathCells.contains(cell)){
    //                 g2.setColor(new Color(52, 152, 219)); // Path
    //             } 
    //             else{
    //                 g2.setColor(Color.WHITE);
    //             }

    //             g2.fillRect(x, y, cellSize, cellSize);
    //             g2.setColor(Color.LIGHT_GRAY);
    //             g2.drawRect(x, y, cellSize, cellSize);
    //         }//for

    //     }//for

    // }//paintComponent()



