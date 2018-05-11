/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class RocketBase {
    public int population;
    public int numberOfScientists;
    public Rocket rocket =  new Rocket();
    public List<RocketPart> partsInInventory;
    
    public RocketBase(float difficulty)
    {
        population = (int) Math.floor(1000 * (1-difficulty));
        numberOfScientists = (int) Math.floor(250 * (1-difficulty));
        partsInInventory = new ArrayList<>();
        if(difficulty < 0.25)
        {
            
        }
    }
}
