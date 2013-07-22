package proj;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FirewallRulesCreator{
  private String cmd;	
	//constructor
	public FirewallRulesCreator(){
	}
	//create netsh rule to block an ip
	public void blockIp(String ip){
		cmd = "netsh advfirewall firewall " +
		"add " +
		"rule name = " + ip + " " +
		"dir = in " +
		"action = block " +
		"enable = yes " +
		"profile = any " +
		"remoteip = " + ip + " " +
		"protocol = any " +
		"interface = any " +
		"";
		
		execute();
	}
	//delete a netsh rule that blocked an ip
	public void unblockIp(String ip){
		cmd = "netsh advfirewall firewall " +
		"delete " +
		"rule name = " + ip + 
		"";
		
		execute();
	}
	//execute command line
	private void execute(){
		try{
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmd);
			//String line = "";
			
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while((input.readLine()) != null){
				//System.out.println(line);
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
