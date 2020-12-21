package agh.cs.project1.simulation.interfaces;

import agh.cs.project1.simulation.classes.Animal;
import agh.cs.project1.simulation.map.Vector2d;

public interface IPositionChangeObserver {

    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);

}
