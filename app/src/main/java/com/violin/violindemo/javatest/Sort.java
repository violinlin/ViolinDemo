package com.violin.violindemo.javatest;

class Sort {


    public void maopaoSort(int[] array) {

        for (int i = 0; i < array.length; i++) {
            boolean swap = false;
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = 0;
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swap = true;
                }

            }
            if (!swap) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        sort(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

    public static void sort(int[] array) {
        int currentIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 != 0) { // 奇数
                if (i > currentIndex && currentIndex > -1) {
                    int temp = array[i];
                    array[i] = array[currentIndex];
                    array[currentIndex] = temp;
                    currentIndex++;
                }

            } else { // 偶数
                if (currentIndex == -1) {
                    currentIndex = i;
                }
            }
        }

        for (int i : array) {
            System.out.println(i);
        }

    }
}
