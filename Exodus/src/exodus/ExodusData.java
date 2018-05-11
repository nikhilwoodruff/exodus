/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

/**
 *
 * @author 12nwoodruff
 */
public class ExodusData {
    public float difficulty;
    public Island[] islands = new Island[3];
    public RocketBase base;
    public int worldPopulation;
    public float worldTime;
    public float worldEndTime;
    public ExodusData(float difficulty)
    {
        worldPopulation = 0;
        for(int i = 0; i < 3; i++)
        {
            islands[i] = new Island(difficulty);
            worldPopulation += islands[i].population;
        }
        base  = new RocketBase(difficulty);
        worldTime = 0;
        worldEndTime = 50;
    }
    public void nextYear()
    {
        //occurs every year
        for(int i = 0; i < 3; i++)
        {
            islands[i].collectTax();
            islands[i].updatePopulation();
        }
        worldTime++;
    }
    public void movePopulation(int fromIslandIndex, int toIslandIndex, int size)
    {
        islands[fromIslandIndex].population -= size;
        islands[toIslandIndex].population += size;
        islands[fromIslandIndex].pay(10 / islands[fromIslandIndex].gdpPerCapita);
    }
}
