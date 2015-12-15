package joke.lib.server.tcp.blocking;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.Response;
import joke.lib.server.Server;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingTcpServer<Q extends Request, P extends Response> implements Server {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(100);

	private final int port;

	@Setter
	protected RequestParser<Q> parser;

	public BlockingTcpServer(int port, RequestParser<Q> parser) {
		this.port = port;
		this.parser = parser;
	}

	@Override public void start() {
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			while(true) {
				Socket socket = serverSocket.accept();
				THREAD_POOL.execute(createWorkerThread(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//TODO override
	protected Thread createWorkerThread(Socket socket) {
		return new Thread(() -> new BlockingTcpServerWorker<Q, P>(parser).work(socket));
	}
}
