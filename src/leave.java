import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class leave extends JFrame {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/military_duty";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "asticX";
    
    public leave() {
        initComponents();
    }

    private void initComponents() {
        // Main frame settings
        setTitle("Leave Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel with background color
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 204));
        mainPanel.setLayout(new BorderLayout());

        // Top navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(255, 255, 204));
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Navigation buttons
        JButton btnHome = createButton("HOME", 50, 50,200,140);
        JButton btnLeave = createButton("LEAVE MANAGER", 290, 50,400,150);
        JButton btnPersonnel = createButton("PERSONNEL DUTIES", 530, 50,350,140);
        JButton btnCompany = createButton("COMPANY DUTIES", 770, 50,350,150);
        JButton btnSearch = createButton("SEARCH", 1010, 50,350,150);
        JButton btnAbout = createButton("ABOUT", 1250, 50,350,150);

        navPanel.add(btnHome);
        navPanel.add(btnLeave);
        navPanel.add(btnPersonnel);
        navPanel.add(btnCompany);
        navPanel.add(btnSearch);
        navPanel.add(btnAbout);

       
        // Title label
        JLabel titleLabel = new JLabel("LEAVE DETAILS");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 73, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(0, 51, 0));

        // Form panel for leave details
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBackground(new Color(255, 255, 204));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // Form labels
        JLabel personnelIdLabel = createFormLabel("Personnel ID :", 19);
        JLabel startDateLabel = createFormLabel("Start Date :", 19);
        JLabel endDateLabel = createFormLabel("End Date :", 19);
        JLabel reasonLabel = createFormLabel("Reason :", 19);

        // Compact text fields
        JTextField personnelIdField = createTextField(15, 14);
        JTextField startDateField = createTextField(15, 14);
        JTextField endDateField = createTextField(15, 14);
        JTextField reasonField = createTextField(15, 14);

        // Add components to form panel
        formPanel.add(personnelIdLabel);
        formPanel.add(personnelIdField);
        formPanel.add(startDateLabel);
        formPanel.add(startDateField);
        formPanel.add(endDateLabel);
        formPanel.add(endDateField);
        formPanel.add(reasonLabel);
        formPanel.add(reasonField);

       

        

        // Text area with scroll pane
        
        // Panel for text area
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setBackground(new Color(255, 255, 204));
        
        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(new Color(255, 255, 204));
        
        JButton addLeaveButton = createActionButton("ADD LEAVE", 150, 30,textAreaPanel,personnelIdField,startDateField,endDateField,reasonField);
        JButton cancelLeaveButton = createActionButton("CANCEL LEAVE", 190, 30,textAreaPanel,personnelIdField,startDateField,endDateField,reasonField);
        JButton viewButton = createActionButton("VIEW", 90, 30,textAreaPanel,personnelIdField,startDateField,endDateField,reasonField);
        
        actionPanel.add(addLeaveButton);
        actionPanel.add(cancelLeaveButton);
        actionPanel.add(viewButton);
        
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 255, 204));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components to content panel
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(separator);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(actionPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(textAreaPanel);

        // Left Logo
        ImageIcon leftIcon = new ImageIcon("C:\\Users\\HP\\Downloads\\leave3.jpg");
        Image scaledLeftImage = leftIcon.getImage().getScaledInstance(250, 225, Image.SCALE_SMOOTH);
        JLabel leftLogoLabel = new JLabel(new ImageIcon(scaledLeftImage));
        leftLogoLabel.setBounds(10, 550, 250, 225); // Positioned at top-left with padding
        mainPanel.add(leftLogoLabel);
        
        
        // Add panels to main panel
        mainPanel.add(navPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, FlowLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
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


    private JButton createActionButton(String text, int width, int height,JPanel textAreaPanel, JTextField personnelIdField,JTextField startDateField,JTextField endDateField,JTextField reasonField) {
        JButton button = new JButton(text) {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 30, 0));
                g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
                g2.dispose();
            }
        };
        button.setBackground(new Color(0, 51, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Times New Roman", Font.BOLD, 19));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener((ActionEvent e) -> {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                PersonnelService personnelService = new PersonnelService(connection);
                
                // Validate personnel ID
                if (personnelIdField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID field cannot be EMPTY!!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(personnelIdField.getText().trim());
                    String start = startDateField.getText().trim();
                    String end = endDateField.getText().trim();
                    String reason = reasonField.getText().trim();
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = null;
                    LocalDate endDate = null;

                    // Parse dates only if fields are not empty
                    if (!start.isEmpty()) {
                        try {
                            startDate = LocalDate.parse(start, formatter);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Invalid start date format (use YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    
                    if (!end.isEmpty()) {
                        try {
                            endDate = LocalDate.parse(end, formatter);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Invalid end date format (use YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Handle different button actions
                    if (text.equals("ADD LEAVE")) {
                        if (startDate == null || endDate == null || reason.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "All are required for adding leave", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        personnelService.addLeaveById(id, startDate, endDate, reason);
                        JOptionPane.showMessageDialog(this, "Leave Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else if (text.equals("CANCEL LEAVE")) {
                        if (startDate == null) {
                            JOptionPane.showMessageDialog(this, "Start date is required for canceling leave", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        personnelService.cancelLeaves(id, startDate);
                        JOptionPane.showMessageDialog(this, "Leave Cancelled Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else if (text.equals("VIEW")) {
                        makeTable(id, textAreaPanel);
                    }
                    
                    // Clear fields after successful operation if needed
                    if (!text.equals("VIEW")) {
                        personnelIdField.setText("");
                        startDateField.setText("");
                        endDateField.setText("");
                        reasonField.setText("");
                    }
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID format (must be a number)", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(this, "Database error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        });
        return button;
    }

    private void makeTable(int id, JPanel textAreaPanel) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PersonnelService personnelService = new PersonnelService(connection);
            List<LeaveRecord> leaves = personnelService.getLeaves(id);
            
            // Clear previous components
            textAreaPanel.removeAll();
            
            // Use BorderLayout for the textAreaPanel to center the table
            textAreaPanel.setLayout(new BorderLayout());
            
            DefaultTableModel model1 = new DefaultTableModel();
            JTable table = new JTable(model1);
            
            // Add columns
            model1.addColumn("Personnel Id");
            model1.addColumn("Start Date");
            model1.addColumn("End Date");
            model1.addColumn("Reason");
            
            // Add data
            for (LeaveRecord l : leaves) {
                model1.addRow(new Object[]{
                    "   "+l.getPersonnelId(),
                    "   "+l.getStartDate(),
                    "   "+l.getEndDate(),
                    "   "+l.getReason()
                });
            }
            
            // Style the table
            table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            table.setRowHeight(25);
            
            // Set column widths
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(200);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(300);
            
            // Center-align personnel numbers
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

            
            // Style the table header
            table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
            
            // Wrap table in scroll pane
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 300)); // Set preferred size
            
            // Create a container panel to center the scroll pane
            JPanel tableContainer = new JPanel(new GridBagLayout());
            tableContainer.setBackground(new Color(255, 255, 204));
            tableContainer.add(scrollPane);
            
            // Add the centered table to the textAreaPanel
            textAreaPanel.add(tableContainer, BorderLayout.CENTER);
            
            // Refresh the panel
            textAreaPanel.revalidate();
            textAreaPanel.repaint();
        }
    }
    private JLabel createFormLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        label.setForeground(new Color(0, 63, 0));
        return label;
    }

    private JTextField createTextField(int columns, int fontSize) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 22));
        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            leave frame = new leave();
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
}