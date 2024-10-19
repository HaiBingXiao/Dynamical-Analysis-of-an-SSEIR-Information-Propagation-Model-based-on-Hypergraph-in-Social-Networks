import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import glob

# 设置中文字体以避免字体缺失警告
plt.rcParams['font.sans-serif'] = ['SimSun']  # 用黑体显示中文
plt.rcParams['axes.unicode_minus'] = False  # 正常显示负号
plt.rcParams['svg.fonttype'] = 'none'
# 设置字体为新罗马并设置字号为11
plt.rcParams['font.family'] = 'Times New Roman'

# 获取所有文件路径
file_paths = glob.glob(r'D:\\桌面\\Hypernetwork_information_model\SSEIR_output2\SEIIR_Hypernetwork_model*.txt')
data0 = pd.read_csv(r'D:\\桌面\\Hypernetwork_information_model\SEIIR_output\SEIIR_Function_model_new.txt', header=None,
                    names=['t', 'S_i', 'S_a', 'E_t', 'I_t', 'R_t'])

# 初始化一个空的数据框
all_data = []

# 循环读取每个文件并合并数据
for file in file_paths:
    data = pd.read_csv(file, header=None, names=['t', 'S_i', 'S_a', 'E_t', 'I_t', 'R_t'])
    all_data.append(data)

# 将所有数据合并到一个DataFrame中
all_data = pd.concat(all_data, ignore_index=True)

# 对每个时间点计算每个状态的最大值、最小值和平均值
agg_data = all_data.groupby('t').agg({
    'S_i': ['max', 'min', 'mean'],
    'S_a': ['max', 'min', 'mean'],
    'E_t': ['max', 'min', 'mean'],
    'I_t': ['max', 'min', 'mean'],
    'R_t': ['max', 'min', 'mean']
})

# 重命名列
agg_data.columns = ['_'.join(col).strip() for col in agg_data.columns.values]

# 每 20 个时间步取一个值
agg_data_sampled = agg_data.iloc[::10]

# 定义需要显示的属性
states_to_display = ['R_t', 'I_t','E_t']  # 修改此列表以显示所需的属性

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
linestyles = {
    'max': '--',
    'min': ':',
    'mean': '-',
    'func': '-.'
}

# 自定义图例元素
legend_elements = {}

for state in states_to_display:
    if show_max:
        plt.scatter(agg_data_sampled.index, agg_data_sampled[f'{state}_max'], color=colors[state], marker='v')
    if show_min:
        plt.scatter(agg_data_sampled.index, agg_data_sampled[f'{state}_min'], color=colors[state], marker='^')

    # 始终显示平均值点
    plt.scatter(agg_data_sampled.index, agg_data_sampled[f'{state}_mean'], color=colors[state], marker='*')

    # 绘制最大值和最小值之间的垂直竖线
    for idx in agg_data_sampled.index:
        if show_max or show_min:
            plt.vlines(x=idx, ymin=agg_data_sampled.loc[idx, f'{state}_min'],
                       ymax=agg_data_sampled.loc[idx, f'{state}_max'], color=colors[state], linestyle='dotted')

# 绘制 func 属性的曲线
for state in states_to_display:
    plt.plot(data0['t'], data0[state], color=colors[state], linestyle=linestyles['func'])
    legend_elements[colors[state]] = state

# 添加标签和标题
plt.xlabel('Time')
plt.ylabel('Density')
#plt.title('The comparison of theoretical and simulation experiments of the SSEIR model')

# 自定义图例
from matplotlib.lines import Line2D
custom_lines = [Line2D([0], [0], color=color, lw=2) for color in legend_elements.keys()]
custom_labels = [f'{label}' for label in legend_elements.values()]
plt.legend(custom_lines, custom_labels)

# 取消背景网格
plt.grid(False)

# 保存图像为SVG文件
plt.tight_layout()
plt.savefig('D:\\桌面\\Hypernetwork_information_model_p\\photo\\theoretical and simulation.svg', format='svg')

# 显示图像
plt.show()


