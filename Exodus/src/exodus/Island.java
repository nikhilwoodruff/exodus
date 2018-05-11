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
        crimeRate = (int) Math.floor(0.2 * difficulty);
        population = (int) Math.floor(1000000 * difficulty);
        money = (int) Math.floor(100000000 * difficulty);
        gdpPerCapita = (int) Math.floor(60000 * difficulty);
        taxRate = 0.15f;
        landArea = (int) Math.floor(1000000 / difficulty);
    }
    public void collectTax()
    {
        money += gdpPerCapita * taxRate;
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
        crimeRate += (population / landArea) * 0.01f;
    }
}
