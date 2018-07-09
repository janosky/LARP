/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestClasses;

import entities.Archtypes;
import entities.Creature;
import entities.Creature_;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author aejan
 */


@Stateless
@Path("getCreatureData")
//@LocalBean
public class GetCreatureData  implements  GetCreatureDataInterface  {
//inject Entity Manager
    
    public static final String PU_NAME = "LARPPU";
 @PersistenceContext(unitName = PU_NAME) 
  protected EntityManager entityManager;
   // EntityManagerFactory emf = Persistence.createEntityManagerFactory(puName);
  //  private EntityManager entityManager = emf.createEntityManager();
    Class resultClass;
    
    @Override
    @GET 
    @Path("getCreatureTypes")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<String> getCreatureTypes() {
      TypedQuery<Creature> query = entityManager.createNamedQuery("Creature.findAll",Creature.class);
       
    //    Query query = entityManager.createNativeQuery("SELECT c FROM Creature c", Creature.class);
        
        List<Creature> creatures = query.getResultList();
        
       LinkedList<String> creatureTypes = new LinkedList<>();
       
       while(!creatures.isEmpty())
       {
           creatureTypes.add(creatures.remove(0).getCreatureType());
       }
               
        return creatureTypes;
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     
    @GET 
    @Path("String")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloWorld() {
    // return entityManager.createNamedQuery("Creature.findByCreatureType", Creature.class).setParameter("creatureType", creatureType).getResultList();
      return "HelloWorld";
        
      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    @GET 
    @Path("getCreatureByType/{type}")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Creature> getCreaturesByType(@PathParam("type") String creatureType) {
     return entityManager.createNamedQuery("Creature.findByCreatureType", Creature.class).setParameter("creatureType", creatureType).getResultList();
      
        
      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Creature getCreatureByID(String creatureID) {
        return entityManager.createNamedQuery("Creature.findByCreatureId",Creature.class).setParameter("creatureId", creatureID).getSingleResult();
    }

    @Override
    public List<Archtypes> getArchtypesByCreature(String creatureID) {
        return entityManager.createNamedQuery("Archtypes.findCreatureID", Archtypes.class).setParameter("creatureId", creatureID).getResultList();
    }
    
    
    
    
}
