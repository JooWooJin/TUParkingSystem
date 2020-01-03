import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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

public class ParkingSeason  extends JFrame implements ActionListener {


	String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "ehdgus12";


	String corre;
	int seasonpay=0;
	JLabel title = new JLabel("정기권 한달에 : 0\\");
	JLabel inputcarname = new JLabel("차량 번호");
	JLabel inputlong = new JLabel("기간 (단위:달)");
	JTextField incarname = new JTextField(20); //차량번호
	JTextField timelong = new JTextField(20); //기간
	JButton b = new JButton();//"정기권 등록"
	JPanel mp = new JPanel(new GridLayout(4,1));
	JPanel p1 = new JPanel(new GridLayout(1,2));
	JPanel p2 = new JPanel(new FlowLayout());
	JPanel p3 = new JPanel(new GridLayout(1,2));


	public ParkingSeason()
	{
		title.setOpaque(true);
		inputcarname.setOpaque(true);
		inputlong.setOpaque(true);
		title.setBackground(new Color(38,38,38));
		inputcarname.setBackground(new Color(38,38,38));
		inputlong.setBackground(new Color(38,38,38));
		title.setForeground(new Color(54,163,247));
		inputcarname.setForeground(new Color(54,163,247));
		inputlong.setForeground(new Color(54,163,247));

		p2.setBackground(new Color(38,38,38));
		//ispay.setBackground(new Color(38,38,38));
		b.setBorder(null);
		b.setIcon(new ImageIcon("register_btn.png"));
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();  

			String sql = "SELECT * from important";
			ResultSet rs1 = stmt.executeQuery(sql);
			rs1.next();   
			seasonpay = rs1.getInt("seasonpay");
			rs1.close();
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
		title.setText("정기권 한달에 : " + seasonpay + "\\");
		title.setFont(new Font("맑은 고딕",Font.BOLD,18));
		inputcarname.setFont(new Font("맑은 고딕",Font.BOLD,18));
		incarname.setFont(new Font("맑은 고딕",Font.BOLD,18));
		b.setFont(new Font("맑은 고딕",Font.BOLD,18));
		inputlong.setFont(new Font("맑은 고딕",Font.BOLD,18));
		timelong.setFont(new Font("맑은 고딕",Font.BOLD,18));
		mp.setBackground(new Color(19,19,19));
		setTitle("주차관리시스템 - 정기권등록");
		setSize(300,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		//UI설정
		b.addActionListener(this);
		incarname.addActionListener(this);
		p1.add(inputcarname);
		p1.add(incarname);
		p2.add(title);
		p3.add(inputlong);
		p3.add(timelong);
		mp.add(p2);
		mp.add(p1);
		mp.add(p3);
		mp.add(b);
		add(mp,BorderLayout.CENTER);


		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ParkingSeason();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub


		if(!(incarname.getText().equals("")))
		{

			int chhk = 0;
			Connection conn = null;
			Statement stmt = null;
			String carnam = new String(incarname.getText());
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			int addmonth=0;
			addmonth = Integer.parseInt(timelong.getText());
			cal.add(Calendar.MONTH, addmonth);
			String today = null;
			today = formatter.format(cal.getTime());
			Timestamp ts = Timestamp.valueOf(today);
			try{
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
				stmt = conn.createStatement();  

				String sql = "SELECT * from season";
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next())
				{
					String cmpp = rs.getString("carname");
					if(carnam.equals(cmpp))																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																
					{
						corre=cmpp;
						chhk=1;
						break;
					}
				}
				if(chhk==1)
				{
					JOptionPane.showMessageDialog(null,"이미 등록된 차량입니다." , "It is already registered car.",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(JOptionPane.showConfirmDialog(null,"해당번호는 없는 차량입니다 등록하시겠습니까?" , "It is not registered car.",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
					{
						sql = "INSERT INTO season values('" + carnam + "','" + ts +"')";
						stmt.executeUpdate(sql);
						formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						cal = Calendar.getInstance();
						today = formatter.format(cal.getTime());
						ts = Timestamp.valueOf(today);
						sql = "UPDATE maindata SET season='O' WHERE carname='"+ carnam +"'";
						stmt.executeUpdate(sql);
						sql = "INSERT INTO history values('" + ts + "','"+addmonth+"달 정기권등록'," + "null" + "," + "null" + "," + (long)(seasonpay*addmonth) + ",'" + carnam + "');";
						stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null,new JLabel("정기권 구매 성공", javax.swing.SwingConstants.CENTER),"Buy Success",JOptionPane.PLAIN_MESSAGE);
						dispose();
					}
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
		else
		{
			JOptionPane.showMessageDialog(null,"정상적인 차량번호를 입력해주세요." , "Please correct car number.",JOptionPane.ERROR_MESSAGE);
		}
	}
}
