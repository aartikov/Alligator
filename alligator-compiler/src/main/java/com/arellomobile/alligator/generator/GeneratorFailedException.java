package com.arellomobile.alligator.generator;

import com.arellomobile.alligator.ElementException;

import javax.lang.model.element.Element;

public class GeneratorFailedException extends ElementException{

	public GeneratorFailedException(final String s, final Element element) {
		super(s, element);
	}
}