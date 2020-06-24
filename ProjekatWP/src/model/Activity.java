package model;

import java.time.LocalDateTime;

public class Activity {
	
	private LocalDateTime from;
	private LocalDateTime to;
	
	public Activity() {
		super();
	}

	public Activity(LocalDateTime from, LocalDateTime to) {
		super();
		this.from = from;
		this.to = to;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Activity [from=" + from + ", to=" + to + "]";
	}
	
	
}
