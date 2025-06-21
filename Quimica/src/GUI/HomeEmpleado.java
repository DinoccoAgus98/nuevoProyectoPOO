package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import BLL.Empleado;
import BLL.Producto;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class HomeEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


		public void run(Empleado empleado) {
				try {
					HomeEmpleado frame = new HomeEmpleado(empleado);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

	/**
	 * Create the frame.
	 */
	
	public HomeEmpleado(Empleado empleado) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//-----BIENVENIDO-----//
		JLabel lblBienvenido = new JLabel("Bienvenido " + empleado.getNombre() + " " + empleado.getApellido());
		lblBienvenido.setFont(new Font("Segoe UI Variable", Font.BOLD, 18));
		lblBienvenido.setBounds(166, 21, 382, 27);
		contentPane.add(lblBienvenido);
	
		//----Boton categorizar---//
		
		JButton btnCategorizar = new JButton("CATEGORIZAR");
		btnCategorizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Empleado empleado = new Empleado();
				empleado.categorizaProducto();
				
			}
		});
		btnCategorizar.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnCategorizar.setBounds(0, 81, 117, 21);
		contentPane.add(btnCategorizar);
		
		//-----------------------------------//
		
		JButton btnPreparar = new JButton("PREPARAR PEDIDO");
		btnPreparar.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnPreparar.setBounds(0, 101, 117, 21);
		contentPane.add(btnPreparar);
		
		//-------------BOTON STOCK----------------------//
		
		ArrayList <Producto> listaProducto = empleado.verStock();
		JButton btnVerStock = new JButton("VER STOCK");
		btnVerStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultListModel<String>modelo = new DefaultListModel<>();
				for (Producto p : listaProducto) {
					modelo.addElement(p.getNombre());
				}
				
				JList<String> list = new JList<String>(modelo);
				list.setBounds(144, 70, 361, 206);
				contentPane.add(list);
				
				setVisible(true);

						}
		});
		
		btnVerStock.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnVerStock.setBounds(0, 122, 117, 21);
		contentPane.add(btnVerStock);
		
		//-----------------------------------//
		
		JButton btnEliminar = new JButton("ELIMINAR PRODUCTO");
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnEliminar.setBounds(0, 143, 117, 21);
		contentPane.add(btnEliminar);
		
		JButton btnAgregar = new JButton("AGREGAR PRODUCTO");
		btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnAgregar.setBounds(0, 163, 117, 21);
		contentPane.add(btnAgregar);
		
		JButton btnFactura = new JButton("HACER FACTURA");
		btnFactura.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnFactura.setBounds(0, 183, 117, 21);
		contentPane.add(btnFactura);
		
		JButton btnRegistrar = new JButton("REGISTRAR USUARIO");
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnRegistrar.setBounds(0, 202, 117, 21);
		contentPane.add(btnRegistrar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\agust\\OneDrive\\Desktop\\viveroLogo.jpg"));
		lblNewLabel.setBounds(-33, 0, 167, 71);
		contentPane.add(lblNewLabel);

		
		

	}
}
