package agh.cs.project1.animation;

import agh.cs.project1.simulation.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapPanel extends JPanel {
    private final SimulationEngine engine1;
    private final SimulationEngine engine2;
    private final Parameters params;

    // both maps have the same size and initial parameters
    public MapPanel(SimulationEngine engine1, SimulationEngine engine2, Parameters params){
        this.engine1 = engine1;
        this.engine2 = engine2;
        this.params = params;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);


        // width & height of window
        int width = this.getWidth();
        int height = 3*this.getHeight()/4;

        // width & height of map tile - depending on given map size
        int tileWidth = width/2/params.getWidth();
        int tileHeight = height/params.getHeight();

        int mapWidth = params.getWidth() * tileWidth;
        int mapHeight = params.getHeight() * tileHeight;

        int wDiff = width/2 - mapWidth;
        int hDiff = height - mapHeight;


        // BACKGROUND
        g.setColor(new Color(52, 59, 26));
        g.fillRect(0, 0, width , height);


        // STEPPE AREA
        g.setColor(new Color(226, 247, 117));

        // first map (left)
        g.fillRect(wDiff/2, hDiff/2, mapWidth , mapHeight);

        // second map (right)
        g.fillRect(width/2 + wDiff/2, hDiff/2, mapWidth , mapHeight);


        // JUNGLE AREA

        g.setColor(new Color(135, 196, 94));
        int jungleLowerLeftX = engine1.getMap().getJungleLowerLeft(params.getWidth(), params.getHeight(),params.getJungleRatio()).x;
        int jungleLowerLeftY = engine1.getMap().getJungleLowerLeft(params.getWidth(), params.getHeight(),params.getJungleRatio()).y;
        // side of jungle
        int sideOfJungle = engine1.getMap().getSideOfJungle(params.getWidth(), params.getHeight(),params.getJungleRatio()) + 1;

        // first map
        g.fillRect(jungleLowerLeftX * tileWidth + wDiff/2,
                jungleLowerLeftY * tileHeight + hDiff/2,
                sideOfJungle  * tileWidth,
                sideOfJungle * tileHeight);


        // second map
        g.fillRect(width/2 + wDiff/2 + jungleLowerLeftX * tileWidth,
                hDiff/2 + jungleLowerLeftY * tileHeight,
                sideOfJungle  * tileWidth,
                sideOfJungle * tileHeight);





        // PLANTS

        // first map
        for (Plant plant : engine1.getPlants()) {
            g.setColor(plant.setColor());
            int x = plant.getPosition().x * tileWidth + wDiff/2;
            int y = plant.getPosition().y * tileHeight + hDiff/2;
            g.fillRect(x, y, tileWidth, tileHeight);
        }

        // second map
        for (Plant plant : engine2.getPlants()) {
            g.setColor(plant.setColor());
            int x = plant.getPosition().x * tileWidth + wDiff/2 + width/2;
            int y = plant.getPosition().y * tileHeight + hDiff/2;
            g.fillRect(x, y, tileWidth, tileHeight);
        }


        // ANIMALS
        // color is blue if animal have dominant genotype

        // first map
        for (Animal animal : engine1.getAnimals()) {
            if (engine1.getStats().getDominantGenotype().equals(animal.getGenotype()))
                g.setColor(new Color(5, 104, 220));
            else
                g.setColor(animal.setColor());

            int x = animal.getPosition().x * tileWidth + wDiff/2;
            int y = animal.getPosition().y * tileHeight + hDiff/2;
            g.fillOval(x, y, tileWidth, tileHeight);
        }

        // second map
        for (Animal animal : engine2.getAnimals()) {
            if (engine2.getStats().getDominantGenotype().equals(animal.getGenotype()))
                g.setColor(new Color(5, 104, 220));
            else
                g.setColor(animal.setColor());

            int x = animal.getPosition().x * tileWidth + wDiff/2 + width/2;
            int y = animal.getPosition().y * tileHeight + hDiff/2;
            g.fillOval(x, y, tileWidth, tileHeight);
        }



        // STATISTICS

        int l = getHeight()/40;
        int statsHeight = tileHeight/2 + height + hDiff;


        g.setColor(Color.black);

        // first map
        Statistics stats1 = engine1.getStats();
        int s1 = getWidth()/8;

        g.drawString("Epoch: " + stats1.getEpoch(), 0,statsHeight);
        g.drawString("Animals: " + stats1.getAliveAnimalsNumber(), s1,statsHeight + l);
        g.drawString("Plants: " + stats1.getPlantsNumber(), s1,statsHeight + 2 * l);
        g.drawString("Avg children: " + stats1.getAverageChildrenNumber(engine1.getAnimals()), s1,statsHeight + 3 * l);
        g.drawString("Avg energy: " + stats1.getAverageEnergyLevel(engine1.getAnimals()), s1,statsHeight + 4 * l);
        g.drawString("Avg lifespan: " + stats1.getAverageDeadAnimalsLifespan(), s1,statsHeight + 5 * l);
        g.drawString("Dominant genotype: " + stats1.getDominantGenotype().getStringGenotype(), s1,statsHeight + 6 * l);
        g.drawString("Alive animals with dominant genotype: " + engine1.haveDominantGenotype(),s1,statsHeight + 7 * l);

        // second map
        Statistics stats2 = engine2.getStats();
        int s2 = 5 * getWidth()/8;

        g.drawString("Animals: " + stats2.getAliveAnimalsNumber(), s2,statsHeight + l);
        g.drawString("Plants: " + stats2.getPlantsNumber(), s2,statsHeight + 2 * l);
        g.drawString("Avg children: " + stats2.getAverageChildrenNumber(engine1.getAnimals()), s2,statsHeight + 3 * l);
        g.drawString("Avg energy: " + stats2.getAverageEnergyLevel(engine1.getAnimals()), s2,statsHeight + 4 * l);
        g.drawString("Avg lifespan: " + stats2.getAverageDeadAnimalsLifespan(), s2,statsHeight + 5 * l);
        g.drawString("Dominant genotype: " + stats2.getDominantGenotype().getStringGenotype(), s2,statsHeight + 6 * l);
        g.drawString("Alive animals with dominant genotype: " + engine2.haveDominantGenotype(),s2,statsHeight + 7 * l);




    }

}
