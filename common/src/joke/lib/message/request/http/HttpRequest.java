package joke.lib.message.request.http;

import joke.lib.message.common.http.header.HttpHeader;
import joke.lib.message.common.http.header.HttpHeaders;
import joke.lib.message.common.http.payload.HttpPayload;
import joke.lib.message.common.http.startline.HttpVersion;
import joke.lib.message.request.Request;
import joke.lib.message.request.http.startline.HttpMethod;
import joke.lib.message.request.http.startline.HttpRequestStartLine;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class HttpRequest implements Request {
	public static final int DEFAULT_PORT = 80;

	private HttpRequestStartLine startLine;
	private HttpHeaders headers;
	private HttpPayload payload;

	private String baseUrl;
	private int port = DEFAULT_PORT;

	private HttpRequest(HttpRequestStartLine startLine, String baseUrl, Integer port, HttpHeaders headers, HttpPayload payload) {
		Objects.requireNonNull(startLine, "Start line should not be null");
		Objects.requireNonNull(baseUrl, "Base url should not be null");

		this.startLine = startLine;
		this.baseUrl = baseUrl;
		this.port = port != null ? port : DEFAULT_PORT;
		this.headers = headers;
		this.payload = payload;
	}

	@Override public String getMessage() {
		StringBuilder request = new StringBuilder();
		request.append(startLine.buildComponent());
		if (headers != null && !headers.isEmpty()) {
			request.append(System.lineSeparator())
				.append(headers.buildComponent());
		}
		if (payload != null && payload.getContent() != null && !payload.getContent().isEmpty()) {
			request.append(System.lineSeparator())
				.append(payload.buildComponent());
		}
		request.append(System.lineSeparator()).append(System.lineSeparator());
		return request.toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private HttpMethod method;
		private String target;
		private String baseUrl;
		private Integer port;
		private HttpVersion version;
		private Map<String, HttpHeader> headers;
		private String payload;

		public Builder() {
			this.headers = new LinkedHashMap<>();
		}

		public HttpRequest build() {
			HttpRequestStartLine startLine = new HttpRequestStartLine();
			startLine.setMethod(this.method);
			startLine.setTarget(this.target);
			startLine.setVersion(this.version);

			HttpHeaders headers = null;
			if (!this.headers.isEmpty()) {
				headers = new HttpHeaders(this.headers);
			}
			HttpPayload payload = null;
			if (this.payload != null) {
				payload = new HttpPayload(this.payload);
			}

			return new HttpRequest(startLine, baseUrl, port, headers, payload);
		}

		public Builder method(HttpMethod method) {
			this.method = method;
			return this;
		}

		public Builder target(String target) {
			this.target = target;
			return this;
		}

		public Builder baseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		public Builder port(Integer port) {
			this.port = port;
			return this;
		}

		public Builder version(HttpVersion version) {
			this.version = version;
			return this;
		}

		public Builder addHeader(HttpHeader header) {
			this.headers.put(header.getName(), header);
			return this;
		}

		public Builder payload(String payload) {
			this.payload = payload;
			return this;
		}
	}
}
