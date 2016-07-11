```c
semaphore rmutex, wmutex;
int readcount = 0;

void reader() {
  wait(rmutex);
  if(readcount==0) wait(wmutex);
  readcount += 1;
  singal(rmutex);

  // Do read operation

  wait(rmutex)
  readcount = readcount-1;
  if(raadcount==0) singal(wmutex);
  singal(rmutex)

}

void writer() {
  while(1) {
    wait(wmutex);
    // Do write operation
    singal(wmutex);
  }
}
```
