package summerVacation;

import java.io.IOException;

public class HuffmanCode {
//�շ�������
//ʵ�ֶ�һ���ı��ı��������
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//���Խ����devilCode���ַ����ִ�����2-384֮�䣬��short�������ݴ洢��	
		//String devilFilename = "E:/Eclipse/xmuTasks/supplement/devil.txt"; 
		//String input = Experiment1.getDevilString(devilFilename);
		
		//����
		//����
		String input = "Welcome";
		int[] counts = getCharacterFrequency(input);
		System.out.printf("%-15s%-15s%-15s%-15s\n",
				"ASCII Code","Character","Frequency","Code");
		Tree tree = getHuffmanTree(counts);
		String[] codes = getCode(tree.root);
		
		//��ʾ���
		for(int i = 0;i < codes.length;i ++)
			if(counts[i] != 0 ){
				if(i != 10 && i != 13)
					System.out.printf("%-15d%-15c%-15d%-15s\n",
						i,(char)i,counts[i],codes[i]);
				else 
					System.out.printf("%-15d%-15c%-15d%-15s\n",
							i,' ' ,counts[i],codes[i]);
			}
		
		//����
		StringBuilder binaryCodes = new StringBuilder();
		for(int i = 0;i < input.length();i ++){
			int charASCII = (int)input.charAt(i);
			binaryCodes.append(codes[charASCII]);
		}
		//���Ժϸ�
		//System.out.println("Binary Codes: " + binaryCodes.toString());
		
		char[] decodedText = 
				searchHuffmanTree(binaryCodes.toString().toCharArray(),tree);
		
