package com.example.qlchdt;

public class FormatNumber {

    public static String formatNumber(int tien) {
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
