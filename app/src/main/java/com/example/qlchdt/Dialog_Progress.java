package com.example.qlchdt;

import android.app.Dialog;
import android.content.Context;

public class Dialog_Progress {

    Dialog dialog;

    public Dialog_Progress(Context Context) {
        dialog=new Dialog(Context);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(false);
    }


    public void show(){
        dialog.show();
    }
    public void cancel(){
        dialog.cancel();
    }
}
