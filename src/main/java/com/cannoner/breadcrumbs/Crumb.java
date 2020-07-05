package com.cannoner.breadcrumbs;

import java.util.ArrayList;
import java.util.List;

import com.cannoner.breadcrumbs.enums.CrumbType;

public class Crumb
{
    public CrumbType type;
    public List<CrumbLocation> locations = new ArrayList();
    public Integer id;
    public long initTime;

    public Crumb(CrumbType type, int id, double x, double y, double z) {
        this.type = type;
        this.id = id;
        this.initTime = System.currentTimeMillis();
        this.locations.add(new CrumbLocation(x, y, z));
    }

    public boolean a() {
        return this.initTime + Breadcrumbs.getInstance().getTimeout() < System.currentTimeMillis();
    }

    public void addLocation(double x, double y, double z) {
        this.locations.add(new CrumbLocation(x, y, z));
    }
}
