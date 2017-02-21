package com.eeyuva.di.module;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 13/7/16.
 */
@Module
public class FragmentModule {
    private final FragmentActivity fragment;

    public FragmentModule(FragmentActivity fragment) {
        this.fragment = fragment;
    }

    /**
     * Expose the fragment to dependents in the graph.
     */
    @Provides
    FragmentActivity fragment() {
        return this.fragment;
    }
}
