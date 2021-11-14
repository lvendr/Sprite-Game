/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.nguy0770.entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Song Nguyen Nguyen $ Liam Dickson
 * Student Number: 040 940 830 & 040 933 739
 * Assignment: 2
 * Professor: Todd Kelley
 * Lab Prof: Todd Kelly
 * Lab#: 301
 * Class: SpriteFacade
 * Methods: getEntityManager(), SpriteFacade()
 * Description: Extends Sprite Facade and contains the persistence unit name for ejb
 */
@Stateless
public class SpriteFacade extends AbstractFacade<Sprite> {
    @PersistenceContext(unitName = "SpriteSong-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpriteFacade() {
        super(Sprite.class);
    }
    
}
