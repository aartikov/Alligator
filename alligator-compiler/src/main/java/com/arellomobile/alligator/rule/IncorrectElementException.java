package com.arellomobile.alligator.rule;

import com.arellomobile.alligator.ElementException;

import javax.lang.model.element.Element;

public class IncorrectElementException extends ElementException {
	IncorrectElementException(final String s, final Element element) {
		super(s, element);
	}
}
