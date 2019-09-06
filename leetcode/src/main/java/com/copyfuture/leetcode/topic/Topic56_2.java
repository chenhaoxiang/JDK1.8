/*
 * souche.com
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package com.copyfuture.leetcode.topic;

import java.util.Arrays;

/**
 * 不进行排序的写法
 * 题目链接：https://leetcode-cn.com/problems/merge-intervals/
 * 226 ms	53.2 MB
 * @author chenhx
 * @version Topic56_1.java, v 0.1 2019-09-06 10:00 chenhx
 */
public class Topic56_2 {

    public static void main(String[] args) {
        int[][] a = new int[3][2];
        a[0][0] = 1;
        a[0][1] = 4;
        a[1][0] = 0;
        a[1][1] = 2;
        a[2][0] = 3;
        a[2][1] = 5;
        int[][] b = merge(a);
        for (int i=0;i<b.length;i++){
            System.out.println(b[i][0]+","+b[i][1]);
        }
    }

    public static int[][] merge(int[][] intervals) {
        int len = intervals.length;

        for (int i=0;i<len-1;i++){
            if(intervals[i]==null){
                continue;
            }
            int min = intervals[i][0];
            int max = intervals[i][1];
            for(int j=i+1;j<len;j++){
                if(intervals[j]==null){
                    continue;
                }
                int a = intervals[j][0];
                int b = intervals[j][1];

                if( (a>=min && a<=max) || (b>=min && b<=max) || (min<=b && min>=a) || (max<=b && max>=a)  ){
                    intervals[j][0] = Math.min(min,a);
                    intervals[j][1] = Math.max(max,b);
                    intervals[i]=null;
                }
            }
        }

        int[][] rb = new int[len][2];
        int ind = 0;
        for (int[] interval : intervals) {
            if (interval != null) {
                rb[ind] = interval;
                ind++;
            }
        }
        int[][] real = new int[ind][2];
        System.arraycopy(rb,0,real,0,ind);
        return real;
    }


}
