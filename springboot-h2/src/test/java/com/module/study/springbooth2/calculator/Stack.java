package com.module.study.springbooth2.calculator;

import java.util.ArrayList;

public class Stack {
    private ArrayList<String> stack = new ArrayList<>();

    public boolean isEmpty() {
        return stack.size() == 0;
    }

    public int getSize() {
        return stack.size();
    }

    public String peek() {
        if (!isEmpty()) {
            return stack.get(stack.size() - 1);
        } else {
            return "false";
        }
    }

    public String pop() {
        if (!isEmpty()) {
            String top = stack.get(stack.size() - 1);
            stack.remove(stack.size() - 1);
            return top;
        } else {
            return "false";
        }
    }

    public void push(String o) {
        stack.add(o);
    }

    @Override
    public String toString() {
        return "Stack:" + stack.toString();
    }
}
