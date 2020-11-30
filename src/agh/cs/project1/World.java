package agh.cs.project1;

public class World {

    public static void main(String[] args) {
        MapDirection md = MapDirection.NORTH;
        System.out.println(md.previous().previous().next().previous());
    }
}