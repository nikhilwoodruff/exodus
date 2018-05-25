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
    private int population;
    private float money;
    private float gdpPerCapita;
    private float taxRate;
    private float crimeRate;
    private float foodSecurity;
    private float jobSecurity;
    private float energySecurity;
    private float landArea;
    private float happiness;
    private float climateChange;
    private float[] budget;
    
    public float[] getBudget() {
        return budget;
    }

    public void setBudget(float[] budget) {
        this.budget = budget;
    }
    
    public float getEnergySecurity() {
        return energySecurity;
    }

    public void setEnergySecurity(float energySecurity) {
        this.energySecurity = energySecurity;
    }
    
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
    
    public void changePopulation(int number) {
        this.population += number;
    }
    
    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getGdpPerCapita() {
        return gdpPerCapita;
    }

    public void setGdpPerCapita(float gdpPerCapita) {
        this.gdpPerCapita = gdpPerCapita;
    }

    public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }

    public float getCrimeRate() {
        return crimeRate;
    }

    public void setCrimeRate(float crimeRate) {
        this.crimeRate = crimeRate;
    }

    public float getFoodSecurity() {
        return foodSecurity;
    }

    public void setFoodSecurity(float foodSecurity) {
        this.foodSecurity = foodSecurity;
    }

    public float getJobSecurity() {
        return jobSecurity;
    }

    public void setJobSecurity(float jobSecurity) {
        this.jobSecurity = jobSecurity;
    }

    public float getLandArea() {
        return landArea;
    }

    public void setLandArea(float landArea) {
        this.landArea = landArea;
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }

    public float getClimateChange() {
        return climateChange;
    }

    public void setClimateChange(float climateChange) {
        this.climateChange = climateChange;
    }
    
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
        budget = new float[] {0.25f, 0.25f, 0.25f, 0.25f};
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
        crimeRate += 0.015f * (1 - jobSecurity) * (1 - foodSecurity) * (population / landArea) * (0.5 + Math.random()) * (1 - crimeRate);
        gdpPerCapita *= 1.08 - crimeRate; 
        happiness *= 1.15 - crimeRate;
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
        float sum = 0;
        for(int i = 0; i < 4; i++)
        {
            sum += budget[i];
        }
        money -= sum * gdpPerCapita * population * taxRate * 0.3f;
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
