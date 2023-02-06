package de.fred4jupiter.fredbet.web.bet;

import java.time.LocalDateTime;
import java.util.List;

public class BetHistoryCommand {

    private int draw;
    private long recordsTotal;
    private long recordsFiltered;

    private List<BetHistoryCommandData> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<BetHistoryCommandData> getData() {
        return data;
    }

    public void setData(List<BetHistoryCommandData> data) {
        this.data = data;
    }
}
