项目说明：
Descriptions:
这是《面向医疗信息抽取的个性化特征选择方法》毕业论文的相关代码说明。
This is code illustrations for paper "Term-based Personalization for Feature Selection in Clinical Handover Form Auto-filling".
实验使用的数据集来源于CLEF eHealth 2016 Task 1，叫做NICTA Synthetic Nursing Handover Data. 
Dataset used in experiment is from CLEF eHealth 2016 Task 1, called NICTA Synthetic Nursing Handover Data.
数据集及任务的相关说明可以参见：https://sites.google.com/site/clefehealth2016/task-1
More description for the dataset and the task can be found in "https://sites.google.com/site/clefehealth2016/task-1".
数据集的下载地址为：https://sites.google.com/site/clefehealth/datasets
Downloading address for this dataset is "https://sites.google.com/site/clefehealth/datasets".
论文的方法流程为：
1. 为原始数据生成不同数量的特征，主要是使用了两种不同的外部数据，分别为Stanford CoreNLP和MetaMap
CoreNLP的版本为3.6,MetaMap的版本为2013V2。
CoreNLP提供了Jar包，可以直接在java项目中引用，然后生成一些特征，具体的特征参见：https://stanfordnlp.github.io/CoreNLP/annotators.html
MetaMap的使用说明参见：https://metamap.nlm.nih.gov/Installation.shtml

2. 生成完特征需要使用CRF++进行训练和标注。我们主要做了四组实验，来验证不用的特征子集对实验结果的影响，具体实验方法参见论文。
CRF++的安装使用可以参见：https://taku910.github.io/crfpp/

3. 为了求得最优解，我们使用了自然选择算法，来找到每个特征子集对应的权重。自然选择算法通过Matlab程序实现。

4. 在实现论文的方法的基础上，我们还开发了一个原型系统。通过该原型可以上传英文文本，然后返回抽取后的结构化的医疗移交表格。

代码说明：
代码分为三大部分，分别为Code1,GA和NERDemo。
其中Code1包含的代码主要是进行特征的生成，解析，CRF模型的训练，包含上述流程的1,2两步。
GA为MATLAB代码，主要实现了自然选择算法，输入为第1,2两步的输出结果。
NERDemo为根据方法写的一个在线的医疗移交表格自动填充原型系统。
各部分代码的使用说明参见各代码文件夹的readme。

Description:
The whole source code contains three parts, they are “Code1”, “GA” and “NERDemo”. In “Code1”, we mainly generate features, train and test models, and compare TPFS model with different baselines. In “GA”, we implemented natural selection algorithm. “NERDemo” is an online visualization system for showing the filled clinical handover form.
