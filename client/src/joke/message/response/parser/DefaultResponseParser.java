package joke.message.response.parser;

import joke.message.response.DefaultResponse;

public class DefaultResponseParser implements ResponseParser<DefaultResponse> {
	@Override public DefaultResponse parse(String responseString) {
		System.out.println(responseString);
		return new DefaultResponse(responseString);
	}
}
