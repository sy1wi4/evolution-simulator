package agh.cs.project1.simulation;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class SimulationEngine{
    private final IWorldMap map;
    private final List<Animal> animals;
    private final List<Plant> plants;
    private final Parameters params;

    private final Random random = new Random();


    public SimulationEngine(IWorldMap map, Parameters params){
        this.map = map;
        this.params = params;
        this.animals  = new ArrayList<>();
        this.plants = new ArrayList<>();

        placeFirstAnimals(params.getNumberOfAnimals());

        System.out.println(map.toString());
        printStatus();
        System.out.println("==============================================");

        for (int i=0; i<7; i++) {
            int day = i+1;
            System.out.println("DAY " + day);
            nextDay();
        }

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
            Animal animal = new Animal(map, position,orientation,params.getStartEnergy());

            animals.add(animal);

        }
    }


    public void nextDay(){
        removeDeadAnimals();
        moveAnimals();
        System.out.println("after move");
        System.out.println(map.toString());
        printStatus();

        feedAnimals();
        reproduceAnimals();
        setPlants();
        System.out.println("after reproducing, feeding & setting plants");
        System.out.println(map.toString());
        printStatus();
        System.out.println("==============================================");
    }



    private void moveAnimals(){
        for (Animal animal : animals){
            int turn = animal.chooseTurn();
            animal.move(turn);
        }
        System.out.println();
    }

    private void removeDeadAnimals(){
        LinkedList<Animal> deadAnimals = new LinkedList<Animal>();
        for (Animal animal : animals){
            if (animal.getEnergy() <= 0){
                System.out.println("animal died" + animal.getPosition());
                deadAnimals.add(animal);
                this.map.removeDeadAnimal(animal,animal.getPosition());
            }
        }
        for (Animal animal : deadAnimals) {
            animals.remove(animal);
        }
    }

    private void feedAnimals(){
        LinkedList<Plant> plantsToRemove = new LinkedList<Plant>();

        for (Plant plant : plants){
            LinkedList<Animal> toFeed = map.findAnimalsToFeed(plant.getPosition());
            if (toFeed != null) {
                for (Animal animal : toFeed) {
                    animal.feed(params.getPlantEnergy() / toFeed.size());
                    System.out.println("eaten: "+params.getPlantEnergy() / toFeed.size()+ " "+animal.getPosition() );
                }
                this.map.removePlant(plant);
                plantsToRemove.add(plant);
            }
        }

        for (Plant plant : plantsToRemove) plants.remove(plant);
    }

    private void reproduceAnimals(){
        LinkedList<LinkedList<Animal>> pairsToReproduce = map.findAllPairsToReproduce();

        System.out.println("pary do rozmnozenia: " + pairsToReproduce.size());
        for (LinkedList<Animal> parents : pairsToReproduce){
            Animal child = parents.getFirst().reproduce(parents.getLast());
            System.out.println("NEW CHILD pos: " + child.getPosition() + " energy: " + child.getEnergy() + "  parents: "
                    + parents.getFirst().getPosition());
            animals.add(child);
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

        // ile tak szukamy pozycji?
        do{
            junglePosition = map.getRandomPosition(llx,urx,lly,ury);
            ctr++;
        } while(map.isOccupied(junglePosition) && ctr < params.getHeight()*params.getWidth());

        if (ctr < params.getHeight()*params.getWidth()){
            Plant plant = new Plant(junglePosition);
            if (map.setPlant(plant)){
                plants.add(plant);
            }
        }
    }

    private void setSteppePlant(){
        int llx = map.getJungleLowerLeft(params.getWidth(),params.getHeight(),params.getJungleRatio()).x;
        int lly = map.getJungleLowerLeft(params.getWidth(),params.getHeight(),params.getJungleRatio()).y;
        int urx = map.getJungleUpperRight(params.getWidth(), params.getHeight(), params.getJungleRatio()).x;
        int ury = map.getJungleUpperRight(params.getWidth(), params.getHeight(), params.getJungleRatio()).y;

        Vector2d steppePosition;

        // ile tak szukamy pozycji?
        do {
            steppePosition = map.getRandomPosition(0, params.getWidth() - 1, 0, params.getHeight() - 1);
            if (steppePosition.x < llx || steppePosition.x > urx || steppePosition.y < lly || steppePosition.y > ury) {

            }
        } while((steppePosition.x >= llx && steppePosition.x <= urx && steppePosition.y >= lly && steppePosition.y <= ury)
                || map.isOccupied(steppePosition));

        Plant plant = new Plant(steppePosition);
        if (map.setPlant(plant)){
            plants.add(plant);
        }
    }

    private void printStatus(){
        for (Animal animal : animals) System.out.println("pos: " + animal.getPosition() + " energy: " + animal.getEnergy());
        System.out.println();
        System.out.println("plants amount: " + plants.size());
        System.out.println("animals amount: " + animals.size());

    }






}