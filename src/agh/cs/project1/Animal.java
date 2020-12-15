package agh.cs.project1;

import java.util.ArrayList;
import static agh.cs.project1.MapDirection.values;

public class Animal implements IMapElement {

    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;
    private final Genotype genotype;
    private int energy;     // mówi nam ile dni zostało jeszcze danemu zwierzątku
    private final ArrayList<IPositionChangeObserver> observers;



    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startEnergy){
        this.orientation = initialOrientation;
        this.position = initialPosition;
        this.observers = new ArrayList<>();
        this.map = map;
        this.energy = startEnergy;
        this.genotype = new Genotype();
        map.placeAnimal(this);
    }


    public String toString() {

        // zwraca schematyczną orientację
        return this.orientation.toString();
    }


    public MapDirection getOrientation() {
        return this.orientation;
    }


    public Vector2d getPosition() {
        return this.position;
    }


    public void move() {
        int turn = genotype.chooseTurn();
        this.orientation = values()[(turn + this.orientation.ordinal()) % 8];

        Vector2d oldPosition = this.position;
        Vector2d newPosition = this.position.add(this.orientation.toUnitVector());
        int newPositionX = newPosition.x;
        int newPositionY = newPosition.y;

        // jeżeli zwierzę wyszłoby za mapę, to "zawija" na drugi kraniec
        if (newPositionX == -1) {
            newPositionX += map.getWidth();
        }
        else if (newPositionX == map.getWidth()) {
            newPositionX -= map.getWidth();
        }

        if (newPositionY == -1) {
            newPositionY += map.getHeight();
        }
        else if (newPositionY == map.getHeight()) {
            newPositionY -= map.getHeight();
        }

        newPosition = new Vector2d(newPositionX,newPositionY);
        this.position = newPosition;
        this.energy -= 1;
        positionChanged(this, oldPosition, newPosition);

    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    //  informuje wszystkich obserwatorów, o tym że pozycja została zmieniona
    private void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers){
            observer.positionChanged(animal, oldPosition, newPosition);
        }
    }

    public int getEnergy() {
        return energy;
    }

    public void feed(int plantEnergy){
        this.energy += plantEnergy;
    }
}
