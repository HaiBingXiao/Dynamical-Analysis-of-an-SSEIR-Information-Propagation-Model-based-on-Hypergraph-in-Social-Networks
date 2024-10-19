import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Generate_Hypernetwork {
    public static int m0 = 6; // 网络最初创建时包含的节点数
    public static int m1 = 5; // 每个新节点连接的旧节点数
    public static int N = 1000; // 网络演化过程中的总步骤数
    public static int E0 = 2; // 每个时间步中新节点新形成的超边数量

    public static void main(String[] args) {
        Network network = new Network(m0, m1); // 创建网络实例
        network.evolve(N); // 使网络演化N步
        network.printHyperEdges(); // 打印超边信息
        generateAndPrintAdjacencyMatrix(network, N + m0); // 调用方法生成邻接矩阵并输出
    }

    // 生成并输出邻接矩阵
    public static void generateAndPrintAdjacencyMatrix(Network network, int N) {
        // 初始化邻接矩阵
        int[][] adjacencyMatrix = new int[N][N];

        // 遍历网络中的所有超边
        for (Set<VElement> hyperEdge : network.eList) {
            for (VElement v1 : hyperEdge) {
                for (VElement v2 : hyperEdge) {
                    if (!v1.equals(v2)) {
                        // 节点出现在同一条超边中，邻接矩阵对应位置标记为1
                        adjacencyMatrix[v1.getValue()][v2.getValue()] = 1;
                        adjacencyMatrix[v2.getValue()][v1.getValue()] = 1; // 确保矩阵的对称性
                    }
                }
            }
        }

        // 输出邻接矩阵到文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\Hypernetwork_"+m0+m1+"_1_AdjacencyMatrix.txt"))) {
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    writer.write(adjacencyMatrix[i][j] + " ");
                }
                writer.newLine(); // 换行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Network {
    public List<VElement> vSet = new ArrayList<>(); // 存储网络中所有节点的列表
    public List<Set<VElement>> eList = new ArrayList<>(); // 存储网络中所有超边的列表

    public Network(int m0, int m1) {
        initializeNetwork(m0); // 初始化网络
    }

    private void initializeNetwork(int m0) {
        for (int i = 0; i < m0; i++) {
            VElement v = new VElement(i);
            vSet.add(v);
        }
        Set<VElement> initialEdge = new HashSet<>(vSet);
        eList.add(initialEdge);
    }

    public void evolve(int t) {
        for (int t0 = 0; t0 < t; t0++) {
            evolveStep();
        }
    }

    private void evolveStep() {
        // 仅添加一个新节点
        VElement newVElement = new VElement(vSet.size());
        vSet.add(newVElement);

        // 为新节点生成 E0 条超边
        for (int e = 0; e < Generate_Hypernetwork.E0; e++) {
            Set<VElement> newEdge = new HashSet<>();
            newEdge.add(newVElement);

            // 选择 m1 个旧节点来与新节点形成超边
            while (newEdge.size() < Generate_Hypernetwork.m1 + 1) {
                VElement selectedOldVElement = getRandomElementBasedOnHyperDegree();
                if (!newEdge.contains(selectedOldVElement)) {
                    newEdge.add(selectedOldVElement);
                }
            }

            eList.add(newEdge); // 将新超边添加到超边列表
        }
    }

    private VElement getRandomElementBasedOnHyperDegree() {
        // 计算总超度
        double totalHyperDegree = vSet.stream().mapToInt(VElement::getDegree).sum();
        double r = Math.random() * totalHyperDegree;
        double sum = 0;
        for (VElement v : vSet) {
            sum += v.getDegree(); // 这里的 getDegree 返回节点的超度
            if (r < sum) {
                return v;
            }
        }
        return null;
    }

    public void printHyperEdges() {
        for (int i = 0; i < eList.size(); i++) {
            Set<VElement> edge = eList.get(i);
            System.out.print("超边 " + (i + 1) + " 包含节点: ");
            for (VElement v : edge) {
                System.out.print(v.getValue() + " ");
            }
            System.out.println();
        }
    }
}

class VElement {
    private final int value;
    private int degree; // 由于已经不再跟踪每个节点的度数，这个属性可以移除

    public VElement(int value) {
        this.value = value;
        this.degree = 1; // 默认设置为1，不再跟踪度数变化
    }

    public int getValue() {
        return value;
    }

    public int getDegree() {
        // 由于不再跟踪度数，我们返回固定值1
        return 1;
    }
}