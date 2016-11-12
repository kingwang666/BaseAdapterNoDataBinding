package com.wang.baseadapternodatabinding.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang
 * on 2016/11/11
 */

public class Chapter {

    public String name;

    public int sectionSize;

    public List<Section> sections;

    public boolean isOpen;

    public Chapter(String name, int sectionSize) {
        this.name = name;
        this.sectionSize = sectionSize;
        this.sections = new ArrayList<>(sectionSize);
        this.isOpen = true;
    }
}
