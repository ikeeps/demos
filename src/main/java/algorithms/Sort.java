package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {

  /**
   * @param args
   */
  public static void main(String[] args) {
    int[] array = new int[]{1};// 7, 0, 1, 2, 3, 89, 39, 10};
    int[] array1 = new int[]{4, 5};
    System.out.println(Arrays.toString(top3(array)));
    System.out.println(Arrays.toString(top3(array, array1)));
    CollectionsSortOrder();
  }
  
  public static void CollectionsSortOrder() {
    List<Integer> list = new ArrayList<Integer>();
    list.add(3);
    list.add(8);
    list.add(1);
    list.add(4);
    list.add(5);
    list.add(4);
    Collections.sort(list, new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        long value = o2 - o1;
        if (value > 0) return 1;
        if (value < 0) return -1;
        return  0; // 大到小
        /*
        return o2 - o1;
        */
      }
    });
    System.out.println(list);
  }
  
  public static int[] top3(int[] array) {
    int len = Math.min(array.length, 3);
    int top[] = new int[len];
    --len;
    int idx = 0;
    for (int relation : array) {
      if (relation > top[idx]) {
        top[idx] = relation;
        for (int i = idx; i > 0; i--) {
          if (top[i] <= top[i - 1]) {
            break;
          }
          int tmp = top[i];
          top[i] = top[i - 1];
          top[i - 1] = tmp;
        }
        idx = Math.min(++idx, len);
      }
    }
    return top;
  }
  
  public static int[] top3(int[] array1, int[] array2){
    array1 = top3(array1);
    array2 = top3(array2);
    int len = Math.min(3, array1.length + array2.length);
    int[] idxs = new int[array2.length];
    Arrays.fill(idxs, len);
    for (int i = 0; i < array2.length; i++) {
      for (int j = array1.length - 1; j > -1; j--) {
        if (array2[i] > array1[j]) {
          idxs[i] = j;
        } else {
          break;
        }
      }
    }
    
    if (idxs.length != 0) {
      int[] uids = new int[len];
      int f_idx = 0;
      int s_idx = 0;
      int u_idx = 0;
      for (int idx : idxs) {
        while(idx > 0 && u_idx < len && f_idx < array1.length) {
          uids[u_idx++] = array1[f_idx++];
          idx--;
        }
        if (u_idx < 3) {
          uids[u_idx++] = array2[s_idx++];
        } else {
          break;
        }
      }
      for (int i = u_idx; i < len; i++) {
        uids[i] = array1[f_idx++];
      }
      return uids;
    } else {
      return array1;
    }

  }
  
}
