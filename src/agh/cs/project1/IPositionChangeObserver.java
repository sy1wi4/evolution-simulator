package agh.cs.project1;

public interface IPositionChangeObserver {
    /**
     * Delete pair <oldPosition, animal> from Hashmap and add pair pair <newPosition, animal>.
     * @param oldPosition,
     *            Old position of given animal.
     * @param  newPosition
     *            New position of given animal.
     */
    void positionChanged( Vector2d oldPosition, Vector2d newPosition);

}
