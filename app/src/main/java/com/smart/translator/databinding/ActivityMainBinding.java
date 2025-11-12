package com.smart.translator.databinding;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
public class ActivityMainBinding {
    public final View root; public final Spinner spinnerEngine; public final Button btnStart;
    private ActivityMainBinding(View r, Spinner s, Button b){ this.root=r; this.spinnerEngine=s; this.btnStart=b; }
    public static ActivityMainBinding inflate(LayoutInflater inflater){ View v=inflater.inflate(com.smart.translator.R.layout.activity_main,null); Spinner sp=v.findViewById(com.smart.translator.R.id.spinnerEngine); Button bt=v.findViewById(com.smart.translator.R.id.btnStart); return new ActivityMainBinding(v, sp, bt); }
}
