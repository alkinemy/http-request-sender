package joke.lib.message.general.http.header;

import joke.lib.message.request.http.HttpRequestComponent;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpHeaders extends LinkedHashMap<String, HttpHeader> implements HttpRequestComponent {

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

	//	private GeneralHeader general;
//	private RequestHeader request;
//	private ResponseHeader response;
//	private EntityHeader entity;
//	private ExtensionHeader extension;
//
//	@Override public String buildComponent() {
//		StringBuilder header = new StringBuilder();
//		header.append(general.buildHeader()).append(System.lineSeparator())
//			.append(request.buildHeader()).append(System.lineSeparator())
//			.append(response.buildHeader()).append(System.lineSeparator())
//			.append(entity.buildHeader()).append(System.lineSeparator())
//			.append(extension.buildHeader()).append(System.lineSeparator());
//		return header.toString();
//	}


}
