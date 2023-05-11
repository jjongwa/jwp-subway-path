package subway.domain;

import java.util.Map;

public class Route {

    private final Map<Line, Sections> sectionsByLine;

    public Route(final Map<Line, Sections> sectionsByLine) {
        this.sectionsByLine = sectionsByLine;
    }

    public void insertLine(final Line line) {
        sectionsByLine.put(line, new Sections());
    }

    public void updateLine(final Line targetLine, final Line updateLine) {
        validateLineExistence(targetLine);
        sectionsByLine.put(updateLine, sectionsByLine.get(targetLine));
        sectionsByLine.remove(targetLine);
    }

    private void validateLineExistence(final Line line) {
        if (!sectionsByLine.containsKey(line)) {
            throw new IllegalArgumentException("해당 호선이 존재하지 않습니다.");
        }
    }

    public void insertStation(final Line line, final Station from, final Station to, final int distance) {
        validateLineExistence(line);
        final Sections sections = sectionsByLine.get(line);

        if (sections.isEmpty()) {
            sections.insertInitially(from, to, distance);
            return;
        }

        sections.insert(from, to, distance);
    }

    public void updateStation(final Line line, final Station targetStation, final Station updateStation) {
        validateLineExistence(line);
        final Sections sections = sectionsByLine.get(line);

        if (!sections.hasStation(targetStation)) {
            throw new IllegalArgumentException("해당 역이 존재하지 않습니다.");
        }

        sections.updateStation(targetStation, updateStation);
    }

    public Map<Line, Sections> getSectionsByLine() {
        return sectionsByLine;
    }
}