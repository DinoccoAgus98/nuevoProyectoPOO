package quimica;

public class Producto {
	private String nombre;
	private int idProducto;
	private String toxicidad;
	private double precio;
	private int cantidad;

	public Producto(String nombre, int idProducto, String toxicidad, double precio, int cantidad) {
		super();
		this.nombre = nombre;
		this.idProducto = idProducto;
		this.toxicidad = toxicidad;
		this.precio = precio;
		this.cantidad=cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getToxicidad() {
		return toxicidad;
	}

	public void setToxicidad(String toxicidad) {
		this.toxicidad = toxicidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "nombre= " + nombre + ", idProducto= " + idProducto + ", toxicidad= " + toxicidad + ", precio= $"
				+ precio + " cantidad: " + cantidad;
	}

}
