package hospital;

import java.time.LocalDateTime;

public class Appointment {

    // Attributes
    
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

    // Constructor

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

    // Getters and Setters

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public StaffMember getProvider() {return provider;}
    public void setProvider(StaffMember newProvider) { this.provider = newProvider;}

    // Methods

    // Cancels the appointment with a given reason
    public void cancel(String reason) {
        this.status = Status.CANCELLED;
        System.out.println("Appointment " + appointmentId + " cancelled. Reason: " + reason);
    }

    // Marks the appointment as completed
    public void complete() {
        this.status = Status.COMPLETED;
        System.out.println("Appointment " + appointmentId + " completed.");
    }

    // Reschedules the appointment to a new date and time
    public void reschedule(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }

    // Checks if the appointment involves a given person (either as patient or provider)
    public boolean matches(String personId) {
        return getPatient().getPatientId().equals(personId) || getProvider().getEmployeeId().equals(personId);
    }

}