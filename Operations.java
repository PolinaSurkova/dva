package com.company;
import java.util.Stack;

public class Operations {
    /**
     * @param text исходное выражение
     * @return булево значение - проверка корректности строки
     */
    private static boolean check(String text)
    {
        int open = 0;
        int close = 0;
        for(int i = 0; i < text.length(); i++)
        {
            switch (text.charAt(i)) {
                case '+':
                case '-':
                case '*':
                case '/':
                    if(i == 0 || i == text.length() - 1) return false;
                    if(text.charAt(i++) == '+' || text.charAt(i++) == '-' || text.charAt(i++) == '*' || text.charAt(i++) == '/') return false;
                case '(':
                    open++;
                    if(text.charAt(i++) == '+' || text.charAt(i++) == '-' || text.charAt(i++) == '*' || text.charAt(i++) == '/' || text.charAt(i++) == ')') return false;
                    if(i == text.length() - 1) return false;
                    i++;
                case ')':
                    close++;
                    if(text.charAt(i--) == '+' || text.charAt(i--) == '-' || text.charAt(i--) == '*' || text.charAt(i--) == '/' || text.charAt(i--) == '(') return false;
                    if(i == 0) return false;
                    i++;
                default:
                    if(text.charAt(i) >= '0' && text.charAt(i) <= '9')
                    {
                        if(text.charAt(i++) == '(' || text.charAt(i--) == ')') return false;
                        i++;
                    }
                    else return false;
            }
        }
        if (close != open) return false;
        return true;
    }

    /**
     * @param ch символ, для которого определяем приоритет
     * @return Возвращаем 0 - если это цифра, 3 - если умножение или деление,
     * 2 - если плюс или минус, 1 - (, -1 - ).
     */
    private static int Type(char ch) {
        if (ch == '*' || ch == '/') return 3;
        else if (ch == '+' || ch == '-') return 2;
        else if (ch == '(') return 1;
        else if (ch == ')') return -1;
        return 0;
    }

    /**
     * @param text - искомое выражение
     * @return идем по строке и, в зависимости от типа, добавляем символ в нужный контейнер.
     */
    private static String reloading(String text) {
        boolean b = check(text);
        if(b) throw new RuntimeException("Выражение некорректно! ");
        Stack<Character> temp = new Stack<Character>();
        String str = "";
        for(int i = 0; i < text.length(); i++) {
            char sym = text.charAt(i);
            int type = Type(sym);
            if (type == 0) str += text.charAt(i);
            if (type == 1) temp.push(text.charAt(i));
            if (type > 1) {
                str += ' ';
                while (!temp.empty()) {
                    if (Type(temp.peek()) >= type)
                        str += temp.pop();
                    else break;
                }
                temp.push(text.charAt(i));
            }
            if (type == -1) {
                str += ' ';
                while (Type(temp.peek()) != 1)
                    str += temp.pop();
                temp.pop();
            }
        }
        while (!temp.empty())
            str += temp.pop();
        return str;
    }

    /**
     * @param str новый вид нашего выражения
     * @return идем по измененной строке и работаем с ней, производя вычисления в нужном нам порядке
     */
    private static double count(String str) {
        String s = "";
        Stack<Double> stack = new Stack<Double>();
        for(int i = 0;i < str.length(); i++) {
            if (str.charAt(i) == ' ') continue;
            if (Type(str.charAt(i)) == 0) {
                while (str.charAt(i) != ' ' && Type(str.charAt(i)) == 0) {
                    s += str.charAt(i++);
                    if (i == str.length()) break;
                }
                stack.push(Double.parseDouble(s));
                s = "";
            }
            if (Type(str.charAt(i)) > 1) {
                double curr1 = stack.pop();
                double curr2 = stack.pop();
                if (str.charAt(i) == '+')
                    stack.push(curr2 + curr1);
                if (str.charAt(i) == '-')
                    stack.push(curr2 - curr1);
                if (str.charAt(i) == '*')
                    stack.push(curr2 * curr1);
                if (str.charAt(i) == '/')
                    stack.push(curr2 / curr1);
            }
        }
        return stack.pop();
    }

    /**
     * @param str выражение, которое нам нужно посчитать
     * @return численное значение выражения
     */
    public static double result (String str) {
        return (Operations.count(Operations.reloading(str)));
    };
}