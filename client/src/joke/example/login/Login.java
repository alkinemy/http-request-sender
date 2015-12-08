package joke.example.login;

import joke.lib.client.TcpClient;
import joke.lib.message.request.Request;

public class Login {
	public static void main(String[] args) {
		TcpClient client = new TcpClient();
		Request request = new Request() {
			@Override public String getAddress() {
				return "";
			}

			@Override public int getPort() {
				return 0;
			}

			@Override public String getMessage() {
				return "";
			}
		};
		client.send(request);
	}
}
