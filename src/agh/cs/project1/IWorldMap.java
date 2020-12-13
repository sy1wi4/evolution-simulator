package agh.cs.project1;

// Interfejs jest gwarancją, że instancje klasy, która go
// implementuje dostarczają konkretną funkcjonalność

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     * @return True if the animal was placed.
     */
    boolean place(Animal animal);

    /**
     * Place a animal on the map.
     *
     * @param plant
     *            The plant to place on the map.
     * @return True if the plant was placed. The plant cannot be placed if the map is already occupied.
     */
    boolean setPlant(Plant plant);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    /**
     * Return random position on given part of map.
     *
     * @param minX
     *            X coordinate of lower left corner of given area.
     * @param maxX
     *            X coordinate of upper right corner of given area.
     * @param minY
     *            Y coordinate of lower left corner of given area.
     * @param minY
     *            Y coordinate upper right corner of given area.
     * @return Random position of given map.
     */
    Vector2d getRandomPosition(int minX, int maxX, int minY, int maxY);

    /**
     * Return true if given position on the map is occupied.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return lower left corner of jungle planted on  the map.
     *
     * @param width
     *          Width of the map.
     * @param height
     *          Height of the map.
     * @param jungleRatio
     *           Size of the map.
     * @return Random lower left corner of jungle.
     */
    Vector2d getJungleLowerLeft(int width, int height, double jungleRatio);

    /**
     * Return upper right corner of jungle planted on  the map.
     *
     * @param width
     *          Width of the map.
     * @param height
     *          Height of the map.
     * @param jungleRatio
     *           Size of the map.
     * @return Random upper right corner of jungle.
     */
    Vector2d getJungleUpperRight(int width, int height, double jungleRatio);


}
