package week6th;

public class ShareOil1073 extends ShareOil3Bottles {
	private static Bottle b1 = new Bottle(10,10);
	private static Bottle b2 = new Bottle(7,0);
	private static Bottle b3 = new Bottle(3,0);
	
	private ShareOil1073() {
		super(b1, b2, b3);
		// TODO Auto-generated constructor stub
	}

	protected boolean isEnd(){
		return bottles[0].oil == 5;
	}
	
	public static State result(){
		return new ShareOil1073().bfs();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ShareOil test = new ShareOil1073();
		State result = test.bfs();
		while(result != null){
			System.out.println(result);
			result = result.parent; 
		}		
	}
}
