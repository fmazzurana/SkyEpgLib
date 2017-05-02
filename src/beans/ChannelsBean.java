package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import database.GenericBean;

public class ChannelsBean extends GenericBean<ChannelsBean> implements Serializable {

	private static final long serialVersionUID = 4599560012872098101L;
	
	// properties
	@JsonProperty("channels")
	private List<ChannelBean> channels;

	public ChannelsBean() {
		channels = new ArrayList<ChannelBean>();
	}
	
	// getters & setters
	public List<ChannelBean> getChannels() {
		return channels;
	}
	public void setChannels(List<ChannelBean> channels) {
		this.channels = channels;
	}

	// publics
	public void add(ChannelBean channel) {
		channels.add(channel);
	}
	
	public boolean isEmpty() {
		return channels.size() == 0;
	}
}
