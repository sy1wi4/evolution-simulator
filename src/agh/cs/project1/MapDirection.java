package agh.cs.project1;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;


    public String toString(){
        switch(this) {
            case NORTH: return "N";
            case SOUTH: return "S";
            case WEST: return "W";
            case EAST: return "E";
            case NORTH_EAST: return "NE";
            case SOUTH_EAST: return "SE";
            case SOUTH_WEST: return "SW";
            case NORTH_WEST: return "NW";
            default: return null;
        }
    }

    // czy to się przyda??
    public MapDirection next(){
        int idx = this.ordinal();
        return this.values()[(idx + 1) % 8];
    }

    public MapDirection previous(){
        int idx = this.ordinal();
        return this.values()[(idx + 7) % 8];
    }

    public Vector2d toUnitVector(){
        switch(this) {
            case NORTH: return new Vector2d(0, 1);
            case SOUTH: return new Vector2d(0, -1);
            case WEST: return new Vector2d(-1, 0);
            case EAST: return new Vector2d(1, 0);
            case NORTH_EAST: return new Vector2d(1, 1);
            case SOUTH_EAST: return new Vector2d(1, -1);
            case SOUTH_WEST: return new Vector2d(-1, -1);
            case NORTH_WEST: return new Vector2d(-1, 1);
            default: return null;
        }
    }

}