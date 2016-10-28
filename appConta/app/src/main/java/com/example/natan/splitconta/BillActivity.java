package com.example.natan.splitconta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//import android.content.res.Resources;
public class BillActivity extends Activity {
    ListView lv_Conta;
    private double formatDouble(String numberSt){
        if(numberSt.isEmpty())
            return 0;
        int len=numberSt.length();
        if(numberSt.charAt(len-1)=='.')
            numberSt+='0';
        return Double.parseDouble(numberSt);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        final int quantidades[]=new int[]{5,5,5,5,3};
        String descricoes[]=new String[]{"COCA COLA","CERVEJA BHAMA ","X SALADA","PRATO DA CASA","CIGARRO"};
        final double unitarios[]=new double[]{2,3.5,3.5,8,3.6};
        lv_Conta = (ListView) findViewById(R.id.listView);
        List<Item> list = new ArrayList<Item>();
        for ( int i = 0 ; i < unitarios.length ; i++){
            list.add(new Item(descricoes[i], quantidades[i] , unitarios[i]));
        }
        Log.d("TAM LISTA", Integer.toString(list.size()));
       /* list.add(new Item("teste" ,0 , 0));
        list.add(new Item("teste2" ,1 , 1));*/
        /*final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);*/
        final YourAdapter adapter = new YourAdapter(this , (ArrayList<Item>) list);
        lv_Conta.setAdapter(adapter);
        /*for(int i=0;i<5;i++){
            String ID="nameProduct"+(i+1);
            int id = getResources().getIdentifier(ID, "id", getPackageName());
            TextView textViewToChange = (TextView) findViewById(id);
            textViewToChange.setText(descricoes[i]);
            //-------------------------------------------------------------------------
            ID="totalProductsNumber"+(i+1);
            id = getResources().getIdentifier(ID, "id", getPackageName());
            textViewToChange = (TextView) findViewById(id);
            textViewToChange.setText(Integer.toString(quantidades[i]));
            //-------------------------------------------------------------------------
            DecimalFormat twoDig=new DecimalFormat("0.00");
            ID="priceOneProduct"+(i+1);
            id = getResources().getIdentifier(ID, "id", getPackageName());
            textViewToChange = (TextView) findViewById(id);
            String unitario=twoDig.format(unitarios[i]);
            textViewToChange.setText(unitario);
        }*/
        /*for(int i=0;i<5;i++){
            final int j=i;
            String ID="numberConsumedProducts"+(i+1);
            int id = getResources().getIdentifier(ID, "id", getPackageName());
            final EditText editText = (EditText)findViewById(id);
            editText.addTextChangedListener(new TextWatcher() {
                String before, on;
                @Override
                public void afterTextChanged(Editable s) {
                    double doubleOn=formatDouble(on);
                    double doubleBefore=formatDouble(before);
                    if(doubleOn>quantidades[j])
                    {
                        editText.setText(before);
                        return;
                    }
                    //----------------------------------------------------------------------
                    double numberProducts =formatDouble(s.toString());
                    double parcialPrice=unitarios[j]*numberProducts;
                    DecimalFormat twoDig=new DecimalFormat("0.00");
                    String parcPrice=twoDig.format(parcialPrice);
                    String auxID="priceParcial"+(j+1);
                    int auxid = getResources().getIdentifier(auxID, "id", getPackageName());
                    TextView textViewToChange = (TextView) findViewById(auxid);
                    textViewToChange.setText(parcPrice);
                    //-----------------------------------------------------------------------
                    double auxSum=0;
                    for(int k=0;k<5;k++){
                        final int m=k;
                        String ID="priceParcial"+(k+1);
                        int id = getResources().getIdentifier(ID, "id", getPackageName());
                        TextView parcVal = (TextView) findViewById(id);
                        double parc=formatDouble(parcVal.getText().toString());
                        auxSum+=parc;
                    }
                    String sum=twoDig.format(auxSum);
                    *//*TextView result = (TextView) findViewById(R.id.result);
                    result.setText(sum);*//*
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    before = editText.getText().toString();
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    on=editText.getText().toString();
                }
            });
        }
 */       /*
        Resources res = getResources();
        String[] nameProducts = res.getStringArray(R.array.nameProducts);
        for(int i=0;i<nameProducts.length;i++){

        }*/
    }
}