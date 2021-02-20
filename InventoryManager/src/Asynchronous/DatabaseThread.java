package Asynchronous;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

public final class DatabaseThread extends Thread {
	
	private static Connection connection;
	
	public DatabaseThread() {
		try {
			connection=getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		
	}
	
	public static ResultSet query(String sql) throws SQLException {
		return connection.createStatement().executeQuery(sql);
	}
	public static void update(String sql) throws SQLException {
		connection.createStatement().executeUpdate(sql);
	}
	
	private static Connection getConnection() throws Exception {
	    String driver = "org.sqlite.JDBC";
	    String myDocuments = null;

	    try {
	        Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
	        p.waitFor();

	        InputStream in = p.getInputStream();
	        byte[] b = new byte[in.available()];
	        in.read(b);
	        in.close();

	        myDocuments = new String(b);
	        myDocuments = myDocuments.split("\\s\\s+")[4];

	    } catch(Throwable t) {
	        t.printStackTrace();
	    }
	    File f= new File(myDocuments+"\\InventoryManager\\inventorymanager.db");
	    System.out.println(myDocuments);
	    String url = "jdbc:sqlite:"+f.getAbsolutePath();
	    String username = "";
	    String password = "";
	    DriverManager.registerDriver(
	    		   (Driver) Class.forName( driver ).newInstance() );
	    return DriverManager.getConnection(url, username, password);
	 }
	
	public void shutdown() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
