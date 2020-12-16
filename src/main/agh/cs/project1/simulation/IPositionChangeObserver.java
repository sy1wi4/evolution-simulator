package agh.cs.project1.simulation;

public interface IPositionChangeObserver {
    /**
     * Delete pair <oldPosition, animal> from Hashmap and add pair pair <newPosition, animal>.
     * @param animal
     *            Animal that has changed the position.
     * @param oldPosition,
     *            Old position of given animal.
     * @param  newPosition
     *            New position of given animal.
     */
    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);

}
