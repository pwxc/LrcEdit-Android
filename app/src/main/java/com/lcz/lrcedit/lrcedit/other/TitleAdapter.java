package com.lcz.lrcedit.lrcedit.other;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcz.lrcedit.lrcedit.R;

import java.util.List;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {

    private List<String> fileNameList;

    private RecycleViewListener recycleViewListener;

    public void setRecycleViewListener(RecycleViewListener recycleViewListener) {
        this.recycleViewListener = recycleViewListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
         public ViewHolder(View view){
             super(view);
             titleTextView = (TextView) view.findViewById(R.id.txtList_recycle_item_textView);
         }
    }

    public TitleAdapter(List<String> fileNameList){
        this.fileNameList = fileNameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.txtlist_recycle_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String title = fileNameList.get(position);
        holder.titleTextView.setText(title);
        if(recycleViewListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    recycleViewListener.OnItemClick(holder.itemView, pos);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }
}
