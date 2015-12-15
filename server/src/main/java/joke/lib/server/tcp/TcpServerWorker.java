package joke.lib.server.tcp;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.DefaultResponse;
import joke.lib.message.response.Response;

import java.io.*;
import java.net.Socket;

public class TcpServerWorker<Q extends Request, P extends Response> {

	private final RequestParser<Q> parser;

	public TcpServerWorker(RequestParser<Q> parser) {
		this.parser = parser;
	}

	public void work(Socket socket) {
		try (OutputStream socketOutputStream = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));
			InputStream socketInputStream = new BufferedInputStream(socket.getInputStream());
			ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream()) {

			int data;
			while ((data = socketInputStream.read()) != -1) {
				requestOutputStream.write(data);
			}
			System.out.println(new String(requestOutputStream.toByteArray(), BlockingTcpServer.DEFAULT_CHARSET));

			Q request = parser.parse(new String(requestOutputStream.toByteArray(), BlockingTcpServer.DEFAULT_CHARSET));

			P response = handleRequest(request);

			socketOutputStream.write(response.getMessage().getBytes(BlockingTcpServer.DEFAULT_CHARSET));
			socketOutputStream.flush();

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuietly(socket);
		}
	}

	//TODO override
	protected P handleRequest(Q request) {
		return (P) new DefaultResponse("???");
	}

	private void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
