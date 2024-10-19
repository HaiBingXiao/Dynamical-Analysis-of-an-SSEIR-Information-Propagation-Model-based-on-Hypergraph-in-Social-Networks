package SEIIR;

import java.io.FileWriter;
import java.io.IOException;

public class SEIIR_Functionnetwork_model_book {
    public static void main(String[] args) {
        double p1 = 0.45;
        double a = 0.5;
        double v1 = 0.02;
        double v2 = 0.01;
        double v3 = 0.03;
        double p2 = 0.07;

        double Si_t = 0.699;
        double Sa_t = 0.30;
        double E_t = 0.0;
        double I_t = 0.001;
        double R_t = 0.0;

        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Function_model.txt");
            for (int t = 1; t <= 200; t++) {
                double new_Si_t = Si_t - (p1 * Si_t * I_t);
                double new_Sa_t = Sa_t - (a * Sa_t * I_t);
                double new_E_t = E_t + (p1 * Si_t * I_t) - ((v1 + v2) * E_t);
                double new_I_t = I_t + (a * Sa_t * I_t) + (v1 * E_t ) - (v3 * I_t) - (p2 * I_t * R_t);
                double new_R_t = R_t + (v2 * E_t) + (v3 * I_t) + (p2 * I_t * R_t);

                Si_t = new_Si_t;
                Sa_t = new_Sa_t;
                E_t = new_E_t;
                I_t = new_I_t;
                R_t = new_R_t;

                // 在此写入每个时间步的值
                writer.write(t + "," + Si_t + "," + Sa_t + "," + E_t + "," + I_t + "," + R_t + "\n");
            }
            // 关闭写入器
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
