package beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class StringBean implements Serializable {
    
	private static final long serialVersionUID = 122144023225641279L;

	// properties
    @JsonProperty("value")
    private String value;

    // getters & setters
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
