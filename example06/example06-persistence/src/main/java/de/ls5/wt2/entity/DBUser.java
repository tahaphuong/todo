package de.ls5.wt2.entity;

// import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.*;

@Entity
public class DBUser extends DBIdentified {

    private String username;

    @OneToMany(targetEntity=DBUser.class, mappedBy="dbuser", fetch=FetchType.EAGER)
    private DBTodo todo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // public ArrayList<DBTodo> getListTodo() { return listTodo; }

    // public void addTodo(DBTodo todo) { this.listTodo.add(todo); }
}

