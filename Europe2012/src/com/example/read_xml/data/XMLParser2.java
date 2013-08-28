package com.example.read_xml.data;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.europe2012.R;
import com.example.read_xml.entity.Away;
import com.example.read_xml.entity.Europe2012;
import com.example.read_xml.entity.Game;
import com.example.read_xml.entity.Group;
import com.example.read_xml.entity.Home;
import com.example.read_xml.entity.Player;
import com.example.read_xml.entity.Team;

public class XMLParser2 {
	private static XMLParser2 instance=null;
	private static XmlResourceParser parser=null;
	private Europe2012 europe2012=null;
	
	private XMLParser2(Context context)
	{
		parser=context.getResources().getXml(R.xml.europe_cup);
	}
	
	public static XMLParser2 getInstance(Context context)
	{
		if (instance==null) {
			instance=new XMLParser2(context);
		}
		return instance;
	}
	
	public Europe2012 getEurope2012()
	{
		if (europe2012==null) {

			try {
				String name=null;
				boolean isHome=false;
				while (parser.getEventType()!=XmlResourceParser.END_DOCUMENT) {
					
					if (parser.getEventType()==XmlResourceParser.START_TAG) {
						name=parser.getName();
						
						if (name.equals("europe_cup")) {
							
							europe2012=new Europe2012();
							ArrayList<Group>groups=new ArrayList<Group>();
							europe2012.setGroups(groups);
							
						}
						else if (name.equals("group")) {
							Group group =new Group();
							ArrayList<Team>teams=new ArrayList<Team>();
							ArrayList<Game>games=new ArrayList<Game>();
							group.setName(parser.getAttributeValue(null, "name"));
							group.setGames(games);
							group.setTeams(teams);
							
							europe2012.getGroups().add(group);
						}
						else if (name.equals("team")) {
							Team team=new Team();
							team.setA(parser.getAttributeValue(null, "A"));
							team.setD(parser.getAttributeValue(null, "D"));
							team.setF(parser.getAttributeValue(null, "F"));
							team.setL(parser.getAttributeValue(null, "L"));
							team.setName(parser.getAttributeValue(null, "name"));
							team.setPts(parser.getAttributeValue(null, "Pts"));
							team.setW(parser.getAttributeValue(null, "W"));
							
							
							europe2012.getLastGroup().getTeams().add(team);
						}
						else if (name.equals("game")) {
							Game game=new Game();
							
							Home home=new Home();
							Away away = new Away();
							
							game.setAway_name(parser.getAttributeValue(null, "away"));
							game.setHome_name(parser.getAttributeValue(null, "home"));
							game.setDate(parser.getAttributeValue(null, "date"));
							game.setResult(parser.getAttributeValue(null, "result"));
							game.setAway(away);
							game.setHome(home);
							
							
							europe2012.getLastGroup().getGames().add(game);
						}
						
						else if (name.equals("home")) {
							ArrayList<Player>players=new ArrayList<Player>();
							europe2012.getLastGroup().getLastGame().getHome().setPlayers(players);
							isHome=true;
						}
						else if (name.equals("away")) {
							ArrayList<Player>players=new ArrayList<Player>();
							europe2012.getLastGroup().getLastGame().getAway().setPlayers(players);
							isHome=false;
						}
						
						else if (name.equals("player")) {
							Player player=new Player();
							player.setEvent(parser.getAttributeValue(null, "event"));
							player.setName(parser.getAttributeValue(null, "name"));
							player.setTime(parser.getAttributeValue(null, "time"));
							
							if (isHome) {
								europe2012.getLastGroup().getLastGame().getHome().getPlayers().add(player);
							}
							else {
								europe2012.getLastGroup().getLastGame().getAway().getPlayers().add(player);
							}
						}
					}
					
					parser.next();
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return europe2012;
	}
}
