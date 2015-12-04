package joke.client;

import joke.message.request.Request;
import joke.message.response.Response;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpClient {
	public Response connectAndSend(Request request) {
		DataOutputStream outputStream = null;
		try (Socket socket = new Socket(request.getAddress(), request.getPort())) {
			outputStream = new DataOutputStream(socket.getOutputStream());

			outputStream.writeUTF(request.getMessage());

			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuietly(outputStream);
		}

		return null; //TODO Response를 리턴하도록 처리
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
