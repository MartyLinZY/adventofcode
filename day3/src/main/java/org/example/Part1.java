package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String path = "day3/src/main/resources/input.txt";
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
            int c1=0;
            List<String> lines = Files.readAllLines(file.toPath());
            int count=0;
            //遍历每一行
            for (String line : lines) {
                for(int i=0;i<line.length()-7;i++){
                    //判断是否为字母
                    int middle=i+4;
                    int right=i;
                    if(line.charAt(i)=='m'&&line.charAt(i+1)=='u'&&line.charAt(i+2)=='l'&&line.charAt(i+3)=='('){
                        c1++;
                        int flag=1;
                        for(int j=i+4;j<line.length();j++){
                            if(line.charAt(j)==','|| line.charAt(j)==')'||(line.charAt(j)<='9'&&line.charAt(j)>='0')){
                                if(line.charAt(j)==','){
                                    middle=j;
                                }
                                if(line.charAt(j)==')'){
                                    right=j;
                                    break;
                                }
                                }
                            else {
                                flag=0;
                                break;
                            }
                        }
                        if(flag==1){
                            String substring = line.substring(i+4, middle);
                            String substring1 = line.substring(middle+1, right);
                            if(isInt(substring)&&isInt(substring1)){
                                count+=Integer.parseInt(substring)*Integer.parseInt(substring1);
                            }
                            i=right;
                        }
                    }
                }
            }
            System.out.println("count: "+c1);
            System.out.println("count: "+count);

            int totalSum = 0;
            // The corrupted memory input
            for (String memory : lines) {

                // Regular expression to match mul(X,Y) pattern
                Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
                Matcher matcher = pattern.matcher(memory);



                // Find all matches and calculate the product of each pair (X, Y)
                while (matcher.find()) {
                    int X = Integer.parseInt(matcher.group(1)); // Get the first number
                    int Y = Integer.parseInt(matcher.group(2)); // Get the second number
                    totalSum += X * Y; // Add the product to the total sum
                }
                // Output the final result
            }
            System.out.println("Total Sum: " + totalSum);

        } catch (IOException e) {

            e.printStackTrace();
        }


    }
    public  static boolean isInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

