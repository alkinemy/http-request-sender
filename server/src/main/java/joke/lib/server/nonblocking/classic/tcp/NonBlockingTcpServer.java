package joke.lib.server.nonblocking.classic.tcp;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.Response;
import joke.lib.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NonBlockingTcpServer<Q extends Request, P extends Response> implements Server {
	private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(100);

	protected RequestParser<Q> parser;
	private final int port;

	private volatile List<SocketChannel> channels = new CopyOnWriteArrayList<>();

	public NonBlockingTcpServer(int port, RequestParser<Q> parser) {
		this.port = port;
		this.parser = parser;
	}

	@Override public void start() {
		try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);

			Thread acceptingConnectionWorker = new Thread(() -> {
				while(true) {
					try {
						SocketChannel socketChannel = serverSocketChannel.accept();
						if (socketChannel == null) {
							continue;
						}

						System.out.println("Socket channel connected");

						socketChannel.configureBlocking(false);
						channels.add(socketChannel);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			Thread readingConnectedSocketsWorker = new Thread(() -> {
				while(true) {
					Iterator<SocketChannel> iterator = channels.iterator();
					while (iterator.hasNext()) {
						SocketChannel socketChannel = iterator.next();
						if (socketChannel.isConnected()) {
							THREAD_POOL.execute(createServerWorker(socketChannel));
						} else {
							channels.remove(socketChannel);
							System.out.println("Socket channel closed");
						}
					}
				}
			});

			acceptingConnectionWorker.start();
			readingConnectedSocketsWorker.start();

			while(true) {
				//계속 실행되게 하기 위함
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Runnable createServerWorker(SocketChannel socketChannel) {
		return () -> new NonBlockingTcpServerWorker<Q, P>(parser).work(socketChannel);
	}
}
