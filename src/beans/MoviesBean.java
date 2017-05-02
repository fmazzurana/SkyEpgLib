package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class MoviesBean extends GenericBean<MoviesBean> implements Serializable {

	private static final long serialVersionUID = -681011049278506037L;

	// properties
	@JsonProperty("total")
	private int total;
	@JsonProperty("movies")
	private List<MovieBean> movies;

	public MoviesBean() {
		total = 0;
		movies = new ArrayList<MovieBean>();
	}
	
	// getters & setters
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<MovieBean> getMovies() {
		return movies;
	}
	public void setMovies(List<MovieBean> movies) {
		this.movies = movies;
	}

	// publics
	public void add(MovieBean movie) {
		movies.add(movie);
		total += 1;
	}
}
