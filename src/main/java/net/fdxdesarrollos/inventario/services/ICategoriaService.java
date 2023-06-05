package net.fdxdesarrollos.inventario.services;

import org.springframework.http.ResponseEntity;

import net.fdxdesarrollos.inventario.model.Categoria;
import net.fdxdesarrollos.inventario.response.CategoriaResponseRest;

public interface ICategoriaService {
	
	public ResponseEntity<CategoriaResponseRest> buscar();
	
	public ResponseEntity<CategoriaResponseRest> buscaPorId(Integer id);
	
	public ResponseEntity<CategoriaResponseRest> guardar(Categoria categoria);
	
	public ResponseEntity<CategoriaResponseRest> editar(Categoria categoria, Integer id);
	
	public ResponseEntity<CategoriaResponseRest> eliminar(Integer id);
	
}
