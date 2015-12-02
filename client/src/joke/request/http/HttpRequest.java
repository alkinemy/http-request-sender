package joke.request.http;

import joke.request.Request;

public class HttpRequest implements Request {
	public static final int DEFAULT_PORT = 80;

	private HttpStartLine startLine;
	private HttpHeader header;
	private HttpPayload payload;

	private HttpRequest(HttpStartLine startLine, HttpHeader header, HttpPayload payload) {
		this.startLine = startLine;
		this.header = header;
		this.payload = payload;
	}

	@Override public String getMessage() {
		StringBuilder request = new StringBuilder();
		request.append(startLine.toString())
			.append(System.lineSeparator())
			.append(header.toString())
			.append(System.lineSeparator())
			.append(payload.toString());
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
		public HttpRequest build() {
			HttpStartLine startLine = new HttpStartLine();
			HttpHeader header = new HttpHeader();
			HttpPayload payload = new HttpPayload();
			return new HttpRequest(startLine, header, payload);
		}
	}
}
