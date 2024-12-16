import java.awt.EventQueue;

import java.util.Vector;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class Imenik {

	private JFrame frmImenikVranjesMomcilo;
	private JTextField txtIme;
	private JTextField txtAdresa;
	private JTextField txtBroj;
	private JTextField txtID;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Imenik window = new Imenik();
					window.frmImenikVranjesMomcilo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Imenik() {
		initialize();
		Connect();
		table();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	ResultSetMetaData rd;
	DefaultTableModel model;

	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost/imenik", "root", "");
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void table() {
		
		int a;
		try {
			pst = con.prepareStatement("select * from osobe");
			rs=pst.executeQuery();
			
			rd = rs.getMetaData();
			a = rd.getColumnCount();
			model= (DefaultTableModel)table.getModel();
			model.setRowCount(0);
			
			while(rs.next()) {
				Vector v = new Vector();
				
				for(int i =1; i<=a; i++) {
					v.add(rs.getString("id"));
					v.add(rs.getString("ime"));
					v.add(rs.getString("adresa"));
					v.add(rs.getString("telefon"));
					
				}
				
				model.addRow(v);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmImenikVranjesMomcilo = new JFrame();
		frmImenikVranjesMomcilo.setTitle("Imenik Vranjes Momcilo MIT13/24");
		frmImenikVranjesMomcilo.setBounds(100, 100, 828, 543);
		frmImenikVranjesMomcilo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmImenikVranjesMomcilo.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Imenik");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 47));
		lblNewLabel.setBounds(241, 34, 290, 76);
		frmImenikVranjesMomcilo.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 64, 128));
		panel.setBorder(new TitledBorder(null, "Unos podataka", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel.setBounds(27, 152, 325, 149);
		frmImenikVranjesMomcilo.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ime");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 26, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Adresa");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 58, 54, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Broj telefona");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 100, 97, 14);
		panel.add(lblNewLabel_1_2);
		
		txtIme = new JTextField();
		txtIme.setBounds(134, 25, 181, 20);
		panel.add(txtIme);
		txtIme.setColumns(10);
		
		txtAdresa = new JTextField();
		txtAdresa.setColumns(10);
		txtAdresa.setBounds(134, 57, 181, 20);
		panel.add(txtAdresa);
		
		txtBroj = new JTextField();
		txtBroj.setColumns(10);
		txtBroj.setBounds(134, 99, 181, 20);
		panel.add(txtBroj);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Pretraga korisnika",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 128)));
		panel_1.setBounds(27, 343, 325, 68);
		frmImenikVranjesMomcilo.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 29, 28, 14);
		panel_1.add(lblNewLabel_2);
		
		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String id = txtID.getText();
					pst = con.prepareStatement("select ime, adresa, telefon from osobe where id =?");
					pst.setString(1,id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true) {
						String ime = rs.getString(1);
						String adresa = rs.getString(2);
						String broj = rs.getString(3);
						
						txtIme.setText(ime);
						txtAdresa.setText(adresa);
						txtBroj.setText(broj);
					}
					else {
						txtIme.setText("");
						txtAdresa.setText("");
						txtBroj.setText("");
					}
				
				}
				
				catch(SQLException ex) {}
			}
		});
		txtID.setBounds(48, 26, 168, 20);
		panel_1.add(txtID);
		txtID.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Pronađi");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(226, 25, 89, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ime, adresa;
				int broj;
				
				ime = txtIme.getText();
				adresa=txtAdresa.getText();
				broj = Integer.parseInt(txtBroj.getText());
				try {
					pst = con.prepareStatement("insert into osobe(ime,adresa,telefon)values(?,?,?)");
					pst.setString(1, ime);
					pst.setString(2, adresa);
					pst.setInt(3, broj);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Uspesno dodato");
					table();
					
					txtIme.setText("");
					txtAdresa.setText("");
					txtBroj.setText("");
					txtIme.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		btnDodaj.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnDodaj.setForeground(new Color(255, 0, 0));
		btnDodaj.setBounds(442, 353, 89, 47);
		frmImenikVranjesMomcilo.getContentPane().add(btnDodaj);
		
		JButton btnIzmeni = new JButton("Izmeni");
		btnIzmeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String ime, adresa;
				int broj,id;
				
				id=Integer.parseInt(txtID.getText());
				
				ime = txtIme.getText();
				adresa=txtAdresa.getText();
				broj = Integer.parseInt(txtBroj.getText());
				try {
					pst = con.prepareStatement("update osobe set ime=?,adresa=?,telefon=? where id=?");
					pst.setString(1, ime);
					pst.setString(2, adresa);
					pst.setInt(3, broj);
					pst.setInt(4, id);	
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Uspesno izmenjeno");
					table();
					
					txtIme.setText("");
					txtAdresa.setText("");
					txtBroj.setText("");
					txtID.setText("");
					txtIme.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnIzmeni.setForeground(Color.RED);
		btnIzmeni.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnIzmeni.setBounds(541, 353, 89, 47);
		frmImenikVranjesMomcilo.getContentPane().add(btnIzmeni);
		
		JButton btnObrisi = new JButton("Obriši");
		btnObrisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				String id;
				
				id = txtID.getText();
				
				
				try {
					pst = con.prepareStatement("delete from osobe where id=?");
					
					pst.setString(1, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Uspesno obrisano");
					table();
					
					txtIme.setText("");
					txtAdresa.setText("");
					txtBroj.setText("");
					txtIme.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnObrisi.setForeground(Color.RED);
		btnObrisi.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnObrisi.setBounds(640, 353, 89, 47);
		frmImenikVranjesMomcilo.getContentPane().add(btnObrisi);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(420, 152, 325, 149);
		frmImenikVranjesMomcilo.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Ime", "Adresa", "Broj telefona"
			}
		));
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("D:\\Downloads\\Dario-Arnaez-Genesis-3G-Emails-Folder.96 (1).png"));
		lblNewLabel_3.setBounds(64, 34, 96, 98);
		frmImenikVranjesMomcilo.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("");
		lblNewLabel_3_1.setIcon(new ImageIcon("D:\\Downloads\\Dario-Arnaez-Genesis-3G-Emails-Folder.96 (1).png"));
		lblNewLabel_3_1.setBounds(618, 34, 96, 98);
		frmImenikVranjesMomcilo.getContentPane().add(lblNewLabel_3_1);
	}
}
