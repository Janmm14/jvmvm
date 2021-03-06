/**
 * Copyright (c) 2005 Nuno Cruces
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holders nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **/

package com.github.drxaos.jvmvm.vm.insn;

import com.github.drxaos.jvmvm.vm.Frame;
import com.github.drxaos.jvmvm.vm.VirtualMachine;

import java.lang.reflect.Array;

import static org.objectweb.asm.Opcodes.*;

public abstract class IntInsn extends Insn {
    public static Insn getInsn(int opcode, int operand) {
        switch (opcode) {
            case BIPUSH:
            case SIPUSH:
                return new PushInsn(operand, opcode);
            case NEWARRAY:
                return new NewArrayInsn(operand);
            default:
                assert false;
                return null;
        }
    }


    static final class PushInsn extends IntInsn {
        private final int opcode;
        private final int i;

        PushInsn(int i, int opcode) {
            this.i = i;
            this.opcode = opcode;
        }

        public void execute(VirtualMachine vm) {
            vm.getFrame().pushInt(i);
        }

        @Override
        public String toString() {
            return getOpcodeName(opcode) + " " + i;
        }
    }

    static final class NewArrayInsn extends IntInsn {
        private final Class<?> c;

        NewArrayInsn(int i) {
            switch (i) {
                case 4:
                    this.c = boolean.class;
                    return;
                case 5:
                    this.c = char.class;
                    return;
                case 6:
                    this.c = float.class;
                    return;
                case 7:
                    this.c = double.class;
                    return;
                case 8:
                    this.c = byte.class;
                    return;
                case 9:
                    this.c = short.class;
                    return;
                case 10:
                    this.c = int.class;
                    return;
                case 11:
                    this.c = long.class;
                    return;
                default:
                    this.c = null;
                    assert false;
            }
        }

        public void execute(VirtualMachine vm) {
            Frame frame = vm.getFrame();
            frame.pushObject(Array.newInstance(c, frame.popInt()));
        }

        @Override
        public String toString() {
            return getOpcodeName(NEWARRAY) + " " + c.getName();
        }
    }
}