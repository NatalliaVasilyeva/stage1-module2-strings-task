package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String[] firstPart = signatureString.split("[()]");
        MethodSignature methodSignature;
        String[] signature = firstPart[0].replace("\\)", "").split("\\s");
        List<MethodSignature.Argument> listOfArguments = new ArrayList<>();
        if(firstPart.length != 1) {
            String[] arguments = firstPart[1].split(", ");
            Arrays.stream(arguments).collect(Collectors.toList()).
                forEach(line -> {
                    String[] lines = line.split(" ");
                    MethodSignature.Argument argument = new MethodSignature.Argument(lines[0], lines[1]);
                    listOfArguments.add(argument);
                });
        }
        if (signature.length == 3) {
            methodSignature = new MethodSignature(signature[2], listOfArguments);
            methodSignature.setAccessModifier(signature[0]);
            methodSignature.setReturnType(signature[1]);
        } else {
            methodSignature = new MethodSignature(signature[1], listOfArguments);
            methodSignature.setReturnType(signature[0]);
        }

        return methodSignature;
    }
}