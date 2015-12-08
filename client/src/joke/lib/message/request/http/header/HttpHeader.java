package joke.lib.message.request.http.header;

import joke.lib.message.request.http.HttpRequestComponent;

public class HttpHeader implements HttpRequestComponent {
	private GeneralHeader general;
	private RequestHeader request;
	private ResponseHeader response;
	private EntityHeader entity;
	private ExtensionHeader extension;

	@Override public String buildComponent() {
		StringBuilder header = new StringBuilder();
		header.append(general.buildHeader()).append(System.lineSeparator())
			.append(request.buildHeader()).append(System.lineSeparator())
			.append(response.buildHeader()).append(System.lineSeparator())
			.append(entity.buildHeader()).append(System.lineSeparator())
			.append(extension.buildHeader()).append(System.lineSeparator());
		return header.toString();
	}
}
