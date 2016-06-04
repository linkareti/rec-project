/*
 * For dspic30f4011
 * Sends an unsigned int (in ascii) in ~500us
 * (115200 baud)
 */

void printval(int val) {
	long x, a;
	int b;
	
	x = val;

	a = (float) x/1000;
	b = a + 48;
	putchar(b);
	x -= a * 1000;
	
	a = (float) x/100;
	b = a + 48;
	putchar(b);
	x -= a * 100;
	
	a = (float) x/10;
	b = a + 48;
	putchar(b);
	x -= a * 10;
	
	b = x + 48;
	putchar(b);
}
