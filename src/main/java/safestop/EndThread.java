package safestop;

/**
 * @author czz
 * 安全的中断停止线程
 */
public class EndThread {


    /**
     * Thread 的中断操作
     */
    private static class endThread extends Thread{

        public endThread(String name){
            super(name);
        }

        @Override
        public void run(){

            String thread_name = Thread.currentThread().getName();

            while (!isInterrupted()){

                System.out.println(thread_name + ": 正在工作");
            }

            System.out.println(thread_name + "中断标志位->" + isInterrupted());

        }

    }


    /**
     * Runnable 的中断操作
     */
    private static class endRunnable implements Runnable{

        public void run() {

            String thread_name = Thread.currentThread().getName();

            while (!Thread.currentThread().isInterrupted()){

                System.out.println(thread_name + ": 正在工作");
            }

            System.out.println(thread_name + "中断标志位->" + Thread.currentThread().isInterrupted());

        }
    }



    /*public static void main(String[] args) throws InterruptedException {

        Thread endThread = new endThread("endThread");

        endThread.start();

        *//*主线程休眠*//*
        Thread.sleep(20);

        *//*给子线程发出中断信号*//*
        endThread.interrupt();

    }*/


    public static void main(String[] args) throws InterruptedException {

        endRunnable endRunnable = new endRunnable();

        Thread endThread = new Thread(endRunnable);

        endThread.start();

        /*主线程休眠*/
        Thread.sleep(20);

        /*给子线程发出中断信号*/
        endThread.interrupt();

    }

}
