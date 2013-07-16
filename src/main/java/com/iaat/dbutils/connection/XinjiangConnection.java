package com.iaat.dbutils.connection;

import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import com.iaat.dbutils.source.DataSourceConfig;

public class XinjiangConnection implements  BaseConnection{

    private static LinkedList m_notUsedConnection = new LinkedList();    
    private static HashSet m_usedUsedConnection = new HashSet();    
    private static String m_url = DataSourceConfig.getProperty("Xinjiang_URL");   
    private static String m_user = DataSourceConfig.getProperty("Xinjiang_USERNAME");    
    private static String m_password = DataSourceConfig.getProperty("Xinjiang_PASSWORD");
    private static String JDBC_DRIVER ="com.mysql.jdbc.Driver";  
    static final boolean DEBUG = true;    
    static private long m_lastClearClosedConnection = System.currentTimeMillis();    
    public static long CHECK_CLOSED_CONNECTION_TIME = 4 * 60 * 60 * 1000;    
    private static  XinjiangConnection single = null;
  
    static {    
        initDriver();    
    }   
  
    private  XinjiangConnection() {    
    }   
  
    public synchronized  static XinjiangConnection getInstance() {
        if (single == null) {  
            single = new XinjiangConnection();
        }  
       return single;     
    }

    private static void initDriver() {    
        Driver driver = null;    
        //load oracle driver    
        try {    
            driver = (Driver) Class.forName(JDBC_DRIVER).newInstance();    
            installDriver(driver);    
        } catch (Exception e) {    
        }   
  

    }   
  
    public static void installDriver(Driver driver) {    
        try {    
            DriverManager.registerDriver(driver);    
        } catch (Exception e) {    
        	e.printStackTrace();    
        }    
    }   
  
  
    public  synchronized Connection getConnection() {   
       
        clearClosedConnection();    
        while (m_notUsedConnection.size() > 0) {    
            try {    
                XinjiangConnectionWrapper wrapper = (XinjiangConnectionWrapper) m_notUsedConnection.removeFirst();    
                if (wrapper.connection.isClosed()) {    
                    continue;    
                }    
                m_usedUsedConnection.add(wrapper);    
                if (DEBUG) {    
                    wrapper.debugInfo = new Throwable("Connection initial statement");    
                }    
                return wrapper.connection;    
            } catch (Exception e) {    
            }    
        }    
        int newCount = getIncreasingConnectionCount();    
        LinkedList list = new LinkedList();    
        XinjiangConnectionWrapper wrapper = null;    
        for (int i = 0; i < newCount; i++) {    
            wrapper = getNewConnection();    
            if (wrapper != null) {    
                list.add(wrapper);    
            }    
        }    
        if (list.size() == 0) {    
            return null;    
        }    
        wrapper = (XinjiangConnectionWrapper) list.removeFirst();    
        m_usedUsedConnection.add(wrapper);   
  
        m_notUsedConnection.addAll(list);    
        list.clear();   
  
        return wrapper.connection;    
    }   
  
