
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class JavaFileScanner {
 
    public static void main(String[] args) throws IOException {
    	
    	
    try {

    	String fileName = "C:\\Users\\krommydakisp\\Desktop\\capstone\\fw_logs\\CheckPoint-FW.txt";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
         
        //read file line by line
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()){
            System.out.println("Lines: "+scanner.next());
        }
        scanner.close();
        //read Log File and parse it to object array
        scanner = new Scanner(Paths.get("C:\\Users\\krommydakisp\\Desktop\\capstone\\fw_logs\\CheckPoint-FW.txt"));
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()){
            //parse line to get Source IP Object
        	SuspiciousEvent event = parseLogLine(scanner.next());
            System.out.println(event.toString());
        }
        scanner.close();
         
        
	  } catch (Exception e) {
		   e.printStackTrace();
		  }
    }
     
    private static SuspiciousEvent parseLogLine(String line) throws UnknownHostException {
         Scanner scanner = new Scanner(line);
         scanner.useDelimiter("\\s*,\\s*");
         String date = scanner.next();
         String time = scanner.next();
         String action = scanner.next();
         String fw_name = scanner.next();
         String direction = scanner.next();
         String srcIP = scanner.next();
         String destIP = scanner.next();
         String numberOfBytes = scanner.next();
         String ruleNumber = scanner.next();
         String protocol = scanner.next();

         JavaFileScanner jfs = new JavaFileScanner();

         
         srcIP = srcIP.replace("src=", "" );
         srcIP = srcIP.replaceAll("^\"|\"$", "");
         
         String [] ipv4_parts = srcIP.split ("\\.");
         
         String new_srcIP = "";
         for (String s : ipv4_parts)
         {
        
        	 //System.out.println(s);
        	 String new_s = org.apache.commons.lang.StringUtils.leftPad(s, 3, "0");
        	 //System.out.println(new_s);
        	 
        	 new_srcIP = new_srcIP +"." + new_s;
        	 //System.out.println(new_srcIP);
         
         }
         
         srcIP = new_srcIP.substring(1);
         //System.out.println(srcIP);
         

         return jfs.new SuspiciousEvent(srcIP);
    }
 
    public class SuspiciousEvent{
        private String srcIP;

 
        public SuspiciousEvent(String n){ 
            this.srcIP = n;

        }
         
        @Override
        public String toString(){

        	
              System.out.println("Suspicious IP="+this.srcIP);
         	  Connection con = null;
           	  String url = "jdbc:mysql://localhost:3306/";
           	  String db =  "threat";
           	  String driver = "com.mysql.jdbc.Driver";
           	  String user = "root";
           	  String pass = "a11111111!";
           	 
           	  try {
           	   Class.forName(driver);
           	   con = DriverManager.getConnection(url + db, user, pass);
     		  if (con!=null) 
      		    System.out.println("Database Connection Opened...");
           	  } catch (ClassNotFoundException e) {
           	   e.printStackTrace();
           	  } catch (SQLException e) {
           	   e.printStackTrace();
           	  }
        	try {
				databaseQuery(con, this.srcIP);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try{
        		 con.close();
        		  if (con.isClosed()) 
        		    System.out.println("...Database Connection Closed");
        		} catch (SQLException e) {
        		  System.err.println("SQLException: " +e.getMessage());
        		}
            return "Cross-check has been concluded for suspicious IP="+this.srcIP + "\n\n";  
            
        }
        

        
     private void databaseQuery(Connection con, String srcIP) throws SQLException {
        	
    	String srcTableName = "sans";
    	String dstTableName = "threat";

    	StringBuffer sb = new StringBuffer("SELECT * FROM " + srcTableName + " WHERE malicious_ip= ?" );
        //System.out.println("Prepared SQL statement:" + sb.toString());

    	try {
     		 
    		 PreparedStatement pstmt = null;
    		 pstmt = con.prepareStatement(sb.toString());
    		 pstmt.setString(1, srcIP);
    		 
    		 //System.out.println("Prepared SQL statement:" + pstmt.toString());

     		 ResultSet rs= pstmt.executeQuery();
     		  

     		  
     		 	if (rs.next()) {
     		 		System.out.println("WARNING!!! Potential attack identified from source IP address: " + srcIP);
     		 		
     		    	StringBuffer db = new StringBuffer("INSERT INTO " + dstTableName + " (suspicious_ip) VALUES(?)" );
     		    	//System.out.println("Prepared SQL statement:" + db.toString());
     		    	
     	    		 PreparedStatement stmt = null;
     	    		 stmt = con.prepareStatement(db.toString());
     	    		 stmt.setString(1, srcIP);

     	     		 stmt.executeUpdate();
     		 		
     		 	}
     		  
     		 
     		  } catch (SQLException e) {
     		   e.printStackTrace();
     		  }
          
        
     }
    
    
   
    }    
   
}
 

