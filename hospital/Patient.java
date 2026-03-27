package hospital;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient in the hospital system.
 * A patient can be admitted to, discharged from, or transferred between departments,
 * and can store visit summaries from prior care.
 */
public class Patient {

    private String patientId;
    private String dateOfBirth;
    private PatientStatus status;
    private Department assignedDepartment;
    private List<VisitSummary> visitSummaries;

    /**
     * Constructs a patient with a patient ID and date of birth.
     * New patients start in REGISTERED status with no assigned department.
     *
     * @param patientId unique patient ID
     * @param dateOfBirth patient's date of birth
     */
    public Patient(String patientId, String dateOfBirth) {
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.status = PatientStatus.REGISTERED;
        this.assignedDepartment = null;
        this.visitSummaries = new ArrayList<>();
    }

    /**
     * Returns the patient ID.
     *
     * @return patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Updates the patient ID if the value is not empty.
     *
     * @param patientId new patient ID
     */
    public void setPatientId(String patientId) {
        if (patientId != null && !patientId.trim().isEmpty()) {
            this.patientId = patientId;
        } else {
            System.out.println("Patient ID cannot be empty.");
        }
    }

    /**
     * Returns the patient's date of birth.
     *
     * @return date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Updates the date of birth if the value is not empty.
     *
     * @param dateOfBirth new date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        if (dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
            this.dateOfBirth = dateOfBirth;
        } else {
            System.out.println("Date of birth cannot be empty.");
        }
    }

    /**
     * Returns the current patient status.
     *
     * @return current status
     */
    public PatientStatus getStatus() {
        return status;
    }

    /**
     * Updates the patient's status if the value is not null.
     *
     * @param status new patient status
     */
    public void setStatus(PatientStatus status) {
        if (status != null) {
            this.status = status;
        } else {
            System.out.println("Status cannot be null.");
        }
    }

    /**
     * Returns the department currently assigned to this patient.
     *
     * @return assigned department, or null if none
     */
    public Department getAssignedDepartment() {
        return assignedDepartment;
    }

    /**
     * Updates the assigned department reference.
     *
     * @param assignedDepartment new assigned department
     */
    public void setAssignedDepartment(Department assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    /**
     * Returns the list of visit summaries.
     *
     * @return visit summary list
     */
    public List<VisitSummary> getVisitSummaries() {
        return visitSummaries;
    }

    /**
     * Replaces the visit summary list if the provided list is not null.
     *
     * @param visitSummaries new visit summary list
     */
    public void setVisitSummaries(List<VisitSummary> visitSummaries) {
        if (visitSummaries != null) {
            this.visitSummaries = visitSummaries;
        } else {
            System.out.println("Visit summaries list cannot be null.");
        }
    }

    /**
     * Admits the patient to the given department if possible.
     * The department handles the list update and the department reference sync.
     *
     * @param dept department to admit the patient to
     */
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

        // Let the department manage the relationship update.
        dept.addPatient(this);
        status = PatientStatus.ADMITTED;
    }

    /**
     * Discharges the patient from the currently assigned department.
     * The department handles removing the patient and clearing the reference.
     */
    public void discharge() {
        if (assignedDepartment == null) {
            System.out.println("Patient is not currently assigned to a department.");
            return;
        }

        // Store a temporary reference because removePatient may clear assignedDepartment.
        Department currentDepartment = assignedDepartment;
        currentDepartment.removePatient(this);
        status = PatientStatus.DISCHARGED;
    }

    /**
     * Transfers the patient to a new department if the destination can accept them.
     * Uses department methods so the department lists and patient reference stay in sync.
     *
     * @param newDept destination department
     * @throws FullCapacityException if the destination department is full
     */
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

        // Let department logic handle moving the patient and updating references.
        assignedDepartment.movePatient(this, newDept);
        status = PatientStatus.TRANSFERRED;
    }

    /**
     * Adds a visit summary to this patient's record.
     *
     * @param vs visit summary to add
     */
    public void addVisitSummary(VisitSummary vs) {
        if (vs == null) {
            System.out.println("Visit summary cannot be null.");
            return;
        }

        visitSummaries.add(vs);
    }

    /**
     * Returns a readable string containing all visit summaries.
     * Each visit summary is placed on a new line.
     *
     * @return formatted visit summary list or a message if none exist
     */
    public String viewVisitSummaries() {
        // Check if there are no visit summaries recorded for this patient
        if (visitSummaries.isEmpty()) {
            return "No visit summaries found.";
        }

        // StringBuilder is used to efficiently build a single output string
        // instead of repeatedly creating new String objects
        StringBuilder sb = new StringBuilder();

        // Loop through each VisitSummary object in the list
        for (VisitSummary summary : visitSummaries) {

            // Convert each VisitSummary to its string representation
            // (implicitly calls summary.toString())
            // and append it to the result with a newline for formatting

            sb.append(summary.toString()).append("\n");
        }

        // Convert the accumulated result into a single String and return it
        return sb.toString();
    }

    /**
     * Returns a summary string describing the patient's current state.
     *
     * @return formatted patient string
     */
    @Override
    public String toString() {
        String departmentName = (assignedDepartment == null)
                ? "None"
                : assignedDepartment.getName();

        return "Patient{"
                + "patientId='" + patientId + '\''
                + ", dateOfBirth='" + dateOfBirth + '\''
                + ", status=" + status
                + ", assignedDepartment=" + departmentName
                + ", visitSummaryCount=" + visitSummaries.size()
                + '}';
    }
}

/**
 * Represents the lifecycle status of a patient in the hospital system.
 */
enum PatientStatus {
    REGISTERED,
    ADMITTED,
    TRANSFERRED,
    DISCHARGED
}