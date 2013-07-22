package proj;

import java.util.ArrayList;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;

public class Capture implements Runnable{
  //thread
    public void run(){
    	System.out.println("Capture Thread Name: " + Thread.currentThread().getName());
    	List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
        StringBuilder errbuf = new StringBuilder(); // For any error msgs  
  
        // Get a list of devices on this system 
        int r = Pcap.findAllDevs(alldevs, errbuf);  
        if(r == Pcap.NOT_OK || alldevs.isEmpty()){
        	System.err.printf("Can't read list of devices, error is %s", errbuf.toString());  
            return;  
        }  

        PcapIf device = alldevs.get(0); // We know we have at least 1 device    

        // Open up the selected device 
        int snaplen = 64 * 1024;           // Capture all packets, no truncation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 1;           			// 10 seconds in milliseconds  
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);  
  
        if(pcap == null){  
            System.err.printf("Error while opening device for capture: " + errbuf.toString());  
            return;  
        }  

        PcapBpfProgram program = new PcapBpfProgram();
        String synExpression = "port 80";
        int optimize = 1;   // 0 = false, 1 = true
        int netmask = 0;

        if(pcap.compile(program, synExpression, optimize, netmask) != Pcap.OK || pcap.setFilter(program) != Pcap.OK){
            throw new RuntimeException("filter init error: " + pcap.getErr());
        }

        JPacketHandler<String> packetHandler = new JPacketHandler<String>(){
          	public void nextPacket(JPacket packet, String user){
          		new Thread(new ConnectionAttemptHandler(packet)).start();
            } 
        };
        
        pcap.loop(Pcap.LOOP_INFINITE, packetHandler, "done");
    }
}
