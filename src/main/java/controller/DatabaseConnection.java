package controller;

import org.hibernate.SessionFactory;

/**
 * Classes that want to make a new connection
 * to a database need to implement this interface.
 */
public interface DatabaseConnection {

    SessionFactory getSessionFactory();
}
