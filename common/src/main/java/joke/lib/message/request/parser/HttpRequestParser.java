package joke.lib.message.request.parser;

import joke.lib.message.common.http.header.HttpHeader;
import joke.lib.message.common.http.header.HttpHeaders;
import joke.lib.message.request.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class HttpRequestParser implements RequestParser<HttpRequest> {
	@Override public HttpRequest parse(String requestString) {
		String[] request = requestString.split("\r\n\r", 2);
		HttpRequest.Builder builder = HttpRequest.builder();

		try (BufferedReader reader = new BufferedReader(new StringReader(request[0]))) {
			//start line
			String startLine = reader.readLine();
			String[] startLinePart = startLine.split(" ", 3);
			builder.method(startLinePart[0])
				.target(startLinePart[1])
				.version(startLinePart[2]);

			//header
			String header;
			while((header = reader.readLine()) != null) {
				String[] headerComponents = header.split(HttpHeaders.SEPARATOR);
				System.out.println(headerComponents[0]);
				System.out.println(headerComponents[1]);
				builder.addHeader(HttpHeader.of(headerComponents[0], headerComponents[1]));

				if ("Host".equals(headerComponents[0])) {
					builder.baseUrl(headerComponents[1].split(":")[0]);
				}
			}

			//payload
			builder.payload(request[1]);

			return builder.build();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
