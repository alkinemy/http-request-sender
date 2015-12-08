package joke.message.response.parser;

import joke.message.response.Response;

public interface ResponseParser<T extends Response> {
	T parse(String responseString);
}
