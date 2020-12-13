package agh.cs.project1;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// implements IEngine?
public class SimulationEngine{
    private final IWorldMap map;
    // potrzebne?
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
        setPlants();
        System.out.println(map.getJungleLowerLeft(params.getWidth(),params.getHeight(),params.getJungleRatio()).toString());
        System.out.println(map.getJungleUpperRight(params.getWidth(),params.getHeight(),params.getJungleRatio()).toString());

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

            MapDirection orientation = MapDirection.values()[random.nextInt(8)];
            Animal animal = new Animal(map, position,orientation,params.getStartEnergy());
            map.place(animal);
        }
    }

    // codziennie 2 nowe rośliny w każdej ze stref
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
            map.setPlant(plant);
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
        map.setPlant(plant);
    }

}
