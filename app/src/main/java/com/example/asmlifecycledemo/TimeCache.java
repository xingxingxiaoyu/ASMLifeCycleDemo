package com.example.asmlifecycledemo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 描述信息：
 *
 * @author xujiafeng
 * @date 2020/7/12
 */
public class TimeCache {
    public static final String TAG = "TimeCache";
    public static LinkedList<Long> sStartTime = new LinkedList<>();

    public static void setStartTime(String methodName, long time) {
        sStartTime.add(time);
    }

    public static void setEndTime(String methodName, long time) {
        android.util.Log.d(TAG, "setEndTime: " + methodName + (time - sStartTime.removeLast()));
    }

}
