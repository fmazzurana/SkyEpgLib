package skyresponses;

public class SkyEvent {

	private int id;
	private int pid;
	private String starttime;	// format hh:mm
	private int dur;
	private String title;
	private String normalizedtitle;
	private String desc;
	private String genre;
	private String subgenre;
	private boolean prima;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public int getDur() {
		return dur;
	}
	public void setDur(int dur) {
		this.dur = dur;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNormalizedtitle() {
		return normalizedtitle;
	}
	public void setNormalizedtitle(String normalizedtitle) {
		this.normalizedtitle = normalizedtitle;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public boolean isPrima() {
		return prima;
	}
	public void setPrima(boolean prima) {
		this.prima = prima;
	}
}
