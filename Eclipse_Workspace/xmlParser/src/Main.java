import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Main {

	private static String tableName = "sans";
	 
	 
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		XmlConfigurationParser xmlConfigurationParser=new XmlConfigurationParser();
		Document document=xmlConfigurationParser.loadFile("https://isc.sans.edu/api/sources/attacks/10/2014-09-01?xml");
		Element element=xmlConfigurationParser.getRootElement(document);
		List<Node> dataNodes=xmlConfigurationParser.getChildNodesByTagName(element, "data", NodeType.ELEMENT_NODE);
		List<String> ips=new ArrayList<>();
		for (Node datanode:dataNodes){
			List<Node> ipNodes=xmlConfigurationParser.getChildNodesByTagName(datanode, "ip", NodeType.ELEMENT_NODE);
			for (Node ipNode:ipNodes){
				NodeList nodeLst=ipNode.getChildNodes();
				if (nodeLst.getLength()==1){
				  Node node=nodeLst.item(0);
				  ips.add(node.getNodeValue());
				}
			}
			
		}
		
		for (String ip:ips){
			System.out.println("Retrieve Malicious IP from Internet Storm Centre: " + ip);
			
		}
		
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
		
	
		StringBuffer sb = new StringBuffer("INSERT INTO " + tableName + " (malicious_ip)");
		
		sb.append(" VALUES");
		
		int size = ips.size();
		int count = 0;
		Iterator<String> iterator = ips.iterator();
		   
		while (iterator.hasNext()) {
		if (count < (size - 1))
		    sb.append("(\"" +iterator.next() + "\"),");
		else
		    sb.append("(\"" + iterator.next() + "\");");
		 
		count++;
		  }
		 
		 
		  System.out.println(sb.toString());
		 
		try {
		  PreparedStatement pstmt = con.prepareStatement(sb.toString());
		  pstmt.executeUpdate();
		 
		 } catch (SQLException e) {
		   e.printStackTrace();
		 }
		try{
   		 con.close();
   		  if (con.isClosed()) 
   		    System.out.println("...Database Connection Closed");
 		    System.out.println("Local Threat Agents database has been updated with the list of Malicious IPs from Internet Storm Centre (SANS Institute) !!!");
   		} catch (SQLException e) {
   		  System.err.println("SQLException: " +e.getMessage());
   		}

      
	}
	
}
