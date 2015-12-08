package joke.message.response;

public class DefaultResponse implements Response {
	private String responseString;

	public DefaultResponse(String responseString) {
		this.responseString = responseString;
	}

	public String getResponseString() {
		return responseString;
	}
}
