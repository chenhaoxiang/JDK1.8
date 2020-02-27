/*
 * souche.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package cn.chenhaoxiang.aliyundeveloper.topic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhx
 * @version 0.0.1
 * @className Top34.java
 * @date 2020-01-17 16:46
 * @description https://developer.aliyun.com/coding/103
 */
public class Top103 {

    public static void main(String[] args) {
        for(int i=1;i<10;i++) {
            if(i%2==0) {
                System.out.println(solution(i));
            }
        }
    }
    private static String[] strM = {"o","x","p"};

    public static int solution(int n) {
        if(n==2){
            return 7;
        }
        //先组成n长的字符串、遍历出所有可能
        List<String> allStr = new ArrayList<>();
        for(int i=0;i<n;i++){
            get(allStr,i,n,"");
        }

        //遍历，找出最终可以被消完的字符串的个数
        int size= allStr.size();
        for (String s : allStr) {
            int oi =0;
            int xi =0;
            int pi =0;
            for (int i = 0; i < s.length(); i++) {
                if(s.charAt(i)=='o'){
                    oi++;
                }else if(s.charAt(i)=='x'){
                    xi++;
                }else if(s.charAt(i)=='p'){
                    pi++;
                }
            }
            if(oi==0){
                continue;
            }
            if(xi==0){
                continue;
            }
            //ooox xxxo oo ox xx oxoxppxxxp opxxxopo
            //TODO

        }
        return size%998244353;
    }

    /**
     * 拼接字符串
     * @param allStr
     * @param i
     * @param n
     * @param str
     */
    static void get(List<String> allStr,int i,int n,String str){
        if(i==n){
            if(str.length()==n){
                allStr.add(str);
            }
            return;
        }
        for (String s : strM) {
            get(allStr,i+1,n,str+s);
        }
    }

}
