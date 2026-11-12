package service;

import bean.EventBean;
import bean.ParticipantBean;
import bean.RegistrationBean;

import dao.EventDAO;
import dao.ParticipantDAO;
import dao.RegistrationDAO;

import java.util.List;

public class EventManagementService {

    // ==========================================
    // DAO OBJECTS
    // ==========================================

    private EventDAO eventDAO =
            new EventDAO();

    private ParticipantDAO participantDAO =
            new ParticipantDAO();

    private RegistrationDAO registrationDAO =
            new RegistrationDAO();


    // ==========================================
    // EVENT METHODS
    // ==========================================

    public boolean addEvent(EventBean e) {

        return eventDAO.addEvent(e);
    }


    public void viewEvents() {

        eventDAO.viewEvents();
    }


    // GET ALL EVENTS
    public List<EventBean> getAllEvents() {

        return eventDAO.getAllEvents();
    }


    public boolean updateEvent(EventBean e) {

        return eventDAO.updateEvent(e);
    }


    public boolean deleteEvent(int id) {

        return eventDAO.deleteEvent(id);
    }


    public int getTotalEvents() {

        return eventDAO.getTotalEvents();
    }


    public EventBean getEventById(int id) {

        return eventDAO.getEventById(id);
    }


    // ==========================================
    // PARTICIPANT METHODS
    // ==========================================

    public int addParticipant(ParticipantBean p) {

        return participantDAO.addParticipant(p);
    }


    public int getParticipantIdByEmail(String email) {

        return participantDAO.getParticipantIdByEmail(email);
    }


    public void viewParticipants() {

        participantDAO.viewParticipants();
    }


    public boolean updateParticipant(ParticipantBean p) {

        return participantDAO.updateParticipant(p);
    }


    public boolean deleteParticipant(int id) {

        return participantDAO.deleteParticipant(id);
    }


    public int getTotalParticipants() {

        return participantDAO.getTotalParticipants();
    }


    // ==========================================
    // REGISTRATION METHODS
    // ==========================================

    public boolean registerParticipant(
            RegistrationBean r) {

        // First register participant
        boolean registered =
                registrationDAO.registerParticipant(r);


        // If registration is successful,
        // reduce event capacity by 1
        if (registered) {

            boolean capacityUpdated =
                    eventDAO.reduceCapacity(
                            r.getEventId());


            if (!capacityUpdated) {

                System.out.println(
                        "Registration successful, " +
                                "but capacity update failed.");
            }
        }


        return registered;
    }


    public void viewRegistrations() {

        registrationDAO.viewRegistrations();
    }


    public boolean cancelRegistration(int id) {

        return registrationDAO.cancelRegistration(id);
    }


    public int getTotalRegistrations() {

        return registrationDAO.getTotalRegistrations();
    }
}