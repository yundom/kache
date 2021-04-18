```
[NEW] Released to Maven Central: 'com.github.yundom:kache:1.x.x'
```
![Kache Logo](https://github.com/yundom/kache/blob/master/images/logo.png)

# Kache
[ ![Download](https://api.bintray.com/packages/yundom/Kache/Kache/images/download.svg) ](https://bintray.com/yundom/Kache/Kache/_latestVersion)
[![CircleCI branch](https://circleci.com/gh/yundom/kache.svg?style=shield&circle-token=656b534e746e391d3ab9bfbac01cb6b60a5ab087)](https://circleci.com/gh/yundom/kache/tree/master)
[![GitHub license](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

A runtime in-memory cache.
## Installation
Put this in your `build.gradle`
```
implementation 'com.github.yundom:kache:x.x.x'
```

Make sure `jcenter` is in your top-level `build.gradle` file.
```
allprojects {
    repositories {
        jcenter()
    }
}
```
## Usage
### Create a cache instance
Kache provides a simple DSL to create cache instance.

Create a cache instance with default policy `LRU` and capacity `128`:
```kotlin
val cache: Kache<Int, String> = Builder.build()
```

Create a FIFO cache instance with capacity of `32`:
```kotlin
val fifoCache: Kache<Int, String> = Builder.build {
    policy = FIFO
    capacity = 32
}
```

Create a LRU cache with capacity `1024`
```kotlin
val lruCache: Kache<Int, String> = Builder.build {
    policy = LRU
    capacity = 1024
}
```

__Supported parameters__

NAME | TYPE | VALUE
-----|------|----
policy | Policy | `FIFO` for first in first out cache, or `LRU` for least recently used cache.
capacity | Int | The maximum size of the cache.

### Cache operations
Put an entry in the cache:
```kotlin
cache.put(1, "Hello")  // [1 to "Hello"]
cache.put(2, "World") // [1 to "Hello", 2 to "World"]
```

Get an entry from the cache:
```kotlin
cache.get(1)  // return the value "Hello"
```

Check the key :
```kotlin
cache.put(2, "World")
cache.exists(2) // return true
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
