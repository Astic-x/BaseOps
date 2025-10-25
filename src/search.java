
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
import java.util.List;

public class search extends JFrame {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/military_duty";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "asticX";
    JLabel id;
    JLabel idv;
    JLabel rank;
    JLabel rankv;
    JLabel company;
    JLabel companyv;
    JLabel name;
    JLabel namev;
    JLabel current;
    JLabel currentv;
    JLabel last;
    JLabel lastv;
    public search() {
        initComponents();
    }

    private void initComponents() {
        // Main frame settings
        setTitle("Personnel Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Center the window

        // Main panel with background color
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(255, 255, 204)); // Light yellow background
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        

        
        // Title label
        JLabel titleLabel = new JLabel("PERSONNEL DETAILS");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 64, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Left panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(255, 255, 204));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // "Fetch Personnel Details" label
        JLabel fetchLabel = new JLabel("Fetch Personnel Details");
        fetchLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        fetchLabel.setForeground(new Color(0, 67, 0));
        fetchLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Input field panel
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.setBackground(new Color(255, 255, 204));
        idPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel("Enter ID");
        idLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        
        idLabel.setForeground(new Color(0, 64, 0));

        JTextField idField = new JTextField();
        idField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        idField.setPreferredSize(new Dimension(200, 30));

        idPanel.add(idLabel);
        idPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        idPanel.add(idField);
        
        ImageIcon leftIcon = new ImageIcon("C:\\Users\\HP\\Downloads\\personnel.jpg");
        Image scaledLeftImage = leftIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        JLabel leftLogoLabel = new JLabel(new ImageIcon(scaledLeftImage));
        leftLogoLabel.setBounds(15, 15, 500, 280); 
        mainPanel.add(leftLogoLabel);
      
        
        

        // Add components to input panel
        inputPanel.add(fetchLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        inputPanel.add(idPanel);

 
        
        // Right panel for results display 
        JPanel rPanel = new JPanel(null);  

        rPanel.setPreferredSize(new Dimension(600, 600));

        // Position components using setBounds(x, y, width, height)
        name = new JLabel("Name : ");
        name.setFont(new Font("Times New Roman", Font.BOLD, 20));
        name.setForeground(new Color(0, 64, 0));
        name.setBounds(10, 90, 100, 30);  
        rPanel.add(name);

        namev = new JLabel("");
        namev.setFont(new Font("Times New Roman", Font.BOLD, 20));
        namev.setForeground(new Color(0, 64, 0));
        namev.setBounds(85, 90, 200, 30);  
        rPanel.add(namev);

        id = new JLabel("ID : ");
        id.setFont(new Font("Times New Roman", Font.BOLD, 20));
        id.setForeground(new Color(0, 64, 0));
        id.setBounds(10, 10, 100, 30);  
        rPanel.add(id);

        idv = new JLabel("");
        idv.setFont(new Font("Times New Roman", Font.BOLD, 20));
        idv.setForeground(new Color(0, 64, 0));
        idv.setBounds(60, 10, 200, 30);  
        rPanel.add(idv);

        rank = new JLabel("Rank : ");
        rank.setFont(new Font("Times New Roman", Font.BOLD, 20));
        rank.setForeground(new Color(0, 64, 0));
        rank.setBounds(10, 50, 100, 30);  
        rPanel.add(rank);

        rankv = new JLabel("");
        rankv.setFont(new Font("Times New Roman", Font.BOLD, 20));
        rankv.setForeground(new Color(0, 64, 0));
        rankv.setBounds(80, 50, 200, 30);
        rPanel.add(rankv);

        company = new JLabel("Company : ");
        company.setFont(new Font("Times New Roman", Font.BOLD, 20));
        company.setForeground(new Color(0, 64, 0));
        company.setBounds(10, 130, 100, 30);  
        rPanel.add(company);

        companyv = new JLabel("");
        companyv.setFont(new Font("Times New Roman", Font.BOLD, 20));
        companyv.setForeground(new Color(0, 64, 0));
        companyv.setBounds(115, 130, 200, 30);
        rPanel.add(companyv);
        
        current = new JLabel("Current Duty : ");
        current.setFont(new Font("Times New Roman", Font.BOLD, 20));
        current.setForeground(new Color(0, 64, 0));
        current.setBounds(10, 170, 200, 30);  
        rPanel.add(current);
        
        currentv = new JLabel("");
        currentv.setFont(new Font("Times New Roman", Font.BOLD, 20));
        currentv.setForeground(new Color(0, 64, 0));
        currentv.setBounds(150, 170, 200, 30);
        rPanel.add(currentv);
        
        last = new JLabel("Last Duty Date : ");
        last.setFont(new Font("Times New Roman", Font.BOLD, 20));
        last.setForeground(new Color(0, 64, 0));
        last.setBounds(10, 210, 200, 30);  
        rPanel.add(last);
        
        lastv = new JLabel("");
        lastv.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lastv.setForeground(new Color(0, 64, 0));
        lastv.setBounds(160, 210, 200, 30);
        rPanel.add(lastv);
        
        
        

        // Container panel
        JPanel rightContainer = new JPanel(new BorderLayout());
//        rightContainer.setBackground(new Color(255, 255, 204));
        rightContainer.add(rPanel, BorderLayout.WEST);

        
       
        // Main content panel
        JPanel contentPanel = new JPanel(new FlowLayout());
        contentPanel.setBackground(new Color(255, 255, 204));
        contentPanel.add(inputPanel, BorderLayout.WEST);
        contentPanel.add(rightContainer, BorderLayout.CENTER);

        // Add components to main panel
        JButton btnHome = createButton("HOME", 50, 700,200,140,idField,rPanel);
        JButton btnLeave = createButton("LEAVE MANAGER", 290, 700,400,150,idField,rPanel);
        JButton btnPersonnel = createButton("PERSONNEL DUTIES", 530, 700,350,140,idField,rPanel);
        JButton btnCompany = createButton("COMPANY DUTIES", 770, 700,350,150,idField,rPanel);
        JButton btnSearch = createButton("SEARCH", 1010, 700,350,150,idField,rPanel);
        JButton btnAbout = createButton("ABOUT", 1250, 700,350,150,idField,rPanel);
        
        mainPanel.add(btnHome);
        mainPanel.add(btnLeave);
        mainPanel.add(btnPersonnel);
        mainPanel.add(btnCompany);
        mainPanel.add(btnSearch);
        mainPanel.add(btnAbout);
        
        JButton btnSearchP = createButton("Search Personnel", 1250, 700,350,150,idField,rPanel);
        idPanel.add(btnSearchP);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
    }
    
