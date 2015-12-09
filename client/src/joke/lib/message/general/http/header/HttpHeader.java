package joke.lib.message.general.http.header;

public class HttpHeader {

	private String name;
	private Object value;

	public static HttpHeader of(String name, Object value) {
		HttpHeader header = new HttpHeader();
		header.setName(name);
		header.setValue(value);
		return header;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getStringValue() {
		return value.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
