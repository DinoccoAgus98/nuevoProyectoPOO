package quimica;

import javax.swing.JOptionPane;

public class Test {

	public static void main(String[] args) {
		
		String[] opciones = { "Iniciar sesion", "Salir" };
			int elejido = JOptionPane.showOptionDialog(null, "¿Desesa iniciar Sesion?", "Iniciar sesion",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
			switch (elejido) {
			case 0:
				
				String email=JOptionPane.showInputDialog("Email");
				String contrasenia = JOptionPane.showInputDialog("Contraseña");
				
				 if (email!=null || contrasenia!=null || !email.isEmpty() || !contrasenia.isEmpty()) {
			            Empleado empleadoPrueba = new Empleado(0, "","", 0, "", "", 0, email, contrasenia, "empleado", 0);
			            Cliente clientePrueba = new Cliente(0,"","",0,"","",0, email, contrasenia, "cliente", "", 0, true);
			            clientePrueba.iniciarSesion(email, contrasenia);
			            empleadoPrueba.iniciarSesion(email, contrasenia);
				  }else {
					  System.out.println("Credenciales incorrectas");
				  }
				
				break;
			}
			
		}

}

