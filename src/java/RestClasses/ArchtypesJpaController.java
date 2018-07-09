/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestClasses;

import RestClasses.exceptions.NonexistentEntityException;
import RestClasses.exceptions.PreexistingEntityException;
import RestClasses.exceptions.RollbackFailureException;
import entities.Archtypes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Creature;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author aejan
 */
public class ArchtypesJpaController implements Serializable {

    public ArchtypesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Archtypes archtypes) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Creature creatureId = archtypes.getCreatureId();
            if (creatureId != null) {
                creatureId = em.getReference(creatureId.getClass(), creatureId.getCreaturePK());
                archtypes.setCreatureId(creatureId);
            }
            em.persist(archtypes);
            if (creatureId != null) {
                creatureId.getArchtypesCollection().add(archtypes);
                creatureId = em.merge(creatureId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findArchtypes(archtypes.getArchytypeId()) != null) {
                throw new PreexistingEntityException("Archtypes " + archtypes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Archtypes archtypes) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Archtypes persistentArchtypes = em.find(Archtypes.class, archtypes.getArchytypeId());
            Creature creatureIdOld = persistentArchtypes.getCreatureId();
            Creature creatureIdNew = archtypes.getCreatureId();
            if (creatureIdNew != null) {
                creatureIdNew = em.getReference(creatureIdNew.getClass(), creatureIdNew.getCreaturePK());
                archtypes.setCreatureId(creatureIdNew);
            }
            archtypes = em.merge(archtypes);
            if (creatureIdOld != null && !creatureIdOld.equals(creatureIdNew)) {
                creatureIdOld.getArchtypesCollection().remove(archtypes);
                creatureIdOld = em.merge(creatureIdOld);
            }
            if (creatureIdNew != null && !creatureIdNew.equals(creatureIdOld)) {
                creatureIdNew.getArchtypesCollection().add(archtypes);
                creatureIdNew = em.merge(creatureIdNew);
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
                String id = archtypes.getArchytypeId();
                if (findArchtypes(id) == null) {
                    throw new NonexistentEntityException("The archtypes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Archtypes archtypes;
            try {
                archtypes = em.getReference(Archtypes.class, id);
                archtypes.getArchytypeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The archtypes with id " + id + " no longer exists.", enfe);
            }
            Creature creatureId = archtypes.getCreatureId();
            if (creatureId != null) {
                creatureId.getArchtypesCollection().remove(archtypes);
                creatureId = em.merge(creatureId);
            }
            em.remove(archtypes);
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

    public List<Archtypes> findArchtypesEntities() {
        return findArchtypesEntities(true, -1, -1);
    }

    public List<Archtypes> findArchtypesEntities(int maxResults, int firstResult) {
        return findArchtypesEntities(false, maxResults, firstResult);
    }

    private List<Archtypes> findArchtypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Archtypes.class));
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

    public Archtypes findArchtypes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Archtypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getArchtypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Archtypes> rt = cq.from(Archtypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
