package com.example.qlchdt.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlchdt.Activity.DangNhapActivity;
import com.example.qlchdt.Activity.ThemHoaDonActivity;
import com.example.qlchdt.Dialog_Progress;
import com.example.qlchdt.Model.HoaDon;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdepterHoaDon extends BaseAdapter {
    final String database_name="SQLite_QLCHDT.db";


    Activity context;
    ArrayList<HoaDon> list;
    public AdepterHoaDon(Activity context, ArrayList<HoaDon> list) {
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.listview_row_hoadonban,null);
        final TextView tMaHDB=(TextView)  row.findViewById(R.id.tMaHDB);
        TextView tHoten=(TextView)  row.findViewById(R.id.tHoten);
        ImageButton bXoa=(ImageButton) row.findViewById(R.id.bXoa);
        Button bCT=(Button) row.findViewById(R.id.bCT);
        final HoaDon hdb=list.get(position);
        tMaHDB.setText(hdb.getMaHdb().toString()+"");
        tHoten.setText(hdb.getHoten().toString()+"");
        bCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ThemHoaDonActivity.class);
//                intent.putExtra("hoadon",hdb);
                intent.putExtra("MaHDB",hdb.getMaHdb());
                context.startActivity(intent);


            }
        });
        bXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận!");
                builder.setMessage("Bạn đã chắc chắn chưa?");
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HoaDon a=list.get(position);
                        Dialog_Progress dp=new Dialog_Progress(context);
                        dp.show();
                        Dataservice dataservice= APIService.getService();
                        Call<ResponseBody> call=dataservice.xoaHD("Bearer "+ DangNhapActivity.userLogin.getToken(),a);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.body()!=null){
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    list.remove(a);
                                    notifyDataSetChanged();
                                    dp.cancel();
                                    dialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                    }
                });
                Dialog dialog=builder.create();
                dialog.show();
            }
        });
        return row;
    }
}
