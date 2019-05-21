package safestop;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author czz
 * 抛出InterruptedException异常的时候，要注意中断标志位
 */
public class HasInterrputException {
	
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss_SSS");
	
	private static class UseThread extends Thread{
		
		public UseThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {

			String threadName = Thread.currentThread().getName();

			/*确保响应中断*/
			while(!isInterrupted()) {

				try {

					System.out.println("UseThread:"+formater.format(new Date()));

					Thread.sleep(3000);

				} catch (InterruptedException e) {

					System.out.println(threadName+" 当前标志位 " + isInterrupted()+ " at " + (formater.format(new Date())));

					/*捕捉到 InterruptedException 后中断标志位会被重置为false将导致无限循环, 所已需要手动调用一下 interrupt() */
					interrupt();

					e.printStackTrace();
				}

				System.out.println(threadName);

			}

			System.out.println(threadName+" interrput flag is " + isInterrupted());

		}
	}

	public static void main(String[] args) throws InterruptedException {

		Thread endThread = new UseThread("HasInterrputEx");

		endThread.start();

		System.out.println("Main:"+formater.format(new Date()));

		Thread.sleep(800);

		System.out.println("Main begin interrupt thread:"+formater.format(new Date()));

		endThread.interrupt();

	}

}
