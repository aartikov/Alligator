package me.aartikov.alligatorcompiler;

import java.util.List;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class NavigationFactoryGenerator {
	private static final String PACKAGE = "me.aartikov.alligator.navigationfactories";
	private static final String CLASS_NAME = "GeneratedNavigationFactory";
	private static final String SUPERCLASS_NAME = "RegistryNavigationFactory";

	private ProcessingUtils utils;

	public NavigationFactoryGenerator(ProcessingUtils utils) {
		this.utils = utils;
	}

	public JavaFile generate(List<RegistrationAnnotatedClass> annotatedClasses) throws ProcessingException {
		MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC);

		for (RegistrationAnnotatedClass annotatedClass : annotatedClasses) {
			ClassName annotatedClassName = ClassName.get(annotatedClass.getClassElement());
			ClassName screenClassName = ClassName.bestGuess(annotatedClass.getScreenClassName());

			if (annotatedClass.getScreenResultClassName() == null) {
				String registrationMethod = getRegistrationMethod(annotatedClass.getScreenType());
				constructorBuilder.addStatement(registrationMethod, screenClassName, annotatedClassName);
			} else {
				String registrationForResultMethod = getRegistrationForResultMethod(annotatedClass.getScreenType());
				ClassName screenResultClassName = ClassName.bestGuess(annotatedClass.getScreenResultClassName());
				constructorBuilder.addStatement(registrationForResultMethod, screenClassName, annotatedClassName, screenResultClassName);
			}
		}

		TypeSpec navigationFactory = TypeSpec.classBuilder(CLASS_NAME)
				.addModifiers(Modifier.PUBLIC)
				.superclass(ClassName.get(PACKAGE, SUPERCLASS_NAME))
				.addMethod(constructorBuilder.build())
				.build();

		return JavaFile.builder(PACKAGE, navigationFactory).build();
	}

	private String getRegistrationMethod(ScreenType screenType) {
		String screenTypeName = utils.getSimpleClassName(screenType.getClassName());
		return "register" + screenTypeName + "($1T.class, $2T.class)";
	}

	private String getRegistrationForResultMethod(ScreenType screenType) {
		String screenTypeName = utils.getSimpleClassName(screenType.getClassName());
		return "register" + screenTypeName + "ForResult($1T.class, $2T.class, $3T.class)";
	}
}
