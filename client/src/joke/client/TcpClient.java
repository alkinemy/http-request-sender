package joke.client;

import joke.message.request.Request;
import joke.message.response.Response;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TcpClient {

	public Response connectAndSend(Request request) {
		try (Socket socket = new Socket(request.getAddress(), request.getPort());
			OutputStream socketOutputStream = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));
			InputStream socketInputStream = new BufferedInputStream(socket.getInputStream());
			ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

			socketOutputStream.write(request.getMessage().getBytes(Charset.forName("UTF-8")));
			socketOutputStream.flush();

			int data;
			while((data = socketInputStream.read()) != -1) {
				bos.write(data);
			}
			byte[] bytes = bos.toByteArray();
			System.out.println(new String(bytes, Charset.forName("UTF-8")));

			return null;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
