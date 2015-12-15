package joke.lib.server;

import joke.lib.message.request.Request;
import joke.lib.message.response.Response;

public interface ServerWorker<Q extends Request, P extends Response> {
	P handleRequest(Q request);
}
