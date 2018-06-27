package interfaces;


import entities.Archtypes;
import entities.Creature;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aejan
 */
public interface GetCreatureDataInterface {
    
  
    public  List<String> getCreatureTypes();
    public ArrayList<Creature> getCreaturesByType(String creatureType);
    public ArrayList<Archtypes> getArchtypesByCreature(String creatureID);
    
}
