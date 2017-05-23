package net.wildpark.dswp.entitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nylaet on 19.05.2017.
 */
@Entity
public class DeviceHistory {
    private static final long serialVersionUID = 1L;
    @Id
    private String chipId;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> dataHistory = new ArrayList<>();
    @ElementCollection
    private List<String> logHistory = new ArrayList<>();
    @ElementCollection
    private List<String> commandHistory = new ArrayList<>();

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }

    public List<String> getDataHistory() {
        return dataHistory;
    }

    public void setDataHistory(List<String> dataHistory) {
        this.dataHistory = dataHistory;
    }

    public List<String> getLogHistory() {
        return logHistory;
    }

    public void setLogHistory(List<String> logHistory) {
        this.logHistory = logHistory;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    public void setCommandHistory(List<String> commandHistory) {
        this.commandHistory = commandHistory;
    }
}
