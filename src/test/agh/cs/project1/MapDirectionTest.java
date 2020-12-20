package agh.cs.project1;

import agh.cs.project1.simulation.MapDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {

    @Test
    public void getRandomOrientationTest(){
        MapDirection direction = MapDirection.getRandomOrientation();
        assertTrue(direction.toUnitVector().x >= -1 && direction.toUnitVector().x <= 1);
        assertTrue(direction.toUnitVector().y >= -1 && direction.toUnitVector().y <= 1);
    }
}
