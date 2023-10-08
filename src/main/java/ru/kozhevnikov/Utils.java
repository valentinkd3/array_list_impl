package ru.kozhevnikov;

import java.util.Comparator;

/**
 * Вспомогательный класс, содержащий статические методы для сортировки слиянием объектов,
 * реализующих интерфейс {@code CustomList}.
 *
 * @author Kozhevnikov Valentin
 * @see CustomList
 */
public class Utils {
    /**
     * Сортирует передаваемый в качестве параметра список, реализующий интерфейс {@code CustomList},
     * с помощью алгоритма сортировки слиянием в соответствии с методом
     * {@code compare} компаратора - объекта класса, реализующего функциональный
     * интерфейс {@code Comparator}.
     *
     * @param list сортируемый список, реализующий интерфейс {@code CustomList}
     * @param comparator объект класса, реализующий функциональный интерфейс интерфейс {@code Comparator}
     * @param <E> тип элементов в списке
     */
    public static <E> void mergeSort(CustomList<E> list, Comparator<? super E> comparator) {
        mergeSort(list, list.size(), comparator);
    }
    /**
     * Сортирует передаваемый в качестве параметра список, реализующий интерфейс {@code CustomList},
     * с помощью алгоритма сортировки слиянием в натуральном порядке.
     *
     * @param list сортируемый список, реализующий интерфейс {@code CustomList}
     * @param <E> тип элементов в списке
     */
    public static <E extends Comparable<? super E>> void mergeSort(CustomList<E> list) {
        mergeSort(list, list.size());
    }

    private static <E> void mergeSort(CustomList<E> list, int n, Comparator<? super E> comparator) {
        if (n < 2) {
            return;
        }

        int mid = n / 2;

        CustomList<E> left = list.subList(0, mid);
        CustomList<E> right = list.subList(mid, n);

        mergeSort(left, left.size(), comparator);
        mergeSort(right, right.size(), comparator);

        mergeCollections(list, left, right, left.size(), right.size(), comparator);
    }

    private static <E> void mergeCollections(CustomList<E> list, CustomList<E> left,
                                             CustomList<E> right, int leftLength, int rightLength,
                                             Comparator<? super E> comparator) {
        int i = 0, j = 0, k = 0;
        while (i < leftLength && j < rightLength) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }
        while (i < leftLength) {
            list.set(k++, left.get(i++));
        }
        while (j < rightLength) {
            list.set(k++, right.get(j++));
        }
    }

    private static <E extends Comparable<? super E>> void mergeSort(CustomList<E> list, int n) {
        if (n < 2) {
            return;
        }

        int mid = n / 2;

        CustomList<E> left = list.subList(0, mid);
        CustomList<E> right = list.subList(mid, n);

        mergeSort(left, left.size());
        mergeSort(right, right.size());

        mergeCollections(list, left, right, left.size(), right.size());
    }

    private static <E extends Comparable<? super E>> void mergeCollections(CustomList<E> list,
                                                                           CustomList<E> left,
                                                                           CustomList<E> right,
                                                                           int leftLength,
                                                                           int rightLength) {
        int i = 0, j = 0, k = 0;
        while (i < leftLength && j < rightLength) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }
        while (i < leftLength) {
            list.set(k++, left.get(i++));
        }
        while (j < rightLength) {
            list.set(k++, right.get(j++));
        }
    }
}
