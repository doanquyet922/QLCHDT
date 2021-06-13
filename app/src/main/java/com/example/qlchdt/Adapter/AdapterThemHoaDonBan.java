package com.example.qlchdt.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.qlchdt.Activity.DangNhapActivity;
import com.example.qlchdt.Model.CthoaDon;
import com.example.qlchdt.Model.ModelThemHoaDon;
import com.example.qlchdt.Model.SanPham;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterThemHoaDonBan extends BaseAdapter {
    Activity context;
    ArrayList<CthoaDon> list;
    final String database_name = "SQLite_QLCHDT.db";

    public AdapterThemHoaDonBan(Activity context, ArrayList<CthoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_them_sp_hdb, null);
         TextView tMaSP = (TextView) row.findViewById(R.id.edMaSP);
         TextView tTenSP = (TextView) row.findViewById(R.id.tTenSP);
         TextView tSoluong = (TextView) row.findViewById(R.id.edSoluong);
         TextView tTongtien = (TextView) row.findViewById(R.id.tTongtien);
        ImageButton bXoa = (ImageButton) row.findViewById(R.id.bXoa);
        final CthoaDon a=list.get(position);
        final int[] tongTien = {0};
        tMaSP.setText(a.getMaSp()+"");
        tTenSP.setText(a.getTenSp()+"");
        tSoluong.setText(a.getSoLuong()+"");
        Dataservice dataservice= APIService.getService();
        Call<SanPham> call=dataservice.getById("Bearer "+DangNhapActivity.userLogin.getToken(),a.getMaSp());
        call.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                SanPham sp=response.body();
                if(sp!=null){
//                    Log.d("BBB", "sl: "+a.getSoLuong()+"-gb: "+sp.getGiaBan());
                    tongTien[0] = a.getSoLuong()*sp.getGiaBan();
                    tTongtien.setText(formatNumber(tongTien[0])+"");
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {

            }
        });


        bXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa!");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Dialog dialog=builder.create();
                dialog.show();

            }
        });


        return row;
        }
    public String formatNumber(int tien) {
        String s = tien + "";
        int d = 0;
        String tmp = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            d++;
            tmp = tmp + s.charAt(i) + "";
            if (d % 3 == 0) {
                tmp=tmp+".";
            }
        }
        String t="";
        for (int i = tmp.length() - 1; i >= 0; i--) {
            int vtcuoi= tmp.length() - 1;
            String a=tmp.charAt(i)+"";
            if(i==vtcuoi && a.equals(".")){
                continue;
            }

            t=t+tmp.charAt(i)+"";

        }
        return t;
    }


}

