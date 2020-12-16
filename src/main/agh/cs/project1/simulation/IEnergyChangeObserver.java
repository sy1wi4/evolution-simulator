package agh.cs.project1.simulation;

public interface IEnergyChangeObserver {
    /**
     * Delete pair <oldPosition, animal> from Hashmap and add pair pair <newPosition, animal>.
     * @param animal
     *            Animal whose energy has changed.
     */
    void energyChanged(Animal animal);
}
