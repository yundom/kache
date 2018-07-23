![Kache Logo](https://github.com/yundom/kache/blob/master/images/logo.png)

# Kache
[![CircleCI branch](https://circleci.com/gh/yundom/kache.svg?style=shield&circle-token=656b534e746e391d3ab9bfbac01cb6b60a5ab087)](https://circleci.com/gh/yundom/kache/tree/master)
[![GitHub license](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A library to provide runtime in-memory cache, with options of LRU and FIFO.
## Installation
Include the library in your `build.gradle`
```
compile 'com.yundom:kache:1.0.1'
```

## Usage
### Create a cache instance
Kache provides a builder for you to configure and create cache instance.

Create a cache instance:
```kotlin
val cache = Builder.build<Int, String>()
```
Then you will get:
1. A cache instance with key of type `Int`, and value of type `String`.
2. Default capacity is `128`.
3. Default cache policy is `LRU`.

Create a FIFO cache instance with capacity of 32:
```kotlin
val cache = Builder.build<Int, String>({
    policy = FIFO
    capacity = 32
})
```
__Supported parameters__

NAME | TYPE | VALUE
-----|------|----
policy | Policy | **FIFO** for first in first out cache, or **LRU** for least recently used cache.
capacity | Int | The maximum size of the cache.

### Cache operations
Put an entry into the cache:
```kotlin
cache.put(1, "Hello")  // [1 to "Hello"]
cache.put(2, "World") // [1 to "Hello", 2 to "World"]
```

Get an entry from the cache:
```kotlin
cache.get(1)  // return the value "Hello"
```

Return null if the key does not exist.
```kotlin
cache.get(4) // return null
```

Remove an entry from the cache:
```kotlin
cache.remove(1) // remove the entry then return the removed value "Hello"
```

Clear the cache:
```kotlin
cache.clear()
```
## License
```text
The MIT License (MIT)

Copyright (c) 2016 Dennis Hsieh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
