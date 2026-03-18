package hospital;

import java.util.*;

public class Patient {

    private String patientId;
    private String dateOfBirth;
    private PatientStatus status;
    private Department assignedDepartment;
    private List<VisitSummary> visitSummaries;

    public Patient(String patientId, String dateOfBirth) {
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.status = PatientStatus.REGISTERED;
        this.assignedDepartment = null;
        this.visitSummaries = new ArrayList<>();
    }

    //setters and getters

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        if (patientId != null && !patientId.trim().isEmpty()) {
            this.patientId = patientId;
        } else {
            System.out.println("Patient ID cannot be empty.");
        }
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
            this.dateOfBirth = dateOfBirth;
        } else {
            System.out.println("Date of birth cannot be empty.");
        }
    }

    public PatientStatus getStatus() {
        return status;
    }

    public void setStatus(PatientStatus status) {
        if (status != null) {
            this.status = status;
        } else {
            System.out.println("Status cannot be null.");
        }
    }

    public Department getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(Department assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    public List<VisitSummary> getVisitSummaries() {
        return visitSummaries;
    }

    public void setVisitSummaries(List<VisitSummary> visitSummaries) {
        if (visitSummaries != null) {
            this.visitSummaries = visitSummaries;
        } else {
            System.out.println("Visit summaries list cannot be null.");
        }
    }

    public void admitTo(Department dept) {
        if (dept == null) {
            System.out.println("Department cannot be null.");
            return;
        }

        if (assignedDepartment != null) {
            System.out.println("Patient is already assigned to a department.");
            return;
        }

        if (!dept.canAcceptPatient(this)) {
            System.out.println("Department cannot accept this patient.");
            return;
        }

        dept.addPatient(this);
        assignedDepartment = dept;
        status = PatientStatus.ADMITTED;
    }

    public void discharge() {
        if (assignedDepartment == null) {
            System.out.println("Patient is not currently assigned to a department.");
            return;
        }

        assignedDepartment.removePatient(this);
        assignedDepartment = null;
        status = PatientStatus.DISCHARGED;
    }

    public void transferTo(Department newDept) throws FullCapacityException {
        if (newDept == null) {
            System.out.println("New department cannot be null.");
            return;
        }

        if (assignedDepartment == null) {
            System.out.println("Patient is not currently assigned to any department.");
            return;
        }

        if (assignedDepartment == newDept) {
            System.out.println("Patient is already in that department.");
            return;
        }

        if (!newDept.canAcceptPatient(this)) {
            throw new FullCapacityException("Target department is full.");
        }

        assignedDepartment.removePatient(this);
        newDept.addPatient(this);
        assignedDepartment = newDept;
        status = PatientStatus.TRANSFERRED;
    }

    public void addVisitSummary(VisitSummary vs) {
        if (vs == null) {
            System.out.println("Visit summary cannot be null.");
            return;
        }

        visitSummaries.add(vs);
    }

    public String viewVisitSummaries() {
        if (visitSummaries.isEmpty()) {
            return "No visit summaries found.";
        }

        StringBuilder sb = new StringBuilder();
        for (VisitSummary summary : visitSummaries) {
            sb.append(summary).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        String departmentName = (assignedDepartment == null)
                ? "None"
                : assignedDepartment.getName();

        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", status=" + status +
                ", assignedDepartment=" + departmentName +
                ", visitSummaryCount=" + visitSummaries.size() +
                '}';
    }
}

enum PatientStatus {
    REGISTERED,
    ADMITTED,
    TRANSFERRED,
    DISCHARGED
}
