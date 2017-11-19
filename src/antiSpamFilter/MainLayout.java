package antiSpamFilter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class MainLayout {

	private JFrame frame;
	private JTextField txtFicheiro1;
	private JTextField txtFicheiro2;
	private JTextField txtFicheiro3;
	private JPanel panelFileChoose;
	private JButton btnFicheiro1;
	private JButton btnFicheiro2;
	private JButton btnFicheiro3;
	private JButton btnSaveConfiguration;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainLayout window = new MainLayout();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainLayout() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("150dlu:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		txtFicheiro1 = new JTextField();
		txtFicheiro2 = new JTextField();
		txtFicheiro3 = new JTextField();
		txtFicheiro1.setColumns(10);
		txtFicheiro2.setColumns(10);
		txtFicheiro3.setColumns(10);
		panel.add(txtFicheiro1, "2, 2, fill, default");
		panel.add(txtFicheiro2, "2, 4, fill, default");
		panel.add(txtFicheiro3, "2, 6, fill, default");
		btnFicheiro1 = new JButton("");
		btnFicheiro2 = new JButton("");
		btnFicheiro3 = new JButton("");
		panel.add(btnFicheiro1, "4, 2");
		panel.add(btnFicheiro2, "4, 4");
		panel.add(btnFicheiro3, "4, 6, left, default");
		btnSaveConfiguration = new JButton("ConfigurarCaminho");
		panel.add(btnSaveConfiguration, "2, 8, fill, default");
		btnSaveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnFicheiro1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(btnSaveConfiguration);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    txtFicheiro1.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnFicheiro2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(btnSaveConfiguration);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    txtFicheiro2.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnFicheiro3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(btnSaveConfiguration);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    txtFicheiro3.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		
		
		panelFileChoose = new JPanel();
		frame.getContentPane().add(panelFileChoose, BorderLayout.WEST);
		panelFileChoose.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("140dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("140dlu:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20dlu:grow"),}));
		
		
	}

}
