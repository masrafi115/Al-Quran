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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.graphics.Typeface;
import androidx.media.*;
import com.chibde.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class SuraIndexFragmentActivity extends  Fragment  { 
	
	
	private String json = "";
	
	private ArrayList<HashMap<String, Object>> quran_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> quran_page = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private Intent i = new Intent();
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.sura_index_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
		listview1 = (ListView) _view.findViewById(R.id.listview1);
	}
	
	private void initializeLogic() {
		try {
			  java.io.InputStream is = getActivity().getAssets().open("contents/QuranJsonNew.json");
			           int size = is.available();
			           byte[] buffer = new byte[size];
			           is.read(buffer);
			           is.close();
			           json = new String(buffer, "UTF-8");
			 //File f = new File(is,"demin");
			quran_list = new Gson().fromJson(json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			quran_page = new Gson().fromJson("[\n  {\n    \"page\": \"0\",\n    \"caption\": \"Surah Al-Baqarah\"\n  },\n  {\n    \"page\": \"0\",\n    \"caption\": \"Surah Al-Faatihah\"\n  },\n  {\n    \"page\": \"28\",\n    \"caption\": \"Surah Ali-'Imran\"\n  },\n  {\n    \"page\": \"52\",\n    \"caption\": \"Surah An-Nisaa\"\n  },\n  {\n    \"page\": \"79\",\n    \"caption\": \"Surah Al-Maaidah\"\n  },\n  {\n    \"page\": \"98\",\n    \"caption\": \"Surah Al-An'aam\"\n  },\n  {\n    \"page\": \"118\",\n    \"caption\": \"Surah Al-A'raaf\"\n  },\n  {\n    \"page\": \"142\",\n    \"caption\": \"Surah Al-Anfaal\"\n  },\n  {\n    \"page\": \"150\",\n    \"caption\": \"Surah At-Taubah\"\n  },\n  {\n    \"page\": \"169\",\n    \"caption\": \"Surah Yunus\"\n  },\n  {\n    \"page\": \"181\",\n    \"caption\": \"Surah Huud\"\n  },\n  {\n    \"page\": \"195\",\n    \"caption\": \"Surah Yusuf\"\n  },\n  {\n    \"page\": \"208\",\n    \"caption\": \"Surah Ar-Ra'd\"\n  },\n  {\n    \"page\": \"214\",\n    \"caption\": \"Suarh Ibrahim\"\n  },\n  {\n    \"page\": \"219\",\n    \"caption\": \"Surah Al-Hijr\"\n  },\n  {\n    \"page\": \"224\",\n    \"caption\": \"Surah An-Nahl\"\n  },\n  {\n    \"page\": \"238\",\n    \"caption\": \"Surah Al-Israa\"\n  },\n  {\n    \"page\": \"248\",\n    \"caption\": \"Surah Al-Qaif\"\n  },\n  {\n    \"page\": \"258\",\n    \"caption\": \"Surah Maryam\"\n  },\n  {\n    \"page\": \"265\",\n    \"caption\": \"Surah Thaahaa\"\n  },\n  {\n    \"page\": \"273\",\n    \"caption\": \"Surah Al-Anbiyaa\"\n  },\n  {\n    \"page\": \"282\",\n    \"caption\": \"Surah Al-Hajj\"\n  },\n  {\n    \"page\": \"291\",\n    \"caption\": \"Surah Al-Mu'minuun\"\n  },\n  {\n    \"page\": \"298\",\n    \"caption\": \"Surah An-Nuur\"\n  },\n  {\n    \"page\": \"308\",\n    \"caption\": \"Surah Al-Furqaan\"\n  },\n  {\n    \"page\": \"313\",\n    \"caption\": \"Surah Asy-Syu'arra\"\n  },\n  {\n    \"page\": \"322\",\n    \"caption\": \"Surah An-Naml\"\n  },\n  {\n    \"page\": \"330\",\n    \"caption\": \"Surah Al-Qashash\"\n  },\n  {\n    \"page\": \"340\",\n    \"caption\": \"Surah Al-Ankabuut\"\n  },\n  {\n    \"page\": \"347\",\n    \"caption\": \"Surah Ar-Ruum\"\n  },\n  {\n    \"page\": \"353\",\n    \"caption\": \"Surah Luqman\"\n  },\n  {\n    \"page\": \"356\",\n    \"caption\": \"Surah As-Sajdah\"\n  },\n  {\n    \"page\": \"359\",\n    \"caption\": \"Surah Al-Ahzaab\"\n  },\n  {\n    \"page\": \"368\",\n    \"caption\": \"Surah Saba\"\n  },\n  {\n    \"page\": \"374\",\n    \"caption\": \"Surah Faathir\"\n  },\n  {\n    \"page\": \"379\",\n    \"caption\": \"Surah Yaasiin\"\n  },\n  {\n    \"page\": \"384\",\n    \"caption\": \"Surah Ash-Shaffaat\"\n  },\n  {\n    \"page\": \"391\",\n    \"caption\": \"Surah Shaad\"\n  },\n  {\n    \"page\": \"395\",\n    \"caption\": \"Surah Az-Zumar\"\n  },\n  {\n    \"page\": \"403\",\n    \"caption\": \"Surah Mu'min\"\n  },\n  {\n    \"page\": \"412\",\n    \"caption\": \"Surah Fushshilat\"\n  },\n  {\n    \"page\": \"417\",\n    \"caption\": \"Surah Asy-Syuura\"\n  },\n  {\n    \"page\": \"423\",\n    \"caption\": \"Surah Az-Zukhruf\"\n  },\n  {\n    \"page\": \"429\",\n    \"caption\": \"Surah Ad-Dukhaan\"\n  },\n  {\n    \"page\": \"431\",\n    \"caption\": \"Surah Al-Jaatsiyah\"\n  },\n  {\n    \"page\": \"435\",\n    \"caption\": \"Surah Al-Ahqaaf\"\n  },\n  {\n    \"page\": \"439\",\n    \"caption\": \"Surah Muhammad\"\n  },\n  {\n    \"page\": \"443\",\n    \"caption\": \"Surah Al-Fath\"\n  },\n  {\n    \"page\": \"446\",\n    \"caption\": \"Surah Al-Hujuraat\"\n  },\n  {\n    \"page\": \"449\",\n    \"caption\": \"Surah Qaaf\"\n  },\n  {\n    \"page\": \"451\",\n    \"caption\": \"Surah Adz-Dzaariyaat\"\n  },\n  {\n    \"page\": \"454\",\n    \"caption\": \"Surah Ath- Thuur\"\n  },\n  {\n    \"page\": \"456\",\n    \"caption\": \"Surah An-Najm\"\n  },\n  {\n    \"page\": \"458\",\n    \"caption\": \"Surah Al-Qamar\"\n  },\n  {\n    \"page\": \"461\",\n    \"caption\": \"Surah Ar-Rahmaan\"\n  },\n  {\n    \"page\": \"464\",\n    \"caption\": \"Surah Al-Waaqi'ah\"\n  },\n  {\n    \"page\": \"467\",\n    \"caption\": \"Shrah Al-Hadiid\"\n  },\n  {\n    \"page\": \"471\",\n    \"caption\": \"Surah Al-mujaadilah\"\n  },\n  {\n    \"page\": \"474\",\n    \"caption\": \"Surah Al-Hasyr\"\n  },\n  {\n    \"page\": \"478\",\n    \"caption\": \"Surah Al-Mumtahanah\"\n  },\n  {\n    \"page\": \"480\",\n    \"caption\": \"Surah Ash-Shaaf\"\n  },\n  {\n    \"page\": \"482\",\n    \"caption\": \"Surah Al-Jumu'ah\"\n  },\n  {\n    \"page\": \"483\",\n    \"caption\": \"Surah Al-Munaafiquun\"\n  },\n  {\n    \"page\": \"485\",\n    \"caption\": \"Surah At -Taghaabun\"\n  },\n  {\n    \"page\": \"487\",\n    \"caption\": \"Surah Ath-Thalaaq\"\n  },\n  {\n    \"page\": \"489\",\n    \"caption\": \"Surah At-Tahriim\"\n  },\n  {\n    \"page\": \"491\",\n    \"caption\": \"Surah Al-Mulk \"\n  },\n  {\n    \"page\": \"492\",\n    \"caption\": \"Surah Al-Mulk\"\n  },\n  {\n    \"page\": \"493\",\n    \"caption\": \"Surah Nun\"\n  },\n  {\n    \"page\": \"495\",\n    \"caption\": \"Surah Al-Haaqqah\"\n  },\n  {\n    \"page\": \"499\",\n    \"caption\": \"Surah Nuh\"\n  },\n  {\n    \"page\": \"501\",\n    \"caption\": \"Surah Al-Jin\"\n  },\n  {\n    \"page\": \"503\",\n    \"caption\": \"Surah Al-Muzzammil\"\n  },\n  {\n    \"page\": \"504\",\n    \"caption\": \"Surah Al-Muddatstsir\"\n  },\n  {\n    \"page\": \"506\",\n    \"caption\": \"Surah Al-Qiyaamah\"\n  },\n  {\n    \"page\": \"507\",\n    \"caption\": \"Surah Al-Insaan\"\n  },\n  {\n    \"page\": \"509\",\n    \"caption\": \"Surah Al-Mursalaat\"\n  },\n  {\n    \"page\": \"511\",\n    \"caption\": \"Surah An-Nabaa\"\n  },\n  {\n    \"page\": \"512\",\n    \"caption\": \"Surah An-Naazi'aat\"\n  },\n  {\n    \"page\": \"513\",\n    \"caption\": \"Surah Abasa\"\n  },\n  {\n    \"page\": \"515\",\n    \"caption\": \"Surah At -Takwiir\"\n  },\n  {\n    \"page\": \"516\",\n    \"caption\": \"Surah Al-Infithaar\"\n  },\n  {\n    \"page\": \"517\",\n    \"caption\": \"Surah Al-Muthaffifiin\"\n  },\n  {\n    \"page\": \"519\",\n    \"caption\": \"Surah Al-Insyiqaaq\"\n  },\n  {\n    \"page\": \"520\",\n    \"caption\": \"Surah Al-Buruuj\"\n  },\n  {\n    \"page\": \"521\",\n    \"caption\": \"Surah Al-A'laa\"\n  },\n  {\n    \"page\": \"522\",\n    \"caption\": \"Surah Al-Ghaasyiyah\"\n  },\n  {\n    \"page\": \"523\",\n    \"caption\": \"Surah Al-Fajir\"\n  },\n  {\n    \"page\": \"524\",\n    \"caption\": \"Surah Al-Balad\"\n  },\n  {\n    \"page\": \"525\",\n    \"caption\": \"Surah Asy-syams\"\n  },\n  {\n    \"page\": \"526\",\n    \"caption\": \"Surah Al-Lail\"\n  },\n  {\n    \"page\": \"527\",\n    \"caption\": \"Surah Adh-Dhuhaa\"\n  },\n  {\n    \"page\": \"528\",\n    \"caption\": \"Surah Al-Insyiraah\"\n  },\n  {\n    \"page\": \"529\",\n    \"caption\": \"Surah At -Tiin\"\n  },\n  {\n    \"page\": \"530\",\n    \"caption\": \"Surah Al-Alaq\"\n  },\n  {\n    \"page\": \"531\",\n    \"caption\": \"Surah Al-Qadr\"\n  },\n  {\n    \"page\": \"532\",\n    \"caption\": \"Surah Al-Bayyinah\"\n  },\n  {\n    \"page\": \"533\",\n    \"caption\": \"Surah Al-Zalzalah\"\n  },\n  {\n    \"page\": \"534\",\n    \"caption\": \"Surah Al-Aadiyaat\"\n  },\n  {\n    \"page\": \"535\",\n    \"caption\": \"Surah Al-Qaari'Ah\"\n  },\n  {\n    \"page\": \"536\",\n    \"caption\": \"Surah At-Takaatsur\"\n  },\n  {\n    \"page\": \"537\",\n    \"caption\": \"Surah Al-Ashr\"\n  },\n  {\n    \"page\": \"538\",\n    \"caption\": \"Surah Al-Humazah\"\n  },\n  {\n    \"page\": \"539\",\n    \"caption\": \"Surah Al-Fiil\"\n  },\n  {\n    \"page\": \"540\",\n    \"caption\": \"Surah Quraisy\"\n  },\n  {\n    \"page\": \"541\",\n    \"caption\": \"Surah Al-Maa'Uun\"\n  },\n  {\n    \"page\": \"542\",\n    \"caption\": \"Surah Al-Kautsar\"\n  },\n  {\n    \"page\": \"543\",\n    \"caption\": \"Surah Al-Kaafiruun\"\n  },\n  {\n    \"page\": \"544\",\n    \"caption\": \"Surah Al-Nashr\"\n  },\n  {\n    \"page\": \"545\",\n    \"caption\": \"Surah Al-Lahab\"\n  },\n  {\n    \"page\": \"546\",\n    \"caption\": \"Surah Al- Ikhlaash \"\n  },\n  {\n    \"page\": \"548\",\n    \"caption\": \"Surah Al-Falaq\"\n  }\n]\n", new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			if (quran_list.size() > 0) {
				listview1.setAdapter(new Listview1Adapter(quran_list));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		} catch(Exception e) {
			
			listview1.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _setRipple (final View _view) {
		TypedValue typedValue = new TypedValue();
		
		getContext().getTheme().resolveAttribute(16843868, typedValue, true);
		
		_view.setBackgroundResource(typedValue.resourceId);
		
		_view.setClickable(true);
	}
	
	
	public void _click_effect (final View _view, final String _c) {
		_view.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(_c)));
		_view.setClickable(true);
		
	}
	
	public static class Drawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[8];
			        Arrays.fill(outerRadii, 0);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
			    }
	}
	public static class CircleDrawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[180];
			        Arrays.fill(outerRadii, 80);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
		}
	}
	
	public void drawableclass() {
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
			LayoutInflater _inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.list_sura, null);
			}
			
			final LinearLayout linear11 = (LinearLayout) _view.findViewById(R.id.linear11);
			final LinearLayout linear14 = (LinearLayout) _view.findViewById(R.id.linear14);
			final LinearLayout linear13 = (LinearLayout) _view.findViewById(R.id.linear13);
			final TextView textview12 = (TextView) _view.findViewById(R.id.textview12);
			final TextView textview10 = (TextView) _view.findViewById(R.id.textview10);
			final TextView textview11 = (TextView) _view.findViewById(R.id.textview11);
			
			textview10.setText(_data.get((int)_position).get("transliteration").toString().concat(" - ").concat(_data.get((int)_position).get("name").toString()));
			textview11.setText(String.valueOf((long)(Double.parseDouble(_data.get((int)_position).get("total_verses").toString()))).concat(" Ayat"));
			textview12.setText(String.valueOf((long)(_position + 1)));
			textview10.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/manrope_bold.ttf"), 0);
			textview11.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/manrope_bold.ttf"), 0);
			textview12.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/manrope_bold.ttf"), 0);
			linear11.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.setClass(getContext(), Quran3dActivity.class);
					i.putExtra("page", quran_page.get((int)_position).get("page").toString());
					startActivity(i);
				}
			});
			
			return _view;
		}
	}
	
	
}