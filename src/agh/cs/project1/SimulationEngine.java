package agh.cs.project1;


import java.util.ArrayList;
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
    }




}
