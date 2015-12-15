package joke.lib.message.request.parser;

import joke.lib.message.request.Request;

public interface RequestParser<T extends Request> {
	T parse(String requestString);
}
