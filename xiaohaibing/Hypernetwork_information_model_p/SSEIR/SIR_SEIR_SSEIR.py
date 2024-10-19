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
data0 = pd.read_csv('D:\桌面\Hypernetwork_information_model\output\SIR_SEIR_SSEIR_1.txt', header=None, names=['t', 'S','I','R'])
data1 = pd.read_csv('D:\桌面\Hypernetwork_information_model\output\SIR_SEIR_SSEIR_2.txt', header=None, names=['t', 'S', 'E','I','R'])
data2 = pd.read_csv('D:\桌面\Hypernetwork_information_model\output\SIR_SEIR_SSEIR_3.txt', header=None, names=['t', 'S_i','S_a','E','I','R'])

plt.figure(figsize=(10, 6))

plt.plot(data0['t'], data0['I'],color='blue',linestyle = "--",  label='SIR')
plt.plot(data1['t'], data1['I']+data1['E'],color='red',linestyle = "-",  label='SEIR')
plt.plot(data2['t'], data2['I']+data2['E'],color='green',linestyle = ":",  label='SSEIR')

# plt.plot(data0['t'], data0['R'],color='blue',linestyle = "--",  label='SIR')
# plt.plot(data1['t'], data1['R'],color='red',linestyle = "-",  label='SEIR')
# plt.plot(data2['t'], data2['R'],color='green',linestyle = ":",  label='SSEIR')

plt.xlabel('Time (t)')
plt.ylabel('Density')
plt.legend()
plt.grid(False)
# 保存图像为SVG文件
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\SIR_SEIR_SSEIR.svg', format='svg')
plt.show()