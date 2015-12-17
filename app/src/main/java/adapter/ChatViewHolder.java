package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.teamnullpointer.ridesharenp.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    LinearLayout chatHolder;
    TextView username;
    TextView message;

    public ChatViewHolder(View itemView) {
        super(itemView);

        username = (TextView) itemView.findViewById(R.id.username);
        message = (TextView) itemView.findViewById(R.id.message);
        chatHolder = (LinearLayout) itemView.findViewById(R.id.chatholder);
    }
}