package agh.cs.project1;

import agh.cs.project1.simulation.Genotype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenotypeTest {

    private final Genotype genotype = new Genotype();

    @Test
    public void generateGenotypeTest(){
        int[] g = genotype.getGenes();
        for (int gene : g) assertTrue(gene >= 0 && gene <=7);
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
}