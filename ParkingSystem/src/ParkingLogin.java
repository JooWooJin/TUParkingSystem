import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class ParkingLogin extends JFrame implements ActionListener {

	ParkingSystemAdmin ma;
	String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";
	JPanel cp = new JPanel(new GridLayout(4,1));
	JPanel p1 = new JPanel(new FlowLayout());
	JPanel p2 = new JPanel(new FlowLayout());
	JPanel p3 = new JPanel(new FlowLayout());
	JPanel p4 = new JPanel(new FlowLayout());
	JPanel p5 = new JPanel(new FlowLayout());
	JPanel ls1 = new JPanel(new BorderLayout());
	JLabel blk1 = new JLabel(" ");
	JLabel img = new JLabel();
	JLabel jaemok = new JLabel();
	JLabel state = new JLabel("패스워드를 입력해주세요.");
	JButton b = new JButton(new ImageIcon("login_btn1.png"));
	JPasswordField pass = new JPasswordField(20);
	public ParkingLogin() {
		pass.setBorder(null);
		jaemok.setIcon(new ImageIcon("login_txt.p ng"));
		b.setPreferredSize(new Dimension(200, 40));
		img.setIcon(new ImageIcon("login_bg.png"));
		p1.setBackground(new Color(38,38,38));
		p2.setBackground(new Color(38,38,38));
		p3.setBackground(new Color(38,38,38));
		p4.setBackground(new Color(38,38,38));
		p5.setBackground(new Color(38,38,38));
		cp.setBackground(new Color(38,38,38));
		blk1.setBackground(new Color(38,38,38));
		ls1.setBackground(new Color(38,38,38));
		//.setBackground(new Color(38,38,38));
		setBackground(new Color(38,38,38));
		setTitle("주차관리시스템 - 로그인창");
		setSize(350,500);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		jaemok.setFont(new Font("굴림체",Font.BOLD,25));
		jaemok.setForeground(new Color(255,255,255));
		b.setFont(new Font("굴림체",Font.BOLD,25));
		state.setFont(new Font("맑은 고딕",Font.PLAIN,18));
		pass.setFont(new Font("굴림체",Font.BOLD,20));
		state.setForeground(Color.GREEN);
		pass.addActionListener(this);
		b.addActionListener(this);
		p1.add(jaemok);
		p2.add(pass);
		p3.add(b);
		cp.add(p1);
		cp.add(p2);
		cp.add(p4);
		cp.add(p3);
		p4.add(state);
		p5.add(img);
		ls1.add(blk1,BorderLayout.NORTH);
		ls1.add(p5);
		add(ls1,BorderLayout.CENTER);
		add(cp,BorderLayout.SOUTH);

		setResizable(false);
		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UIManager.put("ComboBox.background", new ColorUIResource(new Color(38,38,38)));
		UIManager.put("ComboBox.foreground", new ColorUIResource(new Color(255,255,255)));
		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new Color(38,38,38)));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(255,255,255)));
		new ParkingLogin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement stmt = null;
		String comparepassword;
		if(e.getSource()==b || e.getSource()==pass)
		{
			String eq = new String(pass.getPassword());
			try{
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
				state.setForeground(Color.GREEN);
				state.setText("로그인중 입니다.");
				stmt = conn.createStatement();

				String sql;
				sql = "SELECT password FROM Important";
				ResultSet rs = stmt.executeQuery(sql);

				rs.next();
				comparepassword = rs.getString("password");
				if(eq.equals(comparepassword))
				{
					JOptionPane.showMessageDialog(null,new JLabel("로그인 성공", javax.swing.SwingConstants.CENTER),"Login Success",JOptionPane.PLAIN_MESSAGE);

					ma = new ParkingSystemAdmin();
					dispose();

				}
				else
				{
					JOptionPane.showMessageDialog(null,"비밀번호가 일치하지 않습니다." , "Password is not eqauls",JOptionPane.ERROR_MESSAGE);
					state.setText("패스워드가 일치하지않습니다.");
					state.setForeground(Color.RED);
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
				}
				catch(SQLException se2){
				}
				try{
					if(conn!=null)
						conn.close();
				}
				catch(SQLException se){
					se.printStackTrace();
				}
			}
		}
	}
}