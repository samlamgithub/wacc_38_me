.data
string_msg:
.word 5
.ascii"%.*s\0"
overflow:
.word 16
.ascii"Overflow Error\n\0"
int_msg:
.word 3
.ascii"%d\0"




.text
.global main

main:
PUSH {lr}
SUB sp, sp, #4
LDR r4, =0
STR r4, [sp]
B L0
L0:
LDR r4, [sp]
MOV r0, r4
BL p_print_int
LDR r4, [sp]
LDR r5, =1
ADDS r4, r4, r5
BLVS p_throw_overflow_error
STR r4, [sp]
B L1
L1:
LDR r4, [sp]
LDR r5, =5
CMP r4, r5
MOVLT r4, #1
MOVGE r4, #0
CMP r4, #1
BEQ L0
ADD sp, sp, #4
LDR r0, =0
POP {pc}
.ltorg
p_throw_runtime_error:
BL p_print_string
MOV r0, #-1
BL exit
p_throw_overflow_error:
LDR r0, =overflow
BL p_throw_runtime_error
p_print_int:
PUSH {lr}
MOV r1, r0
LDR r0, =int_msg
ADD r0, r0, #4
BL printf
MOV r0, #0
BL fflush
POP {pc}
p_print_string:
PUSH {lr}
LDR r1, [r0]
ADD r2, r0, #4
LDR r0, =string_msg
ADD r0, r0, #4
BL printf
MOV r0, #0
BL fflush
POP {pc}

