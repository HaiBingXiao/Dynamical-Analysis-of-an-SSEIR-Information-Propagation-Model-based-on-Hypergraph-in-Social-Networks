package SEIIR;

import java.io.FileWriter;
import java.io.IOException;

public class SEIIR_Functionnetwork_model_new {
    public static void main(String[] args) {
        double p1 = 0.9;
        double a = 0.8;
        double p3 = 0.018;
        double p4 = 0.01;
        double v3 = 0.008;
        double p2 = 0.008;
        double de = 0.05;
        double m2 = 5;
        double m = 2;

        double Si_t = 0.695;
        double Sa_t = 0.29;
        double E_t = 0.0;
        double I_t = 0.006;
        double R_t = 0.0;

        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Function_model_new.txt");
            for (int t = 1; t <= 500; t++) {
                double new_Si_t = Si_t - (p1 * m2 * m2* m * de * Si_t * I_t);
                double new_Sa_t = Sa_t - (a *m2 * m2* m * de * Sa_t * I_t);
                double new_E_t = E_t + (p1 *m2 * m2* m * de * Si_t * I_t) - (p3 * E_t ) - (p4 * E_t);
                double new_I_t = I_t + (a * m2 * m2* m * de * Sa_t * I_t) + (p3 * E_t ) - (v3 * I_t) - (p2 * I_t);
                double new_R_t = R_t + (p4 * E_t) + (v3 * I_t) + (p2 * I_t);

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
