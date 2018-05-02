package game;

public class RSP {

	private int rsp = 0;
	private int result;
	final int defeat = 0;
	final int win = 1;
	final int draw = 2;
	final int scissor = 3;
	final int rock = 4;
	final int paper = 5;
	
	public void setRsp(int rsp){
		
		this.rsp = rsp;
		
	}
	
	public int getRsp(){
		
		return this.rsp;
		
	}
	
	public int compareRsp(int rsp, int cRsp) {

		if (rsp == cRsp)
			result = draw;
		else {
			switch (rsp) {
			case rock:
				if (cRsp == scissor)
					result = win;
				if (cRsp == paper)
					result = defeat;
				break;
			case scissor:
				if (cRsp == paper)
					result = win;
				if (cRsp == rock)
					result = defeat;
				break;
			case paper:
				if (cRsp == rock)
					result = win;
				if (cRsp == scissor)
					result = defeat;
				break;
			}
		}
		
		return result;
		
	}
	
	
}
