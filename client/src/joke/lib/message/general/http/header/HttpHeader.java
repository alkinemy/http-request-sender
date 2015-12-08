package joke.lib.message.general.http.header;

public class HttpHeader {

	private String name;
	private String value;

	public static HttpHeader of(String name, String value) {
		HttpHeader header = new HttpHeader();
		header.setName(name);
		header.setValue(value);
		return header;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
