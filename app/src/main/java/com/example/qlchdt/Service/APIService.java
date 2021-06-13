package com.example.qlchdt.Service;

public class APIService {
    public static String base_url="https://api-bandienthoai.conveyor.cloud/api/";
    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
