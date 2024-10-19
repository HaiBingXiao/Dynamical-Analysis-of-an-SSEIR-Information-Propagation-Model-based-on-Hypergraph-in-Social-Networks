package SEIIR;

import java.io.FileWriter;
import java.io.IOException;

public class SIR_SEIR_SSEIR {
    public static void main(String[] args) {
        double aa = 0.9/1.1;
        double bb = 0.8/1.1;
        double oo = 0.018/1.1;
        double rr = 0.01/1.1;
        double vv = 0.008/1.1;
        double ee = 0.008/1.1;
        double de = 0.05;
        double m2 = 5;
        double m = 2;
        double Si_t = 0.695;
        double Sa_t = 0.29;
        double E_t = 0.0;
        double I_t = 0.006;
        double R_t = 0.0;
        double S_t = Sa_t + Si_t;

        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\SIR_SEIR_SSEIR_1.txt");
            for (int t = 1; t <= 500; t++) {
                double new_S_t = S_t - (((aa + bb) / 2) * I_t * S_t);
                double new_I_t = I_t + (((aa + bb) / 2) * I_t * S_t) - (((vv + ee) / 2) * I_t);
                double new_R_t = R_t + (((vv + ee) / 2) * I_t);
                S_t = new_S_t;
                I_t = new_I_t;
                R_t = new_R_t;
                // 在此写入每个时间步的值
                writer.write(t + "," + S_t + "," + I_t + "," + R_t + "\n");
            }
            // 关闭写入器
            writer.close();
            System.out.println("SIR模型运行完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Si_t = 0.695;
        Sa_t = 0.29;
        E_t = 0.0;
        I_t = 0.006;
        R_t = 0.0;
        S_t = Sa_t + Si_t;
        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\SIR_SEIR_SSEIR_2.txt");
            for (int t = 1; t <= 500; t++) {
                double new_S_t = S_t - (aa * I_t * S_t);
                double new_E_t = E_t + (aa * S_t * I_t) - (oo * E_t);
                double new_I_t = I_t + (oo * E_t) - (((vv + ee) / 2) * I_t);
                double new_R_t = R_t + (((vv + ee) / 2) * I_t);
                S_t = new_S_t;
                E_t = new_E_t;
                I_t = new_I_t;
                R_t = new_R_t;
                // 在此写入每个时间步的值
                writer.write(t + "," + S_t + "," + E_t + "," + I_t + "," + R_t + "\n");
            }
            // 关闭写入器
            writer.close();
            System.out.println("SEIR模型运行完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Si_t = 0.695;
        Sa_t = 0.29;
        E_t = 0.0;
        I_t = 0.006;
        R_t = 0.0;
        S_t = Sa_t + Si_t;
        try {
            FileWriter writer = new FileWriter("D:\\桌面\\Hypernetwork_information_model\\output\\SIR_SEIR_SSEIR_3.txt");
            for (int t = 1; t <= 500; t++) {
                double new_Si_t = Si_t - (aa * m2 * m2* m * de * Si_t * I_t);
                double new_Sa_t = Sa_t - (bb *m2 * m2* m * de * Sa_t * I_t);
                double new_E_t = E_t + (aa *m2 * m2* m * de * Si_t * I_t) - (oo * E_t ) - (rr * E_t);
                double new_I_t = I_t + (aa * m2 * m2* m * de * Sa_t * I_t) + (oo * E_t ) - (vv * I_t) - (ee * I_t);
                double new_R_t = R_t + (rr * E_t) + (vv * I_t) + (ee * I_t);

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
            System.out.println("SSEIR模型运行完成");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
