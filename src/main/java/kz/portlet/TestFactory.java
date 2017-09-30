/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.portlet;

/**
 *
 * @author Admin
 */
public class TestFactory {

    public static java.util.Collection generateCollection() {
// Creates the collection
        java.util.Vector collection = new java.util.Vector();

// Adds the values in the bean and adds it into the collection
        collection.add(new PersonBean("Ted", 20));
        collection.add(new PersonBean("Jack", 34));
        collection.add(new PersonBean("Bob", 56));
        collection.add(new PersonBean("Alice", 12));
        collection.add(new PersonBean("Robin", 22));
        collection.add(new PersonBean("Peter", 28));

// returns the collection of beans.
        return collection;
    }
}
