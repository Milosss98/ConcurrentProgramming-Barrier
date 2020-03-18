package barrier;

import java.util.concurrent.Semaphore;

public class SemaphoreBarrier extends Thread{
	private static int N=5;
	private static Semaphore mutex=new Semaphore(1);
	private static Semaphore barrier=new Semaphore(0),ok=new Semaphore(0);
	private static int cnt=0;
	
	 private static int posId=0;
	 private int id=++posId;
	public void run() {
		try {
		while (!interrupted()) {
			 sleep((long)(Math.random()*1000));
			 mutex.acquire();
			 cnt++;
			 if (cnt==N) {
				 cnt=0;
				 for (int i=0;i<N-1;i++) {
					 barrier.release();
				     ok.acquire();
				 }
				 mutex.release();
			 }
			 else {
				 System.out.println("Process "+id+" waits on barrier");
				 mutex.release();
				 barrier.acquire();
				 ok.release();
			 }
			 System.out.println("Process "+id+" goes through barrier");
		}
		}
		catch(InterruptedException e) { }
	}
	public static void main(String[] args) {
		SemaphoreBarrier[] processes=new SemaphoreBarrier[N];
		for (int i=0;i<N;i++) {
			processes[i]=new SemaphoreBarrier();
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
