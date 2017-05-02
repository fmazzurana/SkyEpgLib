package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class ListOfValue extends GenericBean<ListOfValue> implements Serializable {

	private static final long serialVersionUID = -3005926713435278212L;
	
	// properties
	@JsonProperty("values")
	private List<String> values;

	public ListOfValue() {
		values = new ArrayList<String>();
	}
	
	// getters & setters
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}

	// publics
	public void add(String value) {
		values.add(value);
	}
}
