package com.example.qlchdt.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlchdt.Adapter.AdapterThemHoaDonBan;
import com.example.qlchdt.Dialog_Progress;
import com.example.qlchdt.FormatNumber;
import com.example.qlchdt.Model.CthoaDon;
import com.example.qlchdt.Model.HoaDon;
import com.example.qlchdt.Model.ModelThemHoaDon;
import com.example.qlchdt.Model.ReturnApiSearchSP;
import com.example.qlchdt.Model.SanPham;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemHoaDonActivity extends AppCompatActivity {
    EditText edHoTen,edDiaChi;
    TextView tHoaDon;
    Button bLuu;
    ImageButton bThemSPBan;
    ListView lv;
    ArrayList<CthoaDon> list=new ArrayList<>();
    AdapterThemHoaDonBan adapterThemHoaDonBan;
    boolean checkEdit=false;
    String maHD="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);
        init();
        getData();
        events();
    }

    private void getData() {

        Intent it=getIntent();


        if(it.hasExtra("MaHDB")==true) {
            tHoaDon.setText("Hóa đơn");
            checkEdit=true;
            String ma=it.getStringExtra("MaHDB");
            Dialog_Progress dp=new Dialog_Progress(ThemHoaDonActivity.this);
            dp.show();
            Dataservice dataservice=APIService.getService();
            Call<HoaDon> call=dataservice.getByIdHoaDon("Bearer "+DangNhapActivity.userLogin.getToken(),ma);
            call.enqueue(new Callback<HoaDon>() {
                @Override
                public void onResponse(Call<HoaDon> call, Response<HoaDon> response) {
                    HoaDon hd=response.body();
                    if(hd!=null){
                        maHD=hd.getMaHdb();
                        edHoTen.setText(hd.getHoten());
                        edDiaChi.setText((hd.getDiaChi()));
                        ArrayList<CthoaDon> dsct= (ArrayList<CthoaDon>) hd.getList();
                        list.addAll(dsct);
                        adapterThemHoaDonBan.notifyDataSetChanged();
                        dp.cancel();
                    }
                }

                @Override
                public void onFailure(Call<HoaDon> call, Throwable t) {
                    Log.d("BBB", "onFailure: "+t.getMessage());
                    dp.cancel();
                }
            });
        }

    }

    private void events() {
        bThemSPBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemSP();
            }
        });
        bLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHD();
            }
        });
    }

    private void LuuHD() {
        if (checkEdit == false) {
            if (!edHoTen.getText().toString().equals("") && !edDiaChi.getText().toString().equals("")) {
                int thanhtien = 0;
                for (CthoaDon ct : list) {
                    thanhtien = thanhtien + ct.getTongTien();
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ThemHoaDonActivity.this);
                alertDialog.setTitle("Thanh toán!");
                alertDialog.setMessage("Số tiền KH phải trả: " + FormatNumber.formatNumber(thanhtien));
                alertDialog.setPositiveButton("Thanh toán", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog_Progress dp = new Dialog_Progress(ThemHoaDonActivity.this);
                        dp.show();
                        String ht = edHoTen.getText().toString().trim();
                        String dc = edDiaChi.getText().toString().trim();
                        HoaDon hd = new HoaDon(ht, dc, list);
                        Dataservice dataservice = APIService.getService();
                        Call<ResponseBody> call = dataservice.themHD("Bearer " + DangNhapActivity.userLogin.getToken(), hd);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            Log.d("BBB", "onResponse: "+response.toString());

                                startActivity(new Intent(ThemHoaDonActivity.this, HoaDonActivity.class));
                                finish();
                                dp.cancel();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d("BBB", "onFailure: " + t.getMessage());
                                dp.cancel();

                            }
                        });
                    }
                });
                alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            } else {
                Toast.makeText(this, "Vui lòng nhập họ tên và địa chỉ", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (!edHoTen.getText().toString().equals("") && !edDiaChi.getText().toString().equals("")) {
                int thanhtien = 0;
                for (CthoaDon ct : list) {
                    int tongtien=ct.getGiaBan()*ct.getSoLuong();
                    thanhtien = thanhtien + tongtien;
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ThemHoaDonActivity.this);
                alertDialog.setTitle("Sửa hóa đơn!");
                alertDialog.setMessage("Số tiền thanh toán: " + FormatNumber.formatNumber(thanhtien));
                alertDialog.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog_Progress dp = new Dialog_Progress(ThemHoaDonActivity.this);
                        dp.show();
                        String ht = edHoTen.getText().toString().trim();
                        String dc = edDiaChi.getText().toString().trim();
                        HoaDon hd = new HoaDon(maHD,ht, dc, list);
                        Dataservice dataservice = APIService.getService();
                        Call<ResponseBody> call = dataservice.suaHD("Bearer " + DangNhapActivity.userLogin.getToken(), hd);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            Log.d("BBB", "onResponse: "+response.toString());

                                startActivity(new Intent(ThemHoaDonActivity.this, HoaDonActivity.class));
                                finish();
                                dp.cancel();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d("BBB", "onFailure: " + t.getMessage());
                                dp.cancel();

                            }
                        });
                    }
                });
                alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            } else {
                Toast.makeText(this, "Vui lòng nhập họ tên và địa chỉ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        edHoTen=findViewById(R.id.tMaNV);
        edDiaChi=findViewById(R.id.edSDTKH);
        bLuu=findViewById(R.id.bLuu);
        bThemSPBan=findViewById(R.id.bThemSP);
        tHoaDon=findViewById(R.id.textView26);
        lv=findViewById(R.id.lv);
        adapterThemHoaDonBan=new AdapterThemHoaDonBan(ThemHoaDonActivity.this,list);
        lv.setAdapter(adapterThemHoaDonBan);
    }
    public void DialogThemSP(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_sp_hdb);
        dialog.setCanceledOnTouchOutside(false);
        //ánh xạ
        final EditText edMaSP=(EditText) dialog.findViewById(R.id.edMaSP);
        final TextView tTenSP=(TextView) dialog.findViewById(R.id.tTenSP);
        final EditText edSoluong=(EditText) dialog.findViewById(R.id.edSoluong);
        final TextView tTongtien=(TextView) dialog.findViewById(R.id.tTongtien);
        Button bChonSP =dialog.findViewById(R.id.bChonSP);
        Button bOK=(Button) dialog.findViewById(R.id.bOK);
        Button bHuy=(Button) dialog.findViewById(R.id.bHuy);
        final int[] tt = {0};
        final int[] gb = {0};
        bChonSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialogChonSP=new Dialog(ThemHoaDonActivity.this);
                dialogChonSP.setContentView(R.layout.dialog_chonsp);
                SearchView searchViewChonSP=dialogChonSP.findViewById(R.id.chonsp_search);
                ListView lvChonsp=dialogChonSP.findViewById(R.id.chonsp_lv);
                ArrayList<SanPham> dssp=new ArrayList<>();
                ArrayAdapter<SanPham> arrayAdapter=new ArrayAdapter<>(ThemHoaDonActivity.this, android.R.layout.simple_list_item_1,dssp);
                lvChonsp.setAdapter(arrayAdapter);
                searchViewChonSP.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Dialog_Progress dp=new Dialog_Progress(ThemHoaDonActivity.this);
                        dp.show();
                        Dataservice dataservice= APIService.getService();
                        ReturnApiSearchSP returnApiSearchSP=new ReturnApiSearchSP(searchViewChonSP.getQuery().toString(),1,1000);
                        String token=DangNhapActivity.userLogin.getToken();
                        Call<ReturnApiSearchSP> call=dataservice.searchSanpham("Bearer " + token,returnApiSearchSP);
                        call.enqueue(new Callback<ReturnApiSearchSP>() {
                            @Override
                            public void onResponse(Call<ReturnApiSearchSP> call, Response<ReturnApiSearchSP> response) {
//                                Log.d("BBB", "lsp: "+response.toString());
                                ArrayList<SanPham> lsp = (ArrayList<SanPham>) response.body().getLsp();
                                if (lsp!=null){
                                    dssp.clear();
                                    dssp.addAll(lsp);
                                    arrayAdapter.notifyDataSetChanged();
                                    dp.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<ReturnApiSearchSP> call, Throwable t) {
                                Toast.makeText(ThemHoaDonActivity.this, "Lỗi get chonSP", Toast.LENGTH_SHORT).show();
                                dp.cancel();
                            }
                        });

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                lvChonsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SanPham sp=dssp.get(position);
                        edMaSP.setText(sp.getMaSp());
                        tTenSP.setText(sp.getTenSp());
                        dialogChonSP.cancel();
                    }
                });

                dialogChonSP.show();
            }
        });
        if (edMaSP.getText().toString().equals("")) {
            edMaSP.setError("Mã sản phẩm khác rỗng");
        }
        edMaSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    edMaSP.setError(" Mã sản phẩm khác rỗng");
                    tTenSP.setText("");
                } else {
                    edMaSP.setError(null);
//                    String TenSP=tTenSP.getText().toString();
//
//
//
//                    if(!TenSP.equals("")){
//
//
//                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tTenSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edSoluong.setText("0");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (edSoluong.getText().toString().equals("")) {
            edSoluong.setError("Số lượng khác rỗng");
        }
        edSoluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tt[0]=0;
                    tTongtien.setText("");
                    edSoluong.setError(" Số lượng >0");

                } else {
                    edSoluong.setError(null);

                    String maSP=edMaSP.getText().toString().trim();
                    Dataservice dataservice=APIService.getService();
                    String token="Bearer "+DangNhapActivity.userLogin.getToken();
                    Call<SanPham> call=dataservice.getById(token,maSP);
                    call.enqueue(new Callback<SanPham>() {
                        @Override
                        public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                              SanPham sp=response.body();
                              if(sp!=null){
                                  int gb1 = 0;
                                  int slcon=0;
                                  slcon=sp.getSoLuong();
                                  int sl=0;
                                  try {
                                       sl = Integer.parseInt(edSoluong.getText().toString());
                                  }catch (Exception e){

                                  }

                                  if(slcon>=sl){
                                      gb1=sp.getGiaBan();
                                      int tt1 = gb1 * sl;
                                      tt[0] =tt1;
                                      gb[0] =gb1;
                                      tTongtien.setText(FormatNumber.formatNumber(tt[0])+"");

                                  }
                                  else {edSoluong.setError("Số lượng quá giới hạn");
                                      Toast.makeText(ThemHoaDonActivity.this,"Số lượng SP còn:"+slcon,Toast.LENGTH_LONG).show();
                                      edSoluong.setText("");
                                  }
                              }
                        }

                        @Override
                        public void onFailure(Call<SanPham> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edMaSP.getText().toString().equals("") && !tTenSP.getText().toString().equals("") && !edSoluong.getText().toString().equals("") &&
                        Integer.parseInt(edSoluong.getText().toString())>0 && tt[0]>0 && gb[0]>0){
                    String MaSP=edMaSP.getText().toString();
                    String TenSP=tTenSP.getText().toString();
                    int sl=Integer.parseInt(edSoluong.getText().toString());
                    CthoaDon ct=new CthoaDon(MaSP,TenSP,sl,tt[0],gb[0]);
                    list.add(ct);
                    adapterThemHoaDonBan.notifyDataSetChanged();
                    dialog.cancel();}
                else Toast.makeText(ThemHoaDonActivity.this,"Không hợp lệ",Toast.LENGTH_SHORT).show();
            }
        });
        bHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}