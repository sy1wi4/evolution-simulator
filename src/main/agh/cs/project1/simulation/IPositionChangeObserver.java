package agh.cs.project1.simulation;

public interface IPositionChangeObserver {

    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);

}
