package agh.cs.project1;

import java.util.ArrayList;

public class Animal implements IMapElement {

    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;
    private Genotype genotype;
    private int energy;
    private ArrayList<IPositionChangeObserver> observers;




    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startEnergy){
        this.orientation = initialOrientation;
        this.position = initialPosition;
        this.observers = new ArrayList<>();
        this.map = map;
        this.energy = startEnergy;
        this.genotype = new Genotype();
        map.place(this);
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


//    public void tryToMove(Vector2d newPosition) {
//
//        if (this.map.canMoveTo(newPosition)) {
//            Vector2d oldPosition = this.position;
//
//            this.position = newPosition;
//            // informuje obserwatorów o zmianie pozycji
//            positionChanged(oldPosition, newPosition);
//
//        }
//    }


//    public void move(MoveDirection direction) {
//
//        Vector2d newPosition;
//        switch (direction) {
//
//            case RIGHT:
//                this.orientation = this.orientation.next();
//                break;
//
//            case LEFT:
//                this.orientation = this.orientation.previous();
//                break;
//
//            case FORWARD:
//                newPosition = position.add(orientation.toUnitVector());
//                tryToMove(newPosition);
//                break;
//
//            case BACKWARD:
//                newPosition = position.subtract(orientation.toUnitVector());
//                tryToMove(newPosition);
//                break;

//        }
//
//    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    //  informuje wszystkich obserwatorów, o tym że pozycja została zmieniona
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public int getEnergy() {
        return energy;
    }
}
