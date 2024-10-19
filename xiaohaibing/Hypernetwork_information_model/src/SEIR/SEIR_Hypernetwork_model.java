package SEIR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SEIR_Hypernetwork_model {
    public static void main(String[] args) {
        double a = 0.3; // S遇到I后变为E的概率
        double b = 0.2; // E变为I的概率
        double y = 0.05; // I变为R的概率
        int n; // 节点总数
        double[][] matrix_AdjacencyMatrix; // 邻接矩阵
        try {
            matrix_AdjacencyMatrix = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_43_AdjacencyMatrix.txt");
            n = matrix_AdjacencyMatrix.length;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Random random = new Random();
        int index = random.nextInt(n); // 随机选择一个初始节点为I状态
        int[][] state = new int[n][2]; // 记录节点状态
        for (int i = 0; i < n; i++) { // 初始化节点状态
            state[i][0] = i;
            state[i][1] = 0; // 0表示S状态；1表示E状态；2表示I状态；3表示R状态
            if (i == index) {
                state[i][1] = 2; // 设置初始感染节点
            }
        }
        double st = n - 1; // S状态的节点总数
        double et = 0; // E状态的节点总数
        double it = 1; // I状态的节点总数
        double rt = 0; // R状态的节点总数

        try (FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\SEIR_Hypernetwork_model.txt")) {
            for (int k = 1; k <= 140; k++) { // 模拟140步
                // 在这里写入每一步的状态比例

                int[] newExposed = new int[n]; // 新暴露者
                int[] newInfected = new int[n]; // 新感染者
                int[] newRecovered = new int[n]; // 新康复者

                for (int i = 0; i < n; i++) {
                    if (state[i][1] == 0) { // 如果节点在S状态
                        for (int j = 0; j < n; j++) { // 遍历所有节点
                            if (matrix_AdjacencyMatrix[i][j] == 1 && state[j][1] == 2 && random.nextDouble() < a) {
                                newExposed[i] = 1; // S状态节点有a概率变为E状态
                            }
                        }
                    } else if (state[i][1] == 1) { // 如果节点在E状态
                        if (random.nextDouble() < b) {
                            newInfected[i] = 1; // E状态节点有b概率变为I状态
                        }
                    } else if (state[i][1] == 2) { // 如果节点在I状态
                        if (random.nextDouble() < y) {
                            newRecovered[i] = 1; // I状态节点有y概率变为R状态
                        }
                    }
                }

                // 更新节点状态
                for (int i = 0; i < n; i++) {
                    if (newExposed[i] == 1) {
                        state[i][1] = 1;
                        st--;
                        et++;
                    }
                    if (newInfected[i] == 1) {
                        state[i][1] = 2;
                        et--;
                        it++;
                    }
                    if (newRecovered[i] == 1) {
                        state[i][1] = 3;
                        it--;
                        rt++;
                    }
                }

                // 更新状态计数并写入文件
                double xx = st / n; // S状态节点比例
                double ww = et / n; // E状态节点比例
                double yy = it / n; // I状态节点比例
                double zz = rt / n; // R状态节点比例
                writer.write(k + "," + xx + "," + ww + "," + yy + "," + zz + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从文件读取矩阵
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