package com.nn.kovaleva.irina.schooltutor.UI.resurces;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<User> users = new ArrayList<>();
    
    public static final String TAG = "SearchAdapter";
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_person_item1, parent, false);
        return new SearchViewHolder(view);
    }

    public SearchAdapter() {
    }

    public SearchAdapter(List<User> users) {
        this.users = users;
    }

    public void setItems(Collection<User> users){
        Log.d(TAG, "setItems: ");
        this.users.clear();
        this.users.addAll(users);
        //notifyItemRangeInserted(0, users.size());
        notifyDataSetChanged();
    }

    public List<User> getUsers() {
        Log.d(TAG, "getUsers: ");
        return users;
    }

    public void clearItems(){
        Log.d(TAG, "clearItems: ");
        users.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        public static final String TAG = "SearchViewHolder";
        private TextView fio, extraInfo;
        public SearchViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "SearchViewHolder: ");
            fio = itemView.findViewById(R.id.fio_row_search);
            extraInfo = itemView.findViewById(R.id.extra_info_search);
        }

        public void bind(User user){
            Log.d(TAG, "bind: ");
            if (!user.firstName.equals("") || !user.secondName.equals("")) {
                fio.setText(user.firstName + " " + user.secondName);
            }
            ArrayList<String> subjects = user.themes;
            String subj = "";
            for (int i = 0; i < subjects.size(); i ++){
                subj += (i == 0) ? subjects.get(i) : (", " + subjects.get(i));
            }
            if (!subj.equals("")) {
                extraInfo.setText(subj);
            }
        }
    }
}
