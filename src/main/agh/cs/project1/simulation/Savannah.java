package agh.cs.project1.simulation;

import java.util.*;

public class Savannah implements IWorldMap,IPositionChangeObserver, IEnergyChangeObserver {

    private final Map<Vector2d, PriorityQueue<Animal>> animals;
    private final Map<Vector2d, Plant> plants;
    private final int width;
    private final int height;
    private final int startEnergy;
    private final Random random = new Random();


    public Savannah(int width, int height, int startEnergy){
        this.width = width;
        this.height = height;
        this.startEnergy = startEnergy;
        this.animals = new HashMap<>();
        this.plants = new HashMap<>();
    }


    @Override
    public void placeAnimal(Animal animal) {

        // after placing animal on map, add map as its energy & position observer
        animal.addPositionObserver(this);
        animal.addEnergyObserver(this);

        Vector2d position = animal.getPosition();
        addAnimalAtPosition(animal,position);
    }


    @Override
    public void setPlant(Plant plant) {
        plants.put(plant.getPosition(),plant);
    }


    @Override
    public void removePlant(Plant plant) {
        plants.remove(plant.getPosition());
    }


    @Override
    // do zmiany kiedyś tam - tylko do debugowania na razie
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


    @Override
    public int getSideOfJungle(int width, int height, double jungleRatio){
        // the jungle is always square
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

    @Override
    public void removeDeadAnimal(Animal animal, Vector2d position){
        removeAnimalFromPosition(animal,position);
        animal.removeEnergyObserver(this);
        animal.removePositionObserver(this);
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimalFromPosition(animal, oldPosition);
        addAnimalAtPosition(animal,newPosition);
    }

    @Override
    public void energyChanged(Animal animal) {
        removeAnimalFromPosition(animal, animal.getPosition());
        addAnimalAtPosition(animal,animal.getPosition());
    }


    // after move remove animal - it will be added back
    public void removeAnimalFromPosition(Animal animal, Vector2d position){
        PriorityQueue<Animal> animalsOnOldPos = animals.get(position);

        if (animalsOnOldPos.size() == 0) throw new IllegalArgumentException("No animal on position" + position);
        else animalsOnOldPos.remove(animal);
    }


    // after move add animal back
    public void addAnimalAtPosition(Animal animal, Vector2d position){
        PriorityQueue<Animal> animalsOnPos = animals.get(position);

        if (animalsOnPos == null)
        {
            // animals are ordered descending by energy -  we can simply get the strongest
            PriorityQueue<Animal> newList = new PriorityQueue<>((a1,a2) -> a2.getEnergy() - a1.getEnergy());
            newList.add(animal);
            animals.put(position,newList);
        }
        else {
            animalsOnPos.add(animal);
        }
    }



    private LinkedList<Animal> findAnimalsToReproduceAtPosition(Vector2d position){
        PriorityQueue<Animal> animalsOnPos = animals.get(position);

        if(animalsOnPos.size() >= 2){
            LinkedList<Animal> parents;
            parents = new LinkedList<>();
            // [poll] removes element from pq, [peek] doesn't
            Animal first = animalsOnPos.poll();
            Animal second = animalsOnPos.peek();
            animalsOnPos.add(first);

            // animals can reproduce only if both have enough energy
            assert second != null;
            if (second.getEnergy() >= (this.startEnergy / 2)) {
                parents.add(first);
                parents.add(second);
                return parents;
            }
            else return null;
        }
        else return null;
    }


    @Override
    public LinkedList<LinkedList<Animal>> findAllPairsToReproduce(){
        LinkedList<LinkedList<Animal>> toReproduce = new LinkedList<>();

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
            LinkedList<Animal> toFeed = new LinkedList<>();
            Animal first = animalsOnPos.poll();
            toFeed.add(first);

            // feed strongest animal on position - if there is more than 1, share a plant
            while (animalsOnPos.peek() != null && animalsOnPos.peek().getEnergy() == first.getEnergy()){
                toFeed.add(animalsOnPos.poll());
            }
            animalsOnPos.addAll(toFeed);
            return toFeed;
        }
        else return null;
    }


    public Vector2d getChildPosition(Vector2d parentsPosition){
        int parentsX = parentsPosition.x;
        int parentsY = parentsPosition.y;
        int childX = (parentsX - 1);
        int childY = (parentsY - 1);
        if (childX < 0) childX += width;
        if (childY < 0) childY += height;


        // look for free position around the parents
        for (int x = 0; x < 3; x++){
            for (int y = 0; y< 3; y++){
                Vector2d childPosition = new Vector2d((childX + x) % width,(childY + y) % height);
                if (!isOccupied(childPosition)) return childPosition;
            }
        }

        // if there is no free position - get random occupied position around parents
        int x = random.nextInt(3);
        int y = random.nextInt(3);

        // while random position is parents' position
        while (x == 1 && y == 1) {
            x = random.nextInt(3);
            y = random.nextInt(3);
        }

        return new Vector2d(x,y);
    }

    @Override
    public Animal getStrongestAnimal(Vector2d position){
        PriorityQueue<Animal> animalsOnPos = animals.get(position);
        System.out.println(animalsOnPos.peek().getNumberOfChildren());
        return animalsOnPos.peek();
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
