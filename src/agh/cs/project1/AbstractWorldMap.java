package agh.cs.project1;

import java.util.*;


abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected LinkedHashMap<Vector2d,Animal> animals = new LinkedHashMap<>();
    protected LinkedHashMap<Vector2d,Animal> plants = new LinkedHashMap<>();
    protected int width;
    protected int height;
    protected int jungleRatio;
    private final Random random = new Random();

    @Override
    public Object objectAt(Vector2d position) {

        // zmień
        return animals.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

    public Vector2d getRandomPosition(int width, int height){
        int x = random.nextInt(width);
        int y = random.nextInt(height);

        Vector2d position = new Vector2d(x,y);
        return position;
    }

}
