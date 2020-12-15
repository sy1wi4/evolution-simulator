package agh.cs.project1;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class World {

    public static void main(String[] args) throws FileNotFoundException {
        Genotype g = new Genotype();
        // ładowanie parametrów z pliku .json
        Gson gson = new Gson();
        Parameters params =  (Parameters) gson.fromJson(new FileReader("src\\agh\\cs\\project1\\parameters.json"), Parameters.class);


        /////////////////////////////////////////////////////////////////

        IWorldMap map = new Savannah(params.getWidth(), params.getHeight(), params.getJungleRatio(),
                params.getStartEnergy());
        SimulationEngine engine = new SimulationEngine(map,params);


        // TODO Szukanie miejsca np. dla rośliny w dżungli - do kiedy? Kiedy stwierdzić, że jest ona pełna?
        // TODO może by tak wydzielić funkcje np. do szukania różnych wolnych pozycji, itd?
        // co musi być w IWorldMap, a co nie?
        // bool gdy dodaję np. zwierzaka konieczny?
        // nextDay czy cos w sim engine
        // sprawdz czy kolejka pr. dziala git - wyszukiwanie najsilniejszych itd
        // dzielenie rosliny miedzy zwierzeta int czy float>


    }
}