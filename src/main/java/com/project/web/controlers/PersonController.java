package com.project.web.controlers;

import com.project.entities.Person;
import com.project.services.EntityFacade;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: selim.bensenouci
 * Date: 02/08/13
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
@Named
@ConversationScoped
public class PersonController implements Serializable {

    private final static Logger LOG = Logger.getLogger(PersonController.class);

    @Inject
    EntityFacade entityFacade;

    private Person  person = new Person();

    private List<Person> personList;


    @PostConstruct
    private void postInit(){
        LOG.info("PostConstruct " + this);
        this.personList = this.entityFacade.findAllPersons();
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void addNewPerson(){
           this.entityFacade.createPerson(person);
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
