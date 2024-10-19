import pandas as pd
import matplotlib.pyplot as plt
# 读取 CSV 文件
data0 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_model_N=1000.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
data1 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_model_N=5000.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
data2 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_model_N=10000.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
plt.figure(figsize=(10, 6))
plt.plot(data0['t'], data0['R_t'],color='green',linestyle = ":", label='N=1000')
plt.plot(data1['t'], data1['R_t'],color='red',linestyle = "--", label='N=5000')
plt.plot(data2['t'], data2['R_t'],color='blue',linestyle = "-.", label='N=10000')
plt.xlabel('Time (t)')
plt.ylabel('R-Probability')
plt.title('glob_dissemination_of_N')
plt.legend()
plt.grid(True)



plt.show()