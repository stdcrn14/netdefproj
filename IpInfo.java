package proj;

public class IpInfo {
  /*
	 * FORMAT IS:
	 * ipAddress,connectionAttempts,firstConnection,lastConnection
	 */
	private String data;
	private String ipAddress;
	private int connectionAttempts;
	private long firstConnection;
	private long lastConnection;
	private int violations;
	//constructor for ip address given ip only
	public IpInfo(String str){
		ipAddress = str;
		connectionAttempts = 0;
		firstConnection = System.currentTimeMillis();
		lastConnection = System.currentTimeMillis();
		violations = 0;
		update();
		generateFields();
	}
	//updates the ipInfo field
	private void update(){
		data = ipAddress + "," + connectionAttempts + "," + firstConnection + "," + lastConnection + "," + violations;
	}
	//gets the ip address
	public String getIpAddress() {
		return ipAddress;
	}
	//set ip address
	private void setIpAddress(String str) {
		ipAddress = str;
	}
	//get connection attempts
	public int getConnectionAttempts() {
		return connectionAttempts;
	}
	//set connection attempts to n
	private void setConnectionAttempts(int n) {
		connectionAttempts = n;
	}
	//resets connections to null
	public void clearConnectionTimes(){
		setFirstConnection(System.currentTimeMillis());
		setLastConnection(System.currentTimeMillis());
		update();
		generateFields();
	}
	//increments connection attempts
	public void addConnectionAttempts(long time){
		try{
			setLastConnection(time);
		}catch(Exception e){
			e.printStackTrace();
		}
		connectionAttempts++;
		update();
	}
	//decrements connection attempts
	public void clearConnectionAttempts(){
		connectionAttempts = 0;
		setFirstConnection(getLastConnection());
	}
	//store field data from string
	private void generateFields(){
		String[] fieldList = data.split(",");
		try{
			setIpAddress(fieldList[0]);
			setConnectionAttempts(Integer.parseInt(fieldList[1]));
			setFirstConnection(Long.parseLong(fieldList[2]));
			setLastConnection(Long.parseLong(fieldList[3]));
			setViolations(Integer.parseInt(fieldList[4]));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		
		}
	}
	//compare two ipinfo (ip address only)
	public boolean is(String ip1){
		if(getIpAddress().equals(ip1)){
			return true;
		}else{
			return false;
		}
	}
	//returns ipinfo as a string - used with String.split()
	public String toString(){
		update();
		return data;
	}
	//gets the date of the first connection
	public long getFirstConnection() {
		return firstConnection;
	}
	//sets the first connection date
	public void setFirstConnection(long d) {
		firstConnection = d;
		update();
	}
	//gets the date of the last connection
	public long getLastConnection() {
		return lastConnection;
	}
	//sets the last connection date
	public void setLastConnection(long d) {
		lastConnection = d;
		update();
	}
	//return the time between first and last connection (in milliseconds)
	public long getTimeWindow(){
		try{
			return (getLastConnection() - getFirstConnection());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	//get the violation number
	public int getViolations() {
		return violations;
	}
	//add the violation number
	public void addViolations() {
		violations++;
	}
	public void setViolations(int v){
		violations = v;
	}
	//reset connections
	public void resetConnections(){
		setFirstConnection(System.currentTimeMillis());
		setLastConnection(System.currentTimeMillis());
		setConnectionAttempts(0);
	}
	
}
