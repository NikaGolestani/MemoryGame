package MemoryGame;

class Score implements Comparable<Score> {
    private String name;
    private int point;

    public Score(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    @Override
    public int compareTo(Score other) {
        // Compare scores in descending order
        return Integer.compare(other.point, this.point);
    }
}