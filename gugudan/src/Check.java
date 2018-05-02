
public class Check {

	
	
	public int checkInt(String str){
		
		int value;
		
		value = Integer.parseInt(str);
		
		return value;
		
	}
	
	public boolean checkBool(String str){
		
		boolean result = false;
		
		if(str.equals("true"))
			result = true;
		else if(str.equals("false"))
			result = false;
				
		return result;
	}
	
	public String boolToString(boolean bool){
		
		String result = "";
		
		if(bool)
			result = "true\n";
		else
			result = "false\n";
			
		return result;
		
	}
	
	public String intToString(int value){
		
		String result = "";
		
		result = Integer.toString(value);
		
		return result;
		
	}
	
	
}
