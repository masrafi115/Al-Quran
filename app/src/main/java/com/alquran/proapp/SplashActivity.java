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
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
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
import com.alquran.proapp.service.util.DefaultDownloadReceiver;
import com.alquran.proapp.service.QuranDownloadService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alquran.proapp.util.QuranFileUtils;
import com.alquran.proapp.util.QuranScreenInfo;
import com.alquran.proapp.service.util.ServiceIntentHelper;
import com.alquran.proapp.data.Const;
import android.preference.PreferenceManager;

public class SplashActivity extends  AppCompatActivity implements DefaultDownloadReceiver.SimpleDownloadListener  { 
	
	private Timer _timer = new Timer();
	
	private  DefaultDownloadReceiver mDownloadReceiver = null;
	private  AsyncTask<Void, Void, Boolean> mCheckAudioTask;
	private  AlertDialog mErrorDialog = null;
	private  AlertDialog mPromptForDownloadDialog = null;
	private  static final String PAGES_DOWNLOAD_KEY = "PAGES_DOWNLOAD_KEY";
	private String mPatchUrl = "";
	private boolean mIsPaused = false;
	private double in = 0;
	private  static final String TAG = "com.alquran.proapp";
	private  SharedPreferences mSharedPreferences;
	
	private ArrayList<String> SMAP = new ArrayList<>();
	
	private LinearLayout Bg;
	private ImageView imageLogo;
	
