package com.penelope.jugueteria.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConnector {
	
	public static Session getSession() {
		Session session=null;
		StandardServiceRegistry registry =
				new StandardServiceRegistryBuilder()
				.configure() //lee el hibernate.cfg.xml
				.build();//Construye el objeto registry
		SessionFactory sessionF=
				new MetadataSources(registry)
				.buildMetadata()//construye el metadata a partir del registry
				.buildSessionFactory();//Construye la session a partir de la metadata
		session=sessionF.openSession();
		
		System.out.println("Se abrio la conexion");
		return session;
	}
	
	public static void closeSession(Session session) {
		
		session.close();
		System.out.println("Se cerro la conexion");
		
	}

}
