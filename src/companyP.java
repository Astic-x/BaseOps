
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
//Company Class
import java.util.List;




public class companyP extends JFrame {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/military_duty";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "asticX";
    
    private JPanel jPanel1;
    private JLabel jLabel1, jLabel2;
    private JSeparator jSeparator1;

    public companyP() throws SQLException {
        initComponents();
    }

    private void initComponents() throws SQLException {
    	
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        
        jSeparator1 = new JSeparator();
        
    		 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setBackground(new Color(255, 255, 204));
        jPanel1.setLayout(null);

        jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 36));
        jLabel1.setForeground(new Color(0, 62, 0));
        jLabel1.setText("6th RAJRIF HAS 6 COMPANIES");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(460, 130, 700, 42);
        
		makeTable(jPanel1);
		
        JButton btnRotate = createButton("ROTATE DUTIES", 850, 680,350,150,jPanel1);
        jPanel1.add(btnRotate);

        JButton btnHome = createButton("HOME", 50, 50,200,140,jPanel1);
        JButton btnLeave = createButton("LEAVE MANAGER", 290, 50,400,150,jPanel1);
        JButton btnPersonnel = createButton("PERSONNEL DUTIES", 530, 50,350,140,jPanel1);
        JButton btnCompany = createButton("COMPANY DUTIES", 770, 50,350,150,jPanel1);
        JButton btnSearch = createButton("SEARCH", 1010, 50,350,150,jPanel1);
        JButton btnAbout = createButton("ABOUT", 1250, 50,350,150,jPanel1);

        jPanel1.add(btnHome);
        jPanel1.add(btnLeave);
        jPanel1.add(btnPersonnel);
        jPanel1.add(btnCompany);
        jPanel1.add(btnSearch);
        jPanel1.add(btnAbout);
        
        // Left Logo
        ImageIcon leftIcon = new ImageIcon("C:\\Users\\HP\\Downloads\\company.jpg");
        Image scaledLeftImage = leftIcon.getImage().getScaledInstance(450, 600, Image.SCALE_SMOOTH);
        JLabel leftLogoLabel = new JLabel(new ImageIcon(scaledLeftImage));
        leftLogoLabel.setBounds(15, 170, 450, 600); // Positioned at top-left with padding
        jPanel1.add(leftLogoLabel);
        

        jSeparator1.setForeground(new Color(0, 51, 0));
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(420, 110, 1240, 10);

        getContentPane().add(jPanel1);
        setSize(1700, 1000); // Adjust to fit full GUI
        setLocationRelativeTo(null); // Center the window
    }
    
    private JButton createButton(String text, int x, int y,int width , int length,JPanel jPanel1) {
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
        	else if(text=="ROTATE DUTIES")
        	{
        		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        			MilitaryDutySystem main1 = new MilitaryDutySystem();
        			main1.rotate(connection);
        			makeTable(jPanel1);
        		} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
            }
        });
        return button;
    }

    private void makeTable(JPanel mainPanel) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            MilitaryDutySystem main = new MilitaryDutySystem();
            List<Company> companies = main.findCompanies(connection);
            PersonnelService personnelService = new PersonnelService(connection);
            // Remove only the existing table if it exists
            Component[] components = mainPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JScrollPane) {
                    mainPanel.remove(component);
                }
            }

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Set column names
            model.setColumnIdentifiers(new String[]{"COMPANY", "TOTAL PERSONNEL","ASSIGNED PERSONNEL", "ASSIGNED DUTY"});

            JTable table = new JTable(model);
            table.setFont(new Font("Times New Roman", Font.BOLD, 20));
            table.setRowHeight(50);
            table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
            
            // Set column widths
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(230);
            table.getColumnModel().getColumn(3).setPreferredWidth(350);
            
            // Center-align personnel numbers
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

            // Add data
            for (Company company : companies) {
            	List<Personnel> allPersonnel = personnelService.getPersonnelByCompany(company.getId());
            	List<Personnel> assignPersonnel = personnelService.getPersonnelWithDuties(company.getId());
                model.addRow(new Object[]{
                    company.getName(),
                    allPersonnel.size(),
                    assignPersonnel.size(),
                    company.getCurrentAssignedDuty()
                });
            }

            // Create scroll pane with your exact positioning
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(525, 250, 932, 350);
            
            // Add the scroll pane without changing other components
            mainPanel.add(scrollPane);
            mainPanel.setComponentZOrder(scrollPane, 0); // Bring to front
            
            // Refresh the panel
            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading company data: " + e.getMessage(),
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
    	SwingUtilities.invokeLater(() -> {
            try {
				new companyP().setVisible(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
    }
}