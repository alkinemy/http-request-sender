package joke.lib.message.request.http;

import joke.lib.message.request.Request;
import joke.lib.message.general.http.header.HttpHeaders;
import joke.lib.message.general.http.payload.HttpPayload;
import joke.lib.message.request.http.startline.HttpMethod;
import joke.lib.message.request.http.startline.HttpRequestStartLine;
import joke.lib.message.general.http.startline.HttpVersion;

import java.util.Objects;

public class HttpRequest implements Request {
	public static final int DEFAULT_PORT = 80;

	private HttpRequestStartLine startLine;
	private HttpHeaders header;
	private HttpPayload payload;

	private HttpRequest(HttpRequestStartLine startLine, HttpHeaders header, HttpPayload payload) {
		Objects.requireNonNull(startLine, "Start line should not be null");
		Objects.requireNonNull(header, "Header should not be null");
		Objects.requireNonNull(payload, "Payload should not be null");

		this.startLine = startLine;
		this.header = header;
		this.payload = payload;
	}

	@Override public String getMessage() {
		StringBuilder request = new StringBuilder();
		request.append(startLine.buildComponent())
			.append(System.lineSeparator())
			.append(header.buildComponent())
			.append(System.lineSeparator())
			.append(payload.buildComponent());
		return request.toString();
	}

	public String getAddress() {
		return startLine.getTarget();
	}

	@Override public int getPort() {
		return DEFAULT_PORT;
	}

	public static Builder builder() {
		return new Builder();
	}

	//TODO builder 구현
	public static class Builder {
		private HttpMethod method;
		private String target;
		private HttpVersion version;

		public HttpRequest build() {
			HttpRequestStartLine startLine = new HttpRequestStartLine();
			startLine.setMethod(method);
			startLine.setTarget(target);
			startLine.setVersion(version);
			HttpHeaders header = new HttpHeaders();
			HttpPayload payload = new HttpPayload();
			return new HttpRequest(startLine, header, payload);
		}

		public Builder method(HttpMethod method) {
			this.method = method;
			return this;
		}

		public Builder target(String target) {
			this.target = target;
			return this;
		}

		public Builder version(HttpVersion version) {
			this.version = version;
			return this;
		}
	}
}
