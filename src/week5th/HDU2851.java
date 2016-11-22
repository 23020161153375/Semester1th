package week5th;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class HDU2851 {
//内存超限了
	
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	//private static Destination[] destinations;
	private static int[][] roads = new int[2000][3];
	
	private static int[] destinations = new int[500];
	private static int n,m;
	
	private static WeightedGraph graph;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cases = input.nextInt();
		//int n,m;
		
		while(cases -- != 0){
			n = input.nextInt();
			m = input.nextInt();
			//int[][] roads = new int[n][3];
			//destinations = new Destination[m];
			
			//三个位置依次存储起点、终点、危险水平
			for(int i = 0;i < n;i ++){
				roads[i][0] = input.nextInt();				
				roads[i][1]  = input.nextInt();
				roads[i][2]  = input.nextInt();				
			}
			for(int i = 0;i < m;i ++){
				//int des = input.nextInt();
				//destinations[i] = new Destination(des,roads[des - 1][2]);				
				destinations[i] = input.nextInt();
			}
			
			graph = new WeightedGraph(getEdges(roads) ,n);		
			int[] cost = graph.getShortestPathsCost(0);
			for(int i = 0;i < m;i ++){
				//int des = destinations[i].des;
				//System.out.println(cost[des - 1] + destinations[i].dangerousLevel);;
				if(cost[ destinations[i] - 1] != -1)
                	System.out.println(cost[ destinations[i] - 1] + roads[destinations[i] - 1][2]);	
				else
                    System.out.println(-1);	
			}
		}	
	}

	
	private static int[][] getEdges(int[][] roads){
		int[][ ] edges = new int[n][n];
		int max = Integer.MAX_VALUE;
		for(int i = 0;i < n;i ++)
			for(int j = 0;j < n;j ++){
				if(i == j)
					edges[i][j] = 0;
				else
					edges[i][j] = max;
			}
								
		for(int i = 0;i < n;i ++){
			int e1 = roads[i][1];
			for(int j = i + 1;j < n;j ++){
				int s2 = roads[j][0];
				if(s2 <= e1)
					edges[i][j] = roads[i][2];			
			}			
		}
			
		return edges;
	}
	
/*	private static class Destination{
		private int des;
		private int dangerousLevel;
		
		public Destination(int des,int dangerousLevel){
			this.des = des;
			this.dangerousLevel = dangerousLevel;			
		}		
	}
*/
	public static class WeightedGraph{
		//private ArrayList<Integer> vertices = new ArrayList<Integer>();
		private int vertices ;
		
		//public static final int DEFAULT_ARC_WEIGHT = 1001;		
		private int[][] arcs;
		
		public static final int UNREACHABLE_DESTINATION_COST = -1;

		private boolean[] shortestPathsFound;
		private int[] shortestPathsCost;
		
		public WeightedGraph(int[][] edges,int numberOfVertices){
			arcs = edges;
		/*
			for(int i = 1; i <= numberOfVertices;i ++)
				vertices.add(i);
		*/
			vertices = numberOfVertices;
			
		}
		
		public int getSize(){
			return vertices;
			//return vertices.size();
		}
	
		public int[]  getShortestPathsCost(int indexOfSource){
			int venum = getSize();
			
			shortestPathsFound = new boolean[venum ];			
			shortestPathsCost = new int[venum ];
			
			for(int i = 0;i < venum;i ++)
				shortestPathsCost[i] = arcs[indexOfSource][i];
			shortestPathsFound[indexOfSource] = true;	
			
			boolean unreachableVerticesLeft = false;
			for(int i = 1; i < venum && !unreachableVerticesLeft;i ++){
				int minimumCost = Integer.MAX_VALUE;
				int indexOfMinCost = -1;
				
				for(int j = 0;j < venum;j ++)
					if(!shortestPathsFound[j] && shortestPathsCost[j] < minimumCost){
						minimumCost = shortestPathsCost[j] ;
						indexOfMinCost = j;
					}
			
				//每次找到离源点最近的点
				//如果剩下来的点对源点都是不可达的，也会依次
				//选出这些点加入“最短路径”点集，其代价是默认的1001（bug）
				if(minimumCost != Integer.MAX_VALUE){
						
					shortestPathsFound[indexOfMinCost] = true;					
					
					for(int j = 0;j < venum;j ++)
						if(!shortestPathsFound[j] &&arcs[indexOfMinCost][j] != Integer.MAX_VALUE 
							&&  minimumCost + arcs[indexOfMinCost][j] < shortestPathsCost[j])
							shortestPathsCost[j] = minimumCost + arcs[indexOfMinCost][j];													
				}else
					unreachableVerticesLeft = true;					
			}
			
			for(int i = 0;i < venum;i ++)
				if(!shortestPathsFound[i])
					shortestPathsCost[i] = -1;
					
			return shortestPathsCost;
		}
		
	}
	
	
}
