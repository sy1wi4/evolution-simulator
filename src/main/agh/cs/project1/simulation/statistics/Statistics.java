package agh.cs.project1.simulation.statistics;

import agh.cs.project1.simulation.classes.Animal;
import agh.cs.project1.simulation.classes.Genotype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Statistics {
    private int aliveAnimalsNumber = 0;
    private int plantsNumber = 0;
    private int overallDeadAnimalsNumber = 0;
    private int lifespanSum = 0;
    private Genotype dominantGenotypeInEpoch = null;
    private int epoch = 1;
    private int dominantCtrInEpoch = 0;

    // to get averaged statistics from epochs
    private final Map<Genotype,Integer> genotypeCounterThroughEpochs = new HashMap<>();
    private Genotype dominantGenotypeThroughEpochs = null;
    private int aliveAnimalsInEpochSum = 0;  // sum of alive animals on map every day
    private int plantInEpochSum = 0;   // sum of plants on map every day
    private int avgEnergySum = 0;
    private int avgChildrenNumberSum = 0;


    public void nextEpoch(){
        epoch++;
    }

    public int getEpoch(){
        return this.epoch;
    }

    public void encounteredNewAnimal(Genotype genotype){
        aliveAnimalsNumber++;
        addGenotype(genotype);
    }

    public void animalDied(int lifespan){
        aliveAnimalsNumber--;
        overallDeadAnimalsNumber++;
        lifespanSum += lifespan;
    }

    public void newPlant(){
        plantsNumber++;
    }

    public void plantEaten(){
        plantsNumber--;
    }

    // per epoch
    public int getAverageChildrenNumber(List<Animal> animals){
        int sum = 0;
        int size = animals.size();
        if (size == 0) return 0;
        for (Animal a : animals) sum += a.getNumberOfChildren();
        avgChildrenNumberSum += sum/size;
        return sum/size;
    }

    // per epoch
    public int getAverageEnergyLevel(List<Animal> animals){
        int sum = 0;
        int size = animals.size();
        if (size == 0) return 0;
        for (Animal a : animals) sum += a.getEnergy();
        avgEnergySum += sum/size;
        return sum/size;
    }

    public int getAverageDeadAnimalsLifespan(){
        if (overallDeadAnimalsNumber == 0) return 0;
        else return lifespanSum/overallDeadAnimalsNumber;
    }

    public int getAliveAnimalsNumber(){
        aliveAnimalsInEpochSum += aliveAnimalsNumber;
        return aliveAnimalsNumber;
    }

    public int getPlantsNumber(){
        plantInEpochSum += plantsNumber;
        return plantsNumber;
    }

    public Genotype getDominantGenotypeInEpoch(List<Animal> aliveAnimals) {
        Map<Genotype,Integer> genotypeCounter = new HashMap<>();
        this.dominantCtrInEpoch = 0;
        this.dominantGenotypeInEpoch = null;
        for (Animal animal : aliveAnimals)
            addGenotypeInEpoch(animal.getGenotype(),genotypeCounter);
        return dominantGenotypeInEpoch;
    }

    private void addGenotypeInEpoch(Genotype genotype, Map<Genotype,Integer> genotypeCounter){

        if (genotypeCounter.containsKey(genotype)) {
            int ctr = genotypeCounter.remove(genotype);
            genotypeCounter.put(genotype, ctr + 1);

            // current genotype has become dominant
            if (ctr + 1 > dominantCtrInEpoch) {
                dominantGenotypeInEpoch = genotype;
                dominantCtrInEpoch = ctr + 1;
            }
        }
        else {
            genotypeCounter.put(genotype, 1);
            if (dominantGenotypeInEpoch == null) {
                dominantGenotypeInEpoch = genotype;
                dominantCtrInEpoch = 1;
            }
        }
    }


    // without division into epochs
    private void addGenotype(Genotype genotype){

        if (genotypeCounterThroughEpochs.containsKey(genotype)) {
            genotypeCounterThroughEpochs.put(genotype, genotypeCounterThroughEpochs.remove(genotype) + 1);
            // current genotype has become dominant
            if (genotypeCounterThroughEpochs.get(genotype) > genotypeCounterThroughEpochs.get(dominantGenotypeThroughEpochs))
                dominantGenotypeThroughEpochs = genotype;
        }
        else {
            genotypeCounterThroughEpochs.put(genotype, 1);
            if (dominantGenotypeThroughEpochs == null) dominantGenotypeThroughEpochs = genotype;
        }
    }


    public int getAvgAnimalsNumberThroughEpochs(){
        return this.aliveAnimalsInEpochSum/this.epoch;
    }

    public int getAvgPlantsNumberThroughEpochs(){
        return this.plantInEpochSum/this.epoch;
    }

    public Genotype getDominantGenotypeThroughEpochs() {
        return dominantGenotypeThroughEpochs;
    }

    public int getAvgEnergyLevelThroughEpochs(){
        return this.avgEnergySum/this.epoch;
    }

    public int getAvgChildrenNumberThroughEpochs(){
        return this.avgChildrenNumberSum/this.epoch;
    }

}
