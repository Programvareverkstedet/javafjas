import java.io.*;

public class CircleComputationProgram implements Runnable {
	private RadiusReader r;

	static class Size {
		Number n;
		public Size(Number n) {
			this.n = n;
		}
		public Number getNumber() {
			return n;
		}
		public Size multiplyBy(Size s) {
			return new Size(s.getNumber().doubleValue()*this.getNumber().doubleValue());
		}
		public String toString() {
			return ""+n;
		}
	}
	static class Radius extends Size {
		public Radius(Number n) throws DivideByCucumberError {
			super(n);
			if (n.doubleValue() == 8)
				throw new DivideByCucumberError("\n\n+++ Please Reinstall Universe And Reboot +++\n");
		}
	}
	static abstract class GeometryComputer {
		private Shape s;
		private Formula f;
		protected GeometryComputer(Shape s, Formula f) {
			this.f = f;
			this.s = s;
		}
		protected Size getSize() {
			return f.compute(s);
		}
	}

	static class Area extends Size {
		public Area(Number n) {
			super(n);
		}
		public void print() throws AreaWantsToBePrintedException {
			String s = this.toString();
			throw new AreaWantsToBePrintedException(s);
		}
	}

	static class AreaWantsToBePrintedException extends PrintingException {
		public AreaWantsToBePrintedException(final String msg) {
			super(new Runnable() { public void run() { System.out.println("Area: " + msg);}});
		}
	}

	static interface AreaComputer {
		public Area getArea();
	}

	static class AbstractAreaComputer extends GeometryComputer implements AreaComputer {
		public AbstractAreaComputer(Shape s, Formula f) {
			super(s, f);
		}
		public Area getArea() {
			return new Area(getSize().getNumber());
		}
	}

	static abstract class AreaComputerFactory {
		public abstract AreaComputer makeAreaComputer();
		public static AreaComputerFactory makeFactory(final Shape s, final Formula f) {
			return new AreaComputerFactory() {
				public AreaComputer makeAreaComputer() {
					return new AbstractAreaComputer(s, f);
				}
			};
		}
	}


	static class Perimeter extends Size {
		public Perimeter(Number n) {
			super(n);
		}
		public void print() throws PerimeterWantsToBePrintedException {
			String s = this.toString();
			throw new PerimeterWantsToBePrintedException(s);
		}
	}

	static class PerimeterWantsToBePrintedException extends PrintingException {
		public PerimeterWantsToBePrintedException(final String msg) {
			super(new Runnable() { public void run() { System.out.println("Perimeter: " + msg);}});
		}
	}

	static interface PerimeterComputer {
		public Perimeter getPerimeter();
	}

	static class AbstractPerimeterComputer extends GeometryComputer implements PerimeterComputer {
		public AbstractPerimeterComputer(Shape s, Formula f) {
			super(s, f);
		}
		public Perimeter getPerimeter() {
			return new Perimeter(getSize().getNumber());
		}
	}

	static abstract class PerimeterComputerFactory {
		public abstract PerimeterComputer makePerimeterComputer();
		public static PerimeterComputerFactory makeFactory(final Shape s, final Formula f) {
			return new PerimeterComputerFactory() {
				public PerimeterComputer makePerimeterComputer() {
					return new AbstractPerimeterComputer(s, f);
				}
			};
		}
	}

	static class PrintingException extends Throwable {
		Runnable r;
		public PrintingException(Runnable r) {
			this.r = r;
		}
		public void doPrint() {
			r.run();
		}
	}

	static interface Formula {
		public Size compute(Object obj);
	}

	static interface Shape {
		public AreaComputerFactory getAreaComputerFactory();
		public PerimeterComputerFactory getPerimeterComputerFactory();
	}

	static abstract class AbstractShape implements Shape {
		public AreaComputerFactory getAreaComputerFactory() {
			return AreaComputerFactory.makeFactory(this, getAreaFormula());
		}

		public PerimeterComputerFactory getPerimeterComputerFactory() {
			return PerimeterComputerFactory.makeFactory(this, getPerimeterFormula());
		}

