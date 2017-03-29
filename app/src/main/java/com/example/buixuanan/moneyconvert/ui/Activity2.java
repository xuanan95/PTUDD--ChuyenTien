package com.example.buixuanan.moneyconvert.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.buixuanan.moneyconvert.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity2 extends Activity {
    private List<String>lists = new ArrayList<>();
    TextView txtConvert;
    private ListView listView;
    private ListMoneyAdapter listMoneyAdapter;
    private  static final String NAME_MG[]={"USD","GBP","EUR","AUD","CAD","SGD","HKD","MYR","THB","JPY","RUB"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        txtConvert = (TextView) findViewById(R.id.txtTextCV);
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("data");
        String name = bundle.getString("name");
        txtConvert.setText("Bạn muốn chuyển " + name + " sang: ");
        listView = (ListView)findViewById(R.id.lvLview);
        for(int i =0; i<11; i++){
            if (NAME_MG[i].equals(name)){
            }
            else{
                lists.add(NAME_MG[i]);
            }
        }
        lists.add("VND");
        listMoneyAdapter = new ListMoneyAdapter(getBaseContext(),lists);
        listView.setAdapter(listMoneyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(Activity2.this,Activity3.class); //tranmission data to Activity 3
                bundle.putString("name1",lists.get(position));
                intent1.putExtra("data1",bundle);
                startActivity(intent1);
            }
        });

    }
    public class ListMoneyAdapter extends ArrayAdapter {
        private List<String> lists;
        private Context context;
        private LayoutInflater inflater;
        public ListMoneyAdapter(Context context, List<String>lists){
            super(context,0,lists);
            this.lists = lists;
            this.context = context;
            inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            ItemHolder2 itemHolder2 = null;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.list_item_ac2,parent,false);
                itemHolder2 = new ItemHolder2(convertView);
                convertView.setTag(itemHolder2);
            }

            else {
                itemHolder2 = (ItemHolder2)convertView.getTag();
            }
            for(int i = 0; i<11; i++){
               if(position == i){
                   itemHolder2.txtText.setText(lists.get(i));
               }
            }
            return  convertView;
        }

    }
    public class ItemHolder2{
        @BindView(R.id.txtText)
        TextView txtText;
        public ItemHolder2(View v){
            ButterKnife.bind(this,v);
        }
    }
}
