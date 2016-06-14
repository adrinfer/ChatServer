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
import es.chatserver.model.ClientConver;
import es.chatserver.model.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author adrinfer
 */
public class MessageJpaController implements Serializable {

    public MessageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Message message) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientConver clientConver = message.getClientConver();
            if (clientConver != null) {
                clientConver = em.getReference(clientConver.getClass(), clientConver.getClientConverPK());
                message.setClientConver(clientConver);
            }
            em.persist(message);
            if (clientConver != null) {
                clientConver.getMessageCollection().add(message);
                clientConver = em.merge(clientConver);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Message message) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Message persistentMessage = em.find(Message.class, message.getId());
            ClientConver clientConverOld = persistentMessage.getClientConver();
            ClientConver clientConverNew = message.getClientConver();
            if (clientConverNew != null) {
                clientConverNew = em.getReference(clientConverNew.getClass(), clientConverNew.getClientConverPK());
                message.setClientConver(clientConverNew);
            }
            message = em.merge(message);
            if (clientConverOld != null && !clientConverOld.equals(clientConverNew)) {
                clientConverOld.getMessageCollection().remove(message);
                clientConverOld = em.merge(clientConverOld);
            }
            if (clientConverNew != null && !clientConverNew.equals(clientConverOld)) {
                clientConverNew.getMessageCollection().add(message);
                clientConverNew = em.merge(clientConverNew);
            }
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = message.getId();
                if (findMessage(id) == null) {
                    throw new NonexistentEntityException("The message with id " + id + " no longer exists.");
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
            Message message;
            try {
                message = em.getReference(Message.class, id);
                message.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The message with id " + id + " no longer exists.", enfe);
            }
            ClientConver clientConver = message.getClientConver();
            if (clientConver != null) {
                clientConver.getMessageCollection().remove(message);
                clientConver = em.merge(clientConver);
            }
            em.remove(message);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Message> findMessageEntities() {
        return findMessageEntities(true, -1, -1);
    }

    public List<Message> findMessageEntities(int maxResults, int firstResult) {
        return findMessageEntities(false, maxResults, firstResult);
    }

    private List<Message> findMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Message.class));
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

    public Message findMessage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Message.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Message> rt = cq.from(Message.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Message> findMessagesFilterByConver(int converid)
    {
        EntityManager em = getEntityManager();
        //String query = "SELECT NEW CustomObject(i.firstProperty, i.secondProperty) FROM ObjectName i WHERE i.id=10";
        Query query = em.createNamedQuery("Message.findByConverId");
        query.setParameter("converid", converid);
        
        return query.getResultList();
    }
    
}
