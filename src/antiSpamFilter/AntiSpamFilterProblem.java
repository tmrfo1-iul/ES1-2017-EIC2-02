package antiSpamFilter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {
	private static final long serialVersionUID = 1L;

	private static final int NUMBER_OF_OBJECTIVES = 2;
	private static final double LOWER_BOUND = -5.0;
	private static final double UPPER_BOUND = 5.0;
	private static final double SPAM_THRESHOLD = 5.0;

	private final HashMap<String, Integer> rules;
	private final List<List<String>> spamLog;
	private final List<List<String>> hamLog;

	public AntiSpamFilterProblem(String spamPath, String hamPath, HashMap<String, Integer> rules) throws IOException {
		spamLog = readLog(spamPath, "spam");
		hamLog = readLog(hamPath, "ham");
		this.rules=rules;

		setNumberOfVariables(rules.size());
		setNumberOfObjectives(NUMBER_OF_OBJECTIVES);
		setName("AntiSpamFilterProblem");

		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());
		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(LOWER_BOUND);
			upperLimit.add(UPPER_BOUND);
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}
	
	
	private List<List<String>> readLog(String filePath, String fileType) throws IOException{
		List<List<String>> list = new ArrayList<>();
		Path path = Paths.get(filePath);
		try {
			Scanner scanner = new Scanner(path);
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split("\t");
				List<String> emailRules = new ArrayList<String>();
				// Starts at one because the file format starts each line with the email ID (not a rule)
				for(int i = 1; i < line.length; i++){
					emailRules.add(line[i]);
				}
				list.add(emailRules);
			}
			scanner.close();
			return list;
		} catch (IOException e) {
			throw new IOException("Invalid path to " + fileType + " file.");
		}
	}

	public void evaluate(DoubleSolution solution) {
		int falsePositives = 0;
		int falseNegatives = 0;

		// Non-SPAM messages classified as SPAM (False Positives)
		for (int hamMsgIterator = 0; hamMsgIterator < hamLog.size(); hamMsgIterator++) {
			int total = 0;
			List<String> ruleNames = hamLog.get(hamMsgIterator);
			for (int j = 0; j < ruleNames.size(); j++) {
				Integer mappedIterator = rules.get(ruleNames.get(j));
				if(mappedIterator != null){
					total += solution.getVariableValue(mappedIterator);
				}
			}
			if (total > SPAM_THRESHOLD) {
				falsePositives++;
			}
		}
		
		// SPAM messages classified as non-SPAM (False Negatives)
		for (int spamMsgIterator = 0; spamMsgIterator < spamLog.size(); spamMsgIterator++) {
			int total = 0;
			List<String> ruleNames = spamLog.get(spamMsgIterator);
			for (int j = 0; j < ruleNames.size(); j++) {
				Integer mappedIterator = rules.get(ruleNames.get(j));
				if(mappedIterator != null){
					total += solution.getVariableValue(mappedIterator);
				}
			}
			if (total <= SPAM_THRESHOLD) {
				falseNegatives++;
			}
		}

		solution.setObjective(0, falsePositives);
		solution.setObjective(1, falseNegatives);
	}
}