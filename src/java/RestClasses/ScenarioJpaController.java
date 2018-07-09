/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestClasses;

import RestClasses.exceptions.IllegalOrphanException;
import RestClasses.exceptions.NonexistentEntityException;
import RestClasses.exceptions.PreexistingEntityException;
import RestClasses.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Creature;
import entities.Scenario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aejan
 */
public class ScenarioJpaController implements Serializable {

    public ScenarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Scenario scenario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (scenario.getCreatureCollection() == null) {
            scenario.setCreatureCollection(new ArrayList<Creature>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Creature> attachedCreatureCollection = new ArrayList<Creature>();
            for (Creature creatureCollectionCreatureToAttach : scenario.getCreatureCollection()) {
                creatureCollectionCreatureToAttach = em.getReference(creatureCollectionCreatureToAttach.getClass(), creatureCollectionCreatureToAttach.getCreaturePK());
                attachedCreatureCollection.add(creatureCollectionCreatureToAttach);
            }
            scenario.setCreatureCollection(attachedCreatureCollection);
            em.persist(scenario);
            for (Creature creatureCollectionCreature : scenario.getCreatureCollection()) {
                Scenario oldScenarioOfCreatureCollectionCreature = creatureCollectionCreature.getScenario();
                creatureCollectionCreature.setScenario(scenario);
                creatureCollectionCreature = em.merge(creatureCollectionCreature);
                if (oldScenarioOfCreatureCollectionCreature != null) {
                    oldScenarioOfCreatureCollectionCreature.getCreatureCollection().remove(creatureCollectionCreature);
                    oldScenarioOfCreatureCollectionCreature = em.merge(oldScenarioOfCreatureCollectionCreature);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findScenario(scenario.getScenarioId()) != null) {
                throw new PreexistingEntityException("Scenario " + scenario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Scenario scenario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Scenario persistentScenario = em.find(Scenario.class, scenario.getScenarioId());
            Collection<Creature> creatureCollectionOld = persistentScenario.getCreatureCollection();
            Collection<Creature> creatureCollectionNew = scenario.getCreatureCollection();
            List<String> illegalOrphanMessages = null;
            for (Creature creatureCollectionOldCreature : creatureCollectionOld) {
                if (!creatureCollectionNew.contains(creatureCollectionOldCreature)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Creature " + creatureCollectionOldCreature + " since its scenario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Creature> attachedCreatureCollectionNew = new ArrayList<Creature>();
            for (Creature creatureCollectionNewCreatureToAttach : creatureCollectionNew) {
                creatureCollectionNewCreatureToAttach = em.getReference(creatureCollectionNewCreatureToAttach.getClass(), creatureCollectionNewCreatureToAttach.getCreaturePK());
                attachedCreatureCollectionNew.add(creatureCollectionNewCreatureToAttach);
            }
            creatureCollectionNew = attachedCreatureCollectionNew;
            scenario.setCreatureCollection(creatureCollectionNew);
            scenario = em.merge(scenario);
            for (Creature creatureCollectionNewCreature : creatureCollectionNew) {
                if (!creatureCollectionOld.contains(creatureCollectionNewCreature)) {
                    Scenario oldScenarioOfCreatureCollectionNewCreature = creatureCollectionNewCreature.getScenario();
                    creatureCollectionNewCreature.setScenario(scenario);
                    creatureCollectionNewCreature = em.merge(creatureCollectionNewCreature);
                    if (oldScenarioOfCreatureCollectionNewCreature != null && !oldScenarioOfCreatureCollectionNewCreature.equals(scenario)) {
                        oldScenarioOfCreatureCollectionNewCreature.getCreatureCollection().remove(creatureCollectionNewCreature);
                        oldScenarioOfCreatureCollectionNewCreature = em.merge(oldScenarioOfCreatureCollectionNewCreature);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = scenario.getScenarioId();
                if (findScenario(id) == null) {
                    throw new NonexistentEntityException("The scenario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Scenario scenario;
            try {
                scenario = em.getReference(Scenario.class, id);
                scenario.getScenarioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The scenario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Creature> creatureCollectionOrphanCheck = scenario.getCreatureCollection();
            for (Creature creatureCollectionOrphanCheckCreature : creatureCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Scenario (" + scenario + ") cannot be destroyed since the Creature " + creatureCollectionOrphanCheckCreature + " in its creatureCollection field has a non-nullable scenario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(scenario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Scenario> findScenarioEntities() {
        return findScenarioEntities(true, -1, -1);
    }

    public List<Scenario> findScenarioEntities(int maxResults, int firstResult) {
        return findScenarioEntities(false, maxResults, firstResult);
    }

    private List<Scenario> findScenarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Scenario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Scenario findScenario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Scenario.class, id);
        } finally {
            em.close();
        }
    }

    public int getScenarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Scenario> rt = cq.from(Scenario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
