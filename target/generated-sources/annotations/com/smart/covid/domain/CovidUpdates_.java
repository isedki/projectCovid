package com.smart.covid.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CovidUpdates.class)
public abstract class CovidUpdates_ {

	public static volatile SingularAttribute<CovidUpdates, String> image;
	public static volatile SingularAttribute<CovidUpdates, String> publishedAt;
	public static volatile SingularAttribute<CovidUpdates, String> domain;
	public static volatile SingularAttribute<CovidUpdates, Long> id;
	public static volatile SingularAttribute<CovidUpdates, String> source;
	public static volatile SingularAttribute<CovidUpdates, String> title;
	public static volatile SingularAttribute<CovidUpdates, String> content;

	public static final String IMAGE = "image";
	public static final String PUBLISHED_AT = "publishedAt";
	public static final String DOMAIN = "domain";
	public static final String ID = "id";
	public static final String SOURCE = "source";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

}

