package joke.lib.message.response.http;

import joke.lib.message.general.http.header.HttpHeader;
import joke.lib.message.general.http.header.HttpHeaders;
import joke.lib.message.general.http.payload.HttpPayload;
import joke.lib.message.general.http.startline.HttpVersion;
import joke.lib.message.response.Response;
import joke.lib.message.response.http.startline.HttpResponseStartLine;
import joke.lib.message.response.http.startline.HttpStatus;

import java.util.*;

public class HttpResponse implements Response {
	private HttpResponseStartLine startLine;
	private HttpHeaders header;
	private HttpPayload payload;

	private HttpResponse(HttpResponseStartLine startLine, HttpHeaders header, HttpPayload payload) {
		Objects.requireNonNull(startLine);
		Objects.requireNonNull(header);
		Objects.requireNonNull(payload);

		this.startLine = startLine;
		this.header = header;
		this.payload = payload;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private HttpVersion version;
		private HttpStatus status;
		private Map<String, HttpHeader> headers;
		private String payload;

		public Builder() {
			this.headers = new LinkedHashMap<>();
		}

		public HttpResponse build() {
			HttpResponseStartLine startLine = new HttpResponseStartLine();
			startLine.setStatus(status);
			startLine.setVersion(version);

			HttpHeaders header = new HttpHeaders(headers);

			HttpPayload payload = new HttpPayload();
			payload.setContent(this.payload);

			return new HttpResponse(startLine, header, payload);
		}

		public Builder version(String version) {
			this.version = HttpVersion.convertToVersion(version);
			return this;
		}

		public Builder statusCode(String status) {
			this.status = HttpStatus.convertToStatus(status);
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
