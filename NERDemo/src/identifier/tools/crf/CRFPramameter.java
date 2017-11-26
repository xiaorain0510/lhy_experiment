package identifier.tools.crf;

public class CRFPramameter {
	
	public static String crf_tool_path = "E:/ClefeHealth/Software/CRF++-0.58_zip";//CRF++ 在的位置
	public static double crf_c = 10.0;//with larger C value, CRF tends to overfit the give training corpus.
	public static int crf_H = 20;//in MIRA training, change the shrinking size.When setting smaller NUM, shrinking occurs in early stage, which drastically reduces training time
	public static String template_path = "E:/project_data/template_project";//CRF++ template文件在的位置 
	public static String train_path = "E:/project_data/train.xml";//训练文件在的位置
	public static String model_path = "E:/project_data/model_project";//训练完成之后model在的位置
}
