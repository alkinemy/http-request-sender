package joke.lib.message.response.http.startline;

import joke.lib.message.general.http.startline.HttpVersion;
import joke.lib.message.response.http.HttpResponseComponent;

public class HttpResponseStartLine implements HttpResponseComponent {
	private HttpVersion version;
	private HttpStatus status;

	public HttpVersion getVersion() {
		return version;
	}

	public void setVersion(HttpVersion version) {
		this.version = version;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
