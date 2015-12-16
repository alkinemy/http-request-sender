package joke.example;

import joke.lib.server.nonblocking.http.NonBlockingHttpServer;

public class NonBlockingServer {

	public static void main(String[] args) {
		NonBlockingHttpServer server = new NonBlockingHttpServer(8080);
		server.start();
	}
}
