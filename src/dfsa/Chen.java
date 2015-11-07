package dfsa;

public class Chen implements Estimator {

	public static double fatorial(double a, double b, double c, double d) {
		double resultado = 1;
		
		while(a > 1) {
			resultado = resultado * a;
			a--;
			
			if(b>1) {
				resultado = resultado / b;
				b--;
			}
			
			if(c > 1) {
				resultado = resultado / c;
				c--;
			}
			
			if(d > 1) {
				resultado = resultado / d;
				d--;
			}
		}
		
		return resultado;
	}
	
	public static double pow(double a, double b) {
		double resultado = a;
		
		for(int i = 0; i < b; i++) {
			resultado *= a;
		}
		
		return resultado;
	}
	
	@Override
	public int estimate(int success, int empty, int collision) {
		// TODO Auto-generated method stub
		double frameSize = success + empty + collision;
		if(frameSize == 0) {
			return 64;
		}
		double n = success + 2.0*collision;
		double next = 0.0;
		double previous = -1.0;
		
		double pe, ps, pc;
		
		while(previous < next) {
			pe = Math.pow(1.0-(1.0/frameSize), n);
			ps =(n/frameSize)*Math.pow(1.0-(1.0/frameSize), n-1);
			pc = 1.0 - pe - ps;
			
			previous = next;
			
			next = fatorial(frameSize, empty, success, collision)*Math.pow(pe, empty)*Math.pow(ps, success)*Math.pow(pc, collision);
			n++;
		}
		
		return (int) Math.ceil(n-2);		
	}
	
	public static void main(String[] args) {
		Chen c = new Chen();
		
//		System.out.println(fatorial(10, 5, 3, 2)*0.478*0.372*0.15);
		System.out.println(c.estimate(3, 5, 2));
	}
}
