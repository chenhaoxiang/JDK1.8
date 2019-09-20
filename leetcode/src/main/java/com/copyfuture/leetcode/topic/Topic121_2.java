/*
 * souche.com
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package com.copyfuture.leetcode.topic;

/**
 * 题目链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 * 题解： https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/solution/mai-mai-gu-piao-de-zui-jia-shi-ji-by-leetcode/
 *
 * 执行用时 :
 * 1 ms
 * , 在所有 Java 提交中击败了
 * 99.97%
 * 的用户
 * 内存消耗 :
 * 36.6 MB
 * , 在所有 Java 提交中击败了
 * 96.22%
 * 的用户
 *
 * @author chenhx
 * @version Topic56_1.java, v 0.1 2019-09-06 10:00 chenhx
 */
public class Topic121_2 {

    public static void main(String[] args) {
        int[] a = new int[4];
        a[0] = 1;
        a[1] = 15;
        a[2] = 2;
        a[3] = 8;
        System.out.println(maxProfit(a));
    }

    public static int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int price : prices) {
            if (price < min) {
                min = price;
            } else if (price - min > max) {
                max = price - min;
            }
        }
        return max;
    }

}
