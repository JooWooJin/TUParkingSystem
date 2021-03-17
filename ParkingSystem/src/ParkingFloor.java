import java.awt.BorderLayout;
import java.awt.Color;
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

public class ParkingFloor extends JFrame implements ActionListener {

	String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
	String DB_URL = "jdbc:mariadb://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";
	
	
	JPanel cp = new JPanel(new GridLayout(6,1));
	JPanel p1 = new JPanel(new GridLayout(1,2));
	JPanel p2 = new JPanel(new GridLayout(1,2));
	JPanel p3 = new JPanel(new GridLayout(1,2));
	JPanel p4 = new JPanel(new GridLayout(1,2));
	JPanel p5 = new JPanel(new GridLayout(1,2));
	JPanel p32 = new JPanel(new GridLayout(1,2));
	JPanel p42 = new JPanel(new GridLayout(1,2));
	JPanel p52 = new JPanel(new GridLayout(1,2));
	JLabel iplacesize = new JLabel("������ ����������");
	JLabel ifloorsize = new JLabel("���� ��������");
	JLabel ismallsize = new JLabel("�������� ��������");
	JLabel imiddlesize = new JLabel("�������� ��������");
	JLabel ilargesize = new JLabel("�������� ��������");
	JButton b = new JButton();//"�缳��"
	JTextField isetfloor = new JTextField(10);
	JTextField isetplace = new JTextField(10);
	JTextField isetsmall1 = new JTextField(4);
	JTextField isetsmall2 = new JTextField(4);
	JTextField isetmiddle1 = new JTextField(4);
	JTextField isetmiddle2 = new JTextField(4);
	JTextField isetlarge1 = new JTextField(4);
	JTextField isetlarge2 = new JTextField(4);

	int s1=0;
	int s2=0;
	int m1=0;
	int m2=0;
	int l1=0;
	int l2=0;

	String floor="";
	String place="";
	public ParkingFloor()
	{
		cp.setBackground(new Color(19,19,19));
		b.setBorder(null);
		b.setIcon(new ImageIcon("resetfloor_btn.png"));
		ifloorsize.setOpaque(true);
		iplacesize.setOpaque(true);
		ismallsize.setOpaque(true);
		imiddlesize.setOpaque(true);
		ilargesize.setOpaque(true);
		iplacesize.setBackground(new Color(38,38,38));
		ifloorsize.setBackground(new Color(38,38,38));
		ismallsize.setBackground(new Color(38,38,38));
		imiddlesize.setBackground(new Color(38,38,38));
		ilargesize.setBackground(new Color(38,38,38));
		ifloorsize.setForeground(new Color(54,163,247));
		iplacesize.setForeground(new Color(54,163,247));
		ismallsize.setForeground(new Color(54,163,247));
		imiddlesize.setForeground(new Color(54,163,247));
		ilargesize.setForeground(new Color(54,163,247));
		iplacesize.setFont(new Font("���� ���",Font.BOLD,15));
		ifloorsize.setFont(new Font("���� ���",Font.BOLD,15));
		ismallsize.setFont(new Font("���� ���",Font.BOLD,15));
		imiddlesize.setFont(new Font("���� ���",Font.BOLD,15));
		ilargesize.setFont(new Font("���� ���",Font.BOLD,15));
		b.setFont(new Font("���� ���",Font.BOLD,15));
		isetfloor.setFont(new Font("���� ���",Font.BOLD,15));
		isetplace.setFont(new Font("���� ���",Font.BOLD,15));
		isetsmall1.setFont(new Font("���� ���",Font.BOLD,15));
		isetsmall2.setFont(new Font("���� ���",Font.BOLD,15));
		isetmiddle1.setFont(new Font("���� ���",Font.BOLD,15));
		isetmiddle2.setFont(new Font("���� ���",Font.BOLD,15));
		isetlarge1.setFont(new Font("���� ���",Font.BOLD,15));
		isetlarge2.setFont(new Font("���� ���",Font.BOLD,15));

		
		setTitle("���������ý��� - ������������");
		setSize(300,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		//�ؽ�Ʈ�ʵ� �������
		isetsmall1.setHorizontalAlignment(JTextField.CENTER);
		isetsmall2.setHorizontalAlignment(JTextField.CENTER);
		isetmiddle1.setHorizontalAlignment(JTextField.CENTER);
		isetmiddle2.setHorizontalAlignment(JTextField.CENTER);
		isetlarge1.setHorizontalAlignment(JTextField.CENTER);
		isetlarge2.setHorizontalAlignment(JTextField.CENTER);
		//UI����
		p1.add(iplacesize);
		p1.add(isetfloor);
		p2.add(ifloorsize);
		p2.add(isetplace);
		p3.add(ismallsize);
		p32.add(isetsmall1);
		p32.add(isetsmall2);
		p3.add(p32);
		p4.add(imiddlesize);
		p42.add(isetmiddle1);
		p42.add(isetmiddle2);
		p4.add(p42);
		p5.add(ilargesize);
		p52.add(isetlarge1);
		p52.add(isetlarge2);
		p5.add(p52);
		cp.add(p1);
		cp.add(p2);
		cp.add(p3);
		cp.add(p4);
		cp.add(p5);
		cp.add(b);
		isetplace.addActionListener(this);
		b.addActionListener(this);
		add(cp,BorderLayout.CENTER);

		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setResizable(false);
		setVisible(true);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ParkingFloor();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement stmt = null;
		String comparepassword;
		floor = isetfloor.getText();
		place = isetplace.getText();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		s1=Integer.parseInt(isetsmall1.getText());
		s2=Integer.parseInt(isetsmall1.getText());
		m1=Integer.parseInt(isetmiddle1.getText());
		m2=Integer.parseInt(isetmiddle2.getText());
		l1=Integer.parseInt(isetlarge1.getText());
		l2=Integer.parseInt(isetlarge2.getText());
		if(e.getSource()==b || e.getSource()==isetlarge2)
		{
			if(s1>s2)
			{
				int t=s1;
				s1=s2;
				s2=t;
			}
			if(m1>m2)
			{
				int t=m1;
				m1=m2;
				m2=t;
			}
			if(l1>l2)
			{
				int t=l1;
				l1=l2;
				l2=t;
			}
			if(l2<=Integer.parseInt(floor) && m2<=Integer.parseInt(floor) && s2<=Integer.parseInt(floor) && l1>0 && m1>0 && s1>0)
			{
				if(floor.equals("")==false && place.equals("")==false)
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

						sql = "UPDATE Important SET floor='"+floor+"',place='"+place+"' WHERE password='"+ imp +"'";
						stmt.executeUpdate(sql);

						sql = "insert into history values('"+ts+"','��������ũ�⺯��','"+floor+"','"+place+"',null,null)";
						stmt.executeUpdate(sql);
						
						stmt.close();
						conn.close();
						JOptionPane.showMessageDialog(null,new JLabel("���������� ����Ǿ����ϴ�.", javax.swing.SwingConstants.CENTER),"Success",JOptionPane.PLAIN_MESSAGE);
						dispose();
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
					JOptionPane.showMessageDialog(null,"��Ȯ�� ���ڸ� �Է����ּ���." , "Please correct input",JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"������ �´� ������ �������� ������ �Է����ּ���." , "Please correct input",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
