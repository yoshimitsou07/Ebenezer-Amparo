package com.example.prelimexam;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	EditText txtName, txtCourse;
	Button btnAdd;
	ListView lv;
	ArrayList<String> list=new ArrayList<String>();
	ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.txtName=(EditText) findViewById(R.id.editText1);
        this.txtCourse=(EditText) findViewById(R.id.editText2);
        this.btnAdd=(Button) findViewById(R.id.button1);
        
        this.lv=(ListView) findViewById(R.id.listView1);
        this.adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        this.lv.setAdapter(adapter);
        
        this.btnAdd.setOnClickListener(this);
        this.lv.setOnItemClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		String name=this.txtName.getText().toString();
		String course=this.txtCourse.getText().toString();
		
		if(!name.equals("")&&!course.equals(""))
		{
			list.add(name);
			this.adapter.notifyDataSetChanged();
		}
		else Toast.makeText(this, "Type Name and Course", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
