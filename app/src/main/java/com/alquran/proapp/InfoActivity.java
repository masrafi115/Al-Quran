package com.alquran.proapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Typeface;
import androidx.media.*;
import com.chibde.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class InfoActivity extends  AppCompatActivity  { 
	
	
	private ArrayList<String> Translations = new ArrayList<>();
	
	private LinearLayout ly_Bg;
	private LinearLayout ly_Head;
	private TextView tv_Reciter;
	private LinearLayout ly_AlbumBg;
	private TextView tv_SuraName;
	private ImageView iv_Album;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.info);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		ly_Bg = (LinearLayout) findViewById(R.id.ly_Bg);
		ly_Head = (LinearLayout) findViewById(R.id.ly_Head);
		tv_Reciter = (TextView) findViewById(R.id.tv_Reciter);
		ly_AlbumBg = (LinearLayout) findViewById(R.id.ly_AlbumBg);
		tv_SuraName = (TextView) findViewById(R.id.tv_SuraName);
		iv_Album = (ImageView) findViewById(R.id.iv_Album);
	}
	
	private void initializeLogic() {
		try {
			_Translate(Locale.getDefault().getDisplayLanguage());
			if (getIntent().getBooleanExtra("dialogTheme",true)) {
				getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
				
				{
					android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
					int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
					SketchUi.setColor(0xFF1F1F1F);SketchUi.setCornerRadius(50);
					
					((ViewGroup)getWindow().getDecorView()).getChildAt(0).setBackground(SketchUi);
				}
				ly_Bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF1F1F1F));
			}
			tv_Reciter.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 0);
			
			tv_SuraName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 1);
			tv_Reciter.setText(Translations.get((int)(0)).concat(":  ".concat(getIntent().getStringExtra("Reciter"))));
			
			tv_SuraName.setText(getIntent().getStringExtra("Sura"));
			ly_AlbumBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));
			ly_AlbumBg.setClipToOutline(true);
			iv_Album.setImageResource(R.drawable.app_logo);
		} catch(Exception e) {
			 
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
	
	public void _dialogTheme () {
	}
	 @Override 
	    public void setContentView( int layoutResID) {
		if(getIntent().getBooleanExtra("dialogTheme",true)){
			supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
			setTheme(R.style.Theme_AppCompat_Light_Dialog);
			setFinishOnTouchOutside(true);
			
			//change true to false if you want to make dialog non cancellable when clicked outside
			try {
				 	java.lang.reflect.Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions"); getActivityOptions.setAccessible(true);
				 Object options = getActivityOptions.invoke(this); Class<?>[] classes = Activity.class.getDeclaredClasses(); Class<?> translucentConversionListenerClazz = null; 
				for (Class clazz : classes) { if (clazz.getSimpleName().contains("TranslucentConversionListener")) { translucentConversionListenerClazz = clazz; } } 
				java.lang.reflect.Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions.class); convertToTranslucent.setAccessible(true); convertToTranslucent.invoke(this, null, options); } catch (Throwable t) {
			}
		}
		super.setContentView(layoutResID);  
	}
	{
	}
	
	
	public String _getDuration (final double _duration) {
		long millie = (long)_duration;
		    long seconds = (millie / 1000);
		    long second = seconds % 60;
		    long minute = (seconds / 60) % 60;
		    long hour = (seconds / (60 * 60)) % 24;
		
		    String result = "";
		    if (hour > 0) {
			        return String.format("%02d:%02d:%02d", hour, minute, second);
			    }
		    else {
			        return String.format("%02d:%02d" , minute, second);
			    }
	}
	
	
	public void _Translate (final String _lang) {
		if (_lang.toUpperCase().contains("ESP")) {
			Translations.add("Reciter");
			Translations.add("Duration");
		}
		else {
			if (_lang.toUpperCase().contains("ENG")) {
				Translations.add("Reciter");
				Translations.add("Duration");
			}
			else {
				Translations.add("Reciter");
				Translations.add("Duration");
			}
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