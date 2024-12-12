package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {

    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String path = "day11/src/main/resources/test.txt";
        long[] stones = new long[1];

        try {
            // 读取文件内容
            String content = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");

            String[] parts = content.split(" ");  // 以空行拆分规则和更新
            for (int i = 0; i < parts.length; i++) {
                stones[i] = Long.parseLong(parts[i]);
            }

            for(int time=0;time<25;time++){
                    int flag=stones.length;
                    int i=0;
                    while(flag>i){
                        stones = getNewStones(stones, i);
                        if(stones.length>flag){
                            flag=stones.length;
                            i++;
                        }
                        i++;
                    }

            }

            System.out.println("Result: "+ stones.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static long[] getNewStones(long[] stones, int i) {
        if (stones[i] == 0) {
            stones[i] = 1;
            return stones;
        }

        if (String.valueOf(stones[i]).length()%2==0){
            long[] newStones= new long[stones.length+1];
            for (int j = 0; j < i; j++) {
                newStones[j] = stones[j];
            }
            int mid = String.valueOf(stones[i]).length() / 2;
            String previous = String.valueOf(stones[i]).substring(0, mid);
//            StringBuilder previous= new StringBuilder();
            String next = String.valueOf(stones[i]).substring(mid);
//            StringBuilder next= new StringBuilder();
//            for (int j = 0; j < String.valueOf(stones[i]).length()/2; j++) {
//                previous.append(String.valueOf(stones[i]).charAt(j));
//            }
//            for (int j = String.valueOf(stones[i]).length()/2; j < String.valueOf(stones[i]).length(); j++) {
//                next.append(String.valueOf(stones[i]).charAt(j));
//            }
            newStones[i] = Long.parseLong(previous);
            newStones[i+1] = Long.parseLong(next);
            for (int j = i+1; j < stones.length; j++) {
                newStones[j+1] = stones[j];
            }

            return newStones;
        }

        stones[i] *=2024;
        return stones;

    }

}