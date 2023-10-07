package ru.kozhevnikov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kozhevnikov.util.Student;


import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    @BeforeEach
    public void initStudents(){
        student1 = new Student("Pavel", 18, 'm', 1);
        student2 = new Student("Marina", 23, 'f', 5);
        student3 = new Student("Victor", 19, 'm', 2);
        student4 = new Student("Julia", 21, 'f', 3);
    }
    @Test
    void initialList_NOT_NULL() {
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        assertNotNull(customList);
    }

    @Test
    void returnCorrectSize() {
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        assertEquals(0, customList.size());
    }

    @Test
    void addElementToEmptyList() {
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        customList.add(13);
        assertEquals(1, customList.size());
        assertEquals(13, customList.get(customList.size() - 1));
    }

    @Test
    void addMultipleElementsToEmptyList() {
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        for (int i = 0; i < 5; i++) {
            customList.add(i);
        }
        assertEquals(5, customList.size());
        assertEquals(0, customList.get(0));
        assertEquals(1, customList.get(1));
        assertEquals(2, customList.get(2));
        assertEquals(3, customList.get(3));
        assertEquals(4, customList.get(4));
    }
    @Test
    void addElementToMiddleOfList(){
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        customList.add(1);
        customList.add(3);

        customList.add(1,2);

        assertEquals(3, customList.size());
        assertEquals(2, customList.get(1));
    }
    @Test
    void checkTheCorrectListExpansion(){
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        for (int i = 0; i < 10; i++) {
            customList.add(i);
        }
        assertEquals(10,customList.size());
        customList.add(10);
        customList.add(11);
        customList.add(12);
        assertEquals(13, customList.size());
        assertEquals(10, customList.get(10));
        assertEquals(11, customList.get(11));
        assertEquals(12, customList.get(12));
    }
    @Test
    void checkAddNull(){
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        customList.add(null);
        assertEquals(null,customList.get(0));
    }
    @Test
    void getElementOutOfBounds(){
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        int indexOutOfBounds = 0;

        Throwable outOfBoundsException = assertThrows(IndexOutOfBoundsException.class,
                () -> customList.get(indexOutOfBounds));

        assertEquals(String.format("Index %d out of bounds for length %d", indexOutOfBounds, customList.size()),
                outOfBoundsException.getMessage());
    }
    @Test
    void removeElementFromEmptyList(){
        CustomArrayList<String> customList = new CustomArrayList<>();

        Throwable outOfBoundsException = assertThrows(IndexOutOfBoundsException.class,
                () -> customList.remove(1));

        assertEquals(String.format("Index 1 out of bounds for length %d", customList.size()),
                outOfBoundsException.getMessage());
    }
    @Test
    void getIndexByElement() {
        CustomArrayList<Student> students = new CustomArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        assertEquals(2, students.indexOf(student3));

    }
    @Test
    void shouldClear(){
        CustomArrayList<Integer> customList = new CustomArrayList<>();
        for (int i = 0; i < 10; i++) {
            customList.add(i);
        }
        customList.clear();
        assertEquals(0,customList.size());
    }
    @Test
    void removeFirstElementByIndex() {
        CustomArrayList<Student> actual = new CustomArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);

        CustomArrayList<Student> expected = new CustomArrayList<>();
        expected.add(student2);
        expected.add(student3);
        expected.add(student4);

        actual.remove(0);

        assertEquals(expected,actual);
    }
    @Test
    void removeLastElementByIndex() {
        CustomArrayList<Student> actual = new CustomArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);

        CustomArrayList<Student> expected = new CustomArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student3);

        actual.remove(actual.size()-1);

        assertEquals(expected,actual);
    }
    @Test
    void removeMiddleElementByIndex() {
        CustomArrayList<Student> actual = new CustomArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);

        CustomArrayList<Student> expected = new CustomArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student4);

        actual.remove(2);

        assertEquals(expected,actual);
    }

    @Test
    void removeElement() {
        CustomArrayList<Student> actual = new CustomArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);

        CustomArrayList<Student> expected = new CustomArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student4);

        actual.remove(student3);

        assertEquals(expected,actual);
    }
    @Test
    void removeIf() {
        CustomArrayList<Student> actual = new CustomArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);

        CustomArrayList<Student> expected = new CustomArrayList<>();
        expected.add(student2);
        expected.add(student4);

        actual.removeIf(e -> e.getSex() == 'm');

        assertEquals(expected,actual);
    }
    @Test
        void quickSort() {
        CustomArrayList<Student> actual = new CustomArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
        actual.add(student4);

        CustomArrayList<Student> expected = new CustomArrayList<>();
        expected.add(student1);
        expected.add(student3);
        expected.add(student4);
        expected.add(student2);

        actual.sort(((o1, o2) -> o1.getAge() - o2.getAge()));

        assertEquals(expected,actual);
    }
}