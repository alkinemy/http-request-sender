package joke.lib.server.nonblocking.classic.http;

import joke.lib.message.request.http.HttpRequest;
import joke.lib.message.request.parser.HttpRequestParser;
import joke.lib.message.response.http.HttpResponse;
import joke.lib.server.nonblocking.classic.NonBlockingHttpServerWorker;
import joke.lib.server.nonblocking.classic.tcp.NonBlockingTcpServer;

import java.nio.channels.SocketChannel;

public class NonBlockingHttpServer extends NonBlockingTcpServer<HttpRequest, HttpResponse> {
	public static final int DEFAULT_PORT = 80;

	public NonBlockingHttpServer() {
		this(DEFAULT_PORT);
	}

	public NonBlockingHttpServer(int port) {
		super(port, new HttpRequestParser());
	}

	@Override protected Runnable createServerWorker(SocketChannel socketChannel) {
		return () -> new NonBlockingHttpServerWorker(parser).work(socketChannel);
	}
}
