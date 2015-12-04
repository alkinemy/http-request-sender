package joke.request.http.header;

import joke.request.http.HttpRequestPotion;

public class HttpHeader implements HttpRequestPotion {
	private GeneralHeader general;
	private RequestHeader request;
	private ResponseHeader response;
	private EntityHeader entity;
	private ExtensionHeader extension;

	@Override public String buildMessage() {
		//TODO 구현
		return null;
	}
}
