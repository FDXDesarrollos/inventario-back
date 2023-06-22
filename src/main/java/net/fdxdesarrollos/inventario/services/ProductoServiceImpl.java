package net.fdxdesarrollos.inventario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.fdxdesarrollos.inventario.dao.ICategoriaDao;
import net.fdxdesarrollos.inventario.dao.IProductoDao;
import net.fdxdesarrollos.inventario.model.Categoria;
import net.fdxdesarrollos.inventario.model.Producto;
import net.fdxdesarrollos.inventario.response.ProductoResponseRest;
import net.fdxdesarrollos.inventario.utils.Util;

@Service
public class ProductoServiceImpl implements IProductoService {
	
	private ICategoriaDao categoriaDao;
	private IProductoDao productoDao;
	
	public ProductoServiceImpl(ICategoriaDao categoriaDao, IProductoDao productoDao) {
		super();
		this.categoriaDao = categoriaDao;
		this.productoDao = productoDao;
	}

	@Override
	@Transactional
	public ResponseEntity<ProductoResponseRest> guardar(Producto producto, Integer categoriaId) {
		ProductoResponseRest response = new ProductoResponseRest();
		List<Producto> lista = new ArrayList<>();
		
		try {
			// Buscar categoria para establecer en el objeto producto
			Optional<Categoria> categoria = categoriaDao.findById(categoriaId);
			
			if(categoria.isPresent()) {
				producto.setCategoria(categoria.get());
			}
			else {
				response.setMetadata("respuesta Err", "-1", "Categoria no asociada al producto");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			Producto productoGuarda = productoDao.save(producto);
			
			if(productoGuarda != null) {
				lista.add(productoGuarda);
				response.getProductoResponse().setProducto(lista);
				response.setMetadata("respuesta OK", "00", "Información registrada");
			}
			else {
				response.setMetadata("respuesta Err", "-1", "Información no registrada");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			response.setMetadata("respuesta Err", "-1", "Error al registrar");
			return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductoResponseRest> buscaPorId(Integer Id) {
		ProductoResponseRest response = new ProductoResponseRest();
		List<Producto> lista = new ArrayList<>();
		
		try {
			Optional<Producto> producto = productoDao.findById(Id);
			
			if(producto.isPresent()) {
				byte[] imagen = Util.decompressZLib(producto.get().getImagen());
				producto.get().setImagen(imagen);
				lista.add(producto.get());
				response.getProductoResponse().setProducto(lista);
				response.setMetadata("respuesta OK", "00", "Producto encontrado");
			}
			else {
				response.setMetadata("respuesta Err", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			response.setMetadata("respuesta Err", "-1", "Error al buscar por Id");
			return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductoResponseRest> buscaPorNombre(String nombre) {
		ProductoResponseRest response = new ProductoResponseRest();
		List<Producto> lista = new ArrayList<>();
		List<Producto> listaAux = new ArrayList<>();
		
		try {
			listaAux = productoDao.findByNombreContainingIgnoreCase(nombre);
			
			if(listaAux.size() > 0) {
				listaAux.stream().forEach( (p) -> {
					byte[] imagen = Util.decompressZLib(p.getImagen());
					p.setImagen(imagen);
					lista.add(p);					
				});

				response.getProductoResponse().setProducto(lista);
				response.setMetadata("respuesta OK", "00", "Producto encontrado");
			}
			else {
				response.setMetadata("respuesta Err", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			response.setMetadata("respuesta Err", "-1", "Error al buscar por nombre");
			return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductoResponseRest> eliminar(Integer id) {
		ProductoResponseRest response = new ProductoResponseRest();
		
		try {
			productoDao.deleteById(id);
			response.setMetadata("respuesta OK", "00", "Producto eliminado");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			response.setMetadata("respuesta Err", "-1", "Error al eliminar");
			return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductoResponseRest> listar() {
		ProductoResponseRest response = new ProductoResponseRest();
		List<Producto> lista = new ArrayList<>();
		List<Producto> listaAux = new ArrayList<>();
		
		try {
			listaAux = (List<Producto>) productoDao.findAll();
			
			if(listaAux.size() > 0) {
				listaAux.stream().forEach( (p) -> {
					byte[] imagen = Util.decompressZLib(p.getImagen());
					p.setImagen(imagen);
					lista.add(p);					
				});

				response.getProductoResponse().setProducto(lista);
				response.setMetadata("respuesta OK", "00", "Productos encontrados");
			}
			else {
				response.setMetadata("respuesta Err", "-1", "Productos no encontrados");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			response.setMetadata("respuesta Err", "-1", "Error al listar productos");
			return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductoResponseRest> editar(Producto producto, Integer cateId, Integer prodId) {
		ProductoResponseRest response = new ProductoResponseRest();
		List<Producto> lista = new ArrayList<>();
		
		try {
			// Buscar categoria para establecer en el objeto producto
			Optional<Categoria> categoria = categoriaDao.findById(cateId);
			
			if(categoria.isPresent()) {
				producto.setCategoria(categoria.get());
			}
			else {
				response.setMetadata("respuesta Err", "-1", "Categoria no asociada al producto");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			Optional<Producto> buscaProducto = productoDao.findById(prodId);
			
			if(buscaProducto.isPresent()) {
				buscaProducto.get().setNombre(producto.getNombre());
				buscaProducto.get().setCategoria(producto.getCategoria());
				buscaProducto.get().setPrecio(producto.getPrecio());
				buscaProducto.get().setCantidad(producto.getCantidad());
				buscaProducto.get().setImagen(producto.getImagen());
				
				Producto prod = productoDao.save(buscaProducto.get());
				
				if(prod != null) {
					lista.add(prod);
					response.getProductoResponse().setProducto(lista);
					response.setMetadata("respuesta OK", "00", "Información actualizada");
				}
				else {
					response.setMetadata("respuesta Err", "-1", "Información no actualizada");
					return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.BAD_REQUEST);					
				}
			}
			else {
				response.setMetadata("respuesta Err", "-1", "Información no encontrada");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			response.setMetadata("respuesta Err", "-1", "Error al actualizar");
			return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
	}

}
