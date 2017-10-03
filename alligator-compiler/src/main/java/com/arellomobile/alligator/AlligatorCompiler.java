package com.arellomobile.alligator;

import com.arellomobile.alligator.generator.Generator;
import com.arellomobile.alligator.generator.GeneratorFailedException;
import com.arellomobile.alligator.generator.NavigationFactoryClassGenerator;
import com.arellomobile.alligator.rule.AnnotationRule;
import com.arellomobile.alligator.rule.IncorrectElementException;
import com.arellomobile.alligator.rule.ScreenAnnotationRule;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import me.aartikov.alligator.RegisterScreen;

@SupportedAnnotationTypes("me.aartikov.alligator.RegisterScreen")
public class AlligatorCompiler extends AbstractProcessor {
	private static ErrorReporter sErrorReporter;
	private static Elements elements;

	public static Elements getElementUtils() {
		return elements;
	}

	public static ErrorReporter getErrorReporter() {
		return sErrorReporter;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public synchronized void init(final ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);
		sErrorReporter = new ErrorReporter(processingEnvironment);
		elements = processingEnvironment.getElementUtils();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> result = new HashSet<>();
		Collections.addAll(result,
				RegisterScreen.class.getCanonicalName());
		return result;
	}

	@Override
	public boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
		if (set.isEmpty()) {
			return false;
		}

		return interruptProcess(roundEnvironment);
	}

	private boolean interruptProcess(final RoundEnvironment roundEnvironment) {
		checkScreens(roundEnvironment, RegisterScreen.class, new ScreenAnnotationRule());

		NavigationFactoryClassGenerator navigationFactoryClassGenerator = new NavigationFactoryClassGenerator();
		generateCode(navigationFactoryClassGenerator, roundEnvironment);

		return true;
	}

	private void checkScreens(final RoundEnvironment roundEnv, Class<? extends Annotation> clazz, AnnotationRule annotationRule) {
		for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(clazz)) {
			try {
				annotationRule.checkAnnotation(annotatedElement);
			}
			catch (IncorrectElementException e) {
				e.printStackTrace();
				sErrorReporter.reportError(e.getLocalizedMessage(), annotatedElement);
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends Element> void generateCode(Generator<T> generator, RoundEnvironment roundEnvironment) {
		List<JavaFile> javaFiles = new ArrayList<>();
		for (String annotationName : generator.getSupportedAnnotations()) {
			try {
				for (Element annotatedElements : roundEnvironment.getElementsAnnotatedWith((Class<? extends Annotation>) Class.forName(annotationName))) {
					generator.implementFor((T) annotatedElements);
				}
			}
			catch (ClassNotFoundException e) {
				sErrorReporter.reportError("Unknown annotation " + annotationName, null);
			}
			catch (ClassCastException e) {
				sErrorReporter.reportError(e.getLocalizedMessage(), null);
			}
		}

		try {
			generator.generate(javaFiles);
		}
		catch (GeneratorFailedException e) {
			sErrorReporter.reportError(e.getLocalizedMessage(), e.getElement());
		}

		for (JavaFile javaFile : javaFiles) {
			try {
				javaFile.writeTo(processingEnv.getFiler());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
