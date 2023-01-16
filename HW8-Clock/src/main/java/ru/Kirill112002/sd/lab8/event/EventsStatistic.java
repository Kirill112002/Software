package ru.Kirill112002.sd.lab8.event;

import java.util.Map;

public interface EventsStatistic {
    void incEvent(String name);

    double getEventStatisticByName(String name);

    Map<String, Double> getAllEventStatistic();

    void printStatistic();

    void clear();
}