package agh.cs.project1.animation;

import agh.cs.project1.simulation.classes.Animal;
import agh.cs.project1.simulation.classes.Plant;
import agh.cs.project1.simulation.engine.Parameters;
import agh.cs.project1.simulation.engine.SimulationEngine;
import agh.cs.project1.simulation.map.Vector2d;
import agh.cs.project1.simulation.statistics.Statistics;

import javax.swing.*;
import java.awt.*;


public class MapPanel extends JPanel {
    private final SimulationEngine engine1;
    private final SimulationEngine engine2;
    private final Parameters params;
    private boolean showDominant;
    private Animal trackedAnimal;
    private int trackedOnMap;
    private int trackedEpoch;

    // both maps have the same size and initial parameters
    public MapPanel(SimulationEngine engine1, SimulationEngine engine2, Parameters params){
        this.engine1 = engine1;
        this.engine2 = engine2;
        this.params = params;
        this.showDominant = false;
        this.trackedAnimal = null;
        this.trackedOnMap = -1;   // 0 is left map, 1 is right map
        this.trackedEpoch = -1;   // epoch when animal started to be tracked

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
        g.setColor(new Color(220, 241, 141));

        // first map (left)
        g.fillRect(wDiff/2, hDiff/2, mapWidth , mapHeight);

        // second map (right)
        g.fillRect(width/2 + wDiff/2, hDiff/2, mapWidth , mapHeight);


        // JUNGLE AREA

        g.setColor(new Color(164, 229, 105));
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
            if (this.showDominant && engine1.getDominantGenotypeInEpoch().equals(animal.getGenotype()))
                g.setColor(new Color(5, 104, 220));
            else
            g.setColor(animal.setColor());

            int x = animal.getPosition().x * tileWidth + wDiff/2;
            int y = animal.getPosition().y * tileHeight + hDiff/2;
            g.fillOval(x, y, tileWidth, tileHeight);
        }

        // second map
        for (Animal animal : engine2.getAnimals()) {
            if (this.showDominant && engine2.getDominantGenotypeInEpoch().equals(animal.getGenotype()))
                g.setColor(new Color(5, 104, 220));
            else
            g.setColor(animal.setColor());

            int x = animal.getPosition().x * tileWidth + wDiff/2 + width/2;
            int y = animal.getPosition().y * tileHeight + hDiff/2;
            g.fillOval(x, y, tileWidth, tileHeight);
        }

        setShowDominant(false);



        // STATISTICS

        int l = getHeight()/40;
        int statsHeight = tileHeight/2 + height + hDiff;


        g.setColor(Color.black);

        // first map
        Statistics stats1 = engine1.getStats();
        int s1 = getWidth()/8;

        Font currentFont = g.getFont();
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString("Epoch: " + stats1.getEpoch(), 0,statsHeight);
        g.drawString("LEFT MAP ", s1,statsHeight);
        g.setFont(currentFont);
        g.drawString("Animals: " + stats1.getAliveAnimalsNumber(), s1,statsHeight + l);
        g.drawString("Plants: " + stats1.getPlantsNumber(), s1,statsHeight + 2 * l);
        g.drawString("Avg children: " + stats1.getAverageChildrenNumber(engine1.getAnimals()), s1,statsHeight + 3 * l);
        g.drawString("Avg energy: " + stats1.getAverageEnergyLevel(engine1.getAnimals()), s1,statsHeight + 4 * l);
        g.drawString("Avg lifespan: " + stats1.getAverageDeadAnimalsLifespan(), s1,statsHeight + 5 * l);
        g.drawString("Dominant genotype: " + engine1.getDominantGenotypeInEpoch().getStringGenotype(), s1,statsHeight + 6 * l);
        g.drawString("Alive animals with dominant genotype: " + engine1.haveDominantGenotype(),s1,statsHeight + 7 * l);

        // second map
        Statistics stats2 = engine2.getStats();
        int s2 = 3 * getWidth()/8;

        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString("RIGHT MAP ", s2,statsHeight);
        g.setFont(currentFont);
        g.drawString("Animals: " + stats2.getAliveAnimalsNumber(), s2,statsHeight + l);
        g.drawString("Plants: " + stats2.getPlantsNumber(), s2,statsHeight + 2 * l);
        g.drawString("Avg children: " + stats2.getAverageChildrenNumber(engine1.getAnimals()), s2,statsHeight + 3 * l);
        g.drawString("Avg energy: " + stats2.getAverageEnergyLevel(engine1.getAnimals()), s2,statsHeight + 4 * l);
        g.drawString("Avg lifespan: " + stats2.getAverageDeadAnimalsLifespan(), s2,statsHeight + 5 * l);
        g.drawString("Dominant genotype: " + engine2.getDominantGenotypeInEpoch().getStringGenotype(), s2,statsHeight + 6 * l);
        g.drawString("Alive animals with dominant genotype: " + engine2.haveDominantGenotype(),s2,statsHeight + 7 * l);


