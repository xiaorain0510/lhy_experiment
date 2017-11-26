这是《面向医疗信息抽取的个性化特征选择方法》毕业论文的相关代码说明。
实验使用的数据集来源于CLEF eHealth 2016 Task 1，叫做NICTA Synthetic Nursing Handover Data. 
数据集及任务的相关说明可以参见：https://sites.google.com/site/clefehealth2016/task-1
数据集的下载地址为：https://sites.google.com/site/clefehealth/datasets
论文的方法流程为：
1. 为原始数据生成不同数量的特征，主要是使用了两种不同的外部数据，分别为Stanford CoreNLP和MetaMap
CoreNLP的版本为3.6,MetaMap的版本为2013V2。
CoreNLP提供了Jar包，可以直接在java项目中引用，然后生成一些特征，具体的特征参见：https://stanfordnlp.github.io/CoreNLP/annotators.html
MetaMap的使用说明参见：https://metamap.nlm.nih.gov/Installation.shtml

2. 生成完特征需要使用CRF++进行训练和标注。我们主要做了四组实验，来验证不用的特征子集对实验结果的影响，具体实验方法参见论文。
CRF++的安装使用可以参见：https://taku910.github.io/crfpp/

3. 为了求得最优解，我们使用了自然选择算法，来找到每个特征子集对应的权重。自然选择算法通过Matlab程序实现。

4. 在实现论文的方法的基础上，我们还开发了一个原型系统。通过该原型可以上传英文文本，然后返回抽取后的结构化的医疗移交表格。

