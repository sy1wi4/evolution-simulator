package agh.cs.project1;

import java.util.Random;

public class Genotype {
    private int[] genotype;

    public Genotype(){
        this.genotype = new int[32];
        generateGenotype();
//        for (int g : genotype){
//            System.out.print(g);
//        }
    }

    public void generateGenotype(){
        Random random = new Random();
        for (int i=0; i<this.genotype.length; i++){
            this.genotype[i] = random.nextInt(8);
        }
    }
}
