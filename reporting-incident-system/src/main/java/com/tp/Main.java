package com.tp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        final String persistenceUnitName = "test-bd";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);

        EntityManager manager = factory.createEntityManager();

        System.out.println(manager.createQuery("FROM Technical").getResultList());
    }
}