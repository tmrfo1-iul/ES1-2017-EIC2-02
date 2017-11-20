package antiSpamFilter;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class MainLayout {
	private JFrame frame;
	private CardLayout cardLayout;
	private final String MAIN_PANEL = "Main Panel";
	private final String PATH_PANEL = "Path Panel";
	
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardLayout = new CardLayout();
		frame.getContentPane().setLayout(cardLayout);
		frame.getContentPane().add(createPathPanel(), PATH_PANEL);
		frame.getContentPane().add(createMainPanel(), MAIN_PANEL);
		showPathPanel();
	}

	
	/**
	 * Initialize the contents of the panel to specify paths.
	 */
	private JPanel createPathPanel(){
		JPanel pathPanel = new JPanel(new BorderLayout());
		JPanel panel = new JPanel();
		pathPanel.add(panel, BorderLayout.NORTH);
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
		JTextField txtRules = new JTextField();
		JTextField txtHam = new JTextField();
		JTextField txtLog = new JTextField();
		txtRules.setEditable(false);
		txtHam.setEditable(false);
		txtLog.setEditable(false);
		txtRules.setColumns(10);
		txtHam.setColumns(10);
		txtLog.setColumns(10);
		panel.add(txtRules, "2, 2, fill, default");
		panel.add(txtHam, "2, 4, fill, default");
		panel.add(txtLog, "2, 6, fill, default");
		JButton btnRules = new JButton("");
		JButton btnHam = new JButton("");
		JButton btnLog = new JButton("");
		panel.add(btnRules, "4, 2");
		panel.add(btnHam, "4, 4");
		panel.add(btnLog, "4, 6, left, default");
		JButton btnSaveConfiguration = new JButton("ConfigurarCaminho");
		panel.add(btnSaveConfiguration, "2, 8, fill, default");
		btnSaveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showMainPanel();
			}
		});
		

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(btnSaveConfiguration);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					txtRules.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnHam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(btnSaveConfiguration);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					txtHam.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		
		btnLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(btnSaveConfiguration);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					txtLog.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		
		JPanel panelFileChoose = new JPanel();
		pathPanel.add(panelFileChoose, BorderLayout.WEST);
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
		
		return pathPanel;
	}
	
	
	/**
	 * Displays the panel to specify paths.
	 */
	private void showPathPanel() {
		cardLayout.show(frame.getContentPane(), PATH_PANEL);
	}
	

	/**
	 * Initialize the contents of the panel to visualize filter data.
	 */
	private JPanel createMainPanel(){
		JPanel mainPanel = new JPanel(null);
		JButton btnCalcular = new JButton("Calcular FP/FN");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		btnCalcular.setBounds(18, 320, 120, 25);
		mainPanel.add(btnCalcular);
		
		JButton btnGenerate = new JButton("Gerar Pesos");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		btnGenerate.setBounds(145, 320, 150, 25);
		mainPanel.add(btnGenerate);
		
		JButton btnChangePath = new JButton("Alterar path");
		btnChangePath.setBounds(300, 320, 120, 25);
		btnChangePath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPathPanel();
			}
		});
		mainPanel.add(btnChangePath);
		
		JScrollPane scrollPaneRules = new JScrollPane();
		scrollPaneRules.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneRules.setBounds(17, 20, 400, 200);
		mainPanel.add(scrollPaneRules);
		
		JTable tableRules = new JTable();
		tableRules.setModel(getRulesAndWeightsTable(false));
		scrollPaneRules.setViewportView(tableRules);
		
		JLabel lblResults = new JLabel("Resultados:");
		lblResults.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblResults.setBounds(185, 240, 80, 14);
		mainPanel.add(lblResults);
		
		JScrollPane scrollPaneResults = new JScrollPane();
		scrollPaneResults.setViewportBorder(null);
		scrollPaneResults.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneResults.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneResults.setBounds(17, 260, 400, 40);
		mainPanel.add(scrollPaneResults);
		
		JTable tableResults = new JTable();
		tableResults.setShowHorizontalLines(false);
		tableResults.setFillsViewportHeight(true);
		tableResults.setRowSelectionAllowed(false);
		tableResults.setModel(getResultsTable());
		scrollPaneResults.setViewportView(tableResults);
		return mainPanel;
	}


	/**
	 * Displays the panel to visualize filter data.
	 */
	private void showMainPanel(){
		cardLayout.show(frame.getContentPane(), MAIN_PANEL);
	}

	
	private TableModel getRulesAndWeightsTable(boolean editable) {
		// TODO
		DefaultTableModel model =  new DefaultTableModel(
			new Object[][] {
				{"c1", "p1"},
				{"c2", "p2"},
				{"c3", "p3"},
				{"c4", "p4"},
				{"c5", "p5"},
				{"c6", "p6"},
				{"c7", "p7"},
				{"c8", "p8"},
				{"c9", "p9"},
				{"c10", "p10"},
				{"c11", "p11"},
				{"c12", "p12"},
				{"c13", "p13"},
				{"c14", "p14"},
				{"c15", "p15"},
				{"c16", "p16"}
			},
			new String[] {
				"Regras", "Pesos"
			})
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return editable;
			}	
		};
		
		return model;
	}
	
	
	private TableModel getResultsTable() {
		// TODO
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
				{"0", "0"},
			},
			new String[] {
					"Falsos Positivos", "Falsos Negativos"
			})
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return model;	
	}
}
