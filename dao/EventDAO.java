package dao;

import bean.EventBean;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // ==========================================
    // ADD EVENT
    // ==========================================

    public boolean addEvent(EventBean e) {

        String sql =
                "INSERT INTO Event VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, e.getEventId());
            ps.setString(2, e.getEventName());
            ps.setString(3, e.getEventDate());
            ps.setString(4, e.getVenue());
            ps.setInt(5, e.getCapacity());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


    // ==========================================
    // DATABASE DISPLAY
    // ==========================================

    public void viewEvents() {

        String sql =
                "SELECT * FROM Event ORDER BY eventId";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            System.out.println();

            System.out.println(
                    "====================================================================");

            System.out.printf(
                    "%-5s %-20s %-15s %-15s %-10s%n",
                    "ID",
                    "NAME",
                    "DATE",
                    "VENUE",
                    "CAPACITY");

            System.out.println(
                    "====================================================================");

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-20s %-15s %-15s %-10d%n",
                        rs.getInt("eventId"),
                        rs.getString("eventName"),
                        rs.getString("eventDate"),
                        rs.getString("venue"),
                        rs.getInt("capacity"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // ==========================================
    // GET ALL EVENTS
    // ==========================================

    public List<EventBean> getAllEvents() {

        List<EventBean> events =
                new ArrayList<>();

        String sql =
                "SELECT * FROM Event ORDER BY eventId";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                EventBean e =
                        new EventBean();

                e.setEventId(
                        rs.getInt("eventId"));

                e.setEventName(
                        rs.getString("eventName"));

                e.setEventDate(
                        rs.getString("eventDate"));

                e.setVenue(
                        rs.getString("venue"));

                e.setCapacity(
                        rs.getInt("capacity"));

                events.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return events;
    }


    // ==========================================
    // UPDATE EVENT
    // ==========================================

    public boolean updateEvent(EventBean e) {

        String sql =
                "UPDATE Event SET " +
                        "eventName=?, eventDate=?, venue=?, capacity=? " +
                        "WHERE eventId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1, e.getEventName());
            ps.setString(2, e.getEventDate());
            ps.setString(3, e.getVenue());
            ps.setInt(4, e.getCapacity());
            ps.setInt(5, e.getEventId());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


    // ==========================================
    // DELETE EVENT
    // ==========================================

    public boolean deleteEvent(int id) {

        String sql =
                "DELETE FROM Event WHERE eventId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


    // ==========================================
    // TOTAL EVENTS
    // ==========================================

    public int getTotalEvents() {

        String sql =
                "SELECT COUNT(*) FROM Event";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }


    // ==========================================
    // GET EVENT BY ID
    // ==========================================

    public EventBean getEventById(int id) {

        String sql =
                "SELECT * FROM Event WHERE eventId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                EventBean e =
                        new EventBean();

                e.setEventId(
                        rs.getInt("eventId"));

                e.setEventName(
                        rs.getString("eventName"));

                e.setEventDate(
                        rs.getString("eventDate"));

                e.setVenue(
                        rs.getString("venue"));

                e.setCapacity(
                        rs.getInt("capacity"));

                return e;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    // ==========================================
    // REDUCE EVENT CAPACITY
    // ==========================================

    public boolean reduceCapacity(int eventId) {

        String sql =
                "UPDATE Event " +
                        "SET capacity = capacity - 1 " +
                        "WHERE eventId = ? AND capacity > 0";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}