package agh.cs.project1.simulation;

import java.util.Objects;

public class Vector2d {

    // zmiennej finalnej można tylko raz przypisać wartość
    public final int x;
    public final int y;

    // konstruktor
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return String.format("(%d, %d)",x ,y);
    }



    public Vector2d add(Vector2d other){
        if (other == null) return this;
        return new Vector2d(this.x + other.x, this.y + other.y);
    }


    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;

        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
