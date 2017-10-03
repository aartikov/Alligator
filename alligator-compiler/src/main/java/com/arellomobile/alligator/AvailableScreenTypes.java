package com.arellomobile.alligator;

public enum AvailableScreenTypes {
	ACTIVITY("android.app.Activity"),
	SUPPORT_FRAGMENT("android.support.v4.app.Fragment"),
	SUPPORT_DIALOG_FRAGMENT("android.support.v4.app.DialogFragment");

	public final String className;

	AvailableScreenTypes(String className) {
		this.className = className;
	}

	public static String availableTypes() {
		StringBuilder stringBuilder = new StringBuilder();

		for (AvailableScreenTypes availableScreenTypes : AvailableScreenTypes.values()) {
			stringBuilder.append(availableScreenTypes.className);

			if (availableScreenTypes.ordinal() < AvailableScreenTypes.values().length - 1) {
				stringBuilder.append(", ");
			} else {
				stringBuilder.append(".");
			}

		}

		return stringBuilder.toString();
	}
}
