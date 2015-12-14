package joke.lib.message.common.http.payload;

import joke.lib.message.request.http.HttpRequestComponent;
import joke.lib.message.response.http.HttpResponseComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HttpPayload implements HttpRequestComponent, HttpResponseComponent {
	private String content;

	public HttpPayload(String content) {
		this.content = content;
	}

	@Override public String buildComponent() {
		return content;
	}
}
