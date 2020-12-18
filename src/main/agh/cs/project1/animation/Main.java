package agh.cs.project1.animation;

import agh.cs.project1.simulation.Parameters;
import agh.cs.project1.simulation.SimulationEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    private final Timer timer;
    private final MapPanel mapPanel;
    private final SimulationEngine engine1;
    private final SimulationEngine engine2;

    private JButton startButton;

    public Main(SimulationEngine engine1,SimulationEngine engine2, Parameters params){
        int delay = params.getDelay();
        this.timer = new Timer(delay,this);
        Frame frame = new Frame();

        this.engine1 = engine1;
        this.engine2 = engine2;
        this.mapPanel = new MapPanel(engine1,engine2, params);


        frame.setVisible(true);
        frame.add(mapPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        mapPanel.repaint();
        engine1.nextDay();
        engine2.nextDay();
        if (engine1.allAnimalsDead() && engine2.allAnimalsDead())
            timer.stop();
    }



    public void run(){
        timer.start();
    }
}
