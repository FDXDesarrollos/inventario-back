package net.fdxdesarrollos.inventario.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class Producto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private double precio;
	private Integer cantidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters=true)
	private Categoria categoria;
	
	//@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="imagen", length = 1000)
	private byte[] imagen;
}
