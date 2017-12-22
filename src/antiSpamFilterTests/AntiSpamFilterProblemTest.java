package antiSpamFilterTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.uma.jmetal.solution.DoubleSolution;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;
import antiSpamFilter.AntiSpamFilterProblem;
import antiSpamFilter.SpamSolution;

public class AntiSpamFilterProblemTest {

	@Test
	public void testAntiSpamFilterProblem() {
		HashMap<String, Integer> rulesMap = new HashMap<String, Integer>();
			try {
				rulesMap.put("BAYES_00",0);
				rulesMap.put("FREEMAIL_FROM",1);
				AntiSpamFilterProblem k = new AntiSpamFilterProblem("src/antiSpamFilterTests/spam.log","src/antiSpamFilterTests/ham.log",rulesMap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Test
	public void testEvaluate() {
		HashMap<String, Integer> rulesMap = new HashMap<String, Integer>();
		try {
			rulesMap.put("BAYES_00",0);
			rulesMap.put("FREEMAIL_FROM",1);
			AntiSpamFilterProblem k = new AntiSpamFilterProblem("src/antiSpamFilterTests/spam.log","src/antiSpamFilterTests/ham.log",rulesMap);
			SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
			k.evaluate(solution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}

}
