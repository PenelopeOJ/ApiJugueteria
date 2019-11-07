package com.penelope.jugueteria.resources;

//import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//import com.google.gson.Gson;
import com.penelope.jugueteria.models.Juguete;
import com.penelope.jugueteria.util.HibernateConnector;
//import com.penelope.jugueteria.util.DatabaseConnector;
//import com.penelope.jugueteria.util.JuguetesManager;

@Path("juguetes")
public class JuguetesResource {
	
	//aqui podria estar declarada una session para todos los metodos
	
	/*
	 * Connection conn=DatabaseConnector.getConnection(); JuguetesManager
	 * jgManager=new JuguetesManager(conn); List<Juguete>
	 * listaJuguetes=jgManager.getAll(); String jsonJuguetes=new
	 * Gson().toJson(listaJuguetes);
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {

		List<Juguete> listaJuguetes = new ArrayList<Juguete>();
		SessionFactory sessionFactory = null;
		Session session = null;
		String rs = null;

		Status codigo;

		try {
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			session = sessionFactory.openSession();
			System.out.println("Justo antes de la query");
			Query query = session.createQuery("FROM Juguete j",Juguete.class);
			System.out.println("Justo despues de la query");
			listaJuguetes = query.getResultList();
			rs = "\"data\":\"" + listaJuguetes + "\"";
			codigo = Status.OK;
		} catch (Exception e) {
			codigo = Status.INTERNAL_SERVER_ERROR;
		} 		
		if(session != null && rs != null) {
			session.close();
			return Response.status(codigo).entity(rs).build();
		}
		else
			return Response.status(codigo).build();
		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearJuguete(Juguete juguete) {
		
		Session session=null;
		Transaction tx=null;
		Map<String, Object> response = null;
		
		Status codigo=Status.INTERNAL_SERVER_ERROR;//Notificar error interno del servidor en la logica
		String mensaje=null;
		
		try {
			session=HibernateConnector.getSession();
			tx=session.beginTransaction();
			juguete.setFechaRegistro(new Date());
			session.save(juguete);
			tx.commit();
			codigo=Status.CREATED;
			mensaje="Se almaceno el juguete";
		} catch (TransactionException e) {
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();//deshacer la operacion
			}
			mensaje="Fallo en la transaccion";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje="Error en el servidor";
		}catch (Exception e) {
			e.printStackTrace();
			mensaje="Error";
		}finally {
			if (session!=null) {
				HibernateConnector.closeSession(session);
			}
		}
		
		response=new HashMap<>();
		response.put("mensaje", mensaje);
		response.put("data", juguete);
		
		//String rs = "\"message\":\"funciono post\"";
		return Response.status(codigo).entity(response).build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateJuguete(Juguete juguete, @PathParam("id") Integer id) {
		
		Session session=null;
		Transaction tx=null;
		Map<String, Object> response = null;
		Status status=Status.INTERNAL_SERVER_ERROR;
		String mensaje=null;
		Juguete dbJuguete=null;		
		try {
			session=HibernateConnector.getSession();
			dbJuguete=session.find(Juguete.class,id);
			if(dbJuguete!=null) {
				tx=session.beginTransaction();
				//operaciones con la base de datos
				dbJuguete.setNombre(juguete.getNombre());
				dbJuguete.setDescripcion(juguete.getDescripcion());
				dbJuguete.setPrecio(juguete.getPrecio());
				session.update(dbJuguete);
				tx.commit();
				
				status=Status.OK;
				mensaje="Se actualizo el juguete";
				
			}else {
				status=Status.NOT_FOUND;
				mensaje="No se encontro el juguete";
			}
		} catch (TransactionException e) {
			// TODO: handle exception
			e.printStackTrace();
			mensaje="Error en la transaccion";
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			mensaje="Error con la base de datos";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mensaje="Error";
		}finally {
			if(session!=null) {
				HibernateConnector.closeSession(session);
			}
		}
		
		//Configurar datos de respuesta
		response=new HashMap<>();
		response.put("mensaje", mensaje);
		response.put("data", dbJuguete);
		
		//Construir la respuesta
		return Response.status(status).entity(response).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteJuguete(@PathParam("id") Integer id) {
		
		Status status=Status.INTERNAL_SERVER_ERROR;
		Session session=null;
		Transaction tx=null;
		String mensaje=null;
		Juguete dbJuguete=null;
		Map<String, Object>response=null;
		
		try {
			session=HibernateConnector.getSession();
			dbJuguete=session.get(Juguete.class, id);
			if (dbJuguete!=null) {
				tx=session.beginTransaction();
				session.remove(dbJuguete);
				tx.commit();
				mensaje="Se elimino el juguete";
				status=Status.OK;
			}else {
				status=Status.NOT_FOUND;
				mensaje="No se encontro el juguete a eliminar";
			}
			
		} catch (TransactionException e) {
			e.printStackTrace();
			mensaje="Error al realizar la transaccion";
		} catch (HibernateException e) {
			e.printStackTrace();
			mensaje="Error la conexion con la base de datos";
		} catch (Exception e) {
			e.printStackTrace();
			mensaje="Error";
		}finally {
			if (session!=null) {
				HibernateConnector.closeSession(session);
			}
		}
		
		response=new HashMap<>();
		response.put("mensaje", mensaje);
		response.put("data", dbJuguete);
		
		return Response.status(status).entity(response).build();
	}
}
