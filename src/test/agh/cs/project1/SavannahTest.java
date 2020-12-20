package agh.cs.project1;

import agh.cs.project1.simulation.Animal;
import agh.cs.project1.simulation.Plant;
import agh.cs.project1.simulation.Savannah;
import agh.cs.project1.simulation.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static agh.cs.project1.simulation.MapDirection.*;
import static org.junit.jupiter.api.Assertions.*;

public class SavannahTest {

    @Test
    public void placeAnimalTest(){
        Savannah map = new Savannah(4,4,10);
        Vector2d animalPosition = new Vector2d(3,2);

        Animal animal = new Animal(map,animalPosition, SOUTH,10,0);
        map.placeAnimal(animal);

        assertTrue(map.isOccupied(animalPosition));
    }

    @Test
    public void setRemovePlantTest(){
        Savannah map = new Savannah(5,4,4);
        Vector2d plantPosition = new Vector2d(1,3);

        Plant plant = new Plant(plantPosition);
        map.setPlant(plant);

        assertTrue(map.isOccupied(plantPosition));

        map.removePlant(plant);
        assertFalse(map.isOccupied(plantPosition));
        assertNull(map.objectAt(plantPosition));

    }


    @Test
    public void objectAtTest(){
        Savannah map = new Savannah(10,10,4);

        Vector2d plantPosition = new Vector2d(5,0);
        Plant plant = new Plant(plantPosition);
        map.setPlant(plant);
        assertEquals(plant,map.objectAt(plantPosition));

        Vector2d animalPosition = new Vector2d(3,2);
        Animal animal1 = new Animal(map,animalPosition, WEST,10,0);
        assertEquals(animal1,map.objectAt(animalPosition));
        Animal animal2 = new Animal(map,animalPosition, WEST,99,0);
        Animal animal3 = new Animal(map,animalPosition, WEST,5,0);
        // most energetic animal on position
        assertEquals(animal2,map.objectAt(animalPosition));
        assertNotEquals(animal3,map.objectAt(animalPosition));
    }

    @Test
    public void getRandomPositionTest(){
        Savannah map = new Savannah(40,80,4);

        // whole map
        Vector2d position = map.getRandomPosition(0,39,0,79);
        assertTrue(position.x >= 0 && position.x < 40);
        assertTrue(position.y >= 0 && position.y < 80);
    }

    @Test
    public void isOccupiedTest(){
        Savannah map = new Savannah(10,10,4);

        Vector2d plantPosition = new Vector2d(5,0);
        assertFalse(map.isOccupied(plantPosition));
        Plant plant = new Plant(plantPosition);
        map.setPlant(plant);
        assertTrue(map.isOccupied(plantPosition));

        Vector2d animalPosition = new Vector2d(3,2);
        assertFalse(map.isOccupied(animalPosition));
        new Animal(map,animalPosition, WEST,10,0);
        assertTrue(map.isOccupied(animalPosition));
    }

    @Test
    public void getSideOfJungleTest(){
        Savannah map = new Savannah(10,10,4);
        double jungleRatio = 0.1;
        int side = map.getSideOfJungle(map.getWidth(),map.getHeight(),jungleRatio);
        assertEquals(3,side);

        Savannah map2 = new Savannah(10,3,41);
        double jungleRatio2 = 0.3;
        int side2 = map2.getSideOfJungle(map2.getWidth(),map2.getHeight(),jungleRatio2);
        assertEquals(3,side2);

    }

    @Test
    public void getJungleLowerLeftTest(){
        Savannah map = new Savannah(10,10,4);
        double jungleRatio = 0.1;
        Vector2d ll = map.getJungleLowerLeft(map.getWidth(),map.getHeight(),jungleRatio);
        assertEquals(new Vector2d(4,4),ll);

        Savannah map2 = new Savannah(10,3,41);
        double jungleRatio2 = 0.3;
        Vector2d ll2 = map2.getJungleLowerLeft(map2.getWidth(),map2.getHeight(),jungleRatio2);
        assertEquals(new Vector2d(4,0),ll2);
    }

    @Test
    public void getJungleUpperRightTest(){
        Savannah map = new Savannah(10,10,4);
        double jungleRatio = 0.1;
        Vector2d ur = map.getJungleUpperRight(map.getWidth(),map.getHeight(),jungleRatio);
        assertEquals(new Vector2d(7,7),ur);

        Savannah map2 = new Savannah(10,3,41);
        double jungleRatio2 = 0.3;
        Vector2d ur2 = map2.getJungleUpperRight(map2.getWidth(),map2.getHeight(),jungleRatio2);
        assertEquals(new Vector2d(7,3),ur2);
    }

