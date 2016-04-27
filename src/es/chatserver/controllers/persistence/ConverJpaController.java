/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.persistence;

import es.chatserver.controllers.persistence.exceptions.IllegalOrphanException;
import es.chatserver.controllers.persistence.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.chatserver.model.ClientConver;
import es.chatserver.model.Conver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Practicas01
 */
public class ConverJpaController implements Serializable {

    public ConverJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conver conver) {
        if (conver.getClientConverCollection() == null) {
            conver.setClientConverCollection(new ArrayList<ClientConver>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ClientConver> attachedClientConverCollection = new ArrayList<ClientConver>();
            for (ClientConver clientConverCollectionClientConverToAttach : conver.getClientConverCollection()) {
                clientConverCollectionClientConverToAttach = em.getReference(clientConverCollectionClientConverToAttach.getClass(), clientConverCollectionClientConverToAttach.getClientConverPK());
                attachedClientConverCollection.add(clientConverCollectionClientConverToAttach);
            }
            conver.setClientConverCollection(attachedClientConverCollection);
            em.persist(conver);
            for (ClientConver clientConverCollectionClientConver : conver.getClientConverCollection()) {
                Conver oldConverOfClientConverCollectionClientConver = clientConverCollectionClientConver.getConver();
                clientConverCollectionClientConver.setConver(conver);
                clientConverCollectionClientConver = em.merge(clientConverCollectionClientConver);
                if (oldConverOfClientConverCollectionClientConver != null) {
                    oldConverOfClientConverCollectionClientConver.getClientConverCollection().remove(clientConverCollectionClientConver);
                    oldConverOfClientConverCollectionClientConver = em.merge(oldConverOfClientConverCollectionClientConver);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conver conver) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conver persistentConver = em.find(Conver.class, conver.getId());
            Collection<ClientConver> clientConverCollectionOld = persistentConver.getClientConverCollection();
            Collection<ClientConver> clientConverCollectionNew = conver.getClientConverCollection();
            List<String> illegalOrphanMessages = null;
            for (ClientConver clientConverCollectionOldClientConver : clientConverCollectionOld) {
                if (!clientConverCollectionNew.contains(clientConverCollectionOldClientConver)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientConver " + clientConverCollectionOldClientConver + " since its conver field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ClientConver> attachedClientConverCollectionNew = new ArrayList<ClientConver>();
            for (ClientConver clientConverCollectionNewClientConverToAttach : clientConverCollectionNew) {
                clientConverCollectionNewClientConverToAttach = em.getReference(clientConverCollectionNewClientConverToAttach.getClass(), clientConverCollectionNewClientConverToAttach.getClientConverPK());
                attachedClientConverCollectionNew.add(clientConverCollectionNewClientConverToAttach);
            }
            clientConverCollectionNew = attachedClientConverCollectionNew;
            conver.setClientConverCollection(clientConverCollectionNew);
            conver = em.merge(conver);
            for (ClientConver clientConverCollectionNewClientConver : clientConverCollectionNew) {
                if (!clientConverCollectionOld.contains(clientConverCollectionNewClientConver)) {
                    Conver oldConverOfClientConverCollectionNewClientConver = clientConverCollectionNewClientConver.getConver();
                    clientConverCollectionNewClientConver.setConver(conver);
                    clientConverCollectionNewClientConver = em.merge(clientConverCollectionNewClientConver);
                    if (oldConverOfClientConverCollectionNewClientConver != null && !oldConverOfClientConverCollectionNewClientConver.equals(conver)) {
                        oldConverOfClientConverCollectionNewClientConver.getClientConverCollection().remove(clientConverCollectionNewClientConver);
                        oldConverOfClientConverCollectionNewClientConver = em.merge(oldConverOfClientConverCollectionNewClientConver);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = conver.getId();
                if (findConver(id) == null) {
                    throw new NonexistentEntityException("The conver with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conver conver;
            try {
                conver = em.getReference(Conver.class, id);
                conver.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conver with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClientConver> clientConverCollectionOrphanCheck = conver.getClientConverCollection();
            for (ClientConver clientConverCollectionOrphanCheckClientConver : clientConverCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Conver (" + conver + ") cannot be destroyed since the ClientConver " + clientConverCollectionOrphanCheckClientConver + " in its clientConverCollection field has a non-nullable conver field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(conver);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Conver> findConverEntities() {
        return findConverEntities(true, -1, -1);
    }

    public List<Conver> findConverEntities(int maxResults, int firstResult) {
        return findConverEntities(false, maxResults, firstResult);
    }

    private List<Conver> findConverEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conver.class));
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

    public Conver findConver(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conver.class, id);
        } finally {
            em.close();
        }
    }

    public int getConverCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conver> rt = cq.from(Conver.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
