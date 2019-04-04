package com.sandeep.collector.redshift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.json.simple.JSONObject;

public class RedShiftConnection {
  private static final String dbURL = "url";
  private static final String MasterUsername = "user";
  private static final String MasterUserPassword = "pwd";

  public ArrayList<JSONObject> getTableData(String table) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ArrayList<JSONObject> list = new ArrayList<JSONObject>();
    try {
      Class.forName("com.amazon.redshift.jdbc.Driver");
      System.out.println("Connecting to database...");
      Properties props = new Properties();

      props.setProperty("user", MasterUsername);
      props.setProperty("password", MasterUserPassword);
      conn = DriverManager.getConnection(dbURL, props);

      stmt = conn.createStatement();
      String sql = "select * from " + table + ";";
      ResultSet rs = stmt.executeQuery(sql);
      ResultSetMetaData meta = rs.getMetaData();
      int num_columns = meta.getColumnCount();
      System.out.println("Total number of columns: " + num_columns);
      JSONObject json = null;
      while (rs.next()) {
        json = new JSONObject();
        for (int i = 1; i <= num_columns; i++) {
          String col = meta.getColumnName(i);
          json.put(col, rs.getString(col));
        }
        list.add(json);
      }
      rs.close();
      stmt.close();
      conn.close();
      return list;
    }

    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(ex.getMessage());
    } finally {
      try {
        if (stmt != null)
          stmt.close();
      } catch (Exception ex) {
        throw new Exception(ex.getMessage());
      }
      try {
        if (conn != null)
          conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
        throw new Exception(ex.getMessage());
      }
    }
  }
}
