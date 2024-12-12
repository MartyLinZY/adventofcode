package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {
    public static void main(String[] args) {
        //输出./src/main/resources/1.txt文件的内容
        String filePath = "day5/src/main/resources/input.txt";
        try {
            // 读取文件内容
            String content = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");

            // 将输入拆分为规则和更新
            String[] parts = content.split("\n\n");
            String[] rules = parts[0].split("\n");
            String[] updates = parts[1].split("\n");
            System.out.println("排序规则:");
            for (String rule : rules) {
                System.out.println(rule);
            }

            System.out.println("\n更新页面:");
            for (String update : updates) {
                System.out.println(update);
            }
            // Step 1: 建立页面排序规则的图
            Map<Integer, List<Integer>> graph = new HashMap<>();
            Map<Integer, Integer> inDegree = new HashMap<>();

            // 解析排序规则
            for (String rule : rules) {
                String[] partsRule = rule.split("\\|");
                int from = Integer.parseInt(partsRule[0]);
                int to = Integer.parseInt(partsRule[1]);

                graph.putIfAbsent(from, new ArrayList<>());
                graph.get(from).add(to);

                // 确保每个页面都有初始的入度
                inDegree.put(from, inDegree.getOrDefault(from, 0));
                inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            }

            // Step 2: 对每个更新进行检查和排序
            int totalMiddlePageSum = 0;

            for (String update : updates) {
                String[] pages = update.split(",");
                List<Integer> pageList = new ArrayList<>();
                for (String page : pages) {
                    pageList.add(Integer.parseInt(page));
                }

                // Step 2.1: 检查更新是否已经是正确顺序
                if (!isCorrectlyOrdered(pageList, graph, inDegree)) {
                    // Step 2.2: 如果不正确，进行拓扑排序
                    List<Integer> sortedPages = topologicalSort(pageList, graph, inDegree);
                    // Step 2.3: 找到排序后的中间页面
                    int middlePage = sortedPages.get(sortedPages.size() / 2);
                    totalMiddlePageSum += middlePage;
                }
            }

            // 输出总和
            System.out.println("Total sum of middle pages from correctly ordered updates: " + totalMiddlePageSum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 检查更新是否按正确的顺序排列
    private static boolean isCorrectlyOrdered(List<Integer> update, Map<Integer, List<Integer>> graph, Map<Integer, Integer> inDegree) {
        // 创建一个 map 来存储页面在当前更新中的位置
        Map<Integer, Integer> position = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            position.put(update.get(i), i);
        }

        // 检查所有排序规则是否得到满足
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            int from = entry.getKey();
            for (int to : entry.getValue()) {
                // 如果 'from' 出现在 'to' 后面，则说明顺序不正确
                if (position.get(from) > position.get(to)) {
                    return false;
                }
            }
        }

        return true; // 如果所有规则都满足，则表示顺序正确
    }

    // 使用拓扑排序重新排列页面顺序
    private static List<Integer> topologicalSort(List<Integer> pages, Map<Integer, List<Integer>> graph, Map<Integer, Integer> inDegree) {
        // 初始化一个队列，存储所有入度为 0 的页面
        Queue<Integer> queue = new LinkedList<>();
        for (int page : pages) {
            if (inDegree.get(page) == 0) {
                queue.add(page);
            }
        }

        List<Integer> sortedPages = new ArrayList<>();
        while (!queue.isEmpty()) {
            int currentPage = queue.poll();
            sortedPages.add(currentPage);

            for (int neighbor : graph.getOrDefault(currentPage, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return sortedPages;
    }
}