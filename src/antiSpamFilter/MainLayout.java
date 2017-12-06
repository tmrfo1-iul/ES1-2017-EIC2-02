package antiSpamFilter;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

public class MainLayout {

	private JFrame frame;
	private JTextField txtRules;
	private JTextField txtHam;
	private JTextField txtSpam;
	private JTable table_configuracaoManual;
	private JTextField CM_tx_FalsePositive;
	private JTextField CM_tx_FalseNegative;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table_configuracaoAutomatica;
	private JTextField A_tx_FalsePositive;
	private JTextField A_tx_FalseNegative;
	private HashMap<String, Integer> rulesMap = new HashMap<String, Integer>();

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
		readSavedFilePaths();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("AntiSpamConfigurationForLeisureMailbox");
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 667);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "Do you wish to save the File Paths?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					saveFilePaths();
				}
				System.exit(0);
			}

		});

		JLabel lblRules = new JLabel("Rules");

		JLabel lblHam = new JLabel("Ham");

		JLabel lblSpam = new JLabel("Spam");

		txtRules = new JTextField();
		txtRules.setEditable(false);
		txtRules.setColumns(10);

		txtHam = new JTextField();
		txtHam.setEditable(false);
		txtHam.setColumns(10);

		txtSpam = new JTextField();
		txtSpam.setEditable(false);
		txtSpam.setColumns(10);

		JButton btnRules = new JButton("");
		btnRules.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));

		JButton btnHam = new JButton("");
		btnHam.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));

		JButton btnSpam = new JButton("");
		btnSpam.setIcon(new ImageIcon(MainLayout.class.getResource("/imageWindowBuilder/foldericon.png")));
		JFileChooser fileChooserRules = new JFileChooser();
		JFileChooser fileChooserOther = new JFileChooser();
		FileNameExtensionFilter filterRules = new FileNameExtensionFilter("CF Files", "cf", "cf");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LOG Files", "log", "log");
		fileChooserRules.setFileFilter(filterRules);
		fileChooserOther.setFileFilter(filter);
		fileChooserRules.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooserOther.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooserRules.showOpenDialog(btnRules);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserRules.getSelectedFile();
					if(selectedFile.exists()){
					txtRules.setText(selectedFile.getAbsolutePath());
					}
				}
			}
		});
		btnHam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooserOther.showOpenDialog(btnHam);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserOther.getSelectedFile();
					if(selectedFile.exists()){
						txtHam.setText(selectedFile.getAbsolutePath());
						}
				}
			}
		});

		btnSpam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooserOther.showOpenDialog(btnSpam);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooserOther.getSelectedFile();
					if(selectedFile.exists()){
						txtSpam.setText(selectedFile.getAbsolutePath());
						}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JButton btnAvaliar = new JButton("Evaluate");
		btnAvaliar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluationManual();
				// TODO avaliar pesos da avaliacao manual
			}
		});

		JButton btnGravar = new JButton("Save");
		btnGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO guardar os pesos manuais para um ficheiro
			}
		});

		CM_tx_FalsePositive = new JTextField();
		CM_tx_FalsePositive.setEditable(false);
		CM_tx_FalsePositive.setColumns(10);

		CM_tx_FalseNegative = new JTextField();
		CM_tx_FalseNegative.setEditable(false);
		CM_tx_FalseNegative.setColumns(10);

		JLabel CM_lblFalse_Positive = new JLabel("False Positive:");

		JLabel CM_lblFalse_Negative = new JLabel("False Negative:");

		JLabel label = new JLabel("False Negative:");

		textField_2 = new JTextField();
		textField_2.setColumns(10);

		JLabel label_1 = new JLabel("False Positive:");

		textField_3 = new JTextField();
		textField_3.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();

		JScrollPane scrollPane_2 = new JScrollPane();

		JButton btnGerarConfigurao = new JButton("Generate configuration");
		btnGerarConfigurao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO gerar configuracao optima
			}
		});

		JButton btnGravar_1 = new JButton("Save");
		btnGravar_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO guardar os pesos optimos para um ficheiro
			}
		});

		JLabel A_lblFalsePositive = new JLabel("False Positive:");

		JLabel A_lblFalseNegative = new JLabel("False Negative:");

		A_tx_FalsePositive = new JTextField();
		A_tx_FalsePositive.setEditable(false);
		A_tx_FalsePositive.setColumns(10);

		A_tx_FalseNegative = new JTextField();
		A_tx_FalseNegative.setEditable(false);
		A_tx_FalseNegative.setColumns(10);
		
		JButton btnFilePaths = new JButton("Carregar ficheiros");
		btnFilePaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processRulesFile();
			}
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblHam, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtHam, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnHam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblRules)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtRules, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(btnRules, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblSpam, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txtSpam, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(btnFilePaths)
											.addGap(130)))
									.addComponent(btnSpam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
							.addGap(49)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(39)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnGravar, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnAvaliar, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(A_lblFalseNegative)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(A_tx_FalseNegative, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(A_lblFalsePositive)
										.addGap(10)
										.addComponent(A_tx_FalsePositive, 0, 0, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(btnGerarConfigurao))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(29)
									.addComponent(btnGravar_1, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(CM_lblFalse_Negative)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(CM_tx_FalseNegative, 0, 0, Short.MAX_VALUE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(CM_lblFalse_Positive)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(CM_tx_FalsePositive, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRules)
						.addComponent(txtRules, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRules, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtHam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHam)
						.addComponent(btnHam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSpam)
						.addComponent(txtSpam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSpam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addComponent(btnAvaliar)
							.addGap(18)
							.addComponent(btnGravar)
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(CM_lblFalse_Positive)
								.addComponent(CM_tx_FalsePositive, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(CM_lblFalse_Negative)
								.addComponent(CM_tx_FalseNegative, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnGerarConfigurao)
							.addGap(18)
							.addComponent(btnGravar_1)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(A_lblFalsePositive)
								.addComponent(A_tx_FalsePositive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(A_lblFalseNegative)
								.addComponent(A_tx_FalseNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(344)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(436)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(label_1))
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(label)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(118)
					.addComponent(btnFilePaths))
		);

		table_configuracaoAutomatica = new JTable();
		table_configuracaoAutomatica.setEnabled(false);
		table_configuracaoAutomatica.setRowSelectionAllowed(false);
		table_configuracaoAutomatica.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Rules", "Weights"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_configuracaoAutomatica.getColumnModel().getColumn(0).setResizable(false);
		table_configuracaoAutomatica.getColumnModel().getColumn(1).setResizable(false);
		scrollPane_2.setViewportView(table_configuracaoAutomatica);
		table_configuracaoManual = new JTable();
		table_configuracaoManual.setRowSelectionAllowed(false);
		table_configuracaoManual
				.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Rules", "Weights"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_configuracaoManual.getColumnModel().getColumn(0).setResizable(false);
		table_configuracaoManual.getColumnModel().getColumn(1).setResizable(false);
		scrollPane.setViewportView(table_configuracaoManual);
		frame.getContentPane().setLayout(groupLayout);
	}
	private void saveFilePaths(){
		try {
			PrintWriter writer = new PrintWriter("savedFilePaths","UTF-8");
				writer.println(txtRules.getText());
				writer.println(txtHam.getText());
				writer.print(txtSpam.getText());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readSavedFilePaths(){
		File file = new File("savedFilePaths");
		String i;
	    try {
	        Scanner sc = new Scanner(file);
	        if(sc.hasNextLine()){   
	        	i = sc.nextLine();
	            txtRules.setText(i);
	            if(sc.hasNextLine()){
	            	i = sc.nextLine();
	            	txtHam.setText(i);
	            	if(sc.hasNextLine()){
	            		i = sc.nextLine();
	            		txtSpam.setText(i);
	            	}
	            }
	        sc.close();
	        } 
	    }
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	private void processRulesFile(){
		String line=null;
		DefaultTableModel modelManual = (DefaultTableModel)table_configuracaoManual.getModel();
		DefaultTableModel modelAutomatico = (DefaultTableModel)table_configuracaoAutomatica.getModel();
		Path path = Paths.get(txtRules.getText());
		try {
			Scanner scanner = new Scanner(path);
			int it = 0;
			while (scanner.hasNextLine()) {
				line=scanner.nextLine();
				rulesMap.put(line, it);
				modelAutomatico.addRow(new Object[]{line, null});
				modelManual.addRow(new Object[]{line, null});
				it++;
			}
			scanner.close();

		} catch (IOException e) {
			
		}
	}
	private void evaluationManual(){
		AntiSpamFilterProblem problem=null;
		SpamSolution solution=null;
		int size = rulesMap.size();
		double temp=0.0;
		DefaultTableModel modelManual = (DefaultTableModel)table_configuracaoManual.getModel();
		try {
			problem = new AntiSpamFilterProblem(txtRules.getText(),txtSpam.getText(),txtHam.getText(),rulesMap);
			solution = new SpamSolution(-5.0,5.0,2,size);
			  for(int i=0;i<size;i++){
				  if(modelManual.getValueAt(i, 1)==null){
					  temp=0.0;
					  solution.setVariableValue(i, temp);
				  }
				  else{
					  temp=(double)modelManual.getValueAt(i, 1);
				  if(temp>=5.0)
					  solution.setVariableValue(i, 5.0);
				  if(temp<=-5.0)
					  solution.setVariableValue(i,-5.0);
				  if(temp>5.0 && temp<-5.0 )
					  solution.setVariableValue(i, temp);

				  }
			  }
			  problem.evaluate(solution);
			  CM_tx_FalsePositive.setText(Double.toString(solution.getObjective(0)));
			  CM_tx_FalseNegative.setText(Double.toString(solution.getObjective(1)));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

