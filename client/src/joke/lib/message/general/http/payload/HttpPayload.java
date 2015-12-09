package joke.lib.message.general.http.payload;

import joke.lib.message.request.http.HttpRequestComponent;
import joke.lib.message.response.http.HttpResponseComponent;

public class HttpPayload implements HttpRequestComponent, HttpResponseComponent {
	private String content;

	public HttpPayload() {
	}

	public HttpPayload(String content) {
		this.content = content;
	}

	@Override public String buildComponent() {
		//TODO 구현
		return content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}
}
