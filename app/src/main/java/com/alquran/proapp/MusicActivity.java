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
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.widget.SeekBar;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.graphics.Typeface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;

public class MusicActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private String State = "";
	private double repeat_times = 0;
	private double RandomValue = 0;
	private boolean Seeking = false;
	private double Current = 0;
	private boolean FirstPlay = false;
	private boolean UserPause = false;
	private double SeekValue = 0;
	private double Random = 0;
	private boolean Shuffled = false;
	private  AudioManager am;
	private boolean Headphone = false;
	private String Session = "";
	private  VolumeShaper Vsh;
	private String path = "";
	private boolean showFavorite = false;
	private double lastPosition = 0;
	private double song = 0;
	private String filepath = "";
	private double oldPosition = 0;
	private double viewIndex = 0;
	private double viewTop = 0;
	private boolean onSeek = false;
	private boolean isDarkMode = false;
	private String song_duration = "";
	private String list_json = "";
	private String accentColor = "";
	
	private ArrayList<String> Translates = new ArrayList<>();
	private ArrayList<String> sura = new ArrayList<>();
	private ArrayList<String> files = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> FavoriteMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> All_Song_Data = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> background_list = new ArrayList<>();
	
	private LinearLayout ly_Bg;
	private LinearLayout ly_Toolbar;
	private CoordinatorLayout cl_Bg;
	private TextView tv_ToolbarTitle;
	private RecyclerView rv_SongList;
	private LinearLayout bsb_Player;
	private LinearLayout linearCurPlays;
	private RelativeLayout rl_FullPlay;
	private SeekBar seekProgress;
	private LinearLayout linearInfo;
	private LinearLayout AlbumImgBg;
	private LinearLayout linearSongInfo;
	private ImageView iv_Play;
	private ImageView iv_Next;
	private ImageView AlbumImage;
	private TextView textSongTitle;
	private TextView textArtist;
	private LinearLayout AlbumImgFullBg;
	private LinearLayout backLayer;
	private ImageView AlbumImageFull;
	private LinearLayout topLayer;
	private LinearLayout middleLayer;
	private ImageView iv_Menup;
	private TextView textSongTitleFull;
	private TextView textArtistFull;
	private SeekBar seekProgressFull;
	private LinearLayout ly_TimesBg;
	private LinearLayout ly_ControllerBg;
	private TextView textCurrentTime;
	private LinearLayout ly_FillTimes;
	private TextView textFullTime;
	private ImageView iv_Repeat;
	private ImageView iv_PreviousFull;
	private ImageView iv_PlayFull;
	private ImageView iv_NextFull;
	private ImageView iv_Suffle;
	
	private Intent i_Info = new Intent();
	private Intent intent = new Intent();
	private Calendar calendar = Calendar.getInstance();
	private SharedPreferences songData;
	private TimerTask timer;
	private TimerTask time;
	private AlertDialog.Builder media_error_dialog;
	private AlertDialog.Builder permission;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.music);
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
		
		ly_Bg = (LinearLayout) findViewById(R.id.ly_Bg);
		ly_Toolbar = (LinearLayout) findViewById(R.id.ly_Toolbar);
		cl_Bg = (CoordinatorLayout) findViewById(R.id.cl_Bg);
		tv_ToolbarTitle = (TextView) findViewById(R.id.tv_ToolbarTitle);
		rv_SongList = (RecyclerView) findViewById(R.id.rv_SongList);
		bsb_Player = (LinearLayout) findViewById(R.id.bsb_Player);
		linearCurPlays = (LinearLayout) findViewById(R.id.linearCurPlays);
		rl_FullPlay = (RelativeLayout) findViewById(R.id.rl_FullPlay);
		seekProgress = (SeekBar) findViewById(R.id.seekProgress);
		linearInfo = (LinearLayout) findViewById(R.id.linearInfo);
		AlbumImgBg = (LinearLayout) findViewById(R.id.AlbumImgBg);
		linearSongInfo = (LinearLayout) findViewById(R.id.linearSongInfo);
		iv_Play = (ImageView) findViewById(R.id.iv_Play);
		iv_Next = (ImageView) findViewById(R.id.iv_Next);
		AlbumImage = (ImageView) findViewById(R.id.AlbumImage);
		textSongTitle = (TextView) findViewById(R.id.textSongTitle);
		textArtist = (TextView) findViewById(R.id.textArtist);
		AlbumImgFullBg = (LinearLayout) findViewById(R.id.AlbumImgFullBg);
		backLayer = (LinearLayout) findViewById(R.id.backLayer);
		AlbumImageFull = (ImageView) findViewById(R.id.AlbumImageFull);
		topLayer = (LinearLayout) findViewById(R.id.topLayer);
		middleLayer = (LinearLayout) findViewById(R.id.middleLayer);
		iv_Menup = (ImageView) findViewById(R.id.iv_Menup);
		textSongTitleFull = (TextView) findViewById(R.id.textSongTitleFull);
		textArtistFull = (TextView) findViewById(R.id.textArtistFull);
		seekProgressFull = (SeekBar) findViewById(R.id.seekProgressFull);
		ly_TimesBg = (LinearLayout) findViewById(R.id.ly_TimesBg);
		ly_ControllerBg = (LinearLayout) findViewById(R.id.ly_ControllerBg);
		textCurrentTime = (TextView) findViewById(R.id.textCurrentTime);
		ly_FillTimes = (LinearLayout) findViewById(R.id.ly_FillTimes);
		textFullTime = (TextView) findViewById(R.id.textFullTime);
		iv_Repeat = (ImageView) findViewById(R.id.iv_Repeat);
		iv_PreviousFull = (ImageView) findViewById(R.id.iv_PreviousFull);
		iv_PlayFull = (ImageView) findViewById(R.id.iv_PlayFull);
		iv_NextFull = (ImageView) findViewById(R.id.iv_NextFull);
		iv_Suffle = (ImageView) findViewById(R.id.iv_Suffle);
		songData = getSharedPreferences("songData", Activity.MODE_PRIVATE);
		media_error_dialog = new AlertDialog.Builder(this);
		permission = new AlertDialog.Builder(this);
		
		linearCurPlays.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		rl_FullPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		seekProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged (SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				if (M.mp != null){
					if (onSeek) {
						if (M.mp.isPlaying()) {
							M.mp.pause();
						}
						else {
							M.mp.pause();
						}
						M.mp.seekTo(_progressValue);
					}
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				if (M.mp != null){
					onSeek = true;
				}
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				if (M.mp != null){
					onSeek = false;
					if (M.mp.isPlaying()) {
						
					}
					else {
						M.mp.start();
					}
				}
			}
		});
		
		iv_Play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Play_Pause();
			}
		});
		
		iv_Next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Next();
			}
		});
		
		iv_Menup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		seekProgressFull.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged (SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				if (M.mp != null){
					if (onSeek) {
						if (M.mp.isPlaying()) {
							M.mp.pause();
						}
						else {
							M.mp.pause();
						}
						M.mp.seekTo(_progressValue);
					}
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				if (M.mp != null){
					onSeek = true;
				}
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				if (M.mp != null){
					onSeek = false;
					if (M.mp.isPlaying()) {
						
					}
					else {
						M.mp.start();
					}
				}
			}
		});
		
		iv_Repeat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (songData.getString("repeat", "").equals("false")) {
					songData.edit().putString("repeat", "true").commit();
					iv_Repeat.setImageResource(R.drawable.round_repeat_one_white_24dp);
				}
				else {
					if (songData.getString("repeat", "").equals("true")) {
						songData.edit().putString("repeat", "one").commit();
						iv_Repeat.setImageResource(R.drawable.round_repeat_one_white_24dp);
					}
					else {
						songData.edit().putString("repeat", "false").commit();
						if (isDarkMode) {
							iv_Repeat.setImageResource(R.drawable.round_repeat_white_24dp);
						}
						else {
							iv_Repeat.setImageResource(R.drawable.round_repeat_white_24dp);
						}
					}
				}
			}
		});
		
		iv_PreviousFull.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Previous();
			}
		});
		
		iv_PlayFull.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Play_Pause();
			}
		});
		
		iv_NextFull.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Next();
			}
		});
		
		iv_Suffle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (songData.getString("shuffle", "").equals("false")) {
					songData.edit().putString("shuffle", "true").commit();
					iv_Suffle.setImageResource(R.drawable.round_shuffle_on_white_24dp);
				}
				else {
					songData.edit().putString("shuffle", "false").commit();
					if (isDarkMode) {
						iv_Suffle.setImageResource(R.drawable.round_shuffle_white_24dp);
					}
					else {
						iv_Suffle.setImageResource(R.drawable.round_shuffle_white_24dp);
					}
					songData.edit().putString("repeat", "false").commit();
					if (isDarkMode) {
						iv_Repeat.setImageResource(R.drawable.round_repeat_white_24dp);
					}
					else {
						iv_Repeat.setImageResource(R.drawable.round_repeat_white_24dp);
					}
				}
			}
		});
	}
	
	private void initializeLogic() {
		/*StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
*/
		isDarkMode = false;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =MusicActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF000000);
		}
		_ColorShadow_SDK28(ly_Toolbar, "#FF006FFF", 55);
		_ExpandableBottomSheet(bsb_Player);
		bsb_Player.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFF121212));
		tv_ToolbarTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 1);
		textSongTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 1);
		textArtist.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 0);
		textSongTitleFull.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 1);
		textArtistFull.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 0);
		textCurrentTime.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 0);
		textFullTime.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 0);
		FirstPlay = true;
		UserPause = false;
		Headphone = false;
		Current = 0;
		_Translate(Locale.getDefault().getDisplayLanguage());
		
		_startApp();
		/*
FileUtil.writeFile(FileUtil.getExternalStorageDir(), FileUtil.getExternalStorageDir());
*/
		rv_SongList.setAdapter(new Rv_SongListAdapter(All_Song_Data));
		rv_SongList.setLayoutManager(new LinearLayoutManager(this));
		_CheckSelf();
		if (songData.getString("accent", "").equals("")) {
			songData.edit().putString("accent", "#f44336").commit();
		}
		else {
			
		}
		accentColor = songData.getString("accent", "");
		if (songData.getString("theme", "").equals("")) {
			songData.edit().putString("theme", "white").commit();
			isDarkMode = false;
		}
		else {
			if (songData.getString("theme", "").equals("dark")) {
				isDarkMode = true;
			}
			else {
				isDarkMode = false;
			}
		}
		if (M.mp != null) {
			if (M.mp.isPlaying()) {
				if (isDarkMode) {
					iv_Play.setImageResource(R.drawable.round_pause_white_24dp);
					iv_PlayFull.setImageResource(R.drawable.round_pause_white_24dp);
				}
				else {
					iv_Play.setImageResource(R.drawable.round_pause_white_24dp);
					iv_PlayFull.setImageResource(R.drawable.round_pause_white_24dp);
				}
				calendar.setTimeInMillis((long)(M.mp.getDuration()));
				textFullTime.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
				seekProgress.setMax((int)M.mp.getDuration());
				seekProgressFull.setProgress((int)M.mp.getCurrentPosition());
				timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								calendar.setTimeInMillis((long)(M.mp.getCurrentPosition()));
								textCurrentTime.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
								seekProgress.setProgress((int)M.mp.getCurrentPosition());
								seekProgressFull.setProgress((int)M.mp.getCurrentPosition());
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(timer, (int)(0), (int)(400));
			}
			else {
				calendar.setTimeInMillis((long)(M.mp.getDuration()));
				textFullTime.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
				seekProgress.setProgress((int)M.mp.getCurrentPosition());
				seekProgressFull.setMax((int)M.mp.getDuration());
				calendar.setTimeInMillis((long)(M.mp.getCurrentPosition()));
				textCurrentTime.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
				seekProgress.setProgress((int)M.mp.getCurrentPosition());
				seekProgressFull.setProgress((int)M.mp.getCurrentPosition());
			}
		}
		textSongTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		textSongTitle.setMarqueeRepeatLimit(-1);
		textSongTitle.setSingleLine(true);
		textSongTitle.setSelected(true);
		list_json = new Gson().toJson(All_Song_Data);
		background_list = new Gson().fromJson(list_json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		if (songData.getString("repeat", "").equals("")) {
			songData.edit().putString("repeat", "false").commit();
		}
		else {
			if (songData.getString("repeat", "").equals("false")) {
				if (isDarkMode) {
					iv_Repeat.setImageResource(R.drawable.round_repeat_white_24dp);
				}
				else {
					iv_Repeat.setImageResource(R.drawable.round_repeat_white_24dp);
				}
			}
			else {
				if (songData.getString("repeat", "").equals("true")) {
					iv_Repeat.setImageResource(R.drawable.round_repeat_one_white_24dp);
				}
				else {
					iv_Repeat.setImageResource(R.drawable.round_repeat_white_24dp);
				}
			}
		}
		if (songData.getString("shuffle", "").equals("")) {
			songData.edit().putString("shuffle", "false").commit();
		}
		else {
			if (songData.getString("shuffle", "").equals("false")) {
				if (isDarkMode) {
					iv_Suffle.setImageResource(R.drawable.round_shuffle_white_24dp);
				}
				else {
					iv_Suffle.setImageResource(R.drawable.round_shuffle_white_24dp);
				}
			}
			else {
				iv_Suffle.setImageResource(R.drawable.round_shuffle_on_white_24dp);
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
	public void onStop() {
		super.onStop();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onBackPressed() {
		_MediaPlayer(M.mp);
		finish();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (!songData.getString("viewIndex", "").equals("")) {
			viewIndex = Double.parseDouble(songData.getString("viewIndex", ""));
			viewTop = Double.parseDouble(songData.getString("viewTop", ""));
			//listview1.setSelectionFromTop((int)viewIndex,(int) viewTop);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/*
songData.edit().putString("viewIndex", String.valueOf((long)(rv_SongList.getFirstVisiblePosition()))).commit();
View v = rv_SongList.getChildAt(0);
songData.edit().putString("viewTop", String.valueOf((long)((v == null) ? 0 : (v.getTop() - rv_SongList.getPaddingTop())))).commit();
*/
	}
	public void _ExpandableBottomSheet (final View _view) {
		try {
			BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(_view); bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
			
			// set callback for changes
			bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
				    @Override
				    public void onStateChanged(@NonNull View bottomSheet, int newState) {
					
					State = String.valueOf(newState);
					
					if (Double.parseDouble(State) == 3) {
						
						android.transition.TransitionManager.beginDelayedTransition(ly_Bg);
						ly_Toolbar.setVisibility(View.GONE);
							linearCurPlays.setVisibility(View.GONE);
						
						bsb_Player.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)0, 0xFF121212));
						
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
								Window w =MusicActivity.this.getWindow();
								w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
								w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF121212);
						}
						
						
					}
					else {
							if (Double.parseDouble(State) == 4) {
							
							android.transition.TransitionManager.beginDelayedTransition(ly_Bg);
							
							ly_Toolbar.setVisibility(View.VISIBLE);
							
							bsb_Player.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFF121212));
								linearCurPlays.setVisibility(View.VISIBLE);
							if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
									Window w =MusicActivity.this.getWindow();
									w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
									w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF000000);
							}
							
							
							}
					}
					
					        
					    }
				
				    @Override
				    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
					
					    }
			});
		} catch(Exception e) {
			 
		}
	}
	
	
	public void _ColorShadow_SDK28 (final View _view, final String _color, final double _number) {
		_view.setElevation((float)_number);
		
		_view.setOutlineAmbientShadowColor(Color.parseColor(_color));
		_view.setOutlineSpotShadowColor(Color.parseColor(_color));
	}
	
	
	public void _rippleRoundStroke (final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _FadeAnimation (final ImageView _view) {
		AlphaAnimation fadeIn=new AlphaAnimation(0,1);
		
		AlphaAnimation fadeOut=new AlphaAnimation(0,1);
		
		
		final AnimationSet set = new AnimationSet(false);
		
		set.addAnimation(fadeIn);
		fadeOut.setStartOffset(1000);
		set.setDuration(1500);
		_view.startAnimation(set);
		
		set.setAnimationListener(new Animation.AnimationListener() {
			    @Override
			    public void onAnimationStart(Animation animation) { }
			    @Override
			    public void onAnimationRepeat(Animation animation) { }
			    @Override
			    public void onAnimationEnd(Animation animation) { }
		});
	}
	
	
	public void _Previous () {
		if (showFavorite) {
			//((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			if(M.mp == null){
				M.mp=new MediaPlayer();
			}else{
				M.mp.pause();
				M.mp.reset();
				if (FavoriteMap.size() == 0) {
					SketchwareUtil.showMessage(getApplicationContext(), "List lenght from 0");
				}
				else {
					if (songData.getString("shuffle", "").equals("true")) {
						_playWithPosition(SketchwareUtil.getRandom((int)(0), (int)(FavoriteMap.size() - 1)));
					}
					else {
						if (songData.getString("repeat", "").equals("true")) {
							if (lastPosition == (FavoriteMap.size() + 1)) {
								_playWithPosition(0);
							}
							else {
								_playWithPosition(lastPosition - 1);
							}
						}
						else {
							if (songData.getString("repeat", "").equals("one")) {
								_playWithPosition(lastPosition);
							}
							else {
								song--;
								if (song > -1) {
									filepath = FavoriteMap.get((int)song).get("data").toString();
									try {
										if (M.mp.isPlaying()) {
											M.mp.reset();
											M.mp.prepare();
										}else{
											M.mp.setDataSource(filepath);
											M.mp.prepare();
											M.mp.start();
										}
									} catch (Exception e) {
									}
									songData.edit().putString("lastPath", FavoriteMap.get((int)song).get("data").toString()).commit();
									songData.edit().putString("lastPosition", String.valueOf((long)(song))).commit();
									_mp_move();
								}
								else {
									song = 0;
									SketchwareUtil.showMessage(getApplicationContext(), "Top of list");
								}
							}
						}
					}
				}
			}
			/*
((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
*/
		}
		else {
			//((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			if(M.mp == null){
				M.mp=new MediaPlayer();
			}else{
				M.mp.pause();
				M.mp.reset();
				if (All_Song_Data.size() == 0) {
					SketchwareUtil.showMessage(getApplicationContext(), "List lenght from 0");
				}
				else {
					if (songData.getString("shuffle", "").equals("true")) {
						_playWithPosition(SketchwareUtil.getRandom((int)(0), (int)(All_Song_Data.size() - 1)));
					}
					else {
						if (songData.getString("repeat", "").equals("true")) {
							if (lastPosition == (All_Song_Data.size() + 1)) {
								_playWithPosition(0);
							}
							else {
								_playWithPosition(lastPosition - 1);
							}
						}
						else {
							if (songData.getString("repeat", "").equals("one")) {
								_playWithPosition(lastPosition);
							}
							else {
								song--;
								if (song > -1) {
									filepath = All_Song_Data.get((int)song).get("data").toString();
									try {
										if (M.mp.isPlaying()) {
											M.mp.reset();
											M.mp.prepare();
										}else{
											M.mp.setDataSource(filepath);
											M.mp.prepare();
											M.mp.start();
										}
									} catch (Exception e) {
									}
									songData.edit().putString("lastPath", All_Song_Data.get((int)song).get("data").toString()).commit();
									songData.edit().putString("lastPosition", String.valueOf((long)(song))).commit();
									_mp_move();
								}
								else {
									song = 0;
									SketchwareUtil.showMessage(getApplicationContext(), "Top of list");
								}
							}
						}
					}
				}
			}
			/*
((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
*/
		}
	}
	
	
	public void _Play_Pause () {
		//((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		if(M.mp != null){
			if (M.mp.isPlaying()) {
				iv_Play.setImageResource(R.drawable.round_play_arrow_white_24dp);
				iv_PlayFull.setImageResource(R.drawable.round_play_arrow_white_24dp);
				M.mp.pause();
			}
			else {
				iv_Play.setImageResource(R.drawable.round_pause_white_24dp);
				iv_PlayFull.setImageResource(R.drawable.round_pause_white_24dp);
				M.mp.start();
			}
			/*
((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
*/
		}else {
			if (songData.getString("lastPath", "").equals("")) {
				
			}
			else {
				_playWithPosition(Double.parseDouble(songData.getString("lastPosition", "")));
			}
		}
	}
	
	
	public void _Next () {
		//((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		if(M.mp == null){
			M.mp=new MediaPlayer();
		}else{
			M.mp.pause();
			M.mp.reset();
			if (All_Song_Data.size() == 0) {
				SketchwareUtil.showMessage(getApplicationContext(), "List lenght from 0");
			}
			else {
				if (songData.getString("shuffle", "").equals("true")) {
					_playWithPosition(SketchwareUtil.getRandom((int)(0), (int)(All_Song_Data.size() - 1)));
				}
				else {
					if (songData.getString("repeat", "").equals("true")) {
						if (lastPosition == (All_Song_Data.size() - 1)) {
							_playWithPosition(0);
						}
						else {
							_playWithPosition(lastPosition + 1);
						}
					}
					else {
						if (songData.getString("repeat", "").equals("one")) {
							_playWithPosition(lastPosition);
						}
						else {
							song++;
							if (song < All_Song_Data.size()) {
								filepath = All_Song_Data.get((int)song).get("data").toString();
								try {
									if (M.mp.isPlaying()) {
										M.mp.reset();
										M.mp.prepare();
									}else{
										M.mp.setDataSource(filepath);
										M.mp.prepare();
										M.mp.start();
									}
								} catch (Exception e) {
								}
								songData.edit().putString("lastPath", All_Song_Data.get((int)song).get("data").toString()).commit();
								songData.edit().putString("lastPosition", String.valueOf((long)(song))).commit();
								_mp_move();
							}
							else {
								song = All_Song_Data.size();
								SketchwareUtil.showMessage(getApplicationContext(), "end of list");
							}
						}
					}
				}
			}
		}
		
		/*
((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
*/
	}
	
	
	public void _Translate (final String _lang) {
		if (_lang.toUpperCase().contains("ESP")) {
			Translates.add("Musica");
			Translates.add("Reproducir en bucle la lista");
			Translates.add("Reproducir en bucle la canción");
			Translates.add("Reproducción en lista");
			Translates.add("Reproducción aleatoria");
			Translates.add("Información");
			Translates.add("Lista de reproducción");
			Translates.add("Configuración");
		}
		else {
			if (_lang.toUpperCase().contains("ENG")) {
				Translates.add("Quran");
				Translates.add("Loop Quran");
				Translates.add("Loop Play the Sura");
				Translates.add("Quran Playback");
				Translates.add("Shuffle Playback");
				Translates.add("Information");
				Translates.add("Quran");
				Translates.add("Configuration");
			}
			else {
				Translates.add("Quran");
				Translates.add("Loop Quran");
				Translates.add("Loop Play the Sura");
				Translates.add("Quran Playback");
				Translates.add("Shuffle Playback");
				Translates.add("Information");
				Translates.add("Quran");
				Translates.add("Configuration");
			}
		}
		tv_ToolbarTitle.setText(Translates.get((int)(0)));
	}
	
	
	public void _MediaPlayer (final MediaPlayer _Mp) {
		if (_Mp != null) {
			_Mp.stop();
		}
	}
	
	
	public void _CheckSelf () {
		if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
			permission.setTitle("Note Permissions");
			permission.setMessage("Read permission required, do you allow it?");
			permission.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
				}
			});
			permission.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					SketchwareUtil.showMessage(getApplicationContext(), "Obs files could not be retrieved not allowed");
				}
			});
			permission.setCancelable(false);
			permission.create().show();
		}else {
			getAllSongData();
		}
	}
	
	
	public void _AllData () {
		path = "Music";
		/*

*/
	}
	public void getAllSongData() { 
		
		sura = new Gson().fromJson("[\n  \"Surah Al-Baqarah\",\n  \"Surah Al-Faatihah\",\n  \"Surah Ali-'Imran\",\n  \"Surah An-Nisaa\",\n  \"Surah Al-Maaidah\",\n  \"Surah Al-An'aam\",\n  \"Surah Al-A'raaf\",\n  \"Surah Al-Anfaal\",\n  \"Surah At-Taubah\",\n  \"Surah Yunus\",\n  \"Surah Huud\",\n  \"Surah Yusuf\",\n  \"Surah Ar-Ra'd\",\n  \"Suarh Ibrahim\",\n  \"Surah Al-Hijr\",\n  \"Surah An-Nahl\",\n  \"Surah Al-Israa\",\n  \"Surah Al-Qaif\",\n  \"Surah Maryam\",\n  \"Surah Thaahaa\",\n  \"Surah Al-Anbiyaa\",\n  \"Surah Al-Hajj\",\n  \"Surah Al-Mu'minuun\",\n  \"Surah An-Nuur\",\n  \"Surah Al-Furqaan\",\n  \"Surah Asy-Syu'arra\",\n  \"Surah An-Naml\",\n  \"Surah Al-Qashash\",\n  \"Surah Al-Ankabuut\",\n  \"Surah Ar-Ruum\",\n  \"Surah Luqman\",\n  \"Surah As-Sajdah\",\n  \"Surah Al-Ahzaab\",\n  \"Surah Saba\",\n  \"Surah Faathir\",\n  \"Surah Yaasiin\",\n  \"Surah Ash-Shaffaat\",\n  \"Surah Shaad\",\n  \"Surah Az-Zumar\",\n  \"Surah Mu'min\",\n  \"Surah Fushshilat\",\n  \"Surah Asy-Syuura\",\n  \"Surah Az-Zukhruf\",\n  \"Surah Ad-Dukhaan\",\n  \"Surah Al-Jaatsiyah\",\n  \"Surah Al-Ahqaaf\",\n  \"Surah Muhammad\",\n  \"Surah Al-Fath\",\n  \"Surah Al-Hujuraat\",\n  \"Surah Qaaf\",\n  \"Surah Adz-Dzaariyaat\",\n  \"Surah Ath- Thuur\",\n  \"Surah An-Najm\",\n  \"Surah Al-Qamar\",\n  \"Surah Ar-Rahmaan\",\n  \"Surah Al-Waaqi'ah\",\n  \"Shrah Al-Hadiid\",\n  \"Surah Al-mujaadilah\",\n  \"Surah Al-Hasyr\",\n  \"Surah Al-Mumtahanah\",\n  \"Surah Ash-Shaaf\",\n  \"Surah Al-Jumu'ah\",\n  \"Surah Al-Munaafiquun\",\n  \"Surah At -Taghaabun\",\n  \"Surah Ath-Thalaaq\",\n  \"Surah At-Tahriim\",\n  \"Surah Al-Mulk \",\n  \"Surah Al-Mulk\",\n  \"Surah Nun\",\n  \"Surah Al-Haaqqah\",\n  \"Surah Nuh\",\n  \"Surah Al-Jin\",\n  \"Surah Al-Muzzammil\",\n  \"Surah Al-Muddatstsir\",\n  \"Surah Al-Qiyaamah\",\n  \"Surah Al-Insaan\",\n  \"Surah Al-Mursalaat\",\n  \"Surah An-Nabaa\",\n  \"Surah An-Naazi'aat\",\n  \"Surah Abasa\",\n  \"Surah At -Takwiir\",\n  \"Surah Al-Infithaar\",\n  \"Surah Al-Muthaffifiin\",\n  \"Surah Al-Insyiqaaq\",\n  \"Surah Al-Buruuj\",\n  \"Surah Al-A'laa\",\n  \"Surah Al-Ghaasyiyah\",\n  \"Surah Al-Fajir\",\n  \"Surah Al-Balad\",\n  \"Surah Asy-syams\",\n  \"Surah Al-Lail\",\n  \"Surah Adh-Dhuhaa\",\n  \"Surah Al-Insyiraah\",\n  \"Surah At -Tiin\",\n  \"Surah Al-Alaq\",\n  \"Surah Al-Qadr\",\n  \"Surah Al-Bayyinah\",\n  \"Surah Al-Zalzalah\",\n  \"Surah Al-Aadiyaat\",\n  \"Surah Al-Qaari'Ah\",\n  \"Surah At-Takaatsur\",\n  \"Surah Al-Ashr\",\n  \"Surah Al-Humazah\",\n  \"Surah Al-Fiil\",\n  \"Surah Quraisy\",\n  \"Surah Al-Maa'Uun\",\n  \"Surah Al-Kautsar\",\n  \"Surah Al-Kaafiruun\",\n  \"Surah Al-Nashr\",\n  \"Surah Al-Lahab\",\n  \"Surah Al- Ikhlaash \",\n  \"Surah Al-Falaq\"\n]\n", new TypeToken<ArrayList<String>>(){}.getType());
		//showMessage("GettingAll");
		String[] projection = {
				android.provider.MediaStore.Audio.Media._ID, 		android.provider.MediaStore.Audio.AudioColumns.ALBUM,
				android.provider.MediaStore.Audio.AudioColumns.ALBUM_KEY, 
				android.provider.MediaStore.Audio.AudioColumns.ARTIST,
				android.provider.MediaStore.Audio.AudioColumns.DATA,
				android.provider.MediaStore.Audio.AudioColumns.TITLE,
				android.provider.MediaStore.Audio.AudioColumns.DURATION,
				
				android.provider.MediaStore.Audio.AudioColumns.ALBUM_ID,
		};
		
		String data = android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
		
		android.net.Uri uri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI; 
		
		/*cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
null,
android.provider.MediaStore.Audio.Media.DATA + " LIKE ? AND " + android.provider.MediaStore.Audio.Media.DATA + " NOT LIKE ?",
new String[]{path + "%", path + "%/%"},
android.provider.MediaStore.Audio.Media.DISPLAY_NAME + " ASC");

*/
		/*String selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " +
android.provider.MediaStore.Audio.Media.DATA + " LIKE '"+path+"/%'";
cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
null,
selection,
null, null);*/
		
		cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
		projection,
		android.provider.MediaStore.Audio.AudioColumns.DATA + " like ? ",
		new String[]{"%kurani_kerim%"}, // Put your device folder / file location here.
		null);
		/*cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
projection,

null, null,null);*/
		getAlbumColumnData(cursor);
	} 
	{
	}
	public static android.database.Cursor cursor;
	public static int music_column_index;
	
	private void getAlbumColumnData(android.database.Cursor cur) {
			try {
					if (cur.moveToFirst()) {
							int _id;
							String name;
							String data;
							String artist;
							String album;
							String songs_duration;
							
							do {
									
									name = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.TITLE));
									data = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.DATA));
									
									artist = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ARTIST));
									
									album = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ALBUM));
									//songs_duration = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.DURATION));
									//art = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ALBUM_ID));
									
									
									{
											HashMap<String, Object> _item = new HashMap<>();
											if(name.matches("\\d+(?:\\.\\d+)?"))
											{
													_item.put("album", "Al Quran");
													_item.put("name", sura.get((int)Double.parseDouble(name)).toString());
													_item.put("data", data);
													_item.put("artist", "Saud Al-Shuraim");
													_item.put("favorite", "false");
													
													
													All_Song_Data.add( _item);
											}
											else
											{
													
											}
											
											
											
									}
									
									
							} while (cur.moveToNext());}
			} catch (Exception e) {
					e.printStackTrace();
			}
	}
	
	{
	}
	private String setCorrectDuration(String songs_duration) {
						// TODO Auto-generated method stub
				
						if(Integer.valueOf(songs_duration) != null) {
									int time = Integer.valueOf(songs_duration);
						
									int seconds = time/1000;
									int minutes = seconds/60;
									seconds = seconds % 60;
						
									if(seconds<10) {
												songs_duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
								song_duration = songs_duration;
									} else {
												songs_duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
								song_duration = songs_duration;
									}
									return songs_duration;
						}
						return null;
			}
	{
	}
	
	
	public void _startApp () {
		oldPosition = -1;
		if (songData.getString("lastPosition", "").equals("")) {
			song = -1;
		}
		else {
			song = Double.parseDouble(songData.getString("lastPosition", ""));
		}
	}
	
	
	public void _Animator (final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.start();
	}
	
	
	public void _setClass () {
	}
	static class M{
		static Context context;
		static MediaPlayer mp;
	}
	
	private void shareFile(java.io.File file) { Intent intentShareFile = new Intent(Intent.ACTION_SEND); intentShareFile.setType(java.net.URLConnection.guessContentTypeFromName(file.getName())); intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file.getAbsolutePath())); //if you need //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject); //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description"); startActivity(Intent.createChooser(intentShareFile, "Share File")); 
	}
	
	
	public void _setText (final View _view, final String _text) {
		TextView view = (TextView)_view;
		
		view.setText(_text);
	}
	
	
	public void _playWithPosition (final double _position) {
		song = _position;
		if (!(_position < 0)) {
			songData.edit().putString("lastPath", All_Song_Data.get((int)_position).get("data").toString()).commit();
			songData.edit().putString("lastPosition", String.valueOf((long)(_position))).commit();
			lastPosition = Double.parseDouble(songData.getString("lastPosition", ""));
			//((BaseAdapter)rv_SongList.getAdapter()).notifyDataSetChanged();
			if(M.mp == null){
				M.mp=new MediaPlayer();
			}else{
				M.mp.pause();
				M.mp.reset();
			}
			filepath = All_Song_Data.get((int)_position).get("data").toString();
			try {
				if (M.mp.isPlaying()) {
					M.mp.pause();
				}else{
					iv_Play.setImageResource(R.drawable.round_pause_white_24dp);
					iv_PlayFull.setImageResource(R.drawable.round_pause_white_24dp);
					M.mp.setDataSource(filepath);
					M.mp.prepare();
					M.mp.start();
					_mp_move();
				}
			} catch (java.io.IOException e) { }
		}
	}
	
	
	public void _mp_move () {
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
		if (M.mp != null){
			/*MediaMetadataRetriever mr = new MediaMetadataRetriever();
mr.setDataSource(filepath);
try{
byte[] art = mr.getEmbeddedPicture();
BitmapFactory.Options opt = new BitmapFactory.Options();
opt.inSampleSize = 2;
Bitmap songImage = BitmapFactory.decodeByteArray(art,0,art.length,opt);

album.setImageBitmap(songImage);

}catch (java.lang.Exception e){
album.setImageResource(R.drawable.icon_large_white);
}*/
			textSongTitleFull.setText(All_Song_Data.get((int)song).get("name").toString());
			textSongTitle.setText(All_Song_Data.get((int)song).get("name").toString());
			if (All_Song_Data.get((int)song).containsKey("artist")) {
				textArtist.setText(All_Song_Data.get((int)song).get("artist").toString());
				textArtistFull.setText(All_Song_Data.get((int)song).get("artist").toString());
			}
			else {
				textArtist.setText("Unknown");
				textArtistFull.setText("Unknown");
			}
			/*
if (All_Song_Data.get((int)song).containsKey("album")) {
text_album.setText(All_Song_Data.get((int)song).get("album").toString());
text_album.setText(All_Song_Data.get((int)song).get("album").toString());
}
else {
text_album.setText("Unknown");
text_album.setText("Unknown");
}
*/
			calendar.setTimeInMillis((long)(M.mp.getDuration()));
			textFullTime.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
			seekProgress.setMax((int)M.mp.getDuration());
			seekProgressFull.setMax((int)M.mp.getDuration());
			timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							calendar.setTimeInMillis((long)(M.mp.getCurrentPosition()));
							textCurrentTime.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
							seekProgress.setProgress((int)M.mp.getCurrentPosition());
							seekProgressFull.setProgress((int)M.mp.getCurrentPosition());
						}
					});
				}
			};
			_timer.scheduleAtFixedRate(timer, (int)(400), (int)(400));
		}else {
			SketchwareUtil.showMessage(getApplicationContext(), "Not: Media is not found expention");
		}
		M.mp.setOnCompletionListener (new MediaPlayer.OnCompletionListener (
		) {
			public void
			 onCompletion (MediaPlayer theMediaPlayer) {
				if(M.mp == null){
					M.mp=new MediaPlayer();
				}else{
					M.mp.pause(); M.mp.reset();
					if (!(All_Song_Data.size() == 0)) {
						if (songData.getString("shuffle", "").equals("true")) {
							_playWithPosition(SketchwareUtil.getRandom((int)(lastPosition), (int)(All_Song_Data.size() - 1)));
						}
						else {
							if (songData.getString("repeat", "").equals("true")) {
								if (lastPosition == (All_Song_Data.size() - 1)) {
									_playWithPosition(0);
								}
								else {
									_playWithPosition(lastPosition + 1);
								}
							}
							else {
								if (songData.getString("repeat", "").equals("one")) {
									_playWithPosition(lastPosition);
								}
								else {
									song++;
									if (song < All_Song_Data.size()) {
										filepath = All_Song_Data.get((int)song).get("data").toString();
										try {
											if (M.mp.isPlaying()) {
												M.mp.reset();
												M.mp.prepare();
											}else{
												M.mp.setDataSource(filepath);
												M.mp.prepare();
												M.mp.start();
											}
										} catch (Exception e) {
										}
										/*
((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
*/
										_mp_move();
									}
									else {
										song = All_Song_Data.size();
									}
								}
							}
						}
					}
				}
			}});
		M.mp.setOnErrorListener(new MediaPlayer.OnErrorListener(){
			
							@Override
							public boolean onError(MediaPlayer p1, int p2, int p3)
							{
				if (!(All_Song_Data.size() == 0)) {
					if (!((song == All_Song_Data.size()) || (song == 0))) {
						media_error_dialog.setTitle("Media Error");
						media_error_dialog.setMessage("Unfortunately this file is corrupt, media cannot be played");
						media_error_dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						media_error_dialog.create().show();
					}
					else {
						
					}
				}
				M.mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
					@Override
					public void onPrepared(MediaPlayer mp){
					}
				});
				return true;
			}
		});
	}
	
	
	public void _GradientDrawable (final View _view, final double _radius, final double _stroke, final double _shadow, final String _color, final String _borderColor, final boolean _ripple, final boolean _clickAnim, final double _animDuration) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			if (Build.VERSION.SDK_INT >= 21){
				_view.setElevation((int)_shadow);}
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9e9e9e")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			_view.setBackground(gd);
			if (Build.VERSION.SDK_INT >= 21){
				_view.setElevation((int)_shadow);}
		}
		if (_clickAnim) {
			_view.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()){
						case MotionEvent.ACTION_DOWN:{
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues(0.9f);
							scaleX.setDuration((int)_animDuration);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues(0.9f);
							scaleY.setDuration((int)_animDuration);
							scaleY.start();
							break;
						}
						case MotionEvent.ACTION_UP:{
							
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues((float)1);
							scaleX.setDuration((int)_animDuration);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues((float)1);
							scaleY.setDuration((int)_animDuration);
							scaleY.start();
							
							break;
						}
					}
					return false;
				}
			});
		}
	}
	
	
	public class Rv_SongListAdapter extends RecyclerView.Adapter<Rv_SongListAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Rv_SongListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout ly_Bg = (LinearLayout) _view.findViewById(R.id.ly_Bg);
			final LinearLayout ly_Info = (LinearLayout) _view.findViewById(R.id.ly_Info);
			final TextView tv_Title = (TextView) _view.findViewById(R.id.tv_Title);
			final TextView tv_SubTitle = (TextView) _view.findViewById(R.id.tv_SubTitle);
			
			tv_Title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 0);
			tv_SubTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/psr.ttf"), 0);
			tv_Title.setText(_data.get((int)_position).get("name").toString());
			tv_SubTitle.setText(" ".concat(_data.get((int)_position).get("artist").toString()).concat(" - ").concat(_data.get((int)_position).get("album").toString()));
			ly_Bg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_playWithPosition(_position);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
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