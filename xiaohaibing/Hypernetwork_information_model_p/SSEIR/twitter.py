import pandas as pd
import matplotlib.pyplot as plt
import glob
from matplotlib.lines import Line2D

# 设置中文字体以避免字体缺失警告
plt.rcParams['font.sans-serif'] = ['SimSun']  # 用黑体显示中文
plt.rcParams['axes.unicode_minus'] = False  # 正常显示负号
plt.rcParams['svg.fonttype'] = 'none'
plt.rcParams['font.family'] = 'Times New Roman'  # 设置字体为新罗马
plt.rcParams.update({'font.size': 20})  # 设置所有字体大小为16
# 获取所有文件路径
file_paths = glob.glob(r'D:\\桌面\\Hypernetwork_information_model\SSEIR_output2\SEIIR_Hypernetwork_model*.txt')

# 读取并合并所有数据
all_data = pd.concat(
    [pd.read_csv(file, header=None, names=['t', 'S_i', 'S_a', 'E_t', 'I_t', 'R_t']) for file in file_paths],
    ignore_index=True
)

# 对每个时间点计算每个状态的最大值、最小值和平均值
agg_data = all_data.groupby('t').agg(['max', 'min', 'mean'])
agg_data.columns = ['_'.join(col) for col in agg_data.columns]

# 每 5 个时间步取一个值
agg_data_sampled = agg_data.iloc[::10]

# 定义需要显示的属性
states_to_display = ['R_t', 'I_t', 'E_t']  # 修改此列表以显示所需的属性

# 控制是否显示最大值和最小值点
show_max = False
show_min = False

# 绘制图像
plt.figure(figsize=(10, 6))

colors = {
    'S_i': 'blue',
    'S_a': 'black',
    'E_t': 'lightblue',
    'I_t': 'red',
    'R_t': 'green'
}

for state in states_to_display:
    # 显示平均值点并用线连接
    plt.plot(agg_data_sampled.index, agg_data_sampled[f'{state}_mean'], color=colors[state], linestyle='-', marker='*', label=f'{state}')

    # 绘制最大值和最小值之间的垂直竖线（可选）
    if show_max or show_min:
        plt.vlines(
            x=agg_data_sampled.index,
            ymin=agg_data_sampled[f'{state}_min'] if show_min else agg_data_sampled[f'{state}_mean'],
            ymax=agg_data_sampled[f'{state}_max'] if show_max else agg_data_sampled[f'{state}_mean'],
            color=colors[state], linestyle='dotted'
        )

# 添加标签和标题
plt.xlabel('Time')
plt.ylabel('Density')
plt.title("(B)   Social Network Analysis")
# 自定义图例
plt.legend()

# 取消背景网格
plt.grid(False)

# 保存图像为SVG文件
plt.tight_layout()
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\twitter.svg', format='svg')

# 显示图像
plt.show()
