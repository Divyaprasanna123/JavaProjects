import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles mathematical expression evaluation using the Shunting Yard algorithm.
 * Supports basic operators, parentheses, and scientific functions.
 */
public class CalculatorLogic {

    private static final Map<String, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put("+", 1);
        PRECEDENCE.put("-", 1);
        PRECEDENCE.put("*", 2);
        PRECEDENCE.put("/", 2);
        PRECEDENCE.put("%", 2);
        PRECEDENCE.put("^", 3);
        // Functions have high precedence
        PRECEDENCE.put("sin", 4);
        PRECEDENCE.put("cos", 4);
        PRECEDENCE.put("tan", 4);
        PRECEDENCE.put("log", 4);
        PRECEDENCE.put("ln", 4);
        PRECEDENCE.put("sqrt", 4);
    }

    /**
     * Evaluates a mathematical expression.
     * @param expression The expression to evaluate.
     * @return The result as a String.
     * @throws Exception If expression is invalid.
     */
    public static String evaluate(String expression) throws Exception {
        if (expression == null || expression.trim().isEmpty()) return "0";
        
        List<String> tokens = tokenize(expression);
        List<String> rpn = infixToRPN(tokens);
        double result = evaluateRPN(rpn);
        
        // Format result: Remove trailing .0 if present
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return String.valueOf(result);
        }
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        // Regex to match numbers, scientific functions, constants and operators/parentheses
        String regex = "(\\d+\\.\\d+|\\d+|sin|cos|tan|log|ln|sqrt|pi|e|[+\\-*/%^()])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression.toLowerCase().replaceAll("\\s+", ""));
        
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        
        // Handle unary minus (e.g., -5, (-5), 2*-3)
        List<String> processedTokens = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals("-")) {
                if (i == 0 || tokens.get(i - 1).equals("(") || PRECEDENCE.containsKey(tokens.get(i - 1))) {
                    processedTokens.add("0");
                    processedTokens.add("-");
                } else {
                    processedTokens.add(token);
                }
            } else {
                processedTokens.add(token);
            }
        }
        
        return processedTokens;
    }

    private static List<String> infixToRPN(List<String> tokens) throws Exception {
        List<String> outputQueue = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token) || token.equals("pi") || token.equals("e")) {
                outputQueue.add(token);
            } else if (isFunction(token)) {
                operatorStack.push(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    outputQueue.add(operatorStack.pop());
                }
                if (operatorStack.isEmpty()) throw new Exception("Mismatched parentheses");
                operatorStack.pop(); 
                if (!operatorStack.isEmpty() && isFunction(operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
            } else if (PRECEDENCE.containsKey(token)) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") &&
                        PRECEDENCE.getOrDefault(operatorStack.peek(), 0) >= PRECEDENCE.get(token)) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            String op = operatorStack.pop();
            if (op.equals("(") || op.equals(")")) throw new Exception("Mismatched parentheses");
            outputQueue.add(op);
        }

        return outputQueue;
    }

    private static double evaluateRPN(List<String> rpn) throws Exception {
        Stack<Double> stack = new Stack<>();

        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (token.equals("pi")) {
                stack.push(Math.PI);
            } else if (token.equals("e")) {
                stack.push(Math.E);
            } else if (isFunction(token)) {
                if (stack.isEmpty()) throw new Exception("Invalid expression");
                double a = stack.pop();
                switch (token) {
                    case "sin": stack.push(Math.sin(Math.toRadians(a))); break;
                    case "cos": stack.push(Math.cos(Math.toRadians(a))); break;
                    case "tan": stack.push(Math.tan(Math.toRadians(a))); break;
                    case "log": stack.push(Math.log10(a)); break;
                    case "ln": stack.push(Math.log(a)); break;
                    case "sqrt": stack.push(Math.sqrt(a)); break;
                }
            } else {
                if (stack.size() < 2) throw new Exception("Invalid expression");
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": 
                        if (b == 0) throw new Exception("Division by zero");
                        stack.push(a / b); 
                        break;
                    case "%": stack.push(a % b); break;
                    case "^": stack.push(Math.pow(a, b)); break;
                }
            }
        }

        if (stack.size() != 1) throw new Exception("Invalid expression");
        return stack.pop();
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFunction(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("tan") ||
               token.equals("log") || token.equals("ln") || token.equals("sqrt");
    }
}
