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
public class Top34_2 {

    public int solution(int[][] m) {
        if(m==null){
            return 0;
        }
        if(m.length==0){
            return 0;
        }
        return minPath(m,0,0);
    }


    /**
     * 暴力递归方式求解最短路径问题
     * @param array 二维数组
     * @param i  当前走到的行
     * @param j  当前走到的列
     * @return
     */
    private static int minPath(int[][] array, int i, int j){
        //当i的值为array.length - 1并且j的值为array[0].length  - 1时表示走到了右下角
        if(i == array.length - 1 && j == array[0].length  - 1){
            //走到了右下角则直接返回右下角的数值
            return array[i][j];
        }

        //当i的值为array.length - 1并且j的值不为array[0].length - 1时，只能往右走
        if(i == array.length - 1 && j != array[0].length - 1){
            return array[i][j] + minPath(array, i ,j + 1);
        }else if(i != array.length - 1 && j == array[0].length - 1){
            //当i的值不为array.length - 1并且j的值为array[0].length - 1时，只能往下走
            return array[i][j] + minPath(array, i + 1, j);
        }

        //否则既可以向下走也可以向右走，此时选取路径最短的那个
        return array[i][j] + Math.min(minPath(array, i, j + 1), minPath(array, i + 1, j));
    }
}
