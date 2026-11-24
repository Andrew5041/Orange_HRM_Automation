package org.orange_hrm.other_homeworks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.orange_hrm.models.Address;
import org.orange_hrm.models.TestEmployee;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TestEmployeesClass {

    private static final List<TestEmployee> testEmployees = List.of(
            new TestEmployee("Anna", 28, "QA Engineer", true,
                    new Address("Warszawa", "Polna")),
            new TestEmployee("Bartek", 34, "Java Developer", true,
                    new Address("Krakow", "Dluga")),
            new TestEmployee("Celina", 25, "Manual Tester", false,
                    new Address("Gdansk", "Morska")),
            new TestEmployee("Daniel", 31, "QA Engineer", true,
                    new Address("Krakow", "Lipowa")),
            new TestEmployee("Ewa", 29, "Java Developer", false,
                    new Address("Warszawa", "Lesna")),
            new TestEmployee("Filip", 38, "QA Engineer", true,
                    new Address("Gdansk", "Krotka"))
    );

    public static void main(String[] args) {

        //1
        System.out.println("1");
        getActiveEmployees(testEmployees).forEach(e -> System.out.println(e.name()));

        //2
        System.out.println("2");
        System.out.println(getQAEngineerEmployees(testEmployees));

        //3
        System.out.println("3");
        getEmployeesFromWarsaw(testEmployees).forEach(e -> System.out.println(e.name()));

        //4
        System.out.println("4");
        System.out.println(getEmployeesWithMinimumAge30(testEmployees));

        //5
        System.out.println("5");
        System.out.println(getCitiesWithoutDuplicates(testEmployees));

        //6
        System.out.println("6");
        getFirstEmployeeWithJobTitleJavaDeveloper(testEmployees).ifPresent(e -> System.out.println(e.name()));

    }

    public static List<TestEmployee> getActiveEmployees(List<TestEmployee> employees) {

        return employees.stream()
                .filter(e -> e.activity())
                .toList();
    }

    public static List<String> getQAEngineerEmployees(List<TestEmployee> employees) {

        return employees.stream()
                .filter(e -> e.jobTitle().equals("QA Engineer"))
                .map(e -> e.name())
                .toList();
    }

    public static List<TestEmployee> getEmployeesFromWarsaw(List<TestEmployee> employees) {

        return employees.stream()
                .filter(e -> e.address().city().equals("Warszawa"))
                .toList();
    }

    public static List<String> getEmployeesWithMinimumAge30(List<TestEmployee> employees) {

        return employees.stream()
                .filter(e -> e.age() >= 30)
                .sorted(Comparator.comparingInt(e -> e.age()))
                .map(e -> e.name())
                .toList();
    }

    public static List<String> getCitiesWithoutDuplicates(List<TestEmployee> employees) {

        return employees.stream()
                .map(e -> e.address().city())
                .distinct()
                .sorted()
                .toList();
    }

    public static Optional<TestEmployee> getFirstEmployeeWithJobTitleJavaDeveloper(List<TestEmployee> employees) {

        return employees.stream()
                .filter(e -> e.jobTitle().equals("Java Developer"))
                .findFirst();
    }

    public static Optional<TestEmployee> findEmployeeByName(List<TestEmployee> employees, String name) {

        return employees.stream()
                .filter(e -> (e.name().equals(name)))
                .findFirst();
    }

    public static Optional<TestEmployee> findEmployeeByStatus(List<TestEmployee> employees) {

        return employees.stream()
                .filter(e -> !e.activity())
                .findFirst();
    }

    public static String getActiveEmployeeJobTitle(List<TestEmployee> employees, String name) {

        return findEmployeeByName(employees, name)
                .filter(e -> e.activity())
                .map(e -> e.jobTitle())
                .orElse("No active user");
    }

    //7
    @Test
    public void onlyExistingEmployeesShouldBeFoundByName() {

        Assertions.assertTrue(findEmployeeByName(testEmployees, "Anna").isPresent());
        Assertions.assertEquals("Anna", findEmployeeByName(testEmployees, "Anna").map(e -> e.name()).orElse("User name is not equal"));
        Assertions.assertFalse(findEmployeeByName(testEmployees, "Piotr").isPresent());
    }


    //8
    @Test
    public void onlyForExistingEmployeesCityShouldBeFound() {

        Assertions.assertEquals("Warszawa",
                findEmployeeByName(testEmployees, "Anna")
                        .map(e -> e.address().city())
                        .orElse("User was not found"));

        Assertions.assertEquals("User was not found", findEmployeeByName(testEmployees, "Piotr")
                .map(e -> e.address().city())
                .orElse("User was not found"));
    }

    //9
    @Test
    public void inactiveEmployeeShouldBeFound() {

        Assertions.assertTrue(findEmployeeByStatus(testEmployees).isPresent());
        Assertions.assertEquals("Celina", findEmployeeByStatus(testEmployees).map(e -> e.name()).orElse("User was not found"));
        Assertions.assertEquals("Manual Tester", findEmployeeByStatus(testEmployees).map(e -> e.jobTitle()).orElse("User was not found"));
    }

    //10
    @Test
    public void shouldReturnJobTitleOnlyForActiveEmployee() {

        Assertions.assertEquals("QA Engineer", getActiveEmployeeJobTitle(testEmployees, "Anna"));
        Assertions.assertEquals("No active user", getActiveEmployeeJobTitle(testEmployees, "Ewa"));
        Assertions.assertEquals("No active user", getActiveEmployeeJobTitle(testEmployees, "Piotr"));
    }
}







