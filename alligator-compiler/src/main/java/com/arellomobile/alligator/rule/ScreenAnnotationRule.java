package com.arellomobile.alligator.rule;

import com.arellomobile.alligator.AvailableScreenTypes;
import com.arellomobile.alligator.Utils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public final class ScreenAnnotationRule extends AnnotationRule {
	public ScreenAnnotationRule() {
		super(ElementKind.CLASS, Modifier.PUBLIC);
	}

	@Override
	public void checkAnnotation(final Element annotatedClass) throws IncorrectElementException {
		validateElementClass(annotatedClass);

		validateModifiers(annotatedClass);
	}

	private void validateElementClass(final Element annotatedClass) throws IncorrectElementException {
		boolean availableScreenType = false;
		for (AvailableScreenTypes availableScreenTypes : AvailableScreenTypes.values()) {
			if (Utils.isAssignableFrom(annotatedClass, availableScreenTypes.className)) {
				availableScreenType = true;
			}
		}

		if (!availableScreenType) {
			throw new IncorrectElementException(annotatedClass.getSimpleName() + " must extends of " + AvailableScreenTypes.availableTypes(), annotatedClass);
		}
	}
}
