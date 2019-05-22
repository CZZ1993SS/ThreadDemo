package notify;


/**
 * @author czz
 * 快递实体类
 */
public class Express {

    public final static String CITY = "ShangHai";

    /**
     * 快递运输里程数
     */
    private int km;

    /**
     * 快递到达地点
     */
    private String site;

    public Express() {}

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /**
     * 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理
     */
    public synchronized void changeKm(){

        this.km = 101;

        notify();

        //notifyAll();

        /*其他的业务代码*/

    }

    /**
     * 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理
     */
    public synchronized void changeSite(){

        this.site = "BeiJing";

        notify();

        //notifyAll();

    }

    public synchronized void waitKm(){

        while(this.km <= 100) {

            try {

                wait();

                System.out.println("检测公里数的线程 [" + Thread.currentThread().getId() + "] 被唤醒通知.");

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

        System.out.println("当前公里数-》" + this.km + ",开始更新地理数据 ~~~.");

    }

    public synchronized void waitSite(){

        while(CITY.equals(this.site)) {

            try {

                wait();

                System.out.println("检测地点的线程 [" + Thread.currentThread().getId() + "] 被唤醒通知.");

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        System.out.println("当前地点还在-》"+this.site+",稍后即将通知用户 -----.");

    }

}
