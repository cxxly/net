public class ProducerConsumer {
	public static void main(String[] args) {
		SyncBuffer buffer = new SyncBuffer(5);
		Producer p1 = new Producer(buffer, 1);
		Producer p2 = new Producer(buffer, 2);
		Consumer c = new Consumer(buffer, 1);
		
		p1.start();
		p2.start();
		c.start();
	}
}

class SyncBuffer {
	private int BUFFERSIZE = 0;
	private int[] buffer;
	private int len = 0;

	public SyncBuffer(int size) {
		this.BUFFERSIZE = size;
		this.buffer = new int[this.BUFFERSIZE];
	}

	public synchronized void produce(int item) {
		while (len >= BUFFERSIZE-1) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		this.buffer[this.len++] = item;
		notifyAll();
	}

	public synchronized int consume() {
		while (len < 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.println(len);
		int res = this.buffer[this.len--];
		notifyAll();
		return res;
	}

}

class Producer extends Thread {
	private SyncBuffer bf;
	private int id;

	public Producer(SyncBuffer bf, int id) {
		this.bf = bf;
		this.id = id;
	}

	public synchronized void run() {
		for (int i = 0; i < 10; i++) {
			bf.produce(i);
			System.out.println("Producer " + id + " produces date : " + i);
			try {
				sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}
}

class Consumer extends Thread {
	private SyncBuffer bf;
	private int id;

	public Consumer(SyncBuffer bf, int id) {
		this.bf = bf;
		this.id = id;
	}

	public synchronized void run() {
		for(int i=0; i<20; i++) {
			int item = bf.consume();
			System.out.println("Consumer "+id+" get data: " + item);
			try {
				sleep(10);
			}catch(InterruptedException e) {
							}
		}
	}
}