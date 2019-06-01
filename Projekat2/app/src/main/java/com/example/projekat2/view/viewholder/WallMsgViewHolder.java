package com.example.projekat2.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.projekat2.R;
import com.example.projekat2.model.Message;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class WallMsgViewHolder extends MessageAbstractViewHolder {

    private TextView tvName;
    private TextView tvDate;
    private TextView tvText;
    private ImageView ivImage;

    public WallMsgViewHolder(@NonNull View v) {
        super(v);

    }

    @Override
    public void getReferences(View v) {
        tvName = v.findViewById(R.id.tv_wall_msg_name);
        tvDate = v.findViewById(R.id.tv_wall_msg_date);
        tvText = v.findViewById(R.id.tv_wall_msg_text);
        ivImage = v.findViewById(R.id.iv_wall_msg_image);
    }

    @Override
    public void bind(Message message, HashMap<String,String> indexNameMap) {
        String name = indexNameMap.containsKey(message.getSender()) ?
                indexNameMap.get(message.getSender()) : "Unknown";
        tvName.setText(name);
        tvDate.setText(message.getTime());

        switch (message.getType()) {
            case TEXT:
                tvText.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.GONE);
                tvText.setText(message.getContent());
                break;
            case IMAGE:
                tvText.setVisibility(View.GONE);
                ivImage.setVisibility(View.VISIBLE);
                Picasso.get().load(message.getContent()).into(ivImage);
                break;
            default:
                break;
        }
    }
}
