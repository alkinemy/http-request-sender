package joke.lib.message.response.parser;

import joke.lib.message.general.http.header.HttpHeader;
import joke.lib.message.general.http.header.HttpHeaders;
import joke.lib.message.response.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class HttpResponseParser implements ResponseParser<HttpResponse> {
	@Override public HttpResponse parse(String responseString) {
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
				System.out.println(header);
				String[] headerComponents = header.split(HttpHeaders.SEPARATOR);
				builder.addHeader(HttpHeader.of(headerComponents[0], headerComponents[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		//payload
		//TODO header의 content type을 보고 인코딩에 맞게 처리
		builder.payload(response[1]);

		return builder.build();
	}
}
