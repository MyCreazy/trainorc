package com.ztx.trainorc.model.vo;

import java.util.List;

public class JsonRootBean {
    private int logId;
    private int wordsResultNum;
    private List<WordsResult> wordsResult;
    public void setLogId(int logId) {
        this.logId = logId;
    }
    public int getLogId() {
        return logId;
    }

    public void setWordsResultNum(int wordsResultNum) {
        this.wordsResultNum = wordsResultNum;
    }
    public int getWordsResultNum() {
        return wordsResultNum;
    }

    public void setWordsResult(List<WordsResult> wordsResult) {
        this.wordsResult = wordsResult;
    }
    public List<WordsResult> getWordsResult() {
        return wordsResult;
    }
}
