package de.ls5.wt2;

import de.ls5.wt2.entity.DBTodo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;

public class Helper {
    public static boolean checkSubject(Subject subject) {
        return subject != null && subject.isAuthenticated() && subject.getPrincipal() != null;
    }

    public static void checkAuthenticated(Subject subject) {
        if (!Helper.checkSubject(subject)) {
            throw new UnauthenticatedException("Not authenticated");
        }
    }

    public static void checkSubjectForOneElement(Subject subject, DBTodo todo) {
        if (todo.getIsPrivate() && Helper.checkSubject(subject)) {
            if (!subject.hasRole("admin") && todo.getAuthor() != subject.getPrincipal().toString()) {
                throw new UnauthorizedException("Unauthorized for this action.");
            }
        }
    }
}
