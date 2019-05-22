package threadlocal;

/**
 * @author czz
 * threadLocal的基本使用
 */
public class usethreadlocal {

    //static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){

        /*赋初始值*/
        @Override
        protected Integer initialValue(){
            return 1;
        }
    };

   /**
     * 运行3个线程
     */
   public void startThreadArray(){

       Thread[] runs = new Thread[3];

       /* 实例化线程 */
       for(int i=0; i<runs.length; i++){

           runs[i] = new Thread(new myThread(i));
       }

       /* 进入就绪状态 */
       for(int i = 0; i < runs.length; i++){

           runs[i].start();
       }

   }


    /**
     * 测试线程，将 ThreadLocal 变量的值变化， 并写回， 看看线程之间是否互相影响
     */
   public static class myThread implements Runnable{

       int id;

       public myThread(int id){this.id = id;}

       public void run() {

           System.out.println(Thread.currentThread().getName() + ": 启动");

           /*获得线程本地变量*/
           Integer value = threadLocal.get();

           value = value + id;

           /*写回线程本地变量*/
           threadLocal.set(value);

           System.out.println(Thread.currentThread().getName() + ":-> " + threadLocal.get());
       }
   }


    public static void main(String[] args) {

        usethreadlocal test = new usethreadlocal();

        test.startThreadArray();

    }

}
