package de.ls5.wt2.auth;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import de.ls5.wt2.entity.DBTodo;
import de.ls5.wt2.Helper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping(path = {"rest/auth/session/todo", "rest/auth/basic/todo", "rest/auth/jwt/todo"})

public class AuthTodosREST {

    @Autowired
    private EntityManager entityManager;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DBTodo> readAllAsJSON() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBTodo> query = builder.createQuery(DBTodo.class);
        final Root<DBTodo> from = query.from(DBTodo.class);

        Predicate predicateForPublicTodos
            = builder.equal(from.get("isPrivate"), false);

        Subject subject = SecurityUtils.getSubject();

        if (Helper.checkSubject(subject)) {
            if (subject.hasRole("admin")) {
                query.select(from);
            } else {
                Predicate predicateForAuthorTodos
                    = builder.equal(from.get("author"), subject.getPrincipal().toString());
                Predicate mainPredicate = builder.or(predicateForPublicTodos, predicateForAuthorTodos);
                query.select(from).where(mainPredicate);
            }
        } else {
            query.select(from).where(predicateForPublicTodos);
        }

        return this.entityManager.createQuery(query).getResultList();
    }

    @GetMapping(path = "/{id}",
            // consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo readAsJSON(@PathVariable("id") final long id) {
        DBTodo todo = this.entityManager.find(DBTodo.class, id);

        Subject subject = SecurityUtils.getSubject();
        Helper.checkSubjectForOneElement(subject, todo);

        return todo;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo create(@RequestBody final DBTodo param) throws Exception {

        Helper.checkAuthenticated(SecurityUtils.getSubject());

        final DBTodo todo = new DBTodo();
        todo.setHeadline(param.getHeadline());
        todo.setContent(param.getContent());
        todo.setIsPrivate(param.getIsPrivate());
        todo.setAuthor(param.getAuthor());
        todo.setPublishedOn(new Date());

        this.entityManager.persist(todo);
        return todo;
    }

    @PutMapping(path = "/{id}",
            // consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo updateById(
            @PathVariable("id") final long id,
            @RequestBody final DBTodo param) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        Helper.checkAuthenticated(subject);
        DBTodo todo = this.entityManager.find(DBTodo.class, id);
        Helper.checkSubjectForOneElement(subject, todo);

        if (todo != null) {
            if (param.getHeadline() != null) {
                todo.setHeadline(param.getHeadline());
            }
            if (param.getContent() != null) {
                todo.setContent(param.getContent());
            }
        }
        return todo;
    }

    @DeleteMapping(
            path = "/{id}",
            // consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo deleteById(@PathVariable("id") final long id) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        Helper.checkAuthenticated(subject);
        DBTodo todo = this.entityManager.find(DBTodo.class, id);
        Helper.checkSubjectForOneElement(subject, todo);

        this.entityManager.remove(todo);
        return todo;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleItemNotFoundException(
        Exception exception
    ){
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }
}
