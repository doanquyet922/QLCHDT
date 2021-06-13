package com.example.qlchdt.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlchdt.Activity.DangNhapActivity;
import com.example.qlchdt.Activity.SanPhamActivity;
import com.example.qlchdt.Activity.ThemSanPhamActivity;
import com.example.qlchdt.Dialog_Progress;
import com.example.qlchdt.Model.SanPham;
import com.example.qlchdt.R;
import com.example.qlchdt.Service.APIService;
import com.example.qlchdt.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapteSanPham extends BaseAdapter {

    Activity context;
    ArrayList<SanPham> list;


    public AdapteSanPham(Activity context, ArrayList<SanPham> list) {
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
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.listview_row_sanpham,null);
        ImageView imAnhSanPham=(ImageView) row.findViewById(R.id.imSanPham);
        final TextView tMaSp =(TextView) row.findViewById(R.id.tMaSP);
        TextView tTenSP=(TextView) row.findViewById(R.id.tTenSP);
        TextView tTenHang=(TextView) row.findViewById(R.id.tTenHang);
        ImageButton bSua=(ImageButton) row.findViewById(R.id.bSua);
        ImageButton bXoa=(ImageButton) row.findViewById(R.id.bXoa);
//        Button bCT=(Button) row.findViewById(R.id.bChiTietSanPham);

        final SanPham sp=list.get(position);
        tMaSp.setText(sp.getMaSp()+"");
        tTenSP.setText(sp.getTenSp()+"");
        tTenHang.setText(sp.getTenHang()+"");
        if (!sp.getAnh().equals("") && sp.getAnh()!=null){

            String[] tenfile=sp.getAnh().split("\\.");
            Log.d("AAA", "getView: "+tenfile[0]);
            Picasso.get().load(APIService.base_url+"sanpham/GetImage/"+tenfile[0]).into(imAnhSanPham);

        }
        bSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getMaSp()!=null && !sp.getMaSp().equals("")) {
                    Intent intent = new Intent(context, ThemSanPhamActivity.class);
                    intent.putExtra("maSP", sp.getMaSp());
                    context.startActivity(intent);
                    context.finish();
                }
            }
        });
//        bCT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent=new Intent(context,ChiTietSanPham.class);
////                intent.putExtra("MaSP",sp.getMaSP());
////                context.startActivity(intent);
//            }
//        });

        bXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msp=sp.getMaSp();
                String anh=sp.getAnh();
                //Tạo AlertDialog
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bán có chắc chắn muốn xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete(msp,anh);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
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
    private void delete(String MaSPXoa,String Anh){
        Dialog_Progress dp=new Dialog_Progress(context);
        dp.show();
        String token= DangNhapActivity.userLogin.getToken();
        Dataservice dataservice=APIService.getService();
        Call<Void> callDe=dataservice.DeleteImage(Anh);
        callDe.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        SanPham spXoa=new SanPham(MaSPXoa);
        Call<ResponseBody> call=dataservice.XoaSP("Bearer " + token,spXoa);
        Log.d("AAA", "delete: "+MaSPXoa+"-token: "+token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("AAA", "delete: "+response.toString());
                if(response.body()!=null){
//                for(SanPham sp:list){
//                    if (sp.getMaSp().equals(MaSPXoa)){
//                        list.remove(sp);
//                    }
//                }
                context.startActivity(new Intent(context, SanPhamActivity.class));
                context.finish();
                dp.cancel();}
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,  "Erro: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                dp.cancel();
            }
        });


    }
}
