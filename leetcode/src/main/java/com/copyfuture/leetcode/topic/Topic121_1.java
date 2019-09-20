/*
 * souche.com
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package com.copyfuture.leetcode.topic;

/**
 * 题目链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 * 题解： https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/solution/mai-mai-gu-piao-de-zui-jia-shi-ji-by-leetcode/
 * 提交时间
 * 提交结果
 * 执行用时
 * 内存消耗
 * 语言
 *
 * 几秒前	通过	252 ms	37.8 MB	Java
 *
 * @author chenhx
 * @version Topic56_1.java, v 0.1 2019-09-06 10:00 chenhx
 */
public class Topic121_1 {

    public static void main(String[] args) {
        int[] a = new int[4];
        a[0] = 1;
        a[1] = 15;
        a[2] = 2;
        a[3] = 8;
        System.out.println(maxProfit(a));
    }

    public static int maxProfit(int[] prices) {
        int res = 0;
        for (int i = 0; i < prices.length-1; i++) {
            for (int i1 = i+1; i1 < prices.length; i1++) {
                if(prices[i] < prices[i1]){
                    int r = prices[i1] - prices[i];
                    if(r > res){
                        res = r;
                    }
                }

            }
        }
        return res;
    }

}
