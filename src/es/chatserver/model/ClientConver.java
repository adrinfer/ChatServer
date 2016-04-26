/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Practicas01
 */
@Entity
@Table(name = "client_conver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientConver.findAll", query = "SELECT c FROM ClientConver c"),
    @NamedQuery(name = "ClientConver.findByClientid", query = "SELECT c FROM ClientConver c WHERE c.clientConverPK.clientid = :clientid"),
    @NamedQuery(name = "ClientConver.findByConverid", query = "SELECT c FROM ClientConver c WHERE c.clientConverPK.converid = :converid"),
    @NamedQuery(name = "ClientConver.findByMod", query = "SELECT c FROM ClientConver c WHERE c.mod = :mod"),
    @NamedQuery(name = "ClientConver.findByAdmin", query = "SELECT c FROM ClientConver c WHERE c.admin = :admin")})
public class ClientConver implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClientConverPK clientConverPK;
    @Column(name = "mod")
    private Boolean mod;
    @Column(name = "admin")
    private Boolean admin;
    @JoinColumn(name = "clientid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Client client;
    @JoinColumn(name = "converid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Conver conver;
    @OneToMany(mappedBy = "clientConver")
    private Collection<Message> messageCollection;

    public ClientConver() {
    }

    public ClientConver(ClientConverPK clientConverPK) {
        this.clientConverPK = clientConverPK;
    }

    public ClientConver(int clientid, int converid) {
        this.clientConverPK = new ClientConverPK(clientid, converid);
    }

    public ClientConverPK getClientConverPK() {
        return clientConverPK;
    }

    public void setClientConverPK(ClientConverPK clientConverPK) {
        this.clientConverPK = clientConverPK;
    }

    public Boolean getMod() {
        return mod;
    }

    public void setMod(Boolean mod) {
        this.mod = mod;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Conver getConver() {
        return conver;
    }

    public void setConver(Conver conver) {
        this.conver = conver;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientConverPK != null ? clientConverPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientConver)) {
            return false;
        }
        ClientConver other = (ClientConver) object;
        if ((this.clientConverPK == null && other.clientConverPK != null) || (this.clientConverPK != null && !this.clientConverPK.equals(other.clientConverPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.chatserver.model.ClientConver[ clientConverPK=" + clientConverPK + " ]";
    }
    
}
