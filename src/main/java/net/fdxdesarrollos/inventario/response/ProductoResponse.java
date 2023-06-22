package net.fdxdesarrollos.inventario.response;

import java.util.List;

import lombok.Data;
import net.fdxdesarrollos.inventario.model.Producto;

@Data
public class ProductoResponse {
	private List<Producto> producto;
}
