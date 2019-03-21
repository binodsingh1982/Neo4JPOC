package com.stufusion.nlp.textvalidator;

import com.stufusion.nlp.constant.TextValidatorType;

public class TextValidationOutput {
	private TextValidatorType type;
	private boolean result;
	private double score;

	public TextValidationOutput() {

	}

	public TextValidationOutput(TextValidatorType type, boolean result, double score) {
		super();
		this.type = type;
		this.result = result;
		this.score = score;
	}

	public TextValidatorType getType() {
		return type;
	}

	public void setType(TextValidatorType type) {
		this.type = type;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
