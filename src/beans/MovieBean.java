package beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class MovieBean extends GenericBean<MovieBean> implements Serializable {

	private static final long serialVersionUID = -5498181428427978863L;

	// properties
	@JsonProperty("id")
	private int id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("dur")
	private int dur;
	@JsonProperty("genre")
	private String genre;
	@JsonProperty("fulldescr")
	private String fulldescr;
	@JsonProperty("special")
	private String special;
	@JsonProperty("matching")
	private String matching;
	@JsonProperty("ctrl")
	private String ctrl;
	@JsonProperty("schedules")
	private SchedulesBean schedules;

	// getters & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getDur() {
		return dur;
	}
	public void setDur(int dur) {
		this.dur = dur;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getFulldescr() {
		return fulldescr;
	}
	public void setFulldescr(String fulldescr) {
		this.fulldescr = fulldescr;
	}
	
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	
	public String getMatching() {
		return matching;
	}
	public void setMatching(String matching) {
		this.matching = matching;
	}

	public String getCtrl() {
		return ctrl;
	}
	public void setCtrl(String ctrl) {
		this.ctrl = ctrl;
	}

	public SchedulesBean getSchedules() {
		return schedules;
	}
	public void setSchedules(SchedulesBean schedules) {
		this.schedules = schedules;
	}
}
