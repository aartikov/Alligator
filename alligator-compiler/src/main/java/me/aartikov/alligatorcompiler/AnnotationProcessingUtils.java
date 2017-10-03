package me.aartikov.alligatorcompiler;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class AnnotationProcessingUtils {
	private Messager messager;
	private Types typeUtils;

	public AnnotationProcessingUtils(ProcessingEnvironment processingEnv) {
		messager = processingEnv.getMessager();
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

	public void logError(Element element, String message) {
		messager.printMessage(Diagnostic.Kind.ERROR, message, element);
	}
}
