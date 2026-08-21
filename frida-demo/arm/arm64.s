	.text
	.file	"arm64.cpp"
	.globl	testFunction                    // -- Begin function testFunction
	.p2align	2
	.type	testFunction,@function
testFunction:                           // @testFunction
	.cfi_startproc
// %bb.0:
	sub	sp, sp, #16                     // =16
	.cfi_def_cfa_offset 16
	mov	x9, x8
	str	x1, [sp, #8]
	ldr	x8, [x0]
	ldr	x11, [sp, #8]
	mov	x10, #1
	mul	x10, x10, x11
	add	x8, x8, x10
	str	x8, [x9]
	ldr	x8, [x0, #8]
	ldr	x11, [sp, #8]
	mov	x10, #2
	mul	x10, x10, x11
	add	x8, x8, x10
	str	x8, [x9, #8]
	ldr	x8, [x0, #16]
	ldr	x11, [sp, #8]
	mov	x10, #3
	mul	x10, x10, x11
	add	x8, x8, x10
	str	x8, [x9, #16]
	add	sp, sp, #16                     // =16
	ret
.Lfunc_end0:
	.size	testFunction, .Lfunc_end0-testFunction
	.cfi_endproc
                                        // -- End function
	.globl	main                            // -- Begin function main
	.p2align	2
	.type	main,@function
main:                                   // @main
	.cfi_startproc
// %bb.0:
	sub	sp, sp, #128                    // =128
	stp	x29, x30, [sp, #112]            // 16-byte Folded Spill
	add	x29, sp, #112                   // =112
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	mov	w8, wzr
	str	w8, [sp, #12]                   // 4-byte Folded Spill
	stur	wzr, [x29, #-4]
	stur	w0, [x29, #-8]
	stur	x1, [x29, #-16]
	mov	x8, #1
	stur	x8, [x29, #-40]
	mov	x8, #2
	stur	x8, [x29, #-32]
	mov	x8, #3
	stur	x8, [x29, #-24]
	ldur	q0, [x29, #-40]
	add	x0, sp, #16                     // =16
	str	q0, [sp, #16]
	ldur	x8, [x29, #-24]
	str	x8, [sp, #32]
	ldursw	x1, [x29, #-8]
	add	x8, sp, #48                     // =48
	bl	testFunction
	ldr	x1, [sp, #48]
	ldr	x2, [sp, #56]
	ldr	x3, [sp, #64]
	adrp	x0, .L.str
	add	x0, x0, :lo12:.L.str
	bl	printf
	ldr	w0, [sp, #12]                   // 4-byte Folded Reload
	ldp	x29, x30, [sp, #112]            // 16-byte Folded Reload
	add	sp, sp, #128                    // =128
	ret
.Lfunc_end1:
	.size	main, .Lfunc_end1-main
	.cfi_endproc
                                        // -- End function
	.type	.L.str,@object                  // @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%ld, %ld, %ld\r\n"
	.size	.L.str, 16

	.ident	"Android (8481493, based on r416183c2) clang version 12.0.9 (https://android.googlesource.com/toolchain/llvm-project c935d99d7cf2016289302412d708641d52d2f7ee)"
	.section	".note.GNU-stack","",@progbits
