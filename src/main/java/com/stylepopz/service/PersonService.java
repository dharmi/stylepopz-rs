package com.stylepopz.service;


import java.util.List;

import com.stylepopz.model.Person;

public interface PersonService {
    
    public void addPerson(Person person);
    public List<Person> listPeople();
    public void removePerson(Integer id);
}
