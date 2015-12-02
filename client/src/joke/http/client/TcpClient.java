package joke.http.client;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpClient {
	private final String address;
	private final int port;

	public TcpClient(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void connectAndSend(Object obj) {
		DataOutputStream outputStream = null;
		try (Socket socket = new Socket(address, port)) {
			outputStream = new DataOutputStream(socket.getOutputStream());

			outputStream.writeUTF(obj.toString());

			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuietly(outputStream);
		}
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
