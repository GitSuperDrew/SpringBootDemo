package com.module.study.springbooth2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

@SpringBootTest
class Demo2ApplicationTests {

    @Test
    void contextLoads() {

    }


    public static void main(String[] args) throws Exception {
//        BigDecimal a = new BigDecimal("2");
//        BigDecimal b = new BigDecimal("-1");
//        System.out.println(a.subtract(b));
//        System.out.println(b.subtract(a));
        String input = "2-(-2)"; // 2 - ( - 2 )
        System.out.println(getResult(input, 4));
    }

    public static BigDecimal getResult(String input, int decimalPlaces) throws Exception {
        //规范输入形式,避免用户输入中文括号
        input = input.replaceAll("（", "(");
        input = input.replaceAll("）", ")");
        //对输入公式,按符号/数字,用空格分开,以便后面分组
        String[] inputs = input.split("");
        String format = "";
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].equals(" ")) {
                continue;
            } else if (inputs[i].equals("(") || inputs[i].equals(")")
                    || inputs[i].equals("+") || inputs[i].equals("-")
                    || inputs[i].equals("*") || inputs[i].equals("/")) {
                format += " " + inputs[i] + " ";
            } else {
                format += inputs[i];
            }
        }

        //
        List<String> strings = changeInfixExpressionToPostfixExpression(format);
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals("+")) {
                BigDecimal a = new BigDecimal(stack.pop());
                BigDecimal b = new BigDecimal(stack.pop());
                stack.add(b.add(a).toString());
            } else if (strings.get(i).equals("-")) {
                BigDecimal a = new BigDecimal(stack.pop());
                BigDecimal b = new BigDecimal(stack.pop());
                stack.add(b.subtract(a).toString());
            } else if (strings.get(i).equals("*")) {
                BigDecimal a = new BigDecimal(stack.pop());
                BigDecimal b = new BigDecimal(stack.pop());
                stack.add(b.multiply(a).toString());
            } else if (strings.get(i).equals("/")) {
                BigDecimal a = new BigDecimal(stack.pop());
                BigDecimal b = new BigDecimal(stack.pop());
                //这里的1000是做除法以后计算的精确位数,就算1000位也并不会拖慢程序速度,一个公式0.01秒内就能算完,后面的是除不尽的四舍五入
                stack.add(b.divide(a, 1000, BigDecimal.ROUND_HALF_DOWN).toString());
            } else {
                stack.add(strings.get(i));
            }
        }
        //返回的时候格式化一下,取四舍五入小数点后两位
        return new BigDecimal(stack.pop()).setScale(decimalPlaces, BigDecimal.ROUND_HALF_DOWN);
    }


    public static List<String> changeInfixExpressionToPostfixExpression(String input) {
        List<String> resultList = new ArrayList<String>();
        Stack<String> tempStack = new Stack<String>();
        String[] splitArray = input.split(" ");
        for (int i = 0; i < splitArray.length; i++) {
            if (splitArray[i].equals("")) {
                continue;
            }
            //如果字符是右括号的话,说明前面一定有过左括号,将栈里第一个左括号之前全部添加到List里
            if (splitArray[i].equals(")")) {
                while (!tempStack.peek().equals("(")) {
                    resultList.add(tempStack.pop());
                }
                tempStack.pop();//去除前面的左括号
            } else if (splitArray[i].equals("(")) {
                //如果是左括号,那么直接添加进去
                tempStack.add("(");
            } else if (splitArray[i].equals("+") || splitArray[i].equals("-")) {
                //如果是加减号,还需要再判断
                if (tempStack.empty() || tempStack.peek().equals("(")) {
                    tempStack.add(splitArray[i]);
                } else if (tempStack.peek().equals("+") || tempStack.peek().equals("-")) {
                    //读临时栈里的顶部数据,如果也是加减就取出来一个到结果列,这个放临时栈,如果是乘除就开始取到右括号为止
                    resultList.add(tempStack.pop());
                    tempStack.add(splitArray[i]);
                } else {
                    while (!tempStack.empty()) {
                        if (tempStack.peek().equals("(")) {
                            break;
                        } else {
                            resultList.add(tempStack.pop());
                        }
                    }
                    tempStack.add(splitArray[i]);
                }
            } else if (splitArray[i].equals("*") || splitArray[i].equals("/")) {
                //如果是乘除
                if (!tempStack.empty()) {
                    //判断临时栈里栈顶是啥,如果是乘除,取一个出来到结果列,添这个进临时栈
                    if (tempStack.peek().equals("*") || tempStack.peek().equals("/")) {
                        resultList.add(tempStack.pop());
                    }
                }
                tempStack.add(splitArray[i]);
            } else {
                //说明是非符号,都添加进去
                resultList.add(splitArray[i]);
            }
        }
        //遍历完了,把临时stack里的东西都加到结果stack里去
        while (!tempStack.empty()) {
            resultList.add(tempStack.pop());
        }
        return resultList;
    }



}
