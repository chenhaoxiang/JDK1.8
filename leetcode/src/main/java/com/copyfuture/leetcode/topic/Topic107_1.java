/*
 * souche.com
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package com.copyfuture.leetcode.topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/
 * 执行用时 : 2 ms
 * 内存消耗 : 36.5 MB
 * @author chenhx
 * @version Topic56_1.java, v 0.1 2019-10-25 14:00 chenhx
 */
public class Topic107_1 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);

        TreeNode left = new TreeNode(9);
        TreeNode right = new TreeNode(20);
        root.left = left;
        root.right = right;

        TreeNode left1 = new TreeNode(15);
        TreeNode right1 = new TreeNode(7);
        right.left = left1;
        right.right = right1;

        TreeNode left2 = new TreeNode(2);
        TreeNode right2 = new TreeNode(3);
        left.left = left2;
        left.right = right2;

        List<List<Integer>> n = levelOrderBottom(root);

        System.out.println(n);

    }

    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> node = new ArrayList<>();
        List<Integer> n = new ArrayList<>();
        Map<Integer,List<Integer>> layer = new HashMap<>();
        layer.put(0,n);
        addNode(root, n,layer,1);

        for (Integer integer : layer.keySet()) {
            List<Integer> c = layer.get(integer);
            if(c.size()>0) {
                node.add(c);
            }
        }
        //倒叙输出
        Collections.reverse(node);
        return node;
    }

    /**
     * 先递归，再倒叙输出
     * 重点就是层级，利用Map的key存储层级
     * @param root
     * @param n
     * @param layer
     * @param level
     */
    private static void addNode(TreeNode root, List<Integer> n,Map<Integer,List<Integer>> layer,Integer level) {
        if(root==null){
            return;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        n.add(root.val);
        List<Integer> next = new ArrayList<>();
        if(layer.containsKey(level)){
            next = layer.get(level);
        }else {
            layer.put(level,next);
        }
        if(left!=null){
            addNode(left,next,layer,level+1);
        }
        if(right!=null){
            addNode(right,next,layer,level+1);
        }
    }
}


class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
}
