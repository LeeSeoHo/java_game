import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import game.JudgeGame;
import game.RSP;

public class Server_f  {
	
	private ServerSocket welcomeSocket;
	private Socket client_1;
	private Socket client_2;
	private DataOutputStream outClient_1;
	private DataOutputStream outClient_2;
	private BufferedReader inClient_1;	
	private BufferedReader inClient_2;
	private static Integer port = 1337;
	private static boolean Rsp = true;
	Check check;
	

	public Server_f(){
		
		check = new Check();
		
		try{			
			welcomeSocket = new ServerSocket(port);
			System.out.println("\n포트번호 " + welcomeSocket.getLocalPort() + " 에 접속하였습니다...");
			
		}catch(IOException ee){
			System.err.println("서버가 이미 열려있습니다.");
			System.exit(1);;
		}	
		
		
	}
	
	
	private static boolean validPort(Integer x) {
		
		return x >= 1 && x <= 65535 ? true : false;
		
	}
	
	
	private static int getPort() {

		Integer input;

		Scanner scan = new Scanner(System.in);

		do {
			System.out.print(" 1 과 65535 사이의 원하시는 포트번호를 입력해주세요 \n");
			System.out.print("기본 포트를 이용하고 싶으면 \"0\" 을 입력해주세요 (" + Server_f.port + "): ");
			input = scan.nextInt();

		} while (input != 0 && !Server_f.validPort(input));

		scan.close();

		return input == 0 ? Server_f.port : input;
	}
	
	public void checkConnect(){

		try {
		//	while (true) {
				// Player one
				client_1 = welcomeSocket.accept();
				
				if (client_1.isConnected()) {
					System.out.println("\n이서호님이 (" + (client_1.getLocalAddress().toString()).substring(1) + ":"
							+ client_1.getLocalPort() + ") 접속하였습니다 ... 심대현님을 기다리는중 ...");
					
					outClient_1 = new DataOutputStream(client_1.getOutputStream());
					inClient_1 = new BufferedReader(new InputStreamReader(client_1.getInputStream()));
					
					outClient_1.writeBytes("Waiting Other Player...\n");
					outClient_1.flush();
					outClient_1.writeBytes("true\n");
					System.out.println("Client1 Wait : true");
					outClient_1.flush();
//					outClient_1.writeBytes("wait..\n");
//					outClient_1.flush();
					System.out.println("Client1 Wait...");
					
				}



				// Player two
				client_2 = welcomeSocket.accept();
				
				if (client_2.isConnected()) {
					System.out.println("심대현님이 (" + (client_2.getLocalAddress().toString()).substring(1) + ":"
							+ client_1.getLocalPort() + ") 접속하였습니다 ... 가위바위보에 따라 공수 결정 ...");
					
					outClient_2 = new DataOutputStream(client_2.getOutputStream());
					inClient_2 = new BufferedReader(new InputStreamReader(client_2.getInputStream()));
					
					
					outClient_2.writeBytes("Matching Other Player !\n");
					outClient_2.flush();
					outClient_2.writeBytes("false\n");
					System.out.println("Client2 Wait : false");
					outClient_2.flush();
					outClient_1.writeBytes("Matching Other Player !\n");
					outClient_1.flush();					

					try{
						Thread.sleep(3000);
					}catch(InterruptedException e){
						
					}
				}

				
		//	}
		} catch (IOException ee) {
			System.err.println(ee);
			System.exit(1);
		}

	}

