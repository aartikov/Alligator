package me.aartikov.alligatorcompiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.alligator.ScreenResult;

public class RegistrationAnnotatedClassCreator {
	private ProcessingUtils utils;

	public RegistrationAnnotatedClassCreator(ProcessingUtils utils) {
		this.utils = utils;
	}

	public RegistrationAnnotatedClass create(Element element) throws ProcessingException {
		TypeElement classElement = obtainClassElement(element);
		checkThatIsPublic(classElement);
		checkThatIsNotAbstract(classElement);
		ScreenType screenType = obtainScreenType(classElement);
		String screenClassName = obtainScreenClassName(classElement);
		String screenResultClassName = obtainScreenResultClassName(classElement);
		if (screenResultClassName != null) {
			checkThatScreenResultSupported(classElement, screenType);
		}
		return new RegistrationAnnotatedClass(classElement, screenType, screenClassName, screenResultClassName);
	}

	private TypeElement obtainClassElement(Element element) throws ProcessingException {
		if (element.getKind() != ElementKind.CLASS) {
			throw new ProcessingException(element, "Only classes can be annotated with @RegisterScreen.");
		}
		return (TypeElement) element;
	}

	private void checkThatIsPublic(TypeElement classElement) throws ProcessingException {
		if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
			throw new ProcessingException(classElement, "The class %s is not public.", classElement.getQualifiedName());
		}
	}

	private void checkThatIsNotAbstract(TypeElement classElement) throws ProcessingException {
		if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
			throw new ProcessingException(classElement, "The class %s is abstract. You can't annotate abstract classes with @RegisterScreen.", classElement.getQualifiedName());
		}
	}

	private ScreenType obtainScreenType(TypeElement classElement) throws ProcessingException {
		for (ScreenType screenType : ScreenType.values()) {
			if (utils.isAssignableFrom(classElement, screenType.getClassName())) {
				return screenType;
			}
		}
		throw new ProcessingException(classElement, "Only Activity, support Fragment and support DialogFragment can be annotated with @RegisterScreen.");
	}

	private String obtainScreenClassName(TypeElement classElement) {
		RegisterScreen annotation = classElement.getAnnotation(RegisterScreen.class);
		try {
			Class<?> clazz = annotation.value();
			return clazz.getCanonicalName();
		} catch (MirroredTypeException mte) {
			DeclaredType type = (DeclaredType) mte.getTypeMirror();
			TypeElement typeElement = (TypeElement) type.asElement();
			return typeElement.getQualifiedName().toString();
		}
	}

	private String obtainScreenResultClassName(TypeElement classElement) {
		RegisterScreen annotation = classElement.getAnnotation(RegisterScreen.class);
		final String defaultName = ScreenResult.class.getCanonicalName();
		try {
			Class<?> clazz = annotation.screenResult();
			String name = clazz.getCanonicalName();
			return name.equals(defaultName) ? null : name;
		} catch (MirroredTypeException mte) {
			DeclaredType type = (DeclaredType) mte.getTypeMirror();
			TypeElement typeElement = (TypeElement) type.asElement();
			String name = typeElement.getQualifiedName().toString();
			return name.equals(defaultName) ? null : name;
		}
	}

	private void checkThatScreenResultSupported(TypeElement classElement, ScreenType screenType) throws ProcessingException {
		if (screenType != ScreenType.ACTIVITY) {
			throw new ProcessingException(classElement, "ScreenResult can be applied only for Activity.");
		}
	}
}
