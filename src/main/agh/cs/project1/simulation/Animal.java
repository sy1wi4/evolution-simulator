package agh.cs.project1.simulation;

import java.util.ArrayList;
import static agh.cs.project1.simulation.MapDirection.values;

public class Animal implements IMapElement {

    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;
    private final Genotype genotype;
    private int energy;     // mówi nam ile dni zostało jeszcze danemu zwierzątku
    private final ArrayList<IPositionChangeObserver> positionObservers;
    private final ArrayList<IEnergyChangeObserver> energyObservers;



    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startEnergy){
        this.orientation = initialOrientation;
        this.position = initialPosition;
        this.positionObservers = new ArrayList<>();
        this.energyObservers = new ArrayList<>();
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


    public int chooseTurn(){
        return this.genotype.chooseTurn();
    }

    public void move(int turn) {
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
        this.positionChanged(oldPosition, newPosition);
        this.energyChanged();

    }

    public void addPositionObserver(IPositionChangeObserver observer){
        this.positionObservers.add(observer);
    }

    public void removePositionObserver(IPositionChangeObserver observer){
        this.positionObservers.remove(observer);
    }

    public void addEnergyObserver(IEnergyChangeObserver observer){
        this.energyObservers.add(observer);
    }

    public void removeEnergyObserver(IEnergyChangeObserver observer){
        this.energyObservers.remove(observer);
    }


    //  informuje wszystkich obserwatorów, o tym że pozycja została zmieniona
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : positionObservers){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    //  informuje wszystkich obserwatorów, o tym że energia się zmieniła
    private void energyChanged() {
        for (IEnergyChangeObserver observer : energyObservers){
            observer.energyChanged(this);
        }
    }

    public int getEnergy() {
        return energy;
    }

    public void feed(int plantEnergy){

        this.energy += plantEnergy;
        this.energyChanged();
    }


    public Animal reproduce(Animal other){
        Vector2d childPosition = map.getChildPosition(this.position);
        int childEnergy = this.energy/4 + other.energy/4;
        this.energy -= this.energy/4;
        other.energy -= other.energy/4;
        this.energyChanged();
        other.energyChanged();
        return new Animal(map,childPosition,MapDirection.getRandomOrientation(),childEnergy);
    }

}