		//��ʾ������
		for(int i = 0; i < decodedText.length;i ++)
			System.out.print(decodedText[i]);
			
	}
	
	/**���ݶ����Ʊ��룬�ڸ����շ������ϲ�����Ӧ��㣬
	 * ���ؽ����������ַ���ע��շ�����Ӧ�����ƥ�䡣
	 * @param binaryCodes ��������ʽ�洢���ַ����룬����ֻ���ɡ�0������1�����
	 * @param huffmanTree �ڱ���/������Ҫ�õ��ĺշ�����
	 * @return �������ַ�����˳��洢�������С�
	 * */
	public static char[] searchHuffmanTree(char[] binaryCodes,Tree huffmanTree){
		Tree.Node root = huffmanTree.root;
		char[] decodedChars = new char[ root.weight];
		
		//�ʼ����binaryCodes��0λ�ÿ�ʼ���ұ���
		int offset = 0;
		for(int i = 0;i < decodedChars.length;i ++){
			//ÿ�ο�ʼ����һ���ַ�,����index����0��ʼ
			Tree.Node tn = searchHuffmanTree(binaryCodes,root,offset,0);
			decodedChars[i] = tn.element;
			
			//�Ѿ�����ı��벻��ʹ�ã������ƫ��
			offset += tn.code.length();
		}
		
		return decodedChars;
	}
	/**<p>�����ݹ麯�������ڶԺշ�����������</p>
	 * ���ݶ����Ʊ��룬��ĳ����㿪ʼ���ڸ����շ������ϲ�����ӦҶ��㡣
	 * ����ҵ����������Ҷ��㣬���򷵻ؿա�
	 * @param binaryCodes ��������ʽ�洢���ַ����룬����ֻ���ɡ�0������1�����
	 * @param root �շ������ĸ��ڵ�
	 * @param offset ָʾ���ұ�������
	 * @param index �ݹ�����в��ұ����ָ��
	 * 
	 * */
	public static Tree.Node searchHuffmanTree(char[] binaryCodes,Tree.Node root,
			int offset,int index){
		
		if(offset + index < binaryCodes.length){
			if(root.left != null && binaryCodes[offset + index] == '0')
				//��root.Left != null����ʾroot�ڵ��к��ӣ������Һ��ӣ�
				//root���� binaryCodes[offset + index]��ָʾѡ��·��
				//�ԱȽ���һ���ַ�
				return searchHuffmanTree(binaryCodes,root.left,offset,index + 1);
			else if(root.left != null && binaryCodes[offset + index] == '1')
				return searchHuffmanTree(binaryCodes,root.right,offset,index + 1);
			else
				//����Ҷ�ڵ㣬˵��ƥ����룬���ظý��
				return root;
		}else if(offset + index == binaryCodes.length)
			//��ʱ�Ѿ��Ƚ����
			//˵��binaryCodes[offset...length -1]������ĳ���ַ��ı���
			//length��binaryCodes�ĳ���
			return root;
		else
			//binaryCodes[offset...length -1]��α���
			//������úշ������ı��룺ֻ��ĳ���ַ������һ����
			return null;
			
		
		
		
	}
	
	/**��д����ȡҶ�ӽ���Ӧ���ַ����룬�ú���
	 * Ӧ�ںշ������������������ã������㽫û��
	 * ������Ϣ��
	 * @param root �շ����������
	 * @return 256���ַ��ĺշ������룬���ĳ����㲻�����У�
	 * ��Ӧ��ŵ�ֵΪ��
	 * */
	public static String[] getCode(Tree.Node root){
		if(root == null) return null;
		String[] codes = new String[256];
		assignCode(root,codes);
		return codes;
	}
	
	/**<p>�ݹ麯��</p>
	 * ��Ҷ���д�����ĵݹ麯����������������
	 * ��Ϣ����codes*/
	private static void assignCode(Tree.Node root ,String[] codes) {
		if(root.left != null){
			//��root����к��ӽ�㣨�����Һ��ӣ�ʱ
			//ǰ�����
			
			root.left.code = root.code + '0';
			assignCode(root.left,codes);
			
			root.right.code = root.code + '1';
			assignCode(root.right,codes);
		}else{
			//ֻ��Ҷ���ı�����Ϣ��Ч
			codes[(int)root.element] = root.code;			
		}		
	}
	
	/**�Ը��ַ���ASCII�����ֵĴ���Ϊ���ݽ����շ�����
	 * @param counts ��¼���ַ����ִ�����
	 * ��ͨ��getCharacterFrequency(String text)�������
	 * @return �����ĺշ�����*/
	public static Tree getHuffmanTree(int[] counts){
		//�������ö����򣬴�����Եúܼ��
		Heap<Tree> heap = new Heap<Tree>();
		for(int i = 0;i < counts.length;i ++){
			if(counts[i] > 0)
				//�ѵĳ�ʼ��
				heap.add(new Tree(counts[i],(char)i));		
		}
		
		//�շ����㷨
		while(heap.getSize() > 1){
			Tree t1 = heap.remove();
			Tree t2 = heap.remove();
			heap.add(new Tree(t1,t2));
		}
		
		//������ֻ��һ����ʱ������
		return heap.remove();
	}
	
	/**��ȡһ���ַ������ַ����ֵ�Ƶ��
	 * @param text �������ַ���
	 * @return ��Ƶ
	 * */
	public static int[] getCharacterFrequency(String text){
		int[] counts = new int[256];
		for(int i = 0; i < text.length();i ++){
			counts[(int)text.charAt(i)] ++;
		}				
		return counts;
	}
	
	/**�շ�������ʵ����Comparable�ӿڣ�����
	 * �Ϳ����öѶ������������
	 * �á�static��������Ϊ�˷����ھ�̬������ʹ��
	 * */
	public static class Tree implements Comparable<Tree>{
		//��ʽ�ṹ�洢�շ�����
		Node root;
		
		//���캯��
		//ʵ�������շ������ĺϲ�
		public Tree(Tree t1,Tree t2){
			root = new Tree.Node();		
			root.left = t1.root;
			root.right = t2.root;
			root.weight = t1.root.weight + t2.root.weight;
		}
		
		//���캯��������һ�����ڵ���
		public Tree(int weight,char element){
			root = new Node(weight,element);
		}
		
		@Override
		public int compareTo(Tree o) {
			// TODO Auto-generated method stub
			if(root.weight < o.root.weight)
				//������ʱ����������С����
				return 1;
			else if(root.weight == o.root.weight)
				return 0;
			else
				return -1;
		}
		
		public  class Node{
			//����������̬����
			
			//֮���Բ��ӡ�static��������Ϊ
			//û��Ҫ��������ڲ������ʹ����ʽ
			//����Ҳ�ǿ��Եġ�

			
			//�ַ�ֵ��Ȩ���ڶѳ�ʼ��ʱ��ֵ
			char element;
			int weight;//Ȩֵ�ںϲ�ʱ�޸�
			
			//���ӽ�㣬�����ϲ�ʱ��ֵ
			Node left;
			Node right;
			
			//���룬�ڵ���getCode������ֵ
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
