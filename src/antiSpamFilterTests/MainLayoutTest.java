package antiSpamFilterTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.table.DefaultTableModel;

import org.junit.Test;

import antiSpamFilter.MainLayout;

public class MainLayoutTest {

	@Test
	public void testMain() {
		MainLayout k= new MainLayout();
	}

	@Test
	public void testMainLayout() {
		MainLayout k= new MainLayout();
	}

	@Test
	public void testInitialize() {
		MainLayout k= new MainLayout();
		k.initialize();
	}

	@Test
	public void testReadOptimalResults() {
		MainLayout k= new MainLayout();
		k.readOptimalResults();
	}

	@Test
	public void testSaveWeights() {

		MainLayout k= new MainLayout();
		k.saveWeights("");
		DefaultTableModel modelAutomatico = (DefaultTableModel)k.tableOptimalConfig.getModel();
		DefaultTableModel modelManual = (DefaultTableModel)k.tableManualConfig.getModel();
		modelAutomatico.addRow(new Object[]{"t", 0.0});
		modelAutomatico.addRow(new Object[]{"t", null});
		modelManual.addRow(new Object[]{"t", 0.0});
		modelManual.addRow(new Object[]{"t", null});
		k.saveWeights("");
		k.saveWeights("Automatico");
	}

	@Test
	public void testSaveFilePaths() {
		MainLayout k= new MainLayout();
		String auxRules = k.txtRules.getText();
		String auxHam = k.txtHam.getText();
		String auxSpam = k.txtSpam.getText();
		k.saveFilePaths();

	}

	@Test
	public void testReadSavedFilePaths() {
		MainLayout k= new MainLayout();
		k.txtSpam.setText("test1");
		k.txtHam.setText("test2");
		k.txtRules.setText("test3");
		k.saveFilePaths();
		k.readSavedFilePaths();
		k.txtSpam.setText("");
		k.saveFilePaths();
		k.readSavedFilePaths();
		k.txtHam.setText("");
		k.saveFilePaths();
		k.readSavedFilePaths();
		k.txtRules.setText("");
		k.saveFilePaths();
		k.readSavedFilePaths();
	}

	@Test
	public void testProcessRulesFile() {
		MainLayout k= new MainLayout();
		k.processRulesFile();
		k.txtRules.setText("src/antiSpamFilterTests/rules2.cf");
		k.processRulesFile();
	}

	@Test
	public void testEvaluationManual() {
		MainLayout k= new MainLayout();
		k.evaluationManual();
		k.txtHam.setText("src/antiSpamFilterTests/ham.log");
		k.txtSpam.setText("src/antiSpamFilterTests/spam.log");
		k.txtRules.setText("src/antiSpamFilterTests/rules2.cf");
		k.rulesMap.put("t",0);
		k.rulesMap.put("k", 1);
		k.rulesMap.put("b", 2);
		k.rulesMap.put("k", 3);
		k.rulesMap.put("b", 4);
		DefaultTableModel modelManual = (DefaultTableModel)k.tableManualConfig.getModel();
		modelManual.addRow(new Object[]{"t", 8.0});	
		modelManual.addRow(new Object[]{"t", null});
		modelManual.addRow(new Object[]{"t", -8.0});
		k.evaluationManual();
	}

	@Test
	public void testPromptUser() {
		MainLayout k= new MainLayout();
		k.promptUser("hmm", true);
	}

	@Test
	public void testCreateProgressPopUp() {
		//MainLayout k= new MainLayout();
		//k.createProgressPopUp();
	//	k.progressDialog.dispose();
		}

	@Test
	public void testResetTableModels() {
		MainLayout k= new MainLayout();
		k.resetTableModels();
		DefaultTableModel modelAutomatico = (DefaultTableModel)k.tableOptimalConfig.getModel();
		DefaultTableModel modelManual = (DefaultTableModel)k.tableManualConfig.getModel();
		assertEquals(modelManual.getColumnClass(0),String.class);
		assertEquals(modelManual.isCellEditable(0, 0),false);
		assertEquals(modelAutomatico.getColumnClass(0),String.class);
		assertEquals(modelAutomatico.isCellEditable(0, 0),false);
	}
	
}
