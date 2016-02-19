
public class ArrayStatDemo {

	private static final String HELP = 
			"Help:\n"
			+ "-h -help: Display help.\n"
			+ "-l <int>, -length <int>: Custom array length, default 300.\n";
	public static void main(String[] args) {
		int arrayLength = 300;
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-h" :
			case "-help" :
				System.out.println(HELP);
				return;
			case "-l" :
			case "-length" : 
				if (i + 1 < args.length) {
					arrayLength = Integer.parseInt(args[i+1]);
				}
				break;
			default:
				break;
			}
		}
		ArrayStat statDemo = new ArrayStat(arrayLength);
		System.out.println(statDemo);
		statDemo.getStatistics();
	}

}
