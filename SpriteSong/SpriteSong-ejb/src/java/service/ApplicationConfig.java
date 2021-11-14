/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.ws.rs.core.Application;

/**
 * 
 * @author Song Nguyen Nguyen $ Liam Dickson
 * Student Number: 040 940 830 & 040 933 739
 * Assignment: 2
 * Professor: Todd Kelley
 * Lab Prof: Todd Kelly
 * Lab#: 301
 * Class: ApplicationConfig
 * Methods: getClasses(), AddRestResourceClasses(Set<Class<?>> resources)
 * Description: Holds the Application path and automatically populate all resources defined in the project for REST
 */
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'java:comp/DefaultDataSource'}",
        callerQuery = "#{'select password from app.appuser where userID = ?'}",
        groupsQuery = "select groupName from app.appuser where userID = ?",
        hashAlgorithm = PasswordHash.class,
        priority = 10
)
@BasicAuthenticationMechanismDefinition
@DeclareRoles({"Admin", "JsfGroup"})
@RolesAllowed({"Admin", "JsfGroup"})
//@FormAuthenticationMechanismDefinition
@Named
@ApplicationScoped
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.SpriteFacadeREST.class);
    }
    
}
