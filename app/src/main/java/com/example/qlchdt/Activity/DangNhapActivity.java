package com.example.qlchdt.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.qlchdt.Dialog_Progress;
import com.example.qlchdt.ManHinhChinhActivity;
import com.example.qlchdt.Model.User;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends Activity {

    EditText edtDNUser,edtPass;
    Button btnDN;
    CheckBox cbLuuMK;
    SharedPreferences sharedPreferences;
    public static User userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        sharedPreferences=getSharedPreferences("dataLogin",MODE_PRIVATE);//Khởi tạo 1 file
        // lấy giá trị sharedPreferences
        edtDNUser.setText(sharedPreferences.getString("TaiKhoan",""));
        edtPass.setText(sharedPreferences.getString("MatKhau",""));
        cbLuuMK.setChecked(sharedPreferences.getBoolean("checked",false));
//        if(!edtPass.getText().toString().equals("") && !edtDNUser.getText().toString().equals("")){
//            CheckDangNhap();
//        }
        events();
    }


    private void events() {
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDangNhap();
            }
        });

    }

    private void init() {
        edtDNUser=(EditText) findViewById(R.id.edtDNUser);
        edtPass=(EditText) findViewById(R.id.edtPass);
        btnDN=findViewById(R.id.btnDN);
        cbLuuMK=findViewById(R.id.cbLuuMK);

    }
    private void CheckDangNhap(){
        Dialog_Progress dialog_progress=new Dialog_Progress(DangNhapActivity.this);
        dialog_progress.show();

        String tk=edtDNUser.getText().toString();
        String mk=edtPass.getText().toString();
//        Log.d("AAA", "tk: "+tk+"-mk"+mk);
        User user=new User(tk,mk);
        Dataservice dataservice= APIService.getService();
        Call<User> call=dataservice.loginUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                userLogin=response.body();
//                Log.d("AAA", "onResponse: "+response.body());
                if(userLogin!=null){
                    if(userLogin.getToken()!=null ) {
//                        Log.d("AAA", "token log: "+response.body().getToken());
//                            Toast.makeText(Activity_DangNhap.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("TaiKhoan",tk);
                            editor.commit();
                            if(cbLuuMK.isChecked()){
                                editor.putString("MatKhau",mk);
                                editor.putBoolean("checked",true);
                                editor.commit();
                            }
                            else {
                                editor.remove("MatKhau");
                                editor.remove("checked");
                                editor.commit();
                            }
                            startActivity(new Intent(DangNhapActivity.this,ManHinhChinhActivity.class));
                            finish();
                            dialog_progress.cancel();


                    }
                    else {
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập không thành công. Token null", Toast.LENGTH_SHORT).show();
                        dialog_progress.cancel();
                    }
                }else {
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập không thành công. login null", Toast.LENGTH_SHORT).show();
                    dialog_progress.cancel();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("err", "DangNhap: "+t.getMessage());
            }
        });

    }
}
