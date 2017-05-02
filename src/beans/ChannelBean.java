package beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class ChannelBean extends GenericBean<ChannelBean> implements Serializable {

	private static final long serialVersionUID = -8555681414646707424L;

	// properties
	@JsonProperty("id")
	private int id;
	@JsonProperty("number")
	private int number;
	@JsonProperty("name")
	private String name;
	@JsonProperty("service")
	private int service;
	@JsonProperty("genreId")
	private int genreId;

	// getters & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public int getService() {
		return service;
	}
	public void setService(int service) {
		this.service = service;
	}
	
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
}
