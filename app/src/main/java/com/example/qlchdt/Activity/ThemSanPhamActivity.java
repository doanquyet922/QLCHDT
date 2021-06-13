 package com.example.qlchdt.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlchdt.Dialog_Progress;
import com.example.qlchdt.Model.SanPham;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSanPhamActivity extends AppCompatActivity {

    Button bLuu;
    TextView tMaSP;
    EditText edTenSP,edTenHang,edGiaNhap,edGiaBan,edSoluong,edTGBH,edCauHinh;
    ImageView imSanPham;
    boolean checkSua=false;
    String maSP="";
    String Anh="";
    final int RESQUEST_TAKE_PHOTO=123;
    final int RESQUEST_CHOOSE_PHOTO=321;
    String realpath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        init();
        getDataSua();

        event();
    }

    private void getDataSua() {
        Intent it=getIntent();
        if(it.hasExtra("maSP")){
            checkSua=true;
            String  masp=it.getStringExtra("maSP");
            Dialog_Progress dp=new Dialog_Progress(ThemSanPhamActivity.this);
            dp.show();
            Dataservice dataservice=APIService.getService();
            Call<SanPham> call=dataservice.getById("Bearer "+DangNhapActivity.userLogin.getToken(),masp);
            call.enqueue(new Callback<SanPham>() {
                @Override
                public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                    SanPham sp=response.body();
                    if(sp!=null){
                        maSP=sp.getMaSp();
                        Anh=sp.getAnh();
                        if(!sp.getAnh().equals("")){
                            String []file=sp.getAnh().split("\\.");
                            Picasso.get().load(APIService.base_url+"sanpham/GetImage/"+file[0]).into(imSanPham);
                        }
                        edTenSP.setText(sp.getTenSp());
                        edTenHang.setText(sp.getTenHang());
                        edGiaBan.setText(sp.getGiaBan()+"");
                        edGiaNhap.setText(sp.getGiaNhap()+"");
                        edSoluong.setText(sp.getSoLuong()+"");
                        edTGBH.setText(sp.getTgBh()+"");
                        edCauHinh.setText(sp.getCauHinh());
                        dp.cancel();
                    }
                }

                @Override
                public void onFailure(Call<SanPham> call, Throwable t) {
                    Toast.makeText(ThemSanPhamActivity.this, "Erro: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    dp.cancel();
                }
            });
        }
    }

    private void event() {
        imSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        if(edTenSP.getText().toString().equals("")){
            edTenSP.setError("Tên sản phẩm khác rỗng");
        }
        edTenSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    edTenSP.setError(" Tên sản phẩm khác rỗng");
                }else edTenSP.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(edTenHang.getText().toString().equals("")){
            edTenHang.setError("Tên hãng khác rỗng");
        }
        edTenHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    edTenHang.setError(" Tên hãng khác rỗng");
                }else edTenHang.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edGiaNhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    edGiaNhap.setError(" Giá nhập khác rỗng");
                }else edGiaNhap.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edGiaBan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    edGiaBan.setError(" Giá bán khác rỗng");
                }else edGiaBan.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edSoluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    edSoluong.setError(" Số lượng khác rỗng");
                }else edSoluong.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edTGBH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    edTGBH.setError(" Thời gian bảo hành khác rỗng");
                }else edTGBH.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(edCauHinh.getText().toString().equals("")){
            edCauHinh.setError("Cấu hình khác rỗng");
        }
        edCauHinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    edCauHinh.setError(" Cấu hình khác rỗng");
                }else edCauHinh.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Chon hinh

//        bChupHinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePicture();
//            }
//        });
        //Tăng Mã sản phẩm lên 1
