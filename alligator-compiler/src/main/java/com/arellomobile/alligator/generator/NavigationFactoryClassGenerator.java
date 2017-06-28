package com.arellomobile.alligator.generator;

import com.arellomobile.alligator.AvailableScreenTypes;
import com.arellomobile.alligator.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import me.aartikov.alligator.RegisterScreen;
import me.aartikov.alligator.ScreenResult;

import static com.arellomobile.alligator.Utils.getClassName;

public final class NavigationFactoryClassGenerator implements Generator<TypeElement> {

	private List<ScreenParams> screenParamsList = new ArrayList<>();
	private Map<String, TypeElement> elements = new HashMap<>();

	@Override
	public void implementFor(final TypeElement typeElement) {
		String screenClass = getScreenClass(typeElement, RegisterScreenKeys.SCREEN_CLASS);
		String screenResult = getScreenClass(typeElement, RegisterScreenKeys.SCREEN_RESULT);

		for (AvailableScreenTypes availableScreenTypes : AvailableScreenTypes.values()) {
			ScreenParams screenParams = new ScreenParams(screenClass, screenResult, Utils.elementClassName(typeElement).toString(), availableScreenTypes);
			if (Utils.isAssignableFrom(typeElement, availableScreenTypes.className)) {
				elements.put(screenParams.typeClass, typeElement);
				screenParamsList.add(screenParams);
				break;
			}
		}

	}

	private String getScreenClass(final TypeElement typeElement, RegisterScreenKeys registerScreenKeys) {
		String neededKey = registerScreenKeys.key;
		TypeMirror annotationTypeMirror = Utils.getAnnotationTypeMirror(typeElement, neededKey);
		return annotationTypeMirror == null ? null : annotationTypeMirror.toString();
	}

	@Override
	public Collection<String> getSupportedAnnotations() {
		return Collections.singleton(RegisterScreen.class.getCanonicalName());
	}

	@Override
	public void generate(final List<JavaFile> list) throws GeneratorFailedException {
		validateScreenParams();
		MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC);

		for (ScreenParams screenParams : screenParamsList) {
			ClassName screenClassName = getClassName(screenParams.screenClass);
			ClassName typeClassName = getClassName(screenParams.typeClass);
			String format = generateRegisterStatement(screenParams.availableScreenTypes);
			if (format == null) {
				continue;
			}
			constructorBuilder.addStatement(format, screenClassName, typeClassName);

			if (screenParams.screenResult != null && !screenParams.screenResult.equals(ScreenResult.class.getName())) {

				if (!screenParams.availableScreenTypes.equals(AvailableScreenTypes.ACTIVITY)) {
					throw new GeneratorFailedException("Screen Result can be apply only for activity", elements.get(screenParams.typeClass));
				}

				ClassName screenResultType = getClassName(screenParams.screenResult);
				constructorBuilder.addStatement("registerScreenForResult($1T.class, $2T.class)", screenClassName, screenResultType);
			}
		}

		TypeSpec navigationFactory = TypeSpec.classBuilder("GeneratedNavigationFactory")
				.addModifiers(Modifier.PUBLIC)
				.superclass(getClassName(Utils.getDefaultPackageName() + ".navigationfactories.RegistryNavigationFactory"))
				.addMethod(constructorBuilder.build())
				.build();

		JavaFile javaFile = JavaFile.builder(Utils.getDefaultPackageName(), navigationFactory)
				.build();

		list.add(javaFile);
	}

	private void validateScreenParams() throws GeneratorFailedException {
		List<String> screenName = new ArrayList<>();
		List<String> typeName = new ArrayList<>();

		for (ScreenParams screenParams : screenParamsList) {
			if (screenName.contains(screenParams.screenClass)) {
				throw new GeneratorFailedException(screenParams.screenClass + " registered for another type.", elements.get(screenParams.typeClass));
			}
			screenName.add(screenParams.screenClass);

			if (typeName.contains(screenParams.typeClass)) {
				throw new GeneratorFailedException(screenParams.typeClass + " registered for another screen.", elements.get(screenParams.typeClass));
			}
			typeName.add(screenParams.screenClass);
		}
	}

	private String generateRegisterStatement(final AvailableScreenTypes availableScreenTypes) {
		String prefix;
		switch (availableScreenTypes) {

			case ACTIVITY:
				prefix = "registerActivity";
				break;
			case SUPPORT_FRAGMENT:
				prefix = "registerFragment";
				break;
			case SUPPORT_DIALOG_FRAGMENT:
				prefix = "registerDialogFragment";
				break;
			default:
				return null;
		}

		return prefix + "($1T.class,$2T.class)";
	}

	private static class ScreenParams {
		final String screenClass;
		final String screenResult;
		final String typeClass;
		final AvailableScreenTypes availableScreenTypes;

		ScreenParams(final String screenClass, String screenResult, final String typeClass, final AvailableScreenTypes availableScreenTypes) {
			this.screenClass = screenClass;
			this.typeClass = typeClass;
			this.screenResult = screenResult;
			this.availableScreenTypes = availableScreenTypes;
		}

		@Override
		public String toString() {
			return "ScreenParams{" +
					"screenClass=" + screenClass +
					", typeClass=" + typeClass +
					", availableScreenTypes=" + availableScreenTypes +
					'}';
		}
	}

	private enum RegisterScreenKeys {
		SCREEN_CLASS("value"), SCREEN_RESULT("screenResult");

		public final String key;

		RegisterScreenKeys(String key) {
			this.key = key;
		}
	}
}
