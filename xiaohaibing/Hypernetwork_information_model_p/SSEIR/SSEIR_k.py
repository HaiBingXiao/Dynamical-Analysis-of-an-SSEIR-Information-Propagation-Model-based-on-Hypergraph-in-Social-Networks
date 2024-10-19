import pandas as pd
import matplotlib.pyplot as plt
# 设置中文字体以避免字体缺失警告
plt.rcParams['font.sans-serif'] = ['SimSun']  # 用黑体显示中文
plt.rcParams['axes.unicode_minus'] = False  # 正常显示负号
plt.rcParams['svg.fonttype'] = 'none'
# 设置字体为新罗马并设置字号为11
plt.rcParams['font.family'] = 'Times New Roman'
plt.rcParams['font.size'] = 20
# 读取 CSV 文件
data0 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_k_1.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
data1 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_k_2.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
data2 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_k_3.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
plt.figure(figsize=(10, 6))

# plt.plot(data0['t'], data0['I_t'],color='blue',linestyle = "--",  label='m=1 m2=3')
# plt.plot(data1['t'], data1['I_t'],color='red',linestyle = "-",  label='m=2 m2=5')
# plt.plot(data2['t'], data2['I_t'],color='green',linestyle = ":",  label='m=3 m2=7')

plt.plot(data0['t'], data0['R_t'],color='blue',linestyle = "--",  label='m=1 m2=3')
plt.plot(data1['t'], data1['R_t'],color='red',linestyle = "-",  label='m=2 m2=5')
plt.plot(data2['t'], data2['R_t'],color='green',linestyle = ":",  label='m=3 m2=7')

plt.xlabel('Time (t)')
plt.ylabel('Density')
plt.title('Explore the effect of k on the R-state')
plt.legend()
plt.grid(False)
# 保存图像为SVG文件
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\Explore the effect of k on the R-state.svg', format='svg')
plt.show()