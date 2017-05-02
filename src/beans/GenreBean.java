package beans;


import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class GenreBean extends GenericBean<GenreBean> implements Serializable {
	
	private static final long serialVersionUID = -6873487089136881359L;

	// properties
	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("enabled")
	private int enabled;
	@JsonProperty("json")
	private String json;

	// getters & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
}
