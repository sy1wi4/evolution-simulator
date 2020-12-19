package agh.cs.project1;

import agh.cs.project1.simulation.Animal;
import agh.cs.project1.simulation.MapDirection;
import agh.cs.project1.simulation.Savannah;
import agh.cs.project1.simulation.Vector2d;
import org.junit.jupiter.api.Test;

import java.awt.*;

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

    @Test
    public void setColorTest(){
        Savannah map = new Savannah(3,3,5);
        Animal animal = new Animal(map,new Vector2d(0,0), SOUTH,6,0);

        Color green = new Color(36, 141, 75);
        Color orange = new Color(219, 145, 28);
        Color red = new Color(210, 10, 35);

        animal.move(1);
        animal.move(1);
        assertEquals(animal.setColor(), green);
        animal.move(1);
        assertEquals(animal.setColor(), orange);
        animal.move(1);
        animal.move(1);
        assertEquals(animal.setColor(), red);

    }

    @Test
    public void getOrientationTest(){
        Savannah map = new Savannah(300,300,5);
        Animal animal = new Animal(map,new Vector2d(10,99), SOUTH,8,0);

        assertEquals(animal.getOrientation(), SOUTH);
        animal.move(2);
        assertEquals(animal.getOrientation(), WEST);
    }

    @Test
    public void getPositionTest(){
        Savannah map = new Savannah(300,300,5);
        Animal animal = new Animal(map,new Vector2d(10,99), SOUTH,8,0);

        assertEquals(animal.getPosition(),new Vector2d(10,99));
        animal.move(2);
        assertEquals(animal.getPosition(),new Vector2d(9,99));
    }

    @Test
    public void chooseTurnTest(){
        Savannah map = new Savannah(2,2,5);
        Animal animal = new Animal(map,new Vector2d(0,1), NORTH_EAST,8,0);
        int turn = animal.chooseTurn();
        assertTrue(turn >= 0 && turn <= 7);
    }

    @Test
    public void reproduceTest(){
        Savannah map = new Savannah(5,4,5);
        Animal animal1 = new Animal(map,new Vector2d(2,2), NORTH_EAST,8,0);
        Animal animal2 = new Animal(map,new Vector2d(2,2), SOUTH,3,0);

        // child should be placed around parents
        Animal child = animal1.reproduce(animal2,2);
        Vector2d childPosition = child.getPosition();
        assertTrue(childPosition.x >= 1 && childPosition.x <= 3);
        assertTrue(childPosition.y >= 1 && childPosition.y <= 3);
        assertTrue(childPosition.x != 2 || childPosition.y != 2);

        // animal get 1/4 energy from both parents
        assertEquals(child.getEnergy(), 2);
    }
}