package android.os;

public class Build {

	public static class VERSION {
		public static final int SDK_INT = stub();

		private static int stub() {
			throw new RuntimeException("Stub!");
		}
	}

	public static class VERSION_CODES {
		public static final int LOLLIPOP = 21;
	}
}