import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generate_BA_Network {

    private static final int N = 1000; // 总节点数
    private static final int m0 = 5;   // 初始网络的大小
    private static final int m = 2;    // 每次添加的边数

    // 生成BA无标度网络
    private static ArrayList<ArrayList<Integer>> createBANetwork() {
        ArrayList<ArrayList<Integer>> network = new ArrayList<>();
        Random random = new Random();
        int[] degrees = new int[N];

        // 初始化邻接列表和度数数组
        for (int i = 0; i < N; i++) {
            network.add(new ArrayList<>());
            degrees[i] = 0;
        }

        // 创建一个m0大小的全连通初始网络
        for (int i = 0; i < m0; i++) {
            for (int j = i + 1; j < m0; j++) {
                network.get(i).add(j);
                network.get(j).add(i);
                degrees[i]++;
                degrees[j]++;
            }
        }

        // 逐步添加节点，并建立m条边
        for (int i = m0; i < N; i++) {
            int addedEdges = 0;
            while (addedEdges < m) {
                int potentialNode = random.nextInt(i);

                // 优先连接机制：与节点的度成正比
                double probability = degrees[potentialNode] / (double) (2 * (i - 1)); // i-1是总度数的两倍

                if (random.nextDouble() < probability && !network.get(i).contains(potentialNode)) {
                    network.get(i).add(potentialNode);
                    network.get(potentialNode).add(i);
                    degrees[i]++;
                    degrees[potentialNode]++;
                    addedEdges++;
                }
            }
        }

        return network;
    }

    // 将邻接矩阵写入文件
    private static void writeAdjacencyMatrix(ArrayList<ArrayList<Integer>> network, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < N; i++) {
                int[] row = new int[N];
                for (int j : network.get(i)) {
                    row[j] = 1;
                }
                for (int value : row) {
                    writer.write(value + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> network = createBANetwork();
        writeAdjacencyMatrix(network, "D:\\桌面\\Hypernetwork_information_model\\output\\BA_adjacencyMatrix_m=2.txt");
        // 输出网络信息
        for (int i = 0; i < network.size(); i++) {
            System.out.println("Node " + i + " is connected to: " + network.get(i).toString());
        }
    }
}