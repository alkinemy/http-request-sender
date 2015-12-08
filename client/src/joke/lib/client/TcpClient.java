package joke.lib.client;

import joke.lib.message.request.Request;
import joke.lib.message.response.Response;
import joke.lib.message.response.parser.ResponseParser;
import joke.lib.message.response.parser.DefaultResponseParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TcpClient {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private ResponseParser parser;

	public TcpClient() {
		this.parser = new DefaultResponseParser();
	}

	public TcpClient(ResponseParser parser) {
		this.parser = parser;
	}

	public Response send(Request request) {
		try (Socket socket = new Socket(request.getAddress(), request.getPort());
			OutputStream socketOutputStream = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));
			InputStream socketInputStream = new BufferedInputStream(socket.getInputStream());
			ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream()) {

			socketOutputStream.write(request.getMessage().getBytes(DEFAULT_CHARSET));
			socketOutputStream.flush();

			int data;
			while((data = socketInputStream.read()) != -1) {
				responseOutputStream.write(data);
			}

			return parser.parse(new String(responseOutputStream.toByteArray(), DEFAULT_CHARSET));

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setResponseParser(ResponseParser parser) {
		this.parser = parser;
	}
}
