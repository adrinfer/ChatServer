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
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
    @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email"),
    @NamedQuery(name = "Client.findByNick", query = "SELECT c FROM Client c WHERE c.nick = :nick"),
    @NamedQuery(name = "Client.findByPass", query = "SELECT c FROM Client c WHERE c.pass = :pass"),
    @NamedQuery(name = "Client.findByRegistro", query = "SELECT c FROM Client c WHERE c.registro = :registro"),
    @NamedQuery(name = "Client.findByNombre", query = "SELECT c FROM Client c WHERE c.nombre = :nombre")})
public class Client implements Serializable {

    @Column(name = "bloqueado")
    private Boolean bloqueado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "nick")
    private String nick;
    
    @Column(name = "pass")
    private String pass;
    
    @Column(name = "registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registro;
    
    @Column(name = "nombre")
    private String nombre;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Collection<ClientConver> clientConverCollection;
    @OneToMany(mappedBy = "clientid")
    private Collection<Incidencias> incidenciasCollection;

    public Client() {
    }
    
    public Client(String nick, String pass)
    {
        this.nick = nick;
        this.pass = pass;
        this.email = "-";
        this.registro = new Date();
    }
    
    public Client(String nombre, String nick, String pass, String email)
    {
        this.nombre = nombre;
        this.nick = nick;
        this.pass = pass;
        this.email = email;
        //this.bloqueado = true;
        this.registro = new Date();
    }

    public Client(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getRegistro() {
        return registro;
    }

    public void setRegistro(Date registro) {
        this.registro = registro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    @XmlTransient
    public Collection<ClientConver> getClientConverCollection() {
        return clientConverCollection;
    }

    public void setClientConverCollection(Collection<ClientConver> clientConverCollection) {
        this.clientConverCollection = clientConverCollection;
    }

    @XmlTransient
    public Collection<Incidencias> getIncidenciasCollection() {
        return incidenciasCollection;
    }

    public void setIncidenciasCollection(Collection<Incidencias> incidenciasCollection) {
        this.incidenciasCollection = incidenciasCollection;
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.chatserver.model.Client[ id=" + id + " ]";
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    
}
