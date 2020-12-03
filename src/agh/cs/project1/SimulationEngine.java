package agh.cs.project1;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SimulationEngine{
    private final IWorldMap map;
    private final List<Animal> animals;
    private final List<Plant> plants;
    private Parameters params;

    private final Random random = new Random();


    public SimulationEngine(IWorldMap map, Parameters params){
        this.map = map;
        this.params = params;
        this.animals  = new ArrayList<>();
        this.plants = new ArrayList<>();

    }

    private void placeFirstAnimals(int numberOfAnimals){
        for (int i=0; i<params.getNumberOfAnimals(); i++){
            Vector2d position;
            do {
                position = map.getRandomPosition(params.getWidth(), params.getHeight());

            } while(map.canPlaceFirstAnimal(position));

            MapDirection orientation = MapDirection.values()[random.nextInt(8)];
            new Animal(this.map, position, orientation, params.getStartEnergy());
        }
    }

    private void plantJungle(){

    }

}
