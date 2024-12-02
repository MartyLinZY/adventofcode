package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Part2 {
    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String path = "day1/src/main/resources/input.txt";
        File file=new File(path);
        if(!file.exists()){
            System.out.println("文件不存在");
            return;
        }
//        System.out.println("文件内容如下：");
//        try {
//            List<String> lines = Files.readAllLines(file.toPath());
//            for (String line : lines) {
//                System.out.println(line);
//            }
//            System.out.println("size: "+lines.size());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            int[] arrLeft = new int[lines.size()];
            int[] arrRight = new int[lines.size()];
            for (String line : lines) {
                String[] split = line.split(" ");
                arrLeft[lines.indexOf(line)] = Integer.parseInt(split[0]);
                arrRight[lines.indexOf(line)] = Integer.parseInt(split[3]);
            }
            Arrays.sort(arrLeft);
            Arrays.sort(arrRight);
            int ans=0;
            int count=0;
            int j=0;
            for (int i = 0; i < arrLeft.length; i++) {
               //查找第一次出现该数字的位置，数字会重复
                for(;j<arrLeft.length;j++){
                    if(arrLeft[i]>arrRight[j])
                        continue;
                    if(arrLeft[i]==arrRight[j])
                        count++;
                    if(arrLeft[i]<arrRight[j])
                        break;
                }
                ans+=count*arrLeft[i];
                count=0;
            }
            System.out.println("ans: "+ans);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
