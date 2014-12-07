.data
println_msg:
	.word 1
	.ascii	"\0"




.text
.global main

main:
PUSH {lr}
MOV r4, #'#'
MOV r0, r4
BL putchar
BL p_print_ln
LDR r0, =0
POP {pc}
.ltorg
p_print_ln:
	PUSH {lr}
	LDR r0, =println_msg
	ADD r0, r0, #4
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}

