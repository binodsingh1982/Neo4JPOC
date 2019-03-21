package com.stufusion.nlp.textvalidator.spamdetector;

public class SpamPredictionOutput {
	private boolean spam;
	private double probability;
	public boolean isSpam() {
		return spam;
	}
	public void setSpam(boolean spam) {
		this.spam = spam;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
}
