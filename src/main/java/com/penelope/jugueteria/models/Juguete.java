package com.penelope.jugueteria.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
//@NamedQuery(name = "buscarJuguetes",query = "")
@Table(name = "juguete")
public class Juguete {
	
	@Id //Lo identifica como llave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Generar automaticamnte
	private int id;
	
	@Column
	private String nombre;
	
	@Column
	private float precio;
	
	@Column
	private String descripcion;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "fecha") //le dice que la columna se llama asi en la tabla
	@GeneratedValue(strategy = GenerationType.AUTO) // Generar automaticamnte
	private Date fechaRegistro;
	
	public Juguete() {}
	
	public Juguete(int id, String nombre, float precio, String descripcion, Date fechaRegistro) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.fechaRegistro = fechaRegistro;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "Juguete [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion
				+ ", fechaRegistro=" + fechaRegistro + "]";
	}
}
