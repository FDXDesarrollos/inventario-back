package net.fdxdesarrollos.inventario.services;

import org.springframework.http.ResponseEntity;

import net.fdxdesarrollos.inventario.model.Producto;
import net.fdxdesarrollos.inventario.response.ProductoResponseRest;

public interface IProductoService {
	public ResponseEntity<ProductoResponseRest> guardar(Producto producto, Integer categoriaId);
	public ResponseEntity<ProductoResponseRest> editar(Producto producto, Integer cateId, Integer prodId);
	public ResponseEntity<ProductoResponseRest> eliminar(Integer id);
	public ResponseEntity<ProductoResponseRest> listar();	
	public ResponseEntity<ProductoResponseRest> buscaPorId(Integer id);
	public ResponseEntity<ProductoResponseRest> buscaPorNombre(String nombre);
}
