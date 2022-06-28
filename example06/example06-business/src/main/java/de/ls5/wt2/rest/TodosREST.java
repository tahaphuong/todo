package de.ls5.wt2.rest;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.ls5.wt2.entity.DBTodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping(path = "rest/todo")
public class TodosREST {

    @Autowired
    private EntityManager entityManager;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DBTodo> readAllAsJSON() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBTodo> query = builder.createQuery(DBTodo.class);
        final Root<DBTodo> from = query.from(DBTodo.class);

        Predicate predicateForPublicTodos
            = builder.equal(from.get("isPrivate"), false);
        query.select(from).where(predicateForPublicTodos);

        return this.entityManager.createQuery(query).getResultList();
    }

    @GetMapping(path = "/{id}",
                // consumes = MediaType.TEXT_PLAIN_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public DBTodo readAsJSON(@PathVariable("id") final long id) {
        DBTodo todo = this.entityManager.find(DBTodo.class, id);
        if (todo.getIsPrivate()) {
            throw new NoSuchElementException("Element is not available!");
        }
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
