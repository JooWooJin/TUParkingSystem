import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

public class Parkingincomehistory extends JFrame implements ActionListener,MouseListener {


	String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
	String DB_URL = "jdbc:mariadb://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";

	String code;
	String col[] =  {"시간","행동","수입"};
	String data[][] = new String[0][3];
	DefaultTableModel model = new DefaultTableModel(data,col);
	JTable text = new JTable(model); 
	JScrollPane print = new JScrollPane(text);
	JPanel mp = new JPanel(new GridLayout(4,2));
	JPanel Qp = new JPanel(new BorderLayout());
	JPanel bp = new JPanel(new BorderLayout());
	JLabel b = new JLabel("환불버튼");
	JTextField t1 = new JTextField(20);
	JTextField t2 = new JTextField(20);
	JLabel reason = new JLabel("환불 처리 이유");
	JLabel Q = new JLabel("[?]");
	JLabel minus = new JLabel("환불 처리 금액");
	JLabel yy = new JLabel("검색할 년도 선택");
	JLabel mm = new JLabel("검색할 달 선택");

	JComboBox month = new JComboBox();
	JComboBox year = new JComboBox();
	void delseason()
	{
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT *  FROM season WHERE carname='"+code+"';";
			ResultSet rs = stmt.executeQuery(sql);
			if(!(rs.next()))
			{
				JOptionPane.showMessageDialog(null,new JLabel("해당차량은 정기권차량이 아닙니다. 확인해주세요", javax.swing.SwingConstants.CENTER),"Warning",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				sql = "DELETE FROM season WHERE carname='"+code+"';";
				stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null,new JLabel("정상적으로 등록취소가 되었습니다.", javax.swing.SwingConstants.CENTER),"Success",JOptionPane.PLAIN_MESSAGE);
				payback();
			}
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
	void payback()
	{
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();
			long money = Long.parseLong(t2.getText())*(-1);
			String sql = "INSERT INTO history values('" + ts + "','"+t1.getText()+"'," + "null" + "," + "null" + "," + money + ",null);";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		}catch(SQLException se1){
			JOptionPane.showMessageDialog(null,"가격에 숫자이외의 문자를 입력하지마세요" , "Please correct number input",JOptionPane.ERROR_MESSAGE);
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
	void Refresh()
	{
		model.setRowCount(0);
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT * FROM history where income<>'null'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Timestamp histime = rs.getTimestamp("nowtime");
				Date hsdate = new Date(histime.getTime());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String hisdate = simpleDateFormat.format(hsdate);
				String hisaction = rs.getString("action");
				int hisincome = rs.getInt("income");
				Object inputdata[] = {hisdate,hisaction,hisincome};
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
	void selected()
	{
		model.setRowCount(0);
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);	
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT * FROM history where income<>'null'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Timestamp histime = rs.getTimestamp("nowtime");
				//String compy1 =new String(year.getSelectedItem());
				Object ally = (year.getSelectedItem());
				Object allm = (month.getSelectedItem());
				int cmp =1900 + histime.getYear();
				int cmp2 =1+histime.getMonth();
				if((ally.equals("모든연도") && allm.equals("올해전부")) || (year.getSelectedItem().equals(cmp) && allm.equals("올해전부")) || (ally.equals("모든연도") && month.getSelectedItem().equals(cmp2)) || (year.getSelectedItem().equals(cmp) && month.getSelectedItem().equals(cmp2)))
				{
					Date hsdate = new Date(histime.getTime());
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String hisdate = simpleDateFormat.format(hsdate);
					String hisaction = rs.getString("action");
					int hisincome = rs.getInt("income");
					Object inputdata[] = {hisdate,hisaction,hisincome};
					model.addRow(inputdata);
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
	boolean CheckNumber(String str){
		char check;

		if(str.equals(""))
		{
			//문자열이 공백인지 확인
			return false;
		}

		for(int i = 0; i<str.length(); i++){
			check = str.charAt(i);
			if( check < 48 || check > 58)
			{
				//해당 char값이 숫자가 아닐 경우
				return false;
			}

		}		
		return true;
	}
	void inputcomb()
	{
		int i=1;
		year.addItem("모든연도");
		month.addItem("올해전부");
		for(i=1;i<=12;i++)
		{
			month.addItem(i);
		}
		for(i=2000;i<=2099;i++)
		{
			year.addItem(i);
		}
		year.setSelectedItem("2018");
		month.setSelectedItem("12");
	}
	public Parkingincomehistory()
	{
		setTitle("주차관리시스템 - 최근 수입");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,800);
		Refresh();
		inputcomb();
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		month.addActionListener(this);
		year.addActionListener(this);
		Q.addMouseListener(this);
		b.addMouseListener(this);
		t1.setText("정기권 등록취소");
		//UI디자인
		Q.setOpaque(true);
		Q.setBackground(new Color(38,38,38));
		Q.setForeground(new Color(255,255,255));
		Q.setFont(new Font("맑은 고딕",Font.BOLD,30));
		b.setOpaque(true);
		b.setBackground(new Color(38,38,38));
		b.setForeground(new Color(255,255,255));
		b.setFont(new Font("맑은 고딕",Font.BOLD,30));
		yy.setOpaque(true);
		mm.setOpaque(true);
		yy.setBackground(new Color(38,38,38));
		yy.setForeground(new Color(255,255,255));
		mm.setBackground(new Color(38,38,38));
		mm.setForeground(new Color(255,255,255));
		yy.setFont(new Font("맑은 고딕",Font.BOLD,30));
		mm.setFont(new Font("맑은 고딕",Font.BOLD,30));
		year.setFont(new Font("맑은 고딕",Font.BOLD,30));
		month.setFont(new Font("맑은 고딕",Font.BOLD,30));
		reason.setOpaque(true);
		minus.setOpaque(true);
		reason.setBackground(new Color(38,38,38));
		reason.setForeground(new Color(255,255,255));
		minus.setBackground(new Color(38,38,38));
		minus.setForeground(new Color(255,255,255));
		reason.setFont(new Font("맑은 고딕",Font.BOLD,30));
		minus.setFont(new Font("맑은 고딕",Font.BOLD,30));
		t1.setFont(new Font("맑은 고딕",Font.BOLD,30));
		t2.setFont(new Font("맑은 고딕",Font.BOLD,30));
		setBackground(new Color(38,38,38));
		//UI설정
		Qp.add(reason,BorderLayout.CENTER);
		Qp.add(Q,BorderLayout.EAST);
		mp.add(Qp);
		mp.add(t1);
		mp.add(minus);
		bp.add(t2,BorderLayout.CENTER);
		bp.add(b,BorderLayout.EAST);
		mp.add(bp);
		mp.add(year);
		mp.add(yy);
		mp.add(year);
		mp.add(mm);
		mp.add(month);
		add(print,BorderLayout.CENTER);
		add(mp,BorderLayout.SOUTH);
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
		new Parkingincomehistory();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		selected();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		selected();
		if(e.getSource()==Q)
		{
			JOptionPane.showMessageDialog(null,new JLabel("사유에\"정기권 등록취소\"일 경우 환불처리버튼을 누를시 차량번호등록취소창이나오고 환불처리됨", javax.swing.SwingConstants.CENTER),"Warning",JOptionPane.INFORMATION_MESSAGE);
		}
		if(e.getSource()==b)
		{
			try {
				String cmp = t1.getText();
				String cmp2 = new String("정기권 등록취소");
				long pbpay = Long.parseLong(t2.getText());
				if(cmp.equals(cmp2)==true)
				{
					int yesno=JOptionPane.WARNING_MESSAGE;
					code = JOptionPane.showInputDialog(null,"(정기권등록취소)차량번호를 입력해주세요","정기권 등록취소",yesno);
					if(yesno==JOptionPane.YES_OPTION)
					{
						delseason();
					}
				}
				else
				{
					payback();
				}
			}
			catch(NumberFormatException e1)
			{
				JOptionPane.showMessageDialog(null,new JLabel("환블금액에 숫자만 입력해주세요.", javax.swing.SwingConstants.CENTER),"Warning",JOptionPane.ERROR_MESSAGE);
			}
		}
		selected();
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
