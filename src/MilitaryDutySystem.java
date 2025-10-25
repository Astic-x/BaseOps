

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.swing.JOptionPane;

 // Main Class

public class MilitaryDutySystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/military_duty";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "asticX";

    // Main Function
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            initializeDatabase(connection);
            
            CompanyService companyService = new CompanyService(connection);
            PersonnelService personnelService = new PersonnelService(connection);
            DutyService dutyService = new DutyService(connection);
            
//            initializeSampleData(companyService, personnelService);
            
//             Run 3 duty rotations to demonstrate persistence
//            for (int i = 0; i < 3; i++) {
//                System.out.println("\n=== Rotation " + (i+1) + " ===");
//                dutyService.rotateDuties();
//                dutyService.printDutyStatus();
//            }
            
            
//         // Clear and reassign duties for Alpha company
//            dutyService.assignDutiesToCompanyByName("Bravo", LocalDate.now());
//         // Show assigned personnel for Alpha company
//            dutyService.printAssignedPersonnel("Bravo");
//
//            // Add leave for personnel ID 5
//            personnelService.addLeaveById(5, LocalDate.now(), LocalDate.now().plusDays(7), "Vacation");
//
            // View leaves for ID 5
//            List<LeaveRecord> leaves = personnelService.getLeaves(5);
//            for(LeaveRecord l: leaves) {
//            	System.out.println(l.getPersonnelId()+" "+l.getStartDate()+" "+l.getEndDate());
//            }
//            // Cancel all leaves for ID 5 and start date 2025-05-26
//            personnelService.cancelLeaves(5,LocalDate.now());
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //    Function to initialise database 
    
    private static void initializeDatabase(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create tables if they don't exist
            stmt.execute("CREATE TABLE IF NOT EXISTS companies (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "name VARCHAR(50) NOT NULL, " +
                         "current_assigned_duty VARCHAR(50), " +
                         "UNIQUE KEY unique_company_name (name))");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS personnel (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "name VARCHAR(100) NOT NULL, " +
                         "`rank` VARCHAR(50) NOT NULL, " +  // rank is escaped with backticks
                         "last_duty_date DATE, " +
                         "current_duty VARCHAR(50), " +
                         "company_id INT NOT NULL, " +
                         "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE)");
            
            // Create other tables similarly...
            stmt.execute("CREATE TABLE IF NOT EXISTS system_settings (" +
                         "`key` VARCHAR(50) PRIMARY KEY, " +
                         "value VARCHAR(255) NOT NULL)");
            
            // Initialize duty rotation index if not exists
            stmt.execute("INSERT IGNORE INTO system_settings (`key`, value) VALUES ('last_duty_index', '0')");
        }       
    }
    
    public List<Company> findCompanies(Connection connection) throws SQLException{
    	CompanyService companyService = new CompanyService(connection);
    	return companyService.getAllCompanies();
    }
    
    public List<Personnel> findAssignedPersonnel(Connection connection,String company) throws SQLException{
    	PersonnelService personnelService = new PersonnelService(connection);
    	if(company == "Support Company")
    	return personnelService.getPersonnelWithDuties(1);
    	else if(company == "HQ Company")
    		return personnelService.getPersonnelWithDuties(6);    	
    	else if(company == "Bravo Company")
    		return personnelService.getPersonnelWithDuties(3);
    	else if(company == "Charlie Company")
    		return personnelService.getPersonnelWithDuties(4);
    	else if(company == "Delta Company")
    		return personnelService.getPersonnelWithDuties(5);
    	else if(company == "Alpha Company")
    		return personnelService.getPersonnelWithDuties(2);
    	
    	return null;
    	
    }
    
    public void rotate(Connection connection) throws SQLException {
    	DutyService dutyService = new DutyService(connection);
    	dutyService.rotateDuties();
    }
    
    public void assign(Connection connection,String company) throws SQLException {
    	DutyService dutyService = new DutyService(connection);
    	if(company == "Support Company")
    		dutyService.assignDutiesToCompanyByName("Support", LocalDate.now());
        else if(company == "HQ Company")
        	dutyService.assignDutiesToCompanyByName("Head Quarter", LocalDate.now());    	
        else if(company == "Bravo Company")
        	dutyService.assignDutiesToCompanyByName("Bravo", LocalDate.now());
        else if(company == "Charlie Company")
        	dutyService.assignDutiesToCompanyByName("Charlie", LocalDate.now());
        else if(company == "Delta Company")
        	dutyService.assignDutiesToCompanyByName("Delta", LocalDate.now());
        	
    	dutyService.assignDutiesToCompanyByName("Alpha", LocalDate.now());
    	
    }
    
    // Function to initialise sample data
    
