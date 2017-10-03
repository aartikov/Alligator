package me.aartikov.alligatorcompiler;

import javax.lang.model.element.Element;

public class ProcessingException extends Exception {
	private Element element;

	public ProcessingException(Element element, String message, Object... args) {
		super(String.format(message, args));
		this.element = element;
	}

	public ProcessingException(String message, Object... args) {
		this(null, message, args);
	}

	public Element getElement() {
		return element;
	}
}