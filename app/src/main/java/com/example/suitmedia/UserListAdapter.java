package com.example.suitmedia;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
        }

        ImageView imageViewAvatar = convertView.findViewById(R.id.imageViewUser);
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);

        User user = userList.get(position);

        Picasso.get().load(user.getAvatar()).into(imageViewAvatar);
        textViewName.setText(user.getFirstName() + " " + user.getLastName());
        textViewEmail.setText(user.getEmail());

        return convertView;
    }
}
