package joke.lib.message.response.parser;

import joke.lib.message.response.DefaultResponse;

public class DefaultResponseParser implements ResponseParser<DefaultResponse> {
	@Override public DefaultResponse parse(String responseString) {
		System.out.println(responseString);
		return new DefaultResponse(responseString);
	}
}
