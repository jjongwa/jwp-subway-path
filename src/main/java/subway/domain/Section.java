package subway.domain;

import java.util.Objects;

public class Section {

    private Station from;
    private Station to;
    private final Distance distance;

    public Section(final Station from, final Station to, final int distance) {
        validate(from, to);
        this.from = from;
        this.to = to;
        this.distance = new Distance(distance);
    }

    private void validate(final Station from, final Station to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("입력한 두 역은 같습니다.");
        }
    }

    public boolean existById(final Long stationId) {
        return existLeftById(stationId) || existRightById(stationId);
    }

    public boolean existLeftById(final Long stationId) {
        return stationId.equals(from.getId());
    }

    public boolean existRightById(final Long stationId) {
        return stationId.equals(to.getId());
    }

    public boolean exist(final Station station) {
        return existLeft(station) || existRight(station);
    }

    public boolean existLeft(final Station station) {
        return station.equals(from);
    }

    public boolean existRight(final Station station) {
        return station.equals(to);
    }

    public boolean isInsertable(final int otherDistance) {
        return distance.isLongerThan(otherDistance);
    }

    public void updateStation(final Station targetStation, final Station updateStation) {
        if (from.equals(targetStation)) {
            from = updateStation;
            return;
        }
        to = updateStation;
    }

    public Station getFrom() {
        return from;
    }

    public Station getTo() {
        return to;
    }

    public int getDistanceValue() {
        return distance.getDistance();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Section section = (Section) o;
        return Objects.equals(from, section.from) && Objects.equals(to, section.to) && Objects.equals(distance,
                section.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, distance);
    }

    @Override
    public String toString() {
        return "Section{" +
                "from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }
}
