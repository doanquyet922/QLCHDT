package com.example.qlchdt.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlchdt.Adapter.AdapteSanPham;
import com.example.qlchdt.Dialog_Progress;
import com.example.qlchdt.Model.ReturnApiSearchSP;
import com.example.qlchdt.Model.SanPham;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SanPhamActivity extends AppCompatActivity {
    TextView tvThem;
    Toolbar toolbar;
    ListView listView;
    ImageView imRe;
    SearchView searchView;
ArrayList<SanPham> dssp=new ArrayList<>();
    String ten="";
    int page=1;
    int pageSize=10;
    int soSP=10;
    AdapteSanPham sanPhamArrayAdapter;
    private boolean userScrolled=false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        init();
        getDSSP(ten,page,pageSize);
        events();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void events() {
        tvThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamActivity.this,ThemSanPhamActivity.class));
                finish();
            }
        });
        imRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d=dssp.size();
                dssp.clear();
                ten="";
                page=1;
                searchView.setQuery("",false);
                getDSSP(ten,page,pageSize);

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ten=searchView.getQuery().toString();
                dssp.clear();
                page=1;
                getDSSP(ten,page,pageSize);
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                        getDSSP(ten, page, pageSize);}
                }
            }
        });

    }

    private void init() {
        imRe=findViewById(R.id.SP_refrech);
        tvThem=findViewById(R.id.SP_tvThem);
        toolbar=findViewById(R.id.SP_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh sách sản phẩm");
        searchView=findViewById(R.id.searchview);
        listView=findViewById(R.id.SP_listview);
        sanPhamArrayAdapter=new AdapteSanPham(SanPhamActivity.this, dssp);
        listView.setAdapter(sanPhamArrayAdapter);

    }

    private void getDSSP(String inTen,int inpage,int inpageSize) {
        Dialog_Progress dp=new Dialog_Progress(SanPhamActivity.this);
        dp.show();
        ReturnApiSearchSP returnApiSearchSP=new ReturnApiSearchSP(inTen,inpage,inpageSize);
        String token=DangNhapActivity.userLogin.getToken();
        if(token!=null) {
//            Log.d("AAA", "token: "+token);
            Dataservice dataservice = APIService.getService();
            Call<ReturnApiSearchSP> call = dataservice.searchSanpham("Bearer " + token, returnApiSearchSP);
            call.enqueue(new Callback<ReturnApiSearchSP>() {
                @Override
                public void onResponse(Call<ReturnApiSearchSP> call, Response<ReturnApiSearchSP> response) {
                    ArrayList<SanPham> lsp = (ArrayList<SanPham>) response.body().getLsp();
                    if (lsp != null) {
                        soSP = lsp.size();
//                        Log.d("AAA", "to: "+soSP);
                        dssp.addAll(lsp);
                        sanPhamArrayAdapter.notifyDataSetChanged();
                        dp.cancel();
                    }

                }

                @Override
                public void onFailure(Call<ReturnApiSearchSP> call, Throwable t) {
                    Toast.makeText(SanPhamActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    dp.cancel();
                }
            });
        }
    }
}