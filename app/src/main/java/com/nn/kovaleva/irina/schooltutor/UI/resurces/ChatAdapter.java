package com.nn.kovaleva.irina.schooltutor.UI.resurces;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<User> users = new ArrayList<>();
    private List<ChatMessage> messages = new ArrayList<>();

    public static final String TAG = "ChatAdapter";

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    public ChatAdapter() {
    }

    public ChatAdapter(List<User> users, List<ChatMessage> messages) {
        this.users = users;
        this.messages = messages;
    }

    public void setItems(Collection<User> users, Collection<ChatMessage> messages) {
        Log.d(TAG, "setItems: ");
        this.users.clear();
        this.messages.clear();
        this.users.addAll(users);
        this.messages.addAll(messages);
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
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.bind(users.get(position), messages.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "ChatViewHolder";
        private TextView fio, lastMessage;
        RelativeLayout relativeLayout;
        TextView newMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ChatViewHolder: ");
            fio = itemView.findViewById(R.id.fio_row_chat);
            lastMessage = itemView.findViewById(R.id.last_message);
            relativeLayout = itemView.findViewById(R.id.chat_item_parent);
            newMessage = itemView.findViewById(R.id.new_message_icon);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(User user, ChatMessage message) {
            Log.d(TAG, "bind: ");
            if (!user.firstName.equals("") || !user.secondName.equals("")) {
                fio.setText(user.firstName + " " + user.secondName);
            }
            if (!message.text.equals("")){
                lastMessage.setText(message.text);
                if (!message.ifRead){
                    //доделать здесь для непрочитанных сообщений
                    if (message.client_id == Actor.getsInstance().id){
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