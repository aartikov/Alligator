package com.arellomobile.alligator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class Utils {

	public static boolean isAssignableFrom(Element element, String className) {
		if (element == null || element.getKind() != ElementKind.CLASS) {
			return false;
		}

		TypeElement typeElement = (TypeElement) element;
		CharSequence charSequence = elementClassName(typeElement);
		if (charSequence.toString().equals(className)) {
			return true;
		} else {
			TypeElement supperClass = getSuperClassElement(element);
			return isAssignableFrom(supperClass, className);
		}
	}

	public static CharSequence elementClassName(Element element) {
		if (!(element instanceof TypeElement)) {
			return null;
		}

		return AlligatorCompiler.getElementUtils().getBinaryName((TypeElement) element);
	}

	private static TypeElement getSuperClassElement(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			return null;
		}
		TypeMirror superclass = ((TypeElement) element).getSuperclass();
		if (superclass.getKind() == TypeKind.NONE) {
			return null;
		}
		DeclaredType kind = (DeclaredType) superclass;
		return (TypeElement) kind.asElement();
	}

}
