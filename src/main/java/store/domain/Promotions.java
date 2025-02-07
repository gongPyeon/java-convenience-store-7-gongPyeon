package store.domain;

import java.util.Optional;

public class Promotions {
    private final String name;
    private final int buy;
    private final int get;
    private final String startDate;
    private final String endDate;

    public Promotions(String name, int buy, int get, String startDate, String endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    @Override
    public String toString() {
        return "Promotions{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
