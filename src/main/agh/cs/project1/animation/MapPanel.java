package agh.cs.project1.animation;

import agh.cs.project1.simulation.*;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private final SimulationEngine engine;
    private final Main simulation;
    private final Parameters params;

    public MapPanel(SimulationEngine engine, Main simulation, Parameters params){
        this.engine = engine;
        this.simulation = simulation;
        this.params = params;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        // width & height of window
        int width = this.getWidth();
        int height = this.getHeight();

        // width & height of map tile - depending on given map size
        int tileWidth = width / params.getWidth();
        int tileHeight = height / params.getHeight();

        // TODO: nie skaluj, tylko zrób tak by dżungla była zawsze kwadratowa

        // steppe area
        g.setColor(new Color(226, 247, 117));
        g.fillRect(0, 0, width, height);


        // jungle area
        g.setColor(new Color(135, 196, 94));
        int jungleLowerLeftX = engine.getMap().getJungleLowerLeft(params.getWidth(), params.getHeight(),params.getJungleRatio()).x;
        int jungleLowerLeftY = engine.getMap().getJungleLowerLeft(params.getWidth(), params.getHeight(),params.getJungleRatio()).y;
        // side of jungle is
        int sideOfJungle = engine.getMap().getSideOfJungle(params.getWidth(), params.getHeight(),params.getJungleRatio()) + 1;

//        System.out.println("jungle " + sideOfJungle);
//        System.out.println("ll " + engine.getMap().getJungleLowerLeft(params.getWidth(), params.getHeight(),params.getJungleRatio()));
//        System.out.println("ur " + engine.getMap().getJungleUpperRight(params.getWidth(), params.getHeight(),params.getJungleRatio()));

        g.fillRect(jungleLowerLeftX * tileWidth,
                jungleLowerLeftY * tileHeight,
                sideOfJungle  * tileWidth,
                sideOfJungle * tileHeight);


        // plants
        for (Plant plant : engine.getPlants()) {
            g.setColor(plant.setColor());
            int x = plant.getPosition().x * tileWidth;
            int y = plant.getPosition().y * tileHeight;
//            System.out.println(plant.getPosition().x + " " + plant.getPosition().y + " " + tileHeight + " " + tileWidth);
            g.fillRect(x, y, tileWidth, tileHeight);
        }


        // animals
        for (Animal animal : engine.getAnimals()) {
            g.setColor(animal.setColor());
            int x = animal.getPosition().x * tileWidth;
            int y = animal.getPosition().y * tileHeight;
//            System.out.println(animal.getPosition().x + " " + animal.getPosition().y + " " + tileHeight + " " + tileWidth);
            g.fillOval(x, y, tileWidth, tileHeight);
        }
    }


}
