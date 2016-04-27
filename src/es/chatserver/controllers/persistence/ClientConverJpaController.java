/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.persistence;

import es.chatserver.controllers.persistence.exceptions.NonexistentEntityException;
import es.chatserver.controllers.persistence.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.chatserver.model.Client;
import es.chatserver.model.ClientConver;
import es.chatserver.model.ClientConverPK;
import es.chatserver.model.Conver;
import es.chatserver.model.Message;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Practicas01
 */
public class ClientConverJpaController implements Serializable {

    public ClientConverJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientConver clientConver) throws PreexistingEntityException, Exception {
        if (clientConver.getClientConverPK() == null) {
            clientConver.setClientConverPK(new ClientConverPK());
        }
        if (clientConver.getMessageCollection() == null) {
            clientConver.setMessageCollection(new ArrayList<Message>());
        }
        clientConver.getClientConverPK().setClientid(clientConver.getClient().getId());
        clientConver.getClientConverPK().setConverid(clientConver.getConver().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client = clientConver.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getId());
                clientConver.setClient(client);
            }
            Conver conver = clientConver.getConver();
            if (conver != null) {
                conver = em.getReference(conver.getClass(), conver.getId());
                clientConver.setConver(conver);
            }
            Collection<Message> attachedMessageCollection = new ArrayList<Message>();
            for (Message messageCollectionMessageToAttach : clientConver.getMessageCollection()) {
                messageCollectionMessageToAttach = em.getReference(messageCollectionMessageToAttach.getClass(), messageCollectionMessageToAttach.getId());
                attachedMessageCollection.add(messageCollectionMessageToAttach);
            }
            clientConver.setMessageCollection(attachedMessageCollection);
            em.persist(clientConver);
            if (client != null) {
                client.getClientConverCollection().add(clientConver);
                client = em.merge(client);
            }
            if (conver != null) {
                conver.getClientConverCollection().add(clientConver);
                conver = em.merge(conver);
            }
            for (Message messageCollectionMessage : clientConver.getMessageCollection()) {
                ClientConver oldClientConverOfMessageCollectionMessage = messageCollectionMessage.getClientConver();
                messageCollectionMessage.setClientConver(clientConver);
                messageCollectionMessage = em.merge(messageCollectionMessage);
                if (oldClientConverOfMessageCollectionMessage != null) {
                    oldClientConverOfMessageCollectionMessage.getMessageCollection().remove(messageCollectionMessage);
                    oldClientConverOfMessageCollectionMessage = em.merge(oldClientConverOfMessageCollectionMessage);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientConver(clientConver.getClientConverPK()) != null) {
                throw new PreexistingEntityException("ClientConver " + clientConver + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientConver clientConver) throws NonexistentEntityException, Exception {
        clientConver.getClientConverPK().setClientid(clientConver.getClient().getId());
        clientConver.getClientConverPK().setConverid(clientConver.getConver().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientConver persistentClientConver = em.find(ClientConver.class, clientConver.getClientConverPK());
            Client clientOld = persistentClientConver.getClient();
            Client clientNew = clientConver.getClient();
            Conver converOld = persistentClientConver.getConver();
            Conver converNew = clientConver.getConver();
            Collection<Message> messageCollectionOld = persistentClientConver.getMessageCollection();
            Collection<Message> messageCollectionNew = clientConver.getMessageCollection();
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getId());
                clientConver.setClient(clientNew);
            }
            if (converNew != null) {
                converNew = em.getReference(converNew.getClass(), converNew.getId());
                clientConver.setConver(converNew);
            }
            Collection<Message> attachedMessageCollectionNew = new ArrayList<Message>();
            for (Message messageCollectionNewMessageToAttach : messageCollectionNew) {
                messageCollectionNewMessageToAttach = em.getReference(messageCollectionNewMessageToAttach.getClass(), messageCollectionNewMessageToAttach.getId());
                attachedMessageCollectionNew.add(messageCollectionNewMessageToAttach);
            }
            messageCollectionNew = attachedMessageCollectionNew;
            clientConver.setMessageCollection(messageCollectionNew);
            clientConver = em.merge(clientConver);
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.getClientConverCollection().remove(clientConver);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.getClientConverCollection().add(clientConver);
                clientNew = em.merge(clientNew);
            }
            if (converOld != null && !converOld.equals(converNew)) {
                converOld.getClientConverCollection().remove(clientConver);
                converOld = em.merge(converOld);
            }
            if (converNew != null && !converNew.equals(converOld)) {
                converNew.getClientConverCollection().add(clientConver);
                converNew = em.merge(converNew);
            }
            for (Message messageCollectionOldMessage : messageCollectionOld) {
                if (!messageCollectionNew.contains(messageCollectionOldMessage)) {
                    messageCollectionOldMessage.setClientConver(null);
                    messageCollectionOldMessage = em.merge(messageCollectionOldMessage);
                }
            }
            for (Message messageCollectionNewMessage : messageCollectionNew) {
                if (!messageCollectionOld.contains(messageCollectionNewMessage)) {
                    ClientConver oldClientConverOfMessageCollectionNewMessage = messageCollectionNewMessage.getClientConver();
                    messageCollectionNewMessage.setClientConver(clientConver);
                    messageCollectionNewMessage = em.merge(messageCollectionNewMessage);
                    if (oldClientConverOfMessageCollectionNewMessage != null && !oldClientConverOfMessageCollectionNewMessage.equals(clientConver)) {
                        oldClientConverOfMessageCollectionNewMessage.getMessageCollection().remove(messageCollectionNewMessage);
                        oldClientConverOfMessageCollectionNewMessage = em.merge(oldClientConverOfMessageCollectionNewMessage);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ClientConverPK id = clientConver.getClientConverPK();
                if (findClientConver(id) == null) {
                    throw new NonexistentEntityException("The clientConver with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ClientConverPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientConver clientConver;
            try {
                clientConver = em.getReference(ClientConver.class, id);
                clientConver.getClientConverPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientConver with id " + id + " no longer exists.", enfe);
            }
            Client client = clientConver.getClient();
            if (client != null) {
                client.getClientConverCollection().remove(clientConver);
                client = em.merge(client);
            }
            Conver conver = clientConver.getConver();
            if (conver != null) {
                conver.getClientConverCollection().remove(clientConver);
                conver = em.merge(conver);
            }
            Collection<Message> messageCollection = clientConver.getMessageCollection();
            for (Message messageCollectionMessage : messageCollection) {
                messageCollectionMessage.setClientConver(null);
                messageCollectionMessage = em.merge(messageCollectionMessage);
            }
            em.remove(clientConver);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientConver> findClientConverEntities() {
        return findClientConverEntities(true, -1, -1);
    }

    public List<ClientConver> findClientConverEntities(int maxResults, int firstResult) {
        return findClientConverEntities(false, maxResults, firstResult);
    }

    private List<ClientConver> findClientConverEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientConver.class));
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

    public ClientConver findClientConver(ClientConverPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientConver.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientConverCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientConver> rt = cq.from(ClientConver.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
