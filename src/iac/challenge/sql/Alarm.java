package iac.challenge.sql;

public class Alarm {

	private Integer id;
	private Integer hour;
	private Integer min;
	private Integer active;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Boolean getActive() {
		return (active == 1);
	}
	public void setActive(Integer active) {
		this.active = active;
	}
}
