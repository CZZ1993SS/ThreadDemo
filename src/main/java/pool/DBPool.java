package pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author czz
 * 实现一个等待超时数据库的连接池
 */
public class DBPool {

	/**
	 * 数据库池的容器
	 */
	private static LinkedList<Connection> pool = new LinkedList<Connection>();

	/**
	 * 初始化连接池,把连接放进容器
	 */
	public DBPool(int initalSize) {

		if(initalSize>0) {

			for(int i = 0; i < initalSize; i++) {

				pool.addLast(SqlConnectImpl.fetchConnection());
			}

		}
	}

	/**
	 * 拿连接，在 mills 时间内还拿不到数据库连接，返回一个null
	 */
	public Connection fetchConn(long mills) throws InterruptedException {

		synchronized (pool) {

			if (mills < 0) {

				while(pool.isEmpty()) {

					pool.wait();
				}

				/*从头部拿一个连接出来*/
				return pool.removeFirst();

			}else{

				long overtime = System.currentTimeMillis() + mills;

				long remain = mills;

				while(pool.isEmpty() && remain > 0) {

					pool.wait(remain);

					remain = overtime - System.currentTimeMillis();
				}

				Connection result  = null;

				if(!pool.isEmpty()) {

					/*从头部拿一个连接出来*/
					result = pool.removeFirst();
				}

				return result;
			}
		}
	}

	/**
	 * 放回数据库连接
	 */
	public void releaseConn(Connection conn) {

		if(conn != null) {

			synchronized (pool) {

				/*从尾部把当前连接放进去*/
				pool.addLast(conn);

				/*通知其他需要连接的线程*/
				pool.notifyAll();
			}

		}

	}

 
}
