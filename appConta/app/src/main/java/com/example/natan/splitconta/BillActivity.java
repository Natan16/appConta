package com.example.natan.splitconta;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//import android.content.res.Resources;
public class BillActivity extends AppCompatActivity {
    ListView lv_Conta;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFF59D"));
        myToolbar.setBackgroundDrawable(colorDrawable);
        setSupportActionBar(myToolbar);
        final int quantidades[]=new int[]{5,5,5,5,3};
        String descricoes[]=new String[]{"COCA COLA","CERVEJA BHAMA ","X SALADA","PRATO DA CASA","CIGARRO"};
        final double unitarios[]=new double[]{2,3.5,3.5,8,3.6};
        lv_Conta = (ListView) findViewById(R.id.listView);
        List<Item> list = new ArrayList<Item>();
        for ( int i = 0 ; i < unitarios.length ; i++){
            list.add(new Item(descricoes[i], quantidades[i] , unitarios[i]));
        }
        Log.d("TAM LISTA", Integer.toString(list.size()));
        final YourAdapter adapter = new YourAdapter(this , (ArrayList<Item>) list);



        lv_Conta.setAdapter(adapter);
        /*double auxSum=0;
        for (int k = 0; k < adapter.getCount(); k++) {
            View auxView=adapter.vi.findViewById(k);
            TextView parcVal = (TextView) auxView.findViewById(R.id.priceParcial);
            double parc = adapter.formatDouble(parcVal.getText().toString());
            auxSum += parc;
        }
        DecimalFormat twoDig=new DecimalFormat("0.00");
        String sum = twoDig.format(auxSum);
        TextView result = (TextView) adapter.vi.findViewById(R.id.result);
        result.setText(sum);
        |*/
    }
}