    private static XinjiangConnectionWrapper getNewConnection() {    
        try {    
            Connection con = DriverManager.getConnection(m_url, m_user, m_password);    
            XinjiangConnectionWrapper wrapper = new XinjiangConnectionWrapper(con);    
            return wrapper;    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return null;    
    }   
  
    static synchronized void pushConnectionBackToPool(XinjiangConnectionWrapper XinjiangConnectionWrapper) {    
        boolean exist = m_usedUsedConnection.remove(XinjiangConnectionWrapper);    
        if (exist) {    
            m_notUsedConnection.addLast(XinjiangConnectionWrapper);    
        }    
    }   
  
    public static int close() {    
        int count = 0;   
  
        Iterator iterator = m_notUsedConnection.iterator();    
        while (iterator.hasNext()) {    
            try {    
                ( (XinjiangConnectionWrapper) iterator.next()).close();    
                count++;    
            } catch (Exception e) {    
            }    
        }    
        m_notUsedConnection.clear();   
  
        iterator = m_usedUsedConnection.iterator();    
        while (iterator.hasNext()) {    
            try {    
                XinjiangConnectionWrapper wrapper = (XinjiangConnectionWrapper) iterator.next();    
                wrapper.close();    
                if (DEBUG) {    
                    wrapper.debugInfo.printStackTrace();    
                }    
                count++;    
            } catch (Exception e) {    
            }    
        }    
        m_usedUsedConnection.clear();   
  
        return count;    
    }   
  
    private static void clearClosedConnection() {    
        long time = System.currentTimeMillis();    
        //sometimes user change system time,just return    
        if (time < m_lastClearClosedConnection) {    
            time = m_lastClearClosedConnection;    
            return;    
}    
        //no need check very often    
        if (time - m_lastClearClosedConnection < CHECK_CLOSED_CONNECTION_TIME) {    
            return;    
        }    
        m_lastClearClosedConnection = time;   
  
        //begin check    
        Iterator iterator = m_notUsedConnection.iterator();    
        while (iterator.hasNext()) {    
            XinjiangConnectionWrapper wrapper = (XinjiangConnectionWrapper) iterator.next();    
            try {    
                if (wrapper.connection.isClosed()) {    
                    iterator.remove();    
                }    
            } catch (Exception e) {    
                iterator.remove();    
                if (DEBUG) {    
                    System.out.println("connection is closed, this connection initial StackTrace");    
                    wrapper.debugInfo.printStackTrace();    
                }    
            }    
        }   
  
        //make connection pool size smaller if too big    
        int decrease = getDecreasingConnectionCount();    
        if (m_notUsedConnection.size() < decrease) {    
            return;    
        }   
  
        while (decrease-- > 0) {    
            XinjiangConnectionWrapper wrapper = (XinjiangConnectionWrapper) m_notUsedConnection.removeFirst();    
            try {    
                wrapper.connection.close();    
            } catch (Exception e) {    
            }    
        }    
    }   
  
    /**   
     * get increasing connection count, not just add 1 connection   
     * @return count   
     */    
    public static int getIncreasingConnectionCount() {    
        int count = 1;    
        int current = getConnectionCount();    
        count = current / 4;    
        if (count < 1) {    
            count = 1;    
        }    
        return count;    
    }   
  
    /**   
     * get decreasing connection count, not just remove 1 connection   
     * @return count   
     */    
    public static int getDecreasingConnectionCount() {    
        int count = 0;    
        int current = getConnectionCount();    
        if (current < 10) {    
            return 0;    
        }    
        return current / 3;    
    }   
  
    public synchronized static void printDebugMsg() {    
        printDebugMsg(System.out);    
    }   
  
    public synchronized static void printDebugMsg(PrintStream out) {    
        if (DEBUG == false) {    
            return;    
        }    
        StringBuffer msg = new StringBuffer();    
        msg.append("debug message in " + XinjiangConnection.class.getName());    
        msg.append("\r\n");    
        msg.append("total count is connection pool: " + getConnectionCount());    
        msg.append("\r\n");    
        msg.append("not used connection count: " + getNotUsedConnectionCount());    
        msg.append("\r\n");    
        msg.append("used connection, count: " + getUsedConnectionCount());    
        out.println(msg);    
        Iterator iterator = m_usedUsedConnection.iterator();    
        while (iterator.hasNext()) {    
            XinjiangConnectionWrapper wrapper = (XinjiangConnectionWrapper) iterator.next();    
            wrapper.debugInfo.printStackTrace(out);    
        }    
        out.println();    
    }   
  
    public static synchronized int getNotUsedConnectionCount() {    
        return m_notUsedConnection.size();    
}   
  
    public static synchronized int getUsedConnectionCount() {    
        return m_usedUsedConnection.size();    
    }   
  
    public static synchronized int getConnectionCount() {    
        return m_notUsedConnection.size() + m_usedUsedConnection.size();    
    }   
  
    public static String getUrl() {    
        return m_url;    
    }   
  
    public static void setUrl(String url) {    
        if (url == null) {    
            return;    
        }    
        m_url = url.trim();    
    }   
  
    public static String getUser() {    
        return m_user;    
    }   
  
    public static void setUser(String user) {    
        if (user == null) {    
            return;    
        }    
        m_user = user.trim();    
    }   
  
    public static String getPassword() {    
        return m_password;    
    }   
  
    public static void setPassword(String password) {    
        if (password == null) {    
            return;    
        }    
        m_password = password.trim();    
    }   
  
}   
  
class XinjiangConnectionWrapper implements InvocationHandler {    
    private final static String CLOSE_METHOD_NAME = "close";    
    public Connection connection = null;    
    private Connection m_originConnection = null;    
    public long lastAccessTime = System.currentTimeMillis();    
    Throwable debugInfo = new Throwable("Connection initial statement");   
  
    XinjiangConnectionWrapper(Connection con) {    
        Class[] interfaces = {java.sql.Connection.class};    
        this.connection = (Connection) Proxy.newProxyInstance(    
            con.getClass().getClassLoader(),    
            interfaces, this);    
        m_originConnection = con;    
    }   
  
    void close() throws SQLException {    
        m_originConnection.close();    
    }   
  
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {    
        Object obj = null;    
        if (CLOSE_METHOD_NAME.equals(m.getName())) {    
        	XinjiangConnection.pushConnectionBackToPool(this);    
        }    
        else {    
            obj = m.invoke(m_originConnection, args);    
        }    
        lastAccessTime = System.currentTimeMillis();    
        return obj;    
    } 

}
