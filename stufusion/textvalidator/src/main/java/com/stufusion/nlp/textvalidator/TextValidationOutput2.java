package com.stufusion.nlp.textvalidator;

public class TextValidationOutput2 {
	private boolean spamText;
	private boolean abusiveLanguage;
	private boolean junkText;
	private boolean structured;
	public boolean isSpamText() {
		return spamText;
	}
	public void setSpamText(boolean spamText) {
		this.spamText = spamText;
	}
	public boolean isAbusiveLanguage() {
		return abusiveLanguage;
	}
	public void setAbusiveLanguage(boolean abusiveLanguage) {
		this.abusiveLanguage = abusiveLanguage;
	}
	public boolean isJunkText() {
		return junkText;
	}
	public void setJunkText(boolean junkText) {
		this.junkText = junkText;
	}
	public boolean isStructured() {
		return structured;
	}
	public void setStructured(boolean structured) {
		this.structured = structured;
	}
	
	
}
