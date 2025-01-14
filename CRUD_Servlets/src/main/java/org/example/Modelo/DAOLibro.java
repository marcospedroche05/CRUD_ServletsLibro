package org.example.Modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class DAOLibro {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    public DAOLibro() {}

    public void insert(Libro lib) {
        tx.begin();
        em.persist(lib);
        tx.commit();
    }

    public Libro getById(String isbn) {
        return em.find(Libro.class, isbn);
    }

    public List<Libro> getAll() {
        return em.createQuery("from Libro", Libro.class).getResultList();
    }

    public void delete(Libro lib) {
        tx.begin();
        em.remove(lib);
        tx.commit();
    }

    public void update(Libro lib) {
        tx.begin();
        em.merge(lib);
        tx.commit();
    }
}
