package com.tabatatimer.adapters;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public abstract class RecyclerViewAdapterAbstract<VH extends RecyclerView.ViewHolder> extends
                                                                                      RecyclerView.Adapter<VH> {

    @NonNull
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);
    public abstract int getItemCount();
    public abstract void onBindViewHolder(@NonNull VH holder, int position);
    public abstract void deleteItem(int position);
    public abstract void resolveItemClickEvent(int position);

}
