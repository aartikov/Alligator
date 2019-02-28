package me.aartikov.alligatorcompiler;

public enum ScreenType {
	ACTIVITY("android.app.Activity"),
	DIALOG_FRAGMENT("androidx.fragment.app.DialogFragment"),
	FRAGMENT("androidx.fragment.app.Fragment");

	private final String className;

	ScreenType(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}