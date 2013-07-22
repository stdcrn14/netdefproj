package proj;

import java.util.List;
import java.util.ArrayList;

public class ActionQueue {
  private static List<String> q;
	//constrcutor
	public ActionQueue(){
		q = new ArrayList<String>();
	}
	//add a command to the queue
	public static synchronized void addQueue(String str){
		q.add(str);
	}
	//get whatever is at index n in queue
	public static synchronized String getQueue(int n){
		return q.get(n);
	}
	//print out what is in the queue
	public static synchronized String getQueue(){
		String str = "";
		for(String s : q){
			str += s + "\n";
		}
		return str;
	}
}
