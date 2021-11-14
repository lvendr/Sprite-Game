/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cst8218.nguy0770.entity.Sprite;
import java.util.List;
import java.util.Objects;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Song Nguyen Nguyen $ Liam Dickson
 * Student Number: 040 940 830 & 040 933 739
 * Assignment: 2
 * Professor: Todd Kelley
 * Lab Prof: Todd Kelly
 * Lab#: 301
 * Class: SpriteFacadeREST
 * Methods: createSprite(Sprite entity), update(@PathParam("id") Long id, Sprite entity, 
 *          edit(@PathParam("id") Long id, Sprite entity),
 *          remove(@PathParam("id") Long id), find(@PathParam("id") Long id), findAll(),
 *          findRange(@PathParam("from") Integer from, @PathParam("to") Integer to),
 *          countRest(), getEntityManager()
 * Description: Implement REST API for the game
 */
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'java:comp/DefaultDataSource'}",
        callerQuery = "#{'select password from app.appuser where userID = ?'}",
        groupsQuery = "select groupName from app.appuser where userID = ?",
        hashAlgorithm = PasswordHash.class,
        priority = 10
)
//@BasicAuthenticationMechanismDefinition
@DeclareRoles({"Admin", "RestGroup"})
@RolesAllowed({"Admin", "RestGroup"})
//@FormAuthenticationMechanismDefinition
@Stateless
@Path("cst8218.nguy0770.entity.sprite")
public class SpriteFacadeREST extends AbstractFacade<Sprite> {

    @PersistenceContext(unitName = "SpriteSong-ejbPU")
    private EntityManager em;

    public SpriteFacadeREST() {
        super(Sprite.class);
    }

    /**
     * 
     * @param entity
     * Method: POST method to create and update Sprites
     * @return NOT_FOUND 404 if id is not null and Sprite with that id does not exist
     *         NO_CONTENT 204 if the Sprite has been updated
     *         CREATED 201 if the Sprite is created
     *         INTERNAL_SERVER_ERROR 500 error in general
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createSprite(Sprite entity) {
        if(entity.getId() != null && (super.find(entity.getId()) == null)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        if(entity.getId() != null && (super.find(entity.getId()) != null)){
            entity.updates(super.find(entity.getId()));
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        
        if (entity.getId() == null) {
            super.create(entity);
            return Response.status(Response.Status.CREATED).build();
        } 
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 
     * @param id
     * @param entity
     * Method: POST on a specific id to update Sprite with the new Sprite's information
     * @return NOT_FOUND 404 if Sprite is not found
     *         BAD_REQUEST 400 if id is not matching with the id in the body
     *         NO_CONTENT 204 if the Sprite has been updated
     *         INTERNAL_SERVER_ERROR 500 error in general
     */
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Sprite entity) {
        if (super.find(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (entity.getId() != null && !Objects.equals(entity.getId(), id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Sprite's body has non-matching id").build();
        }

        if (spriteExists(super.find(id))) {
            entity.updates(super.find(id));
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 
     * @param id
     * @param entity
     * Method: PUT on a specific id to replace Sprite with the new Sprite
     * @return NOT_FOUND 404 if Sprite is not found
     *         BAD_REQUEST 400 if id is not matching with the id in the body, or missing id in the body
     *         NO_CONTENT 204 if the Sprite has been replaced
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Sprite entity) {
        if (super.find(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        if(entity.getId() == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing id in Sprite's body").build();
        }        

        if (entity.getId() != null && !Objects.equals(entity.getId(), id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Sprite's body has non-matching id").build();
        }

        super.edit(entity);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * 
     * @param id
     * Method: DELETE a Sprite by id
     * @return NOT_FOUND 404 if Sprite is not found
     *         ACCEPTED 202 if id is valid and Sprite is deleted
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        if (super.find(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        super.remove(super.find(id));
        return Response.status(Response.Status.ACCEPTED).build();
    }

    /**
     * 
     * @param id
     * Method: GET a Sprite by id
     * @return NOT_FOUND 404 if Sprite is not found
     *         OK 200 if Sprite is found and its body is returned
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
          if (super.find(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.status(Response.Status.OK).entity(super.find(id)).build();
    }

    /**
     * 
     * Method: GET all Sprites
     * @return List<Sprite> the list of all Sprites and their bodies
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprite> findAll() {
        return super.findAll();
    }

    /**
     * 
     * @param from
     * @param to
     * Method: GET Sprites using the given range
     * @return List<Sprite> the list of Sprites within that range
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprite> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * 
     * Method: GET the number of Sprites created in the database
     * @return String number of Sprites
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
