import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ParkingSeasonCarlist extends JFrame {

	String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";
	
	String col[] =  {"정기권만료시간","차량번호"};
	String data[][] = new String[0][2];
	DefaultTableModel model = new DefaultTableModel(data,col);
	JTable text = new JTable(model); 
	JScrollPane print = new JScrollPane(text);
	
	public ParkingSeasonCarlist()
	{
		setTitle("주차관리시스템 - 정기권차량리스트");
		setSize(300,500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT * FROM season";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Timestamp histime = rs.getTimestamp("last");
				Date hsdate = new Date(histime.getTime());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String hisdate = simpleDateFormat.format(hsdate);
				String carname = rs.getString("carname");
				Object inputdata[] = {hisdate,carname};
				model.addRow(inputdata);
			}
			rs.close();
			stmt.close();
			conn.close();
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
		
		add(print);
		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setResizable(false);
		setVisible(true);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ParkingSeasonCarlist();
	}

}
