package ru.kozhevnikov;

import java.util.*;
import java.util.function.Predicate;

/**
 * Кастомная реализация класса {@code ArrayList}. Реализует такие основные
 * операции по работе со списками, как получение, добавление, удаление элементов
 * списка, а также их сортировку. Позволяет хранить все элементы, включая {@code null}.
 * Клаасс {@code CustomArrayList} не является потокобезопасным.
 *
 * <p>У каждого экземпляра {@code CustomArrayList} есть вместимость - размер
 * массива, позволяющего хранить элементы в списке. Вместимость не может быть меньше
 * размера списка. По мере добавления элементов вместимость автоматически увеличивается.
 *
 * @param <E> тип элементов в списке
 *
 * @author Kozhevnikov Valentin
 * @see    Collection
 * @see    List
 * @see    ArrayList
 */
public class CustomArrayList<E> {
    /**
     * Размер списка
     */
    private int size;
    /**
     * Размер массива, содержащего элементы списка
     */
    private int capacity;
    /**
     * Массив, содержащий элементы списка
     */
    private E[] elementData;

    /**
     * Конструктор для создания пустого списка с вместимостью = 10
     */
    public CustomArrayList() {
        capacity = 10;
        elementData = (E[]) new Object[capacity];
    }

    /**
     * Конструктор для создания пустого списка с указанной вместимостью.
     *
     * @param initCapacity начальная вместимость списка
     * @throws IllegalArgumentException в случае передачи отрицательной
     * вместимости
     */
    public CustomArrayList(int initCapacity) {
        if (initCapacity < 0)
            throw new IllegalArgumentException("Illegal capacity");

        capacity = initCapacity;
        elementData = (E[]) new Object[capacity];
    }

    /**
     * Добавляет передаваемый элемент в конец списка.
     *
     * @param element элемент, добавляемый в список
     */
    public void add(E element) {
        if (isNeedToIncreaseCapacity()) {
            increaseCapacity();
        }
        elementData[size] = element;
        size++;
    }

