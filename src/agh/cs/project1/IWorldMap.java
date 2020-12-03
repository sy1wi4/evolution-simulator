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
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    boolean place(Animal animal);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param width
     * @param height
     *            Size of the map.
     * @return Random position of given map.
     */
    Vector2d getRandomPosition(int width, int height);

    /**
     * Indicate if we can place initial animal on given position.
     * Animal cannot be placed on position occupied with another animal.
     *
     * @param position
     *            The position checked for the placement possibility.
     * @return True if the animal can be placed to that position.
     */
    boolean canPlaceFirstAnimal(Vector2d position);
}
