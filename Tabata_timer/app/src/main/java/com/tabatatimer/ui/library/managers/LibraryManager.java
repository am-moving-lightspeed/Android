package com.tabatatimer.ui.library.managers;


import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tabatatimer.R;
import com.tabatatimer.managers.RecyclerViewItemManager;



public class LibraryManager extends RecyclerViewItemManager {

    public LibraryManager(Context context,
                          RecyclerView.LayoutManager manager) {

        super(context, manager);
    }


    @Override
    public void applyStyleDefault(int position, @IdRes int... idRes) {

        View view = mLayoutManager.findViewByPosition(position);

        if (view != null) {

            view.findViewById(idRes[0])
                .setBackground(
                    ResourcesCompat.getDrawable(
                        mContext.getResources(),
                        R.drawable.item_bg_default,
                        mContext.getTheme()
                    )
                );

            view.findViewById(idRes[1])
                .setVisibility(View.GONE);
        }
    }

}