//   private static void initializeSampleData(CompanyService companyService, PersonnelService personnelService) 
//           throws SQLException {
//       // Initialize companies
//       String[] companyNames = {"Support","Alpha", "Bravo", "Charlie", "Delta", "Head Quarter"};
//       for (String name : companyNames) {
//           companyService.addCompany(name);
//       }
//       
//       // Initialize personnel
//       List<String> ranks = List.of(
//           "Naib Subedar", "Subedar", "Subedar Major",
//           "Lieutenant", "Captain", "Major"
//       );
//       
//        List<Company> companies = companyService.getAllCompanies();
//        for (Company company : companies) {
//            // Add officers and NCOs
//            int officerCount = 0;
//            for (String rank : ranks) {
//                    personnelService.addPersonnel(
//                        new Personnel(0, rank + " " + (++officerCount), rank, company.getId()));
//            }
//            // Add havildars
//            for (int i = 1; i <= 10; i++) {
//                personnelService.addPersonnel(
//                    new Personnel(0, "Havildar " + i, "Havildar", company.getId()));
//            }
//            // Add naiks
//            for (int i = 1; i <= 10; i++) {
//                personnelService.addPersonnel(
//                    new Personnel(0, "Naik " + i, "Naik", company.getId()));
//            }
//            // Add lance naiks
//            for (int i = 1; i <= 15; i++) {
//                personnelService.addPersonnel(
//                    new Personnel(0, "LNaik " + i, "Naik", company.getId()));
//            }
//            // Add sepoys
//            for (int i = 1; i <= 30; i++) {
//                personnelService.addPersonnel(
//                    new Personnel(0, "Sepoy " + i, "Sepoy", company.getId()));
//            }
//        }
//}
}


// Leave Records Class

class LeaveRecord {
    private int id;
    private int personnelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    public LeaveRecord(int id, int personnelId, LocalDate startDate, LocalDate endDate, String reason) {
        this.id = id;
        this.personnelId = personnelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    // Getters
    public int getId() { return id; }
    public int getPersonnelId() { return personnelId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getReason() { return reason; }

    public boolean isOnLeave(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}


// Company Class

class Company {
    private int id;
    private String name;
    private String currentAssignedDuty;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCurrentAssignedDuty() { return currentAssignedDuty; }
    public void setCurrentAssignedDuty(String duty) { this.currentAssignedDuty = duty; }
}


//Personnel class

class Personnel {
    private int id;
    private String name;
    private String rank;
    private LocalDate lastDutyDate;
    private String currentDuty;
    private int companyId;
    private List<LeaveRecord> leaveRecords = new ArrayList<>();
    
 // Add leave record
    public void addLeaveRecord(LeaveRecord leave) {
        leaveRecords.add(leave);
        leaveRecords.sort(Comparator.comparing(LeaveRecord::getStartDate));
    }

    // Check availability
    public boolean isAvailable(LocalDate date) {
        return leaveRecords.stream()
               .noneMatch(leave -> leave.isOnLeave(date));
    }

    // Get all leaves
    public List<LeaveRecord> getLeaveRecords() {
        return Collections.unmodifiableList(leaveRecords);
    }

    public Personnel(int id, String name, String rank, int companyId) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.companyId = companyId;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRank() { return rank; }
    public LocalDate getLastDutyDate() { return lastDutyDate; }
    public String getCurrentDuty() { return currentDuty; }
    public int getCompanyId() { return companyId; }
    public void setLastDutyDate(LocalDate date) { this.lastDutyDate = date; }
    public void setCurrentDuty(String duty) { this.currentDuty = duty; }

	public void setId(int int1) {
		this.id=int1;
		
	}
}


// Company Services class

class CompanyService {
    private final Connection connection;

    public CompanyService(Connection connection) {
        this.connection = connection;
    }
    
    //function to add company
    public void addCompany(String name) throws SQLException {
        String sql = "INSERT INTO companies (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    //function to get all companies in a list
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT id, name, current_assigned_duty FROM companies";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Company company = new Company(rs.getInt("id"), rs.getString("name"));
                company.setCurrentAssignedDuty(rs.getString("current_assigned_duty"));
                companies.add(company);
            }
        }
        return companies;
    }
}


// Personnel services class

class PersonnelService {
    private final Connection connection;

