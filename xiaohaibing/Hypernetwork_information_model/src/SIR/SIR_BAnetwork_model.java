package SIR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SIR_BAnetwork_model {
    public static void main(String[] args) {
        double a = 0.04;
        double b = 0.01;
        int n;
        double[][] matrix_2section;
        try {
            matrix_2section = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\BA_adjacencyMatrix.txt");
            n = matrix_2section.length;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Random random = new Random();
        int index = random.nextInt(n); // 随机选择一个初始节点为I状态
        int[][] state = new int[n][2]; // 记录节点状态
        for (int i = 0; i < n; i++) { // 初始化节点状态
            state[i][0] = i;
            state[i][1] = 0; // 0表示为S状态； 1表示I状态 ；2表示R状态
            if (i == index) {
                state[i][1] = 1;
            }
        }
        double st = n - 1;
        double it = 1;
        double rt = 0;

        try (FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\SIR_BAnetwork_model.txt")) {
            for (int k = 1; k <= 100; k++) { // 演化100步
                double x = st / n; // st状态节点在k时刻占所有节点的比例
                double y = it / n; // it状态节点在k时刻占所有节点的比例
                double z = rt / n; //Rt状态节点在k时刻占所有节点的比例

                writer.write(k + "," + x + "," + y + "," + z + "\n");

                int[] newInfections = new int[n];
                int[] recoveries = new int[n];

                for (int i = 0; i < n; i++) { // 遍历每个节点
                    if (state[i][1] == 1) {
                        // 节点免疫
                        if (random.nextDouble() < b) {
                            recoveries[i] = 2;
                        }
                        // 处理新感染
                        for (int j = 0; j < n; j++) { // 寻找第i个节点的邻居节点
                            if (matrix_2section[i][j] == 1 && state[j][1] == 0 && random.nextDouble() < a) {
                                newInfections[j] = 1;
                            }
                        }
                    }
                }

                // 更新节点状态和统计数
                for (int i = 0; i < n; i++) {
                    if (newInfections[i] == 1) {
                        state[i][1] = 1;
                        it++;
                        st--;
                    }
                    if (recoveries[i] == 2) {
                        state[i][1] = 2;
                        it--;
                        rt++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[][] readMatrixFromFile(String filename) throws IOException {
        List<double[]> matrixList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.trim().split("\\s+");
                double[] row = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Double.parseDouble(values[i]);
                }
                matrixList.add(row);
            }
        }
        double[][] matrix = new double[matrixList.size()][];
        for (int i = 0; i < matrixList.size(); i++) {
            matrix[i] = matrixList.get(i);
        }
        return matrix;
    }
}
