import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class ParkingAuto extends JFrame implements ActionListener,MouseListener {


	
	String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	String DB_URL = "jdbc:mysql://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "ehdgus12";


	int x,y;
	int flr=0,siz=0;
	int s1=0,s2=0,m1=0,m2=0,l1=0,l2=0;
	int tower[][] = new int[100][100]; 
	int checking=0;
	String[] sizeofcar = {"소형", "중형" ,"대형"} ;
	JComboBox carsize;
	JLabel inputname = new JLabel("차주 성함");
	JLabel inputcarname = new JLabel("차량 번호");
	JLabel inputcarsize = new JLabel("차량크기");
	JLabel inputcar = new JLabel("차종");
	JLabel season = new JLabel("정기권여부");
	JLabel OX = new JLabel("X");
	JLabel seasoncheck = new JLabel("검색"); //
	JTextField inname = new JTextField(20); //차주성함
	JTextField incarname = new JTextField(20); //차량번호
	JTextField icar = new JTextField(20); //차종
	JButton b = new JButton(); //입력된 정보로 자동주차 시간 차량위치등록할때 넣어야됨!
	JPanel ctOX = new JPanel(new FlowLayout());
	JPanel ctcheck = new JPanel(new FlowLayout());
	JPanel mp = new JPanel(new GridLayout(6,1));
	JPanel p1 = new JPanel(new GridLayout(1,2));
	JPanel p2 = new JPanel(new GridLayout(1,2));
	JPanel p3 = new JPanel(new GridLayout(1,2));
	JPanel p4 = new JPanel(new GridLayout(1,2));
	JPanel p5 = new JPanel(new GridLayout(1,2));
	JPanel p6 = new JPanel(new GridLayout(1,2));


	void getInformation()
	{
		Connection conn = null;
		Statement stmt = null;
		String nam = new String(inname.getText());
		String carnam = new String(incarname.getText());
		String ca = new String(icar.getText());

		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();  

			String sql = "SELECT * from Important";
			ResultSet rs = stmt.executeQuery(sql);

			rs.next();
			flr=rs.getInt("floor");
			siz=rs.getInt("place");
			s1=rs.getInt("small1");
			s2=rs.getInt("small2");
			m1=rs.getInt("middle1");
			m2=rs.getInt("middle2");
			l1=rs.getInt("large1");
			l2=rs.getInt("large2");



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
	public ParkingAuto()
	{
		//그래픽
		mp.setBackground(new Color(38,38,38));
		p1.setBackground(new Color(38,38,38));
		p2.setBackground(new Color(38,38,38));
		p3.setBackground(new Color(38,38,38));
		p4.setBackground(new Color(38,38,38));
		p5.setBackground(new Color(38,38,38));
		p6.setBackground(new Color(38,38,38));
		b.setIcon(new ImageIcon("Auto_btn.png"));
		b.setBorder(null);
		inputname.setOpaque(true);
		inputname.setBackground(new Color(38,38,38));
		inputname.setForeground(new Color(255,255,255));
		inputcarname.setOpaque(true);
		inputcarname.setBackground(new Color(38,38,38));
		inputcarname.setForeground(new Color(255,255,255));
		inputcarsize.setOpaque(true);
		inputcarsize.setBackground(new Color(38,38,38));
		inputcarsize.setForeground(new Color(255,255,255));
		inputcar.setOpaque(true);
		inputcar.setBackground(new Color(38,38,38));
		inputcar.setForeground(new Color(255,255,255));
		season.setOpaque(true);
		season.setBackground(new Color(38,38,38));
		season.setForeground(new Color(255,255,255));
		seasoncheck.setOpaque(true);
		seasoncheck.setBackground(new Color(38,38,38));
		seasoncheck.setForeground(new Color(255,255,255));
		OX.setOpaque(true);
		OX.setBackground(new Color(38,38,38));
		OX.setForeground(new Color(255,255,255));
		ctOX.setBackground(new Color(38,38,38));
		ctcheck.setBackground(new Color(38,38,38));
		inputname.setFont(new Font("맑은 고딕",Font.BOLD,18));
		inputcarname.setFont(new Font("맑은 고딕",Font.BOLD,18));
		inputcarsize.setFont(new Font("맑은 고딕",Font.BOLD,18));
		inputcar.setFont(new Font("맑은 고딕",Font.BOLD,18));
		inname.setFont(new Font("맑은 고딕",Font.BOLD,18));
		incarname.setFont(new Font("맑은 고딕",Font.BOLD,18));
		icar.setFont(new Font("맑은 고딕",Font.BOLD,18));
		b.setFont(new Font("맑은 고딕",Font.BOLD,18));
		season.setFont(new Font("맑은 고딕",Font.BOLD,18));
		seasoncheck.setFont(new Font("맑은 고딕",Font.BOLD,18));
		OX.setFont(new Font("맑은 고딕",Font.BOLD,18));
		getInformation();
		setTitle("주차관리시스템 - 차량주차");
		setSize(300,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		//UI설정
		carsize = new JComboBox(sizeofcar);
		carsize.setFont(new Font("맑은 고딕",Font.BOLD,18));
		b.addActionListener(this);
		p1.add(inputname);
		p1.add(inname);
		p2.add(inputcarname);
		p2.add(incarname);
		p3.add(inputcar);
		p3.add(icar);
		p4.add(inputcarsize);
		p4.add(carsize);
		p5.add(season);
		p5.add(p6);
		ctOX.add(OX);
		ctcheck.add(seasoncheck);
		seasoncheck.addMouseListener(this);
		p6.add(ctOX);
		p6.add(ctcheck);
		mp.add(p1);
		mp.add(p2);
		mp.add(p3);
		mp.add(p5);
		mp.add(p4);
		mp.add(b);
		add(mp,BorderLayout.CENTER);


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
		new ParkingAuto();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub




		Connection conn = null;
		Statement stmt = null;
		String nam = new String(inname.getText());
		String carnam = new String(incarname.getText());
		String ca = new String(icar.getText());

		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();  

			String sql = "SELECT * from maindata";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				x=Integer.parseInt(rs.getString("carfloor"));
				y=Integer.parseInt(rs.getString("caradd"));
				tower[x][y]=1;
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


		if(!(nam.equals("")) && !(carnam.equals("")) && !(ca.equals("")))
		{
			try{
				String ticket = new String("X");
				int x = 0,y = 0;
				int tempo=0;
				int i,j;
				int checking=0;
				int scar=0;
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				String today = null;
				today = formatter.format(cal.getTime());
				Timestamp ts = Timestamp.valueOf(today);
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT * from maindata";
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next())
				{
					x=Integer.parseInt(rs.getString("carfloor"));
					y=Integer.parseInt(rs.getString("caradd"));
					tower[x][y]=1;
				}

				rs.close();

				sql="select * from season where carname = '" + carnam + "'";
				rs = stmt.executeQuery(sql);
				while(rs.next())
				{
					String cck = rs.getString("carname");
					Timestamp cktm = rs.getTimestamp("last");
					if(cck.equals(carnam) && cktm.after(ts))
					{
						tempo=1;
						ticket="O";
						break;
					}
				}
				if(tempo==0)
				{
					ticket="X";
				}
				sql="select * from maindata where carname = '" + carnam + "'";
				rs = stmt.executeQuery(sql);
				while(rs.next())
				{
					String cck = rs.getString("carname");
					if(cck.equals(carnam))
					{
						scar=1;
						break;
					}
				}
				if(scar==1)
				{
					JOptionPane.showMessageDialog(null,"해당 차량은 이미 주차된 차량입니다." , "The car is already parked.",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(carsize.getSelectedItem().equals("대형"))
					{
						if(checking==0)
						{
							for(i=l1;i<=l2;i++)
							{
								if(checking==0)
								{
									for(j=1;j<=siz;j++)
									{
										if(checking==0)
										{
											if(tower[i][j]==0)
											{
												x=i;
												y=j;
												tower[i][j]=1;
												checking=1;
												break;
											}
										}
									}
								}
							}
						}
						if(x!=0 && y!=0)
						{
							sql = "INSERT INTO maindata values('" + ts + "','" + nam + "','" + (String)(carsize.getSelectedItem()) + "','" + ca + "','" + carnam + "','" + ticket + "','" + Integer.toString(x) +  "','" + Integer.toString(y) + "')";
							stmt.executeUpdate(sql);
							sql = "INSERT INTO history values('" + ts + "','주차'," + Integer.toString(x) + "," + Integer.toString(y) + "," + "null" + ",'" + carnam + "'" + ");";
							stmt.executeUpdate(sql);
							if(checking==1)
							{
								sql = "INSERT INTO carlist values('" + ca + "','대형');";
								stmt.executeUpdate(sql);
							}
							else
							{
								sql = "UPDATE carlist SET carsize='대형' WHERE car='"+ ca +"'";
								stmt.executeUpdate(sql);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"현재 주차가능한공간이 없습니다" , "Do not have any parking place",JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(carsize.getSelectedItem().equals("중형"))
					{
						if(checking==0)
						{
							for(i=m1;i<=m2;i++)
							{
								if(checking==0)
								{
									for(j=1;j<=siz;j++)
									{
										if(checking==0)
										{
											if(tower[i][j]==0)
											{
												x=i;
												y=j;
												tower[i][j]=1;
												checking=1;
												break;
											}
										}
									}
								}
							}
						}
						if(x!=0 && y!=0)
						{
							sql = "INSERT INTO maindata values('" + ts + "','" + nam + "','" + (String)(carsize.getSelectedItem()) + "','" + ca + "','" + carnam + "','" + ticket + "','" + Integer.toString(x) +  "','" + Integer.toString(y) + "')";
							stmt.executeUpdate(sql);
							sql = "INSERT INTO history values('" + ts + "','주차'," + Integer.toString(x) + "," + Integer.toString(y) + "," + "null" + ",'" + carnam + "'" + ");";
							stmt.executeUpdate(sql);
							if(checking==1)
							{
								sql = "INSERT INTO carlist values('" + ca + "','중형');";
								stmt.executeUpdate(sql);
							}
							else
							{
								sql = "UPDATE carlist SET carsize='중형' WHERE car='"+ ca +"'";
								stmt.executeUpdate(sql);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"현재 주차가능한공간이 없습니다" , "Do not have any parking place",JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(carsize.getSelectedItem().equals("소형"))
					{
						if(checking==0)
						{
							for(i=s1;i<=s2;i++)
							{
								if(checking==0)
								{
									for(j=1;j<=siz;j++)
									{
										if(checking==0)
										{
											if(tower[i][j]==0)
											{
												x=i;
												y=j;
												tower[i][j]=1;
												checking=1;
												break;
											}
										}
									}
								}
							}
						}
						if(x!=0 && y!=0)
						{
							sql = "INSERT INTO maindata values('" + ts + "','" + nam + "','" + (String)(carsize.getSelectedItem()) + "','" + ca + "','" + carnam + "','" + ticket + "','" + Integer.toString(x) +  "','" + Integer.toString(y) + "')";
							stmt.executeUpdate(sql);
							sql = "INSERT INTO history values('" + ts + "','주차'," + Integer.toString(x) + "," + Integer.toString(y) + "," + "null" + ",'" + carnam + "'" + ");";
							stmt.executeUpdate(sql);
							if(checking==1)
							{
								sql = "INSERT INTO carlist values('" + ca + "','소형');";
								stmt.executeUpdate(sql);
							}
							else
							{
								sql = "UPDATE carlist SET carsize='소형' WHERE car='"+ ca +"'";
								stmt.executeUpdate(sql);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"현재 주차가능한공간이 없습니다" , "Do not have any parking place",JOptionPane.ERROR_MESSAGE);
						}
					}
					stmt.close();
					conn.close();
					dispose();
				}
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
			JOptionPane.showMessageDialog(null,"정확한 정보를 입력해주세요." , "Please put in correct information",JOptionPane.ERROR_MESSAGE);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		String nam = new String(inname.getText());
		String carnam = new String(incarname.getText());
		String ca = new String(icar.getText());

		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();  

			String sql = "SELECT carname from season where carname='"+carnam+"'";
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next())
			{
				 OX.setText("O");
				 JOptionPane.showMessageDialog(null,new JLabel("해당 차량은 정기권차량입니다.", javax.swing.SwingConstants.CENTER),"This car is seasonticket",JOptionPane.PLAIN_MESSAGE);
			}
			else
			{
				OX.setText("X");
				JOptionPane.showMessageDialog(null,"해당 차량은 정기권차량이 아닙니다." , "This car don't have seasonticket",JOptionPane.ERROR_MESSAGE);
			}
			rs.close();
			
			sql = "SELECT * from carlist where car='"+ca+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				checking=1;
				Object select =rs.getString("carsize");
				carsize.setSelectedItem(select);
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
