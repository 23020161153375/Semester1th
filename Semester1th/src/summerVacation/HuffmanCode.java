package summerVacation;

import java.io.IOException;

public class HuffmanCode {
//赫夫曼编码
//实现对一段文本的编码和译码
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//测试结果：devilCode中字符出现次数在2-384之间，用short类型数据存储。	
		//String devilFilename = "E:/Eclipse/xmuTasks/supplement/devil.txt"; 
		//String input = Experiment1.getDevilString(devilFilename);
		
		//测试
		//编码
		String input = "Welcome";
		int[] counts = getCharacterFrequency(input);
		System.out.printf("%-15s%-15s%-15s%-15s\n",
				"ASCII Code","Character","Frequency","Code");
		Tree tree = getHuffmanTree(counts);
		String[] codes = getCode(tree.root);
		
		//显示结果
		for(int i = 0;i < codes.length;i ++)
			if(counts[i] != 0 ){
				if(i != 10 && i != 13)
					System.out.printf("%-15d%-15c%-15d%-15s\n",
						i,(char)i,counts[i],codes[i]);
				else 
					System.out.printf("%-15d%-15c%-15d%-15s\n",
							i,' ' ,counts[i],codes[i]);
			}
		
		//译码
		StringBuilder binaryCodes = new StringBuilder();
		for(int i = 0;i < input.length();i ++){
			int charASCII = (int)input.charAt(i);
			binaryCodes.append(codes[charASCII]);
		}
		//测试合格
		//System.out.println("Binary Codes: " + binaryCodes.toString());
		
		char[] decodedText = 
				searchHuffmanTree(binaryCodes.toString().toCharArray(),tree);
		
