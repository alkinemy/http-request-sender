package joke.lib.message.common.http.header;

import joke.lib.message.request.http.HttpRequestComponent;
import joke.lib.message.response.http.HttpResponseComponent;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpHeaders extends LinkedHashMap<String, HttpHeader> implements HttpRequestComponent, HttpResponseComponent {

	public static final String SEPARATOR = ": ";

	public HttpHeaders() {
		super();
	}

	public HttpHeaders(Map<String, HttpHeader> headers) {
		super(headers);
	}

	@Override public String buildComponent() {
		StringBuilder message = new StringBuilder();
		for(Map.Entry<String, HttpHeader> header : this.entrySet()) {
			message.append(header.getKey())
				.append(SEPARATOR).append(header.getValue().getValue())
				.append(System.lineSeparator());
		}
		return message.toString();
	}

}
