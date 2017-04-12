package android.content;

import java.io.Serializable;

import android.content.pm.PackageManager;
import android.net.Uri;

public class Intent {
	public static final int FLAG_ACTIVITY_NEW_TASK = 268435456;
	public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
	public static final int FLAG_ACTIVITY_SINGLE_TOP = 536870912;
	public static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;

	public Intent() {
		throw new RuntimeException("Stub!");
	}

	public Intent(Context packageContext, Class<?> cls) {
		throw new RuntimeException("Stub!");
	}

	public Intent addFlags(int flags) {
		throw new RuntimeException("Stub!");
	}

	public ComponentName resolveActivity(PackageManager pm) {
		throw new RuntimeException("Stub!");
	}

	public Uri getData() {
		throw new RuntimeException("Stub!");
	}

	public Intent putExtra(String name, String value) {
		throw new RuntimeException("Stub!");
	}

	public Intent putExtra(String name, Serializable value) {
		throw new RuntimeException("Stub!");
	}

	public String getStringExtra(String name) {
		throw new RuntimeException("Stub!");
	}

	public Serializable getSerializableExtra(String name) {
		throw new RuntimeException("Stub!");
	}
}
