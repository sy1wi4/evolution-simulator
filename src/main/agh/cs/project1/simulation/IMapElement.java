package agh.cs.project1.simulation;

import java.awt.*;

public interface IMapElement {
    /**
     *
     * @return Position of given object.
     */
    Vector2d getPosition();

    /**
     *
     * @return String: "*" if Grass or schematic orientation on the map if Animal.
     */
    String toString();

    /**
     *
     * @return Color of given element - Plants are green, Animal's color is energy-dependant.
     */
    Color setColor();
}
