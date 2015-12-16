package joke.example;

import joke.lib.server.nonblocking.classic.http.NonBlockingClassicHttpServer;

public class NonBlockingServer {

	public static void main(String[] args) {
		NonBlockingClassicHttpServer server = new NonBlockingClassicHttpServer(8080);
		server.start();
	}
}
