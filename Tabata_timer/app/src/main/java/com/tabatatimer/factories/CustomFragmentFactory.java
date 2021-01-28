package com.tabatatimer.factories;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.tabatatimer.ui.library.LibraryFragment;
import com.tabatatimer.ui.sequence.SequenceFragment;
import com.tabatatimer.viewmodels.SharedDbViewModel;



public class CustomFragmentFactory extends FragmentFactory {

    private Context           mContext;
    private SharedDbViewModel mSharedDbVM;


    public CustomFragmentFactory(Context context, SharedDbViewModel viewModel) {

        super();
        mContext    = context;
        mSharedDbVM = viewModel;
    }


    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {

        Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader, className);

        if (fragmentClass.equals(SequenceFragment.class)) {
            return new SequenceFragment(mContext, mSharedDbVM);
        }
        else if (fragmentClass.equals(LibraryFragment.class)) {
            return new LibraryFragment(mContext, mSharedDbVM);
        }
        else {
            return super.instantiate(classLoader, className);
        }
    }

}
