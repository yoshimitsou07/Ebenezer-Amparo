package com.example.crudlist;

import java.util.ArrayList;

import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	
	ListView lv;
	ArrayList<String> list=new ArrayList<String>();
	ArrayAdapter<String> adapter;
	AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.lv=(ListView) this.findViewById(R.id.listView1);
        this.adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        this.lv.setAdapter(adapter);
        this.registerForContextMenu(lv);
    }
    

    @Override
	public boolean onContextItemSelected(MenuItem item) {
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
		int id=item.getItemId();
		switch(id){
		
		case R.id.update:
			String itemSelected=list.get(info.position);
			Intent intent=new Intent(this,UpdateActivity.class);
			intent.putExtra("updatename", itemSelected);
			this.startActivityForResult(intent, 1);
			
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
		String itemSelected=list.get(info.position);
		menu.setHeaderTitle(itemSelected);
		
		
	}




	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent intent=new Intent(this,UpdateActivity.class);
		this.startActivityForResult(intent, 0);
		
		
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	
		if(resultCode==Activity.RESULT_OK){
			
			Bundle b=data.getExtras();
			String myname=b.getString("myname");
			
			switch(requestCode){
			
			case 0:
				list.add(myname);
				break;
			case 1:
				list.set(info.position,myname);
			
			}
			
			adapter.notifyDataSetChanged();
			
			
		}
	}


	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
		switch(arg1){
		case DialogInterface.BUTTON_POSITIVE:
			if(!list.equals("")){				
				adapter.notifyDataSetChanged();
				list.remove(info.position);
				arg0.dismiss();
			}
			//else Toast.makeText(this, "Type Name", Toast.LENGTH_SHORT).show();
			
			
			 break;
		case DialogInterface.BUTTON_NEGATIVE:
			arg0.dismiss();
		
		}
	}
}
