package agh.cs.project1;

import java.util.Random;
import java.util.Arrays;

public class Genotype {
    private final int[] genotype;
    private final Random random = new Random();

    public Genotype(){
        this.genotype = new int[32];
        generateGenotype();
    }

    public void generateGenotype(){
        for (int i=0; i<this.genotype.length; i++){
            this.genotype[i] = random.nextInt(8);
        }
        Arrays.sort(this.genotype);
    }

    // na podstawie genotypu "wybiera" obrót
    public int chooseTurn(){
        return this.genotype[random.nextInt(32)];
    }
}
