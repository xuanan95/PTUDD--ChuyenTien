package com.example.buixuanan.moneyconvert.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buixuanan.moneyconvert.R;
import com.example.buixuanan.moneyconvert.db.MyDatabaseHelper;
import com.example.buixuanan.moneyconvert.db.Note;
import com.example.buixuanan.moneyconvert.dto.MoneyDTO;
import com.example.buixuanan.moneyconvert.service.RestClient;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String ACCESS_KEY = "b4541f7f2d1bf6eda7878593b2da5ece";
    private static final String CURRENCIES = "VND,GBP,EUR,AUD,CAD,SGD,HKD,MYR,THB,JPY,RUB";
    private String NAME_MONEY[] = {"USD","GBP","EUR","AUD","CAD","SGD","HKD","MYR","THB","JPY","RUB"};
    private MoneyAdapter moneyAdapter;
    private  List<String>listMoney = new ArrayList<String>();
    ListView lvListMoney;
    TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        MyDatabaseHelper db = new MyDatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDate = (TextView)findViewById(R.id.txtDate);
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm a MM/dd/yyyy");


        if(wifiManager.isWifiEnabled()){
            initData();
            txtDate.setText("Update: "+ft.format(now));
        }
        else{
            lvListMoney = (ListView)findViewById(R.id.lvListMoney);
            List<String> list = new ArrayList<>();
            listMoney = db.getData();
            moneyAdapter = new MoneyAdapter(getApplication(),listMoney);
            lvListMoney.setAdapter(moneyAdapter);
            txtDate.setText("Update: "+db.getDate());
            ClickListView(listMoney);
        }

    }


    private void initData(){

        Call <MoneyDTO> call = RestClient.getInstance().getListMoney().getListMoney(ACCESS_KEY,CURRENCIES);
        call.enqueue(new Callback<MoneyDTO>() {
            @Override
            public void onResponse(Call<MoneyDTO>call, Response<MoneyDTO> response) {
                if(response.isSuccessful()){
                    MoneyDTO moneyDTO = response.body();
                    Double vnd = (double) Integer.parseInt(moneyDTO.getQuotes().get("USDVND").toString());
                    Double gbp = Double.parseDouble(moneyDTO.getQuotes().get("USDGBP").toString());
                    Double eur = Double.parseDouble(moneyDTO.getQuotes().get("USDEUR").toString());
                    Double aud = Double.parseDouble(moneyDTO.getQuotes().get("USDAUD").toString());
                    Double cad = Double.parseDouble(moneyDTO.getQuotes().get("USDCAD").toString());
                    Double sgd = Double.parseDouble(moneyDTO.getQuotes().get("USDSGD").toString());
                    Double hkd = Double.parseDouble(moneyDTO.getQuotes().get("USDHKD").toString());
                    Double myr = Double.parseDouble(moneyDTO.getQuotes().get("USDMYR").toString());
                    Double thb = Double.parseDouble(moneyDTO.getQuotes().get("USDTHB").toString());
                    Double jpy = Double.parseDouble(moneyDTO.getQuotes().get("USDJPY").toString());
                    Double rub = Double.parseDouble(moneyDTO.getQuotes().get("USDRUB").toString());


                    listMoney.add(moneyDTO.getQuotes().get("USDVND").toString());
                    listMoney.add(String.valueOf(convertMoney(vnd,gbp)));
                    listMoney.add(String.valueOf(convertMoney(vnd,eur)));
                    listMoney.add(String.valueOf(convertMoney(vnd,aud)));
                    listMoney.add(String.valueOf(convertMoney(vnd,cad)));
                    listMoney.add(String.valueOf(convertMoney(vnd,sgd)));
                    listMoney.add(String.valueOf(convertMoney(vnd,hkd)));
                    listMoney.add(String.valueOf(convertMoney(vnd,myr)));
                    listMoney.add(String.valueOf(convertMoney(vnd,thb)));
                    listMoney.add(String.valueOf(convertMoney(vnd,jpy)));
                    listMoney.add(String.valueOf(convertMoney(vnd,rub)));
                    moneyAdapter = new MoneyAdapter(getApplication(),listMoney);
                    lvListMoney = (ListView)findViewById(R.id.lvListMoney);
                    lvListMoney.setAdapter(moneyAdapter);

                    ClickListView(listMoney);
                    databaseInsert(listMoney);
                }
                else{
                    Toast.makeText(getApplication(),"Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MoneyDTO> call, Throwable t) {
                Toast.makeText(getApplication(),"Fail",Toast.LENGTH_LONG).show();
            }
        });
    }
    //insert database
    private void databaseInsert(List listMoney){
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        Note note = new Note();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
        db.deleteData();
        for(int i = 0; i<11; i++){
            int id = i + 1;
            note.setId(id);
            note.setDate(ft.format(date));
            note.setPrice(listMoney.get(i).toString());
            db.insertData(note);
        }

    }
    private void ClickListView(final List<String> list){
        lvListMoney.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",NAME_MONEY[position]);
                bundle.putString("GBP",list.get(1));
                bundle.putString("USD",list.get(0));
                bundle.putString("EUR",list.get(2));
                bundle.putString("AUD",list.get(3));
                bundle.putString("CAD",list.get(4));
                bundle.putString("SGD",list.get(5));
                bundle.putString("HKD",list.get(6));
                bundle.putString("MYR",list.get(7));
                bundle.putString("THB",list.get(8));
                bundle.putString("JPY",list.get(9));
                bundle.putString("RUB",list.get(10));
                intent.putExtra("data",bundle);
                startActivity(intent);

            }
        });
    }
    private int convertMoney(Double vnd, Double other){
        int convert = (int)(vnd/other);
        return convert;
    }

    public class MoneyAdapter extends ArrayAdapter {
        private List<String>quotes;
        private Context context;
        private LayoutInflater inflater;
        public MoneyAdapter(Context context, List<String>quotes){
            super(context,0,quotes);
            this.quotes = quotes;
            this.context = context;
            inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            ItemHolder itemHolder = null;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.list_item,parent,false);
                itemHolder = new ItemHolder(convertView);
                convertView.setTag(itemHolder);
            }

            else {
                itemHolder = (ItemHolder)convertView.getTag();
            }
            int count = 0;
            for(int i = 0; i<11; i++) {
                if(position == i){
                    itemHolder.txtIcon.setText(NAME_MONEY[i]);
                    itemHolder.txtIcon.setBackgroundResource(R.drawable.pink_circle);
                    itemHolder.txtRate.setText(listMoney.get(i));
                    count = i + 1;
                    if(count%3==0){
                        itemHolder.txtIcon.setBackgroundResource(R.drawable.green_circle);
                    }
                    else if(count%2 ==0){
                        itemHolder.txtIcon.setBackgroundResource(R.drawable.pink_circle);
                    }
                    else{
                        itemHolder.txtIcon.setBackgroundResource(R.drawable.background_circle);
                    }
                }
            }
            return  convertView;
        }

    }
    public class ItemHolder{
        @BindView(R.id.txtIcon)
        TextView txtIcon;
        @BindView(R.id.txtRate)
        TextView txtRate;
        public ItemHolder(View v){
            ButterKnife.bind(this,v);
        }
    }


}
