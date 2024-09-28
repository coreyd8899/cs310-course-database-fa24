package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {

                // Get metadata for column names
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Iterate through the result set
                while (rs.next()) {
                    
                    JsonObject jsonObject = new JsonObject();
                    
                    // For each row, iterate over the columns
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        
                        if (rs.getObject(i) != null) {
                            jsonObject.put(columnName, rs.getObject(i));  // Add column value if not null
                        } 
                        else {
                            jsonObject.put(columnName, null);  // Use null for null values
                        }
                    }
                    
                    // Add JSON object (representing a row) to the records array
                    records.add(jsonObject);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Serialize the array of records into a JSON string
        return Jsoner.serialize(records);
    }
}
