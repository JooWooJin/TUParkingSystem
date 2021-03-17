
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

public class ParkingAdminPasswordChange extends JFrame implements ActionListener {


	String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
	String DB_URL = "jdbc:mariadb://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";
	JButton b = new JButton();//비밀번호\n변경
	JLabel state = new JLabel("비밀번호 변경창입니다");
	JLabel nowp = new JLabel("현재 비밀번호");
	JLabel changep = new JLabel("새 비밀번호");
	JLabel rep = new JLabel("새 비밀번호 재입력");
	JPanel mp = new JPanel(new GridLayout(5,1));
	JPanel p0 = new JPanel(new FlowLayout());
	JPanel p1 = new JPanel(new GridLayout(1,2));
	JPanel p2 = new JPanel(new GridLayout(1,2));
	JPanel p3 = new JPanel(new GridLayout(1,2));
	JPasswordField now = new JPasswordField(20);
	JPasswordField pass = new JPasswordField(20);
	JPasswordField repass = new JPasswordField(20);
	public ParkingAdminPasswordChange()
	{
		b.setBorder(null);
		b.setIcon(new ImageIcon("passwordchange_btn.png"));
		state.setOpaque(true);
		nowp.setOpaque(true);
		changep.setOpaque(true);
		rep.setOpaque(true);
		mp.setBackground(new Color(19,19,19));
		state.setBackground(new Color(38,38,38));
		nowp.setBackground(new Color(38,38,38));
		changep.setBackground(new Color(38,38,38));
		rep.setBackground(new Color(38,38,38));
		p0.setBackground(new Color(38,38,38));
		state.setForeground(new Color(54,163,247));
		nowp.setForeground(new Color(54,163,247));
		changep.setForeground(new Color(54,163,247));
		rep.setForeground(new Color(54,163,247));
		b.setFont(new Font("맑은 고딕",Font.BOLD,20));
		state.setFont(new Font("맑은 고딕",Font.BOLD,20));
		nowp.setFont(new Font("맑은 고딕",Font.BOLD,18));
		changep.setFont(new Font("맑은 고딕",Font.BOLD,18));
		rep.setFont(new Font("맑은 고딕",Font.BOLD,18));
		setTitle("주차관리시스템 - 관리자비밀번호변경");
		setSize(400,240);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		//UI
		repass.addActionListener(this);
		b.addActionListener(this);
		p0.add(state);
		p1.add(nowp);
		p1.add(now);
		p2.add(changep);
		p2.add(pass);
		p3.add(rep);
		p3.add(repass);
		mp.add(p0);
		mp.add(p1);
		mp.add(p2);
		mp.add(p3);
		mp.add(b);
		add(mp,BorderLayout.CENTER);

		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stu
		new ParkingAdminPasswordChange();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement stmt = null;
		String comparepassword;
		if(e.getSource()==b || e.getSource()==repass)
		{
			String eq = new String(now.getPassword());
			String np = new String(pass.getPassword());
			String rep = new String(repass.getPassword());
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String today = null;
			today = formatter.format(cal.getTime());
			Timestamp ts = Timestamp.valueOf(today);
			try{
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
				state.setText("Connecting SQL Server");
				state.setForeground(new Color(52,191,163));
				stmt = conn.createStatement();

				String sql;
				sql = "SELECT password FROM Important";
				ResultSet rs = stmt.executeQuery(sql);


				rs.next();
				comparepassword = rs.getString("password");
				if(eq.equals(comparepassword))
				{
					if(eq.equals(np))
					{
						JOptionPane.showMessageDialog(null,"현재 비밀번호와 새 비밀번호가 같습니다." , "The current password is the same as the new password",JOptionPane.ERROR_MESSAGE);
						state.setText("Password is not change");	
						state.setForeground(new Color(183,28,28));
					}
					if(np.equals(""))
					{
						JOptionPane.showMessageDialog(null,"비밀번호를 한글자이상입력해주세요." , "Do not make blank password",JOptionPane.ERROR_MESSAGE);
						state.setText("Do not make blank password");
						state.setForeground(new Color(183,28,28));
					}
					else if(np.equals(rep))
					{
						sql = "UPDATE Important SET password='"+np+"' WHERE password='"+ eq +"'";
						stmt.executeUpdate(sql);
						sql = "insert into history values('"+ts+"','비밀번호변경',null,null,null,null)";
						stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null,new JLabel("비밀번호 변경 완료", javax.swing.SwingConstants.CENTER),"Password Change Success",JOptionPane.PLAIN_MESSAGE);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null,"새 비밀번호가 일치하지 않습니다." , "New Password is not equals",JOptionPane.ERROR_MESSAGE);
						state.setText("New Password is not equals");
						state.setForeground(new Color(183,28,28));
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"현재 비밀번호가 일치하지 않습니다." , "Current Password is not equals",JOptionPane.ERROR_MESSAGE);
					state.setText("Current Password is not equals");
					state.setForeground(new Color(183,28,28));
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
	}
}
