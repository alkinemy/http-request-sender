package joke.example;

import joke.lib.server.blocking.http.BlockingHttpServer;

public class BlockingServer {

	public static void main(String[] args) {
		BlockingHttpServer server = new BlockingHttpServer(8080);
		server.start();
	}
}
