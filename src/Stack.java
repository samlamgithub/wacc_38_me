import java.util.LinkedList;

public class Stack {

	private int sp, currentScopeGroup;
	private LinkedList<VariableData> stack;
	private LinkedList<Integer> scopeSpace;
	private int currentSpaceTakeUp;

	public Stack() {
		sp = 0;
		stack = new LinkedList<>();
		scopeSpace = new LinkedList<Integer>();
		currentScopeGroup = -1;
	}

	public int  getSp() {
		return sp;
	}
	
	public void JustIncreaseSp(int x) {
		sp+=x;
		System.out.println("sp :::::::::::::::::::::::::sp increase:  " + x);
	} 
	
	public void enterScope(int space) {
		System.out.println("enter stack with:            " +space);
		currentScopeGroup++; // new scope group
		sp -= space; // new space
		scopeSpace.add(space); // add scope sapces
		currentSpaceTakeUp = 0;
	}

	public void exitScope(int space) {
		currentScopeGroup--; // delete scope group
		sp += space; // delete space
		int count = 0;
		for (VariableData v : stack) {
			if (v.getScopeGroup() == currentScopeGroup + 1) {
				currentSpaceTakeUp -= getSpace(v.getType());
				count++;
			}
		}
		for (int x = 0; x < count; x++) {
			stack.removeLast();
		}
		scopeSpace.removeLast(); // delete scope spaces
	}

	public void addVariable(String name, Type type) {
		currentSpaceTakeUp += getSpace(type);
		stack.add(new VariableData(name, -currentSpaceTakeUp, type,
				currentScopeGroup));
	}

	public int getSpace(Type type) {
		if (type instanceof IntType || type instanceof StringType
				|| type instanceof ArrayType || type instanceof PairType) {
			return 4;
		} else {
			return 1;
		}
	}

	public LinkedList<VariableData> getScopeGroup(int g) {
		if (g > currentScopeGroup || g < 0) {
			return null;
		} else {
			LinkedList<VariableData> list = new LinkedList<VariableData>();
			for (VariableData v : stack) {
				if (v.getScopeGroup() == g) {
					list.add(v);
				}
			}
			return list;
		}
	}

	public boolean lookUpLocal(String name) {
		return lookUpInGroup(name, currentScopeGroup);
	}

	public boolean lookUpInGroup(String name, int group) {
		for (VariableData v : stack) {
			if (v.getName().equals(name) && v.getScopeGroup() == group) {
				return true;
			}
		}
		return false;
	}

	public boolean lookUpAll(String name) {
		for (int i = currentScopeGroup; i >= 0; i--) {
			if (lookUpInGroup(name, i)) {
				return true;
			}
		}
		return false;
	}

	public int getOffsetGlobal(String name) {
		for (int i = currentScopeGroup; i >= 0; i--) {
			if (lookUpInGroup(name, i)) {
				return getOffsetInGroup(name, i);
			}
		}
		return (Integer) null;
	}
	
	public VariableData get(String name) {
		for (VariableData v : stack) {
			if (v.getName().equals(name)
					&& v.getScopeGroup() == currentScopeGroup) {
				return v;
			}
		}
		return null;
	}

	public int getOffsetLocal(String name) {
		for (VariableData v : stack) {
			if (v.getName().equals(name)
					&& v.getScopeGroup() == currentScopeGroup) {
				return (v.getPosition() - sp);
			}
		}
		return (Integer) null;
	}

	public int getOffsetInGroup(String name, int g) {
		for (VariableData v : stack) {
			if (v.getName().equals(name) && v.getScopeGroup() == g) {
				return (v.getPosition() - sp);
			}
		}
		return (Integer) null;
	}

	public void printStack() {
		System.out.println("print stack: "+currentScopeGroup + " - " + sp);
		for (VariableData v : stack) {
			System.out.println(v.getName() + "::" + v.getScopeGroup() + "::" + v.getPosition() + "::" + sp);
		}
	}

}