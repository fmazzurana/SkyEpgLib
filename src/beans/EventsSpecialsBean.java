package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class EventsSpecialsBean extends GenericBean<EventsSpecialsBean> implements Serializable {

	private static final long serialVersionUID = -7895909099114900521L;

	// properties
	@JsonProperty("specials")
	private List<EventsSpecialBean> specials;

	public EventsSpecialsBean() {
		specials = new ArrayList<EventsSpecialBean>();
	}
	
	// getters & setters
	public List<EventsSpecialBean> getSpecials() {
		return specials;
	}
	public void setSpecials(List<EventsSpecialBean> specials) {
		this.specials = specials;
	}

	// publics
	public void add(EventsSpecialBean special) {
		specials.add(special);
	}
	
	public boolean isEmpty() {
		return specials.size() == 0;
	}
}
