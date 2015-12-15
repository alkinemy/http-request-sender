package joke.lib.server.http.blocking;

import joke.lib.message.request.http.HttpRequest;
import joke.lib.message.request.parser.RequestParser;
import joke.lib.message.response.http.HttpResponse;
import joke.lib.message.response.http.startline.HttpStatus;
import joke.lib.server.tcp.blocking.BlockingTcpServer;
import joke.lib.server.tcp.blocking.BlockingTcpServerWorker;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class BlockingHttpServerWorker extends BlockingTcpServerWorker<HttpRequest, HttpResponse> {

	private static final int[] END_PATTERN = new int[] {13, 10, 13, 10};

	public BlockingHttpServerWorker(RequestParser<HttpRequest> parser) {
		super(parser);
	}

	@Override public HttpResponse handleRequest(HttpRequest request) {
		try {
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
			int[] pattern = new int[4];
			while ((current = socketInputStream.read()) != -1) {
				requestOutputStream.write(current);

				pattern = shiftLeftAndAppend(pattern, current);
				if (Arrays.equals(END_PATTERN, pattern)) {
					break;
				}
			}

			return new String(requestOutputStream.toByteArray(), BlockingTcpServer.DEFAULT_CHARSET);
		}
	}

	private int[] shiftLeftAndAppend(int[] pattern, int data) {
		int[] newPattern = new int[4];
		newPattern[0] = pattern[1];
		newPattern[1] = pattern[2];
		newPattern[2] = pattern[3];
		newPattern[3] = data;
		return newPattern;
	}
}
