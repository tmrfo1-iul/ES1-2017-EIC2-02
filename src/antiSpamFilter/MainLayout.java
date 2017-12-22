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
import java.io.FileOutputStream;
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
	private JFrame frame;
	private JTextField txtRules;
	private JTextField txtHam;
	private JTextField txtSpam;
	private JTextField txtManualFPositive;
	private JTextField txtManualFNegative;
	private JTextField txtOptimalFPositive;
	private JTextField txtOptimalFNegative;
	private JTable tableManualConfig;
	private JTable tableOptimalConfig;
	private JButton btnGravarManual;
	private JButton btnGravarOptimal;
	private JButton btnEvaluateManual;
	private JButton btnGenerateOptimal;
	private JDialog progressDialog;
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
		File currDir = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "logFiles");
		fileChooserRules.setCurrentDirectory(currDir);
		fileChooserOther.setCurrentDirectory(currDir);
		
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

//		JLabel label = new JLabel("False Negative:");

//		JTextField textField_2 = new JTextField();
//		textField_2.setColumns(10);

//		JLabel label_1 = new JLabel("False Positive:");

//		JTextField textField_3 = new JTextField();
//		textField_3.setColumns(10);

//		JScrollPane scrollPane_1 = new JScrollPane();

		JScrollPane scrollPane_2 = new JScrollPane();

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

		JLabel A_lblFalsePositive = new JLabel("False Positive:");

		JLabel A_lblFalseNegative = new JLabel("False Negative:");

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
//						.addGroup(groupLayout.createSequentialGroup()
//							.addContainerGap()
//							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
//							.addGap(26)
//							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//								.addGroup(groupLayout.createSequentialGroup()
//									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
//									.addGap(18)
//									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
//								.addGroup(groupLayout.createSequentialGroup()
//									.addComponent(label, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
//									.addGap(12)
//									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))
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
										.addComponent(btnGravarManual, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnEvaluateManual, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(A_lblFalseNegative)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtOptimalFNegative, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(A_lblFalsePositive)
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
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
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
								.addComponent(A_lblFalsePositive)
								.addComponent(txtOptimalFPositive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(A_lblFalseNegative)
								.addComponent(txtOptimalFNegative, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGap(344)
//							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGap(436)
//							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//								.addGroup(groupLayout.createSequentialGroup()
//									.addGap(3)
//									.addComponent(label_1))
//								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//							.addGap(11)
//							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//								.addComponent(label)
//								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
				)).addGroup(groupLayout.createSequentialGroup()
					.addGap(118)
					.addComponent(btnFilePaths))
		);

		tableOptimalConfig = new JTable();
		tableOptimalConfig.setEnabled(false);
		tableOptimalConfig.setRowSelectionAllowed(false);
		
		tableOptimalConfig.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Rules", "Weights"
			}
		) {
			private static final long serialVersionUID = 1L;

			Class<?>[] columnTypes = new Class[] {
				String.class, String.class
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
		scrollPane_2.setViewportView(tableOptimalConfig);
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
		scrollPane.setViewportView(tableManualConfig);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	
	private void readOptimalResults() {
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
	        	if( i1 < minFN[1] || 
	        			(i1 == minFN[1] && i0 < minFN[0])){
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
	        			modelAutomatico.setValueAt(line[i], i, 1);
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
	
	private void saveWeights(String weigh){
		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File("savedWeights" +weigh), false));
    	if (weigh.equals("Automatico")){
			DefaultTableModel modelAutomatico = (DefaultTableModel)tableOptimalConfig.getModel();
			for(int i = 0; i <modelAutomatico.getRowCount(); i++){
				String x = (String) modelAutomatico.getValueAt(i, 1);
				writer.println(x == null ? 0 : x);
			}
    	}else{
    		DefaultTableModel modeloManual = (DefaultTableModel)tableManualConfig.getModel();
			for(int i =0; i<modeloManual.getRowCount();i++){
				String x = (String) modeloManual.getValueAt(i, 1);
				writer.println(x == null ? 0 : x);
			}
    	}
			writer.close();
			JOptionPane.showMessageDialog(frame, "Weights saved sucessfuly!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private void saveFilePaths(){
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
	    	// there is no problem if this happens, it means the text fields will be empty
	    }
	}
	
	private void processRulesFile(){
		String line=null;
		DefaultTableModel modelManual = (DefaultTableModel)tableManualConfig.getModel();
		DefaultTableModel modelAutomatico = (DefaultTableModel)tableOptimalConfig.getModel();
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
			btnGravarManual.setEnabled(true);
			btnGravarOptimal.setEnabled(false);
			btnEvaluateManual.setEnabled(true);
			btnGenerateOptimal.setEnabled(true);
		} catch (IOException e) {
			promptUser("No file paths selected!", false);
		}
	}
	private void evaluationManual(){
		AntiSpamFilterProblem problem=null;
		SpamSolution solution=null;
		int size = rulesMap.size();
		double temp=0.0;
		DefaultTableModel modelManual = (DefaultTableModel)tableManualConfig.getModel();
		try {
			problem = new AntiSpamFilterProblem(txtSpam.getText(),txtHam.getText(),rulesMap);
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
			  txtManualFPositive.setText(Double.toString(solution.getObjective(0)));
			  txtManualFNegative.setText(Double.toString(solution.getObjective(1)));

		} catch (IOException e) {
			promptUser("An error occurred while trying to load the files from memory.\n"
					+ "Please verify if the paths chosen are incorrect or missing.", true);
		}
	}
	
	private void promptUser(String message, boolean error){
		String title = error ?  "Error!" : "Warning!";
		int iconType = error ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, iconType);
	}
	
	private void createProgressPopUp(){
		progressDialog = new JDialog(frame, "Progress", true);
		progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		Container content = new JFrame().getContentPane();
	    
	    ImageIcon bufferingIcon = new ImageIcon("ajax-loader.gif");
	    content.add(new JLabel("Calculating Optimal Solution...", bufferingIcon, JLabel.CENTER));
	    Point parentLocation = frame.getLocation();
	    progressDialog.add(content);
	    progressDialog.setLocation(parentLocation.x + frame.getWidth() / 4, parentLocation.y + frame.getHeight() / 2);
	    progressDialog.setSize(300, 100);
	    progressDialog.setVisible(true);
	}
}