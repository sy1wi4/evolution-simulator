package agh.cs.project1.simulation;

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
}
