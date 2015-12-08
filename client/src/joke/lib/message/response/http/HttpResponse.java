package joke.lib.message.response.http;

import joke.lib.message.general.http.payload.HttpPayload;
import joke.lib.message.general.http.header.HttpHeader;
import joke.lib.message.request.http.startline.HttpRequestStartLine;
import joke.lib.message.response.Response;

public class HttpResponse implements Response {
	private HttpRequestStartLine startLine;
	private HttpHeader header;
	private HttpPayload payload;
}
