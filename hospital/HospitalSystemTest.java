package hospital;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class HospitalSystemTest {

    /**
     * Verifies that registering a patient adds them to the hospital system
     * and allows them to be found by ID.
     */
    @Test
    void testRegisterPatientSuccess() throws Exception {
        HospitalSystem system = new HospitalSystem();

        Patient patient = new Patient("P001", "2000-01-01");
        system.registerPatient(patient);

        assertNotNull(system.findPatient("P001"));
    }

    /**
     * Verifies that the system enforces the maximum patient limit of 100.
     * The 101st patient should throw FullCapacityException.
     */
    @Test
    void testPatientCapacityLimit() throws Exception {
        HospitalSystem system = new HospitalSystem();

        for (int i = 0; i < 100; i++) {
            system.registerPatient(new Patient("P" + i, "2000-01-01"));
        }

        assertThrows(FullCapacityException.class, () -> {
            system.registerPatient(new Patient("P101", "2000-01-01"));
        });
    }

    /**
     * Verifies that hiring a staff member adds them to the hospital system
     * and allows them to be found by employee ID.
     */
    @Test
    void testHireStaffSuccess() throws Exception {
        HospitalSystem system = new HospitalSystem();

        StaffMember staff = new StaffMember("E001", "Doctor");
        system.hireStaff(staff);

        assertNotNull(system.findStaff("E001"));
    }

    /**
     * Verifies that the system enforces the maximum staff limit of 100.
     * The 101st staff member should throw FullCapacityException.
     */
    @Test
    void testStaffCapacityLimit() throws Exception {
        HospitalSystem system = new HospitalSystem();

        for (int i = 0; i < 100; i++) {
            system.hireStaff(new StaffMember("E" + i, "Nurse"));
        }

        assertThrows(FullCapacityException.class, () -> {
            system.hireStaff(new StaffMember("E101", "Nurse"));
        });
    }

    /**
     * Verifies that searching for a patient who does not exist
     * throws NotFoundException.
     */
    @Test
    void testFindPatientNotFound() {
        HospitalSystem system = new HospitalSystem();

        assertThrows(NotFoundException.class, () -> {
            system.findPatient("INVALID_PATIENT");
        });
    }

    /**
     * Verifies that searching for a staff member who does not exist
     * throws NotFoundException.
     */
    @Test
    void testFindStaffNotFound() {
        HospitalSystem system = new HospitalSystem();

        assertThrows(NotFoundException.class, () -> {
            system.findStaff("INVALID_STAFF");
        });
    }

    /**
     * Verifies that searching for an appointment that does not exist
     * throws NotFoundException.
     */
    @Test
    void testFindAppointmentNotFound() {
        HospitalSystem system = new HospitalSystem();

        assertThrows(NotFoundException.class, () -> {
            system.findAppointment("INVALID_APPOINTMENT");
        });
    }

    /**
     * Verifies that scheduling a valid appointment succeeds
     * and the appointment can be found afterward.
     */
    @Test
    void testScheduleAppointmentSuccess() throws Exception {
        HospitalSystem system = new HospitalSystem();

        Department dept = new FamilyCareDepartment("D001", "Family Care", 20, 10);
        system.addDepartment(dept);

        Patient patient = new Patient("P001", "2000-01-01");
        StaffMember provider = new StaffMember("E001", "Doctor");

        system.registerPatient(patient);
        system.hireStaff(provider);

        Appointment appointment = new Appointment(
                "A001",
                "Checkup",
                LocalDateTime.of(2026, 3, 28, 10, 0),
                patient,
                provider,
                dept
        );

        system.scheduleAppointment(appointment);

        assertNotNull(system.findAppointment("A001"));
    }

    /**
     * Verifies that the system prevents a provider from having
     * two appointments at the same time.
     */
    @Test
    void testScheduleAppointmentConflict() throws Exception {
        HospitalSystem system = new HospitalSystem();

        Department dept = new FamilyCareDepartment("D001", "Family Care", 20, 10);
        system.addDepartment(dept);

        Patient p1 = new Patient("P001", "2000-01-01");
        Patient p2 = new Patient("P002", "2001-02-02");
        StaffMember provider = new StaffMember("E001", "Doctor");

        system.registerPatient(p1);
        system.registerPatient(p2);
        system.hireStaff(provider);

        LocalDateTime sameTime = LocalDateTime.of(2026, 3, 28, 11, 0);

        Appointment a1 = new Appointment("A001", "Checkup", sameTime, p1, provider, dept);
        Appointment a2 = new Appointment("A002", "Checkup", sameTime, p2, provider, dept);

        system.scheduleAppointment(a1);

        assertThrows(SchedulingConflictException.class, () -> {
            system.scheduleAppointment(a2);
        });
    }

    /**
     * Verifies that cancelling an appointment changes its status to CANCELLED.
     */
    @Test
    void testCancelAppointmentChangesStatus() throws Exception {
        HospitalSystem system = new HospitalSystem();

        Department dept = new FamilyCareDepartment("D001", "Family Care", 20, 10);
        system.addDepartment(dept);

        Patient patient = new Patient("P001", "2000-01-01");
        StaffMember provider = new StaffMember("E001", "Doctor");

        system.registerPatient(patient);
        system.hireStaff(provider);

        Appointment appointment = new Appointment(
                "A010",
                "Checkup",
                LocalDateTime.of(2026, 3, 29, 9, 0),
                patient,
                provider,
                dept
        );

        system.scheduleAppointment(appointment);
        system.cancelAppointment("A010", "Patient unavailable");

        assertEquals(Appointment.Status.CANCELLED, appointment.getStatus());
    }

    /**
     * Verifies that completing an appointment changes its status to COMPLETED.
     */
    @Test
    void testCompleteAppointmentChangesStatus() {
        Patient patient = new Patient("P001", "2000-01-01");
        StaffMember provider = new StaffMember("E001", "Doctor");
        Department dept = new FamilyCareDepartment("D001", "Family Care", 20, 10);

        Appointment appointment = new Appointment(
                "A011",
                "Checkup",
                LocalDateTime.of(2026, 3, 29, 10, 0),
                patient,
                provider,
                dept
        );

        appointment.complete();

        assertEquals(Appointment.Status.COMPLETED, appointment.getStatus());
    }

    /**
     * Verifies that fireStaff removes a staff member from the system,
     * so they can no longer be found afterward.
     */
    @Test
    void testFireStaffRemovesStaffFromSystem() throws Exception {
        HospitalSystem system = new HospitalSystem();

        StaffMember staff = new StaffMember("E001", "Doctor");
        system.hireStaff(staff);

        system.fireStaff("E001");

        assertThrows(NotFoundException.class, () -> {
            system.findStaff("E001");
        });
    }
}