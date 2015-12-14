package joke.lib.message.common.http.startline;

public enum HttpVersion {
	HTTP_1_0("HTTP/1.0"),
	HTTP_1_1("HTTP/1.1");

	private String version;

	HttpVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public static HttpVersion convertToVersion(String versionString) {
		for(HttpVersion version : HttpVersion.values()) {
			if (version.getVersion().equals(versionString)) {
				return version;
			}
		}
		return null;
	}
}
