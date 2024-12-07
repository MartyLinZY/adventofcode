package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Part2 {
    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String path = "day4/src/main/resources/input.txt";
        File file=new File(path);
        if(!file.exists()){
            System.out.println("文件不存在");
            return;
        }
        System.out.println("文件内容如下：");
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                System.out.println(line);
            }
            System.out.println("size: "+lines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            String[][] ls = new String[lines.size()][];
            for (String line : lines) {
                String[] split = line.split("");
                ls[lines.indexOf(line)] = split;
            }
            int count=0;
            System.out.println("horcount "+horizontalcount(ls));
            count+=horizontalcount(ls);
            System.out.println("count: "+count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  int horizontalcount(String[][] ls){
        int count = 0;
        for(int i=1;i<ls.length-1;i++){
            for(int j=1;j<ls[i].length-1;j++) {
                if (ls[i][j].equals("A")) {
                    int flag=0;
                    if((ls[i+1][j+ 1].equals("M") && ls[i-1][j-1].equals("S"))||(ls[i+1][j+ 1].equals("S") && ls[i-1][j-1].equals("M")))
                        flag++;
                    if(ls[i+1][j- 1].equals("M") && ls[i-1][j+1].equals("S") ||(ls[i+1][j- 1].equals("S") && ls[i-1][j+1].equals("M")))
                        flag++;
                    if (flag==2){
                        count++;
                    }
                }

            }
        }
        return count;
    }

}