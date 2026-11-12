package dao;

import bean.ParticipantBean;
import util.DBConnection;

import java.sql.*;

public class ParticipantDAO {

    // ADD PARTICIPANT
    public int addParticipant(ParticipantBean p) {

        String sql =
                "INSERT INTO Participant(participantName, email) VALUES(?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getParticipantName());
            ps.setString(2, p.getEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    // GET PARTICIPANT ID BY EMAIL
    public int getParticipantIdByEmail(String email) {

        String sql =
                "SELECT participantId FROM Participant WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("participantId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    // VIEW PARTICIPANTS
    public void viewParticipants() {

        String sql = "SELECT * FROM Participant";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            System.out.println();
            System.out.println(
                    "========================================================");

            System.out.printf(
                    "%-5s %-20s %-25s%n",
                    "ID",
                    "NAME",
                    "EMAIL");

            System.out.println(
                    "========================================================");

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-20s %-25s%n",
                        rs.getInt("participantId"),
                        rs.getString("participantName"),
                        rs.getString("email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // UPDATE PARTICIPANT
    public boolean updateParticipant(ParticipantBean p) {

        String sql =
                "UPDATE Participant " +
                "SET participantName=?, email=? " +
                "WHERE participantId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1, p.getParticipantName());
            ps.setString(2, p.getEmail());
            ps.setInt(3, p.getParticipantId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // DELETE PARTICIPANT
    public boolean deleteParticipant(int id) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            // Delete registrations first
            String registrationSql =
                    "DELETE FROM Registration WHERE participantId=?";

            try (PreparedStatement ps =
                         con.prepareStatement(registrationSql)) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }


            // Delete participant
            String participantSql =
                    "DELETE FROM Participant WHERE participantId=?";

            try (PreparedStatement ps =
                         con.prepareStatement(participantSql)) {

                ps.setInt(1, id);

                int result =
                        ps.executeUpdate();

                con.commit();

                return result > 0;
            }

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {

                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    // TOTAL PARTICIPANTS
    public int getTotalParticipants() {

        String sql =
                "SELECT COUNT(*) FROM Participant";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}