package com.example.natan.splitconta;

import android.content.Context;
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
    private static LayoutInflater inflater = null;
    public YourAdapter(Context context, ArrayList<Item> data) {
        super(context, 0, data);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        // TODO método no gerenciador do banco de dados pra recuperar os Songs a partir do id da songList
        Item  item = getItem(position);
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        //int inic_pos = PlaylistFragment.videos_pos.get(position);
        //TextView pos = (TextView) vi.findViewById(R.id.cur_Pos);
        //pos.setText(new Integer(inic_pos).toString());
        //int inic_pos =  Integer.parseInt(pos.getText().toString());

        final View finalVi = vi;
        DecimalFormat twoDig=new DecimalFormat("0.00");
        String aux = twoDig.format(item.valor);
        //ResourceId rId = getItem(position).get(inic_pos).getId();
        TextView oneProduct = (TextView) vi.findViewById(R.id.priceOneProduct);
        oneProduct.setText(aux);
        EditText priceOne = (EditText) vi.findViewById(R.id.numberConsumedProducts);
        priceOne.setText("");
        TextView desc = (TextView) vi.findViewById(R.id.nameProduct);
        desc.setText(item.desc);
        TextView qtde = (TextView) vi.findViewById(R.id.totalProductsNumber);
        //if ( qtde != null)
        qtde.setText(Integer.toString(item.qtde));

        return vi;
    }

}
