	.text
	.syntax unified
	.globl	main                            @ -- Begin function main
	.p2align	2
	.type	main,%function
	.code	32                              @ @main
main:
	.fnstart
@ %bb.0:
	.save	{r11, lr}
	push	{r11, lr}
	.setfp	r11, sp
	mov	r11, sp
	.pad	#16
	sub	sp, sp, #16
	movw	r2, #0
	str	r2, [r11, #-4]
	str	r0, [sp, #8]
	str	r1, [sp, #4]
	ldr	r0, .LCPI0_0
.LPC0_0:
	add	r0, pc, r0
	bl	printf
	movw	r0, #0
	mov	sp, r11
	pop	{r11, pc}
	.p2align	2
@ %bb.1:
.LCPI0_0:
	.long	.L.str-(.LPC0_0+8)
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cantunwind
	.fnend
                                        @ -- End function
	.type	.L.str,%object                  @ @.str
	.section	.rodata.str1.1,"aMS",%progbits,1
.L.str:
	.asciz	"hello arm\r\n"
	.size	.L.str, 12

	.section	".note.GNU-stack","",%progbits
