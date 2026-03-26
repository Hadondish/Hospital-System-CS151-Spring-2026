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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void cancel(String reason) {
        this.status = Status.CANCELLED;
        System.out.println("Appointment " + appointmentId + " cancelled. Reason: " + reason);
    }

    public void complete() {
        this.status = Status.COMPLETED;
        System.out.println("Appointment " + appointmentId + " completed.");
    }

    public void reschedule(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }

    public boolean matches(String personId) {
        return patient.getPatientId().equals(personId)
                || provider.getEmployeeId().equals(personId);
    }

    public void changeProvider(StaffMember newProvider) {
        this.provider = newProvider;
    }

    public Department getDepartment() {
        return department;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public StaffMember getProvider() {
        return provider;
    }
}