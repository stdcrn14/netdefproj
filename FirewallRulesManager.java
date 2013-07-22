package proj;

public class FirewallRulesManager implements Runnable{
  private int pCon, pWin;
	
	public void run() {
		System.out.println("FireWallRulesManager Thread Name: " + Thread.currentThread().getName());
		loop();
	}
	
	public void loop(){
		boolean running = true;
		while(running){
			try{
				Thread.sleep(5000);
				System.out.println(Thread.currentThread().getName() + " still running");
				System.out.println(Thread.currentThread().getName() + " checking queue...");
				System.out.println(ActionQueue.getQueue());
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(Thread.currentThread().getName() + " crashing");
				running = false;
			}
		}		
	}
}
