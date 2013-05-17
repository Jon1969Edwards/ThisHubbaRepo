package com.android.hubbahubbanew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class MySpotsFragment extends SherlockFragment {

 
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
   Bundle savedInstanceState) {
   View myFragmentView = inflater.inflate(R.layout.myspots_layout, container, false);
  return myFragmentView;
 }

}
