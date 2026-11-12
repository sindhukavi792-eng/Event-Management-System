package bean;

public class RegistrationBean {

    private int registrationId;
    private int eventId;
    private int participantId;

    public RegistrationBean() {
    }

    public RegistrationBean(int registrationId,
                            int eventId,
                            int participantId) {

        this.registrationId = registrationId;
        this.eventId = eventId;
        this.participantId = participantId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }
}