    private JButton createButton(String text, int x, int y,int width , int length,JTextField idField,JPanel rightContainer ) {
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
        	else if(text=="Search Personnel")
        	{
        		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    PersonnelService personnelService = new PersonnelService(connection);
                    if (idField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "ID field cannot be EMPTY!!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else {
                    	
                    	
                    	int id = Integer.parseInt(idField.getText());
                    	Personnel P = personnelService.getPersonnelBasic(id);
                    	namev.setText(P.getName());
                    	idv.setText(Integer.toString(P.getId()));
                    	rankv.setText(P.getRank());
                    	if(P.getCurrentDuty() == null)
                    		currentv.setText("None");                   		
                    	else
                    		currentv.setText(P.getCurrentDuty());
                    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    	if(P.getLastDutyDate() == null)
                    		lastv.setText("None");                    		
                    	else
                    		lastv.setText((P.getLastDutyDate()).format(formatter));
                    	if(P.getCompanyId() == 1)
                    		companyv.setText("Support");
                    	else if(P.getCompanyId() == 3)
                    		companyv.setText("Alpha");
                    	else if(P.getCompanyId() == 2)
                    		companyv.setText("Bravo");
                    	else if(P.getCompanyId() == 2)
                    		companyv.setText("Charlie");
                    	else if(P.getCompanyId() == 2)
                    		companyv.setText("Delta");
                    	else if(P.getCompanyId() == 2)
                    		companyv.setText("Head Quarter");
                    	
                    	
                    	makeTable(rightContainer,id);
                    }
        		} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        	
        });
        return button;
    }
        
    private void makeTable(JPanel mainPanel,int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        	PersonnelService personnelService = new PersonnelService(connection);
            List<String> History = personnelService.getDutyHistoryForPersonnel(id);

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
            model.setColumnIdentifiers(new String[]{"DUTY HISTORY"});

            JTable table = new JTable(model);
            table.setFont(new Font("Times New Roman", Font.BOLD, 20));
            table.setRowHeight(50);
            table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
            
            // Set column widths
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(300);
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            

            // Add data
            for (String company : History) {
                model.addRow(new Object[]{
                    company,
                });
            }

            // Create scroll pane with your exact positioning
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(150, 275, 300, 300);
            
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

           search frame = new search();
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
}