    @Test
    public void removeAnimalFromPositionTest(){
        Savannah map = new Savannah(10,10,4);
        Vector2d animalPosition = new Vector2d(3,2);
        Animal animal1 = new Animal(map,animalPosition, NORTH_EAST,10,0);
        Animal animal2 = new Animal(map,animalPosition, WEST,99,0);
        Animal animal3 = new Animal(map,animalPosition, SOUTH,5,0);

        // no animal on given position
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> map.removeAnimalFromPosition(animal1,new Vector2d(0,5)));

        assertEquals(animal2,map.getAnimalToTrack(animalPosition));
        map.removeAnimalFromPosition(animal2,animalPosition);
        assertNotEquals(animal2,map.getAnimalToTrack(animalPosition));
        assertEquals(animal1,map.getAnimalToTrack(animalPosition));
    }

    @Test
    public void addAnimalOnPositionTest(){
        Savannah map = new Savannah(10,10,4);
        Vector2d animalPosition = new Vector2d(3,2);
        Animal animal1 = new Animal(map,animalPosition, NORTH_EAST,10,0);
        Animal animal2 = new Animal(map,new Vector2d(0,0), WEST,99,0);

        assertEquals(animal1,map.getAnimalToTrack(animalPosition));
        map.addAnimalAtPosition(animal2,animalPosition);
        assertEquals(animal2,map.getAnimalToTrack(animalPosition));
    }

    @Test
    public void findAllPairsToReproduceTest(){
        Savannah map = new Savannah(10,10,4);
        Vector2d animalPosition = new Vector2d(0,0);
        Animal animal1 = new Animal(map,animalPosition, NORTH_EAST,10,0);
        Animal animal2 = new Animal(map,animalPosition, WEST,5,0);
        Animal animal3 = new Animal(map,animalPosition, SOUTH,17,0);


        // reproduce if energy >= 2 (half of start energy)

        LinkedList<LinkedList<Animal>> actual = map.findAllPairsToReproduce();
        LinkedList<Animal> parents = new LinkedList<>();
        parents.add(animal3);
        parents.add(animal1);
        LinkedList<LinkedList<Animal>> expected = new LinkedList<>();
        expected.add(parents);
        assertEquals(expected,actual);


        animal1.setEnergy(4);
        LinkedList<LinkedList<Animal>> actual2 = map.findAllPairsToReproduce();
        LinkedList<Animal> parents2 = new LinkedList<>();
        parents2.add(animal3);
        parents2.add(animal2);
        LinkedList<LinkedList<Animal>> expected2 = new LinkedList<>();
        expected2.add(parents2);
        assertEquals(expected2,actual2);

        animal1.setEnergy(1);
        animal2.setEnergy(1);
        assertEquals(new LinkedList<LinkedList<Animal>>(),map.findAllPairsToReproduce());
    }

    @Test
    public void findAnimalsToFeedTest(){
        Savannah map = new Savannah(10,10,4);
        Vector2d position = new Vector2d(0,0);
        Animal animal1 = new Animal(map,position, NORTH_EAST,4,0);
        Animal animal2 = new Animal(map,position, WEST,4,0);
        Animal animal3 = new Animal(map,position, SOUTH,4,0);

        // feed strongest animal on position - if there is more than 1, share a plant
        assertEquals(3,map.findAnimalsToFeed(position).size());

        animal1.setEnergy(2);
        assertEquals(2,map.findAnimalsToFeed(position).size());

        animal1.move(1);
        animal2.move(1);
        animal3.move(1);
        assertNull(map.findAnimalsToFeed(position));

    }

    @Test
    public void getChildPositionTest(){
        Savannah map = new Savannah(10,10,4);
        Vector2d position = new Vector2d(5,4);
        Animal animal1 = new Animal(map,position, NORTH_EAST,4,0);
        Animal animal2 = new Animal(map,position, WEST,4,0);

        Vector2d childPosition = map.getChildPosition(position);

        assertTrue(childPosition.x >= 4 && childPosition.x <= 6);
        assertTrue(childPosition.y >= 3 && childPosition.y <= 5);
        assertFalse(childPosition.x == 5 && childPosition.y == 4);

    }

    @Test
    public void getAnimalToTrackTest(){
        Savannah map = new Savannah(10,10,4);
        Vector2d position = new Vector2d(5,4);
        Animal animal1 = new Animal(map,position, NORTH_EAST,4,0);
        Animal animal2 = new Animal(map,position, WEST,6,0);


        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> map.getAnimalToTrack(new Vector2d(1,1)));

        assertEquals(animal2,map.getAnimalToTrack(position));

        animal2.move(3);
        assertEquals(animal1,map.getAnimalToTrack(position));

    }

}
