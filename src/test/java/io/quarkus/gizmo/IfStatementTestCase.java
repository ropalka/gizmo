package io.quarkus.gizmo;

import org.junit.Assert;
import org.junit.Test;

public class IfStatementTestCase {

    @Test
    public void testIfStatement() throws Exception {
        TestClassLoader cl = new TestClassLoader(getClass().getClassLoader());
        try (ClassCreator creator = ClassCreator.builder().classOutput(cl).className("com.MyTest").interfaces(MyInterface.class)
                .build()) {
            MethodCreator method = creator.getMethodCreator("transform", String.class, String.class);
            ResultHandle equalsResult = method.invokeVirtualMethod(
                    MethodDescriptor.ofMethod(Object.class, "equals", boolean.class, Object.class), method.getMethodParam(0),
                    method.load("TEST"));
            BranchResult branch = method.ifNonZero(equalsResult);
            branch.trueBranch().returnValue(branch.trueBranch().load("TRUE BRANCH"));
            branch.falseBranch().returnValue(method.getMethodParam(0));

        }
        MyInterface myInterface = (MyInterface) cl.loadClass("com.MyTest").newInstance();
        Assert.assertEquals("PARAM", myInterface.transform("PARAM"));
        Assert.assertEquals("TRUE BRANCH", myInterface.transform("TEST"));
    }

    @Test
    public void testIfNull() throws Exception {
        TestClassLoader cl = new TestClassLoader(getClass().getClassLoader());
        try (ClassCreator creator = ClassCreator.builder().classOutput(cl).className("com.MyTest").interfaces(MyInterface.class)
                .build()) {
            MethodCreator method = creator.getMethodCreator("transform", String.class, String.class);
            ResultHandle nullHandle = method.loadNull();
            BranchResult branch = method.ifNull(nullHandle);
            branch.trueBranch().returnValue(branch.trueBranch().load("TRUE"));
            branch.falseBranch().returnValue(branch.falseBranch().load("FALSE"));
        }
        MyInterface myInterface = (MyInterface) cl.loadClass("com.MyTest").newInstance();
        Assert.assertEquals("TRUE", myInterface.transform("TEST"));
    }

    @Test
    public void testIfGreaterThanZero() throws Exception {
        TestClassLoader cl = new TestClassLoader(getClass().getClassLoader());
        try (ClassCreator creator = ClassCreator.builder().classOutput(cl).className("com.MyTest").interfaces(MyInterface.class)
                .build()) {
            MethodCreator method = creator.getMethodCreator("transform", String.class, String.class);
            ResultHandle lengthHandle = method.invokeVirtualMethod(MethodDescriptor.ofMethod(String.class, "length", int.class),
                    method.getMethodParam(0));
            BranchResult branch = method.ifGreaterThanZero(lengthHandle);
            branch.trueBranch().returnValue(branch.trueBranch().load("TRUE"));
            branch.falseBranch().returnValue(branch.falseBranch().load("FALSE"));
        }
        MyInterface myInterface = (MyInterface) cl.loadClass("com.MyTest").newInstance();
        Assert.assertEquals("TRUE", myInterface.transform("TEST"));
        Assert.assertEquals("FALSE", myInterface.transform(""));
    }

    @Test
    public void testIfIntegerEqual() throws Exception {
        TestClassLoader cl = new TestClassLoader(getClass().getClassLoader());
        try (ClassCreator creator = ClassCreator.builder().classOutput(cl).className("com.MyTest").interfaces(MyInterface.class)
                .build()) {
            MethodCreator method = creator.getMethodCreator("transform", String.class, String.class);
            ResultHandle lengthHandle = method.invokeVirtualMethod(MethodDescriptor.ofMethod(String.class, "length", int.class),
                    method.getMethodParam(0));
            BranchResult branch = method.ifIntegerEqual(lengthHandle, method.load(3));
            branch.trueBranch().returnValue(branch.trueBranch().load("TRUE"));
            branch.falseBranch().returnValue(branch.falseBranch().load("FALSE"));
        }
        MyInterface myInterface = (MyInterface) cl.loadClass("com.MyTest").newInstance();
        Assert.assertEquals("TRUE", myInterface.transform("TES"));
        Assert.assertEquals("FALSE", myInterface.transform("TEST"));
    }

    @Test
    public void testIfIntegerLessThan() throws Exception {
        TestClassLoader cl = new TestClassLoader(getClass().getClassLoader());
        try (ClassCreator creator = ClassCreator.builder().classOutput(cl).className("com.MyTest").interfaces(MyInterface.class)
                .build()) {
            MethodCreator method = creator.getMethodCreator("transform", String.class, String.class);
            ResultHandle lengthHandle = method.invokeVirtualMethod(MethodDescriptor.ofMethod(String.class, "length", int.class),
                    method.getMethodParam(0));
            BranchResult branch = method.ifIntegerLessThan(lengthHandle, method.load(3));
            branch.trueBranch().returnValue(branch.trueBranch().load("TRUE"));
            branch.falseBranch().returnValue(branch.falseBranch().load("FALSE"));
        }
        MyInterface myInterface = (MyInterface) cl.loadClass("com.MyTest").newInstance();
        Assert.assertEquals("TRUE", myInterface.transform("T"));
        Assert.assertEquals("FALSE", myInterface.transform("TEST"));
    }
}
