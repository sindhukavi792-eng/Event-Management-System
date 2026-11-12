package dao;

import bean.RegistrationBean;
import util.DBConnection;

import java.sql.*;

public class RegistrationDAO {

    // REGISTER PARTICIPANT
    public boolean registerParticipant(RegistrationBean r) {

        // 1. Check duplicate registration
        String checkSql =
                "SELECT COUNT(*) FROM Registration " +
                "WHERE eventId=? AND participantId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(checkSql)) {

            ps.setInt(1, r.getEventId());
            ps.setInt(2, r.getParticipantId());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (rs.getInt(1) > 0) {

                    System.out.println();
                    System.out.println(
                            "Participant already registered for this event!");

                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        // 2. Check event capacity
        String capacitySql =
                "SELECT e.capacity, " +
                "COUNT(r.registrationId) AS registered " +
                "FROM Event e " +
                "LEFT JOIN Registration r " +
                "ON e.eventId = r.eventId " +
                "WHERE e.eventId=? " +
                "GROUP BY e.eventId, e.capacity";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(capacitySql)) {

            ps.setInt(1, r.getEventId());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int capacity =
                        rs.getInt("capacity");

                int registered =
                        rs.getInt("registered");

                System.out.println();
                System.out.println(
                        "Available Seats : "
                                + (capacity - registered));

                if (registered >= capacity) {

                    System.out.println("Event is Full!");

                    return false;
                }

            } else {

                System.out.println("Event not found!");

                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        // 3. Insert registration
        // registrationId is NOT entered
        // MySQL will generate it automatically

        String sql =
                "INSERT INTO Registration(eventId, participantId) " +
                "VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, r.getEventId());
            ps.setInt(2, r.getParticipantId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // VIEW REGISTRATIONS
    public void viewRegistrations() {

        String sql =
                "SELECT r.registrationId, " +
                "p.participantName, " +
                "e.eventName, " +
                "e.eventDate, " +
                "e.venue " +
                "FROM Registration r " +
                "JOIN Participant p " +
                "ON r.participantId = p.participantId " +
                "JOIN Event e " +
                "ON r.eventId = e.eventId";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            System.out.println();
            System.out.println(
                    "================================================================================");

            System.out.printf(
                    "%-8s %-20s %-20s %-15s %-15s%n",
                    "REG ID",
                    "PARTICIPANT",
                    "EVENT",
                    "DATE",
                    "VENUE");

            System.out.println(
                    "================================================================================");

            while (rs.next()) {

                System.out.printf(
                        "%-8d %-20s %-20s %-15s %-15s%n",
                        rs.getInt("registrationId"),
                        rs.getString("participantName"),
                        rs.getString("eventName"),
                        rs.getString("eventDate"),
                        rs.getString("venue"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // CANCEL REGISTRATION
    public boolean cancelRegistration(int id) {

        String sql =
                "DELETE FROM Registration " +
                "WHERE registrationId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // TOTAL REGISTRATIONS
    public int getTotalRegistrations() {

        String sql =
                "SELECT COUNT(*) FROM Registration";

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