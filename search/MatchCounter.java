import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MatchCounter implements Callable<Integer> {
	private File dir;
	private String key;
	private ExecutorService pool;
	private int count;

	public MatchCounter(File dir, String key, ExecutorService pool) {
		this.dir = dir;
		this.key = key;
		this.pool = pool;
	}

	public Integer call() {
		count = 0;
		try {
			File[] files = dir.listFiles();
			List<Future<Integer>> results = new ArrayList<Future<Integer>>();

			for (File file : files) {
				if (file.isDirectory()) {
					MatchCounter counter = new MatchCounter(file, key, pool);
					Future<Integer> result = pool.submit(counter);
					results.add(result);
				} else {
					if (search(file))
						count++;
				}
			}

			for (Future<Integer> result : results) {
				try {
					count += result.get();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
		}
		return count;
	}

	public boolean search(File file) {
		try {
			try (Scanner in = new Scanner(file)) {
				boolean found = false;
				while (!found && in.hasNextLine()) {
					String line = in.nextLine();
					if (line.contains(key))
						found = true;
				}
				return found;
			}
		} catch (IOException e) {
			return false;
		}
	}
}
