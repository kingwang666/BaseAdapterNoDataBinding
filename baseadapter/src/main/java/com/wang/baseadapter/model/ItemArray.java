package com.wang.baseadapter.model;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * RecyclerView列表类
 */
public class ItemArray extends ArrayList<TypeData> {

    public ItemArray(int initialCapacity) {
        super(initialCapacity);
    }

    public ItemArray() {
        super();
    }

    public ItemArray(Collection<? extends TypeData> c) {
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
            if (type == get(i).getDataType()) {
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
            if (type == get(i).getDataType()) {
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
            if (type != get(i).getDataType()) {
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
            if (type != get(i).getDataType()) {
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
    public int addAfterLast(int type, TypeData data) {
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
    public int addBeforFirst(int type, TypeData data) {
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
        Iterator<TypeData> iterator = iterator();
        while (iterator.hasNext()) {
            TypeData item = iterator.next();
            if (item.getDataType() == type) {
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
    public int addAllType(int type, List<? extends TypeData> datas) {
        int count = 0;
        int position = findLastTypePosition(type);
        addAll(position, datas);
        return count;

    }

    /**
     * 在列表最后添加多个数据
     *
     * @param type  类型
     * @param datas 数据
     * @param <E>   数据类型
     * @return 数据数目
     */
    public <E> int addAllAtLast(int type, List<E> datas) {
        int count = 0;
        for (E e : datas) {
            if (e instanceof ItemData) {
                ItemData data = (ItemData) e;
                data.dataType = type;
                add(data);
            } else {
                add(new CommData(type, e));
            }

            count++;
        }
        return count;
    }

    /**
     * 在指定位置添加多个数据
     *
     * @param position 开始插入数据位置
     * @param type     类型
     * @param datas    数据
     * @param <E>      数据类型
     * @return 数据数目
     */
    public <E> int addAll(int position, int type, List<E> datas) {
        int count = 0;
        for (E e : datas) {
            if (e instanceof ItemData){
                ItemData data = (ItemData) e;
                data.dataType = type;
                add(position + count, data);
            }else {
                add(position + count, new CommData(type, e));
            }
            count++;
        }
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
    public ItemArray sortNew(Comparator<? extends TypeData> c) {
        Object[] a = toArray();
        Arrays.sort(a, (Comparator) c);
        ItemArray result = new ItemArray(a.length);
        for (Object anA : a) {
            result.add((TypeData) anA);
        }
        return result;
    }

}
