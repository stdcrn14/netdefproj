package proj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class Wmic {
  //store parsed data
	private List<String> data;
	private double cpu;
	private String cmd;
	//constructor
	public Wmic(){
		data = new ArrayList<String>();
		cmd = "wmic cpu get loadpercentage";
		execute();
		cleanData();
	}
	//clean output
	private void cleanData(){
		//isolate %
		for(int i = 0; i < data.size(); i++){
			if(data.get(i) == null || data.get(i).equals("") || data.get(i).equals("LoadPercentage  ")){
				data.remove(i);
				i--;
			}
		}
		//replace spaces
		for(String s : data){
			s.replaceAll(" ","");
		}
		
		try{
			cpu = Double.parseDouble(data.get(0));
		}catch(NumberFormatException e){
			cpu = 0;
		}
	}
	public double getCpuLoadPercentage(){
		return cpu;
	}
	//execute netsh command
	private void execute(){
		try{
			Process pr = Runtime.getRuntime().exec(cmd);
			String str = "";
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while((str = input.readLine()) != null){
				data.add(str);
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
