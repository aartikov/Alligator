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
import javax.tools.Diagnostic;

import me.aartikov.alligator.annotations.RegisterScreen;

public class AlligatorCompiler extends AbstractProcessor {
	private RegistrationAnnotatedClassCreator annotatedClassCreator;
	private NavigationFactoryGenerator navigationFactoryGenerator;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		ProcessingUtils utils = new ProcessingUtils(processingEnv);
		annotatedClassCreator = new RegistrationAnnotatedClassCreator(utils);
		navigationFactoryGenerator = new NavigationFactoryGenerator(utils);
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
				RegistrationAnnotatedClass annotatedClass = annotatedClassCreator.create(element);
				checkThatScreenIsNotAlreadyRegistered(annotatedClasses, annotatedClass);
				annotatedClasses.add(annotatedClass);
			}

			if (!annotatedClasses.isEmpty()) {
				JavaFile javaFile = navigationFactoryGenerator.generate(annotatedClasses);
				javaFile.writeTo(processingEnv.getFiler());
			}
		} catch (ProcessingException e) {
			logError(e.getElement(), e.getMessage());
		} catch (IOException e) {
			logError(null, e.getMessage());
		}
		return true;
	}

	private void checkThatScreenIsNotAlreadyRegistered(List<RegistrationAnnotatedClass> annotatedClasses, RegistrationAnnotatedClass newAnnotatedClass) throws ProcessingException {
		for (RegistrationAnnotatedClass annotatedClass : annotatedClasses) {
			if (annotatedClass.getScreenClassName().equals(newAnnotatedClass.getScreenClassName())) {
				throw new ProcessingException(newAnnotatedClass.getClassElement(), "Screen %s is already registered.", newAnnotatedClass.getScreenClassName());
			}
		}
	}

	private void logError(Element element, String message) {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
	}
}
