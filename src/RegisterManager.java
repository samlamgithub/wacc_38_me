import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RegisterManager {

	private Map<String, Type> dict; // Maps names to Types
	private boolean[] regs;

	public RegisterManager() {
		dict = new LinkedHashMap<String, Type>();
		regs = new boolean[16];
		regs[0] = regs[1] = regs[2] = true;
	}

	public void add(String name, Type type) {
		dict.put(name, type);
	}

	public void remove() {
		dict.clear();
	}

	public Type lookUp(String name) {
		return dict.get(name);
	}

	public int getTypeSize(Type type) {
		if (type instanceof IntType || type instanceof StringType) {
			return 4;
		} else if (type instanceof BoolType || type instanceof CharType) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean containsVar(String name) {
		return dict.containsKey(name);
	}

	public int getSize() {
		return dict.size();
	}

	public int getTotalSize() {
		int sum = 0;
		for (String s : dict.keySet()) {
			sum += getTypeSize(dict.get(s));
		}
		return sum;
	}

	public String nextAvailable() {
		for (int i = 4; i < 16; i++) {
			if (!regs[i]) {
				setUsed(i);
				System.out.println("use reg: r" + i);
				return "r" + i;
			}
		}
		return "no register available";
	}

	public void setUsed(int reg) {
		if (reg > 3 && reg < 16) {
			regs[reg] = true;
		}
	}

	public void setFree(int reg) {
		if (reg > 3 && reg < 16) {
			regs[reg] = false;
		}
	}

	public void setFree(String r) {
		System.out.println("setfree: " + r);
		int reg = Integer.parseInt(r.split("r")[1]);
		if (reg > 3 && reg < 16) {
			regs[reg] = false;
		}
	}

}
