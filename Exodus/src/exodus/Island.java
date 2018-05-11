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
    public float happiness;
    public float climateChange;
    public Island(float difficulty) 
    {
        crimeRate = 0.2f * difficulty;
        population = (int) Math.floor(100000 * difficulty);
        money = 10f * difficulty; //in bn
        gdpPerCapita = 80f * difficulty;
        taxRate = 0.15f;
        landArea = 1000f / difficulty;
        foodSecurity = 0.85f;
        jobSecurity = 0.95f;
        happiness = 0.75f;
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
        //Drought, terrorist attack, etc
    }
    public void triggerCatastrophe(int type)
    {
        //Global stock market crash, hurricane, blizzard, etc
    }
    public void updatePopulation()
    {
        crimeRate += 0.001f * (1 - jobSecurity) * (1 - foodSecurity) * (population / landArea) * (0.5 + Math.random()) * (1 - crimeRate);
        gdpPerCapita *= 1 - (crimeRate * 1f); 
        happiness *= 0.1 + (1 - crimeRate);
        if(happiness > 1)
        {
            happiness = 1;
        }
        if(happiness < 0.05)
        {
            //REVOLUTION
        }
        float random = (float) Math.random() * 10;
        if(random * 5 < 10 * climateChange)
        {
            triggerCatastrophe(0);
        }
        else if(random * 2 < 10 * climateChange)
        {
            triggerDisaster(0);
        }
    }
    public void upgradeFarms()
    {
        foodSecurity = 1 - (float) Math.pow(1 - foodSecurity, 3);
    }
    public void investInInfrastructure()
    {
        foodSecurity = 1 - (float) Math.pow(1 - foodSecurity, 2);
    }
    public void changeTaxRate(float target)
    {
        float deltaTax = target - taxRate;
        taxRate = target;
        jobSecurity -= deltaTax * 0.4;
        happiness -= deltaTax * 0.6;
    }
}
