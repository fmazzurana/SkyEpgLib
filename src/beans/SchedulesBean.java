package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class SchedulesBean extends GenericBean<SchedulesBean> implements Serializable {

	private static final long serialVersionUID = 8056756088624742325L;
	
	// properties
	@JsonProperty("schedules")
	private List<ScheduleBean> schedules;

	public SchedulesBean() {
		schedules = new ArrayList<ScheduleBean>();
	}
	
	// getters & setters
	public List<ScheduleBean> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<ScheduleBean> schedules) {
		this.schedules = schedules;
	}

	// publics
	public void add(ScheduleBean schedule) {
		schedules.add(schedule);
	}
}
