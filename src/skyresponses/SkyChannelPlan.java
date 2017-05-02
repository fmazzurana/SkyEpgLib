package skyresponses;

import java.util.List;

public class SkyChannelPlan {

	private String channel;
	private boolean banned;
	private List<SkyEvent> plan;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	public List<SkyEvent> getPlan() {
		return plan;
	}
	public void setPlan(List<SkyEvent> plan) {
		this.plan = plan;
	}
}
