package proj;

public class Driver{
  public static void main(String[] args) throws Exception{
		//@SuppressWarnings("unused")
		ActionQueue queue = new ActionQueue();
		Thread policy = new Thread(new PolicyManager(), "policy");
		Thread frm = new Thread(new FirewallRulesManager(), "firewall");
		Thread cap = new Thread(new Capture(), "capture");
		System.out.print("starting Capture...");
		cap.start();
		System.out.println("...capture started.");
		System.out.print("starting Policy...");
		policy.start();
		System.out.println("...policy started.");
		System.out.print("starting FireWallRulesManager...");
		frm.start();
		System.out.println("...firewalrulesmanager started.");
	}
}
