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
public class Top35 {

    public int solution(TreeNode root) {
        if(root==null){
            return 0;
        }
        if(root.right==null && root.left==null){
            return 0;
        }

        return 0;
    }

    /**
     * 获取第n大的节点
     * @param node
     * @return
     */
    public TreeNode maxN(TreeNode node,int n){
        //将二叉树进行排序，已经知道了右边大，左边小，按中序遍历顺序，从大到小排序，取第n位即可


        return null;
    }


    class TreeNode{
        public TreeNode left;
        public TreeNode right;
        public Integer value;
    }
}
