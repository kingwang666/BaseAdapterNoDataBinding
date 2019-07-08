package com.wang.baseadapter.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * RecyclerView列表类
 */
public class ItemArray<T extends ItemData> extends ArrayList<T> {

    public ItemArray(int initialCapacity) {
        super(initialCapacity);
    }

    public ItemArray() {
        super();
    }

    public ItemArray(Collection<? extends T> c) {
        super(c);
    }

    /**
     * 找出type类型的第一个数据的位置
     *
     * @param type 类型
     * @return 对应的position，没有就返回-1
     */
    public int findFirstTypePosition(int type) {
        for (int i = 0; i < size(); i++) {
            if (type == get(i).dataType) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 找出type类型的最后数据的位置
     *
     * @param type 类型
     * @return 对应的position，没有就返回-1
     */
    public int findLastTypePosition(int type) {
        for (int i = size() - 1; i >= 0; i--) {
            if (type == get(i).dataType) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 找出type类型的下面第一个数据的位置
     *
     * @param type 类型
     * @return 对应的position，没有就返回-1
     */
    public int findFirstNotTypePosition(int type) {
        for (int i = 0; i < size(); i++) {
            if (type != get(i).dataType) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 找出type类型的上面第一个数据的位置
     *
     * @param type 类型
     * @return 对应的position，没有就返回-1
     */
    public int findLastNotTypePosition(int type) {
        for (int i = size() - 1; i >= 0; i--) {
            if (type != get(i).dataType) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 插入到type类型底部
     *
     * @param type 类型
     * @param data 插入的数据
     * @return 对应的位置
     */
    public int addAfterLast(int type, T data) {
        int position = findLastTypePosition(type);
        add(++position, data);
        return position;
    }

    /**
     * 插入到type类型顶部
     *
     * @param type 类型
     * @param data 插入的数据
     * @return 对应的位置
     */
    public int addBeforFirst(int type, T data) {
        int position = findFirstTypePosition(type);
        add(position, data);
        return position;
    }

    /**
     * 移除type类型的第一个数据
     *
     * @param type 类型
     * @return 对应的位置，没有发现返回-1
     */
    public int removeFirstType(int type) {
        int position = findFirstTypePosition(type);
        if (position != -1) {
            remove(position);
            return position;
        }
        return -1;
    }

    /**
     * 移除所有的type类型数据
     *
     * @param type 类型
     * @return 移除的数量
     */
    public int removeAllType(int type) {
        int count = 0;
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            ItemData item = iterator.next();
            if (item.dataType == type) {
                iterator.remove();
                count++;
            }
        }
        return count;
    }

    /**
     * 在type类型下面添加多个数据
     *
     * @param type  类型
     * @param datas 数据
     * @return 数据数目
     */
    public int addAllType(int type, List<T> datas) {
        int count = 0;
        int position = findLastTypePosition(type);
        addAll(position, datas);
        return count;

    }

    /**
     * 在指定位置移除指定数量数据类型数据
     *
     * @param position 开始移除数据位置
     * @param count    移除的数量
     * @return 移除的数量
     */
    public int removeAllAtPosition(int position, int count) {
        if (count > 0) {
            removeRange(position, position + count);
        }
        return count;
    }

    /**
     * 查询数组中是否存在type类型的项
     *
     * @param type 要查找的type类型
     * @return 空这是true否则false
     */
    public boolean isEmptyOfType(int type) {
        return findFirstTypePosition(type) == -1;
    }

    @SuppressWarnings("unchecked")
    public ItemArray<T> sortNew(Comparator<T> c){
        Object[] a = toArray();
        Arrays.sort(a, (Comparator) c);
        ItemArray<T> result = new ItemArray<>(a.length);
        for (Object anA : a) {
            result.add((T) anA);
        }
        return result;
    }

}
