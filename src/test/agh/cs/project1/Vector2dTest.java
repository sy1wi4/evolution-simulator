package agh.cs.project1;

import agh.cs.project1.simulation.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    Vector2d v1 = new Vector2d(0,0);
    Vector2d v2 = new Vector2d(1,11);
    Vector2d v3 = new Vector2d(-1,-11);
    Vector2d v4 = new Vector2d(1,11);


    @Test
    public void addTest(){
        assertEquals(v2,v1.add(v2));
        assertEquals(v1,v3.add(v2));
    }

    @Test
    public void equalsTest(){
        assertTrue(v4.equals(v2) && v2.equals(v4));
        assertFalse(v1.equals(v2) && v2.equals(v1));
    }

    @Test
    public void hashCodeTest(){
        assertEquals(v4.hashCode(), v2.hashCode());
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }

}
