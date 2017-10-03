package me.aartikov.alligatorcompiler;

import com.squareup.javapoet.ClassName;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class Utils {

	public static String getDefaultPackageName() {
		return "me.aartikov.alligator";
	}

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

	public static ClassName getClassName(final String className) {
		int i1 = className.lastIndexOf(".");
		return ClassName.get(className.substring(0, i1), className.substring(i1 + 1));
	}


	public static TypeMirror getAnnotationTypeMirror(Element element, String neededKey){
		List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
		for (AnnotationMirror annotationMirror : annotationMirrors) {
			Map<? extends ExecutableElement, ? extends AnnotationValue> elemntValues = annotationMirror.getElementValues();

			for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elemntValues.entrySet()) {
				String key = entry.getKey().getSimpleName().toString();
				Object value = entry.getValue().getValue();

				if (neededKey.equals(key)) {
					TypeMirror screen = (TypeMirror) value;
					return screen;

				}
			}
		}

		return null;
	}
}
