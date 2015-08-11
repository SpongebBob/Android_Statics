package com.example.bmj.statistics_all;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BMJBFStatics extends Activity {
    private String [] baifang = null;  //接收方数组
    private String [] bfplace = null;
    private String [] bfdate=null;
    private String [] bftarget=null;
    private String [] bfcreater=null;
    private int[] header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmjbfstatics);
        Intent intent = getIntent();
        Bundle b=this.getIntent().getExtras();
        baifang = b.getStringArray("baifang");
        bfdate=b.getStringArray("bfdate");
        bfplace=b.getStringArray("bfplace");
        bftarget=b.getStringArray("bftarget");
        bfcreater=b.getStringArray("bfcreater");

        if(baifang!=null)
        {
            header = new int[baifang.length];

            for (int i = 0; i < baifang.length; i++)
                header[i] = R.drawable.bmjbfpc;
            if(bfcreater==null){ bfcreater = new String[baifang.length];
            for(int i=0;i<baifang.length;i++)
                bfcreater[i]="我";
            }

            List<Map<String, Object>> listitems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < baifang.length; i++) {
                Map<String, Object> listitem = new HashMap<String, Object>();
                listitem.put("touxiang", header[i]);
                listitem.put("company", baifang[i]);
                listitem.put("date",bfdate[i]);
                listitem.put("place",bfplace[i]);
                listitem.put("target",bftarget[i]);
                listitem.put("creater",bfcreater[i]);
                Log.d("Test",bfcreater[i]+"哈哈哈哈哈哈哈哈");
                listitems.add(listitem);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitems, R.layout.bmjbaifang,
                    new String[]{"touxiang", "company","date","place","target","creater"},
                    new int[]{R.id.bmjbfheader, R.id.bmjbfname,R.id.bmjbfdate,R.id.bmjbfplace,R.id.bmjbftarget,R.id.bmjbfcreater});

            ListView list2 = (ListView) findViewById(R.id.bmjlistviewbf);
            list2.setAdapter(simpleAdapter);

            Log.d("Test1", String.valueOf(baifang.length));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
