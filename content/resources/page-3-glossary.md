# Glossary

A glossary of terminology specific to Clojure. Terms
are listed in alphabetical order.

*What about...?* Are we missing a term you you need? Let us know!
You can ask by filing a new [issue](https://github.com/clojurebridge-minneapolis/installfest/issues).

*NOTE:* portions of this glossary have been adapted
from the [Clojure Doc Site](https://github.com/clojuredocs/guides)
under the [CC-BY-SA 3.0](http://creativecommons.org/licenses/by/3.0/) license.


### arity

The number of arguments a function takes is its arity.  If it's
written to take a variable number of args, it's referred to as
[variadic](#variadic).

Functions can have multiple arity (for example, a function might have
2 different bodies: one for when 2 args are passed, and another when 3
args are passed).



### binding-form

Could mean one of two things:

 1. the expression you're binding to in a
    [let-binding](#let_binding). It might be a simple name, or it
    might be a data structure used for
    [destructuring](#destructuring).

 2. Clojure provides the `binding` macro, used for setting the
    thread-local value of a dynamic var. The whole expression (form)
    is sometimes referred to as the "binding [form](#form)".


### classpath

The search path used by the JVM to locate classes which are not
part of the Java standard class library. May include jar files.



### comparator

A function that takes two args and compares them.  Returns -1, 0, or 1
depending whether the first arg is less than, equal to or greater than
the second.  The stock comparator that Clojure.core comes with is
`compare`.



### coordinates

The "group-id/artifact-id version-string" identifier used in your
project.clj to indicate a particular dependency.

See also [libspec](#libspec).



### destructuring

The handy trick used in a [let-binding](#let_binding) to "unpack" the
values from a data structure into the locals you're going to use.  See
also [binding-form](#binding-form).


### dereference


To get the value of a reference type. You can use the `deref` function
for this, or else some syntactic sugar: `@some-ref-type`.



### entry

A key/value pair in a map. Try `(type (first {:a 1 :b 2}))` and see
that it returns `clojure.lang.MapEntry`.



### evaluator

*todo*



### form

A valid s-expression. For example: `(+ 1 1)` and 
`(defn foo [x] (* x x))`.



### head retention

[Lazy](#lazy) sequences are still [persistent](#persistence). If you
make *another* data structure using one, the original lazy sequence
will be kept around and not garbage-collected. If the lazy sequence in
infinite, and grows very large, it can cause performance problems or
even an out-of-memory error. Accidentally keeping around a lazy
sequence like this is referred to as "head retention".


### homoiconicity

Where the code and the data is represented by the same structure.
This allows the code to be treated as data, and the data to be treated
as code. This feature of Clojure, and other Lisps, allows for
macros in the language, since they can operate on code as a data
structure, and to return a transformation of that structure to
be the representation of new code.



### idempotent

An operation that when given the same inputs will produce the same
result when called one or more times. An idempotent function may
produce a side effect, such a updating a ref or an atom, but will
only produce the side effect once. An idempotent function is
different than a pure function, in that a pure function will
produce no side effects.



### identity

A logical entity in your program that may change over time --- it may take on
different states at different times, but it still means the same logical entity.
Clojure uses [reference types](#reference_types) to represent identities. This
is not to be confused with the `identity` function that just returns the
argument given to it.



### implicit do

The bodies of some expressions act like `do` in that you can include
multiple expressions in them, and the expressions will be evaluated in
the order they appear, with the resulting value of the body being the
last expression evaluated. Forms that do this include: `when`,
`when-let`, `fn`, `defn`, `let`, `loop`, and `try`.



### intern

A method of storing values or immutable data structures as a single
copy of the item, allowing for more space-efficiency, and possibly
time-efficiency, with the trade off of requiring more time being
required when interning the item. When the string "clojure" is interned,
all instances of the string "clojure" will reference the exact same
instance, instead of having multiple string objects with the same value
of "clojure".



### keyword

A Clojure scalar data type whose literal syntax looks `:like` `:this`.
They are like numbers and strings in that they evaluate to themselves,
and are most often seen being used as keys in [hash-maps](#map).

See also [namespaced keyword](#namespaced_keyword)

The term is also used when talking about functions that take "keyword
arguments", for example, something like: `(my-func :speed 42 :mass 2)`
(as opposed to `(my-func {:speed 42 :mass 2})`).



### lazy

Clojure can (and often does) create sequences for you that aren't
fully computed. Upon casual inspection they *look* just like a regular
list, but particular values in them are only computed the moment you
ask for them --- not sooner.

This has the added benefit that you can easily create infinite
sequences that don't consume infinite memory.

Many of the built-in Clojure functions return lazy sequences.

See also [realize](#realize).



### let-binding

AKA, "binding vector", or just "bindings": in a `let` (and expressions
that work like let, for example, `defn`, `loop`, `loop`, & `fn`), the
vector that comes first where you specify lexical bindings.

See also [binding form](#binding_form)



### libspec

*todo*



### macro

A special type of function which is transforms a S-Expression read in
and applies a transformation to the S-Expression resulting in a new
form.  This process is called macro-expansion, and is done as part
of the Clojure reader.



### map

Either refers to the built in `map` function, or else means "a
hash-map object".



### memoization

The ability to cache a result of a function call by given arguments,
and return the result without having to do the calculation again.
Memoization is a time-space trade off in that more memory is used
to store the results of a function call to be able to return the
value instead of having to keep spending time doing the calculation
involved in the function.



### metadata

An extra map that you can attach to a collection value (or a symbol),
which contains data about the data you're attaching it to. Use `meta`
to see the metadata of a given value.



### namespaced keyword

When you put two colons in front of a keyword's name --- for example
::foo --- it is a so-called "namespaced keyword", and is expanded by
the reader to become :current-namespace/foo.



### nullipotent

An operation with no side effects.  The result of calling the function
one or more times is the same as if it was never called.  Queries are
typically good examples of functions that are nullipotent, as they
do not modify the state of the object or structure they are queried
against.


### predicate

A function taking one or more args and returning a boolean (`true` or
`false`). Its name typically ends with a question mark. Some examples:
`nil?`, `zero?`, `string?`.



### pure function

A function that given the same inputs will always produce the same
result. A pure function also does not have any observable side effects
and cannot depend on any outside state, other than that which was given
as arguments to the function. A pure function's result also cannot change
during the execution of the program or between executions of the program,
as the dependency on outside state can lead to changes in the result of
the function.  Pure functions are also
[referentially transparent.](#referential-transparency)



### reader

*todo*



### reader macro

Syntax that the Clojure reader recognizes as special syntactic sugar,
for example, `#""`, `#{}`, quoting, etc.



### realize

When the next value in a [lazy](#lazy) sequence is accessed for the first time,
and is computed so as to made available, it is said to have been "realized".
This term is also used to refer to the status of **promises**, **futures**, and
**delays**. That is, if a promise (for example) is realized then that means its
value has been delivered and is accessible via [dereferencing](#dereference).



### reference types

Vars, atoms, refs, and agents are all reference types. They are
mutable in the sense that you can change to what value they refer, and
Clojure provides thread-safe mechanisms for doing so.



### referential transparency

An expression that will always return the same result for the values
given, and can be substituted for the resulting value, without
effecting the program.  The advantage of referential transparent
expressions is that they can be memoized, and be the subject of
various compilier optimizations.



### reify

*todo*



### REPL

Short for: "Read, Eval, Print, Loop".  The REPL reads in text through
the [reader](#reader) transforming it into a Clojure data structure,
[evaluates](#evaluator) the data structure as code, prints the result
of the evaluation, and loops back waiting to read the next input string.



### rest args

The extra args passed to a [variadic](#variadic) function, for example
if `my-func` were defined like `(defn my-func [a b & more] ...)`, then
called like `(my-func 1 2 3 4 5)`, then 3, 4, & 5 are the "rest args".



### s-expression

Short for Symbolic Expression. A S-Expression is a data structure able
to represent both simple datastructes such as literals, or complex data
structures such as nested expressions. Due to their versatile nature,
S-Expressions are able to represent both data in Clojure, as well as
the Clojure code itself, allowing Clojure to be a
[homoiconic](#homoiconicity) language.



### state

The [value](#value) that a given [identity](#identity) may have at a
given time.  When you change the state of an identity, you're changing
to which value it refers. Clojure uses values to represent states.



### STM (Software Transactional Memory)

Software Transactional Memory (STM) is a concurrency control method to
coordinate and control access to shared storage as an alternative to
lock-based synchronization. Clojure's STM uses multiversion concurrency
control (MVCC) as an alternative to lock-based transactions, as well as
ensuring changes are made atomically, consistently, and in
isolation. It does this by taking a snapshot of the ref, making the
changes in isolation to the snapshot, and apply the result. If the STM
detects that another transaction has made an update to the ref, the
current transaction will be forced to retry.



### symbol

An identifier that refers to vars or local values.


### tagged literals

(Formerly called "reader literals".)

Some literals begin with a hash mark "#" (so-called "dispatch
macros"); for example, `#{}` for sets and `#""` for regex
literals. Starting with Clojure 1.4, you can create your own
\#-prefixed literal which causes the reader to parse the form
following it using a function or macro of your own
choosing/devising. It's in this way that you can *tag* a literal to be
handled specially by the reader.

For more info, see [the "Tagged Literals" section of the reader
doc](http://clojure.org/reader).



### threading macros

The thread-first (`->`) and thread-last (`->>`) macros.  "Threading"
refers to how they pass values to each subsequent argument in the
macro, not concurrency.



### thrush

A combinator. Not the same thing as the [thread-first macro](#threading-macros).
More info at <http://blog.fogus.me/2010/09/28/thrush-in-clojure-redux/> if
you're curious.


### transaction

*todo*



### type erasure

Java-related: Java generics allow you to specify a type for a
collection.  This way you don't have to cast every object you pull out
of an ArrayList like in the old days. This is a courtesy of the java
compiler. The java runtime doesn't know about generics --- the
compiler does all the checking for you, then the type information is
discarded at runtime. In Clojure, this discarding is referred to as
type erasure.



### value

An immutable object, such as the number 1, the character `\a`, the
string "hello", or the vector `[1 2 3]`. In Clojure, all scalars and
built-in core data structures are values.



### variadic

A function that can take a variable number of arguments.
See also [rest args](#rest_args).
