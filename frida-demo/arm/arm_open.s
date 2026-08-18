	.text
	.syntax unified
	.globl	main                            @ -- Begin function main
	.p2align	2
	.type	main,%function
	.code	32                              @ @main
main:
	.fnstart
    push {r10, r11, lr}
    mov r11, r1
    ldr r0, [r11, #4]
    mov r2, #420
    mov r1, #66
    bl open
    mov r10, r0
    ldr r0, [r11, #8]
    bl strlen
    ldr r1, [r11, #8]
    mov r2, r0
    mov r0, r10
    bl write
    mov r0, r10
    bl close
    pop {r10, r11, lr}
    mov r0, #0
    bx lr
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cantunwind
	.fnend

	.section	".note.GNU-stack","",%progbits
