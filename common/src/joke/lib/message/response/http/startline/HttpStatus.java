package joke.lib.message.response.http.startline;

public enum HttpStatus {
	OK("200", "OK"),
	REDIRECT("302", "302 Found"),
	NOT_FOUND("404", "Not Found"),
	INTERNAL("500", "Internal Server Error");

	private String statusCode;
	private String statusMessage;

	HttpStatus(String statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public static HttpStatus convertToStatus(String statusCode) {
		for(HttpStatus httpStatus : HttpStatus.values()) {
			if (httpStatus.getStatusCode().equals(statusCode)) {
				return httpStatus;
			}
		}
		return null;
	}
}
