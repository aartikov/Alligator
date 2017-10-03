package me.aartikov.alligatorcompiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import me.aartikov.alligator.RegisterScreen;

public class AlligatorCompiler extends AbstractProcessor {
	private AnnotationProcessingUtils utils;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		utils = new AnnotationProcessingUtils(processingEnv);
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Collections.singleton(RegisterScreen.class.getCanonicalName());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		try {
			List<RegistrationAnnotatedClass> annotatedClasses = new ArrayList<>();
			for (Element element : roundEnv.getElementsAnnotatedWith(RegisterScreen.class)) {
				RegistrationAnnotatedClass annotatedClass = RegistrationAnnotatedClass.fromElement(element, utils);
				checkThatScreenIsNotAlreadyRegistered(annotatedClasses, annotatedClass);
				annotatedClasses.add(annotatedClass);
			}

			if (!annotatedClasses.isEmpty()) {
				NavigationFactoryGenerator generator = new NavigationFactoryGenerator();
				JavaFile javaFile = generator.generate(annotatedClasses);
				javaFile.writeTo(processingEnv.getFiler());
			}
		} catch (ProcessingException e) {
			utils.logError(e.getElement(), e.getMessage());
		} catch (IOException e) {
			utils.logError(null, e.getMessage());
		}
		return true;
	}

	private static void checkThatScreenIsNotAlreadyRegistered(List<RegistrationAnnotatedClass> annotatedClasses, RegistrationAnnotatedClass newAnnotatedClass) throws ProcessingException {
		for (RegistrationAnnotatedClass annotatedClass : annotatedClasses) {
			if (annotatedClass.getScreenClassName().equals(newAnnotatedClass.getScreenClassName())) {
				throw new ProcessingException(newAnnotatedClass.getClassElement(), "Screen %s is already registered.", newAnnotatedClass.getScreenClassName());
			}
		}
	}
}
