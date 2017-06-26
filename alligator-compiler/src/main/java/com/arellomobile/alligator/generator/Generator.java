package com.arellomobile.alligator.generator;

import com.squareup.javapoet.JavaFile;

import java.util.Collection;
import java.util.List;

import javax.lang.model.element.Element;

public interface Generator<ElementType extends Element> {

	void implementFor(ElementType elementType);

	Collection<String> getSupportedAnnotations();

	void generate(List<JavaFile> list) throws GeneratorFailedException;
}
