package Global_Dissemination;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class global_dissemination_of_SIS {
    static double y = 0.06;//免疫率！！！！！！！！！！！！论文中是0.06，但是原代码中是0.03
    static double[] b = new double[100];
    public static void main(String[] args) {
        double increment = 0.5 / b.length;
        double value = 0.04;
        for (int i = 0; i < b.length; i++) {
            b[i] = value;
            value += increment;
        }
        for (int i = 0; i < 3; i++) {//对理论值进行拟合
            if (i == 0) {
                theory(3);
            }
            if (i==1) {
                theory(5);
            }
            if (i == 2) {
                theory(7);
            }
        }

        double[][] matrix_AdjacencyMatrix_43;
        double[][] matrix_AdjacencyMatrix_65;
        double[][] matrix_AdjacencyMatrix_87;
        try {
            matrix_AdjacencyMatrix_43 = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_43_AdjacencyMatrix.txt");
            matrix_AdjacencyMatrix_65 = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_65_AdjacencyMatrix.txt");
            matrix_AdjacencyMatrix_87 = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_87_AdjacencyMatrix.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        experiment(matrix_AdjacencyMatrix_43,3);
        experiment(matrix_AdjacencyMatrix_65,5);
        experiment(matrix_AdjacencyMatrix_87,7);

    }

    public static void experiment(double[][] matrix, int nn) {
        Random random = new Random();
        int n = matrix.length;
        double gamma = 0.06; // 恢复速率
        double[] bb = {0.04, 0.08, 0.12, 0.2, 0.28, 0.36, 0.44, 0.48}; // 传播速率数组

        try (FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\global_dissemination_of_SIS_Hypernetwork_" + nn + ".txt")) {
            for (double beta : bb) { // 在不同传播率 beta 下
                double[] infectionRatios = new double[50]; // I状态节点在占所有节点的比例

                for (int t = 0; t < 50; t++) { // 重复实验50次
                    int[][] state = new int[n][1]; // 记录节点状态，0表示S状态，1表示I状态
                    int index = random.nextInt(n); // 随机选择一个初始节点为I状态
                    state[index][0] = 1;
                    double it = 1; // I状态节点个数
                    double st = n - it; // S状态节点个数
                    for (int k = 0; k < 50 ; k++) { // 每次实验演化30步或直到达到稳态
                        int[] newInfections = new int[n];
                        int[] recoveries = new int[n];
                        for (int i = 0; i < n; i++) { // 遍历每个节点
                            if (state[i][0] == 1) {
                                // 处理恢复
                                if (random.nextDouble() < gamma) {
                                    recoveries[i] = 1;
                                }
                                // 处理新感染
                                for (int j = 0; j < n; j++) {
                                    if (matrix[i][j] == 1 && state[j][0] == 0 && random.nextDouble() < beta) {
                                        newInfections[j] = 1;
                                    }
                                }
                            }
                        }
                        // 更新节点状态和统计数
                        for (int i = 0; i < n; i++) {
                            if (newInfections[i] == 1) {
                                state[i][0] = 1;
                                it++;
                                st--;
                            }
                            if (recoveries[i] == 1) {
                                state[i][0] = 0;
                                it--;
                                st++;
                            }
                        }
                    }
                    infectionRatios[t] = it / n;
                }
                // 计算平均感染率
                double sum = 0;
                for (double ratio : infectionRatios) {
                    sum += ratio;
                }
                double averageRatio = sum / infectionRatios.length;
                writer.write(beta + "," + averageRatio + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void theory(double m1) {
        double m = m1;
        double p = 0;

        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\global_dissemination_of_SIS_theory_" + m1 + ".txt");
            for (int i = 0; i < b.length; i++) {
                double o = (1 - (y / (b[i] * (m - 1))));
                p = (((m - 1) * b[i] * o) - ((m + 1) * b[i] * o * (1 - o)))
                        / (y + ((m - 1) * b[i] * o));
                writer.write(b[i] + "," + p + "\n");
            }
            // 关闭写入器
            writer.close();
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
