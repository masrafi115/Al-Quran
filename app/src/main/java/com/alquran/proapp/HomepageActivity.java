package com.alquran.proapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.graphics.Typeface;
import java.text.DecimalFormat;
import androidx.media.*;
import com.chibde.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import java.time.LocalTime;
import java.time.Duration
;

public class HomepageActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private String fontName = "";
	private String typeace = "";
	private String json = "";
	private String res = "";
	private double lat = 0;
	private double lng = 0;
	private HashMap<String, Object> mapdata = new HashMap<>();
	private HashMap<String, Object> mapvalue = new HashMap<>();
	private double n = 0;
	private double m = 0;
	private HashMap<String, Object> mapvar = new HashMap<>();
	private HashMap<String, Object> map_data = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<String> prayers = new ArrayList<>();
	private ArrayList<String> prayer_names = new ArrayList<>();
	private ArrayList<String> prayer_times = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private GridView gridview1;
	private TextView up_waqt_time;
	private TextView up_waqt;
	private TextView textview25;
	private TextView textview26;
	
	private Intent i = new Intent();
	private SharedPreferences waqt;
	private Calendar c = Calendar.getInstance();
	private RequestNetwork prayer_time_api;
	private RequestNetwork.RequestListener _prayer_time_api_request_listener;
	private SharedPreferences database;
	private Calendar calendar = Calendar.getInstance();
	private RequestNetwork location_api;
	private RequestNetwork.RequestListener _location_api_request_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.homepage);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		gridview1 = (GridView) findViewById(R.id.gridview1);
		up_waqt_time = (TextView) findViewById(R.id.up_waqt_time);
		up_waqt = (TextView) findViewById(R.id.up_waqt);
		textview25 = (TextView) findViewById(R.id.textview25);
		textview26 = (TextView) findViewById(R.id.textview26);
		waqt = getSharedPreferences("waqt", Activity.MODE_PRIVATE);
		prayer_time_api = new RequestNetwork(this);
		database = getSharedPreferences("database", Activity.MODE_PRIVATE);
		location_api = new RequestNetwork(this);
		
		gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					i.setClass(getApplicationContext(), MusicActivity.class);
					startActivity(i);
				}
				else {
					if (_position == 1) {
						i.setClass(getApplicationContext(), SuraListActivity.class);
						startActivity(i);
					}
					else {
						if (_position == 2) {
							i.setClass(getApplicationContext(), QuranTranslatedActivity.class);
							startActivity(i);
						}
						else {
							if (_position == 3) {
								i.setClass(getApplicationContext(), TasbihActivity.class);
								startActivity(i);
							}
						}
					}
				}
			}
		});
		
		_prayer_time_api_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				FileUtil.writeFile("/storage/emulated/0/tmp/logs.txt", _response);
				try{
						mapdata = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
						//tz.setVisibility(View.VISIBLE);
						//progressbar1.setVisibility(View.GONE);
						JSONObject obj = new JSONObject(_response);
						JSONArray jarr = obj.getJSONArray("data"); 
						JSONObject cv = jarr.getJSONObject(0);
						JSONObject cv2 = cv.getJSONObject("timings");
						waqt.edit().putString(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()), cv2.toString().replaceAll("\\(\\+[0-9]+\\)","").replaceAll(" ","")).commit();
						
						//textview25.setText(new SimpleDateFormat("dd-mm-yyyy").format(c.getTime()));
						up_waqt.setText(_getUpcomingWaqt(waqt.getString(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()), "")));
						up_waqt_time.setText(_getUpcomingWaqtTime(waqt.getString(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()), "")).substring((int)(0), (int)(5)));
					
						
						/*fajr = cv2.getString("Fajr");
	sunrise = cv2.getString("Sunrise");
	dhuhr = cv2.getString("Dhuhr");
	asr = cv2.getString("Asr");
	sunset = cv2.getString("Sunset");
	sunset = cv2.getString("Sunset");
	maghrib = cv2.getString("Maghrib");
	isha = cv2.getString("Isha");
	imsak = cv2.getString("Imsak");
	midnight = cv2.getString("Midnight");
	*/
						//JSONObject timezone = jarr.getJSONObject(4);
						//JSONObject timezone2 = timezone.getJSONObject("meta");
						//zone = timezone2.getString("timezone");
						//tz.setText(zone);
				}catch(Exception e){
					
						showMessage(e.toString());
						
				}
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_location_api_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (_response.equals("404 page not found")) {
					
				}
				else {
					map_data = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
					lat = Double.parseDouble(map_data.get("latitude").toString());
					lng = Double.parseDouble(map_data.get("longitude").toString());
					database.edit().putString("lat", map_data.get("latitude").toString()).commit();
					database.edit().putString("lng", map_data.get("longitude").toString()).commit();
					prayer_time_api.startRequestNetwork(RequestNetworkController.GET, "http://api.aladhan.com/v1/calendar?".concat("latitude=".concat(String.valueOf(lat).concat("&longitude=".concat(String.valueOf(lng).concat("&method=".concat("3".concat("&month=".concat(new SimpleDateFormat("MM").format(calendar.getTime()).concat("&year=".concat(new SimpleDateFormat("yyyy").format(calendar.getTime()))))))))))), "api", _prayer_time_api_request_listener);
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		_data();
		com.google.android.material.appbar.AppBarLayout appBarLayout = (com.google.android.material.appbar.AppBarLayout) _toolbar.getParent();
		appBarLayout.setStateListAnimator(null);
		gridview1.setNumColumns(2); 
		gridview1.setColumnWidth(GridView.AUTO_FIT); 
		gridview1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
		
		gridview1.setAdapter(new Gridview1Adapter(list));
		((BaseAdapter)gridview1.getAdapter()).notifyDataSetChanged();
		_changeActivityFont("lpmq");
		up_waqt_time.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google.ttf"), 1);
		up_waqt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google.ttf"), 1);
		textview25.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google.ttf"), 1);
		textview26.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google.ttf"), 1);
		textview25.setText(new SimpleDateFormat("EEEE, dd MMMM").format(c.getTime()));
		if (!waqt.getString(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()), "").equals("")) {
			up_waqt.setText(_getUpcomingWaqt(waqt.getString(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()), "")));
			up_waqt_time.setText(_getUpcomingWaqtTime(waqt.getString(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()), "")).substring((int)(0), (int)(5)));
		}
		else {
			if (!database.getString("lat", "").equals("") && !database.getString("lng", "").equals("")) {
				lat = Double.parseDouble(database.getString("lat", ""));
				lng = Double.parseDouble(database.getString("lng", ""));
				prayer_time_api.startRequestNetwork(RequestNetworkController.GET, "http://api.aladhan.com/v1/calendar?".concat("latitude=".concat(String.valueOf(lat).concat("&longitude=".concat(String.valueOf(lng).concat("&method=".concat("3".concat("&month=".concat(new SimpleDateFormat("MM").format(calendar.getTime()).concat("&year=".concat(new SimpleDateFormat("yyyy").format(calendar.getTime()))))))))))), "api", _prayer_time_api_request_listener);
			}
			else {
				location_api.startRequestNetwork(RequestNetworkController.GET, "https://freegeoip.app/json/", "api", _location_api_request_listener);
			}
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	public void _data () {
		map = new HashMap<>();
		map.put("name", "MP3");
		map.put("resId", "quran_mp3");
		list.add(map);
		map = new HashMap<>();
		map.put("name", "Reading");
		map.put("resId", "quran");
		list.add(map);
		map = new HashMap<>();
		map.put("name", "Quran Translation");
		map.put("resId", "quran_translated");
		list.add(map);
		map = new HashMap<>();
		map.put("name", "Tasbih");
		map.put("resId", "tasbih");
		list.add(map);
		/*
list = new Gson().fromJson("", new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
*/
	}
	
	
	public void _roundedCorners (final View _view, final double _one, final double _two, final double _three, final double _four, final String _color, final double _stroke, final String _stColor, final double _num, final String _NOTES) {
		Double left_top = _one;
		Double right_top = _two;
		Double right_bottom = _three;
		Double left_bottom = _four;
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
		s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[] {left_top.floatValue(),left_top.floatValue(), right_top.floatValue(),right_top.floatValue(), left_bottom.floatValue(),left_bottom.floatValue(), right_bottom.floatValue(),right_bottom.floatValue()});
		s.setColor(Color.parseColor(_color));
		s.setStroke((int)_stroke, Color.parseColor(_stColor));
		_view.setBackground(s);
		_view.setElevation((int)_num);
	}
	
	
	public void _changeActivityFont (final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			}
			else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				}
				else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					}
					else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
	}
	
	
	public void _Menu () {
	}
	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		menu.add(0, 0, 0, "About").setIcon(R.drawable.outline_info_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
			i.setClass(getApplicationContext(), AboutAppActivity.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public String _getUpcomingWaqt (final String _json) {
		mapvar = new Gson().fromJson(_json, new TypeToken<HashMap<String, Object>>(){}.getType());
		SketchwareUtil.getAllKeysFromMap(mapvar, prayer_names);
		 prayer_times=getAllValuesFromMap(mapvar);
		return (prayer_names.get((int)(prayer_times.indexOf(_getUpcomingWaqtTime(_json)))));
	}
	
	
	public String _getUpcomingWaqtTime (final String _json) {
		n = 0;
		mapvalue = new Gson().fromJson(_json, new TypeToken<HashMap<String, Object>>(){}.getType());
		prayers = getAllValuesFromMap(mapvalue );
		String result = "";
		final LocalTime currentTime = LocalTime.now();
		
		// Create a comparator to sort times based on their difference from the current time
		Comparator<String> timeComparator = new Comparator<String>() {
				@Override
				public int compare(String t1, String t2) {
						LocalTime time1 = LocalTime.parse(t1);
						LocalTime time2 = LocalTime.parse(t2);
						long diff1 = Math.abs(Duration.between(currentTime, time1).toMinutes());
						long diff2 = Math.abs(Duration.between(currentTime, time2).toMinutes());
						return Long.compare(diff1, diff2);
				}
		};
		
		// Sort the times in ascending order of their difference from the current time
		Collections.sort(prayers, timeComparator);
		
		result = prayers.get((int)(0));
		return (result);
	}
	
	
	public double _getLongTime (final String _timestring) {
		Calendar cd = Calendar.getInstance();
		
		try
		{
				Date date1 = new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(new SimpleDateFormat("dd-MM-yyyy").format(cd.getTime())+" "+_timestring.substring((int)(0), (int)(5)));
				Calendar c = Calendar.getInstance();
				c.setTime(date1);
				//System.out.println("ffghh"+ c.getTimeInMillis());
				return c.getTimeInMillis();
		}
		catch (java.text.ParseException e)
		{
				showMessage(e.toString());
				return 0;
		}
		
		
		
		
		
	}
	
	
	public void _getAllValuesFromMap () {
	} public static ArrayList<String> getAllValuesFromMap(Map<String, Object> map) {
		ArrayList<String> output = new ArrayList<String>();
						if (output == null) return null;
						output.clear();
		
						if (map == null || map.size() <= 0) return null;
		
						Iterator itr = map.entrySet().iterator();
						while (itr.hasNext()) {
									Map.Entry<String, String> entry = (Map.Entry) itr.next();
									output.add(entry.getValue());
						}
						return output;
	}
	
	
	public class Gridview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Gridview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.cust, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			
			res = list.get((int)_position).get("resId").toString();
			int resId = getApplicationContext().getResources().getIdentifier(res, "drawable", getApplicationContext().getPackageName());
			
			imageview1.setImageResource(resId);
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}