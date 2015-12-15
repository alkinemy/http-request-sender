package joke.lib.server.tcp.blocking;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.DefaultResponse;
import joke.lib.message.response.Response;
import joke.lib.server.ServerWorker;
import joke.lib.util.CloseableUtils;

import java.io.*;
import java.net.Socket;

public class BlockingTcpServerWorker<Q extends Request, P extends Response> implements ServerWorker<Q, P> {

	private final RequestParser<Q> parser;

	public BlockingTcpServerWorker(RequestParser<Q> parser) {
		this.parser = parser;
	}

	public void work(Socket socket) {
		try (OutputStream socketOutputStream = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));
			InputStream socketInputStream = new BufferedInputStream(socket.getInputStream())) {

			String input = read(socketInputStream);
			if (input.isEmpty()) {
				socket.close();
				return;
			}
//			System.out.println("----request");
//			System.out.println(input);
			Q request = parser.parse(input);

			P response = handleRequest(request);

//			System.out.println("----response");
//			System.out.println(response.getMessage());

			socketOutputStream.write(response.getMessage().getBytes(BlockingTcpServer.DEFAULT_CHARSET));
			socketOutputStream.flush();

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(socket);
		}
	}

	protected String read(InputStream socketInputStream) throws IOException {
		try (ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream()) {
			int data;
			while ((data = socketInputStream.read()) != -1) {
				requestOutputStream.write(data);
			}

			return new String(requestOutputStream.toByteArray(), BlockingTcpServer.DEFAULT_CHARSET);
		}
	}

	//TODO override
	public P handleRequest(Q request) {
		return (P) new DefaultResponse("???");
	}
}
