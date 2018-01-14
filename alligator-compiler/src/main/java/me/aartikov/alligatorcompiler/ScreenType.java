package me.aartikov.alligatorcompiler;

public enum ScreenType {
	ACTIVITY("android.app.Activity"),
	DIALOG_FRAGMENT("android.support.v4.app.DialogFragment"),
	FRAGMENT("android.support.v4.app.Fragment");

	private final String className;

	ScreenType(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}