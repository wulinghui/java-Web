package cn.wlh.util.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _JDBC {
	/**
	 * java存储过程
	 */
public static class UseSQLDataBase3 { 
Connection con; 
Statement state; 
ResultSet rs; 
CallableStatement cs;//调用存储过程使用的接口 
String url = "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=test"; 
String user = "sa"; 
String password = ""; 
public void connectSQL(){ 
  
   try { 
    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver"); 
   } catch (ClassNotFoundException e) { 
    // TODO 自动生成 catch 块 
    e.printStackTrace(); 
   } 
    
   try { 
    con = DriverManager.getConnection(url, user, password); 
    state = con.createStatement(); 
     
     
    //创建存储过程SQL语句 
    String createProcedure = " create procedure SHOW_SUPPLIERS " + 
      "as "+ 
      " select SUPPLIERS.SUP_NAME,COFFEES.COF_NAME "+ 
      "from suppliers,coffees"+ 
      "where suppliers.sup_id = coffees.sup_id "+ 
      "order by sup_name"; 
    //创建存储过程 
    state.executeUpdate("USE TEST"); 
    state.executeUpdate(createProcedure); 
     
    //调用存储过程 
    cs = con.prepareCall("{call SHOW_SUPPLIERS}");//创建一个 CallableStatement 对象来调用数据库存储过程 
    //返回调用的结果集 
    rs = cs.executeQuery(); 
     
    //输出结果 
//    System.out.println("SUPPLIERS.SUP_NAME            COFFEES.COF_NAME"); 
    while(rs.next()){ 
     String sup_name = rs.getString(1); 
     String coffees_name = rs.getString(2); 
//     System.out.println(sup_name + "      " + coffees_name); 
    } 
    con.close(); 
    state.close();   
   } catch (SQLException e) { 
    // TODO 自动生成 catch 块 
    e.printStackTrace(); 
   } 
}
   
   
   public static class CreateTable0 {
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/jdbc_db";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "123456";

	   public  void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
//	      System.out.println("Connecting to a selected database...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
//	      System.out.println("Connected database successfully...");

	      //STEP 4: Execute a query
//	      System.out.println("Creating table in given database...");
	      stmt = conn.createStatement();

	      String sql = "CREATE TABLE student " +
	                   "(id INTEGER not NULL, " +
	                   " first VARCHAR(255), " + 
	                   " last VARCHAR(255), " + 
	                   " age INTEGER, " + 
	                   " PRIMARY KEY ( id ))"; 

	      stmt.executeUpdate(sql);
//	      System.out.println("Created table in given database...");
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
//	   System.out.println("Goodbye!");
	}//end main
   }//end JDBCExample
   
}   
}