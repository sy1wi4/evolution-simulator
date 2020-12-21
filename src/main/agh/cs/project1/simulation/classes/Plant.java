package agh.cs.project1.simulation.classes;

import agh.cs.project1.simulation.interfaces.IMapElement;
import agh.cs.project1.simulation.map.Vector2d;

import java.awt.*;

public class Plant implements IMapElement {
    private final Vector2d position;

    public Plant(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        return "*";
    }

    @Override
    public Color setColor() {
        return new Color(34,236,88);
    }
}
