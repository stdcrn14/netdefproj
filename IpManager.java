package proj;

import java.util.List;
import java.util.ArrayList;

public class IpManager {
  private List<IpInfo> data = null;
	public IpManager(){
		data = new ArrayList<IpInfo>();
	}
	//does ipAddress1 (IpInfo) exist in data (List<IpInfo>)
	public boolean exists(String ipAddress1){
		for(IpInfo ipAddress2 : data){
			if(ipAddress2.is(ipAddress1)){
				return true;
			}
		}
		return false;
	}		
	//print out data (List<IpInfo>) line by line
	public void println(){
		for(IpInfo ip : data){
			System.out.println(ip.toString());
		}
	}
	//get the row that ipAddress (String) is at in data (List<IpInfo>)
	public int getRow(String ipAddress){
		for(int row = 0; row < data.size(); row++){
			if(new IpInfo(ipAddress).is(data.get(row).getIpAddress())){
				return row;
			}
		}
		return 0;
	}
	//attempt connection from ipAddress (String)
	public void connectionAttempt(String ipAddress, long time){
		if(!exists(ipAddress)){
			IpInfo element = new IpInfo(ipAddress);
			element.addConnectionAttempts(time);
			data.add(element);
		}else{
			IpInfo element = data.get(getRow(ipAddress));
			element.addConnectionAttempts(time);
			data.set(getRow(ipAddress), element);
		}
	}
	//get List<IpInfo> data size
	public int getSize(){
		return data.size();
	}
	//get an element at some index from List<IpInfo> 
	public IpInfo getElement(int index){
		if(index < getSize() && index >= 0){
			return data.get(index);
		}else{
			return null;
		}
	}
}
