package me.aartikov.alligatorcompiler;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class ProcessingUtils {
	private final Types typeUtils;

	public ProcessingUtils(ProcessingEnvironment processingEnv) {
		typeUtils = processingEnv.getTypeUtils();
	}

	public boolean isAssignableFrom(TypeElement classElement, String className) {
		TypeElement currentClassElement = classElement;
		while (true) {
			TypeMirror superClassType = currentClassElement.getSuperclass();
			if (superClassType.getKind() == TypeKind.NONE) {
				return false;
			}

			if (superClassType.toString().equals(className)) {
				return true;
			}

			currentClassElement = (TypeElement) typeUtils.asElement(superClassType);
		}
	}

	public String getSimpleClassName(String className) {
		int dotIndex = className.lastIndexOf(".");
		if (dotIndex == -1) {
			return className;
		} else {
			return className.substring(dotIndex + 1);
		}
	}
}
