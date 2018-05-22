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
    float difficulty;
    Island[] islands = new Island[3];
    RocketBase base;
    int worldPopulation;
    float worldTime;
    float worldEndTime;
    float climateChange;
    float yearLength;
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
        printYearlySummary();
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
    void printYearlySummary() //Just for csv data analysis
    {
        String[] historicEntry = new String[37];
        historicEntry[0] = String.valueOf(worldPopulation);
        historicEntry[1] = String.valueOf(worldTime);
        historicEntry[2] = String.valueOf(worldEndTime);
        historicEntry[3] = String.valueOf(climateChange);
        for(int i = 0; i < islands.length; i++)
        {
            historicEntry[4+(i * 10)] = String.valueOf(islands[i].population);
            historicEntry[5+(i * 10)] = String.valueOf(islands[i].money);
            historicEntry[6+(i * 10)] = String.valueOf(islands[i].gdpPerCapita);
            historicEntry[7+(i * 10)] = String.valueOf(islands[i].taxRate);
            historicEntry[8+(i * 10)] = String.valueOf(islands[i].crimeRate);
            historicEntry[9+(i * 10)] = String.valueOf(islands[i].foodSecurity);
            historicEntry[10+(i * 10)] = String.valueOf(islands[i].jobSecurity);
            historicEntry[11+(i * 10)] = String.valueOf(islands[i].landArea);
            historicEntry[12+(i * 10)] = String.valueOf(islands[i].happiness);
            historicEntry[13+(i * 10)] = String.valueOf(islands[i].climateChange);
        }
        history.add(historicEntry);
    }
}
