



.text
.global main

main:
PUSH {lr}
SUB sp, sp, #4
LDR r0, =20
BL malloc
MOV r4, r0
LDR r5, =43
STR r5, [r4, #4]
LDR r5, =2
STR r5, [r4, #8]
LDR r5, =18
STR r5, [r4, #12]
LDR r5, =1
STR r5, [r4, #16]
LDR r5, =4
STR r5, [r4]
STR r4, [sp]
LDR r4, [sp]
MOV r0, len r4
BL p_print_ln
ADD sp, sp, #4
LDR r0, =0
POP {pc}
.ltorgp_print_ln:
	PUSH {lr}
	LDR r0, =msg_3
	ADD r0, r0, #4
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}

