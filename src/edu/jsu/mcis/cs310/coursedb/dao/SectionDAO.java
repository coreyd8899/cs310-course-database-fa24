package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionDAO {
    private final DAOFactory daoFactory;

    public SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String find(int termid, String subjectid, String num) {
        String jsonResult = "[]";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            String query = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
            ps = conn.prepareStatement(query);
            ps.setInt(1, termid);
            ps.setString(2, subjectid);
            ps.setString(3, num);
            rs = ps.executeQuery();

            // Convert ResultSet to JSON using DAOUtility
            jsonResult = DAOUtility.getResultSetAsJson(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return jsonResult;
    }
}
