package joke.lib.server.http;

import joke.lib.message.request.http.HttpRequest;
import joke.lib.message.request.parser.HttpRequestParser;
import joke.lib.message.response.http.HttpResponse;
import joke.lib.server.tcp.BlockingTcpServer;

import java.net.Socket;

public class BlockingHttpServer extends BlockingTcpServer<HttpRequest, HttpResponse> {

	public static final int DEFAULT_PORT = 80;

	public BlockingHttpServer() {
		super(DEFAULT_PORT, new HttpRequestParser());
	}

	public BlockingHttpServer(int port) {
		super(port, new HttpRequestParser());
	}

	@Override protected Thread createWorkerThread(Socket socket) {
		return new Thread(() -> new HttpServerWorker(parser).work(socket));
	}
}
