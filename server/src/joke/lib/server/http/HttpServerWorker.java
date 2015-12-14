package joke.lib.server.http;

import joke.lib.message.request.http.HttpRequest;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.http.HttpResponse;
import joke.lib.message.response.http.startline.HttpStatus;
import joke.lib.server.tcp.TcpServerWorker;

import java.io.*;

public class HttpServerWorker extends TcpServerWorker<HttpRequest, HttpResponse> {
	private static final String RESOURCE_ROOT_PATH = "resources";

	public HttpServerWorker(RequestParser<HttpRequest> parser) {
		super(parser);
	}

	@Override protected HttpResponse handleRequest(HttpRequest request) {
		try {
			//TODO target 경로처리
			String target = request.getStartLine().getTarget();
			File file = new File(RESOURCE_ROOT_PATH + target);

			BufferedReader reader = new BufferedReader(new FileReader(file));
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
		} catch (IOException exception) {
			return HttpResponse.builder()
				.statusCode(HttpStatus.INTERNAL)
				.version(request.getStartLine().getVersion())
				.payload(exception.getMessage())
				.build();
		}
	}
}
