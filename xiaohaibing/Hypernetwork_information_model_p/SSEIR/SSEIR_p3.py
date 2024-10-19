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
data0 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_p3_1.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
data1 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_p3_2.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
data2 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_p3_3.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
plt.figure(figsize=(10, 6))

plt.plot(data0['t'], data0['I_t'],color='blue',linestyle = "--",  label='θ=0.01')
plt.plot(data1['t'], data1['I_t'],color='red',linestyle = "-",  label='θ=0.03')
plt.plot(data2['t'], data2['I_t'],color='green',linestyle = ":",  label='θ=0.05')

# plt.plot(data0['t'], data0['E_t'],color='blue',linestyle = "--",  label='θ=0.01')
# plt.plot(data1['t'], data1['E_t'],color='red',linestyle = "-",  label='θ=0.03')
# plt.plot(data2['t'], data2['E_t'],color='green',linestyle = ":",  label='θ=0.05')

plt.xlabel('Time (t)')
plt.ylabel('Density')
plt.title('Explore the effect of θ on the I-state')
plt.legend()
# 取消背景网格
plt.grid(False)
# 保存图像为SVG文件
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\Explore the effect of θ on the I-state.svg', format='svg')
plt.show()


