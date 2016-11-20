package week6th;

import summerVacation.LinkedQueue;
import summerVacation.MyLinkedList;

public abstract class ShareOil {
	protected Bottle[] bottles;
	private LinkedQueue<State> stateQueue = new LinkedQueue<State>();
	private MyLinkedList<State> visitedStateList = new MyLinkedList<State>();
	private State originalState;
	private State targetState;
	
	protected ShareOil(Bottle[] bottles){
		this.bottles = bottles;
		this.originalState = new State();
	}

	protected ShareOil(Bottle[] bottles,State targetState){
		this(bottles);
		this.targetState = targetState;
	}
			
	public State bfs(){
		stateQueue.clear();
		visitedStateList.clear();
		stateQueue.enqueue(originalState);
		int cases = bottles.length * (bottles.length - 1);
		while(!stateQueue.isEmpty()){
			State pState  = stateQueue.dequeue();
			//加入“已访问状态”列表
			visitedStateList.add(pState);

			changeCurrentState(pState);
			if(isEnd())
				return pState;
			
			State subState;
			for(int i = 1; i <= cases;i ++)
				if((subState = pourOil(i,pState)) != null && !visitedStateList.contains(subState)){
					stateQueue.enqueue(subState);														
			}	
		}
		
		return null;
	}

	protected boolean isEnd(){
		return getCurrentState().equals(targetState);
	}
	
	protected State getCurrentState(){
		return new State();
	}
	
	private void changeCurrentState(State newState){
		for(int i = 0;i < bottles.length;i ++)
			bottles[i].oil = newState.state[i];
	}
	
	public class State{
		private int[] state;
		public State parent;
		
		public State(){
			state = new int[bottles.length];
			for(int i = 0;i < state.length;i ++)
				state[i] = bottles[i].oil;			
		}
		
		public State(State paren){
			this();
			setParent(parent);
		}
		
		public State(int[] state,State parent){
			this.state = state;
			this.parent = parent;
		}
		
		public void setState(int index,int value){
			state[index] = value;
		}
		
		public void setParent(State parent){
			this.parent = parent;
		}			
		
		public boolean equals(Object o){
			State s2 = (State)o;
			for(int i = 0;i < state.length;i ++)
				if(state[i] != s2.state[i])
					return false;
			return true;
		}
		
		public String toString(){
			StringBuilder builder = new StringBuilder();
			builder.append("[ ");
			for(int i = 0;i < state.length - 1;i ++)
				builder.append(state[i] +",");
			builder.append(state[state.length - 1] +" ]");
			return builder.toString();
		}
	}
	
	protected abstract State pourOil(int n,State parentState);
	
	protected State pourOil(int i1,int i2,State parentState){
		if(!Bottle.isSensibleToPour(bottles[i1],bottles[i2]))
			return null;
		State newState = getCurrentState();
		int n = bottles[i1].oil <= bottles[i2].needForFull() 
				? bottles[i1].oil: bottles[i2].needForFull();
		newState.setState(i1, bottles[i1].oil - n);
		newState.setState(i2, bottles[i2].oil + n);
		newState.setParent(parentState);
		return newState;
	}
	
	public static class Bottle{
		int volumn;
		int oil;
		
		public Bottle(int volumn,int oil){
			this.volumn = volumn;
			this.oil = oil;
		}
		
		public boolean isEmpty(){
			return oil == 0;
		}
		
		public boolean isFull(){
			return oil == volumn;
		}
		
		public int needForFull(){
			return volumn - oil;
		}
		
		public static boolean isSensibleToPour(Bottle b1,Bottle b2){
			return !b1.isEmpty() && !b2.isFull(); 			
		}		
	}
	
}