/*
 * Aurora Store
 * Copyright (C) 2019, Rahul Kumar Patel <whyorean@gmail.com>
 *
 * Aurora Store is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aurora Store is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aurora Store.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.aurora.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aurora.store.Constants;
import com.aurora.store.R;
import com.aurora.store.utility.PrefUtil;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private onClickListener listener;
    private ArrayList<String> queryList;
    private Context context;

    public SearchHistoryAdapter(Context context, ArrayList<String> queryList) {
        this.queryList = queryList;
        this.context = context;
    }

    public ArrayList<String> getQueryList() {
        return queryList;
    }

    public void add(int position, String mHistory) {
        queryList.add(mHistory);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        queryList.remove(position);
        updatePrefList();
        notifyItemRemoved(position);
    }

    public void clear() {
        queryList.clear();
        clearPrefList();
        notifyDataSetChanged();
    }

    public void reload() {
        queryList = PrefUtil.getListString(context, Constants.RECENT_HISTORY);
        notifyDataSetChanged();
    }

    private void updatePrefList() {
        PrefUtil.putListString(context, Constants.RECENT_HISTORY, queryList);
    }

    private void clearPrefList() {
        PrefUtil.putListString(context, Constants.RECENT_HISTORY, new ArrayList<>());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String[] mDatedQuery = queryList.get(position).split(":");
        holder.query.setText(mDatedQuery[0]);
        holder.date.setText(getQueryDate(mDatedQuery[1]));
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    public void setOnItemClickListener(onClickListener clickListener) {
        this.listener = clickListener;
    }

    private String getQueryDate(String queryDate) {
        try {
            final long timeInMilli = Long.parseLong(queryDate);
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
            return simpleDateFormat.format(new Date(timeInMilli));
        } catch (NumberFormatException e) {
            return StringUtils.EMPTY;
        }
    }

    public interface onClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public RelativeLayout viewForeground;
        RelativeLayout viewBackground;
        TextView query;
        TextView date;

        ViewHolder(View view) {
            super(view);
            query = view.findViewById(R.id.query);
            date = view.findViewById(R.id.queryTime);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }
}
