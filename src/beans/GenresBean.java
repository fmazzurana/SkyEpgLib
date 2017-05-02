package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;
//
public class GenresBean extends GenericBean<GenresBean> implements Serializable {
	
	private static final long serialVersionUID = -7727665302997192353L;

	// properties
	@JsonProperty("genres")
	private List<GenreBean> genres;

	public GenresBean() {
		genres = new ArrayList<GenreBean>();
	}
	
	// getters & setters
	public List<GenreBean> getGenres() {
		return genres;
	}
	public void setGenres(List<GenreBean> genres) {
		this.genres = genres;
	}

	// publics
	public void add(GenreBean genre) {
		genres.add(genre);
	}
	
	public boolean isEmpty() {
		return genres.size() == 0;
	}
}
