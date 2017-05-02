package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

import beans.ChannelBean;
import beans.ChannelsBean;
import beans.EventsSpecialBean;
import beans.EventsSpecialsBean;
import beans.GenreBean;
import beans.GenresBean;
import beans.ListOfValue;
import beans.MovieBean;
import beans.MoviesBean;
import beans.ParamBean;
import beans.ScheduleBean;
import beans.SchedulesBean;
import commons.EpgException;
import skyresponses.SkyChannel;
import skyresponses.SkyEvent;


public class EpgDatabase extends Database {

	// --------------------------------------------------------------------------------------------
	// Types
	// --------------------------------------------------------------------------------------------
	public enum GenresEnabled  { NO /* 0 */, YES /* 1 */, ALL /* 2 */ };
	
	// --------------------------------------------------------------------------------------------
	// Constants
	// --------------------------------------------------------------------------------------------
	private static final String typeString  = "String";
	private static final String typeInteger = "Integer";
	private static final String typeDouble  = "Double";
	private static final String typeBoolean = "Boolean";
	private static final String invalidValue = "The parameter is not a valid %s value: %s.";

	// --------------------------------------------------------------------------------------------
	// Variables
	// --------------------------------------------------------------------------------------------
	private Connection dbConn = null;
	
	// --------------------------------------------------------------------------------------------
	// Construct
	// --------------------------------------------------------------------------------------------
	/**
	 * Establishes a database connection
	 * 
	 * @throws EpgException
	 */
	public EpgDatabase() throws EpgException {
		super();
		try {
			// TODO: get params from config file ???
			dbConn = super.Connect("192.168.178.3", "epg", 3306, "giant", "Eir3annach");
			//dbConn = db.Connect("fmazzurana.noip.me", "epg", 3306, "giant", "Eir3annach");
		} catch (DBException e) {
			throw new EpgException(e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	// Publics
	// --------------------------------------------------------------------------------------------
	/**
	 * Closes the DB connection
	 * 
	 * @throws EpgException
	 */
	public void Close() throws EpgException {
		if (dbConn != null) {
			try {
				super.Disconnect(dbConn);
			} catch (DBException e) {
				throw new EpgException(e.getMessage());
			}
		}
	}
	
	public void archiver() throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call ut_archiver() }");
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}
	
	public void fixer() throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call ut_fixer() }");
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
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
	public ChannelsBean channelsList(int genreId) throws EpgException {
		ChannelsBean channels = new ChannelsBean();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_channelsList(?) }");
			stmt.setInt(1, genreId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				channels.add(ChannelBean.fromResultSet(rs, ChannelBean.class));
			return channels;
		} catch (SQLException | DBException e) {
			throw new EpgException(e.getMessage());
		}
	}
	
	public void channelsInsertUpdate(int idGenre, SkyChannel chn, byte[] logo) throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_channelsInsertUpdate(?,?,?,?,?,?) }");
			stmt.setInt(1, idGenre);
			stmt.setInt(2, chn.getId());
			stmt.setString(3, chn.getName());
			stmt.setInt(4, chn.getNumber());
			stmt.setInt(5, chn.getService());
			stmt.setBytes(6, logo);
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}

	public void channelsUpdateJson(int chnId, int day, String json) throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_channelsUpdateJson(?,?,?) }");
			stmt.setInt(1, chnId);
			stmt.setInt(2, day);
			stmt.setString(3, json);
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	// Methods on CONTROLS
	// --------------------------------------------------------------------------------------------
	public ListOfValue controlsList() throws EpgException {
		ListOfValue controls = new ListOfValue();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_controlsList() }");
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				controls.add(rs.getString(1));
			return controls;
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	// Methods on EVENTS
	// --------------------------------------------------------------------------------------------
	public EventsSpecialsBean eventsListSpecials() throws EpgException {
		EventsSpecialsBean specials = new EventsSpecialsBean();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_eventsListSpecials() }");
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				specials.add(EventsSpecialBean.fromResultSet(rs, EventsSpecialBean.class));
			return specials;
		} catch (SQLException | DBException e) {
			throw new EpgException(e.getMessage());
		}
	}

	public MoviesBean eventsListMovies(String genre) throws EpgException {
		MoviesBean movies = new MoviesBean();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_eventsListMovies(?) }");
			stmt.setString(1, genre);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				MovieBean movie = MovieBean.fromResultSet(rs, MovieBean.class);
				SchedulesBean schedules = schedulesListMovie(movie.getId());
				movie.setSchedules(schedules);
				movies.add(movie);
			}
			return movies;
		} catch (SQLException | DBException e) {
			throw new EpgException(e.getMessage());
		}
	}

	public void eventsInsert(SkyEvent event, int chnId, LocalDateTime day, String fullDescr) throws EpgException {
		// date
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_eventsInsert(?,?,?,?,?,?,?,?,?) }");
			stmt.setInt(1, event.getId());
			stmt.setInt(2, event.getPid());
			stmt.setInt(3, chnId);
			stmt.setTimestamp(4, Timestamp.valueOf(day));
			stmt.setInt(5, event.getDur());
			stmt.setString(6, event.getTitle());
			stmt.setString(7, event.getGenre());
			stmt.setString(8, event.getSubgenre());
			stmt.setString(9, fullDescr);
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
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
	 * @throws EpgException
	 */
	public GenresBean genresList(GenresEnabled enabled, int id) throws EpgException {
		GenresBean genres = new GenresBean();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_genresList(?,?) }");
			stmt.setInt(1, enabled.ordinal());
			stmt.setInt(2, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				genres.add(GenreBean.fromResultSet(rs, GenreBean.class));
			return genres;
		} catch (SQLException | DBException e) {
			throw new EpgException(e.getMessage());
		}
	}
	
	public void genresUpdateJson(int id, String json) throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_genresUpdateJson(?,?) }");
			stmt.setInt(1, id);
			stmt.setString(2, json);
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	// Methods on LOG
	// --------------------------------------------------------------------------------------------
	public void logAdd(String process, List<String> lines) throws EpgException {
		for (String line : lines)
			logAdd(process, line);
	}
	public void logAdd(String process, String line) throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_logAdd(?,?) }");
			stmt.setString(1, process);
			stmt.setString(2, line);
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	// Methods on MOVIES
	// --------------------------------------------------------------------------------------------
	public MoviesBean moviesList(String title, String genre, String fulldescr, String special, String ctrl) throws EpgException {
		MoviesBean movies = new MoviesBean();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_moviesList(?,?,?,?,?) }");
			stmt.setString(1, title);
			stmt.setString(2, genre);
			stmt.setString(3, fulldescr);
			stmt.setString(4, special);
			stmt.setString(5, ctrl);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				movies.add(MovieBean.fromResultSet(rs, MovieBean.class));
			return movies;
		} catch (SQLException | DBException e) {
			throw new EpgException(e.getMessage());
		}
	}

	public ListOfValue moviesListGenres() throws EpgException {
		ListOfValue genres = new ListOfValue();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_moviesListGenres() }");
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				genres.add(rs.getString(1));
			return genres;
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}

	public void moviesUpdateCtrl(int movieId, String ctrl) throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_moviesUpdateCtrl(?,?) }");
			stmt.setInt(1, movieId);
			stmt.setString(2, ctrl);
			stmt.executeQuery();
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	// Methods on PARAMS
	// --------------------------------------------------------------------------------------------
	private String paramRead(String name, String type) throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_paramRead(?) }");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ParamBean param = ParamBean.fromResultSet(rs, ParamBean.class);
				if (param.getType().equalsIgnoreCase(type))
					return param.getValue();
				else
					throw new EpgException(String.format("The parameter is not of type %s.", type));
			} else
				throw new EpgException(String.format("Parameter not found: %s", name));
		} catch (SQLException | DBException e) {
			throw new EpgException(e.getMessage());
		}
	}
	
	/**
	 * These methods get a parameter from the DB
	 *  
	 * @param name Name of the parameter
	 * @return Parameter's value
	 * @throws EpgException
	 */
	public String getParamAsString(String name) throws EpgException {
		return paramRead(name, typeString);
	}
	public int getParamAsInteger(String name) throws EpgException {
		String value = paramRead(name, typeInteger);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new EpgException(String.format(invalidValue, typeInteger, value));
		}
	}
	public double getParamAsDouble(String name) throws EpgException {
		String value = paramRead(name, typeDouble);
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new EpgException(String.format(invalidValue, typeDouble, value));
		}
	}
	public Boolean getParamAsBoolean(String name) throws EpgException {
		String value = paramRead(name, typeBoolean);
		return Boolean.parseBoolean(value);
	}

	// --------------------------------------------------------------------------------------------
	// Methods on SCHEDULES
	// --------------------------------------------------------------------------------------------
	public SchedulesBean schedulesListMovie(int movieId) throws EpgException {
		SchedulesBean schedules = new SchedulesBean();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_schedulesListMovie(?) }");
			stmt.setInt(1, movieId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				schedules.add(ScheduleBean.fromResultSet(rs, ScheduleBean.class));
			return schedules;
		} catch (SQLException | DBException e) {
			throw new EpgException(e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	// Methods on SKIPCHANNELS
	// --------------------------------------------------------------------------------------------
	public Boolean skipchannelsCheck(int number, String name) throws EpgException {
		try {
			CallableStatement stmt = dbConn.prepareCall("{? = call f_skipchannelsCheck(?,?) }");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, number);
			stmt.setString(3, name);
			stmt.execute();
			return stmt.getInt(1) > 0;
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------
	// Methods on SPECIALS
	// --------------------------------------------------------------------------------------------
	public ListOfValue specialsListAttributes() throws EpgException {
		ListOfValue specialLabels = new ListOfValue();
		try {
			CallableStatement stmt = dbConn.prepareCall("{ call p_specialsListAttributes() }");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
				specialLabels.add(rs.getString(1));
			return specialLabels;
		} catch (SQLException e) {
			throw new EpgException(e.getMessage());
		}
	}
}