    public PersonnelService(Connection connection) {
        this.connection = connection;
    }
    
    //get Personnel By id
    public Personnel getPersonnelBasic(int personnelId) throws SQLException {
        String sql = "SELECT * FROM personnel WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Personnel p = new Personnel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(6));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String date = rs.getString(4);
                    LocalDate dutyDate = null;
                    if (!date.isEmpty()) {
                            dutyDate = LocalDate.parse(date, formatter);
                    }
                    p.setLastDutyDate(dutyDate);
                    p.setCurrentDuty(rs.getString(5));
                    return p;
                }
            }
        }
        return null;
    }
    
    //get duty history by personnel Id
    public List<String> getDutyHistoryForPersonnel(int personnelId) throws SQLException {
        List<String> history = new ArrayList<>();
        String sql = "SELECT duty_name, assignment_date FROM duty_history " +
                     "WHERE personnel_id = ? ORDER BY assignment_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    history.add(String.format("%s on %s", 
                        rs.getString("duty_name"),
                        rs.getDate("assignment_date").toLocalDate()));
                }
            }
        }
        return history;
    }
    
    // function to clear duties of all personnel
    public void clearAllDuties() throws SQLException {
        String sql = "UPDATE personnel SET current_duty = NULL";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
    
    //function to add personnel
    public void addPersonnel(Personnel personnel) throws SQLException {
        String sql = "INSERT INTO personnel (name, `rank`, company_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, personnel.getName());
            stmt.setString(2, personnel.getRank());
            stmt.setInt(3, personnel.getCompanyId());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    personnel.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    //function to get personnel by companyId in a list
    public List<Personnel> getPersonnelByCompany(int companyId) throws SQLException {
        List<Personnel> personnelList = new ArrayList<>();
        String sql = "SELECT id, name, `rank`, last_duty_date, current_duty " +
                     "FROM personnel WHERE company_id = ? ORDER BY `rank`, name";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Personnel p = new Personnel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("rank"),
                        companyId
                    );
                    Date dutyDate = rs.getDate("last_duty_date");
                    if (dutyDate != null) {
                        p.setLastDutyDate(dutyDate.toLocalDate());
                    }
                    p.setCurrentDuty(rs.getString("current_duty"));
                    personnelList.add(p);
                }
            }
        }
        return personnelList;
    }

    //function to update duty of personnel
    public void updatePersonnelDuty(Personnel personnel) throws SQLException {
        String sql = "UPDATE personnel SET current_duty = ?, last_duty_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personnel.getCurrentDuty());
            stmt.setDate(2, personnel.getLastDutyDate() != null ? 
                Date.valueOf(personnel.getLastDutyDate()) : null);
            stmt.setInt(3, personnel.getId());
            stmt.executeUpdate();
        }
    }
    
    //function to get personnel with duties using companyId in a list
    public List<Personnel> getPersonnelWithDuties(int companyId) throws SQLException {
        List<Personnel> personnelList = new ArrayList<>();
        String sql = "SELECT id, name, `rank`, last_duty_date, current_duty " +
                     "FROM personnel WHERE company_id = ? AND current_duty IS NOT NULL " +
                     "ORDER BY `rank`, name";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Personnel p = new Personnel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("rank"),
                        companyId
                    );
                    Date dutyDate = rs.getDate("last_duty_date");
                    if (dutyDate != null) {
                        p.setLastDutyDate(dutyDate.toLocalDate());
                    }
                    p.setCurrentDuty(rs.getString("current_duty"));
                    personnelList.add(p);
                }
            }
        }
        return personnelList;
    }
    
 // Add leave by personnel ID
    public void addLeaveById(int personnelId, LocalDate start, LocalDate end, String reason) throws SQLException {
        String sql = "INSERT INTO leave_records (personnel_id, start_date, end_date, reason) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            stmt.setDate(2, Date.valueOf(start));
            stmt.setDate(3, Date.valueOf(end));
            stmt.setString(4, reason);
            stmt.executeUpdate();
        }
    }

    // Cancel leaves by personnel ID and start date
    public void cancelLeaves(int personnelId,LocalDate start) throws SQLException {
        String sql = "DELETE FROM leave_records WHERE personnel_id = ? && start_date =?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            stmt.setDate(2, Date.valueOf(start));
            stmt.executeUpdate();
        }
    }

    // View all leaves by personnel ID
    public List<LeaveRecord> getLeaves(int personnelId) throws SQLException {
        List<LeaveRecord> leaves = new ArrayList<>();
        String sql = "SELECT * FROM leave_records WHERE personnel_id = ? ORDER BY start_date";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    leaves.add(new LeaveRecord(
                        rs.getInt("id"),
                        personnelId,
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("reason")
                    ));
                }
            }
        }
        return leaves;
    }

    // Helper to view all personnel with IDs
    public void printAllPersonnel() throws SQLException {
        String sql = "SELECT id, name, rank FROM personnel ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nPersonnel List:");
            System.out.println("ID | Name | Rank");
            while (rs.next()) {
                System.out.printf("%d | %s | %s%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("rank"));
            }
        }
    }

}


