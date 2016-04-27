/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.persistence;

import es.chatserver.controllers.persistence.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.chatserver.model.Client;
import es.chatserver.model.Incidencias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Practicas01
 */
public class IncidenciasJpaController implements Serializable {

    public IncidenciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Incidencias incidencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientid = incidencias.getClientid();
            if (clientid != null) {
                clientid = em.getReference(clientid.getClass(), clientid.getId());
                incidencias.setClientid(clientid);
            }
            em.persist(incidencias);
            if (clientid != null) {
                clientid.getIncidenciasCollection().add(incidencias);
                clientid = em.merge(clientid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Incidencias incidencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidencias persistentIncidencias = em.find(Incidencias.class, incidencias.getId());
            Client clientidOld = persistentIncidencias.getClientid();
            Client clientidNew = incidencias.getClientid();
            if (clientidNew != null) {
                clientidNew = em.getReference(clientidNew.getClass(), clientidNew.getId());
                incidencias.setClientid(clientidNew);
            }
            incidencias = em.merge(incidencias);
            if (clientidOld != null && !clientidOld.equals(clientidNew)) {
                clientidOld.getIncidenciasCollection().remove(incidencias);
                clientidOld = em.merge(clientidOld);
            }
            if (clientidNew != null && !clientidNew.equals(clientidOld)) {
                clientidNew.getIncidenciasCollection().add(incidencias);
                clientidNew = em.merge(clientidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = incidencias.getId();
                if (findIncidencias(id) == null) {
                    throw new NonexistentEntityException("The incidencias with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Incidencias incidencias;
            try {
                incidencias = em.getReference(Incidencias.class, id);
                incidencias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incidencias with id " + id + " no longer exists.", enfe);
            }
            Client clientid = incidencias.getClientid();
            if (clientid != null) {
                clientid.getIncidenciasCollection().remove(incidencias);
                clientid = em.merge(clientid);
            }
            em.remove(incidencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Incidencias> findIncidenciasEntities() {
        return findIncidenciasEntities(true, -1, -1);
    }

    public List<Incidencias> findIncidenciasEntities(int maxResults, int firstResult) {
        return findIncidenciasEntities(false, maxResults, firstResult);
    }

    private List<Incidencias> findIncidenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Incidencias.class));
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

    public Incidencias findIncidencias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Incidencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncidenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Incidencias> rt = cq.from(Incidencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