		protected abstract Formula getAreaFormula();
		protected abstract Formula getPerimeterFormula();
	}

	static class Circle extends AbstractShape {
		private Radius r;

		public Circle(Radius r) {
			this.r = r;
		}

		public Radius getRadius() {
			return r;
		}

		protected Formula getAreaFormula() {
			return new Formula() {
				public Size compute(Object obj) {
					Radius r = ((Circle)obj).getRadius();
					return new Size(new Double(Math.PI)).multiplyBy(r).multiplyBy(r);
				}
			};
		}

		protected Formula getPerimeterFormula() {
			return new Formula() {
				public Size compute(Object obj) {
					Radius r = ((Circle)obj).getRadius();
					return new Size(new Double(Math.PI)).multiplyBy(new Size(new Double(2))).multiplyBy(r);
				}
			};
		}
	}

	static class RadiusReader {
		private BufferedReader r;
		public RadiusReader(BufferedReader r) {
			this.r = r;
		}
		public Radius readRadius() throws Exception {
			double d = Double.parseDouble(r.readLine());
			if (d < 0) {
				throw new NegativeRadiusException(d);
			} else if (d > 0) {
				if (d == 42) {
					throw new RadiusIsFortyTwoException(d);
				}
				throw new PositiveRadiusException(d);
			} else {
				throw new ZeroRadiusException(d);
			}
		}
	}

	static abstract class RadiusException extends Exception {
		private Radius r;
		public RadiusException(double d) throws Exception {
			r = new Radius(new Double(d));
		}
		public Radius getRadius() {
			return r;
		}
		public void handleException(Runnable r) {
			printAreaAndPerimeter(getRadius());
			r.run();
		}
	}

	static class NegativeRadiusException extends RadiusException {
		public NegativeRadiusException(double d) throws Exception {
			super(d);
		}
		public void handleException(Runnable r) {}
	}
	static class PositiveRadiusException extends RadiusException {
		public PositiveRadiusException(double d) throws Exception {
			super(d);
		}
	}
	static class ZeroRadiusException extends RadiusException {
		public ZeroRadiusException(double d) throws Exception {
			super(d);
		}
	}
	static class RadiusIsFortyTwoException extends RadiusException {
		public RadiusIsFortyTwoException(double d) throws Exception {
			super(d);
		}
		public void handleException(Runnable r) {
			System.out.println("This program has no Hitchhiker's Guide-themed easter eggs");
			super.handleException(r);
		}
	}

	static class DivideByCucumberError extends Exception {
		public DivideByCucumberError(String msg) {
			super(msg);
		}
	}

	public CircleComputationProgram(RadiusReader r) {
		this.r = r;
	}

	public void run() {
		try {
			printAreaAndPerimeter(r.readRadius());
		} catch (NegativeRadiusException e) {
			e.handleException(this);
		} catch (PositiveRadiusException e) {
			e.handleException(this);
		} catch (ZeroRadiusException e) {
			e.handleException(this);
		} catch (RadiusIsFortyTwoException e) {
			e.handleException(this);
		} catch (IOException e) {
			System.err.println("OH NO!");
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAreaAndPerimeter(Radius r) {
		Circle c = new Circle(r);
		printAreaAndPerimeter(c);
	}

	public static void printAreaAndPerimeter(Circle c) {
		printArea(c);
		printPerimeter(c);
	}

	public static void printArea(Circle c) {
		try {
			c.getAreaComputerFactory().makeAreaComputer().getArea().print();
		} catch (AreaWantsToBePrintedException e) {
			e.doPrint();
		}
	}
	
	public static void printPerimeter(Circle c) {
		try {
			c.getPerimeterComputerFactory().makePerimeterComputer().getPerimeter().print();
		} catch (PerimeterWantsToBePrintedException e) {
			e.doPrint();
		}
	}

	public static void main(String[] args) throws Exception {
		RadiusReader r = new RadiusReader(new BufferedReader(new InputStreamReader(System.in)));
		new CircleComputationProgram(r).run();
	}
}
