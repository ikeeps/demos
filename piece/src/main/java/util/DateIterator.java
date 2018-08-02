package util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author hujianfeng
 *
 */
public class DateIterator implements Iterable<LocalDate> {
    private final LocalDate start;
    private final int count;
    private final ChronoUnit unit;
    private final int step;

    public DateIterator(LocalDate start, int count, ChronoUnit unit, int step) {
        if (step < 0) {
            this.start = start.plus((long)count * step, unit);
        } else {
            this.start = start;
        }
        this.count = count;
        this.unit = unit;
        this.step = Math.abs(step);
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return start.plus((long)count * step, unit);
    }

    public LocalDate peekNext(LocalDate cur) {
        return cur.plus(step, unit);
    }

    public LocalDate peekPrevious(LocalDate cur) {
        return cur.minus(step, unit);
    }

    public long compare(LocalDate cur, LocalDate next) {
        return unit.between(cur, next);
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return new Iterator<LocalDate>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < count;
            }

            @Override
            public LocalDate next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return start.plus((long)current++ * step, unit);
            }
        };
    }

}
