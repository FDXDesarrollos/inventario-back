package net.fdxdesarrollos.inventario.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import net.fdxdesarrollos.inventario.model.Producto;

public interface IProductoDao extends CrudRepository<Producto, Integer>{
	
	//@Query("select p from Producto p where p.nombre like %?1%")
	//List<Producto> findByNombreLike(String nombre);
	
	List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
