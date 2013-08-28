package com.example.europe2012;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.read_xml.data.XMLParser2;
import com.example.read_xml.entity.Europe2012;

public class MainActivity extends Activity {

	private ListView listView=null;
	private List<HashMap<String, String>>imgsHashMaps=null;
	private Europe2012 europe2012=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		//从XML数据读取欧洲杯信息
		XMLParser2 parser=XMLParser2.getInstance(MainActivity.this);
		europe2012 = parser.getEurope2012();
		
		//获取国家名字
		String[] countryStrings=getResources().getStringArray(R.array.country);
		
		/**
		 * 通过名字获取资源id
		 * 
		 * 		getResources().
				public int getIdentifier (String name, String defType, String defPackage) 
		 */
		

		
		imgsHashMaps=new ArrayList<HashMap<String,String>>();
		
		for (int i = 0; i <countryStrings.length; i+=4) {
			
			HashMap<String, String>map=new HashMap<String, String>();
			for (int j = 0; j < 4; j++) {
				map.put("icon_"+(j+1),countryStrings[i+j]);
				map.put("name_"+(j+1),countryStrings[i+j]);
			}
			
			imgsHashMaps.add(map);
		}
		
		listView=(ListView) findViewById(R.id.main_listView);
		
		/**
		 * layout:R.layout.listlayout
		 * "icon":R.id.imageView1
		 * "name":R.id.textView1
		 */
		SimpleAdapter simpleAdapter=
				new SimpleAdapter
				(
						this, imgsHashMaps,
						R.layout.activity_main_listview, 
						new String[]{
								"icon_1","name_1",
								"icon_2","name_2",
								"icon_3","name_3",
								"icon_4","name_4"}, 
						new int[]{
								R.id.imageView1_1,R.id.textView1_1,
								R.id.imageView1_2,R.id.textView1_2,
								R.id.imageView2_1,R.id.textView2_1,
								R.id.imageView2_2,R.id.textView2_2}
				)
		{

			@Override
			public void setViewImage(ImageView v, String value) {
				// TODO Auto-generated method stub
				super.setViewImage(v, value);
				
				//res文件夹下的drawable文件夹里有 名字为 value.toLowerCase()的资源
				int rsId=MainActivity.this.getResources().getIdentifier(value.toLowerCase(), "drawable", MainActivity.this.getPackageName());
				v.setBackgroundResource(rsId);
			}
			
		};
		listView.setAdapter(simpleAdapter);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
	
				//为对话框创建ExpandableListView
				ExpandableListView expandableListView=new ExpandableListView(MainActivity.this);
				EuropeCupExpandableListViewAdapter adapter=new EuropeCupExpandableListViewAdapter(MainActivity.this,europe2012.getGroups().get(arg2));
				
				expandableListView.setAdapter(adapter);
				//绑定ExpandableListView子项的点击事件

				AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
				builder.setView(expandableListView)
							.setIcon(R.drawable.ic_menu_largetiles)
							.setTitle("EuropeCup Group "+europe2012.getGroups().get(arg2).getName());
				final AlertDialog groupDetailDialog=builder.create();
				groupDetailDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						groupDetailDialog.dismiss();
					}
				});
				groupDetailDialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
