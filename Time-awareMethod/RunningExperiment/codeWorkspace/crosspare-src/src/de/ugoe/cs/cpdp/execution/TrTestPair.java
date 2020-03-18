package de.ugoe.cs.cpdp.execution;

public class TrTestPair {
	
	String tr;
	String test;
	boolean evaluationDone;
	
	TrTestPair(String test, String tr){
		this.tr = tr;
		this.test = test;
		evaluationDone = false;
	}
	
	TrTestPair(String tr, String test, boolean evaluationDone){
		this.tr = tr;
		this.test = test;
		this.evaluationDone = evaluationDone;
	}

	public String getTr() {
		return tr;
	}

	public void setTr(String tr) {
		this.tr = tr;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public boolean isEvaluationDone() {
		return evaluationDone;
	}

	public void setEvaluationDone(boolean evaluationDone) {
		this.evaluationDone = evaluationDone;
	}
	
}
