import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Search {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {

		Scanner in = new Scanner(System.in);
		System.out.print("Enter base directory: ");
		String dir = in.nextLine();
		System.out.print("Enter keyword : ");
		String key = in.nextLine();

		ExecutorService pool = Executors.newCachedThreadPool();

		MatchCounter counter = new MatchCounter(new File(dir), key, pool);
		Future<Integer> res = pool.submit(counter);

		try {
			System.out.println(res.get() + " mathcing files");
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		pool.shutdown();

		int largestPool = ((ThreadPoolExecutor) pool).getLargestPoolSize();
		System.out.println("largest pool size: " + largestPool);

	}

}
