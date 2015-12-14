package joke.example;

import joke.lib.server.http.BlockingHttpServer;

public class BlockingServer {

	public static void main(String[] args) {
		BlockingHttpServer server = new BlockingHttpServer(8080);
		server.start();
	}
}
