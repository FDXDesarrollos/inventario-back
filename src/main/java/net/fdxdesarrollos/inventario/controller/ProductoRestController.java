package net.fdxdesarrollos.inventario.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.fdxdesarrollos.inventario.model.Producto;
import net.fdxdesarrollos.inventario.response.ProductoResponseRest;
import net.fdxdesarrollos.inventario.services.IProductoService;
import net.fdxdesarrollos.inventario.utils.CategoriaExcel;
import net.fdxdesarrollos.inventario.utils.ProductoExcel;
import net.fdxdesarrollos.inventario.utils.Util;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ProductoRestController {
	
	private IProductoService productoService;
	
	public ProductoRestController(IProductoService productoService) {
		super();
		this.productoService = productoService;
	}
	
	@GetMapping("/productos/{id}")
	public ResponseEntity<ProductoResponseRest> buscarPorId(@PathVariable Integer id){
		ResponseEntity<ProductoResponseRest> reponse = productoService.buscaPorId(id);
		return reponse;
	}
	
	@GetMapping("/productos/filtro/{nombre}")
	public ResponseEntity<ProductoResponseRest> buscarPorNombre(@PathVariable String nombre){
		ResponseEntity<ProductoResponseRest> reponse = productoService.buscaPorNombre(nombre);
		return reponse;
	}
	
	@GetMapping("/productos")
	public ResponseEntity<ProductoResponseRest> listar(){
		ResponseEntity<ProductoResponseRest> reponse = productoService.listar();
		return reponse;
	}			

	@PostMapping("/productos")
	public ResponseEntity<ProductoResponseRest> guardar(@RequestParam("nombre") String nombre,
											            @RequestParam("cateId") Integer cateId,
													    @RequestParam("precio") double precio,
											            @RequestParam("cantidad") Integer cantidad,
											            @RequestParam("imagen") MultipartFile imagen) throws IOException
	{
		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setCantidad(cantidad);
		producto.setImagen(Util.compressZLib( imagen.getBytes()));
		
		ResponseEntity<ProductoResponseRest> response = productoService.guardar(producto, cateId);
		
		return response;
	}
	
	@PutMapping("/productos/{prodId}")
	public ResponseEntity<ProductoResponseRest> editar( @RequestParam("nombre") String nombre,
			                                            @RequestParam("cateId") Integer cateId,
													    @RequestParam("precio") double precio,
			                                            @RequestParam("cantidad") Integer cantidad,
			                                            @RequestParam("imagen") MultipartFile imagen,
			                                            @PathVariable Integer prodId) throws IOException
	{
		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setCantidad(cantidad);
		producto.setImagen(Util.compressZLib( imagen.getBytes()));
		
		ResponseEntity<ProductoResponseRest> response = productoService.editar(producto, cateId, prodId);
		
		return response;
	}	
	
	@DeleteMapping("/productos/{id}")
	public ResponseEntity<ProductoResponseRest> eliminar(@PathVariable Integer id){
		ResponseEntity<ProductoResponseRest> reponse = productoService.eliminar(id);
		return reponse;
	}
	
	@GetMapping("/productos/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=productos.xlsx";
		
		response.setContentType("application/octet-stream");
		response.setHeader(headerKey, headerValue);
		
		ResponseEntity<ProductoResponseRest> productos = productoService.listar();
		
		ProductoExcel excel = new ProductoExcel(productos.getBody().getProductoResponse().getProducto());
		excel.export(response);
	}	
	



}
