package barrier;

import java.util.concurrent.Semaphore;

public class SemaphoreBarrier2 extends Thread{
	private static Semaphore doorIn=new Semaphore(1);
	private static Semaphore doorOut=new Semaphore(0);
	private static int cnt=0;
    private static int N=5;
    
    private static int posId=0;
    private int id=++posId;
	public void run() {
		
	try {
		while(!interrupted()) {
			
			 sleep((long)(Math.random()*1000));
			 doorIn.acquire();
			 cnt++;
			 if (cnt<N) {
			doorIn.release();
			  System.out.println("Process "+id+" waits on barrier");
		     }
		    else doorOut.release();
		
	     	doorOut.acquire();
		    cnt--;
		
	    	if (cnt==0) {
		    	doorIn.release();
		     }
		    else doorOut.release();
			  System.out.println("Process "+id+" goes through barrier");
		
		
	}
	}
	catch(InterruptedException e) { }
}
	public static void main(String[] args) {
		SemaphoreBarrier2[] processes=new SemaphoreBarrier2[N];
		for (int i=0;i<N;i++) {
			processes[i]=new SemaphoreBarrier2();
			processes[i].start();
		}
		 try {
			sleep((long)(Math.random()*10000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 for (int i=0;i<N;i++) {
				processes[i].interrupt();
			}
		 
		 
	}
}
