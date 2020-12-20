package agh.cs.project1.simulation;

import java.util.LinkedList;

public interface IWorldMap {

    void placeAnimal(Animal animal);

    void setPlant(Plant plant);

    Object objectAt(Vector2d position);

    Vector2d getRandomPosition(int minX, int maxX, int minY, int maxY);

    boolean isOccupied(Vector2d position);

    Vector2d getJungleLowerLeft(int width, int height, double jungleRatio);

    Vector2d getJungleUpperRight(int width, int height, double jungleRatio);

    int getHeight();

    int getWidth();

    LinkedList<Animal> findAnimalsToFeed(Vector2d position);

    LinkedList<LinkedList<Animal>> findAllPairsToReproduce();

    void removePlant(Plant plant);

    Vector2d getChildPosition(Vector2d parentsPosition);

    void removeDeadAnimal(Animal animal, Vector2d position);

    int getSideOfJungle(int width, int height, double jungleRatio);

    Animal getAnimalToTrack(Vector2d position);
}
