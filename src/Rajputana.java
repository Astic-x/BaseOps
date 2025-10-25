import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.*;

public class Rajputana extends JFrame {

    public Rajputana() {
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 204));

        JLabel title = new JLabel("6TH RAJPUTANA  RIFLES");
        title.setFont(new Font("Stencil", Font.BOLD, 60));
        title.setForeground(new Color(0, 69, 0));
        title.setBounds(380, 60, 1200, 70);
        panel.add(title);

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(0, 210, 2000, 10);
        panel.add(sep1);

        JButton btnHome = createButton("HOME", 50, 240,200,140);
        JButton btnLeave = createButton("LEAVE MANAGER", 290, 240,400,150);
        JButton btnPersonnel = createButton("PERSONNEL DUTIES", 530, 240,350,140);
        JButton btnCompany = createButton("COMPANY DUTIES", 770, 240,350,150);
        JButton btnSearch = createButton("SEARCH", 1010, 240,350,150);
        JButton btnAbout = createButton("ABOUT", 1250, 240,350,150);
        panel.add(btnHome);
        panel.add(btnLeave);
        panel.add(btnPersonnel);
        panel.add(btnCompany);
        panel.add(btnAbout);
        panel.add(btnSearch);
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(0, 300, 2000, 10);
        panel.add(sep2);

        JLabel motto = new JLabel("VEER BHOGYA VASUNDHARA");
        motto.setFont(new Font("Vineta BT", Font.BOLD, 40));
        motto.setForeground(new Color(0, 64, 0));
        motto.setBounds(450, 400, 1500, 100);
        panel.add(motto);

        JLabel slogan = new JLabel(". LIVE A LIFE LESS ORDINARY .");
        slogan.setFont(new Font("Agency FB", Font.BOLD, 40));
        slogan.setBounds(550, 550, 700, 50);
        panel.add(slogan);

        JLabel labelAt = new JLabel("@CharlieSquad");
        labelAt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        labelAt.setForeground(new Color(0, 68, 0));
        labelAt.setBounds(10, 750, 200, 30);
        panel.add(labelAt);

        // Left Logo
        ImageIcon leftIcon = new ImageIcon("C:\\Users\\HP\\Downloads\\leftIcon.jpg");
        Image scaledLeftImage = leftIcon.getImage().getScaledInstance(200, 175, Image.SCALE_SMOOTH);
        JLabel leftLogoLabel = new JLabel(new ImageIcon(scaledLeftImage));
        leftLogoLabel.setBounds(10, 10, 200, 175); // Positioned at top-left with padding
        panel.add(leftLogoLabel);
        

        // Right logo
        ImageIcon rightIcon = new ImageIcon("C:\\Users\\HP\\Downloads\\rightIcon.jpg");
        Image scaledRightImage = rightIcon.getImage().getScaledInstance(225, 175, Image.SCALE_SMOOTH);
        JLabel rightLogoLabel = new JLabel(new ImageIcon(scaledRightImage));
        rightLogoLabel.setBounds(1600 - 305, 10, 225, 175); // Positioned at top-right
        panel.add(rightLogoLabel);
        

        this.setContentPane(panel);
        this.setSize(1600, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("6th Rajputana Rifles");
        this.setLocationRelativeTo(null);
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


    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Rajputana rajputana=new Rajputana();
            		rajputana.setVisible(true);
            rajputana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
}