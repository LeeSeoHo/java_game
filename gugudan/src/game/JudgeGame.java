package game;


import java.util.Scanner;




public class JudgeGame {

	private int solution = 0;
	private boolean change = true;
	Scanner input = new Scanner(System.in);
	
		
	public int setSolution(String quiz){
		
		String str = new String();
		int a = 0, b = 0;
		
		if(quiz.length() < 4){
			for (int i = 0; i < quiz.length(); i++) {
				if (quiz.charAt(i) >= 49 && quiz.charAt(i) <= 57) {
					str += quiz.charAt(i);
				}
			}

			a = (int) str.charAt(0) - 48;
			b = (int) str.charAt(1) - 48;
		}
		
		this.solution = a*b;
		
		
		return a*b;
		
	}

	
	public void changeRole(GameUser user1){
		
		user1.setRole(!user1.getRole());
		System.out.println("user1 : " + user1.getRole());
		
	}
	
	
	public boolean comAnswer(int answer, int solution){
				
		boolean result;
		
		if(solution == answer){
			result = true;
		}else{
		//	GameServer.isChange = false;
			result = false;
		}

		
		return result;
		
	}

	
}
