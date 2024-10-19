import matplotlib.pyplot as plt
import pandas as pd

# 文件路径和对应颜色
file_paths = [
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_startr=0.01.txt', 'blue'),
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_startr=0.03.txt', 'red'),
    ('D:\\桌面\\Hypernetwork_information_model\\SEIIR_MY\\SEIIR-of-porperty_startr=0.06.txt', 'green')
]

# 创建图像
plt.figure(figsize=(10, 6))

# 读取每个文件并绘制 I_t
for index, (file_path, color) in enumerate(file_paths, start=1):
    data = pd.read_csv(file_path, header=None, names=['t', 'S_i', 'S_a', 'E_t', 'I_t', 'R_t'])
    label = f'I_t_{index}'
    plt.plot(data['t'], data['I_t'], color=color, linestyle="-.", label=label)

# 设置图表标签和标题
plt.xlabel('Time (t)')
plt.ylabel('Probability')
plt.title('I_t Function Model from Different Files')
plt.legend()
plt.grid(True)
plt.tight_layout()  # 自动调整子图参数以给标签留出更多空间
plt.show()
