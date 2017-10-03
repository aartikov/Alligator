package me.aartikov.alligatorcompiler.generator;

import me.aartikov.alligatorcompiler.ElementException;

import javax.lang.model.element.Element;

public class GeneratorFailedException extends ElementException {

	public GeneratorFailedException(final String s, final Element element) {
		super(s, element);
	}
}