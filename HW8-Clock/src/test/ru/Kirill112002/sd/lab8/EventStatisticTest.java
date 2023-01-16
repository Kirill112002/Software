package ru.Kirill112002.sd.lab8;

import ru.Kirill112002.sd.lab8.clock.MutableClock;
import ru.Kirill112002.sd.lab8.event.EventsStatistic;
import ru.Kirill112002.sd.lab8.event.EventsStatisticImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;

public class EventStatisticTest {
    @Test
    public void testEmpty() {
        final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        Assertions.assertEquals(0.0, eventsStatistic.getEventStatisticByName("1"));
    }

    @Test
    public void testIncEvent() {
        final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        eventsStatistic.incEvent("1");
        Assertions.assertEquals(1.0 / 60, eventsStatistic.getEventStatisticByName("1"));
    }

    @Test
    public void testMultiple1() {
        final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("3");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        Assertions.assertEquals(5.0 / 60, eventsStatistic.getEventStatisticByName("1"));
        Assertions.assertEquals(4.0 / 60, eventsStatistic.getEventStatisticByName("2"));
        Assertions.assertEquals(1.0 / 60, eventsStatistic.getEventStatisticByName("3"));
    }

    @Test
    public void testMultiple2() {
        final MutableClock clock = new MutableClock(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        for(int i = 0; i < 100; i++){
            eventsStatistic.incEvent(String.valueOf((int)Math.floorDiv(i, 10)));
        }
        Map<String, Double> expectedMap = Map.of("0", 10.0/60, "1", 10.0/60, "2", 10.0/60,
                "3", 10.0/60, "4", 10.0/60, "5", 10.0/60, "6", 10.0/60, "7", 10.0/60,
                "8", 10.0/60, "9", 10.0/60);

        Assertions.assertEquals(expectedMap, eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void testOffset() {
        final MutableClock clock = new MutableClock(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        eventsStatistic.incEvent("1");
        clock.offset(Duration.ofHours(1));
        Assertions.assertEquals(0.0, eventsStatistic.getEventStatisticByName("1"));
    }

    @Test
    public void testOffsetMultiple1() {
        final MutableClock clock = new MutableClock(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        eventsStatistic.incEvent("1");
        clock.offset(Duration.ofMinutes(5));
        eventsStatistic.incEvent("2");
        clock.offset(Duration.ofMinutes(1));
        eventsStatistic.incEvent("3");
        clock.offset(Duration.ofMinutes(1));
        eventsStatistic.incEvent("2");
        clock.offset(Duration.ofMinutes(10));
        eventsStatistic.incEvent("1");
        clock.offset(Duration.ofMinutes(30));
        eventsStatistic.incEvent("2");
        clock.offset(Duration.ofMinutes(6));
        eventsStatistic.incEvent("3");
        clock.offset(Duration.ofMinutes(7));
        eventsStatistic.incEvent("1");
        clock.offset(Duration.ofMinutes(5));
        eventsStatistic.incEvent("1");
        clock.offset(Duration.ofMinutes(3));
        eventsStatistic.incEvent("3");
        clock.offset(Duration.ofMinutes(6));
        final Map<String, Double> expectedMap = Map.of("1", 3.0 / 60, "2", 1.0 / 60, "3", 2.0 / 60);
        Assertions.assertEquals(expectedMap, eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void testOffsetMultiple2() {
        final MutableClock clock = new MutableClock(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        for(int i = 0; i < 100; i++){
            eventsStatistic.incEvent(String.valueOf((int)Math.floorDiv(i, 10)));
            clock.offset(Duration.ofMinutes(6));
        }
        Map<String, Double> expectedMap = Map.of("0", 0.0/60, "1", 0.0/60, "2", 0.0/60,
                "3", 0.0/60, "4", 0.0/60, "5", 0.0/60, "6", 0.0/60, "7", 0.0/60,
                "8", 0.0/60, "9", 9.0/60);
        
        Assertions.assertEquals(expectedMap, eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void testOffsetMultiple3() {
        final MutableClock clock = new MutableClock(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        for(int i = 0; i < 100; i++){
            eventsStatistic.incEvent(String.valueOf(i % 10));
            clock.offset(Duration.ofMinutes(6));
        }
        Map<String, Double> expectedMap = Map.of("0", 0.0/60, "1", 1.0/60, "2", 1.0/60,
                "3", 1.0/60, "4", 1.0/60, "5", 1.0/60, "6", 1.0/60, "7", 1.0/60,
                "8", 1.0/60, "9", 1.0/60);

        Assertions.assertEquals(expectedMap, eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void testClear() {
        final MutableClock clock = new MutableClock(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        eventsStatistic.incEvent("1");
        Assertions.assertEquals(1.0 / 60, eventsStatistic.getEventStatisticByName("1"));
        eventsStatistic.clear();
        Assertions.assertEquals(Collections.emptyMap(), eventsStatistic.getAllEventStatistic());
    }

    @Test
    public void testClear2() {
        final MutableClock clock = new MutableClock(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        final EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        Assertions.assertEquals(3.0 / 60, eventsStatistic.getEventStatisticByName("1"));
        Assertions.assertEquals(1.0 / 60, eventsStatistic.getEventStatisticByName("2"));
        eventsStatistic.clear();
        Assertions.assertEquals(Collections.emptyMap(), eventsStatistic.getAllEventStatistic());
    }
}
