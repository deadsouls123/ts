[![Maven Central](https://img.shields.io/maven-central/v/site.kason.cszz/cszz-compiler.svg)]() 
[![Build Status](https://travis-ci.org/kasonyang/cszz.svg)](https://travis-ci.org/kasonyang/cszz)
[![Codecov](https://img.shields.io/codecov/c/github/kasonyang/cszz.svg)](https://codecov.io/gh/kasonyang/cszz)

# What is cszz?

Cszz is a static type-safe and null-safe language base on jvm with flexible syntax.

# Features

* static type-safe
* null safe
* auto type
* flexible syntax
* compatible with other jvm-based language like java,groovy and so on.

# How to build and install

requirements: 

* jdk8
* gradle

clone the source,and run

    >gradlew installDist

add the path`build/install/cszz/bin` to your `PATH` environment variable

# Usage

## run as an editor

```
> cszz -gui
```

![Cszz Editor](https://github.com/kasonyang/cszz/blob/master/snapshot/cszz-editor.png?raw=true)

## run as a compiler

```
> cszz SOURCE_FILE 
```

For example:`cszz Helloworld.kl` would compile `Helloworld.kl` in current directory.

More usages please run

```
> cszz -h
```

# HelloWorld Example

    var s = "Hello,world!";
    println(s);


More examples could be found in [examples](https://github.com/kasonyang/cszz/tree/master/examples) directory.

#IDE plugin support

* [cszz-nb-support](https://github.com/kasonyang/cszz-nb-support) 

#License

MIT

# Base Syntax

## Package and class name

You don't have to declare package name and class name in the code.Cszz use the directory name as package name and the file name as class name.For example,the file `/src/foo/bar/Helloworld.kl` means that the package name is `foo.bar` , the main class name is `Helloworld` and the full class name is `foo.bar.Helloworld`,if the source root is `/src`.

## Import classes

```
import foo.bar.Helloworld;//normal import
import foo.bar.HelloCszz as KL;//use alias
import foo.bar.*;
```

## Declare class
define a normal class:
```
//define a class,default modifier is public
class{
    //declare fields and methods
}
```

define a generic class:

```
class<T>{
    //...
}
```

extend and implement:

```
class extends Object implements List<String>{
    //...
}
```

write as a script:

```
//declare methods or code statements
System.out.print("Hello,world");
```

## Declare method

```
int sum(int a,int b){
    return a+b;
}
```

## Declare variable

```
int a;
int b=2;
var c = 3;//type is inferred,here the type of c is int
var d = String.format("%d",4);//type is String
```

## Interpolation

```
var name = "cszz";
println( "hello,${name}!" );//output:hello,cszz!
```

## Loop

for loop:

```
for(int i=0;i<9;i++){
    //do Some Thing
}
```

foreach loop

```
foreach([1,2,3] as i,v){
    println("index:" + i + ",value:"+v);
}
```

loop invoke

```
var arr = ["a","ab","abc"];
var lens = arr*.length();//lens will be [1,2,3]
```

while loop:

```
while(expression){
    //do Some Thing
}
```

do loop:

```
do{
//do some thing
}while(expression);
```

## Comments

```
//line comment

/*
multi-line comment
*/

```

## Declare array

```
int[] arr = new int[10];//length is 10
int[] arr2 = new int[]{1,2,3};arr2 is initialized and it's length is 3
```

or

```
var intArray = [1,2,3];//int[]
var integerArray= <Integer>[1,2,3];//Integer[]
var stringArray = ["hello","world"];//String[]
```

## Declare map

```
var objectMap = [name:"cszz",year:2016];//Map<Object,Object>
var stringMap = <String,String>[name:"cszz",type:"language"];//Map<String,String>
var ov = objectMap.get("name");//ov is Object type
var sv = stringMap.get("name");sv is String type
```

## Null safe

```
void hello(String? name){
    name.length();//compile error:name may be null
    if(name!=null){
        name.length();//compile passed:name is null checked
    }
}
```

## Cast type automatically

```
Object s = "hello";
if(s instanceof String){
    s.chars();//the type of s is changed from Object to String automatically
}
```
