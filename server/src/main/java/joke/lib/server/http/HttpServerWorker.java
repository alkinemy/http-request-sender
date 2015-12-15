package joke.lib.server.http;

import joke.lib.message.request.http.HttpRequest;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.http.HttpResponse;
import joke.lib.message.response.http.startline.HttpStatus;
import joke.lib.server.tcp.BlockingTcpServer;
import joke.lib.server.tcp.TcpServerWorker;

import java.io.*;
import java.net.URL;

public class HttpServerWorker extends TcpServerWorker<HttpRequest, HttpResponse> {
	public HttpServerWorker(RequestParser<HttpRequest> parser) {
		super(parser);
	}

	@Override protected HttpResponse handleRequest(HttpRequest request) {
		try {
			//TODO target 경로처리
			String target = request.getStartLine().getTarget();
			if (target.startsWith("/")) {
				target = target.replaceFirst("/", "");
			}
			URL targetResource = getClass().getClassLoader().getResource(target);
			if (targetResource == null) {
				throw new FileNotFoundException("Target file is not exist");
			}
			File targetFile = new File(targetResource.getFile());

			BufferedReader reader = new BufferedReader(new FileReader(targetFile));
			String data;
			StringBuilder fileData = new StringBuilder();
			while ((data = reader.readLine()) != null) {
				fileData.append(data);
			}

			return HttpResponse.builder()
				.statusCode(HttpStatus.OK)
				.version(request.getStartLine().getVersion())
				.payload(fileData.toString())
				.build();
		} catch (FileNotFoundException exception) {
			return HttpResponse.builder()
				.statusCode(HttpStatus.NOT_FOUND)
				.version(request.getStartLine().getVersion())
				.build();
		} catch (Exception exception) {
			return HttpResponse.builder()
				.statusCode(HttpStatus.INTERNAL)
				.version(request.getStartLine().getVersion())
				.payload(exception.getMessage())
				.build();
		}
	}

	@Override protected String read(InputStream socketInputStream) throws IOException {
		try (ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream()) {
			int current;
			while ((current = socketInputStream.read()) != -1 && socketInputStream.available() != 0) {
				requestOutputStream.write(current);
			}

			return new String(requestOutputStream.toByteArray(), BlockingTcpServer.DEFAULT_CHARSET);
		}
	}
}
