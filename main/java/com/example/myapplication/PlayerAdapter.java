package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends BaseAdapter {
    private Context mContext;
    DBHelper dbHelper;
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<Bitmap> Image = new ArrayList<Bitmap>();
    private ArrayList<String> Wins = new ArrayList<String>();
    private ArrayList<String> Losts = new ArrayList<String>();
    private ArrayList<String> Cash = new ArrayList<String>();
    public PlayerAdapter(Context  context,ArrayList<String> Id,ArrayList<String> Name, ArrayList<Bitmap> Image, ArrayList<String> Wins,ArrayList<String> Losts,ArrayList<String> Cash
    )
    {
        this.mContext = context;
        this.Id = Id;
        this.Name = Name;
        this.Image = Image;
        this.Wins= Wins;
        this.Losts= Losts;
        this.Cash= Cash;
    }
    @Override
    public int getCount() {
        return Id.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final    viewHolder holder;
        dbHelper = new DBHelper(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout, null);
            holder = new viewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.tvid);
            holder.name = (TextView) convertView.findViewById(R.id.tvname);
            holder.image = (ImageView) convertView.findViewById(R.id.ivimage);
            holder.wins = (TextView) convertView.findViewById(R.id.tvwins);
            holder.losts = (TextView) convertView.findViewById(R.id.tvlosts);
            holder.cash = (TextView) convertView.findViewById(R.id.tvcash);

            holder.wins.setGravity(Gravity.RIGHT|Gravity.END|Gravity.CENTER_VERTICAL);
            holder.losts.setGravity(Gravity.RIGHT|Gravity.END|Gravity.CENTER_VERTICAL);
            holder.cash.setGravity(Gravity.RIGHT|Gravity.END|Gravity.CENTER_VERTICAL);

            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.id.setText(Id.get(position));
        holder.name.setText(Name.get(position));
        holder.image.setImageBitmap(Image.get(position));
        holder.wins.setText(Wins.get(position));
        holder.losts.setText(Losts.get(position));
        holder.cash.setText(Cash.get(position));

        return convertView;
    }
    public class viewHolder {
        TextView id;
        TextView name;
        ImageView image;
        TextView wins;
        TextView losts;
        TextView cash;
    }
}

