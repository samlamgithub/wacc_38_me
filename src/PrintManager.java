import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ak7613 on 03/12/14.
 */
public class PrintManager {
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
        return "msg_" + (((totalBools != 0) ? 2 : 0) + visitedStings++) + ":\n" +
                "\t.word 3\n" +
                "\t.ascii\t\"%d\\0\"";
    }

    public String getBoolMsgBlock(){
        return  "msg_0:\n" +
                "\t.word 5\n" +
                "\t.ascii\t\"true\\0\"\n" +
                "msg_1:\n" +
                "\t.word 6\n" +
                "\t.ascii\t\"false\\0\"";
    }

    public String getStringMsgGeneral(int index){
        //int n = totalStrings + index + ((totalBools != 0) ? 2 : 0);
        return "msg_1:\n" +
                "\t.word 5\n" +
                "\t.ascii\t\"%.*s\\0\"";
    }

    public String getStringMsgBlock(String string){
        return "msg_" + (((totalBools != 0) ? 2 : 0) + visitedStings++) + ":\n" +
               ".word " + (string.length() - 2) + "\n" +
               ".ascii\t" + string;

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

        switch (type) {
            case "Int":
                return "p_print_int:\n" +
                        "\tPUSH {lr}\n" +
                        "\tMOV r1, r0\n" +
                        "\tLDR r0, =msg_" + n + "\n" +
                        "\tADD r0, r0, #4\n" +
                        "\tBL printf\n" +
                        "\tMOV r0, #0\n" +
                        "\tBL fflush\n" +
                        "\tPOP {pc}\n";
            case "String":
                return "p_print_string:\n" +
                        "\tPUSH {lr}\n" +
                        "\tLDR r1, [r0]\n" +
                        "\tADD r2, r0, #4\n" +
                        "\tLDR r0, =msg_" + n + "\n" +
                        "\tADD r0, r0, #4\n" +
                        "\tBL printf\n" +
                        "\tMOV r0, #0\n" +
                        "\tBL fflush\n" +
                        "\tPOP {pc}\n";
            case "Bool":
                return "p_print_bool:\n" +
                        "\tPUSH {lr}\n" +
                        "\tCMP r0, #0\n" +
                        "\tLDRNE r0, =msg_0\n" +
                        "\tLDREQ r0, =msg_" + n + "\n" +
                        "\tADD r0, r0, #4\n" +
                        "\tBL printf\n" +
                        "\tMOV r0, #0\n" +
                        "\tBL fflush\n" +
                        "\tPOP {pc}\n";
        }

        return null;

    }

    public String getPrintLnStatement(){
        int n = print.size() + println.size() - totalStrings + ( (totalStrings == 0) ? 0 : 1 );
        return  "p_print_ln:\n" +
                "\tPUSH {lr}\n" +
                "\tLDR r0, =msg_3\n" +
                "\tADD r0, r0, #4\n" +
                "\tBL puts\n" +
                "\tMOV r0, #0\n" +
                "\tBL fflush\n" +
                "\tPOP {pc}\n";
    }




}