        // tracked animal genotype
        int s3 = 6 * getWidth()/8;

        // tracked animal on map is black - when animal dies, it is removed from map
        if (trackedAnimal != null){


            g.setFont(new Font("TimesRoman", Font.BOLD, 15));
            g.drawString("TRACKED ANIMAL ",s3,statsHeight + l);
            g.setFont(currentFont);
            g.drawString(("Genotype: " + trackedAnimal.getGenotype().getStringGenotype()),s3,statsHeight + 2 * l);
            g.drawString("Tracked in epoch: " + trackedEpoch,s3,statsHeight + 3 * l);
            g.drawString("Birth epoch: " + trackedAnimal.getBirthEpoch(),s3,statsHeight + 4 * l);
            if (trackedOnMap == 0) {
                g.drawString("Children born after " + (stats1.getEpoch() - trackedEpoch) + " epoch(s): " + engine1.getTrackedAnimalChildren(), s3, statsHeight + 5 * l);
                g.drawString("Descendant born after " + (stats1.getEpoch() - trackedEpoch) + " epoch(s): " + engine1.getTrackedAnimalDescendants(), s3, statsHeight + 6 * l);

            }
            else if (trackedOnMap == 1) {
                g.drawString("Children born after " + (stats2.getEpoch() - trackedEpoch) + " epoch(s): " + engine2.getTrackedAnimalChildren(), s3, statsHeight + 5 * l);
                g.drawString("Descendant born after " + (stats2.getEpoch() - trackedEpoch) + " epoch(s): " + engine2.getTrackedAnimalDescendants(), s3, statsHeight + 6 * l);

            }
            if (trackedAnimal.getDeathEpoch() != -1)
                g.drawString("Death epoch: " + trackedAnimal.getDeathEpoch(),s3,statsHeight + 7 * l);




            if (trackedOnMap == 1 && trackedAnimal.getEnergy() > 0) {
                int x = trackedAnimal.getPosition().x * tileWidth + wDiff / 2 + width / 2;
                int y = trackedAnimal.getPosition().y * tileHeight + hDiff / 2;
                g.setColor(new Color(0, 0, 0, 255));
                g.fillOval(x, y, tileWidth, tileHeight);
            }

            else if (trackedOnMap == 0 && trackedAnimal.getEnergy() > 0) {
                int x = trackedAnimal.getPosition().x * tileWidth + wDiff / 2 ;
                int y = trackedAnimal.getPosition().y * tileHeight + hDiff / 2;
                g.setColor(new Color(0, 0, 0, 255));
                g.fillOval(x, y, tileWidth, tileHeight);
            }
        }
    }


    public void setShowDominant(boolean showDominant){
        this.showDominant = showDominant;
    }

    public void findClickedAnimal(int x, int y){
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

        // show its genotype and change color
        // possibly on left map
        if (x < wDiff/2 + mapWidth && y < hDiff/2 + mapHeight) {
            int positionX = (x - wDiff / 2) / tileWidth;
            int positionY = (y - hDiff / 2) / tileWidth;
            Vector2d position = new Vector2d(positionX, positionY);

            trackedAnimal = engine1.getAnimalToTrack(position);
            if (trackedAnimal != null){
                setTrackedAnimal(trackedAnimal,0,engine2.getStats().getEpoch());
            }

        }

        // possibly on right map
        else if (x > width/2 + wDiff/2 && y < hDiff/2 + mapHeight){
            int positionX = (x - wDiff / 2 - width/2) / tileWidth;
            int positionY = (y - hDiff / 2) / tileWidth;
            Vector2d position = new Vector2d(positionX, positionY);

            trackedAnimal = engine2.getAnimalToTrack(position);
            if (trackedAnimal != null){
                setTrackedAnimal(trackedAnimal,1,engine2.getStats().getEpoch());
            }
        }
    }

    private void setTrackedAnimal(Animal animalTracked, int map, int epoch) {
        if (this.trackedAnimal != null) {
            trackedAnimal.setTracked(false);
        }
        this.trackedAnimal = animalTracked;
        this.trackedOnMap = map;
        this.trackedEpoch = epoch;
        trackedAnimal.setTracked(true);
        if (map == 0) {
            engine1.setTrackedAnimalChildren(0);
            engine1.setTrackedAnimalDescendants(0);
            engine1.resetTrackedDescendant();

        }
        else if (map == 1) {
            engine2.setTrackedAnimalChildren(0);
            engine2.setTrackedAnimalDescendants(0);
            engine2.resetTrackedDescendant();

        }

    }
//
//    private void saveStatsToFile(){
//
//    }

}
