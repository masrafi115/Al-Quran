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


public class JuzIndexFragmentActivity extends  Fragment  { 
	
	
	private ArrayList<HashMap<String, Object>> juz_list = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private Intent i = new Intent();
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.juz_index_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
		listview1 = (ListView) _view.findViewById(R.id.listview1);
	}
	
	private void initializeLogic() {
		juz_list = new Gson().fromJson("[\n  {\n    \"page\": \"2\",\n    \"text\": \"Alif Lam Meem\"\n  },\n  {\n    \"page\": \"21\",\n    \"text\": \"Sayaqool\"\n  },\n  {\n    \"page\": \"39\",\n    \"text\": \"Tilkal Rusull\"\n  },\n  {\n    \"page\": \"57\",\n    \"text\": \"Lan tana Loo'\"\n  },\n  {\n    \"page\": \"75\",\n    \"text\": \"5.Wal Mohsanat\"\n  },\n  {\n    \"page\": \"93\",\n    \"text\": \"La Yuhibbuallah\"\n  },\n  {\n    \"page\": \"111\",\n    \"text\": \"Wa Iza Samiu\"\n  },\n  {\n    \"page\": \"129\",\n    \"text\": \"Wa Lao Annana\"\n  },\n  {\n    \"page\": \"147\",\n    \"text\": \"Qalal Malao\"\n  },\n  {\n    \"page\": \"165\",\n    \"text\": \"Wa A'lamu\"\n  },\n  {\n    \"page\": \"183\",\n    \"text\": \"Yatazeroon\"\n  },\n  {\n    \"page\": \"201\",\n    \"text\": \"Wa Mamin Da'abat\"\n  },\n  {\n    \"page\": \"219\",\n    \"text\": \"Wa Ma Ubiroo\"\n  },\n  {\n    \"page\": \"237\",\n    \"text\": \"Rubama\"\n  },\n  {\n    \"page\": \"255\",\n    \"text\": \"Subhanallazi\"\n  },\n  {\n    \"page\": \"273\",\n    \"text\": \"Qal Alam\"\n  },\n  {\n    \"page\": \"291\",\n    \"text\": \"Aqtarabo\"\n  },\n  {\n    \"page\": \"309\",\n    \"text\": \"Qadd Aflaha\"\n  },\n  {\n    \"page\": \"327\",\n    \"text\": \"Wa Qalallazina\"\n  },\n  {\n    \"page\": \"345\",\n    \"text\": \"A'man Khalaq\"\n  },\n  {\n    \"page\": \"363\",\n    \"text\": \"Utlu Ma Oohi\"\n  },\n  {\n    \"page\": \"381\",\n    \"text\": \"Wa Manyaqnut\"\n  },\n  {\n    \"page\": \"399\",\n    \"text\": \"Wa Mali\"\n  },\n  {\n    \"page\": \"417\",\n    \"text\": \"Faman Azlam\"\n  },\n  {\n    \"page\": \"435\",\n    \"text\": \"Elahe Yuruddo\"\n  },\n  {\n    \"page\": \"453\",\n    \"text\": \"Ha'a Meem\"\n  },\n  {\n    \"page\": \"471\",\n    \"text\": \"Qala Fama Khatbukum\"\n  },\n  {\n    \"page\": \"489\",\n    \"text\": \"Qadd Sami Allah\"\n  },\n  {\n    \"page\": \"509\",\n    \"text\": \"Tabarakallazi\"\n  },\n  {\n    \"page\": \"529\",\n    \"text\": \"Amma Yatasa'aloon\"\n  }\n]\n", new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		listview1.setAdapter(new Listview1Adapter(juz_list));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
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
				_view = _inflater.inflate(R.layout.list_juz, null);
			}
			
			final LinearLayout linear11 = (LinearLayout) _view.findViewById(R.id.linear11);
			final LinearLayout linear14 = (LinearLayout) _view.findViewById(R.id.linear14);
			final LinearLayout linear13 = (LinearLayout) _view.findViewById(R.id.linear13);
			final TextView textview12 = (TextView) _view.findViewById(R.id.textview12);
			final TextView textview10 = (TextView) _view.findViewById(R.id.textview10);
			final TextView textview11 = (TextView) _view.findViewById(R.id.textview11);
			
			textview10.setText(_data.get((int)_position).get("text").toString());
			textview11.setText(String.valueOf((long)(Double.parseDouble(_data.get((int)_position).get("page").toString()))));
			textview12.setText(String.valueOf((long)(_position + 1)));
			textview10.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/manrope_bold.ttf"), 0);
			textview11.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/manrope_bold.ttf"), 0);
			textview12.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/manrope_bold.ttf"), 0);
			linear11.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.setClass(getContext(), Quran3dActivity.class);
					i.putExtra("page", String.valueOf((long)(Double.parseDouble(_data.get((int)_position).get("page").toString()))));
					startActivity(i);
				}
			});
			
			return _view;
		}
	}
	
	
}