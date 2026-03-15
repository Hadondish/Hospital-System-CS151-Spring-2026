package hospital;

import java.time.LocalDateTime;

public class Appointment {
    public enum Status {
        SCHEDULED,
        CANCELLED,
        COMPLETED
    }

    private LocalDateTime dateTime;
    private String appointmentId;
    private String appointmentType;
    private Status status;
    private Patient patient;
    private StaffMember provider;
    private Department department;

    public Appointment(String appointmentId, String appointmentType, LocalDateTime dateTime,
                       Patient patient, StaffMember provider, Department department) {
        this.appointmentId = appointmentId;
        this.appointmentType = appointmentType;
        this.dateTime = dateTime;
        this.patient = patient;
        this.provider = provider;
        this.department = department;
        this.status = Status.SCHEDULED;
    }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) throws SchedulingConflictException {}
    public void cancel(String reason) {}
    public void complete() {}
    public void reschedule(LocalDateTime newDateTime) throws SchedulingConflictException {}
    public boolean matches(String personId) { return false; }
    public void changeProvider(StaffMember newProvider) {}
}
