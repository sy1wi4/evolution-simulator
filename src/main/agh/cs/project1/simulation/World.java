package agh.cs.project1.simulation;


import agh.cs.project1.animation.Main;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class World {

    public static void main(String[] args) throws FileNotFoundException {

        // read json file
        Gson gson = new Gson();
        Parameters params =  (Parameters) gson.fromJson(new FileReader("src\\main\\agh\\cs\\project1\\simulation\\parameters.json"), Parameters.class);


        SimulationEngine engine = new SimulationEngine(params);
        Main simulation = new Main(engine,params,100);


        // TODO Szukanie miejsca np. dla rośliny w dżungli - do kiedy? Kiedy stwierdzić, że jest ona pełna?
        // TODO może by tak wydzielić funkcje np. do szukania różnych wolnych pozycji, itd?
        // co musi być w IWorldMap, a co nie?
        // bool gdy dodaję np. zwierzaka konieczny?

    }
}