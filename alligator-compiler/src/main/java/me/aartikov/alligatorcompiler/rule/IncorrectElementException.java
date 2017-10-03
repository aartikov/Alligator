package me.aartikov.alligatorcompiler.rule;

import me.aartikov.alligatorcompiler.ElementException;

import javax.lang.model.element.Element;

public class IncorrectElementException extends ElementException {
	IncorrectElementException(final String s, final Element element) {
		super(s, element);
	}
}
