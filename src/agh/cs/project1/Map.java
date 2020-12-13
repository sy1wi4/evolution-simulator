package agh.cs.project1;

import java.util.LinkedHashMap;
import java.util.Random;

public class Map implements IWorldMap,IPositionChangeObserver{

    private final LinkedHashMap<Vector2d,Animal> animals;
    private final LinkedHashMap<Vector2d,Plant> plants;
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final int width;
    private final int height;
    private final Random random = new Random();

    public Map(int width, int height, double jungleRatio, int startEnergy){
        this.width = width;
        this.height = height;
        this.animals  = new LinkedHashMap<>();
        this.plants  = new LinkedHashMap<>();
    }


    @Override
    public boolean place(Animal animal) {
        animals.put(animal.getPosition(),animal);
        // dodając zwierzę, rejestrujemy mapę jako jego obserwatora
        animal.addObserver(this);
        return true;
    }

    @Override
    public boolean setPlant(Plant plant) {
        plants.put(plant.getPosition(),plant);
        return true;
    }


    @Override
    public Object objectAt(Vector2d position) {
        // zmień
        if (animals.get(position) == null){
            return plants.get(position);
        }
        return animals.get(position);
    }

    @Override
    public Vector2d getRandomPosition(int minX, int maxX, int minY, int maxY) {
        int x = random.nextInt(maxX-minX + 1);
        int y = random.nextInt(maxY-minY + 1);
        Vector2d position = new Vector2d(minX + x,minY + y);
        return position;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


    // długość boku dżungli
    private int getSideOfJungle(int width, int height, double jungleRatio){
        int a = (int)Math.sqrt(width*height*jungleRatio);
        return a;
    }

    @Override
    public Vector2d getJungleLowerLeft(int width, int height, double jungleRatio) {
        int a = getSideOfJungle(width, height, jungleRatio);
        int x = width/2 - a/2;
        int y = height/2 - a/2;
        return(new Vector2d(x,y));
    }

    @Override
    public Vector2d getJungleUpperRight(int width, int height, double jungleRatio) {
        int llx = getJungleLowerLeft(width, height, jungleRatio).x;
        int lly = getJungleLowerLeft(width, height, jungleRatio).y;
        int a = getSideOfJungle(width, height, jungleRatio);

        int x = llx + a;
        int y = lly + a;
        return(new Vector2d(x,y));
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

    public String toString(){
        return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(width-1,height-1));
    }

}
