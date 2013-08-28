package com.example.read_xml.entity;

import android.R.string;

public class Game {
	private String away_name=null;
	private String date=null;
	private String home_name=null;
	private String result=null;
	
	private Home home=null;
	private Away away=null;
	
	public String getAway_name() {
		return away_name;
	}
	public void setAway_name(String away_name) {
		this.away_name = away_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHome_name() {
		return home_name;
	}
	public void setHome_name(String home_name) {
		this.home_name = home_name;
	}

	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Away getAway() {
		return away;
	}
	public void setAway(Away away) {
		this.away = away;
	}
}
