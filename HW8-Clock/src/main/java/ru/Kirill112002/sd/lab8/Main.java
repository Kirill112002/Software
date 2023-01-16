package ru.Kirill112002.sd.lab8;

import ru.Kirill112002.sd.lab8.event.EventsStatistic;
import ru.Kirill112002.sd.lab8.event.EventsStatisticImpl;

import java.time.Clock;

public class Main {
    public static void main(String[] args) {
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(Clock.systemUTC());
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.printStatistic();
    }
}
