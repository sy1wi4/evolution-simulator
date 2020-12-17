package agh.cs.project1.simulation;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Genotype {
    private final int[] genes;
    private final Random random = new Random();

    // random genes
    public Genotype(){
        this.genes = new int[32];
        generateGenotype();
    }

    // genes inherited from parents
    public Genotype(int[] genes){
        this.genes = genes;
    }

    private void generateGenotype(){
        for (int i = 0; i<this.genes.length; i++){
            this.genes[i] = random.nextInt(8);
        }

        int missing = findMissing(genes);
        int idxToReplace;

        while (missing != -1){
            idxToReplace = random.nextInt(32);
            genes[idxToReplace] = missing;
            missing = findMissing(genes);
        }

        Arrays.sort(genes);

    }

    // genotype describes probability of turn in given direction
    public int chooseTurn(){
        return this.genes[random.nextInt(32)];
    }

    public int[] getGenes(){
        return this.genes;
    }

    public Genotype cross(Genotype other) {
        int[] childGenes = new int[32];
        int[] firstParentGenes = this.getGenes();
        int[] secondParentGenes = other.getGenes();

        // divide genes into three parts
        int idx1 = random.nextInt(31);
        int idx2 = random.nextInt(31);
        while (idx1 == idx2) idx2 = random.nextInt(31);

        if (idx1 > idx2) {
            int tmp = idx1;
            idx1 = idx2;
            idx2 = tmp;
        }

        // two parts from one of the parents - which?
        boolean twoFromFirstParent = random.nextBoolean();

        // which part from parent who gives one part
        int whichPart = random.nextInt(3);

        if (!twoFromFirstParent) {
            int[] tmp = firstParentGenes;
            firstParentGenes = secondParentGenes;
            secondParentGenes = tmp;
        }

        // length of first part: idx1 + 1
        // length of second part: idx2 - idx1
        // length of third part: 31 - idx2

        switch (whichPart) {
            case 0 -> {
                System.arraycopy(secondParentGenes, 0, childGenes, 0, idx1 + 1);
                System.arraycopy(firstParentGenes, idx1 + 1, childGenes, idx1 + 1, idx2 -idx1);
                System.arraycopy(firstParentGenes, idx2 + 1, childGenes, idx2 + 1, 31 - idx2);

            }
            case 1 -> {
                System.arraycopy(firstParentGenes, 0, childGenes, 0, idx1 + 1);
                System.arraycopy(secondParentGenes, idx1 + 1, childGenes, idx1 + 1, idx2 -idx1);
                System.arraycopy(firstParentGenes, idx2 + 1, childGenes, idx2 + 1, 31 - idx2);
            }
            case 2 -> {
                System.arraycopy(firstParentGenes, 0, childGenes, 0, idx1 + 1);
                System.arraycopy(firstParentGenes, idx1 + 1, childGenes, idx1 + 1, idx2 -idx1);
                System.arraycopy(secondParentGenes, idx2 + 1, childGenes, idx2 + 1, 31 - idx2);
            }
            default -> {
                throw new IllegalArgumentException("Part must be 0, 1 or 2");

            }
        }


        int missing = findMissing(childGenes);
        int idxToReplace;

        while (missing != -1){
            idxToReplace = random.nextInt(32);
            childGenes[idxToReplace] = missing;
            missing = findMissing(childGenes);
        }

        Arrays.sort(childGenes);

        return new Genotype(childGenes);
    }


    private int findMissing(int[] genes){
        boolean[] exist = new boolean[8];
        for (int i=0; i<8; i++){
            exist[i] = false;
        }

        for (int g : genes) exist[g] = true;

        for (int i=0; i<8; i++){
            if (!exist[i]){
                return i;
            }
        }
        return -1;
    }
}
