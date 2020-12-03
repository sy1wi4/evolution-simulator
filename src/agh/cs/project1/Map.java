package agh.cs.project1;

public class Map extends AbstractWorldMap{


    @Override
    public boolean place(Animal animal) {
        return false;
    }

    // dokończ
    @Override
    public boolean canPlaceFirstAnimal(Vector2d position) {
        return false;
    }
}
