package dao;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContexts;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import model.Person;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void save(Person person) {
        if (person.getId() == null) {
            em.persist(person);
        } else {
            em.merge(person);
        }
    }

    public List<Person> findAll() {

        return em.createQuery("select p from Person  p", Person.class).getResultList();

    }

    @Transactional
    public Optional<Person> findPersonByName(String name) {
        TypedQuery<Person> query = em.createQuery("select p from Person p where p.name = :name", Person.class);
        query.setParameter("name", name);

        return query.getResultStream().findFirst();
    }
}
