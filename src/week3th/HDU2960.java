package week3th;

import java.io.BufferedInputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

/***/
public class HDU2960 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	private static ArrayList<Point> pointsOrderedOnX ;
	private static ArrayList<Point> pointsOrderedOnY;
	private static ArrayList<Integer> horizontalPaths;
	private static ArrayList<Integer> verticalPaths;
	
	private static boolean[] visibleTrees;
	private static HashMap<Integer,Value> workingHMap;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n,m;
		n = input.nextInt();
		m = input.nextInt();
		while(n != 0){//��ʱm != 0
			initiate(n,m);
			coreOperationsX();
			coreOperationsXRev();
			coreOperationsY();
			coreOperationsYRev();
			
			int amountOfVisibleTrees = 0;
			for(int i = 0;i < visibleTrees.length;i ++)
				if(visibleTrees[i])
					amountOfVisibleTrees++;
			System.out.println(amountOfVisibleTrees);
			n = input.nextInt();
			m = input.nextInt();
		}
		input.close();	
	}
	
	private static void initiate(int n,int m){
		pointsOrderedOnX = new ArrayList<Point>(n);		
		
		int x,y;
		for(int i = 0;i < n;i ++){
			x = input.nextInt();
			y = input.nextInt();

			pointsOrderedOnX.add(new Point(x,y)) ;			
		}
		Collections.sort(pointsOrderedOnX);

		for(int i = 1; i <= n;i ++){
			pointsOrderedOnX.get( i - 1).id = i;
		}
		
		//ǳ����
		pointsOrderedOnY = new ArrayList<Point>(pointsOrderedOnX);

		Collections.sort(pointsOrderedOnY, Point.compareY());
		

		horizontalPaths = new ArrayList<Integer>(m / 2);
		verticalPaths = new ArrayList<Integer>(m / 2);
		StringTokenizer path ;

		for(int i = 0;i < m;i ++){
			path = new StringTokenizer(input.next(),"=");
			if(path.nextToken().equals("x"))
				//ע���д����
				//ע����equals���������ǡ�==��
				verticalPaths.add(Integer.parseInt(path.nextToken()));
			else
				horizontalPaths.add(Integer.parseInt(path.nextToken()));				
		}
		Collections.sort(horizontalPaths);
		Collections.sort(verticalPaths);
		
		visibleTrees = new boolean[n];
		for(int i = 0;i < n;i ++)
			visibleTrees[i] = false;
		
		workingHMap = new HashMap<Integer,Value>(n / m * 4); 
	}
	
	private static void collectVisibleTrees(){
		Set<Map.Entry<Integer,Value>> entrySet = workingHMap.entrySet();
		for(Map.Entry<Integer,Value> currentEntry : entrySet){
			Value v = currentEntry.getValue();
			visibleTrees[v.pointID - 1] = true;			
		}
		workingHMap.clear();
	}

	private static void coreOperationsX(){
		
		//i��j�ֱ��ʾ��ǰpoint��pathԪ���ڶ�Ӧ
		//pointsOrderedOnX�Լ�verticalPaths�е��±�
		int i = 0,j = 0;
		Point point = pointsOrderedOnX.get(0);
		Integer path = 0;
		if(!verticalPaths.isEmpty()){
			 path = verticalPaths.get(0);
		}
		
		//ע�⵽���û��·���ǽ�����whileѭ����
		while(i < pointsOrderedOnX.size() && j < verticalPaths.size()){
			if(point.x < path){
				Value v = workingHMap.get(point.y) ;
				if(v == null || path - point.x < v.distanceToPath){
					int distance = path - point.x; 
					workingHMap.put(point.y, new Value(distance,point.id));
				}

				
				if(++ i < pointsOrderedOnX.size())
					point = pointsOrderedOnX.get( i);
			}else{//point.x > path
				//ÿɨ����һ���������򣬱�ǿɼ���
				collectVisibleTrees();
				if(++ j < verticalPaths.size())
					path = verticalPaths.get(j);
			}			
		}
		
		//���ȫ���㶼�������ˣ�����������
		
		//������е�û�б�������˵�����Ƕ������һ����ֱ��·���ұߡ�
		//������ú�����������Ҳ���ش���
		
		//�ռ����һ��ɨ�������Ŀɼ�������Ϊ����û�ռ��������
		collectVisibleTrees();	
	}
	
	private static void coreOperationsXRev(){
		int i =pointsOrderedOnX.size() - 1 ,j = verticalPaths.size() -1;
		Point point = pointsOrderedOnX.get(i);
		Integer path = 0;
		if(!verticalPaths.isEmpty()){
			 path = verticalPaths.get( j);
		}
		
		//ע�⵽���û��·���ǽ�����whileѭ����
		while(i >= 0 && j >= 0){
			if(point.x > path){
				Value v = workingHMap.get(point.y) ;
				if(v == null ||  point.x - path < v.distanceToPath){
					//ע�Ᵽ�־���ʼ�մ���0
					int distance = point.x - path; 
					workingHMap.put(point.y, new Value(distance,point.id));
				}
				
				if(-- i >= 0)
					point = pointsOrderedOnX.get( i);
			}else{//point.x < path
				collectVisibleTrees();
				if(-- j >= 0)
					path = verticalPaths.get(j);
			}			
		}		
		collectVisibleTrees();	
	}
	
	private static void coreOperationsY(){
		int i = 0,j = 0;
		Point point = pointsOrderedOnY.get(0);
		Integer path = 0;
		if(!horizontalPaths.isEmpty()){
			 path = horizontalPaths.get(0);
		}
		
		//ע�⵽���û��·���ǽ�����whileѭ����
		while(i < pointsOrderedOnY.size() && j < horizontalPaths.size()){
			if(point.y < path){
				Value v = workingHMap.get(point.x) ;
				if(v == null || path - point.y < v.distanceToPath){
					int distance = path - point.y; 
					workingHMap.put(point.x, new Value(distance,point.id));
				}
				
				if(++ i < pointsOrderedOnY.size())
					point = pointsOrderedOnY.get( i);
			}else{
				collectVisibleTrees();
				if(++ j < horizontalPaths.size())
					path = horizontalPaths.get(j);
			}			
		}
		collectVisibleTrees();	
	}
	
	private static void coreOperationsYRev(){
		int i =pointsOrderedOnY.size() - 1 ,j = horizontalPaths.size() - 1 ;
		Point point = pointsOrderedOnY.get(i);
		Integer path = 0;
		if(!horizontalPaths.isEmpty()){
			 path = horizontalPaths.get( j);
		}
		
		//ע�⵽���û��·���ǽ�����whileѭ����
		while(i >= 0 && j >= 0){
			if(point.y > path){
				Value v = workingHMap.get(point.x) ;
				if(v == null ||  point.y - path < v.distanceToPath){
					//ע�Ᵽ�־���ʼ�մ���0
					int distance = point.y - path; 
					workingHMap.put(point.x, new Value(distance,point.id));
				}
				
				if(-- i >= 0)
					point = pointsOrderedOnY.get( i);
			}else{//point.x < path
				collectVisibleTrees();
				if(-- j >= 0)
					path = horizontalPaths.get(j);
			}			
		}
		collectVisibleTrees();			
	}
	
	private static class Value{
		private int distanceToPath;

		private int pointID;
		
		public Value(int distance,int pointID){
			distanceToPath = distance;
			this.pointID = pointID;
		}	
	}
	
	private static class Point implements Comparable<Point>{
		private int x,y;
		private int id;
		
		public Point(int x,int y){
			this.x = x;
			this.y = y;
		}
		
		//���������XΪ������бȽ�
		@Override
		public int compareTo(Point p) {
			// TODO Auto-generated method stub
			if(x != p.x)
				return x - p.x;
			else
				return y - p.y;
		}
		
		public static Comparator<Point> compareY(){
			return new CompareY();
		}
		
		 private static class CompareY implements Comparator<Point>{			
			 //���������YΪ������бȽ�
			 @Override
			public int compare(Point p1, Point p2) {
				// TODO Auto-generated method stub
				if(p1.y !=p2.y)
					return p1.y - p2.y;
				else
					return p1.x - p2.x;
			}
			 
		 }
	}
	
}
