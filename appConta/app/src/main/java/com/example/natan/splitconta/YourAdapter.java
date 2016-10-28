package com.example.natan.splitconta;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natan on 10/27/2016.
 */
public class YourAdapter extends ArrayAdapter<Item> {
    Context context;
    List<Item> data;
    View vi;
    private static LayoutInflater inflater = null;
    public YourAdapter(Context context, ArrayList<Item> data) {
        super(context, 0, data);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    protected double formatDouble(String numberSt){
        if(numberSt.isEmpty())
            return 0;
        int len=numberSt.length();
        if(numberSt.charAt(len-1)=='.')
            numberSt+='0';
        return Double.parseDouble(numberSt);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Item getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        final Item  item = getItem(position);
        final View finalVi = vi;
        DecimalFormat twoDig=new DecimalFormat("0.00");
        String aux = twoDig.format(item.valor);
        //ResourceId rId = getItem(position).get(inic_pos).getId();
        TextView oneProduct = (TextView) vi.findViewById(R.id.priceOneProduct);
        oneProduct.setText(aux);
        final EditText priceOne = (EditText) vi.findViewById(R.id.numberConsumedProducts);
        priceOne.setText("");
        TextView desc = (TextView) vi.findViewById(R.id.nameProduct);
        desc.setText(item.desc);
        TextView qtde = (TextView) vi.findViewById(R.id.totalProductsNumber);
        qtde.setText(Integer.toString(item.qtde));
        final TextView parcPrice = (TextView) vi.findViewById(R.id.priceParcial);
        parcPrice.setText("0");
        priceOne.addTextChangedListener(new TextWatcher() {
            String before, on;
            @Override
            public void afterTextChanged(Editable s) {
                double doubleOn = formatDouble(on);
                double doubleBefore = formatDouble(before);
                //----------------------------------------------------------------------
                if (doubleOn > item.qtde) {
                    priceOne.setText(before);
                    return;
                }
                double numberProducts = formatDouble(s.toString());
                double parcialPrice = item.valor * numberProducts;
                DecimalFormat twoDig = new DecimalFormat("0.00");
                String parcPriceSt = twoDig.format(parcialPrice);
                parcPrice.setText(parcPriceSt);
                //-----------------------------------------------------------------------
                double auxSum = 0;
                /*for (int k = 0; k < getCount(); k++) {
                    View auxView=finalVi.findViewById(k);
                    TextView parcVal = (TextView) auxView.findViewById(R.id.priceParcial);
                    double parc = formatDouble(parcVal.getText().toString());
                    auxSum += parc;
                }
                String sum = twoDig.format(auxSum);
                TextView result = (TextView) finalVi.findViewById(R.id.result);
                result.setText(sum);*/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                before = priceOne.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                on = priceOne.getText().toString();
            }
        });
        return vi;
    }

}
