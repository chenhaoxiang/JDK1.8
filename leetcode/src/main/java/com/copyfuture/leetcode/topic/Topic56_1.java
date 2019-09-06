/*
 * souche.com
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package com.copyfuture.leetcode.topic;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目链接：https://leetcode-cn.com/problems/merge-intervals/
 * 9 ms	41.3 MB
 * @author chenhx
 * @version Topic56_1.java, v 0.1 2019-09-06 10:00 chenhx
 */
public class Topic56_1 {

    public static void main(String[] args) {
        int[][] a = new int[4][2];
        a[0][0] = 1;
        a[0][1] = 3;
        a[1][0] = 2;
        a[1][1] = 6;
        a[2][0] = 5;
        a[2][1] = 10;
        a[3][0] = 15;
        a[3][1] = 18;
        int[][] b = merge(a);
        for (int i=0;i<b.length;i++){
            System.out.println(b[i][0]+","+b[i][1]);
        }
    }

    public static int[][] merge(int[][] intervals) {
        //先将intervals进行排序,按照第一列的顺序
        quickSort(intervals);

//        for (int i=0;i<intervals.length;i++){
//            System.out.println(intervals[i][0]+","+intervals[i][1]);
//        }

        int[][] b = new int[intervals.length][2];
        int bIndex = 0;
        for (int i=0;i<intervals.length;i++){
            int left = intervals[i][0];
            int right = intervals[i][1];
            // i不能到最后一行,所以要小于(数组的长度 - 1)
            // 判断所在行的right和下一行的left大小,对right重新进行赋最大值,之后再不断进行while循环判断
            while (i < intervals.length - 1 && right >= intervals[i + 1][0]) {
                i++;
                right = Math.max(right, intervals[i][1]);
            }
            b[bIndex][0] = left;
            b[bIndex][1] = right;
            bIndex++;
        }
        int[][] rb = new int[bIndex][2];
        //截断b数组
        System.arraycopy(b, 0, rb, 0,bIndex);
        return rb;
    }

    public static void quickSort(int[][] arr){
        quickSort(arr, 0, arr.length-1);
    }
    private static int partition(int[][] arr, int left, int right) {
        int temp = arr[left][0];
        int temp1 = arr[left][1];
        while (right > left) {
            // 先判断基准数和后面的数依次比较
            while (temp <= arr[right][0] && left < right) {
                --right;
            }
            // 基准数大于了 arr[right]
            if (left < right) {
                temp(arr, right, arr[left]);
                ++left;
            }
            //  基准数大于了 arr[left]
            while (temp >= arr[left][0] && left < right) {
                ++left;
            }
            if (left < right) {
                temp(arr, left, arr[right]);
                --right;
            }
        }
        arr[left][0] = temp;
        arr[left][1] = temp1;
        return left;
    }

    private static void temp(int[][] arr, int left, int[] ints) {
        int tempA = ints[0];
        int tempB = ints[1];
        ints[0] = arr[left][0];
        ints[1] = arr[left][1];
        arr[left][0] = tempA;
        arr[left][1] = tempB;
    }

    private static void quickSort(int[][] arr, int left, int right) {
        if (arr == null || left >= right || arr.length <= 1) {
            return;
        }
        int mid = partition(arr, left, right);
        quickSort(arr, left, mid);
        quickSort(arr, mid + 1, right);
    }

}
