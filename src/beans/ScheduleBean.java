package beans;

import java.io.Serializable;
import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonProperty;

public class ScheduleBean implements Serializable {

	private static final long serialVersionUID = -8513979814974163514L;
	
	// properties
	@JsonProperty("date")
	private Timestamp date;
	@JsonProperty("number")
	private int number;
	@JsonProperty("name")
	private String name;
	
	// getters & setters
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
