package hospital;

import java.util.*;

public class Patient {
    public enum Status {
        ACTIVE,
        ADMITTED,
        DISCHARGED
    }

    private String patientId;
    private String dateOfBirth;
    private Status status;
    private Department assignedDepartment;
    private List<VisitSummary> visitSummaries;

    public Patient(String patientId, String dateOfBirth) {
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.status = Status.ACTIVE;
        this.visitSummaries = new ArrayList<>();
    }

    public String getPatientID() { return patientId; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getStatus() { return status.name(); }
    public String getAssignedDepartment() { return assignedDepartment == null ? "" : assignedDepartment.getDepartmentType(); }
    public String getVisitSummaries() { return ""; }
    public void admitTo(Department dept) {}
    public void discharge() {}
    public void transferTo(Department newDept) throws FullCapacityException {}
    public void addVisitSummary(VisitSummary vs) {}
    public String viewVisitSummaries() { return ""; }

    @Override
    public String toString() { return patientId; }
}
