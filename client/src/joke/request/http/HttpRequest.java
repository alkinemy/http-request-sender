package joke.request.http;

import joke.request.Request;
import joke.request.http.header.HttpHeader;
import joke.request.http.payload.HttpPayload;
import joke.request.http.startline.HttpMethod;
import joke.request.http.startline.HttpStartLine;
import joke.request.http.startline.HttpVersion;

import java.util.Objects;

public class HttpRequest implements Request {
	public static final int DEFAULT_PORT = 80;

	private HttpStartLine startLine;
	private HttpHeader header;
	private HttpPayload payload;

	private HttpRequest(HttpStartLine startLine, HttpHeader header, HttpPayload payload) {
		Objects.requireNonNull(startLine, "Start line should not be null");
		Objects.requireNonNull(header, "Header should not be null");
		Objects.requireNonNull(payload, "Payload should not be null");

		this.startLine = startLine;
		this.header = header;
		this.payload = payload;
	}

	@Override public String getMessage() {
		StringBuilder request = new StringBuilder();
		request.append(startLine.buildMessage())
			.append(System.lineSeparator())
			.append(header.buildMessage())
			.append(System.lineSeparator())
			.append(payload.buildMessage());
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
			HttpStartLine startLine = new HttpStartLine();
			startLine.setMethod(method);
			startLine.setTarget(target);
			startLine.setVersion(version);
			HttpHeader header = new HttpHeader();
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
