package SIS;

import java.io.FileWriter;
import java.io.IOException;

public class SIS_Function_model {
    public static void main(String[] args) {
        double a = 0.3;
        double b = 0.01;
        double S_t = 0.999;
        double I_t = 0.001;

        try {
            FileWriter writer = new FileWriter("D:\\IDEA\\Hypernetwork_information_model\\output\\SIS_Function_model.txt");
            for (int t = 1; t <= 100; t++) {
                double new_S_t = S_t - (a * I_t * S_t) + (b * I_t);
                double new_I_t = I_t + (a * I_t * S_t) - (b * I_t);
                S_t = new_S_t;
                I_t = new_I_t;
                // 在此写入每个时间步的值
                writer.write( t + "," + S_t + "," + I_t + "\n");
            }
            // 关闭写入器
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
