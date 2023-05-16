package subway.domain;

import java.util.Objects;

public class Distance {

    private final int distance;

    public Distance(final int distance) {
        validate(distance);
        this.distance = distance;
    }

    private static void validate(final int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("거리는 양수여야 합니다.");
        }
    }

    public boolean isLongerThan(final int otherDistance) {
        return distance > otherDistance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Distance distance1 = (Distance) o;
        return distance == distance1.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance);
    }

    @Override
    public String toString() {
        return "Distance{" +
                "distance=" + distance +
                '}';
    }

}
