package SEIIR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SEIIR_NWnetwork_model {
    public static void main(String[] args) {
        final int NUM_EXPERIMENTS = 50;
        final int NUM_STEPS = 500;
        final String OUTPUT_FILE_PATH = "D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_NWnetwork_model.txt";
        double[][] cumulativeStateRatios = new double[NUM_STEPS][5];

        for (int experiment = 0; experiment < NUM_EXPERIMENTS; experiment++) {
            double p1 = 0.3/2.5;
            double a = 0.25/2.5;
            double p3 = 0.20/2.5;
            double p4 = 0.01/2.5;
            double v3 = 0.06/2.5;
            double p2 = 0.002/2.5;

            int n; // 节点总数
            double[][] matrix_2section; // 邻接矩阵
            try {
                matrix_2section = readMatrixFromFile("D:\\桌面\\Hypernetwork_information_model\\output\\NW_adjacencyMatrix.txt");
                n = matrix_2section.length;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Random random = new Random();

            int[][] state = new int[n][2]; // 记录节点状态：0为Si、1为Sa、2为E、3为I、4为R

            double P_sa = 0.3;
            int x = (int)(n * P_sa);//记录Sa用户节点数量
            Set<Integer> set = new HashSet<>();//定义一个集合用于存放Sa状态的节点编号（set集合不重复）
            while (set.size() < x) {//随机选择x个节点作为Sa状态的节点
                set.add(random.nextInt(n));
            }
            for (int node_x : set) {//对set集合中的Sa节点编号进行状态赋值
                state[node_x][1] = 1; //1表示Sa状态
            }
            for (int i = 0; i < n; i++) {
                if (state[i][1] != 1) {
                    state[i][1] = 0;//将剩余节点状态赋值为0
                }
            }
            Set<Integer> index_start = new HashSet<>();//定义一个不重复的集合，来存储初始感染节点编号
            while (index_start.size() <= 5) {
                index_start.add(random.nextInt(n));//随机选择初始感染节点
            }
            int si_n = 0;
            int sa_n = 0;
            for (int i : index_start) {
                if (state[i][1] == 0) {
                    si_n++;
                }
                if (state[i][1] == 1) {
                    sa_n++;
                }
                state[i][1] = 3;//将这节点的状态设置为I状态
            }

            double si = (int)(n * 0.7)-si_n; // Si状态的节点总数
            double sa = (int)(n * 0.3)-sa_n; // Sa状态的节点总数
            double et = 0; // E状态的节点总数
            double it = 5; // I状态的节点总数
            double rt = 0; // R状态的节点总数

            for (int k = 0; k < NUM_STEPS; k++) {
                // 在这里写入每一步的状态比例
                int[] newExposed = new int[n]; // 新暴露者
                int[] newInfected = new int[n]; // 新感染者
                int[] newRecovered = new int[n]; // 新康复者

                for (int i = 0; i < n; i++) {
                    if (state[i][1] == 0) { // 如果节点在Si状态
                        for (int j = 0; j < n; j++) { // 遍历所有节点
                            if (matrix_2section[i][j] == 1 && state[j][1] == 3 && random.nextDouble() < p1) {
                                newExposed[i] = 1; // Si状态节点有p1概率变为E状态
                            }
                        }
                    }else if (state[i][1] == 1) { // 如果节点在Sa状态
                        for (int j = 0; j < n; j++) { // 遍历所有节点
                            if (matrix_2section[i][j] == 1 && state[j][1] == 3 && random.nextDouble() < a) {
                                newInfected[i] = 1;
                            }
                        }
                    } else if (state[i][1] == 2) { // 如果节点在E状态
                        double randomm = random.nextDouble();
                        double rr = random.nextDouble();
                        if (randomm >= 0.5) {//E状态节有可能转换成I也可能变为R
                            if ( rr< p3) {
                                newInfected[i] = 11;//通过E状态变为I
                            }
                        }else {
                            if (rr < p4) {
                                newRecovered[i] = 22;//通过E状态变为R
                            }
                        }
                    } else if (state[i][1] == 3) { // 如果节点在I状态
                        double randomm = random.nextDouble();
                        double rr = random.nextDouble();
                        if (randomm >= 0.5) {//E状态节有可能转换成I也可能变为R
                            if ( rr< v3) {
                                newRecovered[i] = 1;
                            }
                        }else {
                            if (rr < p2) {
                                newRecovered[i] = 1;
                            }
                        }
                    }
                }

                // 更新节点状态
                for (int i = 0; i < n; i++) {
                    if (newExposed[i] == 1) {
                        state[i][1] = 2;
                        si--;
                        et++;
                    }
                    if (newInfected[i] == 1) {
                        state[i][1] = 3;
                        sa--;
                        it++;
                    }
                    if (newInfected[i] == 11) {
                        state[i][1] = 3;
                        et--;
                        it++;
                    }
                    if (newRecovered[i] == 22) {
                        state[i][1] = 4;
                        et--;
                        rt++;
                    }
                    if (newRecovered[i] == 1) {
                        state[i][1] = 4;
                        it--;
                        rt++;
                    }
                }
                // 累加每个状态的节点比例
                cumulativeStateRatios[k][0] += si / n;
                cumulativeStateRatios[k][1] += sa / n;
                cumulativeStateRatios[k][2] += et / n;
                cumulativeStateRatios[k][3] += it / n;
                cumulativeStateRatios[k][4] += rt / n;
            }
        }

        // 计算平均状态比例并写入文件
        try (FileWriter writer = new FileWriter(OUTPUT_FILE_PATH)) {
            for (int k = 0; k < NUM_STEPS; k++) {
                double avgSi = cumulativeStateRatios[k][0] / NUM_EXPERIMENTS;
                double avgSa = cumulativeStateRatios[k][1] / NUM_EXPERIMENTS;
                double avgEt = cumulativeStateRatios[k][2] / NUM_EXPERIMENTS;
                double avgIt = cumulativeStateRatios[k][3] / NUM_EXPERIMENTS;
                double avgRt = cumulativeStateRatios[k][4] / NUM_EXPERIMENTS;

                writer.write((k + 1) + "," + avgSi + "," + avgSa + "," + avgEt + "," + avgIt + "," + avgRt + "\n");
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