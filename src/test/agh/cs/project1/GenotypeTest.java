package agh.cs.project1;

import agh.cs.project1.simulation.Genotype;
import com.google.gson.internal.$Gson$Preconditions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GenotypeTest {

    private final Genotype genotype = new Genotype();

    @Test
    public void generateGenotypeTest(){
        int[] g = genotype.getGenes();
        for (int gene : g) assertTrue(gene >= 0 && gene <=7);

        // no missing genes
        boolean[] present = new boolean[8];
        Arrays.fill(present, false);
        for (int gene : g) present[gene] = true;
        for (boolean p : present) assertTrue(p);
    }

    @Test
    public void chooseTurnTest(){
        int turn1 = genotype.chooseTurn();
        assertTrue(turn1 >= 0 && turn1 <= 7);

        int turn2 = genotype.chooseTurn();
        assertTrue(turn2 >= 0 && turn2 <= 7);

        int turn3 = genotype.chooseTurn();
        assertTrue(turn3 >= 0 && turn3 <= 7);
    }

    @Test
    public void crossTest(){
        Genotype g1 = new Genotype();
        Genotype g2 = new Genotype();

        int[] crossedGenes = g1.cross(g2).getGenes();

        int[] g = genotype.getGenes();
        for (int gene : g) assertTrue(gene >= 0 && gene <=7);


        boolean[] present = new boolean[8];
        Arrays.fill(present, false);
        for (int gene : crossedGenes) present[gene] = true;
        for (boolean p : present) assertTrue(p);
    }

}