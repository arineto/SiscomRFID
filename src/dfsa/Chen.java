package dfsa;

public class Chen implements Estimator {

	public int fatorial(int a, int b, int c, int d) {
		int resultado = 1;
		
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
	
	@Override
	public int estimate(int success, int empty, int collision) {
		// TODO Auto-generated method stub
		int frameSize = success + empty + collision;
		if(frameSize == 0) {
			return 64;
		}
		int n = success + 2*collision;
		double next = 0;
		double previous = -1;
		
		double pe, ps, pc;
		
		while(previous < next) {
			pe = Math.pow(1-(1/frameSize), n);
			ps =(n/frameSize)*Math.pow(1-(1/frameSize), n-1);
			pc = 1 - pe - ps;
			
			previous = next;
			
			next = fatorial(frameSize, empty, success, collision)*Math.pow(pe, empty)*Math.pow(ps, success)*Math.pow(pc, collision);
			n++;
		}
		
		return n-2;
		
	}
}
