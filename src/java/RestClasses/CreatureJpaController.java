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
import entities.Scenario;
import entities.Archtypes;
import entities.Creature;
import entities.CreaturePK;
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
public class CreatureJpaController implements Serializable {

    public CreatureJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Creature creature) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (creature.getCreaturePK() == null) {
            creature.setCreaturePK(new CreaturePK());
        }
        if (creature.getArchtypesCollection() == null) {
            creature.setArchtypesCollection(new ArrayList<Archtypes>());
        }
        creature.getCreaturePK().setScenarioId(creature.getScenario().getScenarioId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Scenario scenario = creature.getScenario();
            if (scenario != null) {
                scenario = em.getReference(scenario.getClass(), scenario.getScenarioId());
                creature.setScenario(scenario);
            }
            Collection<Archtypes> attachedArchtypesCollection = new ArrayList<Archtypes>();
            for (Archtypes archtypesCollectionArchtypesToAttach : creature.getArchtypesCollection()) {
                archtypesCollectionArchtypesToAttach = em.getReference(archtypesCollectionArchtypesToAttach.getClass(), archtypesCollectionArchtypesToAttach.getArchytypeId());
                attachedArchtypesCollection.add(archtypesCollectionArchtypesToAttach);
            }
            creature.setArchtypesCollection(attachedArchtypesCollection);
            em.persist(creature);
            if (scenario != null) {
                scenario.getCreatureCollection().add(creature);
                scenario = em.merge(scenario);
            }
            for (Archtypes archtypesCollectionArchtypes : creature.getArchtypesCollection()) {
                Creature oldCreatureIdOfArchtypesCollectionArchtypes = archtypesCollectionArchtypes.getCreatureId();
                archtypesCollectionArchtypes.setCreatureId(creature);
                archtypesCollectionArchtypes = em.merge(archtypesCollectionArchtypes);
                if (oldCreatureIdOfArchtypesCollectionArchtypes != null) {
                    oldCreatureIdOfArchtypesCollectionArchtypes.getArchtypesCollection().remove(archtypesCollectionArchtypes);
                    oldCreatureIdOfArchtypesCollectionArchtypes = em.merge(oldCreatureIdOfArchtypesCollectionArchtypes);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCreature(creature.getCreaturePK()) != null) {
                throw new PreexistingEntityException("Creature " + creature + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Creature creature) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        creature.getCreaturePK().setScenarioId(creature.getScenario().getScenarioId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Creature persistentCreature = em.find(Creature.class, creature.getCreaturePK());
            Scenario scenarioOld = persistentCreature.getScenario();
            Scenario scenarioNew = creature.getScenario();
            Collection<Archtypes> archtypesCollectionOld = persistentCreature.getArchtypesCollection();
            Collection<Archtypes> archtypesCollectionNew = creature.getArchtypesCollection();
            List<String> illegalOrphanMessages = null;
            for (Archtypes archtypesCollectionOldArchtypes : archtypesCollectionOld) {
                if (!archtypesCollectionNew.contains(archtypesCollectionOldArchtypes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Archtypes " + archtypesCollectionOldArchtypes + " since its creatureId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (scenarioNew != null) {
                scenarioNew = em.getReference(scenarioNew.getClass(), scenarioNew.getScenarioId());
                creature.setScenario(scenarioNew);
            }
            Collection<Archtypes> attachedArchtypesCollectionNew = new ArrayList<Archtypes>();
            for (Archtypes archtypesCollectionNewArchtypesToAttach : archtypesCollectionNew) {
                archtypesCollectionNewArchtypesToAttach = em.getReference(archtypesCollectionNewArchtypesToAttach.getClass(), archtypesCollectionNewArchtypesToAttach.getArchytypeId());
                attachedArchtypesCollectionNew.add(archtypesCollectionNewArchtypesToAttach);
            }
            archtypesCollectionNew = attachedArchtypesCollectionNew;
            creature.setArchtypesCollection(archtypesCollectionNew);
            creature = em.merge(creature);
            if (scenarioOld != null && !scenarioOld.equals(scenarioNew)) {
                scenarioOld.getCreatureCollection().remove(creature);
                scenarioOld = em.merge(scenarioOld);
            }
            if (scenarioNew != null && !scenarioNew.equals(scenarioOld)) {
                scenarioNew.getCreatureCollection().add(creature);
                scenarioNew = em.merge(scenarioNew);
            }
            for (Archtypes archtypesCollectionNewArchtypes : archtypesCollectionNew) {
                if (!archtypesCollectionOld.contains(archtypesCollectionNewArchtypes)) {
                    Creature oldCreatureIdOfArchtypesCollectionNewArchtypes = archtypesCollectionNewArchtypes.getCreatureId();
                    archtypesCollectionNewArchtypes.setCreatureId(creature);
                    archtypesCollectionNewArchtypes = em.merge(archtypesCollectionNewArchtypes);
                    if (oldCreatureIdOfArchtypesCollectionNewArchtypes != null && !oldCreatureIdOfArchtypesCollectionNewArchtypes.equals(creature)) {
                        oldCreatureIdOfArchtypesCollectionNewArchtypes.getArchtypesCollection().remove(archtypesCollectionNewArchtypes);
                        oldCreatureIdOfArchtypesCollectionNewArchtypes = em.merge(oldCreatureIdOfArchtypesCollectionNewArchtypes);
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
                CreaturePK id = creature.getCreaturePK();
                if (findCreature(id) == null) {
                    throw new NonexistentEntityException("The creature with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CreaturePK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Creature creature;
            try {
                creature = em.getReference(Creature.class, id);
                creature.getCreaturePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The creature with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Archtypes> archtypesCollectionOrphanCheck = creature.getArchtypesCollection();
            for (Archtypes archtypesCollectionOrphanCheckArchtypes : archtypesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Creature (" + creature + ") cannot be destroyed since the Archtypes " + archtypesCollectionOrphanCheckArchtypes + " in its archtypesCollection field has a non-nullable creatureId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Scenario scenario = creature.getScenario();
            if (scenario != null) {
                scenario.getCreatureCollection().remove(creature);
                scenario = em.merge(scenario);
            }
            em.remove(creature);
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

    public List<Creature> findCreatureEntities() {
        return findCreatureEntities(true, -1, -1);
    }

    public List<Creature> findCreatureEntities(int maxResults, int firstResult) {
        return findCreatureEntities(false, maxResults, firstResult);
    }

    private List<Creature> findCreatureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Creature.class));
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

    public Creature findCreature(CreaturePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Creature.class, id);
        } finally {
            em.close();
        }
    }

    public int getCreatureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Creature> rt = cq.from(Creature.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
