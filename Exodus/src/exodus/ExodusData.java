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
    public float climateChange;
    public float yearLength;
    public ExodusData(float difficulty, int numberOfMinutes)
    {
        worldPopulation = 0;
        for(int i = 0; i < 3; i++)
        {
            islands[i] = new Island(difficulty);
            worldPopulation += islands[i].population;
        }
        base  = new RocketBase(difficulty);
        worldTime = 0;
        yearLength = numberOfMinutes * 60 / 100;
        worldEndTime = 100;
        climateChange = 0;
    }
    public void nextYear()
    {
        climateChange = worldTime / worldEndTime;
        //occurs every year
        for(int i = 0; i < 3; i++)
        {
            islands[i].collectTax();
            islands[i].updatePopulation();
            islands[i].climateChange = climateChange;
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
