package com.ducnh.oauth2_server.ulti;

import java.util.Iterator;
import java.util.List;

public class Point {
    private double lat;
    private double lng;
    public Point(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "(" + lat + ", " + lng + ")";
    }

    public static String toGeoJson(List<Point> point) {
        StringBuilder buff = new StringBuilder("[");
        Iterator<Point> iter = point.iterator();
        while (iter.hasNext()) {
            buff.append(toGeoJSON(iter.next()));
            if (iter.hasNext()) {
                buff.append(",");
            }
        }
        buff.append("]");
        return buff.toString();
    }

    public static String toGeoJSON(Point point) {
        return "[" + point.getLng() + "," + point.getLat() + "]";
    }
}