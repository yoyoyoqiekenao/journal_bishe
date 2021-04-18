package com.example.jorunal_bishe.eventbus;


/**
 * Created by M02323 on 2017/9/28.
 */

public class UpdateEvent extends Event {
	public String from;
	public String event;

	public UpdateEvent(String event, String from, Object source) {
		super(source);
		this.event = event;
		this.from = from;
	}
}
