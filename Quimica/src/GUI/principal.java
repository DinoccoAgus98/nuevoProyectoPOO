package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import BLL.Empleado;
import BLL.Cliente;

public class principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField campoInicio;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					principal frame = new principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --------LABEL Y BOTON INICIAR SESION------//

		JLabel iniSesion = new JLabel("Iniciar Sesion:");
		iniSesion.setForeground(new Color(135, 95, 201));
		iniSesion.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 14));
		iniSesion.setBounds(192, 53, 159, 22);
		contentPane.add(iniSesion);

		campoInicio = new JTextField();
		campoInicio.setBounds(192, 84, 159, 19);
		contentPane.add(campoInicio);
		campoInicio.setColumns(10);

		// --------LABEL Y BOTON CONTRASEÑA------//

		JLabel constrasenia = new JLabel("Contraseña:");
		constrasenia.setForeground(new Color(135, 95, 201));
		constrasenia.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 14));
		constrasenia.setBounds(192, 117, 159, 22);
		contentPane.add(constrasenia);

		passwordField = new JPasswordField();
		passwordField.setBounds(192, 149, 159, 22);
		contentPane.add(passwordField);

		// -------ICONO------------------//

		JLabel iconoInicio = new JLabel("");
		iconoInicio.setIcon(new ImageIcon("C:\\Users\\agust\\OneDrive\\Desktop\\viveroLogo.jpg"));
		iconoInicio.setBounds(-14, 66, 165, 126);
		contentPane.add(iconoInicio);

		// ----BOTON INICIAR SESION------//

		JButton btnNewButton = new JButton("Iniciar sesion");
		btnNewButton.setFont(new Font("OCR A Extended", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Empleado empleado = new Empleado();
				empleado.iniciarSesion(campoInicio.getText(), passwordField.getText());
				HomeEmpleado home = new HomeEmpleado(empleado);
				home.run(empleado);

				Cliente cliente = new Cliente();
				cliente.iniciarSesion(campoInicio.getText(), passwordField.getText());
				
				dispose();
			}
		});

		btnNewButton.setBounds(192, 192, 159, 22);
		contentPane.add(btnNewButton);

		// ------------------------------//

		// --------TITULO------//

		JLabel lblNewLabel = new JLabel("Gestion de Vivero");
		lblNewLabel.setForeground(new Color(114, 244, 51));
		lblNewLabel.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		lblNewLabel.setBounds(119, 10, 243, 33);
		contentPane.add(lblNewLabel);
	}
}
