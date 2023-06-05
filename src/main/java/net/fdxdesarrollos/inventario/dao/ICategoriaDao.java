package net.fdxdesarrollos.inventario.dao;

import org.springframework.data.repository.CrudRepository;

import net.fdxdesarrollos.inventario.model.Categoria;

public interface ICategoriaDao extends CrudRepository<Categoria, Integer>{

}
