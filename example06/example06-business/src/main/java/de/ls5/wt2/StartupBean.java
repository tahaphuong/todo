package de.ls5.wt2;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import de.ls5.wt2.entity.DBTodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class StartupBean implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final DBTodo firstNewsItem = this.entityManager.find(DBTodo.class, 1L);

        // only initialize once
        if (firstNewsItem == null) {
            final DBTodo todo = new DBTodo();

            todo.setHeadline("Startup 1");
            todo.setContent("Startup Bean successfully executed");
            todo.setPublishedOn(new Date());

            this.entityManager.persist(todo);
        }
    }
}
