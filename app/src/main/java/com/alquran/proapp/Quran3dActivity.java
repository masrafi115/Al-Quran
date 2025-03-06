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
import java.util.ArrayList;
import java.util.HashMap;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.media.*;
import com.chibde.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class Quran3dActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String fontName = "";
	private String typeace = "";
	private double page = 0;
	private double value = 0;
	private boolean juz_b = false;
	
	private ArrayList<String> types = new ArrayList<>();
	private ArrayList<String> juz = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> juz_pages = new ArrayList<>();
	
	private WebView webview1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private ImageView imageview2;
	private TextView textview1;
	private ImageView imageview3;
	private ImageView imageview4;
	
	private AlertDialog.Builder dialog_d;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.quran3d);
		initialize(_savedInstanceState);
		initializeLogic();
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
		webview1 = (WebView) findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		dialog_d = new AlertDialog.Builder(this);
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_goToPag(548);
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_goToPag(page + 1);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_goToPag(page - 1);
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_goToPag(1);
			}
		});
	}
	
	private void initializeLogic() {
		_changeActivityFont("manrope_bold");
		_shapeRadius(textview1, "#ffffff", 45);
		_shapeRadius(linear2, "#000000", 45);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.addJavascriptInterface(new CustomJavaScriptInterface(getApplicationContext()), "Android");
		page = Double.parseDouble(getIntent().getStringExtra("page"));
		textview1.setText(getIntent().getStringExtra("page"));
		webview1.loadUrl("file:///android_asset/index.html#p=".concat(getIntent().getStringExtra("page")));
		types.add("Page");
		types.add("Juz");
		juz = new Gson().fromJson("[\n  \"Alif Lam Meem\",\n  \"Sayaqool\",\n  \"Tilkal Rusull\",\n  \"Lan tana Loo'\",\n  \"5.Wal Mohsanat\",\n  \"La Yuhibbuallah\",\n  \"Wa Iza Samiu\",\n  \"Wa Lao Annana\",\n  \"Qalal Malao\",\n  \"Wa A'lamu\",\n  \"Yatazeroon\",\n  \"Wa Mamin Da'abat\",\n  \"Wa Ma Ubiroo\",\n  \"Rubama\",\n  \"Subhanallazi\",\n  \"Qal Alam\",\n  \"Aqtarabo\",\n  \"Qadd Aflaha\",\n  \"Wa Qalallazina\",\n  \"A'man Khalaq\",\n  \"Utlu Ma Oohi\",\n  \"Wa Manyaqnut\",\n  \"Wa Mali\",\n  \"Faman Azlam\",\n  \"Elahe Yuruddo\",\n  \"Ha'a Meem\",\n  \"Qala Fama Khatbukum\",\n  \"Qadd Sami Allah\",\n  \"Tabarakallazi\",\n  \"Amma Yatasa'aloon\"\n]", new TypeToken<ArrayList<String>>(){}.getType());
		juz_pages = new Gson().fromJson("[\n  {\n    \"page\": \"2\"\n  },\n  {\n    \"page\": \"21\"\n  },\n  {\n    \"page\": \"39\"\n  },\n  {\n    \"page\": \"57\"\n  },\n  {\n    \"page\": \"75\"\n  },\n  {\n    \"page\": \"93\"\n  },\n  {\n    \"page\": \"111\"\n  },\n  {\n    \"page\": \"129\"\n  },\n  {\n    \"page\": \"147\"\n  },\n  {\n    \"page\": \"165\"\n  },\n  {\n    \"page\": \"183\"\n  },\n  {\n    \"page\": \"201\"\n  },\n  {\n    \"page\": \"219\"\n  },\n  {\n    \"page\": \"237\"\n  },\n  {\n    \"page\": \"255\"\n  },\n  {\n    \"page\": \"273\"\n  },\n  {\n    \"page\": \"291\"\n  },\n  {\n    \"page\": \"309\"\n  },\n  {\n    \"page\": \"327\"\n  },\n  {\n    \"page\": \"345\"\n  },\n  {\n    \"page\": \"363\"\n  },\n  {\n    \"page\": \"381\"\n  },\n  {\n    \"page\": \"399\"\n  },\n  {\n    \"page\": \"417\"\n  },\n  {\n    \"page\": \"435\"\n  },\n  {\n    \"page\": \"453\"\n  },\n  {\n    \"page\": \"471\"\n  },\n  {\n    \"page\": \"489\"\n  },\n  {\n    \"page\": \"509\"\n  },\n  {\n    \"page\": \"529\"\n  }\n]\n", new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _Menu () {
	}
	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		menu.add(0, 0, 0, "Goto");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
			_Dialog();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void _shapeRadius (final View _v, final String _color, final double _radius) {
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		  shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		shape.setCornerRadius((int)_radius);
		
		shape.setColor(Color.parseColor(_color));
		_v.setBackgroundDrawable(shape);
	}
	
	
	public void _Dialog () {
		dialog_d.setTitle("Jump");
		View inflated_view = getLayoutInflater().inflate(R.layout.dialog_view, null);
		
		LinearLayout dialog_linear4 = inflated_view.findViewById(R.id.linear4);
		LinearLayout dialog_linear2 = inflated_view.findViewById(R.id.linear2);
		final LinearLayout dialog_linear5 = inflated_view.findViewById(R.id.linear5);
		
		
		final EditText dialog_edittext1 = new EditText(Quran3dActivity.this);
		dialog_edittext1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		dialog_edittext1.setHint("Go to Page");
		dialog_edittext1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
		dialog_linear4.addView(dialog_edittext1);
		
		final Spinner dialog_spinner = new Spinner(Quran3dActivity.this);
		dialog_spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		dialog_spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, types));
		dialog_linear2.addView(dialog_spinner);
		((ArrayAdapter)dialog_spinner.getAdapter()).notifyDataSetChanged();
		
		final Spinner dialog_spinner2 = new Spinner(Quran3dActivity.this);
		dialog_spinner2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		dialog_spinner2.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, juz));
		dialog_linear5.addView(dialog_spinner2);
		((ArrayAdapter)dialog_spinner2.getAdapter()).notifyDataSetChanged();
		
		
		
		dialog_d.setView(inflated_view);
		
		
		
		dialog_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
						final int _position = _param3;
						if (_position == 0) {
								juz_b = true;
								dialog_linear5.setVisibility(View.GONE);
						}
						else {
								juz_b = false;
								dialog_linear5.setVisibility(View.VISIBLE);
							
								
						}
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> _param1) {
						
				}
		});
		
		dialog_d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
				if (dialog_spinner.getSelectedItemPosition() == 0) {
						value = Double.parseDouble(dialog_edittext1.getText().toString());
						
				}
				else {
						//dialog_spinner.getSelectedItemPosition(
						value = Double.parseDouble(juz_pages.get(dialog_spinner2.getSelectedItemPosition()).get("page").toString());
				}
				_goToPag(value);
			}
		});
		dialog_d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog_d.create().show();
	}
	
	
	public void _LightThemeCommand () {
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
	
	
	public void _goToPag (final double _page) {
		webview1.loadUrl("javascript:gotoPageFun(\"".concat(String.valueOf((long)(_page)).concat("\")")));
		page = _page;
		textview1.setText(String.valueOf((long)(_page)));
	}
	
	
	public void _classes () {
	} public class CustomJavaScriptInterface {
			Context mContext;
			
			/** Instantiate the interface and set the context */
			CustomJavaScriptInterface(Context c) {
					mContext = c;
			}
			
			
			/** retrieve the ids */
			@JavascriptInterface
			public void getPage(final String _page) {
					textview1.setText(_page);
					page = Double.parseDouble(_page);
					//Do somethings with the Ids
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