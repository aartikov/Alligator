package com.arellomobile.alligator.rule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

import me.aartikov.alligator.RegisterScreen;

/**
 * Date: 17-Feb-16
 * Time: 16:57
 *
 * @author esorokin
 */
public abstract class AnnotationRule {
	protected final ElementKind mValidKind;
	protected final Set<Modifier> mValidModifiers;

	public AnnotationRule(ElementKind validKind, Modifier... validModifiers) {
		if (validModifiers == null || validModifiers.length == 0) {
			throw new RuntimeException("Valid modifiers cant be empty or null.");
		}

		mValidKind = validKind;
		mValidModifiers = new HashSet<>(Arrays.asList(validModifiers));
	}

	/**
	 * Method describe rules for using Annotation.
	 *
	 * @param AnnotatedField Checking annotated field.
	 */
	public abstract void checkAnnotation(Element AnnotatedField) throws IncorrectElementException;

	protected String validModifiersToString() {
		if (mValidModifiers.size() > 1) {
			StringBuilder result = new StringBuilder("one of [");
			boolean addSeparator = false;
			for (Modifier validModifier : mValidModifiers) {
				if (addSeparator) {
					result.append(", ");
				}
				addSeparator = true;
				result.append(validModifier.toString());
			}
			result.append("]");
			return result.toString();
		} else {
			return mValidModifiers.iterator().next() + ".";
		}
	}


	protected void validateModifiers(final Element annotatedClass) throws IncorrectElementException {
		StringBuilder errorBuilder = new StringBuilder();
		if (annotatedClass.getKind() != mValidKind) {
			errorBuilder.append("Field " + annotatedClass + " of " + annotatedClass.getEnclosingElement().getSimpleName() + " should be " + mValidKind.name() + ", or not mark it as @" + RegisterScreen.class.getSimpleName()).append("\n");
		}

		for (Modifier modifier : annotatedClass.getModifiers()) {
			if (!mValidModifiers.contains(modifier)) {
				errorBuilder.append("Field " + annotatedClass + " of " + annotatedClass.getEnclosingElement().getSimpleName() + " can't be a " + modifier).append(". Use ").append(validModifiersToString()).append("\n");
			}
		}
		if (errorBuilder.length() != 0) {
			throw new IncorrectElementException(errorBuilder.toString(), annotatedClass);
		}
	}
}
