import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 created by Artem
 */
public class PrintManager {
    //hi this is comment
    private List<Type> print;
    private List<Type> println;
    private int totalStrings, totalInts, totalBools, totalChars;
    private int visitedStings;

    public PrintManager(List<Type> print, List<Type> println) {
        this.print = print;
        this.println = println;
        countStatements();
        visitedStings = 0;
    }

    public String getIntMsgBlock(){
        return "int_msg:\n" +
                "\t.word 3\n" +
                "\t.ascii\t\"%d\\0\"\n";
    }

    public String getBoolMsgBlock(){
        return  "bool_msg_true:\n" +
                "\t.word 5\n" +
                "\t.ascii\t\"true\\0\"\n" +
                "bool_msg_false:\n" +
                "\t.word 6\n" +
                "\t.ascii\t\"false\\0\"\n";
    }

    public String getPrintlnMsgBlock(){
        return "println_msg:\n" +
                "\t.word 1\n" +
                "\t.ascii\t\"\\0\"\n";
    }

    public String getStringMsgGeneral(){
        return "string_msg:\n" +
                "\t.word 5\n" +
                "\t.ascii\t\"%.*s\\0\"\n";
    }

    public String getStringMsgBlock(String string, int i){
        return "msg_" + i + ":\n" +
               "\t.word " + (string.length() - 2) + "\n" +
               "\t.ascii\t" + string + "\n";
    }

    private void countStatements(){
        for(Type t : print){
            if(t.toString().equals("String")) totalStrings++;
            else if(t.toString().equals("Bool")) totalBools++;
            else if(t.toString().equals("Int")) totalInts++;
            else if(t.toString().equals("Char")) totalChars++;
        }
        for(Type t : println){
            if(t.toString().equals("String")) totalStrings++;
            else if(t.toString().equals("Bool")) totalBools++;
            else if(t.toString().equals("Int")) totalInts++;
            else if(t.toString().equals("Char")) totalChars++;
        }
    }

    public String getAllPrintStatements(){
        String res = new String();
        int ind = 0;
        if(totalStrings != 0){
            res += getPrintStatement(new StringType(), ind++);
        }
        if(totalInts != 0) res += getPrintStatement(new IntType(), ind++);
        if(totalBools != 0) res += getPrintStatement(new BoolType(), ind++);
        if(totalChars != 0) res += getPrintStatement(new CharType(), ind++);
        if(println.size() != 0) res += getPrintLnStatement();
        return res;
    }

    public String getPrintStatement(Type t, int index){
        String type = t.toString();
        String s = t.toString();
        int n = totalStrings + index + ((totalBools != 0) ? 2 : 0);
        return null;

    }

    public String getStringPrintStatement(){
        return "p_print_string:\n" +
                "\tPUSH {lr}\n" +
                "\tLDR r1, [r0]\n" +
                "\tADD r2, r0, #4\n" +
                "\tLDR r0, =string_msg\n" +
                "\tADD r0, r0, #4\n" +
                "\tBL printf\n" +
                "\tMOV r0, #0\n" +
                "\tBL fflush\n" +
                "\tPOP {pc}\n";
    }

    public String getIntPrintStatement(){
	//test
        return "p_print_int:\n" +
                "\tPUSH {lr}\n" +
                "\tMOV r1, r0\n" +
                "\tLDR r0, =int_msg\n" +
                "\tADD r0, r0, #4\n" +
                "\tBL printf\n" +
                "\tMOV r0, #0\n" +
                "\tBL fflush\n" +
                "\tPOP {pc}\n";
    }

    public String getBoolPrintStatement(){
        return "p_print_bool:\n" +
                "\tPUSH {lr}\n" +
                "\tCMP r0, #0\n" +
                "\tLDRNE r0, =bool_msg_true\n" +
                "\tLDREQ r0, =bool_msg_false\n" +
                "\tADD r0, r0, #4\n" +
                "\tBL printf\n" +
                "\tMOV r0, #0\n" +
                "\tBL fflush\n" +
                "\tPOP {pc}\n";
    }

    public String getPrintLnStatement(){
        int n = print.size() + println.size() - totalStrings + ( (totalStrings == 0) ? 0 : 1 );
        return  "p_print_ln:\n" +
                "\tPUSH {lr}\n" +
                "\tLDR r0, =println_msg\n" +
                "\tADD r0, r0, #4\n" +
                "\tBL puts\n" +
                "\tMOV r0, #0\n" +
                "\tBL fflush\n" +
                "\tPOP {pc}\n";
    }




}
