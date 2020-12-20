package agh.cs.project1.simulation;


import agh.cs.project1.animation.Main;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class World {

    public static void main(String[] args) throws FileNotFoundException {

        // read json file
        Gson gson = new Gson();
        Parameters params = gson.fromJson(new FileReader("src\\main\\agh\\cs\\project1\\simulation\\parameters.json"), Parameters.class);


        SimulationEngine engine1 = new SimulationEngine(params);
        SimulationEngine engine2 = new SimulationEngine(params);
        Main simulation = new Main(engine1,engine2,params);
        simulation.run();


        // TODO: dzieci i potomkowie obserwowanego na mapie ?!?!
        // TODO: zamiast dwóch obserwerów observable (?)
        // TODO: plik tekstowy ze statystykami (średnia statystyk z epok)
        // TODO: integration test


    }
}