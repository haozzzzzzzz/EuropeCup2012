package com.example.read_xml.entity;

import java.util.ArrayList;

public class Europe2012 {

	
	private ArrayList<Group> groups=null;

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}
	public Group getLastGroup()
	{
		if (groups.size()==0) {
			return null;
		}
		return groups.get(groups.size()-1);
	}
}
