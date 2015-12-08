package joke.lib.message.general.http.header;

import joke.lib.message.request.http.HttpRequestComponent;

import java.util.ArrayList;
import java.util.List;

public class HttpHeaders implements HttpRequestComponent {

	public static final String SEPARATOR = ": ";

	private List<HttpHeader> headers;

	public HttpHeaders() {
		this.headers = new ArrayList<>();
	}

	public HttpHeaders(List<HttpHeader> headers) {
		//FIXME 복사 문제?
		this.headers = headers;
	}

	@Override public String buildComponent() {
		StringBuilder message = new StringBuilder();
		for(HttpHeader header : headers) {
			message.append(header.getName())
				.append(SEPARATOR).append(header.getValue())
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
