import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;


public class ParkingSystemAdmin extends JFrame implements Runnable,ActionListener,MouseListener {

	ParkingSeasonCarlist cl;
	ParkingAuto autopark;
	ParkingAdminPasswordChange passchange;
	ParkingSeason registerticket;
	Parkingpay resetpay;
	ParkingSearch searchandpay;
	ParkingLot PL;
	Parkingincomehistory inhis;
	ParkingFloor resetfloor;
	JLabel showtime = new JLabel();

	String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";

	Calendar cal = Calendar.getInstance();
	String col[] =  {"�ð�","�ൿ","����","��ġ","����","�ش� ���� ��ȣ"};
	String data[][] = new String[0][6];
	DefaultTableModel model = new DefaultTableModel(data,col);
	JTable text = new JTable(model);
	JScrollPane his = new JScrollPane(text); 
	JPanel hisp = new JPanel(new BorderLayout());//history
	JPanel P0 = new JPanel (new FlowLayout());
	JPanel Ptxti = new JPanel (new GridLayout(1,2));
	JPanel Ptxtl = new JPanel (new FlowLayout());
	JPanel Pcp = new JPanel (new GridLayout(1,2));
	JPanel Prf = new JPanel (new GridLayout(1,2));
	JPanel pcen = new JPanel(new FlowLayout());
	JPanel p1 = new JPanel(new BorderLayout());//p0 p2����
	JPanel p2 = new JPanel(new GridLayout(3,1));//�̹��޼��� �ǽð�������Ȳ
	JPanel p3 = new JPanel(new GridLayout(6,1));//
	JPanel p4 = new JPanel(new GridLayout(1,3));//�̹��޼��� �ֱٱ����ü����
	JPanel p5 = new JPanel(new GridLayout(1,4));//������Ȳ
	JPanel p6 = new JPanel(new GridLayout(1,3));//���԰��� ���������缳�� �������������Ʈ
	JPanel rfl = new JPanel(new FlowLayout());
	JPanel ref = new JPanel(new FlowLayout());
	JPanel cc1 = new JPanel(new FlowLayout());
	JPanel cc2 = new JPanel(new FlowLayout());
	JPanel cc3 = new JPanel(new FlowLayout());
	JPanel ich = new JPanel(new FlowLayout());
	JPanel scl = new JPanel(new FlowLayout());
	JPanel nwm = new JPanel(new FlowLayout());
	JPanel nwc = new JPanel(new FlowLayout());
	JButton AutoParking = new JButton();//�ڵ�����
	JButton parkingsearch = new JButton();//���������˻�
	JButton charge = new JButton();//��ݴ�������
	JButton changepass = new JButton();//��й�ȣ����
	JButton seasonticket = new JButton();//����ǵ��
	JButton lot = new JButton();//����������Ȳ
	JLabel txti = new JLabel("�̹��� ���� ��Ȳ");
	JLabel income = new JLabel("0\\"); 
	JLabel txtcar = new JLabel("�ǽð� ���� ��Ȳ");
	JLabel car1 = new JLabel("���� : 0��");
	JLabel car2 = new JLabel("���� : 0��");
	JLabel car3 = new JLabel("���� : 0��");
	JLabel hisl = new JLabel("�ֱٱ��");
	JLabel refresh = new JLabel("�ֱٱ����ü����");
	JLabel incomehistory = new JLabel("���� ����");
	JLabel seasoncarlist = new JLabel("����� ���� ����Ʈ");
	JLabel refloor = new JLabel("�������� �缳��");
	//������ ���� ���� �������� ����
	//�߰������� ������������ ����
	//���������������� ������ ó���� �Ȱ��� 30�밡���ٰ�����
	JLabel lcar = new JLabel("0");
	JLabel mcar = new JLabel("0");
	void incomehistory()
	{
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();

			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String today = null;
			today = formatter.format(cal.getTime());
			Timestamp ts = Timestamp.valueOf(today);
			int year=ts.getYear()+1900;
			int month=ts.getMonth()+1;
			String sql="";
			if(month==2)
			{
				if(year%400==0 || (year%4==0 && year%100!=0))
				{
					sql = "SELECT sum(income) FROM history where nowtime between '"+year+"-"+month+"-01 00:00:00' and '"+year+"-"+month+"-29 23:59:59'";
				}
				else
				{
					sql = "SELECT sum(income) FROM history where nowtime between '"+year+"-"+month+"-01 00:00:00' and '"+year+"-"+month+"-28 23:59:59'";
				}
			}
			else if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)
			{
				sql = "SELECT sum(income) FROM history where nowtime between '"+year+"-"+month+"-01 00:00:00' and '"+year+"-"+month+"-31 23:59:59'";
			}
			else
			{
				sql = "SELECT sum(income) FROM history where nowtime between '"+year+"-"+month+"-01 00:00:00' and '"+year+"-"+month+"-30 23:59:59'";
			}
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			income.setText(rs.getLong("sum(income)")+"\\");
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
	void nowcar()
	{
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT Count(carfloor)  FROM  maindata where carsize='����';";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int carcount = rs.getInt("Count(carfloor)");
			car1.setText("���� : " + carcount + "��");
			sql = "SELECT Count(carfloor)  FROM  maindata where carsize='����';";
			rs = stmt.executeQuery(sql);
			rs.next();
			carcount = rs.getInt("Count(carfloor)");
			car2.setText("���� : " + carcount + "��");
			rs.close();
			sql = "SELECT Count(carfloor)  FROM  maindata where carsize='����';";
			rs = stmt.executeQuery(sql);
			rs.next();
			carcount = rs.getInt("Count(carfloor)");
			car3.setText("���� : " + carcount + "��");
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
	void history()
	{

		model.setRowCount(0);
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT * FROM history";
			Calendar cal = Calendar.getInstance();
			String today = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			today = formatter.format(cal.getTime());
			Timestamp ts = Timestamp.valueOf(today);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Timestamp histime = rs.getTimestamp("nowtime");
				Date hsdate = new Date(histime.getTime());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				String hisdate = simpleDateFormat.format(hsdate);
				String hisaction = rs.getString("action");
				int hisfloor = rs.getInt("x");
				int hisadd = rs.getInt("y");
				int hisincome = rs.getInt("income");
				String hiscarname = rs.getString("carname");
				Object inputdata[] = {hisdate,hisaction,hisfloor,hisadd,hisincome,hiscarname};
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
	}
	void seasoncheck()
	{
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
				Timestamp cmptime = rs.getTimestamp("last");
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				String today = null;
				today = formatter.format(cal.getTime());
				Timestamp ts = Timestamp.valueOf(today);
				if(ts.getTime()>cmptime.getTime())
				{
					String xxx = rs.getString("carname");
					sql="UPDATE maindata SET season='X' where carname='" + xxx + "';";
					stmt.executeUpdate(sql);
					sql="DELETE maindata where carname='" + xxx + "';";
					stmt.executeUpdate(sql);
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
	public ParkingSystemAdmin()
	{

		//�⺻UI����
		setTitle("���������ý��� - �����ڷα���");
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//history,nowcar�ҷ�����
		history();
		nowcar();
		seasoncheck();
		incomehistory();
		text.getColumn("�ð�").setPreferredWidth(80);
		text.getColumn("����").setPreferredWidth(10);
		text.getColumn("��ġ").setPreferredWidth(10);
		//��ư�׷���
		AutoParking.setBorder(null);
		parkingsearch.setBorder(null);
		charge.setBorder(null);
		changepass.setBorder(null);
		seasonticket.setBorder(null);
		lot.setBorder(null);
		AutoParking.setIcon(new ImageIcon("main_btn1.png"));
		AutoParking.setPreferredSize(new Dimension(190, 90));
		parkingsearch.setIcon(new ImageIcon("main_btn2.png"));
		parkingsearch.setPreferredSize(new Dimension(190, 90));
		charge.setIcon(new ImageIcon("main_btn3.png"));
		charge.setPreferredSize(new Dimension(190, 90));
		changepass.setIcon(new ImageIcon("main_btn4.png"));
		changepass.setPreferredSize(new Dimension(190, 90));
		seasonticket.setIcon(new ImageIcon("main_btn5.png"));
		seasonticket.setPreferredSize(new Dimension(190, 90));
		lot.setIcon(new ImageIcon("main_btn6.png"));
		lot.setPreferredSize(new Dimension(190, 90));
		//�׷���

		seasoncarlist.setOpaque(true);
		seasoncarlist.setBackground(new Color(38,38,38));
		seasoncarlist.setForeground(new Color(255,255,255));
		text.setBorder(null);
		his.setBorder(null);
		his.setBackground(new Color(38,38,38));
		Prf.setBackground(new Color(38,38,38));
		hisp.setBackground(new Color(38,38,38));
		p6.setBackground(new Color(19,19,19));
		p4.setBackground(new Color(19,19,19));
		p3.setBackground(new Color(19,19,19));
		p1.setBackground(new Color(19,19,19));
		p2.setBackground(new Color(19,19,19));
		his.getViewport().setBackground(new Color(38,38,38));                                                                                       
		showtime.setForeground(new Color(255,255,255));
		showtime.setOpaque(true);
		showtime.setBackground(new Color(19,19,19));
		txti.setOpaque(true);
		txti.setBackground(new Color(19,19,19));
		income.setOpaque(true);
		income.setBackground(new Color(19,19,19));
		txtcar.setOpaque(true);
		txtcar.setBackground(new Color(19,19,19));
		car1.setOpaque(true);
		car1.setBackground(new Color(19,19,19));
		car2.setOpaque(true);
		car2.setBackground(new Color(19,19,19));
		car3.setOpaque(true);
		car3.setBackground(new Color(19,19,19));
		Ptxtl.setBackground(new Color(19,19,19));
		p5.setBackground(new Color(19,19,19));
		cc1.setBackground(new Color(19,19,19));
		cc2.setBackground(new Color(19,19,19));
		cc3.setBackground(new Color(19,19,19));
		ref.setBackground(new Color(19,19,19));
		rfl.setBackground(new Color(19,19,19));
		ich.setBackground(new Color(19,19,19));
		scl.setBackground(new Color(19,19,19));
		nwm.setBackground(new Color(19,19,19));
		nwc.setBackground(new Color(19,19,19));
		P0.setBackground(new Color(19,19,19));


		AutoParking.setForeground(new Color(255,255,255));
		parkingsearch.setForeground(new Color(255,255,255));
		charge.setForeground(new Color(255,255,255));
		changepass.setForeground(new Color(255,255,255));
		seasonticket.setForeground(new Color(255,255,255));
		lot.setForeground(new Color(255,255,255));
		txti.setForeground(new Color(255,255,255));
		income.setForeground(new Color(255,255,255)); 
		txtcar.setForeground(new Color(255,255,255));
		car1.setForeground(new Color(255,255,255));
		car2.setForeground(new Color(255,255,255));
		car3.setForeground(new Color(255,255,255));
		hisl.setForeground(new Color(255,255,255));
		refresh.setForeground(new Color(255,255,255));
		incomehistory.setForeground(new Color(255,255,255));
		refloor.setForeground(new Color(255,255,255));
		//�ð�
		showtime.setFont(new Font("���� ���", Font.BOLD,18));
		new Thread(this).start();
		add(showtime);
		//��Ʈ����
		text.setFont(new Font("���� ���", Font.PLAIN,12));
		AutoParking.setFont(new Font("���� ���", Font.BOLD,18));
		parkingsearch.setFont(new Font("���� ���", Font.BOLD,18));
		charge.setFont(new Font("���� ���", Font.BOLD,18));
		changepass.setFont(new Font("���� ���", Font.BOLD,18));
		seasonticket.setFont(new Font("���� ���", Font.BOLD,18));
		lot.setFont(new Font("���� ���", Font.BOLD,18));
		txti.setFont(new Font("���� ���", Font.BOLD,18));
		income.setFont(new Font("���� ���", Font.BOLD,18)); 
		txtcar.setFont(new Font("���� ���", Font.BOLD,18));
		car1.setFont(new Font("���� ���", Font.BOLD,18));
		car2.setFont(new Font("���� ���", Font.BOLD,18));
		car3.setFont(new Font("���� ���", Font.BOLD,18));
		hisl.setFont(new Font("���� ���", Font.BOLD,18));
		refresh.setFont(new Font("���� ���", Font.BOLD,18));
		refloor.setFont(new Font("���� ���", Font.BOLD,18));
		incomehistory.setFont(new Font("���� ���", Font.BOLD,18));
		seasoncarlist.setFont(new Font("���� ���", Font.BOLD,18));

		//����UI����
		setLayout(new BorderLayout());
		P0.add(hisl);
		Pcp.add(P0);
		p6.add(rfl);
		Pcp.add(showtime);
		hisp.add(Pcp,BorderLayout.NORTH);
		hisp.add(his,BorderLayout.CENTER);
		p1.setLayout(new BorderLayout());
		p1.add(hisp,BorderLayout.CENTER);
		p1.add(p2,BorderLayout.SOUTH);
		p2.add(p6);
		p2.add(p4);
		p2.add(p5);
		nwm.add(txti);
		p4.add(nwm);
		Ptxtl.add(income);
		ich.add(incomehistory);
		p4.add(Ptxtl);
		rfl.add(refloor);
		p6.add(ich);
		scl.add(seasoncarlist);
		seasoncarlist.addMouseListener(this);
		p6.add(scl);
		p4.add(ref);
		lot.addActionListener(this);
		nwc.add(txtcar);
		p5.add(nwc);
		cc1.add(car1);
		cc2.add(car2);
		cc3.add(car3);
		p5.add(cc1);
		p5.add(cc2);
		p5.add(cc3);
		ref.add(refresh);
		p3.add(AutoParking);
		p3.add(parkingsearch);
		p3.add(charge);
		p3.add(changepass);
		p3.add(seasonticket);
		p3.add(lot);
		AutoParking.addMouseListener(this);
		parkingsearch.addMouseListener(this);
		charge.addMouseListener(this);
		changepass.addMouseListener(this);
		seasonticket.addMouseListener(this);
		lot.addMouseListener(this);
		txti.addMouseListener(this);
		income.addMouseListener(this);
		txtcar.addMouseListener(this);
		car1.addMouseListener(this);
		car2.addMouseListener(this);
		car3.addMouseListener(this);
		hisl.addMouseListener(this);
		his.addMouseListener(this);
		text.addMouseListener(this);
		//�׼�
		addMouseListener(this);
		hisl.addMouseListener(this);
		incomehistory.addMouseListener(this);
		refloor.addMouseListener(this);
		refresh.addMouseListener(this);
		AutoParking.addActionListener(this);
		parkingsearch.addActionListener(this);
		charge.addActionListener(this);
		changepass.addActionListener(this);
		seasonticket.addActionListener(this);
		//���	
		add(p1,BorderLayout.CENTER);
		add(p3,BorderLayout.EAST);
		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UIManager.put("ComboBox.background", new ColorUIResource(new Color(38,38,38)));
		UIManager.put("ComboBox.foreground", new ColorUIResource(new Color(255,255,255)));
		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new Color(38,38,38)));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(255,255,255)));
		new ParkingSystemAdmin();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		nowcar();
		history();
		incomehistory();
		if(e.getSource()==lot)
		{
			PL = new ParkingLot();
		}
		if(e.getSource()==AutoParking)
		{
			autopark = new ParkingAuto();
		}
		if(e.getSource()==changepass)
		{
			passchange = new ParkingAdminPasswordChange();
		}
		if(e.getSource()==seasonticket)
		{
			registerticket = new ParkingSeason() ;
		}
		if(e.getSource()==charge)
		{
			resetpay = new Parkingpay();
		}
		if(e.getSource()==parkingsearch)
		{
			searchandpay = new ParkingSearch();
		}
		nowcar();
		history();
		incomehistory();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		while(true)
		{
			Date nowtime = new Date();
			String texttime = formatter.format(nowtime);
			showtime.setText("����ð� : "+texttime);
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		history();
		nowcar();
		incomehistory();
		if(e.getSource()==refresh)
		{
			Connection conn = null;
			Statement stmt = null;
			try{
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
				stmt = conn.createStatement();

				String sql;
				sql = "DELETE FROM history";
				stmt.executeUpdate(sql);
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
		if(e.getSource()==incomehistory)
		{
			inhis = new Parkingincomehistory();
		}
		if(e.getSource()==refloor)
		{
			resetfloor = new ParkingFloor();
		}
		if(e.getSource()==seasoncarlist)
		{
			cl = new ParkingSeasonCarlist();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}