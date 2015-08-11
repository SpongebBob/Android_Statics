package com.example.bmj.statistics_all;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.IBinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;


public class BMJStatisticsActivity extends Activity {
    private int int_mykehu = 0;
    private int int_mylixnaxiren = 0;
    private int int_mybaifang = 0;
    private int int_mymission = 0;
    private int int_undermission= 0;

    private int int_underkehu = 0;
    private int int_underlixnaxiren = 0;
    private int int_underbaifang = 0;

    private EditText sellStartTime;
    private EditText sellEndTime;
    private Spinner spinner2;
    private String[] mData = {"我的数据统计", "下属数据统计"};
    private int timeFlag;
    private int mYear;
    private int mMonth;
    private int mDay;
    public  static int FlagSpinner = 0;
    private  WebSocketConnection mConnection;
    private  WebSocketConnection mConnection2;
    private  WebSocketConnection mConnection3;
    private boolean isConnected = false;
    private boolean isConnected2 = false;
    private boolean isConnected3 = false;
    private TextView tx_kehu;
    private TextView tx_baifang;
    private TextView tx_lixiren;
    private TextView tx_mission;

    private String[] st_mykehu;
    private String[] st_mykehu_creattime;

    private String[] st_mybaifang;
    private String[] st_mybaifang_creattime;

    private String[] st_mylianxiren;
    private String[] st_mylianxiren_craetime;


    private String[] st_underkehu;
    private String[] st_underkehu_creattime;
    private String[] st_underkehu_creater;

    private String[] st_underbaifang;


    private String[] st_underlxr;
    private String[] st_underlxr_creater;
    private String[] st_underlxr_creattime;

    private String[] st_mybfplace;
    private String[] st_mybfcompany;
    private String[] st_mybfdate;
    private String[] st_mybftarget;

    private String[] st_underbfplace;
    private String[] st_underbfdate;
    private String[] st_underbftarget;
    private String[] st_underbfcreater;

    private String[] st_mymission_name;
    private String[] st_mymission_creater;
    private String[] st_mymission_status;

    private String[] st_undermission_name;
    private String[] st_undermission_creater;
    private String[] st_undermission_status;


    private View ly_kehu;
    private View ly_lxr;
    private View ly_bf;
    private View ly_mission;
    private String type3uri = "ws://192.168.50.11:8000/ws";
    private String type3uri2 = "ws://101.200.189.127:1234/ws";


    private int uid;


