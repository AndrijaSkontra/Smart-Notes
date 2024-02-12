package controller;

import org.hibernate.SessionFactory;

public interface DatabaseConnection {

    SessionFactory getSessionFactory();
}
