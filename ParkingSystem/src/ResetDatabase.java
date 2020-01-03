import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ResetDatabase extends JFrame {

	public ResetDatabase()
	{
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

		String USERNAME = "root";
		String PASSWORD = "ehdgus12";
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	

			String sql;
			sql = "drop database parkingsystem;";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "create database parkingsystem;";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "use parkingsystem;";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "create table Important(\r\n" + 
					"	password varchar(20) unique,\r\n" + 
					"	timepay int(10),\r\n" + 
					"	seasonpay int(10),\r\n" + 
					"	floor int(10),\r\n" + 
					"	place int(10),\r\n" + 
					"	small1 int(4),\r\n" + 
					"	small2 int(4),\r\n" + 
					"	middle1 int(4),\r\n" + 
					"	middle2 int(4),\r\n" + 
					"	large1 int(4),\r\n" + 
					"	large2 int(4)\r\n" + 
					");";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "insert into Important values('root',1500,80000,15,20,11,15,6,10,1,5);";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "create table maindata(\r\n" + 
					"  inputtime timestamp,\r\n" + 
					"  name varchar(20),\r\n" + 
					"  carsize varchar(20),\r\n" + 
					"  car varchar(20),\r\n" + 
					"  carname varchar(20) unique,\r\n" + 
					"  season varchar(20),\r\n" + 
					"  carfloor varchar(20),\r\n" + 
					"  caradd varchar(20)\r\n" + 
					");";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "create table season(\r\n" + 
					"	carname varchar(20) unique,\r\n" + 
					"	last timestamp\r\n" + 
					");";			
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "create table history(\r\n" + 
					"	nowtime timestamp,\r\n" + 
					"	action varchar(20),\r\n" + 
					"	x int(10),\r\n" + 
					"	y int(10),\r\n" + 
					"	income int(10),\r\n" + 
					"	carname varchar(20)\r\n" + 
					");";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			sql = "create table carlist(\r\n" + 
					"	car varchar(20),\r\n" + 
					"	carsize varchar(20)\r\n" + 
					");";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
			conn.close();

			JOptionPane.showMessageDialog(null,new JLabel("데이터베이스 초기화 완료", javax.swing.SwingConstants.CENTER),"Datatbase reset already",JOptionPane.PLAIN_MESSAGE);
		}catch(SQLException se1){
			se1.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new ResetDatabase();
	}

}
