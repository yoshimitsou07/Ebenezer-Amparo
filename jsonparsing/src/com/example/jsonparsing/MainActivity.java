package com.example.jsonparsing;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	ListView lv;
	itemAdapter adapter;
	AdapterView.AdapterContextMenuInfo info;
	ArrayList<Student> list = new ArrayList<Student>();
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.lv = (ListView)findViewById(R.id.listView1);
        adapter = new itemAdapter(this,list);
        this.lv.setAdapter(adapter);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        try {
			URL url = new URL("http://10.0.2.2/androidweb/listforandroid.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			StringBuffer sb = new StringBuffer();
			int c = 0;
			InputStream is = conn.getInputStream();
			while((c=is.read())!=-1)
			{
				sb.append((char)c);
			}
			is.close();
			conn.disconnect();
			String jsonstring = sb.toString();
			
			
			JSONObject json = new JSONObject(jsonstring);
			JSONArray studentArray = json.getJSONArray("student");
			
			
			for(int i = 0; i<studentArray.length();i++)
			{
				JSONObject student = (JSONObject)studentArray.get(i);
					Student s = new Student();
						s.setIdno(student.getString("idno"));
						s.setFamilyname(student.getString("familyname"));
						s.setGivenname(student.getString("givenname"));
						s.setCourse(student.getString("course"));
						s.setYearlevel(student.getString("yearlevel"));
						s.setCampus(student.getString("campus"));
						list.add(s);
			}
			
			adapter.notifyDataSetChanged();
			
			
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

       // Intent intent = new Intent(this, addStudent.class);
		//this.startActivity(intent);
        
        return true;
    }
    
    @Override
	public boolean onContextItemSelected(MenuItem item) {
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
		int id=item.getItemId();
		switch(id){
		
		case R.id.update:
			//String itemSelected=list.get(info.position);
			//Intent intent=new Intent(this,UpdateActivity.class);
			//intent.putExtra("updatename", itemSelected);
			//this.startActivityForResult(intent, 1);
			
			break;
		case R.id.delete:
			builder.setTitle("Do you want to delete the item?");
			builder.setPositiveButton("OK", this);
		    builder.setNegativeButton("Cancel", this);
			//builder.setNeutralButton("OK", null);
			AlertDialog dialog=builder.create();
			dialog.show();
			
		}
		return super.onContextItemSelected(item);
	}
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.mycontextmenu, menu);
		
		info=(AdapterContextMenuInfo) menuInfo;
		Student itemSelected=list.get(info.position);
		//menu.setHeaderTitle(itemSelected);
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, addStudent.class);
		this.startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
		switch(arg1){
		case DialogInterface.BUTTON_POSITIVE:
			if(!list.equals("")){				
				adapter.notifyDataSetChanged();
				//list.remove(info.position);
				arg0.dismiss();
			}
			
			 break;
		case DialogInterface.BUTTON_NEGATIVE:
			arg0.dismiss();
		}
	}
}
