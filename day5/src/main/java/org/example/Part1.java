package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {
    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String path = "day5/src/main/resources/input.txt";
        String[] rules= new String[0];
        String[] updates = new String[0];

        try {
            // 读取文件内容
            String content = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");

            // 拆分文件内容为规则部分和更新部分
            String[] parts = content.split("\n\n");  // 以空行拆分规则和更新

            // 第一个部分是规则
            rules = parts[0].split("\n");
            // 第二个部分是更新
            updates = parts[1].split("\n");

            // 打印结果
            System.out.println("排序规则:");
            for (String rule : rules) {
                System.out.println(rule);
            }

            System.out.println("\n更新页面:");
            for (String update : updates) {
                System.out.println(update);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Step 1: Parse the rules into a graph (adjacency list)
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Set<Integer> allPages = new HashSet<>();

        for (String rule : rules) {
            String[] parts = rule.split("\\|");
            int before = Integer.parseInt(parts[0]);
            int after = Integer.parseInt(parts[1]);

            graph.putIfAbsent(before, new HashSet<>());
            graph.putIfAbsent(after, new HashSet<>());
            graph.get(before).add(after);

            allPages.add(before);
            allPages.add(after);
        }

        // Step 2: Check each update
        int sumMiddlePages = 0;
        for (String update : updates) {
            String[] pages = update.split(",");
            List<Integer> updatePages = new ArrayList<>();
            for (String page : pages) {
                updatePages.add(Integer.parseInt(page));
            }

            if (isCorrectlyOrdered(updatePages, graph)) {
                sumMiddlePages += getMiddlePage(updatePages);
            }
        }

        // Output the result
        System.out.println("Sum of middle pages: " + sumMiddlePages);




    }

    // Step 3: Check if the update pages are in the correct order based on the graph
    private static boolean isCorrectlyOrdered(List<Integer> updatePages, Map<Integer, Set<Integer>> graph) {
        // Check the order using topological constraints
        for (int i = 0; i < updatePages.size(); i++) {
            for (int j = i + 1; j < updatePages.size(); j++) {
                int page1 = updatePages.get(i);
                int page2 = updatePages.get(j);

                if (graph.containsKey(page1) && graph.get(page1).contains(page2)) {
                    // page1 must come before page2
                    continue;
                } else {
                    // If a rule is violated, return false
                    return false;
                }
            }
        }
        return true;
    }

    // Step 4: Find the middle page of the update
    private static int getMiddlePage(List<Integer> pages) {
        int middleIndex = pages.size() / 2;
        return pages.get(middleIndex);
    }
}