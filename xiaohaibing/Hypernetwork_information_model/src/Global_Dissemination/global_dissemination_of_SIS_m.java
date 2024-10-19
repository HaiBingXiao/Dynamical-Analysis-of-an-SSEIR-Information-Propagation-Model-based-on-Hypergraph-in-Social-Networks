package Global_Dissemination;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class global_dissemination_of_SIS_m {
    public static void main(String[] args) {
        double b = 0.4;
        double y = 0.06;
        int n1, n2, n3;
        double[][] matrix_AdjacencyMatrix43, matrix_AdjacencyMatrix65, matrix_AdjacencyMatrix87;
        try {
            matrix_AdjacencyMatrix43 = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_87_1_AdjacencyMatrix.txt");
            n1 = matrix_AdjacencyMatrix43.length;

            matrix_AdjacencyMatrix65 = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_87_2_AdjacencyMatrix.txt");
            n2 = matrix_AdjacencyMatrix65.length;

            matrix_AdjacencyMatrix87 = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_87_3_AdjacencyMatrix.txt");
            n3 = matrix_AdjacencyMatrix87.length;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Random random = new Random();

        for (int p = 0; p < 3; p++) {
            int n = 0;
            String xxx = "";
            double[][] matrix = null;
            if (p == 0) {
                n = n1;
                xxx = "m1=7_m=1";
                matrix = matrix_AdjacencyMatrix43;
            } else if (p == 1) {
                n = n2;
                xxx = "m1=7_m=2";
                matrix = matrix_AdjacencyMatrix65;
            } else if (p == 2) {
                n = n3;
                xxx = "m1=7_m=3";
                matrix = matrix_AdjacencyMatrix87;
            }

                List<Double> averageInfectionRatios = new ArrayList<>();
                for (int k = 0; k < 40; k++) {
                    averageInfectionRatios.add(0.0);
                }

                for (int exp = 0; exp < 50; exp++) { // 重复实验50次
                    int[] state = new int[n]; // 记录节点状态
                    // 初始化节点状态
                    for (int i = 0; i < n; i++) {
                        state[i] = 0; // 所有节点初始为S状态
                    }
                    int index = random.nextInt(n);
                    state[index] = 1; // 随机选择一个初始节点为I状态

                    double st = n - 1;
                    double it = 1;

                    for (int k = 0; k < 40; k++) { // 演化40步
                        int[] newInfections = new int[n];
                        int[] recoveries = new int[n];

                        for (int i = 0; i < n; i++) {
                            if (state[i] == 1) {
                                if (random.nextDouble() < y) {
                                    recoveries[i] = 1;
                                }
                                for (int j = 0; j < n; j++) {
                                    if (matrix[i][j] == 1 && state[j] == 0 && random.nextDouble() < b) {
                                        newInfections[j] = 1;
                                    }
                                }
                            }
                        }

                        for (int i = 0; i < n; i++) {
                            if (newInfections[i] == 1 && state[i] == 0) {
                                state[i] = 1;
                                it++;
                                st--;
                            }
                            if (recoveries[i] == 1 && state[i] == 1) {
                                state[i] = 0;
                                it--;
                                st++;
                            }
                        }

                        double currentInfectionRatio = it / n;
                        averageInfectionRatios.set(k, averageInfectionRatios.get(k) + currentInfectionRatio);
                    }
                }

                for (int k = 0; k < 40; k++) {
                    averageInfectionRatios.set(k, averageInfectionRatios.get(k) / 50);
                }

                try (FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\global_dissemination_of_SIS_Hypernetwork_" + xxx + ".txt")) {
                    for (int k = 0; k < 40; k++) {
                        double averageInfectionRatio = averageInfectionRatios.get(k);
                        writer.write((k + 1) + "," + averageInfectionRatio + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
