package app.gui;

import javax.swing.*;
import app.gui.GridPanel;
import app.model.Cell;
import app.model.Grid;
import app.model.Path;

public class Window{
    public static void createWindow(){
        JFrame frame = new JFrame();
        frame.setTitle("Parallel Processing");
        // frame.setLayout(new BorderLayout());
        frame.setIconImage(new ImageIcon("resources/imgs/cpu2.png").getImage());
        Grid grid = new Grid(10, 10);
        GridPanel gridPanel = new GridPanel(grid , 40);
        frame.add(gridPanel);


        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }//createWindow()

}//class Window