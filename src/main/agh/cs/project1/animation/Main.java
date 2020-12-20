package agh.cs.project1.animation;

import agh.cs.project1.simulation.Parameters;
import agh.cs.project1.simulation.SimulationEngine;

import javax.swing.*;
import java.awt.event.*;

public class Main implements ActionListener {
    private final Timer timer;
    private final MapPanel mapPanel;
    private final SimulationEngine engine1;
    private final SimulationEngine engine2;
    private final JButton stopSimulationButton;
    private final JButton resumeSimulationButton;
    private final JButton showAnimalsWithDominantGenotype;


    public Main(SimulationEngine engine1,SimulationEngine engine2, Parameters params){
        int delay = params.getDelay();
        this.timer = new Timer(delay,this);
        Frame frame = new Frame();
        this.engine1 = engine1;
        this.engine2 = engine2;
        this.mapPanel = new MapPanel(engine1,engine2, params);

        this.stopSimulationButton = new JButton("Stop simulation");
        this.resumeSimulationButton = new JButton("Resume simulation");
        this.showAnimalsWithDominantGenotype = new JButton("Show animals with dominant genotype");

        stopSimulationButton.addActionListener(this);
        resumeSimulationButton.addActionListener(this);
        showAnimalsWithDominantGenotype.addActionListener(this);

        mapPanel.add(stopSimulationButton);
        mapPanel.add(resumeSimulationButton);
        mapPanel.add(showAnimalsWithDominantGenotype);

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mapPanel.findClickedAnimal(e.getPoint().x,e.getPoint().y);
            }
        });

        frame.setVisible(true);
        frame.add(mapPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (engine1.allAnimalsDead() && engine2.allAnimalsDead())
            timer.stop();

        Object source = e.getSource();
        if (source == stopSimulationButton) {
            timer.stop();

        }
        else if (source == resumeSimulationButton)
            timer.start();

        else if (source == showAnimalsWithDominantGenotype) {
            mapPanel.repaint();
            mapPanel.setShowDominant(true);
        }


        else {
            mapPanel.repaint();
            engine1.nextDay();
            engine2.nextDay();
        }

    }



    public void run(){
        timer.start();
    }
}
