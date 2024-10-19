import matplotlib.pyplot as plt
import pandas as pd

# 文件路径和对应颜色
file_paths = [
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_starts=0.9.txt', 'blue'),
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_starts=0.8.txt', 'red'),
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_starts=0.7.txt', 'green'),
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_starts=0.6.txt', 'purple'),
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_starts=0.5.txt', 'orange')
]

# 读取第一个文件，获取时间数据
data0 = pd.read_csv(file_paths[0][0], header=None, names=['t', 'S_i', 'S_a', 'E_t', 'I_t', 'R_t'])

# 绘制每个状态的数据
for state in ['S_i', 'S_a', 'E_t', 'I_t', 'R_t']:
    plt.figure(figsize=(10, 6))

    # 绘制每个文件中对应状态的数据
    for file_path, color in file_paths:
        data = pd.read_csv(file_path, header=None, names=['t', 'S_i', 'S_a', 'E_t', 'I_t', 'R_t'])
        plt.plot(data['t'], data[state], color=color, linestyle="-.", label=f'{state}')

    # 设置图表标签和标题
    plt.xlabel('Time (t)')
    plt.ylabel('Probability')
    plt.title(f'{state} Function Model from Different Files')
    plt.legend()
    plt.grid(True)
    plt.tight_layout()  # 自动调整子图参数以给标签留出更多空间
    plt.show()
