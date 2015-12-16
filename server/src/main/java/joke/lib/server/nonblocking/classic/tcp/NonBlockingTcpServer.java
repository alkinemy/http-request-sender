package joke.lib.server.nonblocking.classic.tcp;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.Response;
import joke.lib.server.Server;
import joke.lib.server.nonblocking.classic.SocketChannelWrapper;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NonBlockingTcpServer<Q extends Request, P extends Response> implements Server, Closeable {
	private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(100);

	protected RequestParser<Q> parser;
	private final int port;

	private volatile List<SocketChannelWrapper> channels = new CopyOnWriteArrayList<>();

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

						socketChannel.configureBlocking(false);
						channels.add(SocketChannelWrapper.wrap(socketChannel));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			Thread readingConnectedSocketsWorker = new Thread(() -> {
				while(true) {
//					for(SocketChannelWrapper channel : channels) {
//						if (channel.isConnected()) {
//							if (!channel.isInProgress()) {
//								channel.setInProgress(true);
//								THREAD_POOL.execute(createServerWorker(channel));
//							}
//						} else {
//							if (!channel.isInProgress()) {
//								channels.remove(channel);
//							}
//						}
//					}

					Iterator<SocketChannelWrapper> iterator = channels.iterator();
					while (iterator.hasNext()) {
						SocketChannelWrapper socketChannel = iterator.next();
						if (socketChannel.isConnected()) {
							if (!socketChannel.isInProgress()) {
								socketChannel.setInProgress(true);
								THREAD_POOL.execute(createServerWorker(socketChannel));
							}
						} else {
							if (!socketChannel.isInProgress()) {
								channels.remove(socketChannel);
							}
						}
					}
				}
			});

//			Thread countSocketChannel = new Thread(() -> {
//				while(true) {
//					try {
//						System.out.println("Socket count: " + channels.size());
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			});

			acceptingConnectionWorker.start();
			readingConnectedSocketsWorker.start();
//			countSocketChannel.start();

			while(true) {
				//계속 실행되게 하기 위함
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Runnable createServerWorker(SocketChannelWrapper socketChannel) {
		return () -> new NonBlockingTcpServerWorker<Q, P>(parser).work(socketChannel);
	}

	@Override public void close() throws IOException {
		THREAD_POOL.shutdown();
		//열린 소켓 닫기
	}
}
