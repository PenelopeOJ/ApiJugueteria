package com.penelope.jugueteria.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "bolsa")
public class Bolsa {
	
	@Id
	private Integer id;
	
	@Column
	private String nombre;
	
	@Column
	private String color;
	
	@Column
	private Date fecha;
	
	
	private List<Juguete> juguetes;
	
	public Bolsa() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public List<Juguete> getJuguetes() {
		return juguetes;
	}
	public void setJuguetes(List<Juguete> juguetes) {
		this.juguetes = juguetes;
	}
	
	

}
