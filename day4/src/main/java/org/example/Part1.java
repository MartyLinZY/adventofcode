package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Part1 {
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
            System.out.println("vercount "+verticalcount(ls));
            System.out.println("diacount "+diagonalcount(ls));
            System.out.println("diacount2 "+diagonalcount2(ls));
            count+=horizontalcount(ls)+verticalcount(ls)+diagonalcount(ls)+diagonalcount2(ls);
            System.out.println("count: "+count);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static  int horizontalcount(String[][] ls){
        int count = 0;
        for(int i=0;i<ls.length;i++){
            for(int j=0;j<ls[i].length-3;j++) {
                if (ls[i][j].equals("X") && ls[i][j+ 1].equals("M") && ls[i ][j+ 2].equals("A") && ls[i][j+ 3].equals("S")) {
                    count++;
                }
                if (ls[i][j].equals("S") && ls[i][j+ 1].equals("A") && ls[i ][j+ 2].equals("M") && ls[i][j+ 3].equals("X")) {
                    count++;
                }
            }
        }
        return count;
    }
    public static  int verticalcount(String[][] ls){
        int count = 0;
        for(int i=0;i<ls.length-3;i++){
            for(int j=0;j<ls[i].length;j++) {
                if (ls[i][j].equals("X") && ls[i+ 1][j].equals("M") && ls[i+ 2][j].equals("A") && ls[i+ 3][j].equals("S")) {
                    count++;
                }
                if (ls[i][j].equals("S") && ls[i+ 1][j].equals("A") && ls[i+ 2][j].equals("M") && ls[i+ 3][j].equals("X")) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int diagonalcount(String[][] ls){
        int count = 0;
        for(int i=0;i<ls.length-3;i++){
            for(int j=0;j<ls[i].length-3;j++) {
                if (ls[i][j].equals("X") && ls[i+ 1][j+ 1].equals("M") && ls[i+ 2][j+ 2].equals("A") && ls[i+ 3][j+ 3].equals("S")) {
                    count++;
                }
                if (ls[i][j].equals("S") && ls[i+ 1][j+ 1].equals("A") && ls[i+ 2][j+ 2].equals("M") && ls[i+ 3][j+ 3].equals("X")) {
                    count++;
                }
            }
        }
        return count;
    }
    //反对角线
    public static int diagonalcount2(String[][] ls){
        int count = 0;
        for(int i=0;i<ls.length-3;i++){
            for(int j=3;j<ls[i].length;j++) {
                if (ls[i][j].equals("X") && ls[i+ 1][j- 1].equals("M") && ls[i+ 2][j- 2].equals("A") && ls[i+ 3][j- 3].equals("S")) {
                    count++;
                }
                if (ls[i][j].equals("S") && ls[i+ 1][j- 1].equals("A") && ls[i+ 2][j- 2].equals("M") && ls[i+ 3][j- 3].equals("X")) {
                    count++;
                }
            }
        }
        return count;
    }
}