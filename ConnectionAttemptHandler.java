package proj;

import java.net.InetAddress;

import org.jnetpcap.packet.JPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

public class ConnectionAttemptHandler implements Runnable{
  private Ip4 ip = new Ip4();
	private Tcp tcp = new Tcp();
	private String ipAddress;
	private JPacket packet;
	private IpManager ipt = new IpManager();
	//constructor
  	public ConnectionAttemptHandler(JPacket p){
  		packet = p;
	}
  	//thread
  	public void run(){
    	try{
        	if(!packet.hasHeader(ip) || !packet.hasHeader(tcp)) {
                throw new RuntimeException("tcp syn filter is bad");	
            }else if(tcp.flags_SYN() && !tcp.flags_ACK() && !InetAddress.getByAddress(ip.source()).getHostAddress().equals("172.16.1.11")){
            	//set the ip address
            	setIpAddress(InetAddress.getByAddress(ip.source()).getHostAddress());
            	//create a connection attempt
            	ActionQueue.addQueue("c " + ipAddress);
            	ipt.connectionAttempt(getIpAddress(), System.currentTimeMillis());
            }
        }catch(Exception e){
    		e.printStackTrace();
    	}
  	}
  	//return field ip address stored
	public String getIpAddress() {
		return ipAddress;
	}
	//set field ip address
	private void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}	
}

