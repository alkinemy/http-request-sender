package joke.lib.util;

import java.io.Closeable;
import java.io.IOException;

public abstract class CloseableUtils {
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
