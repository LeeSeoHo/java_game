package game;

import java.util.Scanner;

public class GameUser {

	private boolean role = false;
	private boolean ready = false;
	private int myAnswer;
	private int rsp;
	private String quiz = new String();

	
	Scanner input = new Scanner(System.in);
	
	/*
	public GameUser(boolean role){
		
		setRole(role);
				
	}
*/	
	public void setReady(boolean ready){
		
		this.ready = ready;
	
	}
	
	public boolean getReady(){
		
		return ready;
		
	}
	
	
	public void setRsp(int rsp){
		
		this.rsp = rsp;
		
	}
	
	public int getRsp(){
		
		return this.rsp;

	}
	
	public void setRole(boolean role){
		
		this.role = role;
		
	}
	
	public boolean getRole(){
		
		return this.role;
	}
	
	public void submitQuiz(String userName){
		
		System.out.print( userName + " 문제 입력 : ");
		quiz = input.nextLine();
		
	}

	public void submitAnswer(String userName){
		
		System.out.print(userName + " 해답 입력 : ");
		myAnswer = input.nextInt();		
		
	}
	
	public int getMyAnswer(){
		
		return myAnswer;
		
	}
	public String getMyQuiz(){
		
		return this.quiz;
	}
	
	
}
