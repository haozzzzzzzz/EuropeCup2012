package com.example.europe2012;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.read_xml.entity.Game;
import com.example.read_xml.entity.GameListViewHolder;
import com.example.read_xml.entity.Group;
import com.example.read_xml.entity.GroupHolder;




public class EuropeCupExpandableListViewAdapter extends
		BaseExpandableListAdapter {

	private Context context = null;
	private Group group = null;

	private LayoutInflater layoutInflater=null;
	
	public EuropeCupExpandableListViewAdapter(Context context, Group group) {
		this.context = context;
		this.group = group;
		
		layoutInflater=LayoutInflater.from(context);
		
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		String teamName = group.getTeams().get(arg1).getName();
		return group.findGamesByTeamName(teamName).get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return arg0 * 100 + arg1;
	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		String teamName = group.getTeams().get(arg0).getName();
		return group.findGamesByTeamName(teamName).size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return group.getTeams().get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group.getTeams().size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		// TODO Auto-generated method stub

		View view=arg3;
		GameListViewHolder gameListViewHolder=null;
		if (view==null) {
			view = layoutInflater.inflate(R.layout.game_list, null);
			
			gameListViewHolder=new GameListViewHolder();
			gameListViewHolder.textView=(TextView) view.findViewById(R.id.game_title);
			gameListViewHolder.homePlayerListView=(ListView) view.findViewById(R.id.game_home_playerlistView);
			gameListViewHolder.awayPlayerListView=(ListView) view.findViewById(R.id.game_away_playerlistView);
			
			view.setTag(gameListViewHolder);
		}
		else {
			gameListViewHolder=(GameListViewHolder) view.getTag();
		}
		
		//获取一场比赛
		String teamName=group.getTeams().get(arg0).getName();
		Game game=group.findGamesByTeamName(teamName).get(arg1);
		
		//设置比赛的结果
		gameListViewHolder.textView.setText(game.getHome_name()+" " +game.getResult()+ " "+game.getAway_name());
		
		
		//设置home比赛的数据
		
		List<HashMap<String, String>>game_homePlayerListViewItemHashMaps=new ArrayList<HashMap<String,String>>();
		
		int playerListItemsNum=game.getHome().getPlayers().size();
		
		for (int i = 0; i < playerListItemsNum; i++) {
			HashMap<String, String>hashMap=new HashMap<String, String>();
			
			hashMap.put("home_player_event", game.getHome().getPlayers().get(i).getEvent());
			hashMap.put("home_player_name", game.getHome().getPlayers().get(i).getName());
			hashMap.put("home_player_time", game.getHome().getPlayers().get(i).getTime());
			
			game_homePlayerListViewItemHashMaps.add(hashMap);
		}
		

		SimpleAdapter homePlayerListViewAdapter=
				new SimpleAdapter
				(
						context,
						game_homePlayerListViewItemHashMaps,
						R.layout.game_item,
						new String[]
								{
									"home_player_event",
									"home_player_name",
									"home_player_time",
								},
						new int[]
								{
									R.id.player_event,
									R.id.player_name,
									R.id.player_time,
								}
				)
		{

			@Override
			public void setViewImage(ImageView v,String value) {
				// TODO Auto-generated method stub
				super.setViewImage(v, value);
				
				if (value==null) {
					return;
				}
				//获取资源
				int rsId=context.getResources().getIdentifier(value.toLowerCase(), "drawable", context.getPackageName());
				v.setBackgroundResource(rsId);
			}
			
		};
		
/*		LayoutParams layoutParams=null;
		layoutParams=(LayoutParams) gameListViewHolder.homePlayerListView.getLayoutParams();
		layoutParams.height=(int) (context.getResources().getDimension(R.dimen.game_player_item_height)*playerListItemsNum);*/
		gameListViewHolder.homePlayerListView.setAdapter(homePlayerListViewAdapter);
	/*	gameListViewHolder.homePlayerListView.setLayoutParams(layoutParams);*/
		
		//away比赛数据列表adapter
		List<HashMap<String, String>>game_awayPlayerListViewItemHashMaps=new ArrayList<HashMap<String,String>>();
		playerListItemsNum=game.getAway().getPlayers().size();
		
		for (int i = 0; i < playerListItemsNum; i++) {
			HashMap<String, String>hashMap=new HashMap<String, String>();
			
			hashMap.put("away_player_event", game.getAway().getPlayers().get(i).getEvent());
			hashMap.put("away_player_name", game.getAway().getPlayers().get(i).getName());
			hashMap.put("away_player_time", game.getAway().getPlayers().get(i).getTime());
			
			game_awayPlayerListViewItemHashMaps.add(hashMap);
		}
		
		SimpleAdapter awayPlayerListViewAdapter=
				new SimpleAdapter
				(
						context,
						game_awayPlayerListViewItemHashMaps,
						R.layout.game_item,
						new String[]
								{
									"away_player_event",
									"away_player_name",
									"away_player_time",
								},
						new int[]
								{
									R.id.player_event,
									R.id.player_name,
									R.id.player_time,
								}
				)
		{

			@Override
			public void setViewImage(ImageView v,String value) {
				// TODO Auto-generated method stub
				super.setViewImage(v, value);
				
				if (value==null) {
					return;
				}
				//获取资源
				int rsId=context.getResources().getIdentifier(value.toLowerCase(), "drawable", context.getPackageName());
				v.setBackgroundResource(rsId);
			}
			
		};
		
		gameListViewHolder.awayPlayerListView.setAdapter(awayPlayerListViewAdapter);
		
		return view;
	}


	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		// TODO Auto-generated method stub
		View view = arg2;
		GroupHolder groupHolder = null;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.team_item, null);

			groupHolder = new GroupHolder();
			groupHolder.team_A = (TextView) view.findViewById(R.id.team_A);
			groupHolder.team_D = (TextView) view.findViewById(R.id.team_D);
			groupHolder.team_F = (TextView) view.findViewById(R.id.team_F);
			groupHolder.team_L = (TextView) view.findViewById(R.id.team_L);
			groupHolder.team_order = (TextView) view
					.findViewById(R.id.team_order);
			groupHolder.team_Pts = (TextView) view.findViewById(R.id.team_Pts);
			groupHolder.team_W = (TextView) view.findViewById(R.id.team_W);
			groupHolder.team_icon = (ImageView) view
					.findViewById(R.id.team_icon);
			view.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) view.getTag();
		}
		groupHolder.team_A.setText(group.getTeams().get(arg0).getA());
		groupHolder.team_D.setText(group.getTeams().get(arg0).getD());
		groupHolder.team_F.setText(group.getTeams().get(arg0).getF());
		groupHolder.team_L.setText(group.getTeams().get(arg0).getL());
		groupHolder.team_order.setText(arg0 + 1 + "");
		groupHolder.team_Pts.setText(group.getTeams().get(arg0).getPts());
		groupHolder.team_W.setText(group.getTeams().get(arg0).getW());

		int imgId = context.getResources().getIdentifier(
				group.getTeams().get(arg0).getName().toLowerCase(), "drawable",
				context.getPackageName());

		groupHolder.team_icon.setBackgroundResource(imgId);

		return view;
	}
}
