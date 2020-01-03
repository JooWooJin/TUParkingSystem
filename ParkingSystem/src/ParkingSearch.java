import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParkingSearch extends JFrame implements ActionListener {

	String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "ehdgus12";
	
	String carname;
	String name;
	String intime;
	String sizeofcar;
	String comp1;
	String comp2;
	long charge=0;
	int timepay=0;
	JLabel inputname = new JLabel("이름");
	JLabel inputcarname = new JLabel("차량 번호");
	JLabel itimel = new JLabel("입차시간");
	JLabel icarsizel = new JLabel("차량크기");
	JLabel icarl = new JLabel("차종");
	JLabel icaraddl = new JLabel("차량위치");
	JLabel iseasonl = new JLabel("정기권");
	JLabel iusetimel = new JLabel("사용시간");
	JLabel ichargel = new JLabel("현재금액");
	JLabel itime = new JLabel(); //입차시간
	JLabel icarsize = new JLabel(); //차량크기
	JLabel icar = new JLabel(); //차량이름
	JLabel icaradd = new JLabel(); //차량위치
	JLabel iseason = new JLabel(); //차량정기권여부
	JLabel iusetime = new JLabel(); //사용시간
	JLabel icharge = new JLabel(); //현재요금
	JTextField inname = new JTextField(20);
	JTextField incarname = new JTextField(20);
	JButton b1 = new JButton();//차량 정보 검색
	JButton b2 = new JButton();//현재 검색차량 요금게산




	JPanel mp1 = new JPanel(new BorderLayout());
	JPanel mp2 = new JPanel(new BorderLayout());
	JPanel sp = new JPanel(new GridLayout(2,2));
	JPanel pp = new JPanel(new GridLayout(7,2));


	public ParkingSearch()
	{
		//
		itimel.setOpaque(true);
		icarsizel.setOpaque(true);
		icarl.setOpaque(true);
		icaraddl.setOpaque(true);
		iseasonl.setOpaque(true);
		iusetimel.setOpaque(true);
		ichargel.setOpaque(true);
		itime.setOpaque(true);
		icarsize.setOpaque(true);
		icar.setOpaque(true);
		icaradd.setOpaque(true);
		iseason.setOpaque(true);
		iusetime.setOpaque(true);
		icharge.setOpaque(true);
		
		inputname.setOpaque(true);
		inputcarname.setOpaque(true);
		inputname.setBackground(new Color(38,38,38));
		inputcarname.setBackground(new Color(38,38,38));
		inputname.setForeground(new Color(54,163,247));
		inputcarname.setForeground(new Color(54,163,247));
		itimel.setBackground(new Color(38,38,38));
		icarsizel.setBackground(new Color(38,38,38));
		icarl.setBackground(new Color(38,38,38));
		icaraddl.setBackground(new Color(38,38,38));
		iseasonl.setBackground(new Color(38,38,38));
		iusetimel.setBackground(new Color(38,38,38));
		ichargel.setBackground(new Color(38,38,38));
		itime.setBackground(new Color(38,38,38));
		icarsize.setBackground(new Color(38,38,38));
		icar.setBackground(new Color(38,38,38));
		icaradd.setBackground(new Color(38,38,38));
		iseason.setBackground(new Color(38,38,38));
		iusetime.setBackground(new Color(38,38,38));
		icharge.setBackground(new Color(38,38,38));
		
		itimel.setForeground(new Color(54,163,247));
		icarsizel.setForeground(new Color(54,163,247));
		icarl.setForeground(new Color(54,163,247));
		icaraddl.setForeground(new Color(54,163,247));
		iseasonl.setForeground(new Color(54,163,247));
		iusetimel.setForeground(new Color(54,163,247));
		ichargel.setForeground(new Color(54,163,247));
		itime.setForeground(new Color(54,191,163));
		icarsize.setForeground(new Color(54,191,163));
		icar.setForeground(new Color(54,191,163));
		icaradd.setForeground(new Color(54,191,163));
		iseason.setForeground(new Color(54,191,163));
		iusetime.setForeground(new Color(54,191,163));
		icharge.setForeground(new Color(54,191,163));
		
		
		//
		b1.setBorder(null);
		b2.setBorder(null);
		b1.setIcon(new ImageIcon("search_btn1.png"));
		b2.setIcon(new ImageIcon("search_btn2.png"));
		inname.setFont(new Font("맑은 고딕",Font.BOLD,15));
		incarname.setFont(new Font("맑은 고딕",Font.BOLD,15));
		inputname.setFont(new Font("맑은 고딕",Font.BOLD,15));
		inputcarname.setFont(new Font("맑은 고딕",Font.BOLD,15));
		itimel.setFont(new Font("맑은 고딕",Font.BOLD,15));
		icarsizel.setFont(new Font("맑은 고딕",Font.BOLD,15));
		icarl.setFont(new Font("맑은 고딕",Font.BOLD,15));
		icaraddl.setFont(new Font("맑은 고딕",Font.BOLD,15));
		iseasonl.setFont(new Font("맑은 고딕",Font.BOLD,15));
		iusetimel.setFont(new Font("맑은 고딕",Font.BOLD,15));
		ichargel.setFont(new Font("맑은 고딕",Font.BOLD,15));
		itime.setFont(new Font("맑은 고딕",Font.BOLD,15));
		icarsize.setFont(new Font("맑은 고딕",Font.BOLD,15));
		icar.setFont(new Font("맑은 고딕",Font.BOLD,15));
		icaradd.setFont(new Font("맑은 고딕",Font.BOLD,15));
		iseason.setFont(new Font("맑은 고딕",Font.BOLD,15));
		iusetime.setFont(new Font("맑은 고딕",Font.BOLD,15));
		icharge.setFont(new Font("맑은 고딕",Font.BOLD,15));
		b1.setFont(new Font("맑은 고딕",Font.BOLD,15));
		b2.setFont(new Font("맑은 고딕",Font.BOLD,15));
		setTitle("주차관리시스템 - 차량주차");
		setSize(400,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		//UI설정
		sp.add(inputname);
		sp.add(inname);
		sp.add(inputcarname);
		sp.add(incarname);
		pp.add(itimel);
		pp.add(itime);
		pp.add(icarsizel);
		pp.add(icarsize);
		pp.add(icarl);
		pp.add(icar);
		pp.add(icaraddl);
		pp.add(icaradd);
		pp.add(iseasonl);
		pp.add(iseason);
		pp.add(iusetimel);
		pp.add(iusetime);
		pp.add(ichargel);
		pp.add(icharge);
		b1.addActionListener(this);
		b2.addActionListener(this);
		mp1.add(sp,BorderLayout.CENTER);
		mp1.add(b1,BorderLayout.SOUTH);
		mp2.add(pp,BorderLayout.CENTER);
		mp2.add(b2,BorderLayout.SOUTH);
		add(mp1,BorderLayout.NORTH);
		add(mp2,BorderLayout.CENTER);

		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ParkingSearch();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		Connection conn = null;
		Statement stmt = null;
		if(e.getSource()==b1)
		{
			try{
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
				stmt = conn.createStatement();

				String sql;
				String ddd = new String(inname.getText());
				String sss = new String(incarname.getText());

				sql = "SELECT * FROM important";
				ResultSet rs1 = stmt.executeQuery(sql);
				rs1.next();
				timepay = rs1.getInt("timepay");
				rs1.close();
				
				sql = "SELECT * FROM maindata";
				ResultSet rs = stmt.executeQuery(sql);

				int ckk = 0;
				while(rs.next())
				{
					comp1 = rs.getString("name");
					comp2 = rs.getString("carname");
					if(comp1.equals(ddd) && comp2.equals(sss))
					{
						ckk=1;
						Timestamp lt = rs.getTimestamp("inputtime");
						Date ldate = new Date(lt.getTime());
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String ltdate = simpleDateFormat.format(ldate);
						itime.setText(ltdate);
						icar.setText(rs.getString("car"));
						icarsize.setText(rs.getString("carsize"));
						icaradd.setText("("+rs.getString("carfloor")+"F) " + rs.getString("caradd"));
						iseason.setText(rs.getString("season"));
						long timeminus = ts.getTime()-lt.getTime();
						timeminus = timeminus/1000;
						//몇일 몇시간 몇분 몇초
						long days=timeminus/60/60/24;
						long hour=(timeminus/60/60)%24;
						long minutes=(timeminus/60)%60;
						long seconed=(timeminus%60);
						if(0 < days)
						{
							iusetime.setText(days + "일 " + hour + "시간 " + minutes + "분 " + seconed + "초");
						}
						else if(0 < hour )
						{
							iusetime.setText(hour + "시간 " + minutes + "분 " + seconed + "초");
						}
						else if(0 < minutes )
						{
							iusetime.setText(minutes + "분 " + seconed + "초");
						}
						else if(0 < seconed )
						{
							iusetime.setText(seconed + "초");
						}
						if(iseason.getText().equals("X"))
						{
							charge = timeminus/60*timepay/60/100*100+timepay; // 기본요금 1500원 +시간당 1500원
							icharge.setText(charge + "원"); //현재요금
						}
						else
						{
							charge = 0;
							icharge.setText("정기권 차량");
						}
					}
				}
				if(ckk==0)
				{
					JOptionPane.showMessageDialog(null,"입력하신 차량정보가 없습니다." , "Do not have any input infomation",JOptionPane.ERROR_MESSAGE);					
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
		}
		else if(e.getSource()==b2)
		{
			if(!(icharge.equals("")))
			{
				try{
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
					stmt = conn.createStatement();

					String sql;
					String ddd = new String(inname.getText());
					String sss = new String(incarname.getText());
					sql = "Select * from maindata where carname = '" + sss +  "';";
					ResultSet rs = stmt.executeQuery(sql);
					rs.next();
					String x =rs.getString("carfloor");
					String y =rs.getString("caradd");
					sql = "INSERT INTO history values('" + ts + "','출차'," + x + "," + y + "," + charge + ",'" + sss + "');";
					stmt.executeUpdate(sql);
					
					String carname = incarname.getText();
					sql = "DELETE FROM maindata where carname='" + carname + "'";
					stmt.executeUpdate(sql);

					JOptionPane.showMessageDialog(null,new JLabel("차량 출차 완료", javax.swing.SwingConstants.CENTER),"Check out Successful",JOptionPane.PLAIN_MESSAGE);
					dispose();
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
			}
			else
			{
				JOptionPane.showMessageDialog(null,"검색된 차량정보가없습니다." , "Do not have any search infomation",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
