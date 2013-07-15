package proj;

import java.util.ConcurrentModificationException;

public class PolicyManager implements Runnable{
  private int maxWindow; //time period between connections in seconds
	private int maxConnections; //max connection attempts in maxWindow time period
	private double cpuPercentLoad; //% cpu load from 'wmic cpu get loadpercentage' command
	private int currentTcpConnections; //in case of implementation of alternative to cpu % load as factor
	
	//constructor
	public PolicyManager(){
		updatePolicy();
	}
	//get time period allowed between n connections
	public int getWindow() {
		return maxWindow;
	}
	//set the time period allowed between n connections
	private void setWindow(double win) {
		maxWindow = (int)win;
	}
	//get the max number of connections attempts in x window size
	public int getConnections() {
		return maxConnections;
	}
	//set the max number of connection attempts in x window size
	private void setConnections(double conn) {
		maxConnections = (int)conn;
	}
	//get cpu field
	private double getCpu() {
		return cpuPercentLoad;
	}
	//set cpu field
	private void setCpu(double cpu) {
		cpuPercentLoad = cpu;
	}
	//update policy and either tighten or loosen the policy
	private void updatePolicy(){
		setCpu(new Wmic().getCpuLoadPercentage());
		setWindow(Math.pow(getCpu(), 1.25)); //time window
		setConnections(150*Math.pow(.95, getCpu()) + 1); //connections		
	}
	//thread
	public void run(){
		loop();
	}
	//main thread loop
	public void loop(){
		boolean running = true;
		//initialize some values to find
		ActionQueue.addQueue("p " + getConnections() + "," + getWindow());
		while(running){
			try{
				System.out.println("entering try block");
				Thread.sleep(5000);
				ActionQueue.addQueue("p " + getConnections() + "," + getWindow());
				System.out.println(Thread.currentThread().getName() + " running");
			}catch(ConcurrentModificationException e){
				e.printStackTrace();
				System.out.println(Thread.currentThread().getName() + " crashing");
				running = false;
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(Thread.currentThread().getName() + " crashing");
				running = false;
			}
		}		
	}
}
