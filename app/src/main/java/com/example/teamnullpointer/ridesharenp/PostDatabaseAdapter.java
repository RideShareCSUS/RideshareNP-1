package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by eric on 11/15/15.
 */
public class PostDatabaseAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public PostDatabaseAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(PostDatabase object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        PostDatabaseHolder postdatabaseholder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            postdatabaseholder = new PostDatabaseHolder();
            postdatabaseholder.tx_description = (TextView) row.findViewById(R.id.tx_description);
            row.setTag(postdatabaseholder);
        } else {
            postdatabaseholder = (PostDatabaseHolder) row.getTag();
        }

        PostDatabase postdatabase = (PostDatabase) this.getItem(position);
        postdatabaseholder.tx_description.setText(postdatabase.getDescription());
        return row;
    }

    static class PostDatabaseHolder {
        TextView tx_description;
    }
}
