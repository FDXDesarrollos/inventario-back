package net.fdxdesarrollos.inventario.services;

import org.springframework.http.ResponseEntity;

import net.fdxdesarrollos.inventario.response.CategoriaResponseRest;

public interface ICategoriaService {
	
	public ResponseEntity<CategoriaResponseRest> buscar();

}
