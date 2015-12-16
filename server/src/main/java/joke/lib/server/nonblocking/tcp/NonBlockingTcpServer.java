package joke.lib.server.nonblocking.tcp;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.Response;
import joke.lib.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NonBlockingTcpServer<Q extends Request, P extends Response> implements Server {
	private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(100);

	protected RequestParser<Q> parser;
	private final int port;

	public NonBlockingTcpServer(int port, RequestParser<Q> parser) {
		this.port = port;
		this.parser = parser;
	}

	@Override public void start() {
		try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);

			while(true) {
				SocketChannel socketChannel = serverSocketChannel.accept();
				if (socketChannel == null) {
					//뭔가를 해야하나?
					continue;
				}

				socketChannel.configureBlocking(false);
				THREAD_POOL.execute(createWorkerThread(socketChannel));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Thread createWorkerThread(SocketChannel socketChannel) {
		return new Thread(() -> new NonBlockingTcpServerWorker<Q, P>(parser).work(socketChannel));
	}
}
