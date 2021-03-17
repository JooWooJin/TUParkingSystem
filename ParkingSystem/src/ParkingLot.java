import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ParkingLot extends JFrame implements ActionListener {

	String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
	String DB_URL = "jdbc:mariadb://localhost:3306/parkingsystem?serverTimezone=Asia/Seoul";

	String USERNAME = "root";
	String PASSWORD = "root";
	
	JPanel floor[] = new JPanel[100];
	CardLayout card = new CardLayout();
	JPanel p[][] = new JPanel[100][100];//Ãþ / ÁÖÂ÷°ø°£
	JLabel lb[][] = new JLabel[100][100];
	JLabel lb2[][] = new JLabel[100][100];
	JLabel lb3[][] = new JLabel[100][100];
	JPanel pow[] = new JPanel[100];
	JPanel cl = new JPanel();
	JScrollPane sp[] = new JScrollPane[100];
	//String input[] = new String[15]; 
	JComboBox comb = new JComboBox();

	int flr=0,siz=0;
	int i=1;
	int j=1;
	void getInformation()
	{
		Connection conn = null;
		Statement stmt = null;

		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();  

			String sql = "SELECT * from Important";
			ResultSet rs = stmt.executeQuery(sql);

			rs.next();
			flr=rs.getInt("floor");
			siz=rs.getInt("place");



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
	void loading()
	{

		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT * FROM maindata";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				int x=Integer.parseInt(rs.getString("carfloor"));
				int y=Integer.parseInt(rs.getString("caradd"));
				String carname = rs.getString("carname");
				String ticket = rs.getString("season");
				String carsize = rs.getString("carsize");
				lb[x][y].setText("Â÷·®¹øÈ£ : "+carname);
				lb2[x][y].setText("Á¤±â±Ç : " + ticket + " Â÷Á¾ : "+carsize);
				lb3[x][y].setText("ÁÖÂ÷À§Ä¡ : (" +x + "F) " + y + "");
				lb[x][y].setBackground(new Color(52,191,163));
				lb[x][y].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,15));
				lb2[x][y].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,15));
				lb[x][y].setBackground(new Color(52,191,163));
				lb2[x][y].setBackground(new Color(52,191,163));
				lb3[x][y].setBackground(new Color(52,191,163));
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
	public ParkingLot()
	{
		getInformation();
		cl.setLayout(card);
		setSize(1000,800);		
		setLocationRelativeTo(null);
		setTitle("ÁÖÂ÷°ü¸®½Ã½ºÅÛ-Ãþº° Â÷·®ÇöÈ²");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		for (int i = 0; i <= flr; i++) {
			floor[i] = new JPanel();
			floor[i].setPreferredSize(new Dimension(950, 35*siz));
			sp[i] = new JScrollPane(floor[i]);
			floor[i].setLayout(new GridLayout(siz/5,5));
			floor[i].setBackground(new Color(183,28,28));
			for (int j = 0; j <= siz; j++) {
				p[i][j] = new JPanel();
				lb[i][j] = new JLabel();
				lb[i][j].setOpaque(true);
				lb[i][j].setBackground(new Color(183,28,28));
				lb[i][j].setText("NO car here");
				lb[i][j].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,18));
				lb2[i][j] = new JLabel();
				lb2[i][j].setOpaque(true);
				lb2[i][j].setBackground(new Color(183,28,28));
				lb2[i][j].setText("Empty place");
				lb2[i][j].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,18));
				lb3[i][j] = new JLabel();
				lb3[i][j].setOpaque(true);
				lb3[i][j].setBackground(new Color(183,28,28));
				lb3[i][j].setText("("+i+"F) "+j);
				lb3[i][j].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,18));
			}
		}
		for(i=1;i<=flr;i++)
		{
			comb.addItem(i+"Ãþ");
			comb.setFont(new Font("¸¼Àº °íµñ", Font.BOLD,18));
			for(j=1;j<=siz;j++)
			{
				p[i][j] = new JPanel(new GridLayout(3,1));
				p[i][j].add(lb[i][j]);
				p[i][j].add(lb2[i][j]);
				p[i][j].add(lb3[i][j]);
				floor[i].add(p[i][j]);
			}
		}
		for(i=1;i<=flr;i++)
		{
			cl.add(sp[i],i+"Ãþ");
		}
		loading();
		comb.addActionListener(this);
		add(comb,BorderLayout.NORTH);
		add(cl,BorderLayout.CENTER);

		ImageIcon img = new ImageIcon("TUPK.png");
        setIconImage(img.getImage());
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ParkingLot();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int i=1;
		for(i=1;i<=flr;i++)
		{
			if(comb.getSelectedItem().equals(i+"Ãþ"))
			{
				card.show(cl, i+"Ãþ");
			}
		}
	}
}
