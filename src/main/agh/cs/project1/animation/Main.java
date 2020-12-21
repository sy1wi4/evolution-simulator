package agh.cs.project1.animation;

import agh.cs.project1.simulation.engine.Parameters;
import agh.cs.project1.simulation.engine.SimulationEngine;
import agh.cs.project1.simulation.statistics.Statistics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main implements ActionListener {
    private final Timer timer;
    private final MapPanel mapPanel;
    private final SimulationEngine engine1;
    private final SimulationEngine engine2;
    private final JButton stopSimulationButton;
    private final JButton resumeSimulationButton;
    private final JButton showAnimalsWithDominantGenotype;
    private final JButton saveStatsToFile;


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
        this.saveStatsToFile = new JButton("Save stats to file");

        stopSimulationButton.addActionListener(this);
        resumeSimulationButton.addActionListener(this);
        showAnimalsWithDominantGenotype.addActionListener(this);
        saveStatsToFile.addActionListener(this);

        mapPanel.add(stopSimulationButton);
        mapPanel.add(resumeSimulationButton);
        mapPanel.add(showAnimalsWithDominantGenotype);
        mapPanel.add(saveStatsToFile);

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

        else if (source == saveStatsToFile){
            try {
                saveStatsFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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


    // averaged statistics through all epochs
    public void saveStatsFile() throws IOException {
        Statistics stats1 = engine1.getStats();
        Statistics stats2 = engine2.getStats();

        save(stats1,"left");
        save(stats2,"right");
    }

    private void save(Statistics stats, String map) throws IOException {
        FileWriter write = new FileWriter("src\\main\\agh\\cs\\project1\\simulation\\statistics\\"+ map + "MapStatsAfter" + stats.getEpoch() + "epochs.txt");
        PrintWriter print_line = new PrintWriter(write);
        print_line.println("avg animals number : " + stats.getAvgAnimalsNumberThroughEpochs());
        print_line.println("avg plants number  : " + stats.getAvgPlantsNumberThroughEpochs());
        print_line.println("dominant genotype  : " + stats.getDominantGenotypeThroughEpochs().getStringGenotype());
        print_line.println("avg energy level   : " + stats.getAvgEnergyLevelThroughEpochs());
        print_line.println("avg lifespan       : " + stats.getAverageDeadAnimalsLifespan());
        print_line.println("avg children number: " + stats.getAvgChildrenNumberThroughEpochs());
        print_line.close();
    }
}
