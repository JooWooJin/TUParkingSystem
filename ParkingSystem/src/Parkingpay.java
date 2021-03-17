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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Parkingpay extends JFrame implements ActionListener {

	String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";
	JPanel p0 = new JPanel(new FlowLayout());
	JPanel cp = new JPanel(new GridLayout(5,1));
	JPanel p1 = new JPanel(new GridLayout(1,2));
	JPanel p2 = new JPanel(new GridLayout(1,2));
	JPanel p3 = new JPanel(new GridLayout(1,2));
	JLabel lll = new JLabel("���� �ð��� : 0\\ ����� : 0\\ ");
	JLabel ipay = new JLabel("�ð��� ����");
	JLabel ispay = new JLabel("����� ����");
	JLabel passcheck = new JLabel("��й�ȣ �Է�");
	JButton b = new JButton();//"�缳��"
	JTextField incomepay = new JTextField(10);
	JTextField incomeseasonpay = new JTextField(10);
	JPasswordField pass = new JPasswordField(10);
	String pay1="";
	String pay2="";
	void getnowpay()
	{
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();
			String sql;
			sql = "Select * from important";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int tp = rs.getInt("timepay");
			int sp = rs.getInt("seasonpay");
			lll.setText("���� �ð��� : "+tp+"\\ ����� : "+sp+"\\ ");
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
	public Parkingpay()
	{
		getnowpay();
		cp.setBackground(new Color(19,19,19));
		b.setBorder(null);
		b.setIcon(new ImageIcon("coinreset_btn.png"));
		p0.setOpaque(true);
		p0.setBackground(new Color(38,38,38));
		ispay.setOpaque(true);
		ipay.setOpaque(true);
		ipay.setBackground(new Color(38,38,38));
		ispay.setBackground(new Color(38,38,38));
		ispay.setForeground(new Color(54,163,247));
		ipay.setForeground(new Color(54,163,247));
		lll.setOpaque(true);
		lll.setBackground(new Color(38,38,38));
		lll.setForeground(new Color(54,163,247));
		lll.setFont(new Font("���� ���",Font.BOLD,18));
		passcheck.setOpaque(true);
		passcheck.setBackground(new Color(38,38,38));
		passcheck.setForeground(new Color(54,163,247));
		passcheck.setFont(new Font("���� ���",Font.BOLD,18));
		pass.setFont(new Font("���� ���",Font.BOLD,18));
		ipay.setFont(new Font("���� ���",Font.BOLD,18));
		ispay.setFont(new Font("���� ���",Font.BOLD,18));
		b.setFont(new Font("���� ���",Font.BOLD,15));
		incomepay.setFont(new Font("���� ���",Font.BOLD,18));
		incomeseasonpay.setFont(new Font("���� ���",Font.BOLD,18));
		setTitle("���������ý��� - �����缳��");
		setSize(400,250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		//UI����
		p0.add(lll);
		p1.add(ipay);
		p1.add(incomepay);
		p2.add(ispay);
		p2.add(incomeseasonpay);
		p3.add(passcheck);
		p3.add(pass);
		cp.add(p0);
		cp.add(p1);
		cp.add(p2);
		cp.add(p3);
		cp.add(b);
		pass.addActionListener(this);
		b.addActionListener(this); 
		add(cp,BorderLayout.CENTER);

		ImageIcon img = new ImageIcon("TUPK.png");
		setIconImage(img.getImage());
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Parkingpay();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement stmt = null;
		String comparepassword;
		pay1 = incomepay.getText();
		pay2 = incomeseasonpay.getText();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		if(e.getSource()==b || e.getSource()==pass)
		{
			String eq = new String(pass.getPassword());
			if(pay1.equals("")==false && pay2.equals("")==false)
			{
				try{
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
					stmt = conn.createStatement();
					String sql;
					sql = "Select * from important";
					ResultSet rs = stmt.executeQuery(sql);

					rs.next();
					String imp = rs.getString("password");
					if(imp.equals(eq))
					{
						sql = "UPDATE Important SET timepay='"+pay1+"',seasonpay='"+pay2+"' WHERE password='"+ imp +"'";
						stmt.executeUpdate(sql);

						sql = "insert into history values('"+ts+"','���������',null,null,null,null)";
						stmt.executeUpdate(sql);
						stmt.close();
						conn.close();
						JOptionPane.showMessageDialog(null,new JLabel("���������� ����Ǿ����ϴ�.", javax.swing.SwingConstants.CENTER),"Success",JOptionPane.PLAIN_MESSAGE);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null,"��й�ȣ�� Ʋ�Ƚ��ϴ�." , "Please correct Password",JOptionPane.ERROR_MESSAGE);
					}
				}catch(SQLException se1){
					JOptionPane.showMessageDialog(null,"�����̿��� ���ڸ� �Է�����������" , "Please correct number input",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null,"��Ȯ�� ������ �Է����ּ���" , "Please correct input",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
