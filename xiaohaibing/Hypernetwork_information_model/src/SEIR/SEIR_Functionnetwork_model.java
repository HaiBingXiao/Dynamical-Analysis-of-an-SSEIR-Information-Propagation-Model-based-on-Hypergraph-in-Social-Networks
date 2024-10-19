package SEIR;

import java.io.FileWriter;
import java.io.IOException;

public class SEIR_Functionnetwork_model {
    public static void main(String[] args) {
        double a = 0.3;
        double b = 0.2;
        double y = 0.05;
        double S_t = 0.999;
        double E_t = 0.0;
        double I_t = 0.001;
        double R_t = 0.0;

        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\SEIR_Function_model.txt");
            for (int t = 1; t <= 200; t++) {
                double new_S_t = S_t - (a * I_t * S_t);
                double new_E_t = E_t + (a * S_t * I_t) - (b * E_t);
                double new_I_t = I_t + (b * E_t) - (y * I_t);
                double new_R_t = R_t + (y * I_t);
                S_t = new_S_t;
                E_t = new_E_t;
                I_t = new_I_t;
                R_t = new_R_t;
                // 在此写入每个时间步的值
                writer.write(t + "," + S_t + "," + E_t + "," + I_t + "," + R_t + "\n");
            }
            // 关闭写入器
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
