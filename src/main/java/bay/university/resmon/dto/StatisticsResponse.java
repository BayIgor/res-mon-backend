package bay.university.resmon.dto;

public class StatisticsResponse {

    private Long measCount;
    private Long metersCount;

    public StatisticsResponse() {
    }

    public StatisticsResponse(Long measCount, Long metersCount) {
        this.measCount = measCount;
        this.metersCount = metersCount;
    }

    public Long getMeasCount() {
        return measCount;
    }

    public void setMeasCount(Long measCount) {
        this.measCount = measCount;
    }

    public Long getMetersCount() {
        return metersCount;
    }

    public void setMetersCount(Long metersCount) {
        this.metersCount = metersCount;
    }
}
