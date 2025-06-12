package quimica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Empleado extends Usuario implements Encriptador {
	private int idEmpleado;
	private static int contador = 0;
	private List<Producto> producto = new ArrayList<>();

	public Empleado(int id, String nombre, String apellido, int dni, String direccion, String localidad, int telefono,
			String email, String contrasenia, String tipo, int idEmpleado) {
		super(id, nombre, apellido, dni, direccion, localidad, telefono, email, contrasenia, tipo);
		this.idEmpleado = contador++;

	}

	public Empleado() {

	}

	public List<Producto> getProducto() {
		return producto;
	}

	public void setProducto(List<Producto> producto) {
		this.producto = producto;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	@Override

	// -----------------------------INICIO DE SESION--------------------------//
	public boolean iniciarSesion(String email, String contrasenia) {
		Connection con = Conexion.getInstance().getConnection();
		String queryUsuario = "SELECT * FROM usuario WHERE email = ? AND contrasenia = ? AND tipo = 'empleado'";

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

				JOptionPane.showMessageDialog(null, "Inicio de sesión empleado: " + getNombre());
				menuEmpleado();
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error al obtener buscar el usuario", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al consultar tabla usuario_empleado: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}

	// -----------------------------------------------------------------//

	
	// ----------------------------MENU EMPLEADO-------------------------------------//
	public void menuEmpleado() throws SQLException {
		String[] empleado = { "Categorizar Productos", "Preparar pedido", "Ver stock", "Eliminar producto",
				"Agregar producto", "Hacer facturacion", "Registrar usuario" };
		int opcionEmpleado = JOptionPane.showOptionDialog(null, "Elija que hacer.", "Acciones",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, empleado, empleado[0]);

		switch (opcionEmpleado) {
		case 0:
			categorizaProducto();//falta
			break;

		case 1:
			prepararPedido();//falta
			break;

		case 2:
			verStock();//listo
			break;

		case 3:
			eliminarProducto();//listo
			break;

		case 4:
			agregarProducto();//listo
			break;

		case 5:
			hacerFactura();//falta
			break;

		case 6:
			registrarUsuario();//listo
		}
	}

	// -----------------------------------------------------------------//
	
	@Override
	public String toString() {
		return super.toString() + "Empleado [idEmpleado=" + idEmpleado + ", producto=" + producto + "]";
	}

	// ------------------------------VER STOCK-------------------------//
	public void verStock() {
		PreparedStatement stmtProducto = null;
		ResultSet rsProducto = null;

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


//--------------------------------------------------------------------------------//
	
	
// -------------------------ELIMINAR PRODUCTO-----------------------------------------------//
	public void eliminarProducto() {
        String idProductoStr = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a eliminar:", "Eliminar Producto", JOptionPane.QUESTION_MESSAGE);

        if (idProductoStr == null || idProductoStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operación cancelada o ID no ingresado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        int idProducto = 0;
        try {
            idProducto = Integer.parseInt(idProductoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de producto inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        Connection con = null;
        PreparedStatement stmtEliminar = null;

        try {
            con = Conexion.getInstance().getConnection(); 

            String queryEliminar = "DELETE FROM producto WHERE idProducto = ?";
            stmtEliminar = con.prepareStatement(queryEliminar);

            stmtEliminar.setInt(1, idProducto);

            int filasAfectadas = stmtEliminar.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto con ID " + idProducto + " eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún producto con ID " + idProducto + ".", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
        	
            try {
                if (stmtEliminar != null) {
                    stmtEliminar.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
//--------------------------------------------------------------------------------//
	
	

	
//---------------------------------HACER FACTURA-----------------------------------------------//
	public void hacerFactura() {
		JOptionPane.showMessageDialog(null, "aca se imprime la factura");
	}
//--------------------------------------------------------------------------------//	
	
	
//------------------------------REGISTRAR UN USUARIO--------------------------------------------------//
	public void registrarUsuario() { // echo
		Connection con = Conexion.getInstance().getConnection();
		try {
			String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
			String apellido = JOptionPane.showInputDialog("Ingrese su apellido:");
			int dni = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su DNI:"));
			String direccion = JOptionPane.showInputDialog("Ingrese su dirección:");
			String localidad = JOptionPane.showInputDialog("Ingrese su localidad:");
			long telefono = Long.parseLong(JOptionPane.showInputDialog("Ingrese su teléfono:"));
			String email = JOptionPane.showInputDialog("Ingrese su email:");
			String contrasenia = JOptionPane.showInputDialog("Ingrese su contraseña:");
			String tipo = JOptionPane.showInputDialog("Tipo de usuario:");

			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO usuario (nombre, apellido, dni, direccion, localidad, telefono, email, contrasenia, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, nombre);
			stmt.setString(2, apellido);
			stmt.setInt(3, dni);
			stmt.setString(4, direccion);
			stmt.setString(5, localidad);
			stmt.setLong(6, telefono);
			stmt.setString(7, email);
			stmt.setString(8, contrasenia);
			stmt.setString(9, tipo);

			int filasInsertadas = stmt.executeUpdate();
			if (filasInsertadas > 0) {
				JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
			} else {
				JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage());
		}
	}
	//--------------------------------------------------------------------------------//
	
	
	//------------------------------AGREGAR PRODUCTO--------------------------------------------------//	
	public void agregarProducto() {
		Connection con = Conexion.getInstance().getConnection();
		try {
			String nombre = JOptionPane.showInputDialog("Ingrese nombre del producto:");
			String toxicidad = JOptionPane.showInputDialog("Ingrese toxicidad, alta/ media/ baja: ");
			String input = JOptionPane.showInputDialog("Ingrese el precio");
			int cantidad = Integer.parseInt(JOptionPane.showInputDialog("ingrese la cantidad")); 

			double precio = 0;
			try {
				if (input != null && !input.isEmpty()) {
					precio = Double.parseDouble(input);
				} else {
					System.out.println("El usuario no ingresó un valor o ingresó un valor inválido.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: El valor ingresado no es un número válido.");
			}

			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO producto (nombre, toxicidad, precio, cantidad) VALUES (?, ?, ?,?)");
			stmt.setString(1, nombre);
			stmt.setString(2, toxicidad);
			stmt.setDouble(3, precio);
			stmt.setInt(4, cantidad);

			int filasInsertadas = stmt.executeUpdate();
			if (filasInsertadas > 0) {
				JOptionPane.showMessageDialog(null, "El producto se cargo correctamente.");
			} else {
				JOptionPane.showMessageDialog(null, "Error al cargar el producto.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage());
		}
	}
	
	//--------------------------------------------------------------------------------//	

	// falta lo de abajo

	public void prepararPedido() {
		JOptionPane.showMessageDialog(null, "Aca se prepara el pedido");
	}

	public void categorizaProducto() {
		JOptionPane.showMessageDialog(null, "Aca se actualiza la toxicidad del producto");
	}

}
