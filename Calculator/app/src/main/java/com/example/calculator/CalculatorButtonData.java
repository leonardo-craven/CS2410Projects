package com.example.calculator;

public class CalculatorButtonData {
    enum buttonType {EQUALS, SYMBOL, CLEAR, NUMBER}
    private int row;
    private int col;
    private int size;
    private String text;
    private buttonType type;

    public CalculatorButtonData(String text, int row, int col, int size) {
        this.text = text;
        this.row = row;
        this.col = col;
        this.size = size;
        this.type = buttonType.NUMBER;
    }

    public CalculatorButtonData(String text, int row, int col, int size, buttonType type) {
        this.text = text;
        this.row = row;
        this.col = col;
        this.size = size;
        this.type = type;
    }

    public String getText() {return text;}
    public int getRow() {return row;}
    public int getCol() {return col;}
    public int getSize() {return size;}
    public buttonType getType() { return type; }
}
