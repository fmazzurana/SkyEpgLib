package database;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import beans.ChannelBean;
import beans.EventsSpecialBean;
import beans.GenreBean;
import beans.MovieBean;
import beans.ScheduleBean;
import beans.StringBean;
import skyresponses.SkyChannel;
import skyresponses.SkyEvent;


public class EpgDatabase extends DbMySql {

	// --------------------------------------------------------------------------------------------
	// Types
	// --------------------------------------------------------------------------------------------
	public static enum GenresEnabled  { NO /* 0 */, YES /* 1 */, ALL /* 2 */ };
	
	// --------------------------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * 
	 * @param dbname
	 * @throws DbException
	 */
	public EpgDatabase() throws DbException {
		super("epg");
	}

	public EpgDatabase(String url, int port, String usr, String pwd, String extras) throws DbException {
		super("epg", url, port, usr, pwd, extras);
	}

	// --------------------------------------------------------------------------------------------
	// Publics
	// --------------------------------------------------------------------------------------------
	public void archiver() throws DbException {
		super.callProcedure("ut_archiver");
	}
	
	public void fixer() throws DbException {
		super.callProcedure("ut_fixer");
	}
	
	/*
	 * 
	p_getChannelEventsNum
		SELECT ch.number, ch.`name`, (SELECT COUNT(*) FROM events ev WHERE ev.chnId = ch.id) AS evNum
		FROM channels ch
		ORDER BY ch.number;
	 * 
	 */

	// --------------------------------------------------------------------------------------------
	// Methods on CHANNELS
	// --------------------------------------------------------------------------------------------
	public List<ChannelBean> channelsList(int genreId) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(genreId);
		return super.callProcedure("p_channelsList", params, ChannelBean.class);
	}
	
	public void channelsInsertUpdate(int idGenre, SkyChannel chn, byte[] logo) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(idGenre);
		params.add(chn.getId());
		params.add(chn.getName());
		params.add(chn.getNumber());
		params.add(chn.getService());
		params.add(logo);
		super.callProcedure("p_channelsInsertUpdate", params);
	}

	public void channelsUpdateJson(int chnId, int day, String json) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(chnId);
		params.add(day);
		params.add(json);
		super.callProcedure("p_channelsUpdateJson", params);
	}

	// --------------------------------------------------------------------------------------------
	// Methods on CONTROLS
	// --------------------------------------------------------------------------------------------
	public List<String> controlsList() throws DbException {
		return super.callProcedure("p_controlsList", String.class);
	}

	// --------------------------------------------------------------------------------------------
	// Methods on EVENTS
	// --------------------------------------------------------------------------------------------
	public List<EventsSpecialBean> eventsListSpecials() throws DbException {
		return super.callProcedure("p_eventsListSpecials", EventsSpecialBean.class);
	}

	public List<MovieBean> eventsListMovies(String genre) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(genre);
		List<MovieBean> movies = super.callProcedure("p_eventsListMovies", params, MovieBean.class);
		for (MovieBean movie : movies) {
			List<ScheduleBean> schedules = schedulesListMovie(movie.getId());
			movie.setSchedules(schedules);
		}
		return movies;
	}

	public void eventsInsert(SkyEvent event, int chnId, LocalDateTime day, String fullDescr) throws DbException {
		// date
		DbParamsList params = new DbParamsList();
		params.add(event.getId());
		params.add(event.getPid());
		params.add(chnId);
		params.add(Timestamp.valueOf(day));
		params.add(event.getDur());
		params.add(event.getTitle());
		params.add(event.getGenre());
		params.add(event.getSubgenre());
		params.add(fullDescr);
		super.callProcedure("p_eventsInsert", params);
	}

	// --------------------------------------------------------------------------------------------
	// Methods on GENRES
	// --------------------------------------------------------------------------------------------
	/**
	 * Retrieves a genres list from the DB
	 * 
	 * @param enabled Indicates the genre status
	 * @param id Genre id to be searched (0 = all)
	 * @return A list of <b>Genre</b>. The list may be null if no genres are found.
	 * @throws DBException
	 */
	public List<GenreBean> genresList(GenresEnabled enabled, int id) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(enabled.ordinal());
		params.add(id);
		return super.callProcedure("p_genresList", params, GenreBean.class);
	}
	
	public void genresUpdateJson(int id, String json) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(id);
		params.add(json);
		super.callProcedure("p_genresUpdateJson", params);
	}
	
	// --------------------------------------------------------------------------------------------
	// Methods on LOG
	// --------------------------------------------------------------------------------------------
	public void logAdd(String process, List<String> lines) throws DbException {
		for (String line : lines)
			logAdd(process, line);
	}
	public void logAdd(String process, String line) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(process);
		params.add(line);
		super.callProcedure("p_logAdd", params);
	}

	// --------------------------------------------------------------------------------------------
	// Methods on MOVIES
	// --------------------------------------------------------------------------------------------
	public List<MovieBean> moviesList(String title, String genre, String fulldescr, String special, String ctrl) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(title);
		params.add(genre);
		params.add(fulldescr);
		params.add(special);
		params.add(ctrl);
		return super.callProcedure("p_moviesList", params, MovieBean.class);
	}

	public List<StringBean> moviesListGenres() throws DbException {
		return super.callProcedure("p_moviesListGenres", StringBean.class);
	}

	public void moviesUpdateCtrl(int movieId, String ctrl) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(movieId);
		params.add(ctrl);
		super.callProcedure("p_moviesUpdateCtrl", params);
	}

	// --------------------------------------------------------------------------------------------
	// Methods on PARAMS
	// --------------------------------------------------------------------------------------------

	// --------------------------------------------------------------------------------------------
	// Methods on SCHEDULES
	// --------------------------------------------------------------------------------------------
	public List<ScheduleBean> schedulesListMovie(int movieId) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(movieId);
		return super.callProcedure("p_schedulesListMovie", params, ScheduleBean.class);
	}

	// --------------------------------------------------------------------------------------------
	// Methods on SKIPCHANNELS
	// --------------------------------------------------------------------------------------------
	public Boolean skipchannelsCheck(int number, String name) throws DbException {
		DbParamsList params = new DbParamsList();
		params.add(number);
		params.add(name);
		return super.execFunctionRetInt("f_skipchannelsCheck", params) > 0;
	}

	// --------------------------------------------------------------------------------------------
	// Methods on SPECIALS
	// --------------------------------------------------------------------------------------------
	public List<String> specialsListAttributes() throws DbException {
		return super.callProcedure("p_specialsListAttributes", null, String.class);
	}
}
