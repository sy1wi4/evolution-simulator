package agh.cs.project1.simulation;

import java.awt.*;
import java.util.ArrayList;
import static agh.cs.project1.simulation.MapDirection.values;

public class Animal implements IMapElement {

    private MapDirection orientation;
    private Vector2d position;
    private final IWorldMap map;
    private Genotype genotype;
    private int energy;     // left days
    private final int startEnergy; // to color animal on map
    private final ArrayList<IPositionChangeObserver> positionObservers;
    private final ArrayList<IEnergyChangeObserver> energyObservers;
    private int children;
    private int lifespan;
    private final int birthEpoch;
    private int deathEpoch;
    private boolean isTracked;  // to count tracked animal children
    private boolean isTrackedDescendant;   // to count tracked animals descendants


    // initial animal - random genes
    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startEnergy, int birthEpoch){
        this.orientation = initialOrientation;
        this.position = initialPosition;
        this.positionObservers = new ArrayList<>();
        this.energyObservers = new ArrayList<>();
        this.map = map;
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
        this.genotype = new Genotype();
        this.children = 0;
        this.lifespan = 0;
        this.birthEpoch = birthEpoch;
        this.deathEpoch = -1;
        this.isTracked = false;
        this.isTrackedDescendant = false;
        map.placeAnimal(this);
    }


    // child - genes inherited from parents
    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startEnergy, int birthEpoch, Genotype crossedGenotype){
        this(map,initialPosition,initialOrientation,startEnergy,birthEpoch);
        this.genotype = crossedGenotype;
    }



    public String toString() {
        return this.orientation.toString();
    }


    @Override
    // color of animal on the map indicates its energy level
    public Color setColor() {
        if (this.energy >= 4*this.startEnergy/5) return new Color(61, 154, 38);
        else if (this.energy >= 3*this.startEnergy/5) return new Color(102, 177, 81);
        else if (this.energy>= 2*this.startEnergy/5) return new Color(208, 178, 60);
        else if (this.energy>= this.startEnergy/5) return new Color(198, 92, 54);
        else return new Color(175, 63, 53);
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

        // the map loops - animals can go on the other side
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
        this.positionChanged(oldPosition, newPosition);

        this.energy -= 1;
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


    // informs all observers that animal position has changed
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : positionObservers){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    //  informs all observers that animal energy has changed
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


    public Animal reproduce(Animal other, int birthEpoch){
        // we reproduce animals only if they have enough energy
        Vector2d childPosition = map.getChildPosition(this.position);
        int childEnergy = this.energy/4 + other.getEnergy()/4;
        this.energy -= this.energy/4;
        other.energy -= other.energy/4;

        this.energyChanged();
        other.energyChanged();
        this.newChild();
        other.newChild();
        Genotype crossedGenotype = this.genotype.cross(other.getGenotype());
        return new Animal(map,childPosition,MapDirection.getRandomOrientation(),childEnergy,birthEpoch,crossedGenotype);
    }


    private void newChild() {
        this.children++;
    }

    public int getNumberOfChildren(){
        return this.children;
    }

    public void survivedNextDay(){
        this.lifespan++;
    }

    public int getLifespan(){
        return this.lifespan;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public int getBirthEpoch() {
        return birthEpoch;
    }

    public void setDeathEpoch(int epoch){
        this.deathEpoch = epoch;
    }

    public int getDeathEpoch(){
        return deathEpoch;
    }

    public void setEnergy(int energy){
        this.energy = energy;
        this.energyChanged();
    }

    public void setTracked(boolean isTracked){
        this.isTracked = isTracked;
    }

    public boolean isTracked(){
        return isTracked;
    }
    public void setTrackedDescendant(boolean isTrackedDescendant){
        this.isTrackedDescendant = isTrackedDescendant;
    }

    public boolean isTrackedDescendant(){
        return isTrackedDescendant;
    }
}