	private Intent i_Musi = new Intent();
	private TimerTask t_CheckPermission;
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.splash);
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
mSharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(getApplicationContext());
		
		Bg = (LinearLayout) findViewById(R.id.Bg);
		imageLogo = (ImageView) findViewById(R.id.imageLogo);
	}
	
	private void initializeLogic() {
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
	public void onStart() {
		super.onStart();
		
	}
	
	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mIsPaused = false;
		mDownloadReceiver = new DefaultDownloadReceiver(this,
		QuranDownloadService.DOWNLOAD_TYPE_AUDIO);
		mDownloadReceiver.setCanCancelDownload(true);
		String action = QuranDownloadService.ProgressIntent.INTENT_NAME;
		LocalBroadcastManager.getInstance(this).registerReceiver(
		mDownloadReceiver,
		new IntentFilter(action));
		mDownloadReceiver.setListener(this);
		
		
		// check whether or not we need to download
		
		
		mCheckAudioTask = new CheckAudioAsyncTask();
		mCheckAudioTask.execute();
		
	}
	
	@Override
	public void onPause() {

		mIsPaused = false;
		mDownloadReceiver = new DefaultDownloadReceiver(this,
		QuranDownloadService.DOWNLOAD_TYPE_AUDIO);
		mDownloadReceiver.setCanCancelDownload(true);
		String action = QuranDownloadService.ProgressIntent.INTENT_NAME;
		LocalBroadcastManager.getInstance(this).registerReceiver(
		mDownloadReceiver,
		new IntentFilter(action));
		mDownloadReceiver.setListener(this);
		
		
		// check whether or not we need to download
		
		
		mCheckAudioTask = new CheckAudioAsyncTask();
		mCheckAudioTask.execute();
		
		super.onPause();
	}
	
	public void _extra () {
		  }
	   @Override
	   public void handleDownloadFailure(int errId){
		      if (mErrorDialog != null && mErrorDialog.isShowing()){
			         return;
			      }
		      
		      _showFatalErrorDialog(errId);
		   }
	 @Override
	   public void handleDownloadSuccess(){
		mSharedPreferences.edit().putString("downloaded", "true").commit();
		_openApp();
	}
	
	
	public void _showFatalErrorDialog (final double _errorId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage((int)_errorId);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.download_retry,
		new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						mErrorDialog = null;
						//removeErrorPreferences();
						_downloadQuranAudio(true);
				}
		});
		
		builder.setNegativeButton(R.string.download_cancel,
		new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						mErrorDialog = null;
						//removeErrorPreferences();
						mSharedPreferences.edit().putBoolean(
						Const.PREF_SHOULD_FETCH_PAGES, false)
						.commit();
						//_openApp();
						//Don't allow user to open if not download
						finish();
				}
		});
		
		mErrorDialog = builder.create();
		mErrorDialog.show();
		
	}
	
	
	public void _implement () {
	}
	
	
	public void _promptForDownload () {
		int message = R.string.downloadPrompt;
		/*if (QuranScreenInfo.getInstance().isTablet(this) &&
(mNeedPortraitImages != mNeedLandscapeImages)){
	message = R.string.downloadTabletPrompt;
}*/
		//burası
		if (!TextUtils.isEmpty(mPatchUrl)){
				// patch message if applicable
				message = R.string.downloadImportantPrompt;
		}
		//burası
		
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage(message);
		dialog.setCancelable(false);
		
		dialog.setPositiveButton(R.string.downloadPrompt_ok,
		new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						mPromptForDownloadDialog = null;
						mSharedPreferences.edit().putBoolean(
						Const.PREF_SHOULD_FETCH_PAGES, true)
						.commit();
						_downloadQuranAudio(true);
				}
		});
		
		dialog.setNegativeButton(R.string.downloadPrompt_no, 
		new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						mPromptForDownloadDialog = null;
						finish();
				}
		});
		
		mPromptForDownloadDialog = dialog.create();
		mPromptForDownloadDialog.setTitle(R.string.downloadPrompt_title);
		mPromptForDownloadDialog.show();
		
	}
	
	
	public void _openApp () {
		i.setClass(getApplicationContext(), HomepageActivity.class);
		startActivity(i);
		finish();
	}
	
	
	public void _classes () {
	} public class CheckAudioAsyncTask extends AsyncTask<Void, Void, Boolean> {
			
			@Override
			protected Boolean doInBackground(Void... params) {
					// intentionally not sleeping because waiting
					// for the splash screen is not cool.
					//QuranFileUtils.migrateAudio(MainActivity.this);
					
					
						boolean haveAll = QuranFileUtils.haveAllAudios(
						SplashActivity.this);
						
						return haveAll;
				
			}
			
			@Override
			protected void onPostExecute(Boolean result)
			{
					mCheckAudioTask = null;
					mPatchUrl = "https://web3app.000webhostapp.com/quran.zip";
					
					/*if (!mSharedPreferences.contains(Const.PREF_DOWNLOAD_SCRIPT)) {
			promptForScriptChoice();
			return;
		}*/
					if (mIsPaused) return; 
					if (result == null || !result) 
					{
						//The main problem here. Pages download key is "" empty. its the reason. pages download key shouldn'tbe empty
				//pages download key must have the error code of "page not download" there's severl error key starting from 1-6
				
							String lastErrorItem = mSharedPreferences.getString(QuranDownloadService.PREF_LAST_DOWNLOAD_ITEM, "");
							if (PAGES_DOWNLOAD_KEY.equals(lastErrorItem)) 
							{
									
									int lastError = mSharedPreferences.getInt(QuranDownloadService.PREF_LAST_DOWNLOAD_ERROR, 0);
									int errorId = ServiceIntentHelper.getErrorResourceFromErrorCode(lastError, false);
									//FileUtil.writeFile("/storage/emulated/0/tmp/logasyncerror.txt", "fatal error");
								
									SplashActivity.this._showFatalErrorDialog(errorId);
							} else if (mSharedPreferences.getBoolean(Const.PREF_SHOULD_FETCH_PAGES, false)) 
							{
									//FileUtil.writeFile("/storage/emulated/0/tmp/logasync.txt", "should fetch");
									SplashActivity.this._downloadQuranAudio(false);
							} else {
									//FileUtil.writeFile("/storage/emulated/0/tmp/logasyncnew.txt", "new setup");
									
									SplashActivity.this._promptForDownload();
							}
					}
					else
					{
							// force a check for the images version 3, if it's not
							// there, download the patch.
							/*QuranScreenInfo qsi = QuranScreenInfo.getInstance();
			String widthParam = qsi.getWidthParam();
			if (qsi.isTablet(MainActivity.this)){
				String tabletWidth = qsi.getTabletWidthParam();
				if ((!QuranFileUtils.isVersion(MainActivity.this,
				widthParam, 3)) ||
				(!QuranFileUtils.isVersion(MainActivity.this,
				tabletWidth, 3))){
					widthParam += tabletWidth;
					// get patch for both landscape/portrait tablet images
					//mPatchUrl = QuranFileUtils.getPatchFileUrl(widthParam, 3);
					MainActivity.this._promptForDownload();
					return;
				}
			}
			else if (!QuranFileUtils.isVersion(MainActivity.this,
			widthParam, 3)){
				// explicitly check whether we need to fix the images
				//mPatchUrl = QuranFileUtils.getPatchFileUrl(widthParam, 3);
				MainActivity.this._promptForDownload();
				return;
			}*/
							
							/*long time = mSharedPreferences.getLong(
			Const.PREF_LAST_UPDATED_TRANSLATIONS, 0);
			Date now = new Date();
			Log.wtf(TAG, "checking whether we should update translations..");
			if (now.getTime() - time > Const.TRANSLATION_REFRESH_TIME){
				Log.wtf(TAG, "updating translations list...");
				Intent intent = new Intent(MainActivity.this,
				QuranDownloadService.class);
				intent.setAction(
				QuranDownloadService.ACTION_CHECK_TRANSLATIONS);
				startService(intent);
			}*/
							_openApp();
					}
			}      
		
	}
	
	
	public void _downloadQuranAudio (final boolean _force) {
		if (mDownloadReceiver != null && mDownloadReceiver.didReceieveBroadcast() && !_force){ 
				//showMessage("Firsttry");
				return; 
		}
		if (mIsPaused){ 
				//showMessage("mls is paused");
				return; 
		}
		
		//QuranScreenInfo qsi = QuranScreenInfo.getInstance();
		
		String url = mPatchUrl;
		/*if (mNeedPortraitImages && !mNeedLandscapeImages){
	// phone (and tablet when upgrading on some devices, ex n10)
	url = QuranFileUtils.getZipFileUrl(this);
}
else if (mNeedLandscapeImages && !mNeedPortraitImages){
	// tablet (when upgrading from pre-tablet on some devices, ex n7).
	url = QuranFileUtils.getZipFileUrl(this,qsi.getTabletWidthParam());
}
else {
	// new tablet installation - if both image sets are the same
	// size, then just get the correct one only
	if (qsi.getTabletWidthParam().equals(qsi.getWidthParam())){
		url = QuranFileUtils.getZipFileUrl(this);
	}
	else {
		// otherwise download one zip with both image sets
		String widthParam = qsi.getWidthParam() +
		qsi.getTabletWidthParam();
		url = QuranFileUtils.getZipFileUrl(this,widthParam);
	}
}*/
		
		// if we have a patch url, just use that
		if (!TextUtils.isEmpty(mPatchUrl)){
				url = mPatchUrl;
				String random = String.valueOf((long)SketchwareUtil.getRandom((int)(9999), (int)(99999999)));
				//FileUtil.writeFile("/storage/emulated/0/tmp/log"+random+".txt", url);
				//showMessage(url);
		}
		
		String destination = QuranFileUtils.getQuranBaseDirectory(
		SplashActivity.this);
		String random = String.valueOf((long)SketchwareUtil.getRandom((int)(9999), (int)(99999999)));
			//FileUtil.writeFile("/storage/emulated/0/tmp/logmethod"+random+".txt", url);
		
		// start service
		Intent intent = ServiceIntentHelper.getDownloadIntent(this, url,
		destination, getString(R.string.app_name), PAGES_DOWNLOAD_KEY,
		QuranDownloadService.DOWNLOAD_TYPE_AUDIO);
		
		if (!_force){
				// handle race condition in which we missed the error preference and
				// the broadcast - if so, just rebroadcast errors so we handle them
				intent.putExtra(QuranDownloadService.EXTRA_REPEAT_LAST_ERROR, true);
		}
		
		startService(intent);
		
		
		
		/*
if (_force) {
SketchwareUtil.showMessage(getApplicationContext(), "with force");
}
else {
SketchwareUtil.showMessage(getApplicationContext(), "without force");
}


*/
	}
	
	
	public void _superclass () {
	}
	
	
	public void _initialize_ () {
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