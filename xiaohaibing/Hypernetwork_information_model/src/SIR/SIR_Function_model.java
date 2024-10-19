package SIR;

import java.io.FileWriter;
import java.io.IOException;

public class SIR_Function_model {
    public static void main(String[] args) {
        double a = 0.4;
        double b = 0.1;
        double S_t = 0.9;
        double I_t = 0.1;
        double R_t = 0.0;

        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\SIR_Function_model.txt");
            for (int t = 1; t <= 120; t++) {
                double new_S_t = S_t - (a * I_t * S_t);
                double new_I_t = I_t + (a * I_t * S_t) - (b * I_t);
                double new_R_t = R_t + (b * I_t);
                S_t = new_S_t;
                I_t = new_I_t;
                R_t = new_R_t;
                // 在此写入每个时间步的值
                writer.write(t + "," + S_t + "," + I_t + "," + R_t + "\n");
            }
            // 关闭写入器
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