//        SQLiteDatabase database=Database.initDatabase(this,database_name);
//        Cursor cursor=database.rawQuery("select * from SanPham",null);
//        cursor.moveToLast();
//        String MaSPCuoiCung=cursor.getString(0).toString().trim();
//        int x = Integer.parseInt(MaSPCuoiCung.substring(2));
//        x = x + 1;
//        MaSPTiep = "SP" + x;
//        tMaSP.setText(MaSPTiep+"");
        //
        bLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSua==false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ThemSanPhamActivity.this);
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setTitle("Xác nhận thêm");
                    builder.setMessage("Bán có chắc chắn muốn thêm?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Them();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ThemSanPhamActivity.this);
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setTitle("Xác nhận sửa");
                    builder.setMessage("Bán có chắc chắn muốn sửa?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Sua();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
//        bHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Huy();
//            }
//        });
    }

    private void init() {

        bLuu=(Button)findViewById(R.id.bLuu);
//        bHuy=(Button)findViewById(R.id.bHuy);
        tMaSP=(TextView) findViewById(R.id.tMaSP);
        edTenSP=(EditText) findViewById(R.id.edTenSP);
        edTenHang=(EditText) findViewById(R.id.edTenHang);
        edGiaNhap=(EditText) findViewById(R.id.edGiaNhap);
        edGiaBan=(EditText) findViewById(R.id.edGiaBan);
        edSoluong=(EditText) findViewById(R.id.edSoluong);
        edTGBH=(EditText) findViewById(R.id.edTGBH);
        edCauHinh=(EditText) findViewById(R.id.edCauHinh);
        imSanPham=(ImageView) findViewById(R.id.imSanPham);
    }
//    private void takePicture(){
//        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,RESQUEST_TAKE_PHOTO);
//    }
    private void choosePhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,RESQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                Uri imageUri = data.getData();
                realpath = getRealPathFromURI(imageUri);
                try {
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imSanPham.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
//            else if (requestCode == RESQUEST_TAKE_PHOTO) {
//
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                imSanPham.setImageBitmap(bitmap);
//            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
    private void Them(){
        Dialog_Progress dp=new Dialog_Progress(ThemSanPhamActivity.this);
        dp.show();
        if(!edTenSP.getText().toString().equals("") && !edTenHang.getText().toString().equals("") && Double.parseDouble(edGiaNhap.getText().toString())>=0 &&
                Double.parseDouble(edGiaBan.getText().toString())>=0 && Integer.parseInt(edSoluong.getText().toString())>=0 &&
                Integer.parseInt(edTGBH.getText().toString())>=0 && !edCauHinh.getText().toString().equals("")){
            String TenSP=edTenSP.getText().toString();
            String TenHang=edTenHang.getText().toString();
            int GiaNhap=Integer.parseInt(edGiaNhap.getText().toString());
            int GiaBan=Integer.parseInt(edGiaBan.getText().toString());
            int Soluong=Integer.parseInt(edSoluong.getText().toString());
            int TGBH=Integer.parseInt(edTGBH.getText().toString());
            String CauHinh=edCauHinh.getText().toString();
            String Anh="";
            Dataservice dataservice = APIService.getService();
            if(!realpath.equals("")) {
                File file = new File(realpath);
                String file_path = file.getAbsolutePath();

                String[] tenfile1 = file_path.split("/");
                //        Log.d("FILE_PATH", file_path);
                //trường hợp trùng tên file thì + thêm thời gian vào tên file
                String[] tenfile2 = tenfile1[tenfile1.length - 1].split("\\.");

                //Gán vào ảnh
                tenfile1[tenfile1.length - 1] = tenfile2[0] + System.currentTimeMillis() + "." + tenfile2[1];
                Anh = tenfile1[tenfile1.length - 1];
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("files", Anh, requestBody);

                //API ThemAnh

                Call<String> call = dataservice.UploadPhoto(body);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
//                        Log.d("AAA", "onResponse: " + response.toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
//                    finish();
//                        dp.cancel();
                        Toast.makeText(ThemSanPhamActivity.this, "oh fail Image! " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("AAA", "onFailure: " + t.getMessage());

                    }
                });
            }
            SanPham sp=new SanPham(TenSP,TenHang,CauHinh,Anh,Soluong,TGBH,GiaNhap,GiaBan);
            String token=DangNhapActivity.userLogin.getToken();
            Call<ResponseBody> callThem=dataservice.ThemSP("Bearer " + token,sp);
            callThem.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(ThemSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ThemSanPhamActivity.this,SanPhamActivity.class));
                    finish();
                    dp.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
//            finish();

        }
        else Toast.makeText(ThemSanPhamActivity.this,"Không hợp lệ!",Toast.LENGTH_LONG).show();

    }
    private void Sua(){
        Dialog_Progress dp=new Dialog_Progress(ThemSanPhamActivity.this);
        dp.show();
        if(!edTenSP.getText().toString().equals("") && !edTenHang.getText().toString().equals("") && Double.parseDouble(edGiaNhap.getText().toString())>=0 &&
                Double.parseDouble(edGiaBan.getText().toString())>=0 && Integer.parseInt(edSoluong.getText().toString())>=0 &&
                Integer.parseInt(edTGBH.getText().toString())>=0 && !edCauHinh.getText().toString().equals("")){
            String TenSP=edTenSP.getText().toString();
            String TenHang=edTenHang.getText().toString();
            int GiaNhap=Integer.parseInt(edGiaNhap.getText().toString());
            int GiaBan=Integer.parseInt(edGiaBan.getText().toString());
            int Soluong=Integer.parseInt(edSoluong.getText().toString());
            int TGBH=Integer.parseInt(edTGBH.getText().toString());
            String CauHinh=edCauHinh.getText().toString();

            Dataservice dataservice = APIService.getService();
            if(!realpath.equals("")) {

                Call<Void> callDe=dataservice.DeleteImage(Anh);
                callDe.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                File file = new File(realpath);
                String file_path = file.getAbsolutePath();

                String[] tenfile1 = file_path.split("/");
                //        Log.d("FILE_PATH", file_path);
                //trường hợp trùng tên file thì + thêm thời gian vào tên file
                String[] tenfile2 = tenfile1[tenfile1.length - 1].split("\\.");

                //Gán vào ảnh
                tenfile1[tenfile1.length - 1] = tenfile2[0] + System.currentTimeMillis() + "." + tenfile2[1];
                Anh = tenfile1[tenfile1.length - 1];
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("files", Anh, requestBody);

                //API ThemAnh

                Call<String> call = dataservice.UploadPhoto(body);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
//                        Log.d("AAA", "onResponse: " + response.toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
//                    finish();
//                        dp.cancel();
                        Toast.makeText(ThemSanPhamActivity.this, "oh fail Image! " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("AAA", "onFailure: " + t.getMessage());

                    }
                });
            }
            SanPham sp=new SanPham(maSP,TenSP,TenHang,CauHinh,Anh,Soluong,TGBH,GiaNhap,GiaBan);
            String token=DangNhapActivity.userLogin.getToken();
            Call<ResponseBody> callThem=dataservice.SuaSP("Bearer " + token,sp);
            callThem.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(ThemSanPhamActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ThemSanPhamActivity.this,SanPhamActivity.class));
                    finish();
                    dp.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
//            finish();

        }
        else Toast.makeText(ThemSanPhamActivity.this,"Không hợp lệ!",Toast.LENGTH_LONG).show();

    }
    public void Huy(){
        startActivity(new Intent(ThemSanPhamActivity.this,SanPhamActivity.class));
        finish();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code here
            Huy();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}