	.text
	.syntax unified
	.globl	main                            @ -- Begin function main
	.p2align	2
	.type	main,%function
	.code	32                              @ @main
main:
	.fnstart
    push {r4, r5, r6}
    ldm sp, {r0, r1, r2}
    ldr r0, [r1]
    ldr r2, [r1, #4]
    mov r0, #34
    mov r0, r2 
    bl 0xaaaaa3a0
    b 0xaaaaa4a0
    mov r0, #1
    mov r0, #5
    add r0, r0, #5
    sub r0, r0, #1
    and r0, #0x9
    orr r0, #0x2
    eor r0, #0xAA
    bic r0, r0, #0xF
    lsl r0, #4
    ror r0, #4
    lsr r0, #8
    mov r0, #0
    pop {r4, r5, r6}
    bx lr
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cantunwind
	.fnend

	.section	".note.GNU-stack","",%progbits
