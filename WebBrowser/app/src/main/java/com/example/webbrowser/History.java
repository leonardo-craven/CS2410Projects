package com.example.webbrowser;

public class History {
    public History(String url) {
        this.currPage = new HistoryNode(url);
    }

    public String next() {
        if (currPage.next == null) return null;
        currPage = currPage.next;
        return currPage.url;
    }

    public String previous() {
        if (currPage.previous == null) return null;
        currPage = currPage.previous;
        return currPage.url;
    }

    public String goHere(String url) {
        HistoryNode newPage = new HistoryNode(url);
        currPage.next = newPage;
        newPage.previous = currPage;
        currPage = newPage;
        return newPage.url;
    }

    private static class HistoryNode {
        private HistoryNode(String url) {
            this.url = url;
        }

        private String url;
        private HistoryNode next;
        private HistoryNode previous;
    }

    private HistoryNode currPage;
}
