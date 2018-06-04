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
        crimeRate = 0.2f * difficulty;
        population = (float) Math.floor(50 * difficulty);
        money = 1000f * difficulty; //in bn
        gdpPerCapita = 80f * difficulty;
        taxRate = 0.15f;
        landArea = 250f / difficulty;
        energySecurity = 0.97f;
        foodSecurity = 0.85f;
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
        population *= 1 + (Math.random() - 0.2f) * 0.005f;
        crimeRate += 0.1f * (jobSecurity / 0.85) * (foodSecurity / 0.85) * (population / landArea) * (Math.random() - 0.2f) * (1 - crimeRate);
        foodSecurity += 0.06f * (jobSecurity / 0.9) * 30 / gdpPerCapita * (Math.random() - 0.3f);
        energySecurity += 0.04f * 30 / gdpPerCapita * (Math.random() - 0.4f);
        gdpPerCapita *= ((1.1005 / crimeRate) + (1.88 / foodSecurity) + (1.97 / energySecurity)) / 3; 
        happiness *= 1.15 - crimeRate;
        if(happiness > 1)
        {
            happiness = 1;
        }
        float sum = 0;
        for(int i = 0; i < 4; i++)
        {
            sum += budget[i];
        }
        money -= sum * 40 * population * taxRate;
//        System.out.println("Income: " + population * taxRate * gdpPerCapita);
//        System.out.println("Expenses: " + sum * 40 * population * taxRate);
//        System.out.println("Deficit/Surplus: " + (population * taxRate * gdpPerCapita - sum * 40 * population * taxRate));
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
