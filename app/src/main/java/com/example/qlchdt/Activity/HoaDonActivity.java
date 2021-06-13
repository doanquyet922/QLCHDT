package com.example.qlchdt.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toast;

import com.example.qlchdt.Adapter.AdapteSanPham;
import com.example.qlchdt.Adapter.AdepterHoaDon;
import com.example.qlchdt.Dialog_Progress;
import com.example.qlchdt.Model.HoaDon;
import com.example.qlchdt.Model.ReturnAPISearchHD;
import com.example.qlchdt.Model.ReturnApiSearchSP;
import com.example.qlchdt.Model.SanPham;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoaDonActivity extends AppCompatActivity {


    ImageButton bThem;
    ListView lv;
    ImageView imageView;
    ArrayList<HoaDon> list=new ArrayList<>();
    ArrayList<HoaDon> listTK=new ArrayList<>();
    String hoten="";
    int page=1;
    int pageSize=10;
    int soSP=10;
    private boolean userScrolled=false;
    AdepterHoaDon adapterHoaDon;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        init();
        readData(hoten,page,pageSize);
        events();
    }
    private void events() {
        bThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HoaDonActivity.this,ThemHoaDonActivity.class);
                startActivity(intent);
                finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hoten=query;
                page=1;
                list.clear();
                readData(hoten,page,pageSize);
//                ReturnAPISearchHD returnAPISearchHD=new ReturnAPISearchHD(hoten,page,pageSize);
//                Dialog_Progress dp=new Dialog_Progress(HoaDonActivity.this);
//                dp.show();
//                Dataservice dataservice=APIService.getService();
//                Call<ReturnAPISearchHD> call=dataservice.searchHoaDon("Bearer "+DangNhapActivity.userLogin.getToken(),returnAPISearchHD);
//                call.enqueue(new Callback<ReturnAPISearchHD>() {
//                    @Override
//                    public void onResponse(Call<ReturnAPISearchHD> call, Response<ReturnAPISearchHD> response) {
//                        ArrayList<HoaDon> arrhd= (ArrayList<HoaDon>) response.body().getData();
//                        if(arrhd!=null ){
//                            list.clear();
//                            list.addAll(arrhd);
//                            adapterHoaDon.notifyDataSetChanged();
//                            dp.cancel();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ReturnAPISearchHD> call, Throwable t) {
//                        Toast.makeText(HoaDonActivity.this, "Lỗi get search", Toast.LENGTH_SHORT).show();
//                    dp.cancel();
//                    }
//                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                hoten="";
                page=1;
                list.clear();
                readData(hoten,page,pageSize);
                return false;
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // If scroll state is touch scroll then set userScrolled
                // true
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
// Now check if userScrolled is true and also check if
                // the item is end then update list view and set
                // userScrolled to false
                if (userScrolled && firstVisibleItem + visibleItemCount == totalItemCount) {
                    userScrolled = false;
//                    updateListView();
                    if(soSP==pageSize){
                        page = page + 1;
                        readData(hoten, page, pageSize);}
                }
            }
        });



    }

    private void readData(String inTen,int inpage,int inpageSize) {

        Dialog_Progress dp=new Dialog_Progress(HoaDonActivity.this);
        dp.show();
        ReturnAPISearchHD returnApiSearchHD=new ReturnAPISearchHD(inTen,inpage,inpageSize);
        String token=DangNhapActivity.userLogin.getToken();
        if(token!=null) {
//            Log.d("AAA", "token: "+token);
            Dataservice dataservice = APIService.getService();
            Call<ReturnAPISearchHD> call = dataservice.searchHoaDon("Bearer " + token, returnApiSearchHD);
            call.enqueue(new Callback<ReturnAPISearchHD>() {
                @Override
                public void onResponse(Call<ReturnAPISearchHD> call, Response<ReturnAPISearchHD> response) {

                    ArrayList<HoaDon> lhd = (ArrayList<HoaDon>) response.body().getData();
                    if (lhd != null) {
                        Log.d("AAA", "Size: "+lhd.size());
                        soSP = lhd.size();
//                        Log.d("AAA", "to: "+soSP);
                        list.addAll(lhd);
                        adapterHoaDon.notifyDataSetChanged();
                        dp.cancel();
                    }

                }

                @Override
                public void onFailure(Call<ReturnAPISearchHD> call, Throwable t) {
                    Toast.makeText(HoaDonActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    dp.cancel();
                }
            });
        }
    }

    private void init() {
        searchView= findViewById(R.id.edtk);

        bThem=(ImageButton) findViewById(R.id.bThem);
        lv=(ListView) findViewById(R.id.lv);
        imageView=(ImageView)findViewById(R.id.imageView);
        adapterHoaDon=new AdepterHoaDon(HoaDonActivity.this,list);
        lv.setAdapter(adapterHoaDon);

    }
    public boolean checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            System.out.println("Chuỗi nhập vào không phải là số!");
            return false;
        } else if (number.length() == 10) {

            if (number.substring(0, 1).equals("0")) {
                System.out.println("Số điện thoại hợp lệ");
                return true;
            } else {
                System.out.println("số điện thoại không hợp lệ!");
                return false;
            }

        } else {
            System.out.println("Độ dài chuỗi không hợp lệ!");
            return false;
        }
    }
}