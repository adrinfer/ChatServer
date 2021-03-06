/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.persistence;

import es.chatserver.controllers.persistence.exceptions.IllegalOrphanException;
import es.chatserver.controllers.persistence.exceptions.NonexistentEntityException;
import es.chatserver.model.Client;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import es.chatserver.model.ClientConver;
import java.util.ArrayList;
import java.util.Collection;
import es.chatserver.model.Incidencias;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author adrinfer
 */
public class ClientJpaController implements Serializable {

    public ClientJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) {
        if (client.getClientConverCollection() == null) {
            client.setClientConverCollection(new ArrayList<ClientConver>());
        }
        if (client.getIncidenciasCollection() == null) {
            client.setIncidenciasCollection(new ArrayList<Incidencias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ClientConver> attachedClientConverCollection = new ArrayList<ClientConver>();
            for (ClientConver clientConverCollectionClientConverToAttach : client.getClientConverCollection()) {
                clientConverCollectionClientConverToAttach = em.getReference(clientConverCollectionClientConverToAttach.getClass(), clientConverCollectionClientConverToAttach.getClientConverPK());
                attachedClientConverCollection.add(clientConverCollectionClientConverToAttach);
            }
            client.setClientConverCollection(attachedClientConverCollection);
            Collection<Incidencias> attachedIncidenciasCollection = new ArrayList<Incidencias>();
            for (Incidencias incidenciasCollectionIncidenciasToAttach : client.getIncidenciasCollection()) {
                incidenciasCollectionIncidenciasToAttach = em.getReference(incidenciasCollectionIncidenciasToAttach.getClass(), incidenciasCollectionIncidenciasToAttach.getId());
                attachedIncidenciasCollection.add(incidenciasCollectionIncidenciasToAttach);
            }
            client.setIncidenciasCollection(attachedIncidenciasCollection);
            em.persist(client);
            for (ClientConver clientConverCollectionClientConver : client.getClientConverCollection()) {
                Client oldClientOfClientConverCollectionClientConver = clientConverCollectionClientConver.getClient();
                clientConverCollectionClientConver.setClient(client);
                clientConverCollectionClientConver = em.merge(clientConverCollectionClientConver);
                if (oldClientOfClientConverCollectionClientConver != null) {
                    oldClientOfClientConverCollectionClientConver.getClientConverCollection().remove(clientConverCollectionClientConver);
                    oldClientOfClientConverCollectionClientConver = em.merge(oldClientOfClientConverCollectionClientConver);
                }
            }
            for (Incidencias incidenciasCollectionIncidencias : client.getIncidenciasCollection()) {
                Client oldClientidOfIncidenciasCollectionIncidencias = incidenciasCollectionIncidencias.getClientid();
                incidenciasCollectionIncidencias.setClientid(client);
                incidenciasCollectionIncidencias = em.merge(incidenciasCollectionIncidencias);
                if (oldClientidOfIncidenciasCollectionIncidencias != null) {
                    oldClientidOfIncidenciasCollectionIncidencias.getIncidenciasCollection().remove(incidenciasCollectionIncidencias);
                    oldClientidOfIncidenciasCollectionIncidencias = em.merge(oldClientidOfIncidenciasCollectionIncidencias);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client persistentClient = em.find(Client.class, client.getId());
            Collection<ClientConver> clientConverCollectionOld = persistentClient.getClientConverCollection();
            Collection<ClientConver> clientConverCollectionNew = client.getClientConverCollection();
            Collection<Incidencias> incidenciasCollectionOld = persistentClient.getIncidenciasCollection();
            Collection<Incidencias> incidenciasCollectionNew = client.getIncidenciasCollection();
            List<String> illegalOrphanMessages = null;
            for (ClientConver clientConverCollectionOldClientConver : clientConverCollectionOld) {
                if (!clientConverCollectionNew.contains(clientConverCollectionOldClientConver)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientConver " + clientConverCollectionOldClientConver + " since its client field is not nullable.");
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
            client.setClientConverCollection(clientConverCollectionNew);
            Collection<Incidencias> attachedIncidenciasCollectionNew = new ArrayList<Incidencias>();
            for (Incidencias incidenciasCollectionNewIncidenciasToAttach : incidenciasCollectionNew) {
                incidenciasCollectionNewIncidenciasToAttach = em.getReference(incidenciasCollectionNewIncidenciasToAttach.getClass(), incidenciasCollectionNewIncidenciasToAttach.getId());
                attachedIncidenciasCollectionNew.add(incidenciasCollectionNewIncidenciasToAttach);
            }
            incidenciasCollectionNew = attachedIncidenciasCollectionNew;
            client.setIncidenciasCollection(incidenciasCollectionNew);
            client = em.merge(client);
            for (ClientConver clientConverCollectionNewClientConver : clientConverCollectionNew) {
                if (!clientConverCollectionOld.contains(clientConverCollectionNewClientConver)) {
                    Client oldClientOfClientConverCollectionNewClientConver = clientConverCollectionNewClientConver.getClient();
                    clientConverCollectionNewClientConver.setClient(client);
                    clientConverCollectionNewClientConver = em.merge(clientConverCollectionNewClientConver);
                    if (oldClientOfClientConverCollectionNewClientConver != null && !oldClientOfClientConverCollectionNewClientConver.equals(client)) {
                        oldClientOfClientConverCollectionNewClientConver.getClientConverCollection().remove(clientConverCollectionNewClientConver);
                        oldClientOfClientConverCollectionNewClientConver = em.merge(oldClientOfClientConverCollectionNewClientConver);
                    }
                }
            }
            for (Incidencias incidenciasCollectionOldIncidencias : incidenciasCollectionOld) {
                if (!incidenciasCollectionNew.contains(incidenciasCollectionOldIncidencias)) {
                    incidenciasCollectionOldIncidencias.setClientid(null);
                    incidenciasCollectionOldIncidencias = em.merge(incidenciasCollectionOldIncidencias);
                }
            }
            for (Incidencias incidenciasCollectionNewIncidencias : incidenciasCollectionNew) {
                if (!incidenciasCollectionOld.contains(incidenciasCollectionNewIncidencias)) {
                    Client oldClientidOfIncidenciasCollectionNewIncidencias = incidenciasCollectionNewIncidencias.getClientid();
                    incidenciasCollectionNewIncidencias.setClientid(client);
                    incidenciasCollectionNewIncidencias = em.merge(incidenciasCollectionNewIncidencias);
                    if (oldClientidOfIncidenciasCollectionNewIncidencias != null && !oldClientidOfIncidenciasCollectionNewIncidencias.equals(client)) {
                        oldClientidOfIncidenciasCollectionNewIncidencias.getIncidenciasCollection().remove(incidenciasCollectionNewIncidencias);
                        oldClientidOfIncidenciasCollectionNewIncidencias = em.merge(oldClientidOfIncidenciasCollectionNewIncidencias);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = client.getId();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
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
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClientConver> clientConverCollectionOrphanCheck = client.getClientConverCollection();
            for (ClientConver clientConverCollectionOrphanCheckClientConver : clientConverCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the ClientConver " + clientConverCollectionOrphanCheckClientConver + " in its clientConverCollection field has a non-nullable client field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Incidencias> incidenciasCollection = client.getIncidenciasCollection();
            for (Incidencias incidenciasCollectionIncidencias : incidenciasCollection) {
                incidenciasCollectionIncidencias.setClientid(null);
                incidenciasCollectionIncidencias = em.merge(incidenciasCollectionIncidencias);
            }
            em.remove(client);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
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

    public Client findClient(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Client findClient(String nick)
    {
        for(Client c: findClientEntities())
        {
            if(Objects.equals(c.getNick().toLowerCase(), nick.toLowerCase()))
            {
                return c;
            }
        }
        
        return null;
    }
    
}