// Duty service class
class DutyService {
    private final Connection connection;
    private final List<String> rotatingDuties = List.of(
        "Main Office", "Magazine", "Kote", "Main Gate", "Store"
    );
    
    public DutyService(Connection connection) {
        this.connection = connection;
    }

    // function to rotate duties between companies
    public void rotateDuties() throws SQLException {
        LocalDate today = LocalDate.now();
        
     // Clear all previous duty assignments first
        new PersonnelService(connection).clearAllDuties();
        // Reset all company duties
        resetCompanyDuties();
        
        int dutyIndex = getLastDutyIndex();
        int dutyIndex1=dutyIndex;
        List<String> assignedDuties = new ArrayList<>();
        List<String> eligibleDuties = new ArrayList<>(rotatingDuties);
        eligibleDuties.remove("Quarter Guard"); // Support company can't get Quarter Guard

        
        CompanyService companyService = new CompanyService(connection);
        List<Company> companies = companyService.getAllCompanies();
        
        for (Company company : companies) {
            String duty;
            if (company.getName().equalsIgnoreCase("Head Quarter")) {
                duty = "MT & Fuel";
            } 
            else if (company.getName().equalsIgnoreCase("Support")) {
                duty = eligibleDuties.get(dutyIndex % eligibleDuties.size());
                if(assignedDuties.contains(duty))
                {
                	dutyIndex++;
                	duty = eligibleDuties.get(dutyIndex % eligibleDuties.size());
                }
                assignedDuties.add(duty);
                dutyIndex++;
            }
            else {
                duty = rotatingDuties.get(dutyIndex % rotatingDuties.size());
                while(assignedDuties.contains(duty))
                {
                	dutyIndex++;
                	duty = rotatingDuties.get(dutyIndex % rotatingDuties.size());
                }
                assignedDuties.add(duty);
                dutyIndex++;
            }
            
            updateCompanyDuty(company.getId(), duty);
            assignDutiesToCompany(company.getId(), duty, today);
        }
        dutyIndex1++;
        saveLastDutyIndex(dutyIndex1);
    }
    
    // function to reset duties assigned to companies as null
    private void resetCompanyDuties() throws SQLException {
        String sql = "UPDATE companies SET current_assigned_duty = NULL";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // function to clear the current_duty of all personnel in a company as null
    private void clearCompanyDuties(int companyId) throws SQLException {
        String sql = "UPDATE personnel SET current_duty = NULL WHERE company_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            stmt.executeUpdate();
        }
 
    }
    
