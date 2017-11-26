package feature_group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 图的最小树生成算法
 * @author win7
 *
 */
public class MiniSpanTree {
	
	private int dataSetLength;
	private String path;
	private double threshold;
	
	public MiniSpanTree(int dataSetLength, String path) {
		super();
		this.dataSetLength = dataSetLength;
		this.path = path;
	}

	/**
	 * 求图最小生成树的PRIM算法
	 * 基本思想：假设N=(V,{E})是联通网，TE是N上的最想生成树中的变得集合。算法从U={u0}(u0属于V)，
	 * TE={}开始，重复执行下述操作：在所有的u属于U，v属于V-U的边（u，v）属于E中找到一条代价最小
	 * 的边（u0，v0）并入集合TE，同事v0并入U，直至U=V为止。此时TE中必有n-1条边，则T=(V,{TE})
	 * 为N的最小生成树。
	 * @param  graph  图
	 * @param start 开始节点
	 * @param n     图中节点数
	 */
	private ArrayList<Edge> PRIM(double [][] graph,int start,int n){
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		double [][] mins=new double [n][2];//用于保存集合U到V-U之间的最小边和它的值，mins[i][0]值表示到该节点i边的起始节点
		                             //值为-1表示没有到它的起始点，mins[i][1]值表示到该边的最小值，
		                             //mins[i][1]=0表示该节点已将在集合U中
		for(int i=0;i<n;i++){//初始化mins
		
			if(i==start){
				mins[i][0]=-1;
				mins[i][1]=0;
			}else if( graph[start][i]!=-1){//说明存在（start，i）的边
				mins[i][0]=start;
				mins[i][1]= graph[start][i];
			}else{
				mins[i][0]=-1;
				mins[i][1]=Double.MAX_VALUE;
			}
//			System.out.println("mins["+i+"][0]="+mins[i][0]+"||mins["+i+"][1]="+mins[i][1]);
		}
		for(int i=0;i<n-1;i++){
			int minV=-1;
			double minW=Double.MAX_VALUE;
			for(int j=0;j<n;j++){//找到mins中最小值，使用O(n^2)时间
				
			    if(mins[j][1]!=0&&minW>mins[j][1]){
					minW=mins[j][1];
					minV=j;
				}
			}
//			System.out.println("minV="+minV);
			mins[minV][1]=0;
			System.out.println("最小生成树的第"+i+"条最小边=<"+(int)(mins[minV][0])+","+minV+">，权重="+minW);
			Edge edge = new Edge((int)(mins[minV][0]),minV,minW);
			edgeList.add(edge);
			for(int j=0;j<n;j++){//更新mins数组
				if(mins[j][1]!=0){
//					System.out.println("MINV="+minV+"||tree[minV][j]="+tree[minV][j]);
					if( graph[minV][j]!=-1&& graph[minV][j]<mins[j][1]){
						mins[j][0]=minV;
						mins[j][1]= graph[minV][j];
					}
				}
			}
		}
		return edgeList;
	}
	
	
	public void Excute(){
		CalculateEntropy cal = new CalculateEntropy();
		double [][] tree = new double[dataSetLength][dataSetLength];
		for(int i=0;i<dataSetLength;i++){
			for(int j=0;j<dataSetLength;j++){
				if(i==j){
					tree[i][j] = -1.0;
				}else{
					ArrayList<Integer> i_list = new ArrayList<Integer>();
					i_list.add(i);
					ArrayList<Integer> j_list = new ArrayList<Integer>();
					j_list.add(j);
					tree[i][j] = -cal.CalSU(path, i_list, j_list);
				}				
			}
		}
		ArrayList<Edge> edgeList = PRIM(tree, 0, dataSetLength);
		
		ArrayList<Integer> mark = new ArrayList<Integer>();
		for(int i=0;i<edgeList.size();i++){
			Edge edge = edgeList.get(i);
			ArrayList<Integer> i_list = new ArrayList<Integer>();
			i_list.add(edge.i);
			ArrayList<Integer> j_list = new ArrayList<Integer>();
			j_list.add(edge.j);
			double ijSU = cal.CalSU(path, i_list, j_list);
			System.out.println(ijSU);
			if(ijSU<threshold){
				mark.add(i);
			}
		}
		for(int i=0;i<mark.size();i++){
			edgeList.remove(mark.get(i));
		}
		System.out.println(edgeList.size());
		System.out.println("+++++++++++++++++++++++++++++++++");
	}

}
