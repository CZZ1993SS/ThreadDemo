package notify;

/**
 * @author czz
 * 测试 wait/notify/notifyAll
 */
public class Test {

    private static Express express = new Express(0,Express.CITY);

    /**
     * 检查里程数变化的线程,不满足条件，线程一直等待
     */
    private static class CheckKm extends Thread{

        @Override
        public void run() {
            express.waitKm();
        }
    }

    /**
     * 检查地点变化的线程,不满足条件，线程一直等待
     */
    private static class CheckSite extends Thread{

        @Override
        public void run() {
            express.waitSite();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        /*测试线程数*/
        int count = 3;


        for(int i = 0; i < count; i++){

            /*监听地点的变化*/
            new CheckSite().start();
        }


        for(int i = 0; i < count; i++){

            /*监听里程数的变化*/
            new CheckKm().start();
        }

        /*主线程休眠*/
        Thread.sleep(1000);

        /*快递地点变化*/
        express.changeKm();


        /*notifyAll（全部通知一遍）第一次进入wait被通知，再次进入循环，后进行 wait*/

        /*notify（通知一次）通知信号有可能被其他线程遮断，无法通知到正确的要唤醒线程，无法向下传递*/
    }

}
