package agh.cs.project1.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Statistics {
    private int livingAnimalsNumber = 0;
    private int plantsNumber = 0;
    private int overallDeadAnimalsNumber = 0;
    private int lifespanSum = 0;
    private Genotype dominantGenotype = null;
    private final Map<Genotype,Integer> genotypeCounter = new HashMap<>();
    private int epoch = 1;

    public void nextEpoch(){
        epoch++;
    }

    public int getEpoch(){
        return this.epoch;
    }

    public void encounteredNewAnimal(Genotype genotype){
        livingAnimalsNumber++;
        addGenotype(genotype);
    }

    public void animalDied(int lifespan){
        livingAnimalsNumber--;
        overallDeadAnimalsNumber++;
        lifespanSum += lifespan;
    }

    public void newPlant(){
        plantsNumber++;
    }

    public void plantEaten(){
        plantsNumber--;
    }

    public int getAverageChildrenNumber(List<Animal> animals){
        int sum = 0;
        int size = animals.size();
        if (size == 0) return 0;
        for (Animal a : animals) sum += a.getNumberOfChildren();

        return sum/size;
    }

    public int getAverageEnergyLevel(List<Animal> animals){
        int sum = 0;
        int size = animals.size();
        if (size == 0) return 0;
        for (Animal a : animals) sum += a.getEnergy();

        return sum/size;
    }

    public int getAverageDeadAnimalsLifespan(){
        if (overallDeadAnimalsNumber == 0) return 0;
        else return lifespanSum/overallDeadAnimalsNumber;
    }

    public int getAliveAnimalsNumber(){
        return livingAnimalsNumber;
    }

    public int getPlantsNumber(){
        return plantsNumber;
    }

    public Genotype getDominantGenotype() {
        return dominantGenotype;
    }

    private void addGenotype(Genotype genotype){

        if (genotypeCounter.containsKey(genotype)) {
            genotypeCounter.put(genotype, genotypeCounter.remove(genotype) + 1);
            // current genotype has become dominant
            if (genotypeCounter.get(genotype) > genotypeCounter.get(dominantGenotype))
                dominantGenotype = genotype;
        }
        else {
            genotypeCounter.put(genotype, 1);
            if (dominantGenotype == null) dominantGenotype = genotype;
        }
    }
}
