package ru.kozhevnikov;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;

public class CustomArrayList<E> {
    private int size;
    private int capacity;
    private E[] elementData;
    public CustomArrayList() {
        capacity = 10;
        elementData = (E[]) new Object[capacity];
    }

    public CustomArrayList(int initCapacity) {
        capacity = initCapacity;
        elementData = (E[]) new Object[capacity];
    }

    public void add(E element) {
        if (isNeedToIncreaseCapacity()) {
            increaseCapacity();
        }
        elementData[size] = element;
        size++;
    }

    public void add(int index, E element) {
        if (isNeedToIncreaseCapacity()) {
            increaseCapacity();
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    public E get(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));

        return (E) elementData[index];
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(o)) return i;
        }
        return -1;
    }

    public void remove(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", index, size));

        System.arraycopy(elementData, index + 1, elementData, index, size - index);
        size--;
    }

    public void remove(Object o) {
        int index = indexOf(o);
        if (index != -1)
            remove(index);
    }

    public void removeIf(Predicate<? super E> filter) {
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
    public void clear(){
        int tmpSize = size;
        for (int i = 0; i < tmpSize; i++) {
            elementData[i] = null;
            size--;
        }
        capacity = 10;
    }

    public int size() {
        return size;
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return size == that.size && Arrays.equals(elementData, that.elementData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elementData);
        return result;
    }

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
