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
data0 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Function_model_new.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
plt.figure(figsize=(10, 6))
plt.plot(data0['t'], data0['S_i'],color='blue',linestyle = "--",  label='S_i')
plt.plot(data0['t'], data0['S_a'],color='black',linestyle = "--",  label='S_a')
plt.plot(data0['t'], data0['E_t'],color='lightblue',linestyle = "-",  label='E_t')
plt.plot(data0['t'], data0['I_t'],color='red',linestyle = "-.",  label='I_t')
plt.plot(data0['t'], data0['R_t'],color='green',linestyle = ":", label='R_t')
plt.xlabel('(A)   Time (t)')
plt.ylabel('Density')
plt.title('SSEIR Theoretical model')
plt.legend()
plt.tight_layout()
# 取消背景网格
plt.grid(False)
# 保存图像为SVG文件
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\SSEIR Theoretical model.svg', format='svg')

data0 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\SEIIR_BAnetwork_model.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
plt.figure(figsize=(10, 6))
plt.plot(data0['t'], data0['S_i'],color='blue',linestyle = "--",  label='S_i')
plt.plot(data0['t'], data0['S_a'],color='black',linestyle = "--",  label='S_a')
plt.plot(data0['t'], data0['E_t'],color='lightblue',linestyle = "-",  label='E_t')
plt.plot(data0['t'], data0['I_t'],color='red',linestyle = "-.",  label='I_t')
plt.plot(data0['t'], data0['R_t'],color='green',linestyle = ":", label='R_t')
plt.xlabel('(C)   Time (t)')
plt.ylabel('Density')
plt.title('SSEIR on BA_Network')
plt.legend()
plt.tight_layout()
# 取消背景网格
plt.grid(False)
# 保存图像为SVG文件
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\SSEIR on BA_Network.svg', format='svg')

data0 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\\SEIIR_Hypernetwork_model.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
plt.figure(figsize=(10, 6))
plt.plot(data0['t'], data0['S_i'],color='blue',linestyle = "--",  label='S_i')
plt.plot(data0['t'], data0['S_a'],color='black',linestyle = "--",  label='S_a')
plt.plot(data0['t'], data0['E_t'],color='lightblue',linestyle = "-",  label='E_t')
plt.plot(data0['t'], data0['I_t'],color='red',linestyle = "-.",  label='I_t')
plt.plot(data0['t'], data0['R_t'],color='green',linestyle = ":", label='R_t')
plt.xlabel('(B)   Time (t)')
plt.ylabel('Density')
plt.title('SSEIR on Hyper_Network')
plt.legend()
plt.tight_layout()
# 取消背景网格
plt.grid(False)
# 保存图像为SVG文件
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\SSEIR on Hyper_Network.svg',format='svg')

data0 = pd.read_csv('D:\\桌面\\Hypernetwork_information_model\\SEIIR_output\SEIIR_NWnetwork_model.txt', header=None, names=['t', 'S_i','S_a',  'E_t','I_t','R_t'])
plt.figure(figsize=(10, 6))
plt.plot(data0['t'], data0['S_i'],color='blue',linestyle = "--",  label='S_i')
plt.plot(data0['t'], data0['S_a'],color='black',linestyle = "--",  label='S_a')
plt.plot(data0['t'], data0['E_t'],color='lightblue',linestyle = "-",  label='E_t')
plt.plot(data0['t'], data0['I_t'],color='red',linestyle = "-.",  label='I_t')
plt.plot(data0['t'], data0['R_t'],color='green',linestyle = ":", label='R_t')
plt.xlabel('(D)   Time (t)')
plt.ylabel('Density')
plt.title('SSEIR on NW_Network')
plt.legend()
plt.grid(False)
plt.tight_layout()
# 保存图像为SVG文件
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\SSEIR on NW_Network.svg',format='svg')

# 取消背景网格

plt.show()