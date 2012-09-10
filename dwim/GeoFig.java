import java.io.*;

public class GeoFig {

	static double[] stack = new double[100];
	static int sp = 0;
	static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

	public static void
	PLemp_inn_radius_Pni()
		throws IOException {
		dwim();
	}
	public static void
	dPRadien_er_Ppn()
		throws IOException {
		dwim();
	}
	public static void
	dZ2Z100Z314DMMPOmkretsen_er_Pdpn()
		throws IOException {
		dwim();
	}
	public static void
	Z2sDMPArealet_er_Ppn()
		throws IOException {
		dwim();
	}

	public static double dwim() throws IOException {
		return dwim(2, new double[]{});
	}

	public static double dwim(int l, double[] init) throws IOException {
		Throwable t = new Throwable();
		String cmd = t.getStackTrace()[l].getMethodName();
		for (int i = 0; i < init.length; i++)
			stack[i] = init[i];
		double a = 0;
		double b = 0;
		int mode = 0;
		for (int i = 0; i < cmd.length(); i++) {
			char c = cmd.charAt(i);
			if (c == 'P') {
				mode = 1 - mode;
			} else if (mode == 0) { // instruction mode
				switch (c) {
					// arithmetic
				case 'A': push(pop()+pop()); break;
				case 'S': push(pop()-pop()); break;
				case 'M': push(pop()*pop()); break;
				case 'D': push(pop()/pop()); break;
				case 'Z': // zero
					push(0);             break;
				case 's': // swap
					a = pop(); b = pop();
					push(a);   push(b);  break;
				case 'd': // duplicate
					push(top());         break;
				case 'p': // print
					System.out.print(pop());
					break;
				case 'n': // newline
					System.out.println();break;
				case 'i': // input
					push(Double.parseDouble(r.readLine()));
					break;
				default: // digit
					push(pop()*10 + (c-'0'));
				}
			} else { // literal print mode
				if (c == '_') c = ' ';
				System.out.print(c);
			}
			
		}
		if (sp >= 0)
			return top();
		return 0;
	}

	public static double pop() {
		return stack[sp--];
	}

	public static double top() {
		return stack[sp];
	}

	public static void push(double v) {
		stack[++sp] = v;
	}

}
