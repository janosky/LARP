/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestClasses;

import entities.Archtypes;
import entities.Creature;
import entities.Creature_;
import interfaces.GetCreatureDataInterface;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author aejan
 */


@Stateless
@Path("getCreatureData")
@LocalBean
public class GetCreatureData implements  GetCreatureDataInterface{
//inject Entity Manager
 // @PersistenceContext(unitName = "LARPPU") 
    String puName = "LARPPU";
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(puName);
    private EntityManager entityManager = emf.createEntityManager();
    
    
    @Override
    @GET 
    @Path("getCreatureTypes")
    @Produces("application/json")
    public List<String> getCreatureTypes() {
      TypedQuery<Creature> query = entityManager.createNamedQuery("Creature.findAll",Creature.class);
       
    //    Query query = entityManager.createNativeQuery("SELECT c FROM Creature c", Creature.class);
        
        List<Creature> creatures = query.getResultList();
        
       List<String> creatureTypes = new LinkedList<>();
       
       while(!creatures.isEmpty())
       {
           creatureTypes.add(creatures.remove(0).getCreatureType());
       }
               
        return creatureTypes;
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
