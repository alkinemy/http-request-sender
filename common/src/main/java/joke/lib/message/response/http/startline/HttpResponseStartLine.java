package joke.lib.message.response.http.startline;

import joke.lib.message.common.http.startline.HttpVersion;
import joke.lib.message.response.http.HttpResponseComponent;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class HttpResponseStartLine implements HttpResponseComponent {
	private HttpVersion version;
	private HttpStatus status;

	@Override public String buildComponent() {
		Objects.requireNonNull(version, "Version should not be null");
		Objects.requireNonNull(status, "Status should not be null");

		StringBuilder response = new StringBuilder();
		response.append(version.getVersion()).append(" ")
			.append(status.getStatusCode()).append(" ")
			.append(status.getStatusMessage());
		return response.toString();
	}
}
