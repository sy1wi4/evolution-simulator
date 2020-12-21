package agh.cs.project1.simulation.interfaces;

import agh.cs.project1.simulation.map.Vector2d;

import java.awt.*;

public interface IMapElement {

    Vector2d getPosition();

    String toString();

    Color setColor();
}
