package com.project.services;

import com.project.entities.Person;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity facade Service used for fonctional application
 * @author Selim.Bensenouci
 */
@Named(value = EntityFacade.EF_NAME)
@Stateless
@TransactionAttribute
public class EntityFacade implements Serializable{

    private final static Logger LOG = Logger.getLogger(EntityFacade.class);

    public static final String EF_NAME = "entityFacade";

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    protected void postConstruct() {
        LOG.debug("PostConstruct " + this);
    }


    /**
     * Method to merge person with the PersistantContext
     * @param person to merge
     * @return
     */
    public Person createPerson(Person person){
        return this.em.merge(person);
    }


    /**
     * Method to delete person from the dataBase
     * @param person to delete
     */
    public void deletePerson(Person person){
        this.em.remove(person);
    }

    /**
     * Simple method to find person from this PersistantContext
     * @param id of the wanted person
     */
    public void findPerson(Long id){
        this.em.find(Person.class,id);
    }


       public List<Person> findAllPersons(){
        return  new ArrayList<Person>(this.em.createNamedQuery(Person.FIND_ALL).
                getResultList());
       }



}
