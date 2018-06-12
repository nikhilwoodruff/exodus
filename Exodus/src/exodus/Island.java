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
    private float population;
    private float money;
    private float gdpPerCapita;
    private float crimeRate;
    private float foodSecurity;
    private float jobSecurity;
    private float energySecurity;
    private float landArea;
    private float happiness;
    private float climateChange;
    private float climateImpact;
    boolean[] justHitMilestone = new boolean[] {false, false, false, false, false, false, false, false};
    private float taxRate;
    private float[] budget;

    public float getClimateImpact() {
        return climateImpact;
    }

    public void setClimateImpact(float climateImpact) {
        this.climateImpact = climateImpact;
    }
    
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
    
    public float getPopulation() {
        return population;
    }

    public void setPopulation(float population) {
        this.population = population;
    }
    
    public void changePopulation(float number) {
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
        if(happiness < 0.005f)
        {
            return 0;
        }
        else
        {
            return happiness;
        }
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
        crimeRate = 0.04f * difficulty;
        population = (float) Math.floor(50 * difficulty);
        money = 1000f * difficulty; //in bn
        gdpPerCapita = 105f * difficulty;
        taxRate = 0.4f;
        landArea = 250f / difficulty;
        energySecurity = 0.97f;
        foodSecurity = 0.94f;
        jobSecurity = 0.95f;
        happiness = 0.95f;
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
    public void updatePopulation()
    {
        float popDensity = (population / landArea) / 0.05f;
        population += (Math.random() - 0.2f) * 0.005f * population / 25;
        crimeRate = addToClamped(crimeRate, crimeRate/85 + (Math.random() - 0.35f) * crimeRate/45 + popDensity * crimeRate/100);
        foodSecurity = addToClamped(foodSecurity, -foodSecurity/245 - (Math.random() - 0.8f) * foodSecurity/108 + budget[0] * 0.0015f - popDensity * 0.015f);
        energySecurity = addToClamped(energySecurity, -energySecurity/165 - (Math.random() - 0.6f) * energySecurity/116 + budget[0] * 0.0007f + budget[1] * 0.011f);
        gdpPerCapita = addToClamped(gdpPerCapita, 0.4f * ((0.045 - crimeRate) + (foodSecurity - 0.905) + (energySecurity - 0.924) + (jobSecurity - 0.9)) / 0.3, 0, 100);
        jobSecurity = addToClamped(jobSecurity, (-0.94 + foodSecurity - crimeRate) / 20);
        happiness = addToClamped(happiness, -(-crimeRate + foodSecurity + energySecurity - 1.78)/127 + (budget[0] - 0.2f) * 0.03f - taxRate * 0.02f - popDensity * 0.011f);
        float sum = 0;
        for(int i = 0; i < 4; i++)
        {
            sum += budget[i];
        }
        money -= sum * 40 * population * taxRate * 0.45f;
        
//        System.out.println("Income: " + population * taxRate * gdpPerCapita);
//        System.out.println("Expenses: " + sum * 40 * population * taxRate);
//        System.out.println("Deficit/Surplus: " + (population * taxRate * gdpPerCapita - sum * 40 * population * taxRate));
    }
    float addToClamped(float variable, double amount)
    {
        float result = variable + (float) amount;
        if(result < 0)
        {
            result = 0;
        }
        else if(result > 1)
        {
            result = 1;
        }
        return result;
    }
    float addToClamped(float variable, double amount, float min, float max)
    {
        float result = variable + (float) amount;
        if(result < min)
        {
            result = min;
        }
        else if(result > max)
        {
            result = max;
        }
        return result;
    }
    public void upgradeFarms()
    {
        foodSecurity = 1 - (float) Math.pow(1 - foodSecurity, 3);
    }
    public void changeTaxRate(float target)
    {
        float deltaTax = target - taxRate;
        taxRate = target;
        jobSecurity -= deltaTax * 0.4;
        happiness -= deltaTax * 0.6;
    }
    public void invest()
    {
        
    }
}
