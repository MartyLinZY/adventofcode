package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {

    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String path = "day11/src/main/resources/input.txt";
        long[] stones = new long[8];

        try {
            // 读取文件内容
            String content = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");

            String[] parts = content.split(" ");  // 以空行拆分规则和更新
            for (int i = 0; i < parts.length; i++) {
                stones[i] = Long.parseLong(parts[i]);
            }
            long res=0;
            //构建数字和展开25次后的Map的对应关系
            Map<Long,Map<Long,Integer>> map = new HashMap<>();
            //先对数组第一个数展开25次，对展开25次后的数字也展开25次，直到map中不存在展开后找不到的数字
            for(int i=0;i<stones.length;i++){
                if(map.containsKey(stones[i])){
                    continue;
                }
                Map<Long,Integer> firstMap = get25BlinkResult(stones[i]);
                map.put(stones[i],firstMap);
               //递归地对map中value对应的Hashmap中的key进行展开，并存到map中，直到没有新的数字产生
                for (Map.Entry<Long,Integer> entry:firstMap.entrySet()){
                    if(!map.containsKey(entry.getKey())){
                        Map<Long,Integer> tempMap = get25BlinkResult(entry.getKey());
                        map.put(entry.getKey(),tempMap);
                    }
                }
            }
            int res25=0;
            for (Map.Entry<Long, Map<Long, Integer>> entry:map.entrySet()){
                for(int i=0;i<stones.length;i++){
                    if(entry.getKey()==stones[i]){
                       for(Map.Entry<Long,Integer> entry1:entry.getValue().entrySet()){
                           res25+=entry1.getValue();
                       }
                    }
                }
            }


            System.out.println("Result: "+ res25);
            //对stone里面，先取出展开25次的结果，再对结果中的每一个数字计算数字数量*展开数量
            for (int i = 0; i < stones.length; i++) {
                if(map.containsKey(stones[i])){
                    //展开了25次
                    for (Map.Entry<Long,Integer> entry1:map.get(stones[i]).entrySet()){
                        if(!map.containsKey(entry1.getKey())){
                            Map<Long,Integer> firstMap = get25BlinkResult(entry1.getKey());
                            map.put(entry1.getKey(),firstMap);
                            //回到刚刚的迭代器迭代的位置再来一次
                        }
                        Map<Long,Integer> tempMap1 = map.get(entry1.getKey());
                        int base1=entry1.getValue();
                        //展开了50次
                        for (Map.Entry<Long,Integer> entry2 : tempMap1.entrySet()){
                            if(!map.containsKey(entry2.getKey())){
                                Map<Long,Integer> firstMap = get25BlinkResult(entry2.getKey());
                                map.put(entry2.getKey(),firstMap);
                                //回到刚刚的迭代器迭代的位置再来一次
                            }
                            Map<Long,Integer> tempMap2 = map.get(entry2.getKey());
                            int base2=entry2.getValue();
                            if(tempMap2==null){
                                int yes=1;
                            }
                            //展开了75次
                            for (Map.Entry<Long,Integer> entry3 : tempMap2.entrySet()){
//                                if(!map.containsKey(entry3.getKey())){
//                                    Map<Long,Integer> firstMap = get25BlinkResult(entry3.getKey());
//                                    map.put(entry3.getKey(),firstMap);
//                                }
                                    res+= (long) base1*base2*entry3.getValue();
                            }
                        }
                    }
                }
               else{
                    Map<Long,Integer> firstMap = get25BlinkResult(stones[i]);
                    map.put(stones[i],firstMap);
                    i--;
                }

            }

            System.out.println("Result: "+ res);

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

    private static Map<Long,Integer> get25BlinkResult(long original){
        long[] originalStones = {original};
        for(int time=0;time<25;time++){
            int flag=originalStones.length;
            int i=0;
            while(flag>i){
                originalStones = getNewStones(originalStones, i);
                if(originalStones.length>flag){
                    flag=originalStones.length;
                    i++;
                }
                i++;
            }
        }
        //计算数组内的重复数字
        Map<Long,Integer> map = new HashMap<>();
        for (int j = 0; j < originalStones.length; j++) {
            if(map.containsKey(originalStones[j])){
                map.put(originalStones[j], map.get(originalStones[j])+1);
            }else{
                map.put(originalStones[j], 1);
            }
        }
        return map;
    }

}