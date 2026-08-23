    .text
    .global raw_syscall
    .type raw_syscall, %function

raw_syscall:
    mov r12, sp
    stmfd sp!, {r4-r7}
    mov r7, r0
    mov r0, r1
    mov r1, r2
    mov r2, r3
    ldmia r12, {r3-r6}
    svc 0
    ldmfd sp!, {r4-r7}
    bx lr