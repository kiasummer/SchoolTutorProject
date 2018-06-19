package com.nn.kovaleva.irina.schooltutor.UI.resurces;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.ChatMessage;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<ChatMessage> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public static final String TAG = "MessageAdapter";

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    public MessageAdapter() {
    }

    public MessageAdapter(List<User> users, List<ChatMessage> messages) {
        this.users = users;
        this.messages = messages;
    }

    public void setItems(Collection<User> users, Collection<ChatMessage> messages) {
        Log.d(TAG, "setItems: ");
        this.users.clear();
        this.messages.clear();
        this.users.addAll(users);
        this.messages.addAll(messages);
        //notifyItemRangeInserted(0, users.size());
        notifyDataSetChanged();
    }

    public List<User> getUsers() {
        Log.d(TAG, "getUsers: ");
        return users;
    }

    public void clearItems() {
        Log.d(TAG, "clearItems: ");
        users.clear();
        messages.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.bind(users.get(position), messages.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "ChatViewHolder";
        private TextView fio, messageField, date;
        RelativeLayout relativeLayout;
        ImageView newMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "MessageViewHolder: ");
            fio = itemView.findViewById(R.id.fio_row_message);
            messageField = itemView.findViewById(R.id.message_text);
            relativeLayout = itemView.findViewById(R.id.message_item_parent);
            newMessage = itemView.findViewById(R.id.new_message_icon_message);
            date = itemView.findViewById(R.id.message_date);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(User user, ChatMessage message) {
            Log.d(TAG, "bind: ");
            if (!user.firstName.equals("") || !user.secondName.equals("")) {
                fio.setText(user.firstName + " " + user.secondName);
            }
            DateFormat md = new SimpleDateFormat("hh:mm dd MMMM, yyyy", Locale.ENGLISH);
            date.setText(md.format(message.date));
//            if (message.author_id == Actor.getsInstance().id){
//                relativeLayout.setGravity(Gravity.END);
//            } else {
//                relativeLayout.setGravity(Gravity.START);
//            }
            if (!message.text.equals("")){
                messageField.setText(message.text);
                if (!message.ifRead){
                    //newMessage.setVisibility(View.VISIBLE);
                    //доделать здесь для непрочитанных сообщений
                    if (message.client_id != Actor.getsInstance().id){
                        newMessage.setVisibility(View.VISIBLE);
                    } else {
                        newMessage.setVisibility(View.GONE);
                    }
                    //relativeLayout.setBackgroundColor(R.color.notReadMessage);
                    //lastMessage.setBackgroundColor(R.color.notReadMessage);
                } else {
                    newMessage.setVisibility(View.GONE);
                }
            }
        }
    }
}
