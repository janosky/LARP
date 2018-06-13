/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestClasses;

import entities.Archtypes;
import entities.Creature;
import interfaces.GetCreatureDataInterface;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aejan
 */
public class GetCreatureData implements  GetCreatureDataInterface{
//inject Entity Manager
    @PersistenceContext(unitName = "LARPPU") 
    private EntityManager entityManager;
    
    
    @Override
    public ArrayList<String> getCreatureTypes() {
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Creature> getCreaturesByType(String creatureType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Archtypes> getArchtypesByCreature(String creatureID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
