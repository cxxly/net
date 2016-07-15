package main

type Node struct {
	key, value int
	pre, next  *Node
}

type LRUCache struct {
	CAPACITY int
	cache    map[int]int
	head     *Node
	tail     *Node
}

func (c *LRUCache) get(key int) int {
	value, ok := c.cache[key]
	if !ok {
		return -1
	}
	return value
}

func (c *LRUCache) put(key, value int) {

}
