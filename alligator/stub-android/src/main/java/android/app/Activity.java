package android.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class Activity extends Context {
	public static final int RESULT_CANCELED = 0;
	public static final int RESULT_OK = -1;

	public Intent getIntent() {
		throw new RuntimeException("Stub!");
	}

	public void overridePendingTransition(int enterAnim, int exitAnim) {
		throw new RuntimeException("Stub!");
	}

	public PackageManager getPackageManager() {
		throw new RuntimeException("Stub!");
	}

	public final void setResult(int resultCode, Intent data) {
		throw new RuntimeException("Stub!");
	}

	public void finish() {
		throw new RuntimeException("Stub!");
	}
}
