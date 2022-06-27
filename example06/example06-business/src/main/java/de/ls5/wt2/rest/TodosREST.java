package de.ls5.wt2.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import de.ls5.wt2.entity.DBTodo;
import de.ls5.wt2.entity.DBTodo_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping(path = "rest/todo")
public class TodosREST {

    @Autowired
    private EntityManager entityManager;

    @GetMapping(path = "/newest",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo readNewestNews() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBTodo> query = builder.createQuery(DBTodo.class);

        final Root<DBTodo> from = query.from(DBTodo.class);

        final Order order = builder.desc(from.get(DBTodo_.createdOn));

        query.select(from).orderBy(order);

        return this.entityManager.createQuery(query).setMaxResults(1).getSingleResult();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo create(@RequestBody final DBTodo param) {

        final DBTodo todo = new DBTodo();

        todo.setHeadline(param.getHeadline());
        todo.setContent(param.getContent());
        todo.setPublishedOn(new Date());

        this.entityManager.persist(todo);

        return todo;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DBTodo> readAllAsJSON() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBTodo> query = builder.createQuery(DBTodo.class);

        final Root<DBTodo> from = query.from(DBTodo.class);

        query.select(from);

        return this.entityManager.createQuery(query).getResultList();
    }

    @GetMapping(path = "/{id}",
                // consumes = MediaType.TEXT_PLAIN_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo readAsJSON(@PathVariable("id") final long id) {
        DBTodo todo = this.entityManager.find(DBTodo.class, id);

        return todo;
    }


}