    /**
     * Добавляет передаваемый элемент в указанное место списка по индексу.
     * Сдвигает все элементы, следующие за указанным, на одну позицию вправо.
     *
     * @param index индекс, по которому элемент будет добавлен в список
     * @param element элемент, добавляемый в список
     * @throws IndexOutOfBoundsException если индекс отрицательный или не меньше
     * размера списка
     */
    public void add(int index, E element) {
        checkIndexRange(index);

        if (isNeedToIncreaseCapacity()) {
            increaseCapacity();
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }
    /**
     * Возвращает элемент из списка по его индексу.
     *
     * @param index индекс возвращаемоего элемента
     * @return искомый элемент списка
     * @throws IndexOutOfBoundsException если индекс отрицательный или не меньше
     * размера списка
     */
    public E get(int index) {
        checkIndexRange(index);

        return elementData[index];
    }

    /**
     * Возвращает индекс певрго элемента списка, соответсвующего переданному элементу,
     * или -1 в случае его отсутствия.
     *
     * @param element элемент списка, индекс которого мы хотим получить
     * @return индекс элемента списка
     */
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(element)) return i;
        }
        return -1;
    }
    /**
     * Удаляет элемент из списка по его индексу.Сдвигает все элементы,
     * следующие за удаляемым, на одну позицию влево.
     *
     * @param index индекс удаляемого элемента
     * @throws IndexOutOfBoundsException если индекс отрицательный или не меньше
     * размера списка
     */
    public void remove(int index) {
        checkIndexRange(index);

        System.arraycopy(elementData, index + 1, elementData, index, size - index);
        size--;
    }
    private void checkIndexRange(int index){
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));
    }

    /**
     * Удаляет перый элемент из списка, соответсвующий переданному элементу, в случае
     * его наличия. В случае отсутствия такого элемента список не изменяется.
     *
     * @param element жлемент, который необходимо удалить из списка
     * @return {@code true} если список содержит указанный элемент
     */
    public boolean remove(E element) {
        int index = indexOf(element);
        if (index != -1){
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Удаляет элементы из списка, удовлетворяющие передаваемому условию. В случае
     * отсуствия таких элементов список не изменяется.
     *
     * @param filter условие, в соответствии с которым элементы удаляются из списка
     * @throws NullPointerException если передаваемое условие равно {@code null}
     */
    public void removeIf(Predicate<? super E> filter) {
        if (filter == null)
            throw new NullPointerException("");

        int left = 0;
        for (int i = 0; i < size; i++) {
            E element = get(i);
            if (!filter.test(element)){
                elementData[left] = element;
                left++;
            }
        }
        for (int i = left; i < size; i++) {
            elementData[i] = null;
        }
        size = left;
    }

    /**
     * Удаляет все элементы из списка.
     */
    public void clear(){
        int tmpSize = size;
        for (int i = 0; i < tmpSize; i++) {
            elementData[i] = null;
            size--;
        }
    }

    /**
     * Возвращает размер списка.
     *
     * @return размер списка
     */
    public int size() {
        return size;
    }

    /**
     * Сортирует список с помощью алгоритма быстрой сортировки в соответствии с методом
     * {@code compare} компаратора - объекта класса, реализующего функциональный
     * интерфейс {@code Comparator}.
     *
     * <p> При реализации алгоритма быстрой сортировки в качестве опорного элемента на каждом
     * шаге выбирается случайный элемент, что уменьшает вероятность возникнования ниахудшего
     * случая при сортировке, когда список уже отсортирован или почти отсортирован.
     *
     * @param comparator
     */
    public void sort(Comparator<? super E> comparator){
        quickSort(0, size-1, comparator);
    }
    private void quickSort(int begin, int end, Comparator<? super E> comparator){
        if (begin < end){
            int partitionIndex = getPartitionIndex(begin, end, comparator);

            quickSort(partitionIndex+1, end, comparator);
            quickSort(begin,partitionIndex-1, comparator);
        }
    }
    private int getPartitionIndex(int begin, int end, Comparator<? super E> comparator){
        int randomElementIndex = getRandomElementIndex(begin,end);
        swap(randomElementIndex, end);
        E pivot = elementData[end];
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (comparator.compare(elementData[j], pivot) <= 0){
                i++;
                swap(i,j);
            }
        }
        swap(i+1, end);
        return i+1;
    }
    private int getRandomElementIndex(int begin, int end){
        Random random = new Random();
        return random.nextInt(end-begin)+begin;
    }
    private void swap(int index1, int index2){
        E tmp = elementData[index1];
        elementData[index1] = elementData[index2];
        elementData[index2] = tmp;
    }
    private void increaseCapacity() {
        capacity = (capacity * 3) / 2 + 1;
        E[] modifiedArray = (E[]) new Object[capacity];

        System.arraycopy(elementData, 0, modifiedArray, 0, size);
        elementData = modifiedArray;
    }
    private boolean isNeedToIncreaseCapacity() {
        return size + 1 >= capacity;
    }

    /**
     * Сравнивает передаваемый объект с этим списком на равенство.
     * Возвращается true только в том случае, когда указанный объект является
     * списком и содержит те же элементы в том же порядке, что и этот список.
     *
     * @param o объект для сравнения с этим списком
     * @return true, если указанный объект равен этому списку, в противном случае - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return size == that.size && Arrays.equals(elementData, that.elementData);
    }

    /**
     * Возвращает хэш-код для этого списка. Хэш-код рассчитывается
     * на основе размера спсика и его содержимого.
     *
     * @return хэш-код для этого списка
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elementData);
        return result;
    }

    /**
     * Возвращает строковое представление этого списка. Строка содержит
     * элементы списка в порядке их добавления, заключенные в квадратные
     * скобки ("[]") и разделенные запятыми.
     *
     * @return строковое представление списка
     */
    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i]).append(", ");
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), "");
        sb.append("]");
        return sb.toString();
    }
}
