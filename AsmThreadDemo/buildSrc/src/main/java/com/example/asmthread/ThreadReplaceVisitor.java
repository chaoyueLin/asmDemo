package com.example.asmthread;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class ThreadReplaceVisitor extends AdviceAdapter {

    private static final String TAG = "AsmMethodVisitor -> ";
    private final String methodName;
    private final String className;

    protected ThreadReplaceVisitor(int api, MethodVisitor mv, int access, String name, String desc, String className) {
        super(api, mv, access, name, desc);
        this.className = className;
        this.methodName = name;
        System.out.println(TAG + "CostMethodVisitor -> " + name + ": " + desc);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println(TAG + "visitCode" + this.methodDesc + this.firstLocal + this.methodAccess + this.methodAccess + this.nextLocal + this.mv.toString());
    }

    @Override
    public void visitLabel(Label label) {
        super.visitLabel(label);
        System.out.println(TAG + "visitLabel" + label.info);
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        System.out.println(TAG + "visitInsn");
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        System.out.println(TAG + "visitVarInsn" + opcode + ": " + var);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc);
        System.out.println(TAG + "visitFieldInsn");
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
        System.out.println(TAG + "visitIntInsn");
    }

    @Override
    public void visitLdcInsn(Object cst) {
        super.visitLdcInsn(cst);
        System.out.println(TAG + "visitLdcInsn");
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {
        super.visitMultiANewArrayInsn(desc, dims);
        System.out.println(TAG + "visitMultiANewArrayInsn");
    }

    private boolean find = false;

    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (opcode == Opcodes.NEW && "java/lang/Thread".equals(type)) {
            find = true;
            mv.visitTypeInsn(NEW, "com/example/asmthreaddemo/CustomThread");
            return;
        }
        super.visitTypeInsn(opcode, type);
        System.out.println(TAG + "visitTypeInsn");
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (find && opcode == Opcodes.INVOKESPECIAL && "java/lang/Thread".equals(owner) && !className.equals("com/example/asmthreaddemo/CustomThread")) {
            find = false;
            mv.visitMethodInsn(opcode, "com/example/asmthreaddemo/CustomThread", name, desc, itf);
            System.out.println(TAG + "111111111111="+name);
            return;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
        System.out.println(TAG + "visitMethodInsn: " + opcode + ": " + owner + ": " + name + ": " + desc + ": " + itf);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
        System.out.println(TAG + "visitInvokeDynamicInsn");
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label);
        System.out.println(TAG + "visitJumpInsn: " + opcode + ": " + label.info);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels);
        System.out.println(TAG + "visitLookupSwitchInsn");
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        super.visitTableSwitchInsn(min, max, dflt, labels);
        System.out.println(TAG + "visitTableSwitchInsn");
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type);
        System.out.println(TAG + "visitTryCatchBlock");
    }


    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println(TAG + "onMethodEnter");
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        System.out.println(TAG + "onMethodExit" + opcode);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println(TAG + "visitAnnotation" + desc + visible);
        return super.visitAnnotation(desc, visible);
    }
}
