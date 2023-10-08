package ru.kozhevnikov;

import org.junit.jupiter.api.Test;
import ru.kozhevnikov.util.Student;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void mergeSortWithComparator() {
        CustomList<Student> actual = new CustomArrayList<>();
        Student student1 = new Student("Pavel", 18, 'm', 1);
        Student student2 = new Student("Marina", 23, 'f', 5);
        Student student3 = new Student("Victor", 19, 'm', 2);
        Student student4 = new Student("Julia", 21, 'f', 3);
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);
        CustomList<Student> expected = new CustomArrayList<>();
        expected.add(student1);
        expected.add(student3);
        expected.add(student4);
        expected.add(student2);

        Utils.mergeSort(actual, (o1,o2) -> o1.getCourse() - o2.getCourse());

        assertEquals(expected,actual);
    }

    @Test
    void testMergeSortWithoutComparator() {
        CustomList<Integer> actual = new CustomArrayList<>();
        for (int i = 100; i >= 0; i--) {
            actual.add(i);
        }
        CustomList<Integer> expected = new CustomArrayList<>();
        for (int i = 0; i <= 100; i++) {
            expected.add(i);
        }

        Utils.mergeSort(actual);

        assertEquals(expected,actual);
    }
}