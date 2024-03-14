interface ExprC

class NumC(val n: Float = 0.0f) : ExprC
class StrC(val s: String) : ExprC
class IfC(val a: ExprC, b: ExprC, c: ExprC)
class IdC(val name: String)
class AppC(val fun: ExprC, arg: Array<ExprC>)
class LamC(val arg: Array<String>, body: ExprC)

class Env
{

}

class Value 
{

}


fun interp(exp: ExprC, env: Env) {
  when {
    exp is NumC && exp.n == 10.0f -> println("1 " + exp.n)
    exp is NumC && exp.n == 13.0f -> println("2 " + exp.n)
    else -> println("3")
  }
}

fun parse(s: String) {

}

fun serialize(value: Value) {

}

fun main(args: Array<String>) {
  println("Hello World!")
  
  val obj = NumC(10.0f)
  interp(obj, Env())
}