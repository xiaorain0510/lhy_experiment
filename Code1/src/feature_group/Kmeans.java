package feature_group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Kmeans {
	private int k;
	private int m;// iteration times
	private ArrayList<Integer> dataSet;// dataset linked table
	private int dataSetLength;
	private ArrayList<Integer> center;// center linked table
	private ArrayList<ArrayList<Integer>> cluster; 
	private ArrayList<Float> jc;// sum value of square error 
	private Random random;
    private String path;//input file path
	
    
	public ArrayList<ArrayList<Integer>> getCluster() {
		ArrayList<ArrayList<Integer>> newCluster = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<cluster.size();i++){
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(int j=0;j<cluster.get(i).size();j++){
				list.add(cluster.get(i).get(j)+1);
			}
			list.add(0);
			newCluster.add(list);
		}
		return newCluster;
	}

	public Kmeans(int k, int dataSetLength,String path) {
		super();
		this.k = k;
		this.dataSetLength = dataSetLength-1;
		this.path = path;
	}

	private void init() {
		m = 0;
		random = new Random();
		dataSet = new ArrayList<Integer>();
		for(int i=0;i<dataSetLength;i++){
			dataSet.add(i);
		}
		center = initCenters();
		cluster = initCluster();//k empty cluster
		jc = new ArrayList<Float>();
	}
	
	private ArrayList<Integer> initCenters() {
		ArrayList<Integer> center = new ArrayList<Integer>();
		int[] randoms = new int[k];
		boolean flag;
		int temp = random.nextInt(dataSetLength);
		randoms[0] = temp;
		for (int i = 0; i < k; i++) {
			flag = true;
			while (flag) {
				temp = random.nextInt(dataSetLength);
				int j = 0;
				while (j < i) {
					if (temp == randoms[j]) {
						break;
					}
					j++;
				}
				if (j == i) {
					flag = false;
				}
			}
			randoms[i] = temp;
		}
		for (int i = 0; i < k; i++) {
			center.add(dataSet.get(randoms[i]));// 生成初始化中心链表
		}
		return center;
	}

	/**
	 * @return k empty clusters
	 */
	private ArrayList<ArrayList<Integer>> initCluster() {
		ArrayList<ArrayList<Integer>> cluster = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < k; i++) {
			cluster.add(new ArrayList<Integer>());
		}
		return cluster;
	}

	private double distance(int element, int center) {
		double distance = 0.0;
		CalculateEntropy cal = new CalculateEntropy();
		if(element==center){
			distance = 0.0;
		}else{
			distance = cal.CalSU(path, element+1, center+1);
		}
		return distance;
	}

	private int minDistance(double[] distance) {
		double minDistance = distance[0];
		int minLocation = 0;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minLocation = i;
			} 
		}
		return minLocation;
	}

	/**
	 * 核心，将当前元素放到最小距离中心相关的簇中
	 */
	private void clusterSet() {
		double[] distance = new double[k];
		for (int i = 0; i < dataSetLength; i++) {
			for (int j = 0; j < k; j++) {
				distance[j] = distance(dataSet.get(i), center.get(j));
			}
			int minLocation = minDistance(distance);
			cluster.get(minLocation).add(dataSet.get(i));// 核心，将当前元素放到最小距离中心相关的簇中
		}
	}

	private double errorSquare(int element, int center) {
		CalculateEntropy cal = new CalculateEntropy();
		double errSquare = 0.0;
		if(element!=center){
			errSquare = cal.CalSU(path, element+1, center+1);
		}
		return errSquare;
	}

	/**
	 * 计算误差平方和准则函数方法
	 */
	private void countRule() {
		float jcF = 0;
		for (int i = 0; i < cluster.size(); i++) {
			for (int j = 0; j < cluster.get(i).size(); j++) {
				jcF += errorSquare(cluster.get(i).get(j), center.get(i));
			}
		}
		jc.add(jcF);
	}

	private void setNewCenter() {
		for (int i = 0; i < k; i++) {
			int n = cluster.get(i).size();
			if (n != 0&&n > 2) {
				HashMap<Integer,Double> errorMap = new HashMap<Integer,Double>();
				CalculateEntropy cal = new CalculateEntropy();
				for (int j = 0; j < n; j++) {
					double errorSum = 0.0;
					for(int z=0;z<n;z++){						
						errorSum+=errorSquare(cluster.get(i).get(z), cluster.get(i).get(j));
					}
					errorMap.put(cluster.get(i).get(j), errorSum);
				}
				List<Map.Entry<Integer,Double>> list = new ArrayList<Map.Entry<Integer,Double>>(errorMap.entrySet());
		        Collections.sort(list,new Comparator<Map.Entry<Integer,Double>>() {
		            //升序排序
		            public int compare(Entry<Integer,Double> o1,
		                    Entry<Integer,Double> o2) {
		                return o1.getValue().compareTo(o2.getValue());
		            }
		            
		        });
		        int newCenter = cluster.get(i).get(0);
		        for(Map.Entry<Integer,Double> mapping:list){ 
		               newCenter = mapping.getKey();
		               break;
		          } 
				center.set(i, newCenter);
			}
		}
	}

	 public void printDataArray(ArrayList<Integer> dataArray,  String dataArrayName) {  
	        for (int i = 0; i < dataArray.size(); i++) {  
	            System.out.println("print:" + dataArrayName + "[" + i + "]={"  
	                    + dataArray.get(i) + "}");  
	        }  
	        System.out.println("===================================");  
	    }  
	 
	private void kmeans() {
		init();
		while (true) {
			printArrayList(center);
			clusterSet();
			countRule();
			System.out.println(jc.get(m));
			if (m != 0) {
				if (jc.get(m) - jc.get(m - 1) == 0) {
					break;
				}
			}
			setNewCenter();
			m++;
			cluster.clear();
			cluster = initCluster();
		}
	}

	public void execute() {
		long startTime = System.currentTimeMillis();
		System.out.println("kmeans begins");
		kmeans();
		long endTime = System.currentTimeMillis();
		System.out.println("kmeans running time=" + (endTime - startTime)
				+ "ms");
		System.out.println("kmeans ends");
		System.out.println();
	}
	
	public void printArrayList(ArrayList<Integer> list){
		for(int i=0;i<list.size();i++){
			System.out.print(list.get(i)+" ");
		}
		System.out.println("---------------------");
	}
	
}
