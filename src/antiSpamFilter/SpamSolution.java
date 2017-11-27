package antiSpamFilter;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;

public class SpamSolution implements DoubleSolution{
	private static final long serialVersionUID = 1L;
	private final double lowerBound;
	private final double upperBound;
	private final double[] objectives;
	private final double[] variables;

	public SpamSolution(double lowerBound, double upperBound, int numbObjectives, int numbVariables) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.objectives = new double[numbObjectives];
		this.variables = new double[numbVariables];
	}

	@Override
	public void setObjective(int index, double value) {
		objectives[index] = value;
	}

	@Override
	public double getObjective(int index) {
		return objectives[index];
	}

	@Override
	public Double getVariableValue(int index) {
		return variables[index];
	}

	@Override
	public void setVariableValue(int index, Double value) {
		variables[index] = value;
	}

	@Override
	public String getVariableValueString(int index) {
		return String.valueOf(variables[index]);
	}

	@Override
	public int getNumberOfVariables() {
		return variables.length;
	}

	@Override
	public int getNumberOfObjectives() {
		return objectives.length;
	}

	@Override
	public Solution<Double> copy() {
		Solution<Double> newSolution = 
				new SpamSolution(lowerBound, upperBound, objectives.length, variables.length);
		for (int i = 0; i < objectives.length; i++) {
			newSolution.setObjective(i, objectives[i]);
		}
		for (int i = 0; i < variables.length; i++) {
			newSolution.setVariableValue(i, variables[i]);
		}
		return newSolution;
	}
	
	@Override
	public Double getLowerBound(int index) {
		return lowerBound;
	}

	@Override
	public Double getUpperBound(int index) {
		return upperBound;
	}

	@Override
	public void setAttribute(Object id, Object value) {
	}

	@Override
	public Object getAttribute(Object id) {
		return null;
	}
}