package agh.cs.project1.simulation;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SimulationEngine{
    private final IWorldMap map;
    private final List<Animal> animals;
    private final List<Plant> plants;
    private final Parameters params;
    private final Statistics stats;
    private int trackedAnimalChildren;
    private int trackedAnimalDescendants;


    public SimulationEngine(Parameters params){
        this.map = new Savannah(params.getWidth(), params.getHeight(),params.getStartEnergy());
        this.params = params;
        this.animals  = new ArrayList<>();
        this.plants = new ArrayList<>();
        this.stats = new Statistics();
        this.trackedAnimalChildren = 0;
        this.trackedAnimalDescendants = 0;
        placeFirstAnimals(params.getNumberOfAnimals());
    }

    public Statistics getStats(){
        return stats;
    }



    private void placeFirstAnimals(int numberOfAnimals){
        for (int i=0; i<numberOfAnimals; i++){
            Vector2d position;
            int idx = -1;
            do {
                position = map.getRandomPosition(0,params.getWidth()-1, 0, params.getHeight()-1);
                idx += 1;

            } while(map.isOccupied(position) && idx < numberOfAnimals);
            if (idx == numberOfAnimals){
                throw new IllegalArgumentException("Too much initial animals! Change parameters!");
            }

            MapDirection orientation = MapDirection.getRandomOrientation();
            Animal animal = new Animal(map, position,orientation,params.getStartEnergy(),stats.getEpoch());
            animals.add(animal);
            stats.encounteredNewAnimal(animal.getGenotype());

        }
    }


    public void nextDay(){
        stats.nextEpoch();
        if (animals.size() > 0) {
            removeDeadAnimals();
            moveAnimals();
            feedAnimals();
            reproduceAnimals();
            setPlants();
        }
    }

    private void moveAnimals(){
        for (Animal animal : animals){
            int turn = animal.chooseTurn();
            animal.move(turn);
            animal.survivedNextDay();
        }
    }

    private void removeDeadAnimals(){
        LinkedList<Animal> deadAnimals = new LinkedList<>();
        for (Animal animal : animals){
            if (animal.getEnergy() <= 0){
                deadAnimals.add(animal);
                this.map.removeDeadAnimal(animal,animal.getPosition());
            }
        }
        for (Animal animal : deadAnimals) {
            animals.remove(animal);
            stats.animalDied(animal.getLifespan());
            animal.setDeathEpoch(stats.getEpoch());
        }
    }

    private void feedAnimals(){
        LinkedList<Plant> plantsToRemove = new LinkedList<>();

        for (Plant plant : plants){
            LinkedList<Animal> toFeed = map.findAnimalsToFeed(plant.getPosition());
            if (toFeed != null) {
                for (Animal animal : toFeed) {
                    animal.feed(params.getPlantEnergy() / toFeed.size());
                }
                this.map.removePlant(plant);
                stats.plantEaten();
                plantsToRemove.add(plant);
            }
        }
        for (Plant plant : plantsToRemove) plants.remove(plant);
    }

    private void reproduceAnimals(){
        LinkedList<LinkedList<Animal>> pairsToReproduce = map.findAllPairsToReproduce();

        for (LinkedList<Animal> parents : pairsToReproduce){
            Animal child = parents.getFirst().reproduce(parents.getLast(),stats.getEpoch());

            if (parents.getLast().isTracked() || parents.getFirst().isTracked()) {
                trackedAnimalChildren++;
                trackedAnimalDescendants++;
                child.setTrackedDescendant(true);
            }

            if (parents.getLast().isTrackedDescendant() || parents.getFirst().isTrackedDescendant()) {
                trackedAnimalDescendants++;
                child.setTrackedDescendant(true);
            }
            animals.add(child);

            stats.encounteredNewAnimal(child.getGenotype());
        }
    }

    // each day one new plant in the jungle and on the steppe
    private void setPlants(){
        setJunglePlant();
        setSteppePlant();
    }

    private void setJunglePlant(){
        int llx = map.getJungleLowerLeft(params.getWidth(),params.getHeight(),params.getJungleRatio()).x;
        int lly = map.getJungleLowerLeft(params.getWidth(),params.getHeight(),params.getJungleRatio()).y;
        int urx = map.getJungleUpperRight(params.getWidth(), params.getHeight(), params.getJungleRatio()).x;
        int ury = map.getJungleUpperRight(params.getWidth(), params.getHeight(), params.getJungleRatio()).y;

        Vector2d junglePosition;
        int ctr = 0;

        do{
            junglePosition = map.getRandomPosition(llx,urx,lly,ury);
            ctr++;
        } while(map.isOccupied(junglePosition) && ctr < params.getHeight() * params.getWidth());

        if (ctr < params.getHeight() * params.getWidth()){
            Plant plant = new Plant(junglePosition);
            map.setPlant(plant);
            plants.add(plant);
            stats.newPlant();
        }
    }

    private void setSteppePlant(){
        int llx = map.getJungleLowerLeft(params.getWidth(),params.getHeight(),params.getJungleRatio()).x;
        int lly = map.getJungleLowerLeft(params.getWidth(),params.getHeight(),params.getJungleRatio()).y;
        int urx = map.getJungleUpperRight(params.getWidth(), params.getHeight(), params.getJungleRatio()).x;
        int ury = map.getJungleUpperRight(params.getWidth(), params.getHeight(), params.getJungleRatio()).y;

        Vector2d steppePosition;

        do {
            steppePosition = map.getRandomPosition(0, params.getWidth() - 1, 0, params.getHeight() - 1);

        } while((steppePosition.x >= llx && steppePosition.x <= urx && steppePosition.y >= lly && steppePosition.y <= ury)
                || map.isOccupied(steppePosition));

        Plant plant = new Plant(steppePosition);
        map.setPlant(plant);
        plants.add(plant);
        stats.newPlant();
    }


    public IWorldMap getMap() {
        return map;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }


    public boolean allAnimalsDead(){
        return animals.size() == 0;
    }

    public int haveDominantGenotype(){
        int ctr = 0;
        for (Animal animal : animals){
            if (animal.getGenotype().equals(stats.getDominantGenotype())){
                ctr++;
            }
        }
        return ctr;
    }

    public Animal getAnimalToTrack(Vector2d position){
        return map.getAnimalToTrack(position);
    }

    public void setTrackedAnimalChildren(int children){
        this.trackedAnimalChildren = children;
    }

    public int getTrackedAnimalChildren(){
        return trackedAnimalChildren;
    }

    public void setTrackedAnimalDescendants(int descendants){
        this.trackedAnimalDescendants = descendants;
    }

    public int getTrackedAnimalDescendants(){
        return trackedAnimalDescendants;
    }


}