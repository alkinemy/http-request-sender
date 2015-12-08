package joke.lib.message.response.parser;

import joke.lib.message.response.http.HttpResponse;

public class HttpResponseParser implements ResponseParser<HttpResponse> {
	@Override public HttpResponse parse(String responseString) {
		System.out.println(responseString);

		return null;
	}
}
