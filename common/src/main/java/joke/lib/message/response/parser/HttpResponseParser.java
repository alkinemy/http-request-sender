package joke.lib.message.response.parser;

import joke.lib.message.common.http.header.HttpHeader;
import joke.lib.message.common.http.header.HttpHeaders;
import joke.lib.message.response.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class HttpResponseParser implements ResponseParser<HttpResponse> {
	@Override public HttpResponse parse(String responseString) {
		System.out.println("Original response:\n---------------------------------------------\n"
			+ responseString + "\n---------------------------------------------\n");

		String[] response = responseString.split("\r\n\r\n", 2);
		HttpResponse.Builder builder = HttpResponse.builder();

		try (BufferedReader reader = new BufferedReader(new StringReader(response[0]))) {
			//start line
			String startLine = reader.readLine();
			String[] startLinePart = startLine.split(" ", 3);
			builder.version(startLinePart[0])
				.statusCode(startLinePart[1]);

			//header
			String header;
			while((header = reader.readLine()) != null) {
				String[] headerComponents = header.split(HttpHeaders.SEPARATOR);
				builder.addHeader(HttpHeader.of(headerComponents[0], headerComponents[1]));
			}

			//payload
			//TODO header의 content type을 보고 인코딩에 맞게 처리
			builder.payload(response[1]);

			return builder.build();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
