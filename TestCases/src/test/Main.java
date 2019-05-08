package test;

import com.rits.cloning.Cloner;

public class Main {
	public static void main(String[] args) {
		C c = new C();
		c.setBib("sib");
		B b = new B();
		b.setC(c);
		A a = new A();
		a.setB(b);
		
		Cloner cloner = new Cloner();
		A cloned = cloner.deepClone(a);
		cloned.getB().getC().setBib("did");
		System.out.println(cloned.getB().getC().getBib());
		System.out.println(c.getBib());
	}
}
