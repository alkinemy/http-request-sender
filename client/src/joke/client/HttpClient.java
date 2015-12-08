package joke.client;

import joke.message.request.http.HttpRequest;
import joke.message.response.parser.ResponseParser;
import joke.message.response.http.HttpResponse;
import joke.message.response.parser.HttpResponseParser;

public class HttpClient {
	private TcpClient client;

	private ResponseParser parser;

	public HttpClient() {
		parser = new HttpResponseParser();
		this.client = new TcpClient(parser);
	}

	public HttpResponse send(HttpRequest request) {
		return (HttpResponse) client.send(request);
	}

	public void setResponseParser(ResponseParser parser) {
		this.parser = parser;
		client.setResponseParser(parser);
	}
}
