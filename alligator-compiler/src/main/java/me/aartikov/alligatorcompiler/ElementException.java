package me.aartikov.alligatorcompiler;

import javax.lang.model.element.Element;

public abstract class ElementException extends Exception {
	private Element element;

	public ElementException(final String s, final Element element) {
		super(s);
		this.element = element;
	}

	public Element getElement() {
		return element;
	}
}
