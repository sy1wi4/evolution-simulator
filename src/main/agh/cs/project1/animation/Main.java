package agh.cs.project1.animation;

import agh.cs.project1.simulation.Parameters;
import agh.cs.project1.simulation.SimulationEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    private final int delay;   // milliseconds
    private Timer timer;
    public Frame frame;
    public MapPanel simulationVisualizer;
    public SimulationEngine engine;

    public Main(SimulationEngine engine, Parameters params, int delay){
        this.delay = delay;
        this.timer = new Timer(delay,this);
        this.frame = new Frame();
        this.engine = engine;
        this.simulationVisualizer = new MapPanel(engine,this, params);
        simulationVisualizer.setSize(1,1);

        frame.add(simulationVisualizer);
        frame.setVisible(true);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulationVisualizer.repaint();
        engine.nextDay();
        if (engine.allAnimalsDead())
            timer.stop();
    }
}
