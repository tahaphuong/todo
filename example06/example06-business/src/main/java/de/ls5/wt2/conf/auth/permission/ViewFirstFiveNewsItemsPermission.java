package de.ls5.wt2.conf.auth.permission;

import java.util.List;

import de.ls5.wt2.entity.DBTodo;
import org.apache.shiro.authz.Permission;

public class ViewFirstFiveNewsItemsPermission implements Permission {

    private final List<DBTodo> todos;

    public ViewFirstFiveNewsItemsPermission(final List<DBTodo> todos) {
        this.todos = todos;
    }

    @Override
    public boolean implies(Permission p) {
        return false;
    }

    public boolean check() {
        return this.todos.size() < 5;
    }
}
