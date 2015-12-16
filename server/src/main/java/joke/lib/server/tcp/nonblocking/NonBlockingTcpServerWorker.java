package joke.lib.server.tcp.nonblocking;

import joke.lib.message.request.Request;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.DefaultResponse;
import joke.lib.message.response.Response;
import joke.lib.server.ServerWorker;
import joke.lib.server.tcp.blocking.BlockingTcpServer;
import joke.lib.util.CloseableUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NonBlockingTcpServerWorker<Q extends Request, P extends Response> implements ServerWorker<Q, P> {

	private final RequestParser<Q> parser;

	public NonBlockingTcpServerWorker(RequestParser<Q> parser) {
		this.parser = parser;
	}

	public void work(SocketChannel socketChannel) {
		try {
			String input = read(socketChannel);
			if (input.isEmpty()) {
				socketChannel.close();
				return;
			}

			Q request = parser.parse(input);

			P response = handleRequest(request);
			socketChannel.write(ByteBuffer.wrap(response.getMessage().getBytes()));

			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(socketChannel);
		}
	}

	private String read(SocketChannel socketChannel) throws IOException {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int count;
			while ((count = socketChannel.read(buffer)) > 0) {
				buffer.flip();
				outputStream.write(buffer.array(), 0, count);
				buffer.clear();
			}
			return new String(outputStream.toByteArray(), BlockingTcpServer.DEFAULT_CHARSET);
		}
	}

	public P handleRequest(Q request) {
		return (P) new DefaultResponse("???");
	}
}
