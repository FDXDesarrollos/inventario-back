package net.fdxdesarrollos.inventario.response;

import java.util.List;

import lombok.Data;
import net.fdxdesarrollos.inventario.model.Categoria;

@Data
public class CategoriaResponse {
	
	private List<Categoria> categoria;

}
