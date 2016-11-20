package week6th;

public class ShareOil3Bottles extends ShareOil {

	public ShareOil3Bottles(Bottle b1,Bottle b2,Bottle b3, State targetState) {
		super(new Bottle[]{b1,b2,b3}, targetState);
		// TODO Auto-generated constructor stub		
	}

	protected ShareOil3Bottles(Bottle b1,Bottle b2,Bottle b3) {
		super(new Bottle[]{b1,b2,b3});
		// TODO Auto-generated constructor stub		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected State pourOil(int n,State parentState) {
		// TODO Auto-generated method stub
		State result = null;
		switch(n){
		//ÖØÐÂ¸³Öµ
			case 1: result = pourOil(0,1,parentState);
				break;
			case 2: result = pourOil(0,2,parentState);
				break;
			case 3: result = pourOil(1,0,parentState);
				break;
			case 4: result = pourOil(1,2,parentState);
				break;
			case 5: result = pourOil(2,0,parentState);
				break;
			case 6: result = pourOil(2,1,parentState);
				break;
			default:		
		}		
		return result;
	}

}
