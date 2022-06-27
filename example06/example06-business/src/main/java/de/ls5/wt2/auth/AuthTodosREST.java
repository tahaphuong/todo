package de.ls5.wt2.auth;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import de.ls5.wt2.entity.DBTodo;
import de.ls5.wt2.entity.DBTodo_;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping(path = {"rest/auth/session/todos", "rest/auth/basic/todos", "rest/auth/jwt/todos"})

public class AuthTodosREST {

    @Autowired
    private EntityManager entityManager;

    @GetMapping(path = "newest",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo readNewestNews() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBTodo> query = builder.createQuery(DBTodo.class);

        final Root<DBTodo> from = query.from(DBTodo.class);

        final Order order = builder.desc(from.get(DBTodo_.createdOn));

        query.select(from).orderBy(order);

        return this.entityManager.createQuery(query).setMaxResults(1).getSingleResult();
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo create(@RequestBody final DBTodo param) throws Exception {

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new Exception("Not authenticated");
        }
        final DBTodo toDos = new DBTodo();

        toDos.setHeadline(param.getHeadline());
        toDos.setContent(param.getContent());
        toDos.setPublishedOn(new Date());

        this.entityManager.persist(toDos);

        return toDos;
    }

    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo updateById(
            @PathVariable("id") final long id,
            @RequestBody final DBTodo param) throws Exception {

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new Exception("Not authenticated");
        }

        DBTodo todo = this.entityManager.find(DBTodo.class, id);


        if (todo != null) {
            if (param.getHeadline() != null) {
                todo.setHeadline(param.getHeadline());
            }
            if (param.getContent() != null) {
                todo.setContent(param.getContent());
            }
        }
        System.out.print("updated backend");
        return todo;
    }

    @DeleteMapping(
            path = "/{id}",
            // consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo deleteById(@PathVariable("id") final long id) throws Exception {

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new Exception("Not authenticated");
        }

        DBTodo todo = this.entityManager.find(DBTodo.class, id);

        this.entityManager.remove(todo);

        return todo;
    }
}
