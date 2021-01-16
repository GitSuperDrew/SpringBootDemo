package com.module.study.springbooth2.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class CalculatorPlus {

    /**
     * 测试通过的数据用例：
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("\n请输入公式：");
            String str = input.nextLine();
            if ("exit".equals(str)) {
                flag = false;
            } else {
                try {
//                    BigDecimal d = new BigDecimal("0");
//                    System.out.println(d.compareTo(new BigDecimal(str)));

                    // 方法一：返回双精度类型的数据
                    // Double result = Calculate.result(str);
                    // 方法二：返回大整数类型的数据
                    BigDecimal result = CalculatePlus.result(str);
                    System.out.println("结果为:" + result);
                } catch (Exception e) {
                    System.err.println("出现异常");
                    e.printStackTrace();
                }
            }
        }


    }
}

/**
 * 高精度，大整数类型
 */
class CalculatePlus {
    /**
     * 高精度计算结果入口方法
     * 测试用例：
     * 第一类：加减除【(-10)+0/10、(-10)+10/0、0/10+(-10)、10/0+(-10)】
     * 第二类：正常运算【(-2)-(-20)、(10.4554+20.5656)-(-20)/5+2+(-10) 】
     *
     * @param str 注意：数据格式要求：负数和0值需要使用括号包裹才能正确计算！
     * @return 高精度，保留4位有效数字
     */
    public static BigDecimal result(String str) {
        List<String> strList = strToStrList(str.trim());
        List<String> postList = toPostOrder(strList);
        BigDecimal result = getResult(postList);
        return result;
    }

    /**
     * 将字符串转换为字符串List
     *
     * @param str
     * @return
     */
    private static List<String> strToStrList(String str) {
        List<String> strList = new ArrayList<>();
        int begin = 0, end = 0, post = 0, i = 0;
        for (; i < str.length(); i++) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*'
                    || str.charAt(i) == '/' || str.charAt(i) == '(' || str.charAt(i) == ')') {
                end = i;
                if (begin < end) {
                    strList.add(str.substring(begin, end));
                    strList.add(str.substring(i, i + 1));
                } else {
                    //连续符号时
                    strList.add(str.substring(i, i + 1));
                }
                begin = i + 1;
            }
        }
        //如果没有if包裹，当最后一个字符为操作符时，List末尾会添加一个空字符串
        if (!"".equals(str.substring(begin))) {
            strList.add(str.substring(begin));
        }
        return strList;
    }

    /**
     * 将中缀表达式转换为后缀表达式
     *
     * @param strList
     * @return
     */
    private static List<String> toPostOrder(List<String> strList) {
        /*规则：
         *  1，运算数直接输出
         *  2，左括号压入堆栈
         *  3，右括号 将栈顶的运算符弹出并输出，括号出栈不输出
         *  4，运算符：
         *    若优先级大于栈顶运算符，压入栈
         *    若优先级小于或等于栈顶运算符，栈顶运算符弹出并输出，
         *       继续和新栈顶比较，直到比栈顶运算符优先级大，将它压入栈
         *  5，对象处理完毕后，将栈中运算符弹出并输出
         */
        Stack<String> operStack = new Stack<>();
        List<String> postList = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            if (isOper(strList.get(i), i, strList)) {
                //堆栈为空时操作符直接入栈
                if (operStack.isEmpty()) {
                    operStack.push(strList.get(i));
                } else {
                    //操作符为"("时直接入栈
                    if ("(".equals(strList.get(i))) {
                        operStack.push(strList.get(i));
                    } else if (")".equals(strList.get(i))) {
                        //操作符为")"时栈顶出栈并输出，直到遇到"(", "("出栈,")"不入栈
                        while (!"(".equals(operStack.peek())) {
                            postList.add(operStack.pop());
                        }
                        operStack.pop();
                    } else {
                        //其他操作符需要比较与栈顶元素的优先级
                        if ("(".equals(operStack.peek())) {
                            operStack.push(strList.get(i));
                        } else {
                            //优先级高，直接入栈
                            if (!operStack.isEmpty() && getPriority(strList.get(i)) > getPriority(operStack.peek())) {
                                operStack.push(strList.get(i));
                            } else {
                                if (!operStack.isEmpty() && getPriority(strList.get(i)) <= getPriority(operStack.peek()) && !"(".equals(operStack.peek())) {

                                }
                                //优先级低或者相等，栈顶元素出栈，直到优先级比栈顶元素高
                                while (!operStack.isEmpty() && getPriority(strList.get(i)) <= getPriority(operStack.peek()) && !"(".equals(operStack.peek())) {
                                    postList.add(operStack.pop());
                                }

                                if (operStack.isEmpty()) {
                                    //若堆栈元素全部被弹出，直接入栈
                                    operStack.push(strList.get(i));
                                } else if (getPriority(strList.get(i)) > getPriority(operStack.peek()) || "(".equals(operStack.peek())) {
                                    //若优先级高，入栈
                                    operStack.push(strList.get(i));
                                }
                            }
                        }
                    }
                }
            } else {
                //操作数直接添加
                if ("-".equals(strList.get(i))) {

                } else if (i >= 2 && "-".equals(strList.get(i - 1)) && "(".equals(strList.get(i - 2))) {
                    postList.add(strList.get(i - 1) + strList.get(i));
                } else if (i == 1 && "-".equals(strList.get(i - 1))) {
                    postList.add(strList.get(i - 1) + strList.get(i));
                } else {
                    postList.add(strList.get(i));
                }
            }
        }
        //最后将所有元素出栈
        while (!operStack.isEmpty()) {
            postList.add(operStack.pop());
        }
        return postList;
    }

    /**
     * 计算后缀表达式
     */
    private static BigDecimal getResult(List<String> postList) {
        /*规则：
         *中缀表达式不用比较优先级
         *将运算数入栈，每读到一个运算符
         *就弹出栈顶的两个运算数，运算完毕后将结果压入栈
         */
        Stack<String> numStack = new Stack();
        for (int i = 0; i < postList.size(); i++) {
            if (isNum(postList.get(i))) {
                numStack.push(postList.get(i));
            } else if (isOper(postList.get(i), i, postList)) {

                if ("+".equals(postList.get(i))) {
                    BigDecimal num2 = new BigDecimal(numStack.pop());
                    BigDecimal num1 = new BigDecimal(numStack.pop());
                    BigDecimal result = num1.add(num2);
                    numStack.push(result.toString());
                } else if ("-".equals(postList.get(i))) {
                    BigDecimal num2 = new BigDecimal(numStack.pop());
                    BigDecimal num1;
                    //能计算负数开头
                    if (numStack.isEmpty()) {
                        num1 = new BigDecimal("0.0");
                    } else {
                        num1 = new BigDecimal(numStack.pop());
                    }
                    BigDecimal result = num1.subtract(num2);
                    numStack.push(result.toString());
                } else if ("*".equals(postList.get(i))) {
                    BigDecimal num2 = new BigDecimal(numStack.pop());
                    BigDecimal num1 = new BigDecimal(numStack.pop());
                    BigDecimal result = num1.multiply(num2);
                    numStack.push(result.toString());
                } else if ("/".equals(postList.get(i))) {
                    BigDecimal num2 = new BigDecimal(numStack.pop());
                    BigDecimal num1 = new BigDecimal(numStack.pop());
                    // 分母为0
                    if (num2.compareTo(new BigDecimal("0")) == 0) {
                        // 当除数为0，则过滤掉
                        numStack.push("0");
                    } else {
                        BigDecimal result = num1.divide(num2, 4, RoundingMode.HALF_UP);
                        numStack.push(result.toString());
                    }
                }
            }
        }
        // 分母异常值
        return new BigDecimal(numStack.pop()).setScale(4, RoundingMode.HALF_UP);
        // return ObjectUtils.isEmpty(numStack.pop()) ? new BigDecimal("0") : new BigDecimal(numStack.pop()).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 判断该字符串是否为操作符
     *
     * @param str
     * @param i
     * @param stringList
     * @return
     */
    private static boolean isOper(String str, int i, List<String> stringList) {
        if ("*".equals(str) || "/".equals(str) || "+".equals(str) || "(".equals(str) || ")".equals(str)) {
            return true;
        } else if ("-".equals(str)) {
            return !(i >= 1 && "(".equals(stringList.get(i - 1)));
        } else {
            return false;
        }
    }

    /**
     * 判断该字符串是否为操作数
     *
     * @param str
     * @return
     */
    private static boolean isNum(String str) {
        if ("*".equals(str) || "/".equals(str) || "+".equals(str) || "-".equals(str) || "(".equals(str) || ")".equals(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 得到操作符的优先级
     *
     * @param c
     * @return
     */
    private static int getPriority(String c) {
        if ("*".equals(c) || "/".equals(c)) {
            return 2;
        } else if ("+".equals(c) || "-".equals(c)) {
            return 1;
        } else {
            return 999;
        }
    }
}

/**
 * Double 类型的双精度结果
 */
class Calculate {
    //计算结果
    public static Double result(String str) {
        List<String> strList = strToStrList(str.trim());
        List<String> postList = toPostOrder(strList);
        Double result = getResult(postList);
        return result;
    }

    //将字符串转换为字符串List
    private static List<String> strToStrList(String str) {
        List<String> strList = new ArrayList<>();
        int begin = 0, end = 0, post = 0, i = 0;
        for (; i < str.length(); i++) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*'
                    || str.charAt(i) == '/' || str.charAt(i) == '(' || str.charAt(i) == ')') {
                end = i;
                if (begin < end) {
                    strList.add(str.substring(begin, end));
                    strList.add(str.substring(i, i + 1));
                } else {
                    //连续符号时
                    strList.add(str.substring(i, i + 1));
                }
                begin = i + 1;
            }
        }
        //如果没有if包裹，当最后一个字符为操作符时，List末尾会添加一个空字符串
        if (!"".equals(str.substring(begin))) {
            strList.add(str.substring(begin));
        }
        return strList;
    }

    /*
    将中缀表达式转换为后缀表达式
     */
    private static List<String> toPostOrder(List<String> strList) {
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
        Stack<String> operStack = new Stack<>();
        List<String> postList = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            if (isOper(strList.get(i), i, strList)) {
                //堆栈为空时操作符直接入栈
                if (operStack.isEmpty()) {
                    operStack.push(strList.get(i));
                } else {
                    //操作符为"("时直接入栈
                    if ("(".equals(strList.get(i))) {
                        operStack.push(strList.get(i));
                    } else if (")".equals(strList.get(i))) {
                        //操作符为")"时栈顶出栈并输出，直到遇到"(", "("出栈,")"不入栈
                        while (!"(".equals(operStack.peek())) {
                            postList.add(operStack.pop());
                        }
                        operStack.pop();
                    } else {
                        //其他操作符需要比较与栈顶元素的优先级
                        if ("(".equals(operStack.peek())) {
                            operStack.push(strList.get(i));
                        } else {
                            //优先级高，直接入栈
                            if (!operStack.isEmpty() && getPriority(strList.get(i)) > getPriority(operStack.peek())) {
                                operStack.push(strList.get(i));
                            } else {
                                if (!operStack.isEmpty() && getPriority(strList.get(i)) <= getPriority(operStack.peek()) && !"(".equals(operStack.peek())) {

                                }
                                //优先级低或者相等，栈顶元素出栈，直到优先级比栈顶元素高
                                while (!operStack.isEmpty() && getPriority(strList.get(i)) <= getPriority(operStack.peek()) && !"(".equals(operStack.peek())) {
                                    postList.add(operStack.pop());
                                }

                                if (operStack.isEmpty()) {
                                    //若堆栈元素全部被弹出，直接入栈
                                    operStack.push(strList.get(i));
                                } else if (getPriority(strList.get(i)) > getPriority(operStack.peek()) || "(".equals(operStack.peek())) {
                                    //若优先级高，入栈
                                    operStack.push(strList.get(i));
                                }
                            }
                        }
                    }
                }
            } else {
                //操作数直接添加
                if ("-".equals(strList.get(i))) {

                } else if (i >= 2 && "-".equals(strList.get(i - 1)) && "(".equals(strList.get(i - 2))) {
                    postList.add(strList.get(i - 1) + strList.get(i));
                } else if (i == 1 && "-".equals(strList.get(i - 1))) {
                    postList.add(strList.get(i - 1) + strList.get(i));
                } else {
                    postList.add(strList.get(i));
                }
            }
        }
        //最后将所有元素出栈
        while (!operStack.isEmpty()) {
            postList.add(operStack.pop());
        }
        return postList;
    }

    /**
     * 计算后缀表达式
     */
    private static double getResult(List<String> postList) {
        /*规则：
         *  中缀表达式不用比较优先级
         *  将运算数入栈，每读到一个运算符
         *  就弹出栈顶的两个运算数，运算完毕后将结果压入栈
         */
        Stack<String> numStack = new Stack();
        for (int i = 0; i < postList.size(); i++) {
            if (isNum(postList.get(i))) {
                numStack.push(postList.get(i));
            } else if (isOper(postList.get(i), i, postList)) {

                if ("+".equals(postList.get(i))) {
                    Double num2 = Double.parseDouble(numStack.pop());
                    Double num1 = Double.parseDouble(numStack.pop());
                    Double result = num1 + num2;
                    numStack.push(result.toString());
                } else if ("-".equals(postList.get(i))) {
                    Double num2 = Double.parseDouble(numStack.pop());
                    Double num1;
                    //能计算负数开头
                    if (numStack.isEmpty()) {
                        num1 = 0.0;
                    } else {
                        num1 = Double.parseDouble(numStack.pop());
                    }
                    Double result = num1 - num2;
                    numStack.push(result.toString());
                } else if ("*".equals(postList.get(i))) {
                    Double num2 = Double.parseDouble(numStack.pop());
                    Double num1 = Double.parseDouble(numStack.pop());
                    Double result = num1 * num2;
                    numStack.push(result.toString());
                } else if ("/".equals(postList.get(i))) {
                    Double num2 = Double.parseDouble(numStack.pop());
                    Double num1 = Double.parseDouble(numStack.pop());
                    Double result = num1 / num2;
                    numStack.push(result.toString());
                }
            }
        }
        return Double.parseDouble(numStack.pop());
    }

    /*
     *  判断该字符串是否为操作符
     */
    private static boolean isOper(String str, int i, List<String> stringList) {
        if ("*".equals(str) || "/".equals(str) || "+".equals(str) || "(".equals(str) || ")".equals(str)) {
            return true;
        } else if ("-".equals(str)) {
            return !(i >= 1 && "(".equals(stringList.get(i - 1)));
        } else {
            return false;
        }
    }

    /*
     * 判断该字符串是否为操作数
     */
    private static boolean isNum(String str) {
        if ("*".equals(str) || "/".equals(str) || "+".equals(str) || "-".equals(str) || "(".equals(str) || ")".equals(str)) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * 得到操作符的优先级
     */
    private static int getPriority(String c) {
        if ("*".equals(c) || "/".equals(c)) {
            return 2;
        } else if ("+".equals(c) || "-".equals(c)) {
            return 1;
        } else {
            return 999;
        }
    }
}