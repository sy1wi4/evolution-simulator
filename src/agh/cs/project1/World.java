package agh.cs.project1;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class World {

    public static void main(String[] args) throws FileNotFoundException {
        Genotype g = new Genotype();
        // ładowanie parametrów z pliku .json
        Gson gson = new Gson();
        Parameters params =  (Parameters) gson.fromJson(new FileReader("src\\agh\\cs\\project1\\parameters.json"), Parameters.class);


        /////////////////////////////////////////////////////////////////

        IWorldMap map = new Map(params.getWidth(), params.getHeight(), params.getJungleRatio(),
                params.getStartEnergy());
        SimulationEngine engine = new SimulationEngine(map,params);
        System.out.println(map.toString());


        // TODO Szukanie miejsca np. dla rośliny w dżungli - do kiedy? Kiedy stwierdzić, że jest ona pełna?

    }
}