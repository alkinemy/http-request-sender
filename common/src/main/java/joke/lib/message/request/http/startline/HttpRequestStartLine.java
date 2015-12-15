package joke.lib.message.request.http.startline;

import joke.lib.message.common.http.startline.HttpVersion;
import joke.lib.message.request.http.HttpRequestComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpRequestStartLine implements HttpRequestComponent {
	private HttpMethod method;
	private String target;
	private HttpVersion version;

	@Override public String buildComponent() {
		StringBuilder message = new StringBuilder();
		message.append(method.name()).append(" ")
			.append(target).append(" ")
			.append(version.getVersion());
		return message.toString();
	}
}