    // function to fetch lastDutyIndex to rotate duties between companies
    private int getLastDutyIndex() throws SQLException {
        String sql = "SELECT value FROM system_settings WHERE `key` = 'last_duty_index'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? Integer.parseInt(rs.getString("value")) : 0;
        }
    }

    // function to save the lastDutyindex after rotating company duties
    private void saveLastDutyIndex(int index) throws SQLException {
        String sql = "UPDATE system_settings SET value = ? WHERE `key` = 'last_duty_index'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(index));
            stmt.executeUpdate();
        }
    }

    //function to update company duty during rotation
    private void updateCompanyDuty(int companyId, String duty) throws SQLException {
        String sql = "UPDATE companies SET current_assigned_duty = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, duty);
            stmt.setInt(2, companyId);
            stmt.executeUpdate();
        }
    }
    
    // function to print assigned personnel by company nme
    public void printAssignedPersonnel(String companyName) throws SQLException {
        CompanyService companyService = new CompanyService(connection);
        PersonnelService personnelService = new PersonnelService(connection);
        
        Optional<Company> company = companyService.getAllCompanies().stream()
            .filter(c -> c.getName().equalsIgnoreCase(companyName))
            .findFirst();
        
        if (!company.isPresent()) {
            System.out.println("Company not found: " + companyName);
            return;
        }
        
        List<Personnel> assignedPersonnel = personnelService.getPersonnelWithDuties(company.get().getId());
        
        System.out.println("\nAssigned Personnel for " + companyName + ":");
        System.out.println("Current Duty: " + company.get().getCurrentAssignedDuty());
        System.out.println("----------------------------------");
        
        if (assignedPersonnel.isEmpty()) {
            System.out.println("No personnel currently assigned to duties");
        } else {
            assignedPersonnel.forEach(p -> 
                System.out.printf("%s (%s) - %s (since %s)%n",
                    p.getName(),
                    p.getRank(),
                    p.getCurrentDuty(),
                    p.getLastDutyDate() != null ? p.getLastDutyDate() : "N/A"
                )
            );
        }
    }
    
    // function to assign duty to personnel of a specific company by passing company name
    public void assignDutiesToCompanyByName(String companyName, LocalDate date) throws SQLException {
        CompanyService companyService = new CompanyService(connection);
        
        // Find the company and its assigned duty
        Optional<Company> company = companyService.getAllCompanies().stream()
            .filter(c -> c.getName().equalsIgnoreCase(companyName))
            .findFirst();
        
        if (company.isPresent()) {
            if (company.get().getCurrentAssignedDuty() == null) {
                throw new SQLException("No duty assigned to company: " + companyName);
            }
            
            // Clear existing duties for this company's personnel first
            clearCompanyDuties(company.get().getId());
            
            // Assign duties using the company's current assigned duty
            assignDutiesToCompany(
                company.get().getId(), 
                company.get().getCurrentAssignedDuty(), 
                date
            );
        } else {
            throw new SQLException("Company not found: " + companyName);
        }
    }

    //function to assign duties to personnel of a company by passing company id
    private void assignDutiesToCompany(int companyId, String duty, LocalDate date) throws SQLException {
        PersonnelService personnelService = new PersonnelService(connection);
        List<Personnel> availablePersonnel = getAvailablePersonnel(companyId, date);
        
        DutyMinHeap havildars = new DutyMinHeap();
        DutyMinHeap naiks = new DutyMinHeap();
        DutyMinHeap sepoys = new DutyMinHeap();
        
        for (Personnel p : availablePersonnel) {
            switch (p.getRank()) {
                case "Havildar": havildars.add(p); break;
                case "Naik": naiks.add(p); break;
                case "Sepoy": sepoys.add(p); break;
                default: break;
            }
        }
        
        if (!havildars.isEmpty()) {
            assignDutyToPersonnel(havildars.poll(), duty, date);
        }
        
        if (duty.equals("Magazine") || duty.equals("MT & Fuel")) {
            if (!naiks.isEmpty()) {
                assignDutyToPersonnel(naiks.poll(), duty, date);
            }
        }
        
        int requiredSepoys = getRequiredSepoys(duty);
        for (int i = 0; i < requiredSepoys && !sepoys.isEmpty(); i++) {
            assignDutyToPersonnel(sepoys.poll(), duty, date);
        }
    }

    // function to get list of available personnel by checking leave status
    private List<Personnel> getAvailablePersonnel(int companyId, LocalDate date) throws SQLException {
        PersonnelService personnelService = new PersonnelService(connection);
        List<Personnel> allPersonnel = personnelService.getPersonnelByCompany(companyId);
        List<Personnel> available = new ArrayList<>();
        
        for (Personnel p : allPersonnel) {
            if (isPersonnelAvailable(p.getId(), date)) {
                available.add(p);
            }
        }
        return available;
    }

    //function to check is personnel is available based on leave status
    private boolean isPersonnelAvailable(int personnelId, LocalDate date) throws SQLException {
        String sql = "SELECT 1 FROM leave_records WHERE personnel_id = ? AND ? BETWEEN start_date AND end_date";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personnelId);
            stmt.setDate(2, Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next();
            }
        }
    }

    //function to assign duty to a personnel
    private void assignDutyToPersonnel(Personnel personnel, String duty, LocalDate date) throws SQLException {
        personnel.setCurrentDuty(duty);
        personnel.setLastDutyDate(date);
        
        PersonnelService personnelService = new PersonnelService(connection);
        personnelService.updatePersonnelDuty(personnel);
        
        String sql = "INSERT INTO duty_history (personnel_id, duty_name, assignment_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personnel.getId());
            stmt.setString(2, duty);
            stmt.setDate(3, Date.valueOf(date));
            stmt.executeUpdate();
        }
    }

    //function to get required sepoys according to duty
    private int getRequiredSepoys(String duty) {
        switch (duty) {
            case "Main Office":
            case "Magazine":
            case "MT & Fuel":
            case "Main Gate":
            case "Store":
                return 6;
            case "Kote":
                return 3;
            default:
                return 0;
        }
    }

    //function to print personnel on duty from each company
    public void printDutyStatus() throws SQLException {
        System.out.println("\nMilitary Duty Status - " + LocalDate.now());
        System.out.println("=====================================");
        
        CompanyService companyService = new CompanyService(connection);
        List<Company> companies = companyService.getAllCompanies();
        
        for (Company company : companies) {
            List<Personnel> personnel = new PersonnelService(connection).getPersonnelByCompany(company.getId());
            long assignedCount = personnel.stream().filter(p -> p.getCurrentDuty() != null).count();
            
            System.out.printf("%nCompany: %s (%d personnel, %d on duty)%n", 
                company.getName(), personnel.size(), assignedCount);
            System.out.println("Current Duty: " + company.getCurrentAssignedDuty());
            
            for (Personnel p : personnel) {
                if (p.getCurrentDuty() != null) {
                    System.out.printf("  %s (%s) - %s%n", 
                        p.getName(), p.getRank(), p.getCurrentDuty());
                }
            }
        }
    }
}


