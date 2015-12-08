package joke.lib.message.response.parser;

import joke.lib.message.response.Response;

public interface ResponseParser<T extends Response> {
	T parse(String responseString);
}
