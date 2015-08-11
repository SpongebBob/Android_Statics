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


public class BMJKHStatistics extends Activity {
    private String[] kehu = null;  //���շ�����
    private String[] kehutime=null;
    private String[] kehucreater=null;
    private int[] header = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmjkhstatistics);
        Intent intent = getIntent();
        Bundle b=this.getIntent().getExtras();
        kehu = b.getStringArray("kehu");
        kehutime = b.getStringArray("kehutime");
        kehucreater = b.getStringArray("kehucreater");

        if (kehu != null) {
            //��ʼ����ͷ��
            header = new int[kehu.length];

            for (int i = 0; i < kehu.length; i++)
                header[i] = R.drawable.bmjgongsi;
            if(kehucreater==null)
            {kehucreater= new String[kehu.length];
            for(int i=0;i<kehu.length;i++)
                kehucreater[i]="我";

            }

            List<Map<String, Object>> listitems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < kehu.length; i++) {
                Map<String, Object> listitem = new HashMap<String, Object>();
                listitem.put("touxiang", header[i]);
                listitem.put("xingming", kehu[i]);
                listitem.put("xingmingtime",kehutime[i]);
                listitem.put("xingmingcreater",kehucreater[i]);

                listitems.add(listitem);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitems, R.layout.bmj_simple_item,
                    new String[]{"touxiang", "xingming","xingmingtime","xingmingcreater"},
                    new int[]{R.id.bmjheader, R.id.bmjname,R.id.bmjtime,R.id.bmjcreater});

            ListView list2 = (ListView) findViewById(R.id.bmjlistviewkh);
            list2.setAdapter(simpleAdapter);

            Log.d("Test1", String.valueOf(kehu.length));

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
