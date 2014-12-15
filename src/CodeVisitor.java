import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class CodeVisitor extends BasicParserBaseVisitor<String> {

	RegisterManager regMan;
	FunctionTable<Type> functable;
	private Function<String> currentFunction;
	private List<String> errorList;
	boolean minus;
	boolean takeFun;
	List<String> msgs;
	int numVar;
	private InstructionManager insMan;
	int strNumber = 0; // number of string for declarations
	String code = new String();
	private final String PC = "r15";
	private final String SP = "r16";
	private final String LR = "r14";
	private final String CPSR = "r15";
	List<Boolean> regs;
	private Scope scope;
	private Scope currentScope;
	private int indexOfFunctions;
	private int scopeIndex;
	private List<Type> read;
	private List<Type> print;
	private List<Type> println;
	private String unaryTemp = "";
	private boolean inFunc;
	private Stack stack;
	private LabelManager labelMan;
	private String currentf;
	private int printCount = 0, printLNCount = 0;
	private PrintManager printManager;
	private String preCode, postCode; // for print, error statements and msg
										// statements
	private int currentVarSpace;

	private final List<String> fixedLabels = new ArrayList<String>(
			Arrays.asList("p_check_divide_by_zero:\n" + "PUSH {lr}\n"
					+ "CMP r1, #0\n" + "LDREQ r0, =msg_0\n"
					+ "BLEQ p_throw_runtime_error\n" + "POP {pc}\n",
					"p_throw_runtime_error:\n" + "BL p_print_string\n"
							+ "MOV r0, #-1\n" + "BL exit\n",
					"p_print_string:\n" + "PUSH {lr}\n" + "LDR r1, [r0]\n"
							+ "ADD r2, r0, #4\n" + "LDR r0, =msg_1\n"
							+ "ADD r0, r0, #4\n" + "BL printf\n"
							+ "MOV r0, #0\n" + "BL fflush\n" + "POP {pc}\n"));
	private Map<String, Integer> funcParaSpace;
	private String currentArray;
	private int currentArrayLength;
	private ArrayData currentArrayData;

	// StringBuilder sb = new StringBuilder();

	public CodeVisitor(FunctionTable<Type> functable2, Scope scope,
			List<Type> read, List<Type> print, List<Type> println,
			Map<String, Integer> funcParaSpace) {
		functable = functable2;
		this.scope = scope;
		currentScope = scope;
		this.read = read;
		this.funcParaSpace = funcParaSpace;
		this.print = print;
		this.println = println;
		printManager = new PrintManager(print, println);
		preCode = new String();
		postCode = new String();
	}

	public String getCode() {
		return (preCode.length() != 0 ? preCode : "") + insMan.getFinalString()
				+ postCode;
	}

	@Override
	public String visitProg(BasicParser.ProgContext ctx) {
		currentVarSpace = 0;
		stack = new Stack();
		currentFunction = null;
		currentf = "";
		System.out.println("space: " + scope.getSpace());
		scope.printTable();
		labelMan = new LabelManager();
		// System.out.println("space2: " +
		// scope.getChildScopes().get(0).getSpace());
		System.out.println("visit program");
		regMan = new RegisterManager();
		insMan = new InstructionManager();
		msgs = new ArrayList<String>();
		indexOfFunctions = 0;
		scopeIndex = 0;
		numVar = 0;
		regs = new ArrayList<>();
		for (Boolean b : regs) {
			b = false;
		}
		insMan.addInstruction(".text");
		insMan.addInstruction(".global main");
		InstructionManager save1 = insMan;
		insMan = new InstructionManager();
		for (BasicParser.FunctionContext function : ctx.function()) {
			InstructionManager save = insMan;
			insMan = new InstructionManager();
			visit(function);
			String funcString = insMan.getFinalString();
			insMan = save;
			insMan.addInstruction(funcString);
		}
		String allFunctionStirng = insMan.getFinalString();
		insMan = save1;
		insMan.addInstruction(allFunctionStirng);
		insMan.addInstruction("main:");
		insMan.addInstruction("PUSH {lr}");

		if (currentScope.getSpace() != 0) {
			insMan.addInstruction("SUB sp, sp, #" + currentScope.getSpace());
		}
		System.out.println("space: " + currentScope.getSpace());
		System.out.println("size: " + regMan.getTotalSize());
		InstructionManager msgManager = new InstructionManager();
		stack.enterScope(scope.getSpace());
		String progRest = visit(ctx.statement());
		if (msgs.size() != 0) {
			System.out.println("YES");
			msgManager.addInstruction(".data\n\n");
			int i = 0;
			for (String s : msgs) {
				msgManager.addInstruction("msg_" + i + ":");
				msgManager.addInstruction(".word " + (s.length() - 2));
				msgManager.addInstruction(".ascii " + s);
				i++;
			}
		}
		String everything = msgManager.getFinalString() + "\n\n"
				+ insMan.getFinalString();
		insMan = new InstructionManager();
		insMan.addInstruction(everything);
		if (currentScope.getSpace() != 0) {
			insMan.addInstruction("ADD sp, sp, #" + currentScope.getSpace());
		}
		insMan.addInstruction("LDR r0, =0");
		insMan.addInstruction("POP {pc}");
		insMan.addInstruction(".ltorg");
		stack.exitScope(currentScope.getSpace());
		if (preCode.length() != 0) {
			preCode = ".data\n" + preCode;
			System.out.println(preCode);
		}
		System.out.println(insMan.getFinalString());
		postCode = printManager.getAllPrintStatements();
		System.out.println(postCode);
		return insMan.getFinalString();
	}

	@Override
	public String visitFunction(@NotNull BasicParser.FunctionContext ctx) {
		System.out.println("visitFunction");
		currentf = ctx.IDENT().getText();
		System.out.println("test func para space : "
				+ funcParaSpace.get(currentf));
		Scope save = currentScope;
		currentScope = currentScope.nextChild();
		stack.JustIncreaseSp(-funcParaSpace.get(currentf));
		stack.enterScope(currentScope.getSpace() - funcParaSpace.get(currentf));
		insMan.addInstruction(labelMan.newFuncLabel(ctx.IDENT().getText()));
		insMan.addInstruction("PUSH {lr}");
		insMan.addInstruction("SUB sp, sp, #"
				+ (currentScope.getSpace() - funcParaSpace.get(currentf)));
		BasicParser.ParameterListContext p = ctx.parameterList();
		if (p != null) {
			visit(p);
		}
		String stat = visit(ctx.statement());
		stack.exitScope(currentScope.getSpace());
		insMan.addInstruction("ADD sp, sp, #"
				+ (currentScope.getSpace() - funcParaSpace.get(currentf)));
		insMan.addInstruction("POP {pc}");
		insMan.addInstruction(".ltorg\n");
		currentScope = save;
		stack.JustIncreaseSp(funcParaSpace.get(currentf));
		currentf = "";
		return "";
	}

	@Override
	public String visitParameter(@NotNull BasicParser.ParameterContext ctx) {
		System.out.println("visitParameter");
		String t = visit(ctx.type());
		String varName = ctx.IDENT().getText();
		Type type = currentScope.get(varName);
		stack.addVariable(varName, type);
		System.out.println(varName + "---" + type + "------ "
				+ stack.getOffsetLocal(varName));
		System.out.println("sp: " + stack.getSp());
		return "";
	}

	@Override
	public String visitParameterList(
			@NotNull BasicParser.ParameterListContext ctx) {
		System.out.println("visit ParameterList");
		ListIterator<BasicParser.ParameterContext> li = ctx.parameter()
				.listIterator(ctx.parameter().size());
		while (li.hasPrevious()) {
			visit(li.previous());
		}
		return "";
	}

	@Override
	public String visitAssign(@NotNull BasicParser.AssignContext ctx) {
		System.out.println("visitAssign");
		String lhs = visit(ctx.assignlhs());
		String rhs = visit(ctx.assignrhs());
		Instruction ins1;
		String ope = "STR";
		if (currentVarSpace == 1) {
			ope += "B";
		}
		currentVarSpace = 0;
		if (lhs.equals("0")) {
			ins1 = new Instruction(ope, rhs, "[sp]");
		} else {
			ins1 = new Instruction(ope, rhs, "[sp, #" + lhs + "]");
		}
		regMan.setFree(rhs);
		insMan.addInstruction(ins1);
		return "";
	}

	@Override
	public String visitDeclare(@NotNull BasicParser.DeclareContext ctx) {
		System.out.println("visit Declare");
		String name = ctx.IDENT().getText();
		Type type = currentScope.get(name);
		if (type instanceof IntType) {
			stack.addVariable(name, type);
			String rhsReg = visit(ctx.assignrhs());
			int offset = stack.getOffsetLocal(name);
			if (functable.contains(currentf)
					&& functable.lookupCurrLevelOnly(currentf).getParam(name) != null) {
				offset += 4;
			}
			Instruction ins1;
			if (offset == 0) {
				ins1 = new Instruction("STR", rhsReg, "[sp]");
			} else {
				ins1 = new Instruction("STR", rhsReg, "[sp, #" + offset + "]");
			}
			regMan.setFree(rhsReg);
			insMan.addInstruction(ins1);
		} else if (type instanceof BoolType) {
			stack.addVariable(name, type);
			String rhsReg = visit(ctx.assignrhs());
			int offset = stack.getOffsetLocal(name);
			if (functable.contains(currentf)
					&& functable.lookupCurrLevelOnly(currentf).getParam(name) != null) {
				offset += 4;
			}
			Instruction ins1;
			if (offset == 0) {
				ins1 = new Instruction("STRB", rhsReg, "[sp]");
			} else {
				ins1 = new Instruction("STRB", rhsReg, "[sp, #" + offset + "]");
			}
			regMan.setFree(rhsReg);
			insMan.addInstruction(ins1);
		} else if (type instanceof StringType) {
			stack.addVariable(name, type);
			String rhsReg = visit(ctx.assignrhs());
			int offset = stack.getOffsetLocal(name);
			if (functable.contains(currentf)
					&& functable.lookupCurrLevelOnly(currentf).getParam(name) != null) {
				offset += 4;
			}
			Instruction ins1;
			if (offset == 0) {
				ins1 = new Instruction("STR", rhsReg, "[sp]");
			} else {
				ins1 = new Instruction("STR", rhsReg, "[sp, #" + offset + "]");
			}
			regMan.setFree(rhsReg);
			insMan.addInstruction(ins1);
		} else if (type instanceof CharType) {
			stack.addVariable(name, type);
			String rhsReg = visit(ctx.assignrhs());
			int offset = stack.getOffsetLocal(name);
			if (functable.contains(currentf)
					&& functable.lookupCurrLevelOnly(currentf).getParam(name) != null) {
				offset += 4;
			}
			Instruction ins1;
			if (offset == 0) {
				ins1 = new Instruction("STRB", rhsReg, "[sp]");
			} else {
				ins1 = new Instruction("STRB", rhsReg, "[sp, #" + offset + "]");
			}
			regMan.setFree(rhsReg);
			insMan.addInstruction(ins1);
			String ret = "\nLDR r4, =msg_" + strNumber + "\nSTR r4, [sp";
			strNumber++;
			int x3 = numVar - 4 - regMan.getTotalSize();
			if (x3 != 0) {
				ret += (", #" + x3);
			}
			ret += "]\n";
			regMan.add(name, new StringType());
		} else if (type instanceof ArrayType) {
			stack.addVariable(name, type);
			int unitSpace = getSpace(((ArrayType) type).getTypeOfArray());
			currentArray = name;
			currentArrayData = new ArrayData();
			currentArrayData.setUnitSpace(unitSpace);
			currentArrayData.setName(name);
			currentArrayData.setIndex(0);
			currentArrayLength = ((ArrayType) type).getLength();
			System.out.println("unitspace: " + ((ArrayType) type).getTypeOfArray());
			System.out.println("currentArrayLength: " + currentArrayLength);
			insMan.addInstruction("LDR r0, =" + ((currentArrayLength+1)*unitSpace));
			insMan.addInstruction("BL malloc");
			String arrayPosition = regMan.nextAvailable();
			currentArrayData.setPosition(arrayPosition);
			insMan.addInstruction("MOV " + arrayPosition + ", r0");
			visit(ctx.assignrhs());
			String lengthReg = regMan.nextAvailable();
			insMan.addInstruction("LDR " + lengthReg + ", =" + currentArrayLength);
			insMan.addInstruction("STR " + lengthReg + ", [" + arrayPosition + "]");
			int offset = stack.getOffsetLocal(name);
			if (functable.contains(currentf)
					&& functable.lookupCurrLevelOnly(currentf).getParam(name) != null) {
				offset += 4;
			}
			Instruction ins1;
			if (offset == 0) {
				ins1 = new Instruction("STR", arrayPosition, "[sp]");
			} else {
				ins1 = new Instruction("STR", arrayPosition, "[sp, #" + offset + "]");
			}
			regMan.setFree(arrayPosition);
			regMan.setFree(lengthReg);
			insMan.addInstruction(ins1);
			return "";
		} else if (type instanceof PairType) {
			stack.addVariable(name, type);
			String rhsReg = visit(ctx.assignrhs());
			int offset = stack.getOffsetLocal(name);
			if (functable.contains(currentf)
					&& functable.lookupCurrLevelOnly(currentf).getParam(name) != null) {
				offset += 4;
			}
			Instruction ins1;
			if (offset == 0) {
				ins1 = new Instruction("STR", rhsReg, "[sp]");
			} else {
				ins1 = new Instruction("STR", rhsReg, "[sp, #" + offset + "]");
			}
			regMan.setFree(rhsReg);
			insMan.addInstruction(ins1);
			return "";
		} else {
			return "";
		}
		return "";
	}

	@Override
	public String visitAnd(@NotNull BasicParser.AndContext ctx) {
		System.out.println("visitAnd");
		return "AND";
	}

	public int getSpace(Type type) {
		if (type instanceof IntType || type instanceof StringType
				|| type instanceof ArrayType || type instanceof PairType) {
			return 4;
		} else {
			return 1;
		}
	}

	@Override
	public String visitArgumentList(@NotNull BasicParser.ArgumentListContext ctx) {
		System.out.println("visitArgumentList");
		int allspace = 0;
		int space = 0;
		int count = 0;
		ListIterator<BasicParser.ExpressionContext> li = ctx.expression()
				.listIterator(ctx.expression().size());
		while (li.hasPrevious()) {
			String reg = visit(li.previous());
			Type thistype = functable.lookupCurrLevelOnly(currentf)
					.getParaList().get(count);
			space = getSpace(thistype);
			allspace += space;
			String ope = "STR";
			if (space == 1) {
				ope += "B";
			}
			insMan.addInstruction(new Instruction(ope, reg, "[sp, #-" + space
					+ "]!"));
			count++;
			regMan.setFree(reg);
		}
		return String.valueOf(allspace);
	}

	@Override
	public String visitArrayelement(@NotNull BasicParser.ArrayelementContext ctx) {
		System.out.println("visitArrayelement");
		String ident = visit(ctx.IDENT());
		List<BasicParser.ExpressionContext> exps = ctx.expression();
		for (BasicParser.ExpressionContext exp : exps) {
			visit(exp);
		}
		return "";
	}

	@Override
	public String visitArrayLiteral(@NotNull BasicParser.ArrayLiteralContext ctx) {
		System.out.println("visitArrayLiteral");
		List<BasicParser.ExpressionContext> exps = ctx.expression();
		//currentArray;
		//currentArrayData;
	    for (BasicParser.ExpressionContext exp : exps) {
	    	String resultReg = visit(exp);
	    	insMan.addInstruction("STR " + resultReg + ", [" + currentArrayData.getPosition() + ", #" + currentArrayData.getIndex() + "]");
	    	regMan.setFree(resultReg);
	    }
		return "";
	}

	@Override
	public String visitArraytype(@NotNull BasicParser.ArraytypeContext ctx) {
		System.out.println("visitArraytype");
		return "array";
	}

	@Override
	public String visitAssignLhsArrayElement(
			@NotNull BasicParser.AssignLhsArrayElementContext ctx) {
		System.out.println("visitAssignLhsArrayElement");
		visit(ctx.arrayelement());
		return "";
	}

	@Override
	public String visitAssignlhsIdent(
			@NotNull BasicParser.AssignlhsIdentContext ctx) {
		System.out.println("visitAssignlhsIdent");
		String varName = ctx.IDENT().getText();
		currentVarSpace = getSpace(currentScope.get(varName));
		int offset = stack.getOffsetLocal(varName);
		if (functable.contains(currentf)
				&& functable.lookupCurrLevelOnly(currentf).getParam(varName) != null) {
			System.out.println("in func");
			offset += 4;
		}
		return String.valueOf(offset);
	}

	@Override
	public String visitAssignlhsPairElement(
			@NotNull BasicParser.AssignlhsPairElementContext ctx) {
		System.out.println("visitAssignlhsPairElement");
		visit(ctx.pairelement());
		return "";
	}

	@Override
	public String visitAssignRhsArrayLiteral(
			@NotNull BasicParser.AssignRhsArrayLiteralContext ctx) {
		System.out.println("visitAssignRhsArrayLiteral");
		return visit(ctx.arrayLiteral());
	}

	@Override
	public String visitAssignRhsCall(
			@NotNull BasicParser.AssignRhsCallContext ctx) {
		System.out.println("visitAssignRhsCall");
		currentf = ctx.IDENT().getText();
		int count = 0;
		if (ctx.argumentList() != null) {
			String s = visit(ctx.argumentList());
			if (s != "") {
				count = Integer.parseInt(s);
			}
		}
		String funcName = ctx.IDENT().getText();
		insMan.addInstruction("BL f_" + funcName);
		insMan.addInstruction("ADD sp, sp, #" + count);
		String reg = regMan.nextAvailable();
		insMan.addInstruction("MOV " + reg + ", r0");
		currentf = "";
		return reg;
	}

	@Override
	public String visitAssignrhsexpression(
			@NotNull BasicParser.AssignrhsexpressionContext ctx) {
		System.out.println("visitAssignrhsexpression: " + ctx.getText());
		return visit(ctx.expression());
	}

	@Override
	public String visitAssignRhsNewPair(
			@NotNull BasicParser.AssignRhsNewPairContext ctx) {
		System.out.println("visitAssignRhsNewPair");
		return "";
	}

	@Override
	public String visitAssignRhsPairElem(
			@NotNull BasicParser.AssignRhsPairElemContext ctx) {
		System.out.println("visitAssignRhsPairElem");
		return "";
	}

	@Override
	public String visitBasetype(@NotNull BasicParser.BasetypeContext ctx) {
		System.out.println("visit Basetype");
		System.out.println("ctx.getText() = " + ctx.getText());
		return visit(ctx.baseType());
	}

	@Override
	public String visitBaseType(@NotNull BasicParser.BaseTypeContext ctx) {
		System.out.println("visit BaseType");
		// System.out.println("ctx.getText() = " + ctx.getText());
		return ctx.getText();

	}

	@Override
	public String visitBaseTypeArray(
			@NotNull BasicParser.BaseTypeArrayContext ctx) {
		System.out.println("visitBaseTypeArray");
		return "";
	}

	@Override
	public String visitBoolLiteral(@NotNull BasicParser.BoolLiteralContext ctx) {
		System.out.println("visitBoolLiteral");
		return "";
	}

	@Override
	public String visitDiv(@NotNull BasicParser.DivContext ctx) {
		System.out.println("visitDiv");
		return ctx.DIV().getText();
	}

	@Override
	public String visitEqu(@NotNull BasicParser.EquContext ctx) {
		System.out.println("visitEqu");
		return "CMP";
	}

	@Override
	public String visitExit(@NotNull BasicParser.ExitContext ctx) {
		System.out.println("visit Exit");
		String reg = visit(ctx.expression());
		insMan.addInstruction(new Instruction("MOV", "r0", reg));
		insMan.addInstruction("BL exit");
		regMan.setFree(reg);
		return "";

	}

	@Override
	public String visitExpressArrayElement(
			@NotNull BasicParser.ExpressArrayElementContext ctx) {
		System.out.println("visitExpressArrayElement");
		return visit(ctx.arrayelement());
	}

	@Override
	public String visitExpressBeginNeg(
			@NotNull BasicParser.ExpressBeginNegContext ctx) {
		System.out.println("visitExpressBeginNeg");
		return visit(ctx.beginNeg());
	}

	@Override
	public String visitExpressBinary1(
			@NotNull BasicParser.ExpressBinary1Context ctx) {
		System.out.println("visitExpressBinary1");
		String oper = visit(ctx.binaryoper1());
		String reg1 = visit(ctx.expression(0));
		String reg2 = visit(ctx.expression(1));
		if (ctx.binaryoper1().getText().trim().equals("*")) {
			System.out.println("Multiplication");
			insMan.addInstruction(new Instruction(oper, reg1, reg2, reg1, reg2));
			insMan.addInstruction(new Instruction("CMP", reg2, reg1, "ASR #31"));
			insMan.addInstruction(new Instruction("BLNE p_throw_overflow_error"));
		}
		if (ctx.binaryoper1().getText().trim().equals("/")) {
			System.out.println("Division");
			insMan.addInstruction(new Instruction("MOV", "r0", reg1));
			insMan.addInstruction(new Instruction("MOV", "r1", reg2));
			insMan.addInstruction(new Instruction("BL p_check_divide_by_zero"));
			insMan.addInstruction(new Instruction("BL __aeabi_idiv"));
			insMan.addInstruction(new Instruction("MOV", reg1, "r0"));
		}
		if (ctx.binaryoper1().getText().trim().equals("%")) {
			System.out.println("Mod");
			insMan.addInstruction(new Instruction("MOV", "r0", reg1));
			insMan.addInstruction(new Instruction("MOV", "r1", reg2));
			insMan.addInstruction(new Instruction("BL p_check_divide_by_zero"));
			insMan.addInstruction(new Instruction("BL __aeabi_idivmod"));
			insMan.addInstruction(new Instruction("MOV", reg1, "r1"));
		}
		regMan.setFree(reg2);
		return reg1;
	}

	@Override
	public String visitExpressBinary2(
			@NotNull BasicParser.ExpressBinary2Context ctx) {
		System.out.println("visitExpressBinary2");
		String oper = visit(ctx.binaryoper2());
		/*
		 * if (ctx.binaryoper2().getText().trim().equals("+")) {
		 * 
		 * } if (ctx.binaryoper2().getText().trim().equals("-")) {
		 * 
		 * }
		 */
		String reg1 = visit(ctx.expression(0));
		String reg2 = visit(ctx.expression(1));
		Instruction ins = new Instruction(oper, reg1, reg1, reg2);
		insMan.addInstruction(ins);
		insMan.addInstruction(new Instruction("BLVS p_throw_overflow_error"));
		regMan.setFree(reg2);
		return reg1;
	}

	@Override
	public String visitExpressBinary3(
			@NotNull BasicParser.ExpressBinary3Context ctx) {
		System.out.println("visitExpressBinary3");
		//String oper = visit(ctx.binaryoper3());
		String reg1 = visit(ctx.expression(0));
		String reg2 = visit(ctx.expression(1));
		if (ctx.binaryoper3().getText().trim().equals(">")) {
			 insMan.addInstruction(new Instruction("CMP", reg1, reg2));
			 insMan.addInstruction(new Instruction("MOVGT", reg1, "#1"));
			 insMan.addInstruction(new Instruction("MOVLE", reg1, "#0"));
		}
		if (ctx.binaryoper3().getText().trim().equals(">=")) {
			 insMan.addInstruction(new Instruction("CMP", reg1, reg2));
			 insMan.addInstruction(new Instruction("MOVGE", reg1, "#1"));
			 insMan.addInstruction(new Instruction("MOVLT", reg1, "#0"));
		}
		if (ctx.binaryoper3().getText().trim().equals("<")) {
			 insMan.addInstruction(new Instruction("CMP", reg1, reg2));
			 insMan.addInstruction(new Instruction("MOVLT", reg1, "#1"));
			 insMan.addInstruction(new Instruction("MOVGE", reg1, "#0"));
		}
		if (ctx.binaryoper3().getText().trim().equals("<=")) {
			 insMan.addInstruction(new Instruction("CMP", reg1, reg2));
			 insMan.addInstruction(new Instruction("MOVLE", reg1, "#1"));
			 insMan.addInstruction(new Instruction("MOVGT", reg1, "#0"));
		}
		regMan.setFree(reg2);
		return reg1;
	}

	@Override
	public String visitExpressBinary4(
			@NotNull BasicParser.ExpressBinary4Context ctx) {
		System.out.println("visitExpressBinary4");
		//String oper = visit(ctx.binaryoper4());
		String reg1 = visit(ctx.expression(0));
		String reg2 = visit(ctx.expression(1));
		if (ctx.binaryoper4().getText().trim().equals("==")) {
		   insMan.addInstruction(new Instruction("CMP", reg1, reg2));
		   insMan.addInstruction(new Instruction("MOVEQ", reg1, "#1"));
		   insMan.addInstruction(new Instruction("MOVNE", reg1, "#0"));
		}
		if (ctx.binaryoper4().getText().trim().equals("!=")) {
			 insMan.addInstruction(new Instruction("CMP", reg1, reg2));
			 insMan.addInstruction(new Instruction("MOVNE", reg1, "#1"));
			 insMan.addInstruction(new Instruction("MOVEQ", reg1, "#0"));
		}
		
		
		regMan.setFree(reg2);
		return reg1;
	}

	@Override
	public String visitExpressBinary5(
			@NotNull BasicParser.ExpressBinary5Context ctx) {
		System.out.println("visitExpressBinary5");
		String oper = visit(ctx.binaryoper5());
		String reg1 = visit(ctx.expression(0));
		String reg2 = visit(ctx.expression(1));
		Instruction ins = new Instruction(oper, reg1, reg1, reg2);
		insMan.addInstruction(ins);
		regMan.setFree(reg2);
		return reg1;
	}

	@Override
	public String visitExpressBinary6(
			@NotNull BasicParser.ExpressBinary6Context ctx) {
		System.out.println("visitExpressBinary6");
		String oper = visit(ctx.binaryoper6());
		String reg1 = visit(ctx.expression(0));
		String reg2 = visit(ctx.expression(1));
		Instruction ins = new Instruction(oper, reg1, reg1, reg2);
		insMan.addInstruction(ins);
		regMan.setFree(reg2);
		return reg1;
	}

	@Override
	public String visitExpressBoolLiteral(
			@NotNull BasicParser.ExpressBoolLiteralContext ctx) {
		System.out.println("visit express bool literal");
		String s = ctx.boolLiteral().getText();
		String reg = regMan.nextAvailable();
		if (s.equals("true")) {
			s = "#1";
		} else {
			s = "#0";
		}
		Instruction ins = new Instruction("MOV", reg, s);
		insMan.addInstruction(ins);
		return reg;
	}

	@Override
	public String visitExpressCharliteral(
			@NotNull BasicParser.ExpressCharliteralContext ctx) {
		System.out.println("visitExpressCharliteral");
		String reg = regMan.nextAvailable();
		Instruction ins = new Instruction("MOV", reg, "#"
				+ ctx.CHARLITERAL().getText());
		insMan.addInstruction(ins);
		return reg;
	}

	@Override
	public String visitExpressIdent(@NotNull BasicParser.ExpressIdentContext ctx) {
		System.out.println("visitExpressIdent");
		String reg = regMan.nextAvailable();
		int o = 0;
		currentVarSpace = getSpace(currentScope.get(ctx.IDENT().getText()));
		if (functable.contains(currentf)
				&& functable.lookupCurrLevelOnly(currentf).getParam(
						ctx.IDENT().getText()) != null) {
			o += 4;
		}
		String ope = "LDR";
		if (currentScope.get(ctx.IDENT().getText()) instanceof BoolType) {
			ope += "SB";
		}
		int offset = (stack.getOffsetLocal(ctx.IDENT().getText()) + o);
		if (offset == 0) {
			Instruction ins = new Instruction(ope, reg, "[sp]");
			insMan.addInstruction(ins);
		} else {
			Instruction ins = new Instruction(ope, reg, "[sp, #" + offset
					+ "]");
			insMan.addInstruction(ins);
		}
		return reg;
	}

	@Override
	public String visitExpressNotNegUnary(
			@NotNull BasicParser.ExpressNotNegUnaryContext ctx) {
		System.out.println("visitExpressNotNegUnary");
		return ctx.NOTNEGUNARYOPER().getText() + visit(ctx.expression());
	}

	@Override
	public String visitExpressPaitLiteral(
			@NotNull BasicParser.ExpressPaitLiteralContext ctx) {
		System.out.println("visitExpressPaitLiteral");
		return "";
	}

	@Override
	public String visitExpressParentheses(
			@NotNull BasicParser.ExpressParenthesesContext ctx) {
		System.out.println("visitExpressParentheses");
		String temp;
		if (unaryTemp.equals("-")) {
			temp = "-";
			unaryTemp = "";
		}
		return visit(ctx.expression());
	}

	@Override
	public String visitExpressPositiveIntliteral(
			@NotNull BasicParser.ExpressPositiveIntliteralContext ctx) {
		System.out.println("visit express positive int literal");
		String reg = regMan.nextAvailable();
		String temp = "";
		if (unaryTemp.equals("-")) {
			temp = "-";
			unaryTemp = "";
		}
		String number =  ctx.POSITIVEINTLITERAL().getText();
		number = String.valueOf(Integer.parseInt(number));
		Instruction ins = new Instruction("LDR", reg, "="
				+ number);
		insMan.addInstruction(ins);
		return reg;
	}

	@Override
	public String visitExpressStrliteral(
			@NotNull BasicParser.ExpressStrliteralContext ctx) {
		System.out.println("visitExpressStrliteral");
		msgs.add(ctx.getText());
		String reg = regMan.nextAvailable();
		Instruction ins = new Instruction("LDR", reg, "=msg_"
				+ Integer.toString(msgs.size() - 1));
		insMan.addInstruction(ins);
		return reg;
	}

	@Override
	public String visitFree(@NotNull BasicParser.FreeContext ctx) {
		System.out.println("visitFree");
		return "";
	}

	@Override
	public String visitFst(@NotNull BasicParser.FstContext ctx) {
		System.out.println("visitFst");
		return "";
	}

	@Override
	public String visitGe(@NotNull BasicParser.GeContext ctx) {
		System.out.println("visitGe");
		return ctx.GREATEREQU().getText();
	}

	@Override
	public String visitGreater(@NotNull BasicParser.GreaterContext ctx) {
		System.out.println("visitGreater");
		return ctx.GREATER().getText();
	}

	@Override
	public String visitIfthenelse(@NotNull BasicParser.IfthenelseContext ctx) {
		System.out.println("visitIfthenelse");
		String reg = visit(ctx.expression()); // move result to rn
		
		insMan.addInstruction(new Instruction("CMP", reg, "#0"));
		insMan.addInstruction(new Instruction("BEQ", "L0"));
		
		Scope save = currentScope;
		currentScope = currentScope.nextChild();
		stack.enterScope(currentScope.getSpace());
		String truestat = visit(ctx.statement(0));
		stack.exitScope(currentScope.getSpace());
		currentScope = save;
		
		insMan.addInstruction(new Instruction("B L1"));
		insMan.addInstruction(new Instruction("L0:"));
		
		save = currentScope;
		currentScope = currentScope.nextChild();
		stack.enterScope(currentScope.getSpace());
		String falstat = visit(ctx.statement(1));
		stack.exitScope(currentScope.getSpace());
		currentScope = save;

		insMan.addInstruction(new Instruction("L1:"));
		/**
		 * MOV r4, #1 // result to r4 CMP r4, #0 BEQ L0 // false to L0 do true
		 * stat // else true, do sth B L1 // to else L0: do false
		 * 
		 * L1: else
		 ***/
		
		return reg + "\nCMP Rn, #0\nBEQL0\n" + truestat + "\nB L1\nL0:\n"
				+ falstat + "\nL1:\n";
	}

	@Override
	public String visitLe(@NotNull BasicParser.LeContext ctx) {
		System.out.println("visitLe");
		return ctx.LESSEQU().getText();
	}

	@Override
	public String visitLess(@NotNull BasicParser.LessContext ctx) {
		System.out.println("visitLess");
		return ctx.LESS().getText();
	}

	@Override
	public String visitMinus(@NotNull BasicParser.MinusContext ctx) {
		System.out.println("visitMinus");
		return "SUB";
	}

	@Override
	public String visitMinusexpre(@NotNull BasicParser.MinusexpreContext ctx) {
		System.out.println("visitMinusexpre");
		String reg = visit(ctx.expression());
		insMan.addInstruction(new Instruction("RSBS", reg, reg, "#0"));
		return reg;
	}

	@Override
	public String visitMinusjustlit(@NotNull BasicParser.MinusjustlitContext ctx) {
		System.out.println("visitMinusjustlit");
		// seems like this function is never visited
		String s = "-" + ctx.JUSTINTLITERAL().getText();
		String reg = regMan.nextAvailable();
		insMan.addInstruction(new Instruction("mov", reg, "#" + s));
		return reg;
	}

	@Override
	public String visitMod(@NotNull BasicParser.ModContext ctx) {
		System.out.println("visitMod");
		return ctx.MOD().getText();
	}

	@Override
	public String visitMul(@NotNull BasicParser.MulContext ctx) {
		System.out.println("visitMul");
		return ctx.MUL().getText();
	}

	@Override
	public String visitNestedArray(@NotNull BasicParser.NestedArrayContext ctx) {
		System.out.println("visitNestedArray");
		return "";
	}

	@Override
	public String visitNoteq(@NotNull BasicParser.NoteqContext ctx) {
		System.out.println("visitNoteq");
		return ctx.NOTEQUAL().getText();
	}

	@Override
	public String visitOr(@NotNull BasicParser.OrContext ctx) {
		System.out.println("visitOr");
		return "ORR";
	}

	@Override
	public String visitPair(@NotNull BasicParser.PairContext ctx) {
		System.out.println("visitPair");
		return ctx.PAIR().getText();
	}

	@Override
	public String visitPairArrayType(
			@NotNull BasicParser.PairArrayTypeContext ctx) {
		System.out.println("visitPairArrayType");
		return "";
	}

	@Override
	public String visitPairbaseType(@NotNull BasicParser.PairbaseTypeContext ctx) {
		System.out.println("visitPairbaseType");
		return "";
	}

	@Override
	public String visitPairLiteral(@NotNull BasicParser.PairLiteralContext ctx) {
		System.out.println("visitPairLiteral");
		return "";
	}

	@Override
	public String visitPairtype(@NotNull BasicParser.PairtypeContext ctx) {
		System.out.println("visitPairtype");
		return visit(ctx.pairType());
	}

	@Override
	public String visitPairType(@NotNull BasicParser.PairTypeContext ctx) {
		System.out.println("visitPairType");
		visit(ctx.pairElemType(0));
		visit(ctx.pairElemType(1));
		return "pair";
	}

	@Override
	public String visitPairTypeArray(
			@NotNull BasicParser.PairTypeArrayContext ctx) {
		System.out.println("visitPairTypeArray");
		return "";
	}

	@Override
	public String visitPlus(@NotNull BasicParser.PlusContext ctx) {
		System.out.println("visitPlus");
		return "ADDS";
	}

	@Override
	public String visitPrint(@NotNull BasicParser.PrintContext ctx) {
		System.out.println("visitPrint");
		Type curr = print.get(printCount++);
		insMan.addInstruction(new Instruction("MOV", "r0", visit(ctx
				.expression())));
		if (curr instanceof StringType) {
			/*
			 * LDR r4, =msg_0 MOV r0, r4 BL p_print_string
			 */
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "p_print_string"));
			// insMan.addInstruction();
			preCode += printManager.getStringMsgBlock(ctx.expression()
					.getText());
			// System.out.println("ctx = " +
			// printManager.getStringMsgBlock(ctx.expression().getText()));
		} else if (curr instanceof BoolType) {
			/*
			 * MOV r4, #1 MOV r0, r4 BL p_print_bool
			 */
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "p_print_bool"));
			preCode += printManager.getBoolMsgBlock();
		} else if (curr instanceof CharType) {
			System.out.println("curr = " + ctx.expression().getText());
			/*
			 * MOV r4, #'c' MOV r0, r4 BL putchar
			 */
			// visit(ctx.expression())
			// insMan.addInstruction(new Instruction("MOV", "r4", "#" +
			// ctx.expression().getText()));
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "putchar"));

		} else if (curr instanceof IntType) {
			/*
			 * LDR r4, =1 MOV r0, r4 BL p_print_int
			 */
			// insMan.addInstruction(new Instruction("LDR", "r4", "=" +
			// visit(ctx.expression())));
			// visit(ctx.expression());
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "p_print_int"));
			preCode += printManager.getIntMsgBlock();
		}

		/*
		 * msg_1: .word 5 .ascii "%.*s\0" // string
		 * 
		 * msg_0: 3 .word 5 4 .ascii "true\0" 5 msg_1: 6 .word 6 7 .ascii
		 * "false\0"
		 * 
		 * p_print_bool: //bool 24 PUSH {lr} 25 CMP r0, #0 26 LDRNE r0, =msg_0
		 * 27 LDREQ r0, =msg_1 28 ADD r0, r0, #4 29 BL printf 30 MOV r0, #0 31
		 * BL fflush 32 POP {pc} // bool
		 * 
		 * MOV r4, #'c' 6 MOV r0, r4 7 BL putchar // char
		 * 
		 * msg_0: 3 .word 3 4 .ascii "%d\0" // int
		 * 
		 * 
		 * p_print_string: PUSH {lr} LDR r1, [r0] // r0 = address of string, r1
		 * = string ADD r2, r0, #4 // r2 = end address of string LDR r0, =msg_1
		 * // r0 = address of msg1 ADD r0, r0, #4 // r0 = end address of msg1 BL
		 * printf MOV r0, #0 BL fflush POP {pc}
		 */
		// r0:end address of msg1 r1:string r2:end address of string

		return "";
	}

	@Override
	public String visitPrintln(@NotNull BasicParser.PrintlnContext ctx) {

		System.out.println("visitPrintln");
		Type curr = println.get(printLNCount++);
		String reg = visit(ctx
				.expression());
		insMan.addInstruction(new Instruction("MOV", "r0", reg));
		regMan.setFree(reg);
		if (curr instanceof StringType) {
			/*
			 * LDR r4, =msg_0 MOV r0, r4 BL p_print_string
			 */
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "p_print_string"));
			// insMan.addInstruction();
			preCode += printManager.getStringMsgBlock(ctx.expression()
					.getText());
			// System.out.println("ctx = " +
			// printManager.getStringMsgBlock(ctx.expression().getText()));
		} else if (curr instanceof BoolType) {
			/*
			 * MOV r4, #1 MOV r0, r4 BL p_print_bool
			 */
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "p_print_bool"));
			preCode += printManager.getBoolMsgBlock();
		} else if (curr instanceof CharType) {
			System.out.println("curr = " + ctx.expression().getText());
			/*
			 * MOV r4, #'c' MOV r0, r4 BL putchar
			 */
			// visit(ctx.expression())
			// insMan.addInstruction(new Instruction("MOV", "r4", "#" +
			// ctx.expression().getText()));
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "putchar"));

		} else if (curr instanceof IntType) {
			/*
			 * LDR r4, =1 MOV r0, r4 BL p_print_int
			 */
			// insMan.addInstruction(new Instruction("LDR", "r4", "=" +
			// visit(ctx.expression())));
			// visit(ctx.expression());
			// insMan.addInstruction(new Instruction("MOV", "r0",
			// visit(ctx.expression())));
			insMan.addInstruction(new Instruction("BL", "p_print_int"));
			preCode += printManager.getIntMsgBlock();
		}
		insMan.addInstruction(new Instruction("BL", "p_print_ln"));
		/**
		 * msg_1: 6 .word 5 7 .ascii "%.*s\0" 8 msg_2: 9 .word 1 10 .ascii "\0"
		 * 
		 * p_print_string: 25 PUSH {lr} 26 LDR r1, [r0] 27 ADD r2, r0, #4 28 LDR
		 * r0, =msg_1 29 ADD r0, r0, #4 30 BL printf 31 MOV r0, #0 32 BL fflush
		 * 33 POP {pc} 34 p_print_ln: 35 PUSH {lr} 36 LDR r0, =msg_2 37 ADD r0,
		 * r0, #4 38 BL puts 39 MOV r0, #0 40 BL fflush 41 POP {pc}
		 **/

		// first print string than print newline
		/*
		 * p_print_ln: PUSH {lr} LDR r0, =msg_2 ADD r0, r0, #4 BL puts MOV r0,
		 * #0 BL fflush POP {pc}
		 */

		return "";
	}

	@Override
	public String visitRead(@NotNull BasicParser.ReadContext ctx) {
		System.out.println("visitRead");
		String varName = visit(ctx.assignlhs());
		/*
		 * msg_0: .word 4 .ascii " %c\0" // msg for read char
		 * 
		 * msg_1: .word 3 .ascii "%d\0" // msg for read int
		 * 
		 * ADD r4, sp, #4 // get position -> #n n=sp - size of var after that
		 * MOV r0, r4 // set position to r0 BL p_read_int // jump to read ADD
		 * sp, sp, #8 // add back stack pointer
		 * 
		 * PUSH {lr} // read int MOV r1, r0 // get position from r0 LDR r0,
		 * =msg_0 // load string parameter to r0 ADD r0, r0, #4 // BL scanf //
		 * read POP {pc}
		 * 
		 * r0:end address of msg r1:string
		 */
		return "";
	}

	@Override
	public String visitReturn(@NotNull BasicParser.ReturnContext ctx) {
		System.out.println("visitReturn");
		String reg = visit(ctx.expression());
		insMan.addInstruction(new Instruction("MOV", "r0", reg));
		regMan.setFree(reg);
		insMan.addInstruction("POP {pc}");
		return "";
	}

	@Override
	public String visitSemicolon(@NotNull BasicParser.SemicolonContext ctx) {
		System.out.println("visitSemicolon");
		String s0 = visit(ctx.statement(0));
		System.out.println("");
		String s1 = visit(ctx.statement(1));
		return s0 + s1;
	}

	@Override
	public String visitSkip(@NotNull BasicParser.SkipContext ctx) {
		System.out.println("visit skip");
		return "LDR r0, =0";
	}

	@Override
	public String visitSnd(@NotNull BasicParser.SndContext ctx) {
		System.out.println("visitSnd");
		return "";
	}

	@Override
	public String visitStatementparens(
			@NotNull BasicParser.StatementparensContext ctx) {
		System.out.println("visitStatementparens");
		Scope save = currentScope;
		currentScope = currentScope.nextChild();
		int space = currentScope.getSpace();
		String before = "\nSUB sp, sp, #" + space + "\n";
		stack.enterScope(currentScope.getSpace());
		String s = visit(ctx.statement());
		stack.exitScope(currentScope.getSpace());
		String after = "\nADD sp, sp, #" + space + "\n";
		currentScope = save;
		return before + s + after;
	}

	@Override
	public String visitWhiledo(@NotNull BasicParser.WhiledoContext ctx) {
		System.out.println("visitWhiledo");
		String exp = visit(ctx.expression()); // put result in rn
		String stats = visit(ctx.statement()); // loop contents
		/*
		 * B L0 L1: L0: MOV r4, #0 CMP r4, #1 BEQ L1
		 */
		return "B L0\nL1:\nL0:\n" + exp + "\nCMP rn, #1\nBEQ L1\n";
	}

}