    private void initTextview() {
        tx_kehu = (TextView) findViewById(R.id.txshow2);
        tx_lixiren = (TextView) findViewById(R.id.txshow3);
        tx_baifang = (TextView) findViewById(R.id.txshow4);
        tx_mission = (TextView) findViewById(R.id.txshow1);
        ly_kehu = findViewById(R.id.statics_show2);
        ly_bf = findViewById(R.id.statics_show4);
        ly_lxr = findViewById(R.id.statics_show3);
        ly_mission = findViewById(R.id.statics_show);


        ly_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                if (FlagSpinner == 0){
                    b.putStringArray("missionname", st_mymission_name);
                    b.putStringArray("missioncreater",st_mymission_creater);
                    b.putStringArray("missionstatus",st_mymission_status);
                }
                else
                {
                    b.putStringArray("missionname", st_undermission_name);
                    b.putStringArray("missioncreater",st_undermission_creater);
                    b.putStringArray("missionstatus",st_undermission_status);

                }
                intent.putExtras(b);

                intent.setClass(BMJStatisticsActivity.this, BMJMissionStatics.class);
                startActivity(intent);
            }
        });
        ly_kehu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle b = new Bundle();
                if (FlagSpinner == 0){
                    b.putStringArray("kehu", st_mykehu);
                    b.putStringArray("kehutime",st_mykehu_creattime);
                }
                else {
                    b.putStringArray("kehu", st_underkehu);
                    b.putStringArray("kehutime",st_underkehu_creattime);
                    b.putStringArray("kehucreater",st_underkehu_creater);
                }
                intent.putExtras(b);

                intent.setClass(BMJStatisticsActivity.this, BMJKHStatistics.class);
                startActivity(intent);
            }
        });
        ly_lxr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle b = new Bundle();
                if (FlagSpinner == 0) {
                    b.putStringArray("lxr", st_mylianxiren);
                    b.putStringArray("lxrtime",st_mylianxiren_craetime);
                }
                else{
                    b.putStringArray("lxr", st_underlxr);
                    b.putStringArray("lxrtime",st_underlxr_creattime);
                    b.putStringArray("lxrcreater",st_underlxr_creater);
                }
                intent.putExtras(b);

                intent.setClass(BMJStatisticsActivity.this, BMJLXRStatics.class);
                startActivity(intent);

            }
        });
        ly_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle b = new Bundle();
                if (FlagSpinner == 0) {
                    b.putStringArray("baifang", st_mybaifang);
                    b.putStringArray("bfdate", st_mybfdate);
                    b.putStringArray("bfplace", st_mybfplace);
                    b.putStringArray("bftarget", st_mybftarget);
                } else {
                    b.putStringArray("baifang", st_underbaifang);
                    b.putStringArray("bfdate", st_underbfdate);
                    b.putStringArray("bfplace", st_underbfplace);
                    b.putStringArray("bftarget", st_underbftarget);
                    b.putStringArray("bfcreater",st_underbfcreater);

                }
                intent.putExtras(b);

                intent.setClass(BMJStatisticsActivity.this, BMJBFStatics.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getActionBar().setCustomView(LayoutInflater.from(this).inflate(R.layout.bmjtitle_main, null));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        initTextview();
        mConnection = new WebSocketConnection();
        connect();

        mConnection2 = new WebSocketConnection();
        connect2();

        mConnection3 = new WebSocketConnection();
        connect3();

        Calendar a = Calendar.getInstance();

        mYear = a.get(Calendar.YEAR);
        mMonth = a.get(Calendar.MONTH);
        mDay = a.get(Calendar.DAY_OF_MONTH);
        sellStartTime = (EditText) findViewById(R.id.setstartdatetxt);
        sellEndTime = (EditText) findViewById(R.id.setfinishdatetxt);
        sellStartTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timeFlag = 0;
                showDialog(0);
            }
        });
        sellEndTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timeFlag = 1;
                showDialog(1);
            }
        });
        sellStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    timeFlag = 0;
                    hideIM(v);
                    showDialog(0);
                }
            }
        });

        sellEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    timeFlag = 1;
                    hideIM(v);
                    showDialog(1);
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 = (Spinner) findViewById(R.id.bmjspinner_main);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    FlagSpinner = 0;
                } else if (position == 1)
                    FlagSpinner = 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner2.setVisibility(View.VISIBLE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void connect2() {
        try {
            mConnection2.connect(type3uri2, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    Toast.makeText(getApplicationContext(), "已连接", Toast.LENGTH_SHORT).show();
                    isConnected2 = true;
                    //发送请求

                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d("Test1", "pengxiaolei"+payload);
                    deencode2(payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d("Test1","鹏错误"+reason);
                    isConnected2 = false;
                    Toast.makeText(getApplicationContext(), "彭晓蕾失去连接", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (WebSocketException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void connect() {
        try {
            mConnection.connect(type3uri, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    Toast.makeText(getApplicationContext(), "已连接", Toast.LENGTH_SHORT).show();
                    isConnected = true;
                    //发送请求

                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d("Test1", payload);
                    doencode(payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    isConnected = false;
                    Toast.makeText(getApplicationContext(), "失去连接", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (WebSocketException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void connect3() {
        try {
            mConnection3.connect(type3uri2, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    Toast.makeText(getApplicationContext(), "已连接", Toast.LENGTH_SHORT).show();
                    isConnected3 = true;
                    //发送请求

                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d("Test1", "pengxiaolei"+payload);
                    deencode3(payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d("Test1","鹏错误"+reason);
                    isConnected3 = false;
                    Toast.makeText(getApplicationContext(), "彭晓蕾失去连接", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (WebSocketException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendreq3(){
        if (mConnection3 == null || !mConnection3.isConnected() || !isConnected3)
            return;

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startime = df.parse(sellStartTime.getText().toString());
            Date finshitime = df.parse(sellEndTime.getText().toString());
            Log.d("Test", String.valueOf(startime.getTime() / 1000));
            Log.d("Test", "日了狗");
            JSONObject requestToP2 = new JSONObject();
            requestToP2.put("cmd", "getCareMyTaskStatistic");

            requestToP2.put("startTime", String.valueOf(startime.getTime()/1000));
            //写死了的一个uid，用于调试
            requestToP2.put("endTime", String.valueOf(finshitime.getTime() / 1000));
            requestToP2.put("userId", "100");
            mConnection3.sendTextMessage(requestToP2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
    public void sendreq() {

        if (mConnection == null || !mConnection.isConnected() || !isConnected)
            return;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startime = df.parse(sellStartTime.getText().toString());
            Date finshitime = df.parse(sellEndTime.getText().toString());
            Log.d("Test", String.valueOf(startime.getTime() / 1000));
            Log.d("Test", String.valueOf(finshitime.getTime() / 1000));

            JSONObject request2 = new JSONObject();

      /*      request2.put("cmd", "getMyTaskStatistic");
            request2.put("startTime", String.valueOf(startime.getTime()/1000));
            //写死了的一个uid，用于调试
            request2.put("endTime", String.valueOf(finshitime.getTime()/1000));
            request2.put("userId", "186");*/

            request2.put("start", String.valueOf(startime.getTime() / 1000));
            //写死了的一个uid，用于调试
            request2.put("end", String.valueOf(finshitime.getTime() / 1000));
            request2.put("type", "3");
            request2.put("uid", "101");
            request2.put("cmd", "tongji");
            Log.d("Test", request2.toString());
            mConnection.sendTextMessage(request2.toString());



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void sendreq2(){
        if (mConnection2 == null || !mConnection2.isConnected() || !isConnected2)
            return;

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startime = df.parse(sellStartTime.getText().toString());
            Date finshitime = df.parse(sellEndTime.getText().toString());
            Log.d("Test", String.valueOf(startime.getTime() / 1000));
            Log.d("Test", "日了狗");
            JSONObject requestToP = new JSONObject();
            requestToP.put("cmd", "getMyTaskStatistic");

            requestToP.put("startTime", String.valueOf(startime.getTime()/1000));
            //写死了的一个uid，用于调试
            requestToP.put("endTime", String.valueOf(finshitime.getTime() / 1000));
            requestToP.put("userId", "100");
            mConnection2.sendTextMessage(requestToP.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public void doencode(String test) {

        String strUTF8 = null;
        try {
            JSONObject lan = null;
            strUTF8 = URLDecoder.decode(test, "UTF-8");
            JSONObject root = new JSONObject(strUTF8);

            JSONArray underkehu = root.getJSONArray("underkehu");
            int_underkehu = underkehu.length();
            st_underkehu = new String[int_underkehu];
            st_underkehu_creater = new String[int_underkehu];
            st_underkehu_creattime = new String[int_underkehu];
            for (int i = 0; i < int_underkehu; i++) {
                lan = underkehu.getJSONObject(i);
                st_underkehu[i] = lan.getString("name");
                st_underkehu_creater[i]=lan.getString("creater");
                st_underkehu_creattime[i]=getStringDateShort(lan.getString("createtime"));
            }
            Log.d("Test1","flag1!!!");

            JSONArray underbaifang = root.getJSONArray("underbaifang");
            int_underbaifang = underbaifang.length();
            st_underbaifang = new String[int_underbaifang];
            st_underbfdate = new String[int_underbaifang];
            st_underbfplace = new String[int_underbaifang];
            st_underbftarget = new String[int_underbaifang];
            st_underbfcreater=new String[int_underbaifang];;
            for (int i = 0; i < int_underbaifang; i++) {
                lan = underbaifang.getJSONObject(i);
                //拜访人的名字不知道啊
                st_underbaifang[i] = lan.getString("company");
                st_underbfdate[i] = lan.getString("date");
                st_underbftarget[i] = lan.getString("target");
                st_underbfplace[i] = lan.getString("place");
                st_underbfcreater[i] = lan.getString("creater");
            }
            Log.d("Test1","flag2!!!");


            JSONArray underlianxiren = root.getJSONArray("underlianxiren");
            int_underlixnaxiren = underlianxiren.length();
            st_underlxr = new String[int_underlixnaxiren];
            st_underlxr_creattime = new String[int_underlixnaxiren];
            st_underlxr_creater = new String[int_underlixnaxiren];
            for (int i = 0; i < int_underlixnaxiren; i++) {
                lan = underlianxiren.getJSONObject(i);
                //拜访人的名字不知道啊
                st_underlxr[i] = lan.getString("username");
                st_underlxr_creattime[i]=getStringDateShort(lan.getString("createtime"));
                st_underlxr_creater[i]=lan.getString("creater");
            }

            Log.d("Test1","flag3!!!");
            JSONArray mykehu = root.getJSONArray("mykehu");
            int_mykehu = mykehu.length();
            st_mykehu = new String[int_mykehu];
            st_mykehu_creattime = new String[int_mykehu];
            for (int i = 0; i < int_mykehu; i++) {
                lan = mykehu.getJSONObject(i);
                st_mykehu[i] = lan.getString("name");
                st_mykehu_creattime[i]=getStringDateShort(lan.getString("createtime"));
            }

            Log.d("Test1","flag4!!!");
            JSONArray mybaifang = root.getJSONArray("mybaifang");
            int_mybaifang = mybaifang.length();
            st_mybaifang = new String[int_mybaifang];
            st_mybfdate = new String[int_mybaifang];
            st_mybfplace = new String[int_mybaifang];
            st_mybftarget = new String[int_mybaifang];
            for (int i = 0; i < int_mybaifang; i++) {
                lan = mybaifang.getJSONObject(i);
                st_mybaifang[i] = lan.getString("company");
                st_mybfplace[i] = lan.getString("place");
                st_mybftarget[i] = lan.getString("target");
                st_mybfdate[i] = lan.getString("date");
            }

            Log.d("Test1","flag5!!!");
            JSONArray mylianxiren = root.getJSONArray("mylianxiren");
            int_mylixnaxiren = mylianxiren.length();
            st_mylianxiren = new String[int_mylixnaxiren];
            st_mylianxiren_craetime = new String[int_mylixnaxiren];
            for (int i = 0; i < int_mylixnaxiren; i++) {
                lan = mylianxiren.getJSONObject(i);
                st_mylianxiren[i] = lan.getString("name");
                st_mylianxiren_craetime[i]=getStringDateShort(lan.getString("createtime"));
            }


            Log.d("Test1","flag6!!!");
            if (FlagSpinner == 0) {
                tx_kehu.setText(String.valueOf(int_mykehu) + "人");
                tx_baifang.setText(String.valueOf(int_mybaifang) + "人");
                tx_lixiren.setText(String.valueOf(int_mylixnaxiren) + "人");

            } else {
                tx_kehu.setText(String.valueOf(int_underkehu) + "人");
                tx_baifang.setText(String.valueOf(int_underbaifang) + "人");
                tx_lixiren.setText(String.valueOf(int_underlixnaxiren) + "人");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }
    public void deencode2(String test)
    {
        int j = 0;
        String strUTF8 = null;
        try {
            JSONObject lan = null;
            strUTF8 = URLDecoder.decode(test, "UTF-8");
            JSONObject root = new JSONObject(strUTF8);

            JSONArray mymission = root.getJSONArray("datas");
            int_mymission = mymission.length();
            st_mymission_name = new String[int_mymission];
            st_mymission_status=new String[int_mymission];
            st_mymission_creater = new String[int_mymission];
            for(int i=0;i<int_mymission;i++)
            {
                lan = mymission.getJSONObject(i);
                st_mymission_name[i] = lan.getString("name");
                st_mymission_status[i]=lan.getString("status");
                if(lan.getString("status").equals("已完成")) j++;
                st_mymission_creater[i] = lan.getString("opUserId");

            }
//            st_underkehu = new String[int_underkehu];
//            st_underkehu_creater = new String[int_underkehu];
//            st_underkehu_creattime = new String[int_underkehu];
//            for (int i = 0; i < int_underkehu; i++) {
//                lan = underkehu.getJSONObject(i);
//                st_underkehu[i] = lan.getString("name");
//                st_underkehu_creater[i]=lan.getString("creater");
//                st_underkehu_creattime[i]=getStringDateShort(lan.getString("createtime"));
//            }
            Log.d("Test1","PENG!!!");
            if (FlagSpinner == 0) {

                tx_mission.setText(String.valueOf(j)+"个");
            } else {

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }
    public void deencode3(String test){
        int j = 0;
        String strUTF8 = null;
        try {
            JSONObject lan = null;
            strUTF8 = URLDecoder.decode(test, "UTF-8");
            JSONObject root = new JSONObject(strUTF8);

            JSONArray undermission = root.getJSONArray("datas");
            int_undermission = undermission.length();
            st_undermission_name = new String[int_undermission];
            st_undermission_status=new String[int_undermission];
            st_undermission_creater = new String[int_undermission];

            for(int i=0;i<int_undermission;i++)
            {
                lan = undermission.getJSONObject(i);
                st_undermission_name[i] = lan.getString("name");
                st_undermission_status[i]=lan.getString("status");
                if(lan.getString("status").equals("已完成")) j++;
                st_undermission_creater[i] = lan.getString("opUserId");

            }
//            st_underkehu = new String[int_underkehu];
//            st_underkehu_creater = new String[int_underkehu];
//            st_underkehu_creattime = new String[int_underkehu];
//            for (int i = 0; i < int_underkehu; i++) {
//                lan = underkehu.getJSONObject(i);
//                st_underkehu[i] = lan.getString("name");
//                st_underkehu_creater[i]=lan.getString("creater");
//                st_underkehu_creattime[i]=getStringDateShort(lan.getString("createtime"));
//            }
            Log.d("Test1", "PENG!!!");
            if (FlagSpinner == 0) {
            } else {
                tx_mission.setText(String.valueOf(j)+"个");

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    public static String getStringDateShort(String createtime) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_ok) {
            sendreq();
            sendreq2();
            sendreq3();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return true;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    String mm;
                    String dd;
                    if (monthOfYear <= 9) {
                        mMonth = monthOfYear + 1;
                        mm = "0" + mMonth;
                    } else {
                        mMonth = monthOfYear + 1;
                        mm = String.valueOf(mMonth);
                    }
                    if (dayOfMonth <= 9) {
                        mDay = dayOfMonth;
                        dd = "0" + mDay;
                    } else {
                        mDay = dayOfMonth;
                        dd = String.valueOf(mDay);
                    }
                    mDay = dayOfMonth;
                    if (timeFlag == 0) {
                        sellStartTime.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
                    } else {
                        sellEndTime.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
                    }
                }
            };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            case 1:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    // 隐藏手机键盘
    private void hideIM(View edt) {
        try {
            InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            IBinder windowToken = edt.getWindowToken();
            if (windowToken != null) {
                im.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {

        }
    }
}
