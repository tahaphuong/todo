package de.ls5.wt2.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DBTodo.class)
public abstract class DBTodo_ extends de.ls5.wt2.entity.DBIdentified_ {

	public static volatile SingularAttribute<DBTodo, Date> createdOn;
	public static volatile SingularAttribute<DBTodo, String> headline;
	public static volatile SingularAttribute<DBTodo, String> content;

	public static final String CREATED_ON = "createdOn";
	public static final String HEADLINE = "headline";
	public static final String CONTENT = "content";

}

