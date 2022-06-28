package de.ls5.wt2.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DBTodo.class)
public abstract class DBTodo_ extends de.ls5.wt2.entity.DBIdentified_ {

	public static volatile SingularAttribute<DBTodo, String> author;
	public static volatile SingularAttribute<DBTodo, Date> publishedOn;
	public static volatile SingularAttribute<DBTodo, Boolean> isPrivate;
	public static volatile SingularAttribute<DBTodo, String> headline;
	public static volatile SingularAttribute<DBTodo, String> content;

	public static final String AUTHOR = "author";
	public static final String PUBLISHED_ON = "publishedOn";
	public static final String IS_PRIVATE = "isPrivate";
	public static final String HEADLINE = "headline";
	public static final String CONTENT = "content";

}

