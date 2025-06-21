package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DLL.Conexion;

public class Cliente extends Usuario {
	private String tipoCliente;
	private int idCliente;
	private boolean autorizado = false;

	public Cliente(int id, String nombre, String apellido, int dni, String direccion, String localidad, int telefono,
			String email, String contrasenia, String tipo, String tipoClienta, int idCliente, boolean autorizado) {
		super(id, nombre, apellido, dni, direccion, localidad, telefono, email, contrasenia, tipo);
		this.tipoCliente = tipoClienta;
		this.idCliente = idCliente;
		this.autorizado = autorizado;
	}

	public Cliente() {

	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public String getTipoClienta() {
		return tipoCliente;
	}

	public void setTipoClienta(String tipoClienta) {
		this.tipoCliente = tipoClienta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public boolean isAutorizado() {
		return autorizado;
	}
	
	//-----------------------METODOS---------------//

	public void listarProducto(List<Producto> producto) {
		if (producto == null || producto.isEmpty()) {
			JOptionPane.showMessageDialog(null, "la lista esta vacia");
		} else {
			JOptionPane.showMessageDialog(null, "productos listados");
			StringBuilder lista = new StringBuilder("Productos listados: \n");
			for (Producto p : producto) {
				lista.append(p.toString()).append("\n");
			}
			JOptionPane.showMessageDialog(null, lista.toString());
		}

	}

	public void hacerPedido() {
		JOptionPane.showMessageDialog(null, "Aca estaran los productos pedidos");
	}

	
	//------------PARECIDO A VER STOCK---------------//
	public void verProductos() {
		PreparedStatement stmtProducto = null;
		ResultSet rsProducto = null;
		List<Producto> producto = new ArrayList<>();

		try {
			Connection con = Conexion.getInstance().getConnection();
			String queryProducto = "SELECT * FROM producto";
			stmtProducto = con.prepareStatement(queryProducto);
			rsProducto = stmtProducto.executeQuery();

			producto.clear();
			while (rsProducto.next()) {

				int idProducto = rsProducto.getInt("idProducto");
				String toxicidad = rsProducto.getString("toxicidad");
				String nombre = rsProducto.getString("nombre");
				double precio = rsProducto.getDouble("precio");
				int cantidad = rsProducto.getInt("cantidad");

				Producto p = new Producto(nombre, idProducto, toxicidad, precio, cantidad);
				producto.add(p);
			}

			if (producto.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No hay productos en stock.", "Stock",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				StringBuilder mensajeStock = new StringBuilder("Stock de Productos:\n");
				for (Producto p : producto) {
					mensajeStock.append(p.getIdProducto()).append(" - ").append(p.getNombre()).append(" - ")
							.append(p.getPrecio()).append(" - ").append(p.getCantidad()).append("\n");
				}
				JOptionPane.showMessageDialog(null, mensajeStock.toString(), "Stock de Productos",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al consultar la tabla de productos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				if (rsProducto != null) {
					rsProducto.close();
				}
				if (stmtProducto != null) {
					stmtProducto.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}

	}
	
//----------------------------------------------------------------------//	

	public void verPerfil() {
		JOptionPane.showMessageDialog(null, "Perfil" + nombre + " " + apellido);
	}

	public void subirPermiso() {
		autorizado = true;
		JOptionPane.showMessageDialog(null, "El cliente tiene permiso");

	}
	
	@Override
	public String toString() {
		return super.toString() + "Cliente [tipoClienta=" + tipoCliente + ", idCliente=" + idCliente + ", autorizado="
				+ autorizado + "]";
	}
	
	//------------------------------MENU CLIENTE-----------------------//

	public void menuCliente() {
		String[] cliente = { "Listar Productos", "Hacer pedido", "Ver productos", "Ver perfil", "Subir permiso" };
		int opcionCliente = JOptionPane.showOptionDialog(null, "Elija que hacer.", "Acciones",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, cliente, cliente[0]);

		//LISTA PARA EJEMPLO, DESPUES ELMINAR----//
		List<Producto> listaDeProducto = new ArrayList<>();
		listaDeProducto.add(new Producto("Veneno rata", 1, "alta", 500, 3));
		listaDeProducto.add(new Producto("jabon de piso", 2, "baja", 100, 5));
		//-----------------------------------------------------//

		switch (opcionCliente) {
		case 0:
			listarProducto(listaDeProducto);//FALTA
			break;

		case 1:
			hacerPedido();//FALTA
			break;

		case 2:
			verProductos();//LISTO
			break;

		case 3:
			verPerfil();//FALTA
			break;

		case 4:
			subirPermiso();//FALTA
			break;

		}
	}

	@Override
	
	//--------------------------INICIAR SESION----------------//
    public boolean iniciarSesion(String email, String contrasenia) {
		Connection con = Conexion.getInstance().getConnection();
        String queryUsuario = "SELECT * FROM usuario WHERE email = ? AND contrasenia = ? AND tipo = 'cliente'";
        
        try (PreparedStatement stmtUsuario = con.prepareStatement(queryUsuario)) {
			stmtUsuario.setString(1, email);
			stmtUsuario.setString(2, contrasenia);
			ResultSet rsUsuario = stmtUsuario.executeQuery();

			if (rsUsuario.next()) {
				this.id = rsUsuario.getInt("id");
				this.nombre = rsUsuario.getString("nombre");
				this.apellido = rsUsuario.getString("apellido");
				this.dni = rsUsuario.getInt("dni");
				this.direccion = rsUsuario.getString("direccion");
				this.localidad = rsUsuario.getString("localidad");
				this.telefono = rsUsuario.getInt("telefono");
				this.email = rsUsuario.getString("email");
				this.contrasenia = rsUsuario.getString("contrasenia");
				this.tipo = rsUsuario.getString("tipo");

				JOptionPane.showMessageDialog(null, "Inicio de sesión cliente: " + getNombre());
				menuCliente();
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error al obtener buscar el usuario", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al consultar tabla usuario_cliente " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}

}
