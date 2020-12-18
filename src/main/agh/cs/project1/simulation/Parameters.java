package agh.cs.project1.simulation;

public class Parameters {

    private int width;
    private int height;
    private int startEnergy;
    private int plantEnergy;
    private int numberOfAnimals;
    private double jungleRatio;  // less than 0.5
    private int delay;  // epoch duration in milliseconds


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public double getJungleRatio() {

        if (jungleRatio > 0.5)  throw new IllegalArgumentException("Jungle ratio should be max 1/2! Change parameters");
        return jungleRatio;
    }

    public int getDelay(){
        return delay;
    }

}
