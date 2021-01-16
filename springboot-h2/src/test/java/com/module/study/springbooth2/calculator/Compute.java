package com.module.study.springbooth2.calculator;

import java.util.Arrays;
import java.util.Scanner;

public class Compute {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("\n请输入公式：");
            String str = input.nextLine();
            String[] inOrderArrays = strToArrays(str);
            if (str.equals("exit")) {
                flag = false;
            } else {
                System.out.println(Arrays.toString(inOrderArrays));
                String[] postOrderArrays = toPostOrder(inOrderArrays);
                System.out.println(Arrays.toString(inOrderArrays));
                try {

                    Double result = toCompute(postOrderArrays);
                    System.out.printf("%.3f", result);
                } catch (Exception e) {
                    // e.printStackTrace();
                    System.out.println("出现异常公式，请输入正确公式");
                }
            }
        }

    }

    /*
     * 将字符串分割成操作数和操作符的字符串数组
     */
    public static String[] strToArrays(String str) {
        int strLength = str.length();
        int beginIndex = 0;
        int endIndex = 0;
        String[] Arrays = new String[strLength];
        int arraysIndex = 0;

        for (int i = 0; i < strLength; i++) {
            if (str.charAt(i) == '*' || str.charAt(i) == '/' || str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '(' || str.charAt(i) == ')') {
                endIndex = i - 1;
                if (beginIndex <= endIndex) {
                    Arrays[arraysIndex] = str.substring(beginIndex, endIndex + 1);
                    Arrays[arraysIndex + 1] = String.valueOf(str.charAt(i));
                    arraysIndex += 2;
                    beginIndex = i + 1;
                } else {
                    Arrays[arraysIndex] = String.valueOf(str.charAt(i));
                    arraysIndex += 1;
                    beginIndex = i + 1;
                }
            }
        }
        //简单的判断一下表达式是否是以操作符结尾还是操作数结尾
        if (!isOper(String.valueOf(str.charAt(strLength - 1)))) {
            Arrays[arraysIndex] = str.substring(beginIndex, strLength);
            arraysIndex += 1;
        }
        String[] Arrays2 = new String[arraysIndex];
        for (int i = 0; i < arraysIndex; i++) {
            Arrays2[i] = Arrays[i];
        }
        System.out.println(arraysIndex);
        return Arrays2;
    }

    /*
     * 将中缀表达式转为后缀表达式,返回的是字符串数组
     */
    public static String[] toPostOrder(String[] arrays) {
        /*规则：
         *1，运算数直接输出
         *2，左括号压入堆栈
         *3，右括号 将栈顶的运算符弹出并输出，括号出栈不输出
         *4，运算符：
         *    若优先级大于栈顶运算符，压入栈
         *    若优先级小于或等于栈顶运算符，栈顶运算符弹出并输出，
         *       继续和新栈顶比较，直到比栈顶运算符优先级大，将它压入栈
         *5，对象处理完毕后，将栈中运算符弹出并输出
         */
        Stack operStack = new Stack();//创建了一个操作符的栈
        int arraysLength = arrays.length;
        String[] arrays2 = new String[arraysLength];//输出后的字符数组
        int tempIndex = 0;

        //将字符串数组遍历
        for (int i = 0; i < arraysLength; i++) {
            //操作符入栈
            if (isOper(arrays[i])) {
                //栈为空时直接入栈
                if (operStack.isEmpty()) {
                    operStack.push(arrays[i]);
                } else {
                    //操作符为"("时直接入栈
                    if (arrays[i].equals("(")) {
                        operStack.push(arrays[i]);
                    }
                    //操作符为")"时栈顶出栈并输出，直到遇到"(", "("出栈,")"不入栈
                    else if (arrays[i].equals(")")) {
                        while (operStack.peek().equals("(") == false) {
                            arrays2[tempIndex] = operStack.pop();
                            tempIndex += 1;
                        }
                        operStack.pop();//"("出栈
                    }
                    //其他操作符需要比较与栈顶的优先级
                    else {
                        //栈顶是"(", 直接入栈
                        if (operStack.peek().equals("(")) {
                            operStack.push(arrays[i]);
                        } else {
                            //优先级高，直接入栈
                            if (getPriority(arrays[i].charAt(0)) > getPriority(operStack.peek().charAt(0))
                                    && operStack.isEmpty() == false) {
                                operStack.push(arrays[i]);
                            }
                            //优先级低或者相等，栈顶出栈并输出，直到优先级比栈顶高
                            else {
                                while (getPriority(arrays[i].charAt(0)) <= getPriority(operStack.peek().charAt(0))
                                        && operStack.isEmpty() == false) {
                                    arrays2[tempIndex] = operStack.pop();
                                    tempIndex += 1;
                                    //若栈顶全部弹出，则直接入栈
                                    if (operStack.isEmpty()) {
                                        operStack.push(arrays[i]);
                                        break;
                                    }
                                }
                                if (getPriority(arrays[i].charAt(0)) > getPriority(operStack.peek().charAt(0))) {
                                    operStack.push(arrays[i]);
                                }
                            }
                        }
                    }
                }
            }
            //操作数直接添加到 字符串数组2
            else if (isNum(arrays[i])) {
                arrays2[tempIndex] = arrays[i];
                tempIndex += 1;
            } else {
            }
        }
        while (!operStack.isEmpty()) {
            arrays2[tempIndex] = operStack.pop();
            tempIndex += 1;
        }
        String[] arrays3 = new String[tempIndex];
        for (int i = 0; i < tempIndex; i++) {
            arrays3[i] = arrays2[i];
        }
        return arrays3;
    }

    /*
     * 得到操作符的优先级
     */
    public static int getPriority(char c) {
        if (c == '*' || c == '/') {
            return 2;
        } else if (c == '+' || c == '-') {
            return 1;
        } else {
            return 999;
        }
    }

    /*
     * 由后缀表达式计算得值
     */
    public static double toCompute(String[] arrays) {
        /*规则：
         *中缀表达式不用比较优先级
         *将运算数入栈，每读到一个运算符
         *就弹出栈顶的两个运算数，运算完毕后将结果压入栈
         */
        Stack numStack = new Stack();//创建了一个操作数的栈
        int arraysLength = arrays.length;
        //遍历后缀表达式的字符串数组
        for (String array : arrays) {
            if (isNum(array)) {
                numStack.push(array);
            } else if (isOper(array)) {
                Double num2 = Double.parseDouble(numStack.pop());
                Double num1 = Double.parseDouble(numStack.pop());
                if (array.equals("+")) {
                    Double result = num1 + num2;
                    numStack.push(result.toString());
                } else if (array.equals("-")) {
                    Double result = num1 - num2;
                    numStack.push(result.toString());
                } else if (array.equals("*")) {
                    Double result = num1 * num2;
                    numStack.push(result.toString());
                } else if (array.equals("/")) {
                    Double result = num1 / num2;
                    numStack.push(result.toString());
                } else {
                }
            } else {
            }
        }
        return Double.parseDouble(numStack.pop());
    }

    /*
     * 判断该字符串是否为操作符
     */
    public static boolean isOper(String str) {
        if (str.equals("*") || str.equals("/") || str.equals("+") || str.equals("-") ||
                str.equals("(") || str.equals(")")) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 判断该字符串是否为操作数
     */
    public static boolean isNum(String str) {
        if (str.equals("*") || str.equals("/") ||
                str.equals("+") || str.equals("-") ||
                str.equals("(") || str.equals(")")) {
            return false;
        } else {
            return true;
        }
    }
}