		//显示译码结果
		for(int i = 0; i < decodedText.length;i ++)
			System.out.print(decodedText[i]);
			
	}
	
	/**根据二进制编码，在给定赫夫曼树上查找相应结点，
	 * 返回解码后的所有字符。注意赫夫曼树应与编码匹配。
	 * @param binaryCodes 以数组形式存储的字符编码，编码只能由“0”、“1”组成
	 * @param huffmanTree 在编码/解码需要用到的赫夫曼树
	 * @return 解码后的字符，按顺序存储在数组中。
	 * */
	public static char[] searchHuffmanTree(char[] binaryCodes,Tree huffmanTree){
		Tree.Node root = huffmanTree.root;
		char[] decodedChars = new char[ root.weight];
		
		//最开始，从binaryCodes的0位置开始查找编码
		int offset = 0;
		for(int i = 0;i < decodedChars.length;i ++){
			//每次开始解码一个字符,参数index都从0开始
			Tree.Node tn = searchHuffmanTree(binaryCodes,root,offset,0);
			decodedChars[i] = tn.element;
			
			//已经解出的编码不再使用，将起点偏移
			offset += tn.code.length();
		}
		
		return decodedChars;
	}
	/**<p>辅助递归函数，用于对赫夫曼编码译码</p>
	 * 根据二进制编码，从某个起点开始，在给定赫夫曼树上查找相应叶结点。
	 * 如果找到，返回这个叶结点，否则返回空。
	 * @param binaryCodes 以数组形式存储的字符编码，编码只能由“0”、“1”组成
	 * @param root 赫夫曼树的根节点
	 * @param offset 指示查找编码的起点
	 * @param index 递归过程中查找编码的指针
	 * 
	 * */
	public static Tree.Node searchHuffmanTree(char[] binaryCodes,Tree.Node root,
			int offset,int index){
		
		if(offset + index < binaryCodes.length){
			if(root.left != null && binaryCodes[offset + index] == '0')
				//“root.Left != null”表示root节点有孩子（包括右孩子）
				//root根据 binaryCodes[offset + index]的指示选择路径
				//以比较下一个字符
				return searchHuffmanTree(binaryCodes,root.left,offset,index + 1);
			else if(root.left != null && binaryCodes[offset + index] == '1')
				return searchHuffmanTree(binaryCodes,root.right,offset,index + 1);
			else
				//到了叶节点，说明匹配编码，返回该结点
				return root;
		}else if(offset + index == binaryCodes.length)
			//此时已经比较完毕
			//说明binaryCodes[offset...length -1]正好是某个字符的编码
			//length是binaryCodes的长度
			return root;
		else
			//binaryCodes[offset...length -1]这段编码
			//不是这棵赫夫曼树的编码：只是某个字符编码的一部分
			return null;
			
		
		
		
	}
	
	/**编写、获取叶子结点对应的字符编码，该函数
	 * 应在赫夫曼树建立用立即调用，否则结点将没有
	 * 编码信息。
	 * @param root 赫夫曼树根结点
	 * @return 256个字符的赫夫曼编码，如果某个结点不在其中，
	 * 对应编号的值为空
	 * */
	public static String[] getCode(Tree.Node root){
		if(root == null) return null;
		String[] codes = new String[256];
		assignCode(root,codes);
		return codes;
	}
	
	/**<p>递归函数</p>
	 * 给叶结点写入编码的递归函数，并将整个编码
	 * 信息存入codes*/
	private static void assignCode(Tree.Node root ,String[] codes) {
		if(root.left != null){
			//当root结点有孩子结点（包括右孩子）时
			//前序遍历
			
			root.left.code = root.code + '0';
			assignCode(root.left,codes);
			
			root.right.code = root.code + '1';
			assignCode(root.right,codes);
		}else{
			//只有叶结点的编码信息有效
			codes[(int)root.element] = root.code;			
		}		
	}
	
	/**以各字符（ASCII）出现的次数为依据建立赫夫曼树
	 * @param counts 记录各字符出现次数，
	 * 可通过getCharacterFrequency(String text)函数获得
	 * @return 建立的赫夫曼树*/
	public static Tree getHuffmanTree(int[] counts){
		//这里利用堆排序，代码会显得很简洁
		Heap<Tree> heap = new Heap<Tree>();
		for(int i = 0;i < counts.length;i ++){
			if(counts[i] > 0)
				//堆的初始化
				heap.add(new Tree(counts[i],(char)i));		
		}
		
		//赫夫曼算法
		while(heap.getSize() > 1){
			Tree t1 = heap.remove();
			Tree t2 = heap.remove();
			heap.add(new Tree(t1,t2));
		}
		
		//当堆中只有一棵树时，返回
		return heap.remove();
	}
	
	/**获取一个字符串各字符出现的频率
	 * @param text 待检测的字符串
	 * @return 字频
	 * */
	public static int[] getCharacterFrequency(String text){
		int[] counts = new int[256];
		for(int i = 0; i < text.length();i ++){
			counts[(int)text.charAt(i)] ++;
		}				
		return counts;
	}
	
	/**赫夫曼树，实现了Comparable接口，这样
	 * 就可以用堆对其进行排序了
	 * 用“static”修饰是为了方便在静态函数中使用
	 * */
	public static class Tree implements Comparable<Tree>{
		//链式结构存储赫夫曼树
		Node root;
		
		//构造函数
		//实现两个赫夫曼树的合并
		public Tree(Tree t1,Tree t2){
			root = new Tree.Node();		
			root.left = t1.root;
			root.right = t2.root;
			root.weight = t1.root.weight + t2.root.weight;
		}
		
		//构造函数，创建一个单节点树
		public Tree(int weight,char element){
			root = new Node(weight,element);
		}
		
		@Override
		public int compareTo(Tree o) {
			// TODO Auto-generated method stub
			if(root.weight < o.root.weight)
				//堆排序时，可以生成小顶堆
				return 1;
			else if(root.weight == o.root.weight)
				return 0;
			else
				return -1;
		}
		
		public  class Node{
			//不能声明静态方法
			
			//之所以不加“static”，是因为
			//没必要，这便是内部类基本使用形式
			//加上也是可以的。

			
			//字符值和权，在堆初始化时赋值
			char element;
			int weight;//权值在合并时修改
			
			//孩子结点，在树合并时赋值
			Node left;
			Node right;
			
			//编码，在调用getCode函数后赋值
			String code = "";
			
			public Node(){			
			}
			
			public Node(int weight,char element){
				this.weight = weight;
				this.element = element;
			}
		}	
	}
	
}
