package net.fdxdesarrollos.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.fdxdesarrollos.inventario.dao.ICategoriaDao;
import net.fdxdesarrollos.inventario.model.Categoria;
import net.fdxdesarrollos.inventario.response.CategoriaResponseRest;


@Service
public class CategoriaServiceImp implements ICategoriaService {
	
	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscar() {
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			
			response.getCategoriaResponse().setCategoria(categoria);
			response.setMetadata("Respuesta OK", "00", "Respuesta exitosa !!!");
		}
		catch(Exception e) {
			response.setMetadata("Respuesta BAD", "-1", "Error al consultar");
			e.printStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscaPorId(Integer id) {
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> lista = new ArrayList<>(); 
		
		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);
			
			if(categoria.isPresent()) {
				lista.add(categoria.get());
				response.getCategoriaResponse().setCategoria(lista);
				response.setMetadata("Respuesta OK", "00", "Categoria encontrada");
			}
			else {
				response.setMetadata("Respuesta BAD", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);				
			}
		}
		catch(Exception e) {
			response.setMetadata("Respuesta BAD", "-1", "Error al consultar");
			e.printStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> guarda(Categoria categoria) {
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> lista = new ArrayList<>(); 
		
		try {
			Categoria categoriaGuarda = categoriaDao.save(categoria);
			
			if (categoriaGuarda != null) {
				lista.add(categoriaGuarda);
				response.getCategoriaResponse().setCategoria(lista);
				response.setMetadata("Respuesta OK", "00", "Información registrada");
			}
			else {
				response.setMetadata("Respuesta BAD", "-1", "Categoria no registrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			response.setMetadata("Respuesta BAD", "-1", "Error al registrar");
			e.printStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);		
	}	

}
