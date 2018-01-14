package me.aartikov.alligatorcompiler;

import javax.lang.model.element.TypeElement;

public class RegistrationAnnotatedClass {
	private TypeElement classElement;
	private ScreenType screenType;
	private String screenClassName;
	private String screenResultClassName;

	public RegistrationAnnotatedClass(TypeElement classElement, ScreenType screenType, String screenClassName, String screenResultClassName) {
		this.classElement = classElement;
		this.screenType = screenType;
		this.screenClassName = screenClassName;
		this.screenResultClassName = screenResultClassName;
	}

	public TypeElement getClassElement() {
		return classElement;
	}

	public ScreenType getScreenType() {
		return screenType;
	}

	public String getScreenClassName() {
		return screenClassName;
	}

	public String getScreenResultClassName() {
		return screenResultClassName;
	}
}
