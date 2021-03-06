/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Practicas01
 */
@Entity
@Table(name = "conver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conver.findAll", query = "SELECT c FROM Conver c"),
    @NamedQuery(name = "Conver.findById", query = "SELECT c FROM Conver c WHERE c.id = :id"),
    @NamedQuery(name = "Conver.findByDate", query = "SELECT c FROM Conver c WHERE c.date = :date")})
public class Conver implements Serializable {

    @Column(name = "name")
    private String name;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conver")
    private Collection<ClientConver> clientConverCollection;

    public Conver() {
        this.name = "Conversacion";
    }

    public Conver(Integer id) {
        this.id = id;
        this.date = new Date();
    }
    
    public Conver(String name) {
        this.name = name;
        this.date = new Date();
    }
    
    public Conver(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.date = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    

    @XmlTransient
    public Collection<ClientConver> getClientConverCollection() {
        return clientConverCollection;
    }

    public void setClientConverCollection(Collection<ClientConver> clientConverCollection) {
        this.clientConverCollection = clientConverCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conver)) {
            return false;
        }
        Conver other = (Conver) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.chatserver.model.Conver[ id=" + id + " ]";
    }
    
}
