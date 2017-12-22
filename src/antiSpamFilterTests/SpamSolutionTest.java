package antiSpamFilterTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uma.jmetal.solution.Solution;

import antiSpamFilter.SpamSolution;

public class SpamSolutionTest {

	@Test
	public void testSpamSolution() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
	}

	@Test
	public void testSetObjective() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		solution.setObjective(1,4.0);
	}

	@Test
	public void testGetObjective() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		solution.getObjective(i);
	}

	@Test
	public void testGetVariableValue() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		solution.getVariableValue(i);	
		}

	@Test
	public void testSetVariableValue() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		double k=0.0;
		solution.setVariableValue(i, k);	

		
	}

	@Test
	public void testGetVariableValueString() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		String k=solution.getVariableValueString(i);
	}

	@Test
	public void testGetNumberOfVariables() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int k=solution.getNumberOfVariables();
	}

	@Test
	public void testGetNumberOfObjectives() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int k=solution.getNumberOfObjectives();
	}

	@Test
	public void testCopy() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		Solution<Double> testSolution=solution.copy();
	}

	@Test
	public void testGetLowerBound() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		double k=solution.getLowerBound(i);
	}

	@Test
	public void testGetUpperBound() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		double k=solution.getUpperBound(i);
	}

	@Test
	public void testSetAttribute() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		double k=0.0;
		solution.setAttribute(i, k);	
	}

	@Test
	public void testGetAttribute() {
		SpamSolution solution = new SpamSolution(-5.0,5.0,2,300);
		int i=0;
		solution.getAttribute(i);
	}

}
