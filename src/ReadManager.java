import java.util.List;

public class ReadManager {
    private List<Type> read;
    private int totalStrings, totalInts, totalBools, totalChars;

    public ReadManager(List<Type> read){
        this.read = read;
    }

    public String getAllReadStatements(){
        String res = new String();
        int ind = 0;
        if(totalStrings != 0){
            res += getReadStatement(new StringType());
        }
        if(totalInts != 0) res += getReadStatement(new IntType());
        if(totalBools != 0) res += getReadStatement(new BoolType());
        if(totalChars != 0) res += getReadStatement(new CharType());
        return res;
    }

    private String getReadStatement(Type t){
        String type = t.toString();
        switch (type){
            case "Char": return getReadCharStatement();
            case "Int": return getReadIntStatement();

        }
        return null;
    }

    public String getReadIntMsgBlock(){
        return "read_ch:\n" +
                "\t.word 3\n" +
                "\t.ascii\t\" %d\\0\"\n";
    }

    public String getReadCharMsgBlock(){
        return "read_int:\n" +
                "\t.word 4\n" +
                "\t.ascii\t\" %c\\0\"\n";
    }


    private String getReadIntStatement(){
        return "p_read_int:\n" +
                "\tPUSH {lr}\n" +
                "\tMOV r1, r0\n" +
                "\tLDR r0, =read_int\n" +
                "\tADD r0, r0, #4\n" +
                "\tBL scanf\n" +
                "\tPOP {pc}\n";
    }

    private String getReadCharStatement(){
        return "p_read_char:\n" +
                "\tPUSH {lr}\n" +
                "\tMOV r1, r0\n" +
                "\tLDR r0, =read_ch\n" +
                "\tADD r0, r0, #4\n" +
                "\tBL scanf\n" +
                "\tPOP {pc}\n";
    }


    private void countStatements(){
        for(Type t : read){
            if(t.toString().equals("String")) totalStrings++;
            else if(t.toString().equals("Bool")) totalBools++;
            else if(t.toString().equals("Int")) totalInts++;
            else if(t.toString().equals("Char")) totalChars++;
        }
    }
}