// Heap Class
class DutyMinHeap {
    private final List<Personnel> heap = new ArrayList<>();

    public void add(Personnel p) {
        heap.add(p);
        upHeap(heap.size() - 1);
    }

    public Personnel poll() {
        if (heap.isEmpty()) return null;
        Collections.swap(heap, 0, heap.size() - 1);
        Personnel result = heap.remove(heap.size() - 1);
        heapify(0);
        return result;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void upHeap(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(heap.get(index), heap.get(parent)) < 0) {
                Collections.swap(heap, index, parent);
                index = parent;
            } else break;
        }
    }

    private void heapify(int index) {
        int size = heap.size();
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && compare(heap.get(left), heap.get(smallest)) < 0)
                smallest = left;
            if (right < size && compare(heap.get(right), heap.get(smallest)) < 0)
                smallest = right;

            if (smallest != index) {
                Collections.swap(heap, index, smallest);
                index = smallest;
            } else break;
        }
    }

    private int compare(Personnel p1, Personnel p2) {
        if (p1.getLastDutyDate() == null && p2.getLastDutyDate() == null) return 0;
        if (p1.getLastDutyDate() == null) return -1;
        if (p2.getLastDutyDate() == null) return 1;
        return p1.getLastDutyDate().compareTo(p2.getLastDutyDate());
    }
}