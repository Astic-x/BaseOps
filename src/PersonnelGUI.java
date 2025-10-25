
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonnelGUI extends JFrame{

	private static final String DB_URL = "jdbc:mysql://localhost:3306/military_duty";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "asticX";
    
    private JPanel jPanel1;
    private JButton btnHome, btnLeaveManager, btnPersonnelDuties, btnCompanyDuties, btnAbout;
    private JComboBox<String> comboCompany;
    private JLabel labelChooseCompany;
    private JSeparator separator;
    String selected;
    public PersonnelGUI() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2000, 900); // match the layout size
        setLocationRelativeTo(null); // center the frame

        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(255, 255, 204));
        jPanel1.setLayout(null);

        Font buttonFont = new Font("Times New Roman", Font.BOLD, 20);
        Color buttonBg = new Color(0, 51, 0);
        Color buttonFg = Color.WHITE;

        JButton btnHome = createButton("HOME", 50, 50,200,140,selected);
        JButton btnLeave = createButton("LEAVE MANAGER", 290, 50,400,150,selected);
        JButton btnPersonnel = createButton("PERSONNEL DUTIES", 530, 50,350,140,selected);
        JButton btnCompany = createButton("COMPANY DUTIES", 770, 50,350,150,selected);
        JButton btnSearch = createButton("SEARCH", 1010, 50,350,150,selected);
        JButton btnAbout = createButton("ABOUT", 1250, 50,350,150,selected);

        jPanel1.add(btnHome);
        jPanel1.add(btnLeave);
        jPanel1.add(btnPersonnel);
        jPanel1.add(btnCompany);
        jPanel1.add(btnSearch);
        jPanel1.add(btnAbout);

        labelChooseCompany = new JLabel("Choose Company");
        labelChooseCompany.setFont(new Font("Times New Roman", Font.BOLD, 30));
        labelChooseCompany.setForeground(new Color(0, 62, 0));
        labelChooseCompany.setBounds(20, 150, 300, 40);
        jPanel1.add(labelChooseCompany);

        comboCompany = new JComboBox<>(new String[]{
                "Alpha Company", "Bravo Company", "Charlie Company",
                "Delta Company", "HQ Company", "Support Company"
        });
        
        comboCompany.setFont(buttonFont);
        comboCompany.setBackground(buttonBg);
        comboCompany.setForeground(buttonFg);
        comboCompany.setBounds(20, 200, 280, 30);
        jPanel1.add(comboCompany);
        
        JButton btnAssign = createButton("Assign Duties", 20, 250,350,150,selected);
        jPanel1.add(btnAssign);
        
        JButton btnView = createButton("View Duties", 20, 300,350,150,selected);
        jPanel1.add(btnView);
        
        // Left Logo
        ImageIcon leftIcon = new ImageIcon("C:\\Users\\HP\\Downloads\\search.jpg");
        Image scaledLeftImage = leftIcon.getImage().getScaledInstance(420, 480, Image.SCALE_SMOOTH);
        JLabel leftLogoLabel = new JLabel(new ImageIcon(scaledLeftImage));
        leftLogoLabel.setBounds(20, 350, 420, 480); // Positioned at top-left with padding
        jPanel1.add(leftLogoLabel);
        
        


        separator = new JSeparator();
        separator.setBounds(0, 130, 2000, 10);
        separator.setForeground(Color.BLACK);
        jPanel1.add(separator);

        add(jPanel1);
    }
    
    private JButton createButton(String text, int x, int y,int width , int length,String selecetd) {
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
        	else if(text=="Assign Duties")
        	{
        		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            		
            		MilitaryDutySystem main = new MilitaryDutySystem();
            		main.assign(connection, selected);
            		makeTable(jPanel1,selected);
        		} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        	else if(text=="View Duties")
        	{
        		selected = (String) comboCompany.getSelectedItem();
				System.out.println("Selected: " + selected);
				makeTable(jPanel1,selected);
            }
        });
        return button;
    }
    
    private void makeTable(JPanel jPanel1, String company) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            MilitaryDutySystem main = new MilitaryDutySystem();
            List<Personnel> assignedPersonnel = main.findAssignedPersonnel(connection, company);
            

            // Remove only existing table components
            Component[] components = jPanel1.getComponents();
            for (Component component : components) {
                if (component instanceof JScrollPane || component instanceof JTable) {
                    jPanel1.remove(component);
                }
            }

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table non-editable
                }
            };

            // Set column names
            model.setColumnIdentifiers(new String[]{"RANK", "NAME", "ASSIGNED DUTY"});

            JTable table = new JTable(model);
            // Maintain your exact positioning
            table.setBounds(670, 200, 800, 450);
            
            // Improved styling
            table.setFont(new Font("Times New Roman", Font.BOLD, 20));
            table.setRowHeight(50); // More compact row height
            table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
            
            // Set column widths
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(150); // Rank
            table.getColumnModel().getColumn(1).setPreferredWidth(250); // Name
            table.getColumnModel().getColumn(2).setPreferredWidth(400); // Duty
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

            // Add data without extra spacing
            for (Personnel personnel : assignedPersonnel) {
                model.addRow(new Object[]{
                    personnel.getRank(),
                    personnel.getName(),
                    personnel.getCurrentDuty()
                });
            }

            // Wrap in scroll pane while maintaining position
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(table.getBounds());
            
            // Add to panel without affecting other components
            jPanel1.add(scrollPane);
            jPanel1.setComponentZOrder(scrollPane, 0); // Bring to front
            
            // Refresh display
            jPanel1.revalidate();
            jPanel1.repaint();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading personnel data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PersonnelGUI().setVisible(true);
        });
    }

}