package com.violin.imageview.view;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModelProvider;

import java.util.*;
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class test {
    public static void main(String[] arg) {
        Context context;
        ListNode head = new ListNode(1);
        ListNode cNode = head;
        for(int i=2;i<=4;i++) {
            ListNode nextNode = new ListNode(i);
            cNode.next = nextNode;
            cNode = nextNode;
        }

        reorderList(head);
//        mmvp

    }
    public static void reorderList(ListNode head) {
        ListNode leftNode = head;
        ListNode rightNode = head;
        while(rightNode.next !=null) {
            leftNode = leftNode.next;
            rightNode = rightNode.next.next;
        }
        ListNode preNode = null;
        ListNode currNode = leftNode;
        while (currNode != null) {
            ListNode next = currNode.next;
            currNode.next = preNode;
            preNode = currNode;
            currNode = next;
        }
        ListNode result = operate(head,preNode);

    }
    static boolean  isRervase = false;
    public static ListNode operate(ListNode head,ListNode tail){
        ListNode temp;
        if(head == null) {
            temp = tail;
        } else if(tail == null) {
            temp = head;
        } else
        if(isRervase){
            tail.next = operate(head,tail.next);
            temp =  tail;
        } else {
            head.next = operate(tail,head.next);
            temp = head;
        }
        isRervase = !isRervase;
        return temp;

    }


}
class ListNode {
    int val;
    public ListNode next;
    public ListNode(int val) {

        this.val = val;
    }
}


