package joke.client;

import joke.message.request.http.HttpRequest;
import joke.message.response.http.HttpResponse;

public class HttpClient {
	private TcpClient client;

	public HttpClient() {
		this.client = new TcpClient();
	}

	public HttpResponse sendRequest(HttpRequest request) {
		return (HttpResponse) client.connectAndSend(request);
	}
}
