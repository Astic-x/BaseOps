
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class about extends JFrame {

    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private JButton jButton13, jButton14, jButton15, jButton16, jButton17;
    private JLabel jLabel1, jLabel3;

    public about() {
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jButton13 = new JButton();
        jButton14 = new JButton();
        jButton15 = new JButton();
        jButton16 = new JButton();
        jButton17 = new JButton();
        jLabel1 = new JLabel();
        jLabel3 = new JLabel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("About - 6th Rajputana Rifles");

        jPanel1.setBackground(new Color(255, 255, 204));
        jPanel1.setLayout(null);

        // Text Area
        jTextArea1.setBackground(new Color(255, 255, 204));
        jTextArea1.setColumns(30);
        jTextArea1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        jTextArea1.setForeground(new Color(0, 67, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setText("Nicknamed the Param Vir Chakra Paltan and Shooting Sixth, the regiment was raised in 1940\n"
                + "by Lieutenant Colonel NG Gane MC at Faizabad. Theatre honour Burma during World War II.\n"
                + "CHM Piru Singh was awarded the PVC during the Indo-Pakistani War of 1947–1948.\n"
                + "The battalion was awarded the theatre honour Jammu and Kashmir and battle honour Darapari.\n\n"
                + "During World War II, the regiment was expanded to thirteen battalions and served in the Middle\n"
                + "East, Burma, and Malaya. The 4th Battalion (Outram's) had the distinction of earning two\n"
                + "Victoria Crosses during that conflict by Subedar Richhpal Ram and Company Havildar-Major Chhelu Ram.\n"
                + "In 1945, the numeral \"6th\" was discarded and eventually the regiment would be allocated to the new\n"
                + "Indian Army after independence in 1947 as the Rajputana Rifles.\n\n"
                + "He was 41 years old, and a Subedar in the 6th Rajputana Rifles, in the Indian Army during\n"
                + "World War II when the following deed took place for which he was awarded the VC during the Battle of Keren.");
        jScrollPane1.setViewportView(jTextArea1);
        jScrollPane1.setBounds(80, 120, 900, 400);
        jPanel1.add(jScrollPane1);

        // Buttons (Navigation)
        Font btnFont = new Font("Times New Roman", Font.BOLD, 18);
        Dimension btnSize = new Dimension(200, 40);

        JButton btnHome = createButton("HOME", 50, 50,200,140);
        JButton btnLeave = createButton("LEAVE MANAGER", 290, 50,400,150);
        JButton btnPersonnel = createButton("PERSONNEL DUTIES", 530, 50,350,140);
        JButton btnCompany = createButton("COMPANY DUTIES", 770, 50,350,150);
        JButton btnSearch = createButton("SEARCH", 1010, 50,350,150);
        JButton btnAbout = createButton("ABOUT", 1250, 50,350,150);

        jPanel1.add(btnHome);
        jPanel1.add(btnLeave);
        jPanel1.add(btnPersonnel);
        jPanel1.add(btnCompany);
        jPanel1.add(btnSearch);
        jPanel1.add(btnAbout);

        // Left image
        jLabel1.setIcon(new ImageIcon("images/WhatsApp Image 2025-05-19 at 14.47.03.jpeg"));
        jLabel1.setBounds(10, 120, 150, 220);
        jPanel1.add(jLabel1);

        ImageIcon leftIcon = new ImageIcon("C:\\Users\\HP\\Downloads\\about.jpg");
        Image scaledLeftImage = leftIcon.getImage().getScaledInstance(450, 600, Image.SCALE_SMOOTH);
        JLabel leftLogoLabel = new JLabel(new ImageIcon(scaledLeftImage));
        leftLogoLabel.setBounds(1000, 120, 450, 600); // Positioned at top-left with padding
        jPanel1.add(leftLogoLabel);

        add(jPanel1);
        setSize(1800, 700);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        // Dummy action handlers
        jButton13.addActionListener(e -> JOptionPane.showMessageDialog(this, "LEAVE MANAGER clicked"));
        jButton14.addActionListener(e -> JOptionPane.showMessageDialog(this, "PERSONNEL DUTIES clicked"));
        jButton15.addActionListener(e -> JOptionPane.showMessageDialog(this, "HOME clicked"));
        jButton16.addActionListener(e -> JOptionPane.showMessageDialog(this, "COMPANY DUTY clicked"));
        jButton17.addActionListener(e -> JOptionPane.showMessageDialog(this, "ABOUT clicked"));
    }

    private JButton createButton(String text, int x, int y,int width , int length) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 225, 40);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(new Color(0, 51, 0));
        button.setForeground(Color.WHITE);
        button.addActionListener((ActionEvent e) -> {
        	if(text=="HOME")
        	{
        		Rajputana ob = new Rajputana();
            	ob.setVisible(true);
            	ob.setExtendedState(JFrame.MAXIMIZED_BOTH);
            	this.dispose();
        	}
        	else if(text=="COMPANY DUTIES")
        	{
        		companyP ob;
        		
				try {
					ob = new companyP();
					ob.setVisible(true);
					ob.setExtendedState(JFrame.MAXIMIZED_BOTH);
		        	this.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	
        	}
        	else if(text=="ABOUT")
        	{
        		about ob = new about();
            	ob.setVisible(true);
            	ob.setExtendedState(JFrame.MAXIMIZED_BOTH);
            	this.dispose();
        	}
        	else if(text=="SEARCH")
        	{
        		search ob = new search();
            	ob.setVisible(true);
            	ob.setExtendedState(JFrame.MAXIMIZED_BOTH);
            	this.dispose();
        	}
        	else if(text=="PERSONNEL DUTIES")
        	{
        		PersonnelGUI ob = new PersonnelGUI();
            	ob.setVisible(true);
            	ob.setExtendedState(JFrame.MAXIMIZED_BOTH);
            	this.dispose();
        	}
        	else if(text=="LEAVE MANAGER")
        	{
        		leave ob = new leave();  
        		ob.setVisible(true);
        		ob.setExtendedState(JFrame.MAXIMIZED_BOTH);
            	this.dispose();
            }
        });
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{ 
        about about1 = new about();
        about1.setVisible(true);
        about1.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
}
