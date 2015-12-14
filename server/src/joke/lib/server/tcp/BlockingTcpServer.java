package joke.lib.server.tcp;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.Response;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class BlockingTcpServer<Q extends Request, P extends Response> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final int port;

	@Setter
	protected RequestParser<Q> parser;

	public BlockingTcpServer(int port, RequestParser<Q> parser) {
		this.port = port;
		this.parser = parser;
	}

	public void start() {
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			while(true) {
				Socket socket = serverSocket.accept();
				Thread thread = createWorkerThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//TODO override
	protected Thread createWorkerThread(Socket socket) {
		return new Thread(() -> new TcpServerWorker<Q, P>(parser).work(socket));
	}
}