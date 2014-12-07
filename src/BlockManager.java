
public class BlockManager {
	
	
	public String overflowMsgBlock(){
		String string = "Overflow Error";
        return "overflow:\n" +
                "\t.word " + string.length() + "\n" +
                "\t.ascii\t" + "\"" + string + "\"\n";
	}
	
	public String divisionByZeroMsgBlock(){
		String string = "Division By Zero";
        return "zerodiv:\n" +
                "\t.word " + string.length() + "\n" +
                "\t.ascii\t" + "\"" + string + "\"\n";
	}
	
	public String checkDivisionByZero() {
		return "p_check_divide_by_zero:\n" + "\tPUSH {lr}\n"
				+ "\tCMP r1, #0\n" + "\tLDREQ r0, =zerodiv\n"
				+ "\tBLEQ p_throw_runtime_error\n" + "\tPOP {pc}\n";
	}
	
	public String throwRuntimeError() {
		return "p_throw_runtime_error:\n" + "\tBL p_print_string\n"
				+ "\tMOV r0, #-1\n" + "\tBL exit\n";
	}
	
	
	public String printString() {
		return "p_print_string:\n" + "\tPUSH {lr}\n" + "\tLDR r1, [r0]\n"
				+ "\tADD r2, r0, #4\n" + "\tLDR r0, =msg_1\n"
				+ "\tADD r0, r0, #4\n" + "\tBL printf\n"
				+ "\tMOV r0, #0\n" + "\tBL fflush\n" + "\tPOP {pc}\n";
	}
	
	public String throwOwerflowError() {
		return "p_throw_overflow_error:\n" 
	            + "\tLDR r0, =overflow\n"
				+ "\tBL p_throw_runtime_error\n";
	           
	}
	

}
