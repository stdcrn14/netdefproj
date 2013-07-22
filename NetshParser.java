package proj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class NetshParser {
  //store parsed data
	private List<String> data = new ArrayList<String>();
	private String cmd;
	//constructor
	public NetshParser(){
		cmd = "netsh advfirewall firewall show rule name = all dir = in";
		update();
	}
	//update List<String> data
	public void update(){
		execute();
		filter();
		clearSpaces();
	}
	//filter only lines that start with str (i.e. Rule Name:)
	public void filter(){
		for(int row = 0; row < data.size(); row++){
			if(!data.get(row).contains("Rule Name:")){
				data.remove(row);
				row--;
			}else{
				data.set(row, data.get(row).replaceAll("Rule Name:", ""));
			}
		}
	}
	//delete the Rule Name: and spaces
	public void clearSpaces(){
		for(int row = 0; row < data.size(); row++){
			data.set(row, data.get(row).replaceAll("\\s+", ""));
		}	
	}
	//print
	public void println(){
		for(String str : data){
			System.out.println(str);
		}
	}
	//returns the list of rules
	public List<String> toList(){
		return data;
	}
	//check if ip address exists in generated list
	public boolean exists(String ip){
		for(String str : data){
			if(str.equals(ip)){
				return true;
			}
		}
		return false;
	}
	//execute netsh command
	public void execute(){
		try{
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmd);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while((input.readLine()) != null){
				data.add(input.readLine());
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
