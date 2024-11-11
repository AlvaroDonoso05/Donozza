package modelo;

public class Comanda {
	
	public String nombre;
	public Integer cantidad, precio;
	
	public Comanda(String nombre, Integer cantidad, Integer precio) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Integer getPrecio() {
		return precio;
	}
	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

}
