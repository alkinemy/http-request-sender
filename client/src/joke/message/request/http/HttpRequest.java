package joke.message.request.http;

import joke.message.request.Request;
import joke.message.request.http.header.HttpHeader;
import joke.message.general.http.payload.HttpPayload;
import joke.message.request.http.startline.HttpMethod;
import joke.message.request.http.startline.HttpStartLine;
import joke.message.request.http.startline.HttpVersion;

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