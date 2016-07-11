package main

import (
	"fmt"
	"time"
)

func producer(id int, c chan int) {
	for i := 0; i < 10; i++ {
		c <- i
		fmt.Printf("Producer %d produces data:%d\n", id, i)
		time.Sleep(10 * 1e6)
	}
}

func consumer(id int, c chan int) {
	for i := 0; i < 20; i++ {
		item := <-c
		fmt.Printf("Consumer %d get date:%d\n", id, item)
		time.Sleep(10 * 1e6)
	}
}

func main() {
	item := make(chan int, 5)
	go producer(1, item)
	go producer(2, item)
	go consumer(1, item)

	time.Sleep(1 * 1e9)

	fmt.Println("done!")
}
