package com.geekbrains;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    private volatile boolean winnerExist = false;
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public synchronized boolean isWinnerExist() {
        if (!winnerExist) {
            winnerExist = true;
            return false;
        }
        return true;
    }
}