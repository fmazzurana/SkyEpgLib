package beans;

import java.io.Serializable;
import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class EventsSpecialBean extends GenericBean<EventsSpecialBean> implements Serializable {

	private static final long serialVersionUID = 8832821612763800283L;

	// properties
	@JsonProperty("dow")
	private String dow;
	@JsonProperty("date")
	private Timestamp date;
	@JsonProperty("dur")
	private int dur;
	@JsonProperty("number")
	private int number;
	@JsonProperty("name")
	private String name;
	@JsonProperty("title")
	private String title;
	@JsonProperty("movieid")
	private int movieid;
	@JsonProperty("genre")
	private String genre;
	@JsonProperty("subgenre")
	private String subgenre;
	@JsonProperty("fulldescr")
	private String fulldescr;
	@JsonProperty("special")
	private String special;
	@JsonProperty("matching")
	private String matching;
	@JsonProperty("ctrl")
	private String ctrl;
	
	// getters & setters
	public String getDow() {
		return dow;
	}
	public void setDow(String dow) {
		this.dow = dow;
	}

	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public int getDur() {
		return dur;
	}
	public void setDur(int dur) {
		this.dur = dur;
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
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getMovieid() {
		return movieid;
	}
	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getSubgenre() {
		return subgenre;
	}
	public void setSubgenre(String subgenre) {
		this.subgenre = subgenre;
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
}
