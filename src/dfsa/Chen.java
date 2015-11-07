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
	public int estimate(int lastFrameSize, int successSlots, int colisionSlots, int e) {
		// TODO Auto-generated method stub
		if(lastFrameSize == 0) {
			return 64;
		}
//		int e = lastFrameSize-successSlots-colisionSlots;
		lastFrameSize = e + successSlots + colisionSlots;
		int n = successSlots + 2*colisionSlots;
		double next = 0;
		double previous = -1;
		
		double pe, ps, pc;
		
		while(previous < next) {
			pe = Math.pow(1-(1/lastFrameSize), n);
			ps =(n/lastFrameSize)*Math.pow(1-(1/lastFrameSize), n-1);
			pc = 1 - pe - ps;
			
			previous = next;
			
			next = fatorial(lastFrameSize, e, successSlots, colisionSlots)*Math.pow(pe, e)*Math.pow(ps, successSlots)*Math.pow(pc, colisionSlots);
			n++;
		}
		
		return n-2;
		
	}

	@Override
	public int estimateTags(int lasFrameSize, int successSlots, int colisionSlots) {
		// TODO Auto-generated method stub
		return 0;
	}

}
