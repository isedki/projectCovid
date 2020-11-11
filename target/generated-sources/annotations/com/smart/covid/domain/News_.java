package com.smart.covid.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(News.class)
public abstract class News_ {

	public static volatile SingularAttribute<News, Long> id;
	public static volatile SingularAttribute<News, String> title;

	public static final String ID = "id";
	public static final String TITLE = "title";

}

