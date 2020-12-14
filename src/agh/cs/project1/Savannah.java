package agh.cs.project1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.PriorityQueue;

public class Savannah implements IWorldMap,IPositionChangeObserver{

    private final LinkedList<Animal> animalsList;
    private final LinkedList<Plant> plantsList;
    private final Map<Vector2d, PriorityQueue<Animal>> animals;
    private final Map<Vector2d, Plant> plants;

    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final int width;
    private final int height;
    private final Random random = new Random();

    public Savannah(int width, int height, double jungleRatio, int startEnergy){
        this.width = width;
        this.height = height;
        this.animalsList = new LinkedList<>();
        this.plantsList = new LinkedList<>();
        this.animals = new HashMap<>();
        this.plants = new HashMap<>();
    }


    @Override
    public boolean placeAnimal(Animal animal) {
        animalsList.add(animal);
        // dodając zwierzę, rejestrujemy mapę jako jego obserwatora
        animal.addObserver(this);
        Vector2d position = animal.getPosition();
        addAnimalToPos(animal,position);
        return true;
    }

    @Override
    public boolean setPlant(Plant plant) {
        plantsList.add(plant);
        plants.put(plant.getPosition(),plant);
        return true;
    }


    @Override
    // do zmiany kiedyś tam
    public Object objectAt(Vector2d position) {
        if (animals.get(position) == null || animals.get(position).size()== 0)
            return plants.get(position);
        else return animals.get(position).size();
    }

    @Override
    public Vector2d getRandomPosition(int minX, int maxX, int minY, int maxY) {
        int x = random.nextInt(maxX-minX + 1);
        int y = random.nextInt(maxY-minY + 1);
        return new Vector2d(minX + x,minY + y);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    // długość boku dżungli
    private int getSideOfJungle(int width, int height, double jungleRatio){
        return (int)Math.sqrt(width*height*jungleRatio);
    }

    @Override
    public Vector2d getJungleLowerLeft(int width, int height, double jungleRatio) {
        int a = getSideOfJungle(width, height, jungleRatio);
        int x = width/2 - a/2;
        int y = height/2 - a/2;
        return(new Vector2d(x,y));
    }

    @Override
    public Vector2d getJungleUpperRight(int width, int height, double jungleRatio) {
        int llx = getJungleLowerLeft(width, height, jungleRatio).x;
        int lly = getJungleLowerLeft(width, height, jungleRatio).y;
        int a = getSideOfJungle(width, height, jungleRatio);

        int x = llx + a;
        int y = lly + a;
        return(new Vector2d(x,y));
    }

    public void addAnimalToPos(Animal animal, Vector2d position){
        PriorityQueue<Animal> animalsOnPos = animals.get(position);

        if (animalsOnPos == null)
        {
            // zwierzęta są porządkowane względem energii, co umożliwia szybkie znalezienie najsilniejszych
            PriorityQueue<Animal> newList = new PriorityQueue<>(Comparator.comparing(Animal::getEnergy));
            newList.add(animal);
            animals.put(position,newList);
        }
        else {
            animalsOnPos.add(animal);
        }
    }

    public void removeAnimalFromPos(Animal animal, Vector2d position){
        PriorityQueue animalsOnOldPos = animals.get(position);
        if (animalsOnOldPos.size() == 0) throw new IllegalArgumentException("No animal on position" + position);
        else animalsOnOldPos.remove(animal);
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimalFromPos(animal, oldPosition);
        addAnimalToPos(animal,newPosition);
    }

//    public LinkedList<Animal> findStrongestAnimals(Vector2d position){
//        TreeSet animalsOnPos = animals.get(position);
//
//    }
//
//    public Vector2d findChildPosition(){
//        ;
//    }

    public String toString(){
        return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(width-1,height-1));
    }

    // to debug
    @Override
    public int getNumberOfAnimals(){
        return animalsList.size();
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
}