	public void judgeRsp(){

		int inputClient1;
		int inputClient2;
		int result = 2;
		RSP rsp = new RSP();
		
		
		try {
			
			while(result == 2){
				/*
				System.out.println("임의로 불변수 넘김");
				outClient_1.writeBoolean(true);
				outClient_2.writeBoolean(false);
				System.out.println("넘기기 완료");
				*/
				inputClient1 = inClient_1.read();
				inputClient2 = inClient_2.read();
				
				System.out.println("user 1 : " + inputClient1);
				System.out.println("user 2 : " + inputClient2);
				
				result = rsp.compareRsp(inputClient1, inputClient2);
				
				System.out.println("compare : " + result);
				
				if(result == 1){
					outClient_1.writeBytes("true\n");
					outClient_1.flush();
					System.out.println("Client1 true");
					outClient_2.writeBytes("false\n");
					outClient_2.flush();
					System.out.println("Client2 false");
					Rsp = true;
				}else if(result == 0){
					outClient_1.writeBytes("false\n");
					outClient_1.flush();
					System.out.println("Client1 false");
					outClient_2.writeBytes("true\n");
					outClient_2.flush();
					System.out.println("Client2 true");
					Rsp = false;
				}
				/*
				else{
					outClient_1.writeBytes("draw\n");
					outClient_1.flush();
					outClient_2.writeBytes("draw\n");
					outClient_2.flush();
				}
				*/
							
			}
			
		} catch (IOException ee) {
			System.err.println(ee);
		}

	}
	
	
	public void playGame(){
		
		boolean role1, role2;
		boolean isChange = true;
		int solution;
		String quiz;
		String submit;
		String answer;
		JudgeGame judge = new JudgeGame();
		
		
		try{
			while (isChange) {
				// 클라이언트로부터 공, 수 여부 받음
				role1 = check.checkBool(inClient_1.readLine());
				role2 = check.checkBool(inClient_2.readLine());
				
				System.out.println("role1 : " + role1 + "/ role2 : " + role2);

				if (role1) {
					quiz = inClient_1.readLine();		//answerArea로 문자열 받음
		//			submit = inClient_1.readLine();		//
					
					outClient_1.writeBytes("Accept\n");	//받았다는 응답
					System.out.println("client1 : accept");
					outClient_2.writeBytes("Defend\n");	//
					System.out.println("client2 : defend");
					outClient_1.writeBytes(quiz + "\n");//문제 넘김
					outClient_2.writeBytes(quiz + "\n");//
					
					solution = judge.setSolution(quiz);
					System.out.println("solution : " + solution);
					//여기까진 정상
					answer = inClient_2.readLine();
		//			submit = inClient_2.readLine();
					
					
					outClient_1.writeBytes(answer + "\n");
					outClient_2.writeBytes(answer + "\n");
					System.out.println("answer : " + answer);

					isChange = judge.comAnswer(Integer.parseInt(answer), solution);
					
					System.out.println("isChange1 : " + isChange);
					
				} else {
					quiz = inClient_2.readLine();
					outClient_2.writeBytes("Accept\n");
					outClient_1.writeBytes("Defend\n");	//242줄
					outClient_1.writeBytes(quiz + "\n");
					outClient_2.writeBytes(quiz + "\n");
					
					solution = judge.setSolution(quiz);
					System.out.println("solution : " + solution);
					
					answer = inClient_1.readLine();
					outClient_1.writeBytes(answer + "\n");
					outClient_2.writeBytes(answer + "\n");
					System.out.println("answer : " + answer);
					
					isChange = judge.comAnswer(Integer.parseInt(answer), solution);
					System.out.println("isChange1 : " + isChange);
				}

				outClient_1.writeBytes(check.boolToString(isChange));	//isChange값 보냄
				outClient_2.writeBytes(check.boolToString(isChange));
				
				if (isChange) {
					System.out.println("isChange2 : " + isChange);
					outClient_1.writeBytes("Defend Success ! Change Role !\n");		
					outClient_2.writeBytes("Defend Success ! Change Role !\n");
					outClient_1.writeBytes(check.boolToString(!role1));
					outClient_2.writeBytes(check.boolToString(!role2));
				} else {
					System.out.println("isChange2 : " + isChange);
					outClient_1.writeBytes("Attack Success ! End Game.\n");		
					outClient_2.writeBytes("Attack Success ! End Game.\n");					
				}
			}
		
		}catch(IOException ee){
			
		}
		
	}
	
	
	public void execute(){
		
		
		// Print welcome msg
		//System.out.println(Server_t.welcomeMsg);

		// Set port
		//Server_t.port = Server_t.getPort();

		// Create new server socket & dump out a status msg
		
		checkConnect();
		
		try{	
						
			outClient_1.writeBytes("Start RSP\n");
			outClient_2.writeBytes("Start RSP\n");
								
			// Get client inputs
			System.out.println("Starting RSP");
			judgeRsp();
				
			if(Rsp){
				outClient_1.writeBytes("You Win! Now Starting Attack.\n");
				outClient_2.writeBytes("You Lose ! Now Starting Defend.\n");				
			}else{
				outClient_2.writeBytes("You Win! Now Starting Attack.\n");
				outClient_1.writeBytes("You Lose ! Now Starting Defend.\n");
			}
				
		}catch(IOException ee){
			
			System.err.println(ee);
			
		}		
		
		playGame();
		
		
		try{
						
			client_1.close();
			client_2.close();
			
		}catch(IOException ee){
			
		}
		
	}
	
		
	
	public static void main(String args[]) throws Exception {

		Server_f sc = new Server_f();
		
		sc.execute();	
		

	}
}
