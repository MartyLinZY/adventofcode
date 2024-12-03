package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Part2 {
    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String path = "day2/src/main/resources/input.txt";
        File file=new File(path);
        if(!file.exists()){
            System.out.println("文件不存在");
            return;
        }
        System.out.println("文件内容如下：");
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            System.out.println("size: "+lines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            int count=0;
            for (String line : lines) {
                //将字符串数组转换为整型数组
                int[] arr = Arrays.stream(line.split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();//调用isSafe方法判断是否安全
                if(isSafe(arr)==1){
                    count++;
                }
                else{
                    for(int i=0;i<arr.length;i++){
                        //去掉第i个元素获得新的数组
                        int[] arr2=new int[arr.length-1];
                        for(int j=0;j<arr.length;j++){
                            if(j<i){
                                arr2[j]=arr[j];
                            }
                            if(j>i){
                                arr2[j-1]=arr[j];
                            }
                        }
                        if(isSafe(arr2)==1){
                            count++;
                            i=arr.length;
                        }
                    }

                }
            }
            System.out.println("count: "+count);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static int isSafe(int[] arr){
        int[] arr2=new int[arr.length-1];
        int pos=0;
        if(arr[1]-arr[0]>0){
            pos=1;
        }
        for(int i =0;i<arr.length-1;i++){
            arr2[i]=arr[i+1]-arr[i];

            if(Math.abs(arr2[i])<1||Math.abs(arr2[i])>3){
                return 0;
            }
            if(pos==1){
                if(arr2[i]<0){
                    return 0;
                }
            }
            if(pos==0){
                if(arr2[i]>0){
                    return 0;
                }
            }
        }
        return 1;
    }
}
