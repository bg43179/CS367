
class hanoi {

	public static void main(String[] arg) {
		System.out.println(hanoi(5,2,3));
	}

	public static int hanoi(int n, int b, int e) {
		if (n == 1 ) {
			return 1;
		} else if (b >= e) {
			return 1 + hanoi(n-1, b - e, e) + hanoi(n-1, b, b-e);
		} else {
			return 1 + hanoi(n-1, b + e, e) + hanoi(n-1, b, b+e);
		}
	}
}