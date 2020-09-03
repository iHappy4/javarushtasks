package com.javarush.task.task20.task2025;

import java.util.*;

/*
Алгоритмы-числа
*/
public class Solution {
    private static int[] digitNum;
    private static long[][] pows;
    private static int pos;
    private static int maxNum;
    private static long Na;
    private static ArrayList<Long> arr;

    public static long[] getNumbers(long N) {
        long[] result = null;
        if(N > 0){
        Na = N;
        arr = new ArrayList<>();
        pos = 0;
        maxNum = 9;
        int s = getSize(N);
        if(s == 1){
            s++;
        }
        digitNum = new int[s];
        Arrays.fill(digitNum, 9);
        pows = getPows(s+1);
        generator();
        result = new long[arr.size()];
        Collections.sort(arr);
            for(int i = 0; i < result.length; i++){
                result[i] = arr.get(i);
            }
        }

        return result;
    }
    public static int getSize(long x){
        long p = 10L;
        for(int i = 1; i < 19; i++){
            if(x < p){
                return i;
            }
            p *= 10L;
        }
        return 19;
    }

    public static void generator() {
        if(digitNum[digitNum.length - 1] == 0){
            return;
        }
        for(int i = maxNum; i >= 0; ){
            if(digitNum[0] > 0){
                digitNum[0]--;
                i--;
            }else{
                pos++;
                caret();
                digitNum[0] = maxNum;
                i = maxNum;
            }
            isArm();
        }
    }
    private static void caret(){
        if(pos == digitNum.length-1){
            maxNum--;
        }
        if(digitNum[pos] == 0 && pos < digitNum.length - 1){
            pos++;
            caret();
            digitNum[pos] = maxNum;
        }else{
            digitNum[pos]--;
            maxNum = digitNum[pos];
        }
        pos--;
    }

    public static long[][] getPows(int n){
        long[][] pow = new long[10][n];
        for(int i = 1; i < 10; i++){
            for(int p = 1; p <= n; p++){
                long pown = i;
                for(int c = 1; c < p; c++){
                    pown *= i;
                }
                pow[i][p-1] = pown;
            }
        }
        return pow;
    }
    public static void isArm() {
        int size = 0;
        for(int i = 0; i < digitNum.length; i++){
            if(!(digitNum[i] <= 0)){
                size++;
            }
        }
        if(size == 0){
            return;
        }
        for(; size <= digitNum.length; size++) {
            long res = 0;
            for (int g = 0; g < digitNum.length; g++) {
                if(digitNum[g] >= 0) {
                    res += pows[digitNum[g]][size - 1];
                }
            }
            if(res > 0) {
                long res2 = res;
                int size2 = getSize(res2);
                long res1 = 0;
                for (int i = 0; i < size2; i++) {
                    long a = res2 % 10;
                    res1 += pows[(int) a][size2 - 1];
                    res2 /= 10;
                }
                if (res == res1 && !arr.contains(res1) && res1<Na) {
                    arr.add(res1);
                }
            }
        }
    }

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(2)));
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);

        a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(2)));
        b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);
    }
}