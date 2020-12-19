package agh.cs.project1;

import agh.cs.project1.simulation.Animal;
import agh.cs.project1.simulation.Savannah;
import agh.cs.project1.simulation.Vector2d;
import org.junit.jupiter.api.Test;
import static agh.cs.project1.simulation.MapDirection.*;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    public void moveTest(){

        Savannah map = new Savannah(4,4,10);
        Animal animal = new Animal(map,new Vector2d(0,0), SOUTH,10,0);

        animal.move(0);
        assertEquals(SOUTH, animal.getOrientation());
        assertEquals(new Vector2d(0,3),animal.getPosition());

        animal.move(5);
        assertEquals(NORTH_EAST, animal.getOrientation());
        assertEquals(new Vector2d(1,0),animal.getPosition());

        animal.move(7);
        assertEquals(NORTH, animal.getOrientation());
        assertEquals(new Vector2d(1,1),animal.getPosition());
    }

    @Test
    public void feedTest(){
        Savannah map = new Savannah(3,3,5);
        Animal animal = new Animal(map,new Vector2d(0,0), SOUTH,5,0);

        animal.feed(3);
        assertEquals(8,animal.getEnergy());

        animal.feed(0);
        assertEquals(8,animal.getEnergy());
    }


}