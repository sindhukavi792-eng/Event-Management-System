package main;

import bean.EventBean;
import bean.ParticipantBean;
import bean.RegistrationBean;
import service.EventManagementService;

import java.util.List;
import java.util.Scanner;

public class MainClass {

    static Scanner sc =
            new Scanner(System.in);

    static EventManagementService service =
            new EventManagementService();


    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("       EVENT MANAGEMENT SYSTEM");
            System.out.println("========================================");

            System.out.println("1. Register for Event");
            System.out.println("2. View Participants");
            System.out.println("3. View Registrations");
            System.out.println("4. Delete Participant");
            System.out.println("5. Exit");

            System.out.print("Enter Choice : ");

            String input = sc.nextLine();

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number!");

                continue;
            }


            switch (choice) {

                case 1:
                    registerForEvent();
                    break;

                case 2:
                    service.viewParticipants();
                    break;

                case 3:
                    service.viewRegistrations();
                    break;

                case 4:
                    deleteParticipant();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    return;

                default:
                    System.out.println(
                            "Invalid Choice!");
            }
        }
    }


    // ==========================================
    // REGISTER FOR EVENT
    // ==========================================

    public static void registerForEvent() {

        System.out.println();
        System.out.println(
                "========== AVAILABLE EVENTS ==========");


        // Get all events from database
        List<EventBean> events =
                service.getAllEvents();


        if (events.isEmpty()) {

            System.out.println(
                    "No events available!");

            return;
        }


        // Display events dynamically
        for (int i = 0; i < events.size(); i++) {

            EventBean e =
                    events.get(i);

            System.out.println();

            System.out.println(
                    (i + 1) + ". " +
                            e.getEventName());

            System.out.println(
                    "   Date  : " +
                            e.getEventDate());

            System.out.println(
                    "   Venue : " +
                            e.getVenue());

            System.out.println(
                    "   Seats : " +
                            e.getCapacity());
        }


        System.out.println();

        System.out.print(
                "Enter Event Choice : ");

        String eventInput =
                sc.nextLine();

        int eventChoice;

        try {

            eventChoice =
                    Integer.parseInt(eventInput);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid event number!");

            return;
        }


        // Check event choice
        if (eventChoice < 1 ||
                eventChoice > events.size()) {

            System.out.println(
                    "Invalid Event Choice!");

            return;
        }


        // Get selected event
        EventBean selectedEvent =
                events.get(eventChoice - 1);


        int eventId =
                selectedEvent.getEventId();

        String eventName =
                selectedEvent.getEventName();

        String eventDate =
                selectedEvent.getEventDate();

        String venue =
                selectedEvent.getVenue();


        // ==========================================
        // SELECTED EVENT
        // ==========================================

        System.out.println();

        System.out.println(
                "========== SELECTED EVENT ==========");

        System.out.println(
                "Event ID   : " +
                        eventId);

        System.out.println(
                "Event Name : " +
                        eventName);

        System.out.println(
                "Date       : " +
                        eventDate);

        System.out.println(
                "Venue      : " +
                        venue);

        System.out.println(
                "Available Seats : " +
                        selectedEvent.getCapacity());


        // ==========================================
        // PARTICIPANT DETAILS
        // ==========================================

        System.out.println();

        System.out.print(
                "Enter Participant Name : ");

        String name =
                sc.nextLine();


        System.out.print(
                "Enter Email : ");

        String email =
                sc.nextLine();


        // ==========================================
        // CHECK EXISTING PARTICIPANT USING EMAIL
        // ==========================================

        int participantId =
                service.getParticipantIdByEmail(email);


        // ==========================================
        // IF PARTICIPANT DOES NOT EXIST
        // ==========================================

        if (participantId == 0) {

            ParticipantBean p =
                    new ParticipantBean();

            p.setParticipantName(name);
            p.setEmail(email);


            participantId =
                    service.addParticipant(p);


            if (participantId == 0) {

                System.out.println();

                System.out.println(
                        "Participant Registration Failed!");

                return;
            }

        } else {

            // ==========================================
            // EXISTING PARTICIPANT
            // ==========================================

            System.out.println();

            System.out.println(
                    "Existing Participant Found!");

            System.out.println(
                    "Participant ID : " +
                            participantId);
        }


        // ==========================================
        // REGISTER PARTICIPANT
        // ==========================================

        RegistrationBean r =
                new RegistrationBean(
                        0,
                        eventId,
                        participantId);


        boolean result =
                service.registerParticipant(r);


        // ==========================================
        // REGISTRATION RESULT
        // ==========================================

        if (result) {

            System.out.println();

            System.out.println(
                    "========================================");

            System.out.println(
                    "       REGISTRATION SUCCESSFUL!");

            System.out.println(
                    "========================================");

            System.out.println(
                    "Participant ID : " +
                            participantId);

            System.out.println(
                    "Participant Name : " +
                            name);

            System.out.println(
                    "Email : " +
                            email);

            System.out.println(
                    "Event : " +
                            eventName);

            System.out.println(
                    "Date : " +
                            eventDate);

            System.out.println(
                    "Venue : " +
                            venue);

            System.out.println(
                    "Remaining Seats : " +
                            (selectedEvent.getCapacity() - 1));

        } else {

            System.out.println();

            System.out.println(
                    "Registration Failed!");
        }
    }


    // ==========================================
    // DELETE PARTICIPANT
    // ==========================================

    public static void deleteParticipant() {

        System.out.println();

        System.out.println(
                "========== DELETE PARTICIPANT ==========");

        service.viewParticipants();

        System.out.println();

        System.out.print(
                "Enter Participant ID to Delete : ");

        String input =
                sc.nextLine();

        int id;

        try {

            id =
                    Integer.parseInt(input);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid number!");

            return;
        }


        boolean result =
                service.deleteParticipant(id);


        if (result) {

            System.out.println();

            System.out.println(
                    "Participant Deleted Successfully!");

        } else {

            System.out.println();

            System.out.println(
                    "Participant ID Not Found!");
        }
    }
}