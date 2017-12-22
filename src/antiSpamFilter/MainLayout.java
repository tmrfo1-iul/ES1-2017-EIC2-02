package antiSpamFilter;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
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
	public JFrame frame;
	public JTextField txtRules;
	public JTextField txtHam;
	public JTextField txtSpam;
	public JTextField txtManualFPositive;
	public JTextField txtManualFNegative;
	public JTextField txtOptimalFPositive;
	public JTextField txtOptimalFNegative;
	public JTable tableManualConfig;
	public JTable tableOptimalConfig;
	public JButton btnGravarManual;
	public JButton btnGravarOptimal;
	public JButton btnEvaluateManual;
	public JButton btnGenerateOptimal;
	public JDialog progressDialog;
	public JScrollPane scrollPaneTabel1;
	public JScrollPane scrollPaneTabel2;
	public HashMap<String, Integer> rulesMap = new HashMap<String, Integer>();
	public final static String RULES_FOLDER = "AntiSpamConfigurationForLeisureMailbox" + File.separator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					MainLayout window = new MainLayout();
					window.frame.setVisible(true);
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
	public void initialize() {
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

		scrollPaneTabel1 = new JScrollPane();
		scrollPaneTabel2 = new JScrollPane();

		btnEvaluateManual = new JButton("Evaluate");
		btnEvaluateManual.setEnabled(false);
		btnEvaluateManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluationManual();
			}
		});

		btnGravarManual = new JButton("Save");
		btnGravarManual.setEnabled(false);
		btnGravarManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveWeights("Manual");
			}
		});
		txtManualFPositive = new JTextField();
		txtManualFPositive.setEditable(false);
		txtManualFPositive.setColumns(10);
		txtManualFNegative = new JTextField();
		txtManualFNegative.setEditable(false);
		txtManualFNegative.setColumns(10);
		JLabel lblManualFPositive = new JLabel("False Positive:");
		JLabel lblManualFNegative = new JLabel("False Negative:");
		
		btnGenerateOptimal = new JButton("Generate configuration");
		btnGenerateOptimal.setEnabled(false);
		btnGenerateOptimal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							AntiSpamFilterAutomaticConfiguration.run(txtSpam.getText(), txtHam.getText(), rulesMap);
							readOptimalResults();
							progressDialog.dispose();
						} catch (IOException e1) {
							promptUser("An error occurred while trying to load the files from memory.\n"
									+ "Please verify if the paths chosen are incorrect or missing.", true);
							progressDialog.dispose();
						}
					}
				}).start();
				createProgressPopUp();
			}
		});

		btnGravarOptimal = new JButton("Save");
		btnGravarOptimal.setEnabled(false);
		btnGravarOptimal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveWeights("Automatico");
			}
		});

		JLabel lblFalsePositive = new JLabel("False Positive:");

		JLabel lblFalseNegative = new JLabel("False Negative:");

		txtOptimalFPositive = new JTextField();
		txtOptimalFPositive.setEditable(false);
		txtOptimalFPositive.setColumns(10);

		txtOptimalFNegative = new JTextField();
		txtOptimalFNegative.setEditable(false);
		txtOptimalFNegative.setColumns(10);
		
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
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneTabel2, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneTabel1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
							.addGap(49)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(39)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnGravarManual, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnEvaluateManual, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblFalseNegative)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtOptimalFNegative, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblFalsePositive)
										.addGap(10)
										.addComponent(txtOptimalFPositive, 0, 0, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(btnGenerateOptimal))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(29)
									.addComponent(btnGravarOptimal, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblManualFNegative)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtManualFNegative, 0, 0, Short.MAX_VALUE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblManualFPositive)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtManualFPositive, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))))
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
							.addComponent(scrollPaneTabel1, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addComponent(btnEvaluateManual)
							.addGap(18)
							.addComponent(btnGravarManual)
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblManualFPositive)
								.addComponent(txtManualFPositive, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblManualFNegative)
								.addComponent(txtManualFNegative, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnGenerateOptimal)
							.addGap(18)
							.addComponent(btnGravarOptimal)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFalsePositive)
								.addComponent(txtOptimalFPositive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFalseNegative)
								.addComponent(txtOptimalFNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPaneTabel2, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING))).addGroup(groupLayout.createSequentialGroup()
					.addGap(118)
					.addComponent(btnFilePaths))
		);

		resetTableModels();
		frame.getContentPane().setLayout(groupLayout);
	}
	
	
	/**
	 *Selects the solution with less False Negatives, which is the more appropriate solution.
	 */
	public void readOptimalResults() {
		File fileEvaluation = new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.rf");
		File fileWeight = new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.rs");
		
	    try {
	        Scanner sc = new Scanner(fileEvaluation);
	        int[] minFN = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
	        int chosenLine = -1;
	        int iterator = 0;
	        while(sc.hasNextLine()){   
	        	String[] line = sc.nextLine().split(" ");
	        	int i0 = (int) Double.parseDouble(line[0]);
	        	int i1 = (int) Double.parseDouble(line[1]);
	        	if( i1 < minFN[1] || (i1 == minFN[1] && i0 < minFN[0])){
	        		minFN[0] = i0;
	        		minFN[1] = i1;
	        		chosenLine = iterator;
	        	}
	        	iterator++;
	        }
	    	txtOptimalFPositive.setText(String.valueOf(minFN[0]));
	    	txtOptimalFNegative.setText(String.valueOf(minFN[1]));
	        sc.close();
	        
	        sc = new Scanner(fileWeight);
	        iterator = 0;
	        while(sc.hasNextLine()){   
	        	String[] line = sc.nextLine().split(" ");
	        	if(iterator == chosenLine){
	        		DefaultTableModel modelAutomatico = (DefaultTableModel)tableOptimalConfig.getModel();
	        		for(int i = 0; i < line.length; i++){
	        			modelAutomatico.setValueAt(Double.parseDouble(line[i]), i, 1);
	        		}
	        	}
	        	iterator++;
	        }
	        btnGravarOptimal.setEnabled(true);
	        sc.close();
	    }
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 *Saves weights.
	 */
	public void saveWeights(String weigh){
		try {
			File f = new File(RULES_FOLDER);
			f.mkdirs();
			PrintWriter writer = new PrintWriter(RULES_FOLDER+ "rules.cf");
    	if (weigh.equals("Automatico")){
			DefaultTableModel modelAutomatico = (DefaultTableModel)tableOptimalConfig.getModel();
			for(int i = 0; i <modelAutomatico.getRowCount(); i++){
				Double x = (Double) modelAutomatico.getValueAt(i, 1);
				writer.print(modelAutomatico.getValueAt(i, 0) + " ");
				writer.println(x == null ? 0 : x);
			}
    	}else{
    		DefaultTableModel modeloManual = (DefaultTableModel)tableManualConfig.getModel();
			for(int i =0; i<modeloManual.getRowCount();i++){
				Double x = (Double) modeloManual.getValueAt(i, 1);
				writer.print(modeloManual.getValueAt(i, 0) + " ");
				writer.println(x == null ? 0 : x);
			}
    	}
			writer.close();
			JOptionPane.showMessageDialog(frame, "Weights saved sucessfuly!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *Saves the inputed paths in savedFilePaths.txt.
	 */
	public void saveFilePaths(){
		try {
			PrintWriter writer = new PrintWriter("savedFilePaths");
				writer.println(txtRules.getText());
				writer.println(txtHam.getText());
				writer.print(txtSpam.getText());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *Checks if there is any paths previously saved and loads them 
	 *into the corresponding JTextField.
	 */
	public void readSavedFilePaths(){
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
	    	// there is no problem if this happens, it means the text fields will be empty
	    }
	}
	
	/**
	 *Processes the rules in rules.txt file and loads them into both tables.
	 */
	public void processRulesFile(){
		resetTableModels();
		DefaultTableModel modelManual = (DefaultTableModel) tableManualConfig.getModel();
		DefaultTableModel modelAutomatico = (DefaultTableModel) tableOptimalConfig.getModel();
		Path path = Paths.get(txtRules.getText());
		try {
			Scanner scanner = new Scanner(path);
			int it = 0;
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(" ");
				rulesMap.put(line[0], it);
				if(line.length == 2){
					modelAutomatico.addRow(new Object[]{line[0], Double.parseDouble(line[1])});
					modelManual.addRow(new Object[]{line[0], Double.parseDouble(line[1])});
				}else{
					modelAutomatico.addRow(new Object[]{line[0], null});
					modelManual.addRow(new Object[]{line[0], null});
				}
				it++;
			}
			scanner.close();
			btnGravarManual.setEnabled(true);
			btnGravarOptimal.setEnabled(false);
			btnEvaluateManual.setEnabled(true);
			btnGenerateOptimal.setEnabled(true);
		} catch (IOException e) {
			promptUser("No file paths selected!", false);
		}
	}
	
	/**
	 *Evaluates the weights inputed by the user in the table corresponding to the manual configuration.
	 */
	public void evaluationManual(){
		AntiSpamFilterProblem problem = null;
		SpamSolution solution = null;
		int size = rulesMap.size();
		double temp = 0.0;
		DefaultTableModel modelManual = (DefaultTableModel)tableManualConfig.getModel();
		try {
			problem = new AntiSpamFilterProblem(txtSpam.getText(),txtHam.getText(),rulesMap);
			solution = new SpamSolution(-5.0,5.0,2,size);
			  for(int i=0;i<size;i++){
				  if(modelManual.getValueAt(i, 1)==null){
					  temp=0.0;
					  solution.setVariableValue(i, temp);
				  }else{
					  temp = (Double) modelManual.getValueAt(i, 1);
				  }
				  
				  if(temp < 5.0 && temp > -5.0 ){
					  solution.setVariableValue(i, temp);
				  }else if(temp >= 5.0){
					  solution.setVariableValue(i, 5.0);
				  }else if(temp <= -5.0){
					  solution.setVariableValue(i,-5.0);
				  }
			  }
			  problem.evaluate(solution);
			  txtManualFPositive.setText(Double.toString(solution.getObjective(0)));
			  txtManualFNegative.setText(Double.toString(solution.getObjective(1)));
		} catch (IOException e) {
			promptUser("An error occurred while trying to load the files from memory.\n"
					+ "Please verify if the paths chosen are incorrect or missing.", true);
		}
	}
	
	/**
	 *Prompts the user through a dialog with useful information about the action taken.
	 */
	public void promptUser(String message, boolean error){
		String title = error ?  "Error!" : "Warning!";
		int iconType = error ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, iconType);
	}
	
	/**
	 *Creates a loading Progress popup.
	 */
	public void createProgressPopUp(){
		progressDialog = new JDialog(frame, "Progress", true);
		progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		Container content = new JFrame().getContentPane();
	    
	    ImageIcon bufferingIcon = new ImageIcon("/imageWindowBuilder/ajax-loader.gif");
	    content.add(new JLabel("Calculating Optimal Solution...", bufferingIcon, JLabel.CENTER));
	    Point parentLocation = frame.getLocation();
	    progressDialog.add(content);
	    progressDialog.setLocation(parentLocation.x + frame.getWidth() / 4, parentLocation.y + frame.getHeight() / 2);
	    progressDialog.setSize(300, 100);
	    progressDialog.setVisible(true);
	}
	
	/**
	 *Reset tables models.
	 */
	public  void resetTableModels() {
		tableOptimalConfig = new JTable();
		tableOptimalConfig.setEnabled(false);
		tableOptimalConfig.setRowSelectionAllowed(false);
		
		tableOptimalConfig.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Rules", "Weights"
				}
				){
			private static final long serialVersionUID = 1L;
			
			Class<?>[] columnTypes = new Class[] {
					String.class, Double.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableOptimalConfig.getColumnModel().getColumn(0).setResizable(false);
		tableOptimalConfig.getColumnModel().getColumn(1).setResizable(false);
		tableManualConfig = new JTable();
		tableManualConfig.setRowSelectionAllowed(false);
		tableManualConfig
		.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Rules", "Weights"
				}
				) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					String.class, Double.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableManualConfig.getColumnModel().getColumn(0).setResizable(false);
		tableManualConfig.getColumnModel().getColumn(1).setResizable(false);
		scrollPaneTabel1.setViewportView(tableManualConfig);
		scrollPaneTabel2.setViewportView(tableOptimalConfig);
	}
}