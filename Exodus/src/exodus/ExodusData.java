/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 12nwoodruff
 */

public class ExodusData {
    private float difficulty;
    private Island[] islands = new Island[3];
    private RocketBase base;
    private int worldPopulation;
    private float worldTime;
    private float worldEndTime;
    private float climateChange;
    private float yearLength;
    
    List<String[]> history = new ArrayList<String[]>();

    public float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }

    public Island[] getIslands() {
        return islands;
    }

    public void setIslands(Island[] islands) {
        this.islands = islands;
    }

    public RocketBase getBase() {
        return base;
    }

    public void setBase(RocketBase base) {
        this.base = base;
    }

    public int getWorldPopulation() {
        return worldPopulation;
    }

    public void setWorldPopulation(int worldPopulation) {
        this.worldPopulation = worldPopulation;
    }

    public float getWorldTime() {
        return worldTime;
    }

    public void setWorldTime(float worldTime) {
        this.worldTime = worldTime;
    }

    public float getWorldEndTime() {
        return worldEndTime;
    }

    public void setWorldEndTime(float worldEndTime) {
        this.worldEndTime = worldEndTime;
    }

    public float getClimateChange() {
        return climateChange;
    }

    public void setClimateChange(float climateChange) {
        this.climateChange = climateChange;
    }

    public float getYearLength() {
        return yearLength;
    }

    public void setYearLength(float yearLength) {
        this.yearLength = yearLength;
    }

    public List<String[]> getHistory() {
        return history;
    }

    public void setHistory(List<String[]> history) {
        this.history = history;
    }
    
    
    
    public ExodusData(float difficulty, int numberOfMinutes)
    {
        worldPopulation = 0;
        for(int i = 0; i < 3; i++)
        {
            islands[i] = new Island(difficulty);
            worldPopulation += islands[i].getPopulation();
        }
        base  = new RocketBase(difficulty);
        worldTime = 0;
        yearLength = numberOfMinutes * 60 / 100;
        worldEndTime = 100;
        climateChange = 0;
    }
    public void nextYear()
    {
        printYearlySummary();
        climateChange += 0.01f * (Math.random() - 0.5);
        //occurs every year
        for(int i = 0; i < 3; i++)
        {
            islands[i].collectTax();
            islands[i].updatePopulation();
            islands[i].setClimateChange(climateChange);
        }
        worldTime++;
        climateChange += 1 / worldEndTime;
    }
    public void movePopulation(int fromIslandIndex, int toIslandIndex, int size)
    {
        islands[fromIslandIndex].changePopulation(-size);
        islands[toIslandIndex].changePopulation(size);
        islands[fromIslandIndex].pay(10 / islands[fromIslandIndex].getGdpPerCapita());
    }
    void printYearlySummary() //Just for csv data analysis
    {
        String[] historicEntry = new String[37];
        historicEntry[0] = String.valueOf(worldPopulation);
        historicEntry[1] = String.valueOf(worldTime);
        historicEntry[2] = String.valueOf(worldEndTime);
        historicEntry[3] = String.valueOf(climateChange);
        for(int i = 0; i < islands.length; i++)
        {
            historicEntry[4+(i * 10)] = String.valueOf(islands[i].getPopulation());
            historicEntry[5+(i * 10)] = String.valueOf(islands[i].getMoney());
            historicEntry[6+(i * 10)] = String.valueOf(islands[i].getGdpPerCapita());
            historicEntry[7+(i * 10)] = String.valueOf(islands[i].getTaxRate());
            historicEntry[8+(i * 10)] = String.valueOf(islands[i].getCrimeRate());
            historicEntry[9+(i * 10)] = String.valueOf(islands[i].getFoodSecurity());
            historicEntry[10+(i * 10)] = String.valueOf(islands[i].getJobSecurity());
            historicEntry[11+(i * 10)] = String.valueOf(islands[i].getLandArea());
            historicEntry[12+(i * 10)] = String.valueOf(islands[i].getHappiness());
            historicEntry[13+(i * 10)] = String.valueOf(islands[i].getClimateChange());
        }
        history.add(historicEntry);
    }
}
