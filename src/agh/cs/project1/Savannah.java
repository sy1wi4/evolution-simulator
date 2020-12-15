package agh.cs.project1;

import java.util.*;

public class Savannah implements IWorldMap,IPositionChangeObserver{

    private final LinkedList<Animal> animalsList;
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
        plants.put(plant.getPosition(),plant);
        return true;
    }

    @Override
    public boolean removePlant(Plant plant) {
        plants.remove(plant.getPosition());
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
            PriorityQueue<Animal> newList = new PriorityQueue<>((a1,a2) -> a2.getEnergy() - a1.getEnergy());
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

    private LinkedList<Animal> findAnimalsToReproduceAtPosition(Vector2d position){
        PriorityQueue<Animal> animalsOnPos = animals.get(position);
        if(animalsOnPos.size() >= 2){
            LinkedList<Animal> parents = new LinkedList<Animal>();
            // poll - usuwa element z pq, natomiast peek nie
            Animal first = animalsOnPos.poll();
            Animal second = animalsOnPos.peek();
            animalsOnPos.add(first);

            if (second.canReproduce()) {
                parents.add(first);
                parents.add(second);
                return parents;
            }
            else return null;
        }
        else return null;
    }

    @Override
    // zrob ze tu pary a nie listy 2el xd
    public LinkedList<LinkedList<Animal>> findAllAnimalsToReproduce(){
        LinkedList<LinkedList<Animal>> toReproduce = new LinkedList<LinkedList<Animal>>();

        for ( Vector2d position : animals.keySet() ) {
            if (this.findAnimalsToReproduceAtPosition(position) != null)
                toReproduce.add(this.findAnimalsToReproduceAtPosition(position));
        }

        return toReproduce;
    }


    @Override
    public LinkedList<Animal> findAnimalsToFeed(Vector2d position){
        PriorityQueue<Animal> animalsOnPos = animals.get(position);

        if (animalsOnPos != null && animalsOnPos.size() >= 1){
            LinkedList<Animal> toFeed = new LinkedList<Animal>();
            Animal first = animalsOnPos.poll();
            toFeed.add(first);
            while (animalsOnPos.peek() != null && animalsOnPos.peek().getEnergy() == first.getEnergy()){
                toFeed.add(animalsOnPos.poll());
            }
            animalsOnPos.addAll(toFeed);
            return toFeed;
        }
        else return null;

    }




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
