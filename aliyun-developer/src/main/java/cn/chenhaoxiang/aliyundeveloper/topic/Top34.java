/*
 * souche.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package cn.chenhaoxiang.aliyundeveloper.topic;

/**
 * @author chenhx
 * @version 0.0.1
 * @className Top34.java
 * @date 2020-01-17 16:46
 * @description https://developer.aliyun.com/coding/34
 */
public class Top34 {

    public int solution(int[][] m) {
        if(m==null){
            return 0;
        }
        if(m.length==0){
            return 0;
        }
        int[][] dp = new int[m.length][m[0].length];

        dp[0][0] = m[0][0];
        //先计算第一行
        for (int i = 1; i < m.length; i++) {
            dp[i][0] = dp[i-1][0]+m[i][0];
        }
        //计算第一列
        for (int i = 1; i < m[0].length; i++) {
            dp[0][i] = dp[0][i-1]+m[0][i];
        }

        //从上->下，左->右 计算当前位置的最小值
        for (int i = 1; i < m.length; i++) {

            for (int j = 1; j < m[0].length; j++) {
                dp[i][j] = Math.min(dp[i][j-1],dp[i-1][j]) + m[i][j];
            }

        }

        return dp[m.length-1][m[0].length-1];
    }
}
