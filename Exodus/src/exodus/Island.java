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
public class Island {
    public int population;
    public float money;
    public float gdpPerCapita;
    public float taxRate;
    public float crimeRate;
    public float foodSecurity;
    public float jobSecurity;
    public float landArea;
    public Island(float difficulty) 
    {
        crimeRate = 0.2f * difficulty;
        population = (int) Math.floor(100000 * difficulty);
        money = 100f * difficulty; //in bn
        gdpPerCapita = 60f * difficulty;
        taxRate = 0.15f;
        landArea = 1000f / difficulty;
        foodSecurity = 0.85f;
        jobSecurity = 0.95f;
    }
    public void collectTax()
    {
        money += gdpPerCapita * taxRate * population;
    }
    public void pay(float amount)
    {
        money -= amount;
    }
    public void gain(float amount)
    {
        money += amount;
    }
    public void triggerDisaster(int type)
    {
        
    }
    public void triggerCatastrophe(int type)
    {
        
    }
    public void updatePopulation()
    {
        crimeRate += 0.0001f * (1 - jobSecurity) * (1 - foodSecurity) * (population / landArea) * (0.5 + Math.random()) * (1 - crimeRate);
        gdpPerCapita *= 1 - (crimeRate); 
    }
    public void upgradeFarms()
    {
        foodSecurity = 1 - (float) Math.pow(1 - foodSecurity, 1.5);
    }
}
