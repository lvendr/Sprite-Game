/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.nguy0770.entity;

import java.io.Serializable;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Song Nguyen Nguyen $ Liam Dickson
 * Student Number: 040 940 830 & 040 933 739
 * Assignment: 2
 * Professor: Todd Kelley
 * Lab Prof: Todd Kelly
 * Lab#: 301
 * Class: AppUser
 * Methods: AppUser(), AppUser(Long id), getFirstName(), setFirstName(string firstName), getLastName(), 
 *          setLastName(string lastName), getUserID, setUserID(string userID), getPassword(), setPassword(string password),
 *          getGroupName(), setGroupName(string groupName), hashCode(), equals(Object object), toString()
 * Description: AppUser class to hold its attributes and functionalities to update AppUser entity
 */
@Entity
@Table(name = "APPUSER")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'java:comp/DefaultDataSource'}",
        callerQuery = "#{'select password from app.appuser where userID = ?'}",
        groupsQuery = "select groupName from app.appuser where userID = ?",
        hashAlgorithm = PasswordHash.class,
        priority = 10
)
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotEmpty(message = "{invalid.firstName}")
    protected String firstName;
    @NotNull
    @NotEmpty(message = "{invalid.lastName}")
    protected String lastName;
    @NotNull
    @NotEmpty(message = "{invalid.userID}")
    private String userID;
    @NotNull(message = "{invalid.password}")
    private String password;
    @NotNull
    @NotEmpty(message = "{invalid.groupName}")
    private String groupName;

    public AppUser() {
    }

    public AppUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return "";
    }

    public void setPassword(String password) {
        if(password.isEmpty())
            return;
        
        // initialize a PasswordHash object which will generate password hashes
        Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
        PasswordHash passwordHash = instance.get();
        passwordHash.initialize(new HashMap<String, String>());  // todo: are the defaults good enough?
        // now we can generate a password entry for a given password
        password = passwordHash.generate(password.toCharArray());
        //at this point, passwordEntry refers to a salted/hashed password entry corresponding to mySecretPassword
        this.password = password;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.nguy0770.entity.AppUser[ id=" + id + " ]";
    }

}
