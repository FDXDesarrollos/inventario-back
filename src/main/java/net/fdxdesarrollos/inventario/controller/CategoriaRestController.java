package net.fdxdesarrollos.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.fdxdesarrollos.inventario.model.Categoria;
import net.fdxdesarrollos.inventario.response.CategoriaResponseRest;
import net.fdxdesarrollos.inventario.services.ICategoriaService;


@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping(path = "/api")
public class CategoriaRestController {
	
	@Autowired
	private ICategoriaService service;
	
	@GetMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> buscaCategorias(){
		ResponseEntity<CategoriaResponseRest> response = service.buscar();
		return response;
	}
	
	@GetMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> buscaCategoria(@PathVariable Integer id){
		ResponseEntity<CategoriaResponseRest> response = service.buscaPorId(id);
		return response;
	}
	
	@PostMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> guardar(@RequestBody Categoria categoria){
		ResponseEntity<CategoriaResponseRest> response = service.guardar(categoria);
		return response;
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> editar(@RequestBody Categoria categoria, @PathVariable Integer id){
		ResponseEntity<CategoriaResponseRest> response = service.editar(categoria, id);
		return response;
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> eliminar(@PathVariable Integer id){
		ResponseEntity<CategoriaResponseRest> response = service.eliminar(id);
		return response;
	}		
	
	
}
