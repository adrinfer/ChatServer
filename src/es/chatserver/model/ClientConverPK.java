/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author adrinfer
 */
@Embeddable
public class ClientConverPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "clientid")
    private int clientid;
    @Basic(optional = false)
    @Column(name = "converid")
    private int converid;

    public ClientConverPK() {
    }

    public ClientConverPK(int clientid, int converid) {
        this.clientid = clientid;
        this.converid = converid;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getConverid() {
        return converid;
    }

    public void setConverid(int converid) {
        this.converid = converid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) clientid;
        hash += (int) converid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientConverPK)) {
            return false;
        }
        ClientConverPK other = (ClientConverPK) object;
        if (this.clientid != other.clientid) {
            return false;
        }
        if (this.converid != other.converid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.chatserver.model.ClientConverPK[ clientid=" + clientid + ", converid=" + converid + " ]";
    }
    
}
