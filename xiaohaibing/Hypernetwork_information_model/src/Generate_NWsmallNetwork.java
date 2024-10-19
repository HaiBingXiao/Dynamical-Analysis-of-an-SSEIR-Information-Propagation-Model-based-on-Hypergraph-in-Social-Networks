import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generate_NWsmallNetwork {

    private static final int N = 1000;
    private static final int k = 4;
    private static final double p = 0.1;

    private static ArrayList<ArrayList<Integer>> createNWSmallWorld() {
        ArrayList<ArrayList<Integer>> network = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            network.add(new ArrayList<>());
        }

        for (int i = 0; i < N; i++) {
            for (int j = 1; j <= k / 2; j++) {
                int neighbor = (i + j) % N;
                network.get(i).add(neighbor);
                network.get(neighbor).add(i);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 1; j <= k / 2; j++) {
                if (random.nextDouble() < p) {
                    int neighbor = (i + j) % N;
                    int newNeighbor = random.nextInt(N);

                    network.get(i).remove(Integer.valueOf(neighbor));
                    network.get(neighbor).remove(Integer.valueOf(i));

                    if (!network.get(i).contains(newNeighbor) && newNeighbor != i) {
                        network.get(i).add(newNeighbor);
                        network.get(newNeighbor).add(i);
                    }
                }
            }
        }

        return network;
    }

    private static void writeAdjacencyMatrix(ArrayList<ArrayList<Integer>> network) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\NW_adjacencyMatrix.txt"))) {
            for (int i = 0; i < N; i++) {
                int[] row = new int[N];
                for (int j : network.get(i)) {
                    row[j] = 1;
                }
                for (int j = 0; j < N; j++) {
                    writer.write(row[j] + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> network = createNWSmallWorld();
        writeAdjacencyMatrix(network);
        // 输出网络信息
        for (int i = 0; i < network.size(); i++) {
            System.out.println("Node " + i + " is connected to: " + network.get(i).toString());
        }
    }
}